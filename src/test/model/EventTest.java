package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Unit tests for the Event class was based of the EventTest class in the
 * AlarmSystem example
 * Git URL: https://github.students.cs.ubc.ca/CPSC210/AlarmSystem
 */
public class EventTest {
    private Event e;
    private Date d;

    private Customer c;

    //NOTE: these tests might fail if time at which line (2) below is executed
    //is different from time that line (1) is executed.  Lines (1) and (2) must
    //run in same millisecond for this test to make sense and pass.

    @BeforeEach
    public void runBefore() {
        e = new Event("Sensor open at door");   // (1)
        d = Calendar.getInstance().getTime();   // (2)
    }

    @Test
    public void testEvent() {
        assertEquals("Sensor open at door", e.getDescription());
        assertEquals(d, e.getDate());
    }

    @Test
    public void testToString() {
        assertEquals(d.toString() + "\n" + "Sensor open at door", e.toString());
    }

    @Test
    public void testEqualsNull() {
        assertFalse(e.equals(null));
    }

    @Test
    public void testEqualsStr() {
        c = new Customer("cat");
        assertFalse(e.equals(c));
    }

    @Test
    public void testHashCode() {
        int calculated = (13 * d.hashCode() + e.getDescription().hashCode());
        assertEquals(calculated, e.hashCode());
    }
}
