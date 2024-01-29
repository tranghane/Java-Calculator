package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Event class
 * reference: https://github.students.cs.ubc.ca/CPSC210/AlarmSystem
 */
public class EventTest {
    private Event e;
    private Date d;

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
    public void testEquals() {
        Calculator c1 = new Calculator();
        Event none = null;

        assertNotEquals(e, none);
        assertNotEquals(e, c1);
    }

    @Test
    public void testHashCode() {
        Event event1 = new Event("Test event");
        Event event2 = new Event("Test event");
        assertEquals(event1.hashCode(), event2.hashCode());
    }

}

