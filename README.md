# Track Manager

The track manager dispatches a list of events and plans a conference into multiple tracks. Each track has an hour lunch at 12PM and a Networking Event starts between 4PM and 5PM. No event can be longer than 4 hours, as it would not fit in any session.

In order to run the program you place the executable jar file (tm.jar) anywhere on your computer and start it from the terminal.

    java -jar tm.jar pathToInput.txt

You must provide the location of an input text file. The program will then output a text file with the same name and `timetable` at the end in the same folder as the input file. Pease note that you need to have Java 11 installed on your machine.

## Experimental Optimal Option

The track manager has an experimental optimal option, which looks for an optimal solution of the problem. Please note the time-complexity of this solution is exponential and it is therefore discouraged to try dispatching large Events `(n > 20)`.
To start the track manager in optimal mode start the program in the terminal with

    java -jar tm.jar pathToInput.txt -optimal

## Track Manager API
If you want to plan a different event you can write your own event manager by importing the track-manager jar into a java project and implementing the `Dispatcher` interface. 

