package fms.dao.Model;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

import results.personResults;
import results.singleEvent;

/**
 * Created by Hector Lopez on 11/10/2017.
 */

public class currentUser {
    public String father;
    public String mother;
    private String personId;
    private String authToken;
    private String userName;
    private String port;
    private String host;
    private personResults[] family;
    private singleEvent[] events;
    private ArrayList<singleEvent> eventArrayList;
    private ArrayList<personResults> personResultsArrayList;
    private String firstName;
    private String lastName;

    public String getPersonId(){return personId;}
    public String getAuthToken(){return authToken;}
    public String getUserName(){return userName;}
    public String getPort(){return port;}
    public String getHost(){return host;}
    public ArrayList<personResults> getPersonResultsArrayList(){return personResultsArrayList;}
    public ArrayList<singleEvent> getEventArrayList(){return eventArrayList;}
    public personResults[] getFamily(){return family;}
    public singleEvent[] getEvents(){return events;}
    public String getFirstName(){return firstName;}
    public String getLastName(){return lastName;}
    public void setPersonId(String personId){
        this.personId = personId;
    }
    public void setAuthToken(String authToken){
        this.authToken = authToken;
    }
    public void setUserName(String userName){
        this.userName = userName;
    }
    public void setPort(String port){
        this.port = port;
    }
    public void setHost(String host){
        this.host = host;
    }
    public void setFamily(personResults[] family){
        this.family = family;
    }
    public void setEvents(singleEvent[] events){
        this.events = events;
    }
    public void setFirstName(String firstName){this.firstName = firstName;}
    public void setLastName(String lastName){this.lastName = lastName;}
    public void setFamilyToArrayList(){
        personResultsArrayList = new ArrayList<>(Arrays.asList(family));
        Log.d("Error", personResultsArrayList.toString());

    }
    public void setGender(){
        for(personResults person : family){
            for(singleEvent event : events){
                if(person.person.getPersonId().toLowerCase().equals(event.event.getPersonId().toLowerCase())){
                    event.personGender = person.person.getGender();
                }
            }
        }
    }
    public void setEventsToArrayList(){
        eventArrayList = new ArrayList<>(Arrays.asList(events));
        Log.d("Error", eventArrayList.toString());
    }
}
