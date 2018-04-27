package services;

import com.google.gson.Gson;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

import dao.AuthTokenDao;
import dao.EventDao;
import dao.PersonDao;
import dao.UserDao;
import datagen.Locations;
import datagen.randomData;
import handlers.Connector;

import requests.dualInputRequest;
import requests.registerRequest;
import results.LoginResult;
import results.personResults;
import results.resultsBase;
import results.singleEvent;
import shared.Model.*;

/**
 * Created by Hector Lopez on 10/27/2017.
 */

public class servicesLibrary {
    private final AuthTokenDao authTokenDao;
    private final EventDao eventDao;
    private final PersonDao personDao;
    private final UserDao userDao;
    private final ArrayList<String> EventTypes;
    public servicesLibrary(){
        authTokenDao = new AuthTokenDao();
        eventDao = new EventDao();
        personDao = new PersonDao();
        userDao = new UserDao();
        EventTypes = new randomData().events;
    }

    public resultsBase Register(registerRequest request, Connector connector) throws Exception {
        registerService registerHelper = new registerService();
        if(registerHelper.inputValidation(request) && !registerHelper.inDatabase(request, connector)){
            User user = registerHelper.buildUser(request);
            Person person = registerHelper.buildPerson(request, user.getUserId());
            Event event = registerHelper.buildUserEvent(user, EventTypes.get(0), connector);
            Event eventTwo = registerHelper.buildUserEvent(user, EventTypes.get(1), connector);
            Event eventThree = registerHelper.buildUserEvent(user, EventTypes.get(2), connector);
            Event eventFour = registerHelper.buildUserEvent(user, EventTypes.get(3), connector);

            AuthToken authToken = registerHelper.buildAuthTok(user.getUserName(), connector);
            userDao.addUser(user, connector);
            personDao.addPerson(connector, person);
            eventDao.addEvent(event, connector);
            eventDao.addEvent(eventTwo, connector);
            eventDao.addEvent(eventThree, connector);
            eventDao.addEvent(eventFour, connector);
            authTokenDao.addAuthToken(authToken, connector);
            ArrayList<Person> ancestors =  new ArrayList<>();
            ArrayList<Event> history = new ArrayList<>();
            registerHelper.buildGen(person, user.getUserName(), 4, event.getYear(), connector, ancestors, history);
            for(int i = 0; i < ancestors.size(); i++){
                personDao.addPerson(connector, ancestors.get(i));
            }
            for (int j = 0; j < history.size(); j++){
                eventDao.addEvent(history.get(j), connector);
            }
            LoginResult results = registerHelper.registerSuccess(authToken.getAuthToken(), user.getUserName(), person.getPersonId());
            return results;
        }else {
            return new resultsBase("Bad input please try again");
        }
    }


    public resultsBase Login(dualInputRequest request, Connector connector) throws SQLException {
        loginService helper = new loginService();
        boolean input = helper.checkInput(request);
        boolean user = helper.checkDatabase(connector, request);
        boolean password = helper.checkPassword(connector, request);
        if(input && user && password){
            String PersonID = userDao.getUser(connector, "PersonID", request.userName);
            AuthToken newAuthToken = new AuthToken(request.userName, UUID.randomUUID().toString());
            authTokenDao.addAuthToken(newAuthToken, connector);
            LoginResult success = new LoginResult();
            success.personId = PersonID;
            success.userName = request.userName;
            success.authToken = newAuthToken.getAuthToken();
            return success;
        }
        return new resultsBase("Bad input please try again");
    }

    public resultsBase fill(String userName, int generations, Connector connector) throws Exception {
        boolean inDatabase = userDao.readUser(userName, connector);
        if(inDatabase){
            User user = new User(userDao.getUser(connector, "PersonID", userName), userName, userDao.getUser(connector, "Password", userName),
                    userDao.getUser(connector, "Email", userName), userDao.getUser(connector, "FirstName", userName), userDao.getUser(connector,
                    "LastName", userName), userDao.getUser(connector, "Gender", userName));
            personDao.removePerson(userName, connector);
            eventDao.removeEvent(userName, connector);
            authTokenDao.deleteFilledTable(userName, connector);
            int year = 1993;
            registerService fill = new registerService();
            randomData random = new randomData();
            Person person = new Person(user.getUserId(), userName, user.getFirstName(),
                    user.getLastName(), user.getGender(), random.randMales(), random.randFemales(), random.randFemales());
            Locations.area temp = random.randLocation();
            Event event = new Event(connector.getUniqueIDString(), userName, user.getUserId(),
                    temp.latitude, temp.longitude, temp.country,
                    temp.city, random.events.get(0), year);
            temp = random.randLocation();
            Event eventTwo = new Event(connector.getUniqueIDString(), userName, user.getUserId(),
                    temp.latitude, temp.longitude, temp.country,
                    temp.city, random.events.get(1), year + 8);
            temp = random.randLocation();

            Event eventThree = new Event(connector.getUniqueIDString(), userName, user.getUserId(),
                    temp.latitude, temp.longitude, temp.country,
                    temp.city, random.events.get(2), year + 20);
            temp = random.randLocation();

            Event eventFour = new Event(connector.getUniqueIDString(), userName, user.getUserId(),
                    temp.latitude, temp.longitude, temp.country,
                    temp.city, random.events.get(3), year + 70);
            AuthToken authToken = new AuthToken(userName, UUID.randomUUID().toString());
            authTokenDao.addAuthToken(authToken, connector);
            personDao.addPerson(connector, person);
            System.out.println();
            eventDao.addEvent(event, connector);
            eventDao.addEvent(eventTwo, connector);
            eventDao.addEvent(eventThree, connector);
            eventDao.addEvent(eventFour, connector);
            ArrayList<Person> ancestors = new ArrayList<>();
            ArrayList<Event> history = new ArrayList<>();
            fill.buildGen(person, userName, generations, 1970, connector, ancestors, history);
            for(int i = 0; i < ancestors.size(); i++){
                personDao.addPerson(connector, ancestors.get(i));
            }
            for (int j = 0; j < history.size(); j++){
                eventDao.addEvent(history.get(j), connector);
            }
            return new resultsBase("Successfully added " + (ancestors.size() + 1 ) + " people and " + (history.size() + 4) + " events to the Database");
        }else {
            return new resultsBase("Bad input please try again");
        }
    }


