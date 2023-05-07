package com.github.agoss94.track.manager;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * The optimal dispatcher finds an optimal solution for a collection of events
 * with a given time constraint. The dispatcher looks for an optimal solution by
 * performing the following algorithm: Assume we have a given list of events.
 * <p>
 * <ol>
 * <li>First we check if the combined duration of all events is a solution. If
 * it is</li>
 * </ol>
 */
public class OptimalDispatcher implements Dispatcher {

    /**
     * The starting points for all events.
     */
    private final LocalTime start;

    /**
     * The time limitation for dispatching the events.
     */
    private final Duration limit;

    /**
     * Each array in the set correspondences with a subset of events.
     */
    private Set<int[]> subsets;

    /**
     * A given list of events.
     */
    private List<Event> events;

    /**
     * Creates an optimal dispatcher, which dispatches events at the given start
     * time with a maximal duration. The dispatcher finds an optimal selection of
     * events whose combined duration is less than or equal to the given limit.
     * Events that do not fit in are discarded.
     *
     * @param start the start time of the track.
     * @param limit the time limit of the track.
     * @throws NullPointerException if start or limit is {@code null}.
     */
    public OptimalDispatcher(LocalTime start, Duration limit) {
        this.start = Objects.requireNonNull(start);
        this.limit = Objects.requireNonNull(limit);
    }

    /**
     * The dispatcher looks for an optimal solution by discarding as few events as
     * possible. No guarantee is given which solution is picked if multiple such
     * solutions exist. We assume that all events have finite duration.
     *
     * @param a collection of events.
     * @return a track with an optimal solution under the given time constrain.
     * @throws NullPointerException if events is {@code null}.
     */
    @Override
    public Track dispatch(Collection<Event> collection) {
        Objects.requireNonNull(collection);
        events = Collections.unmodifiableList(new ArrayList<>(collection));
        int[] fullSet = new int[collection.size()];
        Arrays.fill(fullSet, 1);
        subsets = new HashSet<>();
        subsets.add(fullSet);

        // The limit is to small for the collection of events.
        if (events.stream().noneMatch(e -> limit.compareTo(e.getDuration()) >= 1)) {
            throw new IllegalArgumentException("No solution possible.");
        }

        // The loop continues until there is only a set of solution
        // This set must contain an optimal solution.
        while (subsets.stream().anyMatch(s -> !isSolution(s))) {
            findSubsets();
        }

        int[] optimalSolution = Collections.max(subsets,
                (a, b) -> calculateDuration(a).compareTo(calculateDuration(b)));

        Track track = new Track();
        for (int i = 0; i < events.size(); i++) {
            if (optimalSolution[i] == 1) {
                LocalTime time = track.isEmpty() ? start : track.end();
                track.put(time, events.get(i));
            }
        }

        return track;
    }

    /**
     * We construct subset to all non-solution arrays and add them to
     * {@link #subsets}
     */
    private void findSubsets() {
        Set<int[]> newSubsets = new HashSet<>();
        Set<int[]> toRemove = new HashSet<>();
        for (int[] subset : subsets) {
            // A solution cannot have a better subset solution.
            if (!isSolution(subset)) {
                toRemove.add(subset);
                newSubsets.addAll(constructSubsets(subset));
            }
        }
        subsets.removeAll(toRemove);
        subsets.addAll(newSubsets);
    }

    /**
     * We construct new subsets by leaving out one more element over a threshold
     * {@code min}, whereas {@code min} is the smallest index of an element that is
     * left out.
     *
     * @param subset an array of integer indicating which events to take.
     * @return a set of subset arrays.
     */
    private Set<int[]> constructSubsets(int[] subset) {
        Set<int[]> subSets = new HashSet<>();
        // By setting i = indexLastZero + 1 we can guarantee to not form the same
        // subset twice. It is easy to see, that all subset constructed from two arrays
        // with at least one different zero position must therefore mutually distinct.
        // Equally it is easy to see that every possible subset will we formed.
        for (int i = indexLastZero(subset) + 1; i < subset.length; i++) {
            if (subset[i] == 1) {
                int[] newSubset = subset.clone();
                newSubset[i] = 0;
                subSets.add(newSubset);
            }
        }
        return subSets;
    }

    /**
     * Returns {@code true} if {@link #calculateDuration(int[])} is below the limit.
     *
     * @param subset an array of integer indicating which events to take.
     * @return {@code true} if {@link #calculateDuration(int[])} is below the limit.
     */
    private boolean isSolution(int[] subset) {
        return limit.compareTo(calculateDuration(subset)) >= 0;
    }

    /**
     * Returns the combined duration of a subset of events as indicated by the given
     * array.
     *
     * @param subset an array of integer indicating which events to take.
     * @return the combined duration of all chosen events.
     */
    private Duration calculateDuration(int[] subset) {
        Duration d = Duration.ZERO;
        for (int i = 0; i < events.size(); i++) {
            if (subset[i] == 1) {
                Event e = events.get(i);
                d = d.plus(e.getDuration());
            }
        }
        return d;
    }

    /**
     * Returns the index of the last zero observed in the array or -1 if none is
     * present.
     *
     * @param array an array.
     * @return the index of the last zero observed in the array or -1 if none is
     *         present.
     */
    private int indexLastZero(int[] array) {
        int result = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == 0) {
                result = i;
            }
        }
        return result;
    }
}
