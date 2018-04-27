package fms.dao.UI.PersonPack.models;

/**
 * Created by Hector Lopez on 11/22/2017.
 */

public class eventModel {
    public String description;
    public String country;
    public String city;
    public String year;
    public String eventId;
    public String name;
    public eventModel(String description, String country, String city, String year, String name, String eventId){
        this.description = description;
        this.country = country;
        this.city = city;
        this.year = year;
        this.eventId = eventId;
        this.name = name;
    }
    public interface sendEventId{
        public String receiveEventId(String Id);
    }
}
