package model;

import java.util.Calendar;
import java.util.Date;


/**
 * Represents an alarm system event.
 * Code was based off the AlarmSystem example,
 * Git URL: https://github.students.cs.ubc.ca/CPSC210/AlarmSystem
 */
public class Event {
    private static final int HASH_CONSTANT = 13;
    private Date dateLogged;
    private String description;

    /**
     * Creates an event with the given description
     * and the current date/time stamp.
     * @param description  a description of the event
     */
    // EFFECTS: Creates an event with the given description
    //          and the current date/time stamp.
    public Event(String description) {
        dateLogged = Calendar.getInstance().getTime();
        this.description = description;
    }

    /**
     * Gets the date of this event (includes time).
     * @return  the date of the event
     */
    public Date getDate() {
        return dateLogged;
    }

    /**
     * Gets the description of this event.
     * @return  the description of the event
     */
    public String getDescription() {
        return description;
    }

    @Override
    // EFFECTS: returns whether the passed in is the same object as the caller object(this)
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }


        if (other.getClass() != this.getClass()) {
            return false;
        }

        Event otherEvent = (Event) other;

        return (this.dateLogged.equals(otherEvent.dateLogged)
                && this.description.equals(otherEvent.description));
    }

    @Override
    // EFFECTS: overridden hashcode method that returns a int
    public int hashCode() {
        return (HASH_CONSTANT * dateLogged.hashCode() + description.hashCode());
    }

    @Override
    // EFFECTS: returns this as a string including the dateLogged description
    public String toString() {
        return dateLogged.toString() + "\n" + description;
    }
}