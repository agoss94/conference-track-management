package com.github.agoss94.track.manager;

import java.time.Duration;

/**
 * This class represent a talk. A talk has a title and a duration.
 */
public class Talk {

	/**
	 * The title of the talk.
	 */
	private final String title;

	/**
	 * The duration of the talk in minutes.
	 */
	private final Duration duration;

	/**
	 * Simple constructor creates a Talk object with the given title and specified
	 * duration.
	 *
	 * @param title    the title of the talk.
	 * @param duration the duration of the talk.
	 */
	public Talk(String title, Duration duration) {
		this.title = title;
		this.duration = duration;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the duration
	 */
	public Duration getDuration() {
		return duration;
	}

}
