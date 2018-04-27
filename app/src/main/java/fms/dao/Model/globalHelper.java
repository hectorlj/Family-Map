package fms.dao.Model;

import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import results.personResults;
import results.singleEvent;

/**
 * Created by Hector Lopez on 11/4/2017.
 */

public class globalHelper {
    private static globalHelper instance = null;
    public boolean isMapActivity;
    public String markerEventSelectedEvent;
    private String markerEventSelectedUser;
    private GoogleMap currentMap;
    private int mapType;
    private int spouseLineColor;
    private int eventLineColor;
    private int familyLineColor;
    private boolean spouseLinesVisible;
    private boolean familyLinesVisible;
    private boolean eventLinesVisible;
    private currentUser user;
    private Map<String, Set<String>> familyTree;
    private List<singleEvent> allEventsList;
    private Set<String> eventDescriptions;
    private List<Float> colorList;
    private Map<String , Set<singleEvent>> allEventsMap;
    private Map<String, Float> eventColored;
    private Map<String, List<singleEvent>> personEventsMap;
    private List<personResults> allPeopleList;
    public List<String > mapTypeArray;
    public List<String >lineColorList;
    private Map<String, String> eventMapType;
    private boolean loggedIn;
    private boolean fatherLines;
    private boolean motherLines;
    private boolean femaleLines;
    private boolean maleLines;
    private boolean marriageLines;
    private boolean deathLines;
    private boolean birthLines;
    private boolean baptismLines;

    private globalHelper(){
        allPeopleList = new ArrayList<>();
        allEventsList = new ArrayList<>();
        colorList = new ArrayList<>();
        colorList.add(BitmapDescriptorFactory.HUE_BLUE);
        colorList.add(BitmapDescriptorFactory.HUE_RED);
        colorList.add(BitmapDescriptorFactory.HUE_GREEN);
        colorList.add(BitmapDescriptorFactory.HUE_YELLOW);
        colorList.add(BitmapDescriptorFactory.HUE_ORANGE);
        colorList.add(BitmapDescriptorFactory.HUE_VIOLET);
        spouseLinesVisible = true;
        familyLinesVisible = true;
        eventLinesVisible = true;
        loggedIn = false;
        user = new currentUser();
        mapTypeArray = Arrays.asList("Normal", "Hybrid", "Satellite", "Terrain");
        lineColorList = Arrays.asList("Blue", "Green", "Red", "Black", "Yellow", "White");
        mapType = 0;
        spouseLineColor = 0;
        eventLineColor = 1;
        familyLineColor = 2;
        isMapActivity = false;
        fatherLines = true;
        motherLines = true;
        maleLines = true;
        femaleLines = true;
        deathLines = true;
        marriageLines = true;
        birthLines = true;
        baptismLines = true;
        eventMapType = new HashMap<>();
    }

