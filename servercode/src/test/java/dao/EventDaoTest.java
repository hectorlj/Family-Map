package dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import datagen.Locations;
import datagen.randomData;
import handlers.Connector;
import shared.Model.Event;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
/**
 * Created by Hector Lopez on 11/1/2017.
 */
public class EventDaoTest {
    Connector connector = new Connector();
    EventDao eventDao = new EventDao();
    randomData randData = new randomData();
    Event event;
    @Before
    public void setUp() throws Exception {
        new Event();
        Locations.area temp = randData.randLocation();
        event = new Event("0", "Hector", "Person", temp.latitude, temp.longitude, temp.country, temp.city, randData.events.get(0), 1993);
        eventDao.addEvent(event, connector);
    }

    @After
    public void tearDown() throws Exception {
        connector.openConnection();
        connector.clear();
        connector.closeConnection(true);
    }

    @Test
    public void addEvent() throws Exception {
        Locations.area temp = randData.randLocation();
        Event testEvent = new Event("0", "Hector", "Person", temp.latitude, temp.longitude, temp.country, temp.city, randData.events.get(0), 1993);
        eventDao.addEvent(testEvent, connector);
        boolean inDatabase = eventDao.readEvent(testEvent.getEventId(), connector);
        assertTrue(inDatabase);
    }

    @Test
    public void removeEvent() throws Exception {
        eventDao.removeEvent("Hector", connector);
        boolean inDatabase = eventDao.readEvent("0", connector);
        assertFalse(inDatabase);

    }

    @Test
    public void getEvent() throws Exception {
        String eventId = eventDao.getEvent("EventID","Descendant", "Hector", connector);
        assertEquals(event.getEventId(), eventId);
    }

    @Test
    public void getMultiEvent() throws Exception {
        ArrayList<String> eventsList = eventDao.getMultiEvent("Hector", connector);
        assertTrue(!eventsList.isEmpty());
    }

    @Test
    public void readEvent() throws Exception {
        boolean inDatabase = eventDao.readEvent("0", connector);
        assertTrue(inDatabase);
    }

    @Test
    public void getNum() throws Exception {
        double eventDaoLatLong = eventDao.getLatLong("Latitude", "0", connector);
        assertTrue(eventDaoLatLong == event.getLatitude());
    }

}