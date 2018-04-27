package models;

import org.junit.Before;
import org.junit.Test;

import shared.Model.Event;

import static org.junit.Assert.assertTrue;

/**
 * Created by Hector Lopez on 11/3/2017.
 */
public class EventTest {
    Event event;
    @Before
    public void setUp() throws Exception {
        event = new Event("id", "des", "per", 1.0, -1.0, "count", "city", "type", 1443);
    }

    @Test
    public void getDescendant() throws Exception {
        assertTrue(event.getDescendant() == "des");
    }

    @Test
    public void setDescendant() throws Exception {
        event.setDescendant("dirtbag");
        assertTrue(event.getDescendant() == "dirtbag");
    }

    @Test
    public void testString() throws Exception {
        assertTrue(event.toString() != "");
    }

}