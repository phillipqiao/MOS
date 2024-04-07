package ui.exception;

/**
 * Represents the exception that can occur when
 * printing the event log.
 * Code was based off the AlarmSystem example,
 * Git URL: https://github.students.cs.ubc.ca/CPSC210/AlarmSystem
 */
public class LogException extends Exception {

    // EFFECTS: construct a LogException.
    public LogException() {
        super("Error printing log");
    }

    // EFFECTS: construct a LogException with the error msg passed in.
    public LogException(String msg) {
        super(msg);
    }
}