    public void eraseData(){
        allPeopleList = new ArrayList<>();
        allEventsList = new ArrayList<>();
        colorList = new ArrayList<>();
        colorList.add(BitmapDescriptorFactory.HUE_BLUE);
        colorList.add(BitmapDescriptorFactory.HUE_RED);
        colorList.add(BitmapDescriptorFactory.HUE_GREEN);
        colorList.add(BitmapDescriptorFactory.HUE_YELLOW);
        colorList.add(BitmapDescriptorFactory.HUE_ORANGE);
        colorList.add(BitmapDescriptorFactory.HUE_VIOLET);
        mapTypeArray = Arrays.asList("Normal", "Hybrid", "Satellite", "Terrain");
        lineColorList = Arrays.asList("Blue", "Green", "Red", "Black", "Yellow", "White");
        spouseLinesVisible = true;
        familyLinesVisible = true;
        eventLinesVisible = true;
        deathLines = true;
        marriageLines = true;
        birthLines = true;
        baptismLines = true;
        loggedIn = false;
        user = new currentUser();
        mapType = 0;
        spouseLineColor = 0;
        eventLineColor = 1;
        familyLineColor = 2;
        isMapActivity = false;
        fatherLines = true;
        motherLines = true;
        maleLines = true;
        femaleLines = true;
        eventMapType = new HashMap<>();
    }
    public static globalHelper getInstance(){
        if(instance == null){
            instance = new globalHelper();
        }
        return instance;
    }
    public String getMarkerEventSelectedUser(){return markerEventSelectedUser;}
    public int getMapType(){return mapType;}
    public int getSpouseLineColor(){return spouseLineColor;}
    public int getEventLineColor(){return eventLineColor;}
    public int getFamilyLineColor(){return familyLineColor;}
    public String getMarkerEventSelectedEvent(){return markerEventSelectedEvent;}
    public boolean isLoggedIn(){return loggedIn;}
    public boolean isMapActivity(){return isMapActivity;}
    public boolean isSpouseLinesVisible(){return spouseLinesVisible;}
    public boolean isFamilyLinesVisible(){return familyLinesVisible;}
    public boolean isEventLinesVisible(){return eventLinesVisible;}
    public boolean isFatherLines(){return fatherLines;}
    public boolean isMotherLines(){return motherLines;}
    public boolean isFemaleLines(){return femaleLines;}
    public boolean isMaleLines(){return maleLines;}
    public boolean isMarriageLines(){return marriageLines;}
    public boolean isDeathLines(){return deathLines;}
    public boolean isBirthLines(){return birthLines;}
    public boolean isBaptismLines(){return baptismLines;}
    public GoogleMap getCurrentMap(){return currentMap;}
    public currentUser getUser(){return user;}
    public Map<String, Set<String >> getFamilyTree(){return familyTree;}
    public Map<String, List<singleEvent>> getPersonEventsMap(){return personEventsMap;}
    public Map<String, Float> getEventColored(){return eventColored;}
    public Map<String, Set<singleEvent>> getAllEventsMap(){return allEventsMap;}
    public List<Float> getColorList(){return colorList;}
    public List<personResults>getAllPeopleList(){return allPeopleList;}
    public List<singleEvent> getAllEventsList(){return allEventsList;}
    public Set<String> getEventDescriptions(){return eventDescriptions;}
    public void setMapActivity(boolean mapActivity){
        this.isMapActivity = mapActivity;
    }
    public void setMotherLinesChecked(boolean checked){this.motherLines = checked;}
    public void setFatherLinesCheck(boolean checked){this.fatherLines = checked;}
    public void setMaleLinesCheck(boolean checked){this.maleLines = checked;}
    public void setFemaleLinesCheck(boolean checked){this.femaleLines = checked;}
    public void setMarriageLines(boolean checked){this.marriageLines = checked;}
    public void setBirthLines(boolean checked){this.birthLines = checked;}
    public void setBaptismLines(boolean checked){this.baptismLines = checked;}
    public void setDeathLines(boolean checked){this.deathLines = checked;}
    public void setMarkerEventSelectedUser(String markerEventSelectedUser){
        this.markerEventSelectedUser = markerEventSelectedUser;
    }
    public void setMarkerEventSelectedEvent(String markerEventSelectedEvent){
        this.markerEventSelectedEvent = markerEventSelectedEvent;
    }
    public void setCurrentMap(GoogleMap currentMap){
        this.currentMap = currentMap;
    }
    public void setMapType(int mapType){
        this.mapType = mapType;
    }
    public void setSpouseLineColor(int spouseLineColor){
        this.spouseLineColor = spouseLineColor;
    }

    public ArrayList<String> getChildren(String id){
        ArrayList<String> children = new ArrayList<>();
        for(int i = 0; i < allPeopleList.size(); i++){
            if((allPeopleList.get(i).person.getMother().equals(id)&&
                    allPeopleList.get(i).person.getMother() != null) ||
                    ( allPeopleList.get(i).person.getFather() != null&&
                    allPeopleList.get(i).person.getFather().equals(id))){
                children.add(allPeopleList.get(i).getPersonID());
            }
        }
        return children;
    }

    public void getSide(String id, Set<String > parentSide){
        if(familyTree.containsKey(id)){
            parentSide.add(id);
        }
        if(familyTree.containsKey(id) && familyTree.get(id) != null){
            for(String parent : familyTree.get(id)){
                getSide(parent, parentSide);
            }
        }
    }

