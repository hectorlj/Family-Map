package shared.Model;

/**
 * Created by Hector Lopez on 10/20/2017.
 */
public class Event {
    private String eventID = "";
    private String descendant = "";
    private String personID = "";
    private Double latitude = 0.0;
    private Double longitude = 0.0;
    private String country = "";
    private String city = "";
    private String eventType = "";
    private int year = 0;
    public Event(){}
    public Event(String inputEventId, String inputDescendant, String inputPersonId,
                 Double inputLatitude, Double inputLongitude, String inputCountry,
                 String inputCity, String inputEventType, int inputYear){
        setEventId(inputEventId);
        setDescendant(inputDescendant);
        setPersonId(inputPersonId);
        setLatitude(inputLatitude);
        setLongitude(inputLongitude);
        setCountry(inputCountry);
        setCity(inputCity);
        setEventType(inputEventType);
        setYear(inputYear);
    }
    public String getEventId(){return eventID;}
    public String getDescendant(){return descendant;}
    public String getPersonId(){return personID;}
    public String getCountry(){return country;}
    public String getCity(){return city;}
    public String getEventType(){return eventType;}
    public Double getLatitude(){return latitude;}
    public Double getLongitude(){return longitude;}
    public int getYear(){return year;}

    public void setEventId(String newEventId){
        this.eventID = newEventId;
    }
    public void setDescendant(String newDescendant){
        this.descendant = newDescendant;
    }
    public void setPersonId(String newPersonId){
        this.personID = newPersonId;
    }
    public void setLatitude(Double newLatitude){
        this.latitude = newLatitude;
    }
    public void setLongitude(Double newLongitude){
        this.longitude = newLongitude;
    }
    public void setCountry(String newCountry){
        this.country = newCountry;
    }
    public void setCity(String newCity){
        this.city = newCity;
    }
    public void setEventType(String newEventType){
        this.eventType = newEventType;
    }
    public void setYear(int newYear){
        this.year = newYear;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Event:\n[\nEventID: ").append(eventID).append("\nDescendant: ").append(descendant).append("\nPersonID: ").append(personID).append("\nLatitude: ").
                append(latitude).append("\nLongitude: ").append(longitude).append("\nCountry: ").append(country).append("\nCity: ").
                append(city).append("\nEventType: ").append(eventType).append("\nYear: ").append(year).append("\n]\n\n");
        return sb.toString();
    }
}
