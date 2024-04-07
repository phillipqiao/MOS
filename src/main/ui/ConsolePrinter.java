package ui;

import model.Event;
import model.EventLog;
import ui.exception.LogException;

// Represents the ConsolePrinter of the GUI application.
// code this Class was based off:
// The AlarmSystem example.
// link: https://github.students.cs.ubc.ca/CPSC210/AlarmSystem
public class ConsolePrinter implements LogPrinter {

    @Override
    // EFFECTS: prints all events in the el to the console
    public void printLog(EventLog el) throws LogException {
        for (Event next : el) {
            System.out.println(next.toString() + "\n");
        }
    }
}