    public void setEventLineColor(int eventLineColor){
        this.eventLineColor = eventLineColor;
    }
    public void setFamilyLineColor(int familyLineColor){
        this.familyLineColor = familyLineColor;
    }
    public void setSpouseLinesVisible(boolean spouseLinesVisible){
        this.spouseLinesVisible = spouseLinesVisible;
    }
    public void setFamilyLinesVisible(boolean familyLinesVisible){
        this.familyLinesVisible = familyLinesVisible;
    }
    public void setEventLinesVisible(boolean eventLinesVisible){
        this.eventLinesVisible = eventLinesVisible;
    }
    public void setUser(currentUser user){
        this.user = user;
    }
    public void setAllPeopleList(List<personResults> peopleList){
        allPeopleList = peopleList;
    }
    public void setAllEventsList(List<singleEvent> eventsList){
        allEventsList = eventsList;
    }
    public singleEvent getEarliestDate(String id){
        singleEvent earliest = new singleEvent();
        earliest.event.setYear(99999999);
        for(singleEvent event : allEventsList){
            if(event == null)
                Log.d("Error", event.toString() + "\n" + allEventsList.size());
            if(event.event.getPersonId().toLowerCase().equals(id.toLowerCase()) && event.event.getYear() < earliest.event.getYear())
                earliest = event;
        }
        if(earliest.event.getYear() == 99999999)
            return null;
        return earliest;
    }
    public void setEventDescriptions(){
        eventDescriptions = new HashSet<>();
        for (singleEvent event : allEventsList){
            eventDescriptions.add(event.event.getEventType().toLowerCase());
            if(!eventMapType.containsKey(event.event.getEventType().toLowerCase())){
                eventMapType.put(event.event.getEventType().toLowerCase(), "true");
            }
        }
    }

    public Map<String, String> getEventTypeMap(){
        return eventMapType;
    }

    public void changeEventTypeMap(String eventType, boolean isChecked){
        if(!isChecked) {
            eventMapType.put(eventType.toLowerCase(), "false");
        } else{
            eventMapType.put(eventType.toLowerCase(), "true");
        }
    }

    public void sortEvents(List<singleEvent> eventList){
        Collections.sort(eventList, new Comparator<singleEvent>() {
            @Override
            public int compare(singleEvent o1, singleEvent o2) {
                return o1.event.getYear() - o2.event.getYear();
            }
        });
    }
    public void buildPersonEventsMap(){
        personEventsMap = new HashMap<>();
        for (singleEvent eventList : allEventsList) {
            if (!personEventsMap.containsKey(eventList.event.getPersonId())) {
                personEventsMap.put(eventList.event.getPersonId(), new ArrayList<singleEvent>());
            }
            personEventsMap.get(eventList.event.getPersonId()).add(eventList);
        }
    }
    public Set<String> fatherSide;
    public Set<String> motherSide;
    public void setLoggedIn(boolean loggedIn){
        this.loggedIn = loggedIn;
    }
    public void buildFamilyTree(){
        familyTree = new HashMap<>();
        for (personResults person : allPeopleList){
            String id = person.person.getPersonId();
            if(!familyTree.containsKey(id)) {
                familyTree.put(id, new HashSet<String>());
            }
            familyTree.get(id).add(person.person.getMother());
            familyTree.get(id).add(person.person.getFather());

        }
    }
    public Set<String > buildFatherSide(String fatherId){
        if(Objects.equals(fatherId, ""))
            return null;
        fatherSide = new HashSet<>();
        getSide(fatherId, fatherSide);
        Set<String> fatherEvents = new HashSet<>();
        for(int i = 0; i < user.getEvents().length; i++){
            if(fatherSide.contains(user.getEvents()[i].event.getPersonId())){
                fatherEvents.add(user.getEvents()[i].event.getEventId());
            }
        }
        return fatherEvents;
    }

    public Set<String > buildMotherSide(String motherId){
        if(Objects.equals(motherId, ""))
            return null;
        motherSide = new HashSet<>();
        getSide(motherId, motherSide);
        Set<String> motherEvents = new HashSet<>();
        for (int i = 0; i < user.getEvents().length; i++){
            if(motherSide.contains(user.getEvents()[i].event.getPersonId())){
                motherEvents.add(user.getEvents()[i].event.getEventId());
            }
        }
        return motherEvents;
    }


    public void buildAllEventMap(){
        allEventsMap = new HashMap<>();
        for (singleEvent event : allEventsList){
            if(!allEventsMap.containsKey(event.event.getEventType().toLowerCase())){
                allEventsMap.put(event.event.getEventType().toLowerCase(), new HashSet<singleEvent>());
            }
            allEventsMap.get(event.event.getEventType().toLowerCase()).add(event);
        }
    }

    public void buildEventColored(){
        int count = 0;
        eventColored = new HashMap<>();
        for(String event : eventDescriptions){
            count = count % 6;
            eventColored.put(event, colorList.get(count));
            count++;
        }
    }

}
