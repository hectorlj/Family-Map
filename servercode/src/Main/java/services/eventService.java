package services;

import dao.AuthTokenDao;
import dao.EventDao;
import handlers.Connector;
import results.singleEvent;
import shared.Model.Event;


/**
 * Created by Hector Lopez on 10/28/2017.
 */

class eventService {
    public boolean eventAuthCheck(String authToken, String eventID, Connector connector){
        EventDao eventDao = new EventDao();
        AuthTokenDao authTokenDao = new AuthTokenDao();
        String UserName = eventDao.getEvent("Descendant", "EventID", eventID, connector);
        String authTokenDb = authTokenDao.getAuthToken(connector, "AuthToken", "UserName", UserName);
        return authToken.equals(authTokenDb);
    }
    public boolean eventCheck(String eventID, Connector connector){
        return new EventDao().readEvent(eventID, connector);
    }
    public singleEvent buildEvent(String eventID, Connector connector){
        EventDao eventDao = new EventDao();
        int year = (int) eventDao.getLatLong("Year", eventID, connector);
        double latitude = eventDao.getLatLong("Latitude", eventID, connector);
        double longitude = eventDao.getLatLong("Longitude", eventID, connector);
        String Descendant = eventDao.getEvent("Descendant", "EventID", eventID, connector);
        String PersonId = eventDao.getEvent("PersonID", "EventID", eventID, connector);
        String Country = eventDao.getEvent("Country", "EventID", eventID, connector);
        String City = eventDao.getEvent("City", "EventID", eventID, connector);
        String EventType = eventDao.getEvent("EventType", "EventID", eventID, connector);
        Event myEvent = new Event(eventID, Descendant, PersonId, latitude, longitude, Country, City, EventType, year);
        return new singleEvent(myEvent);
    }
}
