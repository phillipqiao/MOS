package ui;

import model.EventLog;
import ui.exception.LogException;

/**
 * Defines behaviours that event log printers must support.
 * Code was based off the AlarmSystem example,
 * Git URL: https://github.students.cs.ubc.ca/CPSC210/AlarmSystem
 */
public interface LogPrinter {
    /**
     * Prints the log
     * @param el  the event log to be printed
     * @throws LogException when printing fails for any reason
     */
    //void printLog(EventLog el) throws LogException;
    void printLog(EventLog el) throws LogException;
}