    public resultsBase load(String jsonBody, Connector connector){
        connector.openConnection();
        connector.clear();
        connector.checkTables();
        connector.closeConnection(true);
        loadService.allInfo container;
        Gson gson = new Gson();
        singlePersonService findGender = new singlePersonService();
        container = gson.fromJson(jsonBody, loadService.allInfo.class);
        int userCount = 0;
        int eventCount = 0;
        int personCount = 0;
        for(int i = 0; i < container.users.size(); i++){
            User user = container.users.get(i);
            //System.out.println(findGender.checkGender(user.getGender()) + " " + !userDao.readUser(user.getUserName(), connector));
            if(findGender.checkGender(user.getGender()) && !userDao.readUser(user.getUserName(), connector)){
                userDao.addUser(user, connector);
                AuthToken token = new AuthToken(user.getUserName(), connector.getUniqueIDString());
                authTokenDao.addAuthToken(token, connector);
                userCount++;
            }
        }
        for(int i = 0; i < container.events.size(); i++){
            Event event = container.events.get(i);
            eventDao.addEvent(event, connector);
            eventCount++;
        }
        for(int i = 0; i < container.persons.size(); i++) {
            Person person = container.persons.get(i);
            //System.out.println(findGender.checkGender(person.getGender()));

            if (findGender.checkGender(person.getGender())) {
                personDao.addPerson(connector, person);
                personCount++;
            }
        }
        return new resultsBase("Successfully added " +
                userCount + " users, " +
                personCount + " persons, and " +
                eventCount + " events!");
    }

    public resultsBase person(String authToken, String personID, Connector connector){
        singlePersonService helper = new singlePersonService();
        boolean tokenCheck = authTokenDao.readTok(authToken, connector);
        String userName = authTokenDao.getAuthToken(connector,"UserName", "AuthToken", authToken);
        boolean checkId = helper.checkPersonID(personID, connector);
        String descendant = personDao.getPerson("Descendant", "PersonID", personID, connector);
        if(tokenCheck && checkId && userName.equals(descendant)){
            return helper.buildPerson(personID, connector);
        }
        return new resultsBase("Bad input: Token is " + tokenCheck + ", ID is " + checkId + ", and descendant match is " + userName.equals(descendant)  );
    }

    public resultsBase event (String authToken, String eventId, Connector connector){
        eventService helper = new eventService();
        boolean tokenCheck = authTokenDao.readTok(authToken, connector);
        String userName = authTokenDao.getAuthToken(connector, "UserName", "AuthToken", authToken);
        boolean eventCheck = helper.eventCheck(eventId, connector);
        String descendant = eventDao.getEvent("Descendant", "EventID", eventId, connector);
        if(tokenCheck && eventCheck && userName.equals(descendant)) {
            return helper.buildEvent(eventId, connector);
        }
        return new resultsBase("Bad input");
    }

    public ArrayList<singleEvent> allEvents(String authToken, Connector connector){
        eventsListService helper = new eventsListService();
        ArrayList<String> events;
        ArrayList<singleEvent> eventObjects = new ArrayList<>();
        eventService getDetails = new eventService();
        boolean inDatabase = authTokenDao.readTok(authToken, connector);
        if(inDatabase){
            events = helper.getEventIdList(authToken, connector);
            for(int i = 0; i < events.size(); i++){
                eventObjects.add(getDetails.buildEvent(events.get(i), connector));
            }
        }
        return eventObjects;
    }

    public ArrayList<personResults> allPeople (String authToken, Connector connector){
        personsListService helper = new personsListService();
        ArrayList<String> persons;
        singlePersonService getDetails = new singlePersonService();
        ArrayList<personResults> events = new ArrayList<>();
        boolean inDatabase = authTokenDao.readTok(authToken, connector);
        if(inDatabase){
            persons = helper.getPersonsIdLIst(authToken, connector);
            for(int i = 0; i < persons.size(); i++){
                events.add(getDetails.buildPerson(persons.get(i), connector));
            }
        }
        return events;
    }
}