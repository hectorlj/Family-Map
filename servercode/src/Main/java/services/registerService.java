package services;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import dao.EventDao;
import dao.PersonDao;
import dao.UserDao;
import datagen.Locations;
import datagen.randomData;
import handlers.Connector;
import requests.registerRequest;
import results.LoginResult;
import shared.Model.*;

/**
 * Created by Hector Lopez on 10/27/2017.
 */

class registerService {
    private final randomData peoplePlaces;
    private static PersonDao personDao;
    private static EventDao eventDao;

    public registerService(){
        peoplePlaces = new randomData();
        personDao = new PersonDao();
        eventDao = new EventDao();
    }

    public boolean inDatabase(registerRequest request, Connector connector){
        UserDao userDao = new UserDao();
        return userDao.readUser(request.userName, connector);
    }

    public boolean inputValidation( registerRequest request){
        String gender = request.gender.toLowerCase();
        return (!gender.equals("f") || !gender.equals("m"));
    }

    private String getRandId(){
        return UUID.randomUUID().toString();
    }

    public Person buildPerson(registerRequest request, String linkedId){
        Person person = new Person();
        person.setFirstName(request.firstName);
        person.setLastName(request.lastName);
        person.setGender(request.gender);
        person.setDescendant(request.userName);
        person.setPersonId(linkedId);
        person.setFather(getRandId());
        person.setMother(getRandId());
        return person;
    }
    public User buildUser(registerRequest request){
        User user = new User(UUID.randomUUID().toString(), request.userName, request.password,
                request.email, request.firstName, request.lastName, request.gender);
        return user;
    }
    public Event buildUserEvent(User user, String EventType,  Connector connector){
        return connector.buildEvent(user.getUserId(), user.getUserName(), 1993, EventType);
    }

    public AuthToken buildAuthTok(String userName, Connector connector){
        return connector.buildToken(userName, getRandId());
    }
    public void buildGen(Person child, String descendant, int generation, int year, Connector connector, ArrayList<Person> ancestors, ArrayList<Event> history) throws Exception{
        if (generation != 0){
        Random random = new Random();
        year = year - 20 + random.nextInt(5);
        Person father = new Person(child.getFather(), descendant, peoplePlaces.randMales(),
                child.getLastName(), "m", getRandId(), getRandId(), child.getMother());
        Person mother = new Person(child.getMother(), descendant, peoplePlaces.randFemales(),
                peoplePlaces.randFemales(), "f", getRandId(), getRandId(), child.getFather());
        Locations.area temp = peoplePlaces.randLocation();
        Event fatherEvent = new Event(UUID.randomUUID().toString(), descendant, father.getPersonId(),
                temp.latitude, temp.longitude, temp.country, temp.city, peoplePlaces.events.get(0), year );
        temp = peoplePlaces.randLocation();
        Event motherEvent = new Event(UUID.randomUUID().toString(), descendant, mother.getPersonId(),
                temp.latitude, temp.longitude, temp.country, temp.city, peoplePlaces.events.get(0), year);
        temp = peoplePlaces.randLocation();
        Event fatherEventtwo = new Event(UUID.randomUUID().toString(), descendant, father.getPersonId(),
                temp.latitude, temp.longitude, temp.country, temp.city, peoplePlaces.events.get(1), year + 8);
        temp = peoplePlaces.randLocation();
        Event motherEventtwo = new Event(UUID.randomUUID().toString(), descendant, mother.getPersonId(),
                temp.latitude, temp.longitude, temp.country, temp.city, peoplePlaces.events.get(1), year + 8);
        temp = peoplePlaces.randLocation();
        Event fatherEventthree = new Event(UUID.randomUUID().toString(), descendant, father.getPersonId(),
                temp.latitude, temp.longitude, temp.country, temp.city, peoplePlaces.events.get(2), year + 20 );
        temp = peoplePlaces.randLocation();
        Event motherEventthree = new Event(UUID.randomUUID().toString(), descendant, mother.getPersonId(),
                temp.latitude, temp.longitude, temp.country, temp.city, peoplePlaces.events.get(2), year + 20);
        temp = peoplePlaces.randLocation();
        Event fatherEventfour = new Event(UUID.randomUUID().toString(), descendant, father.getPersonId(),
                temp.latitude, temp.longitude, temp.country, temp.city, peoplePlaces.events.get(3), year + 80);
        temp = peoplePlaces.randLocation();
        Event motherEventfour = new Event(UUID.randomUUID().toString(), descendant, mother.getPersonId(),
                temp.latitude, temp.longitude, temp.country, temp.city, peoplePlaces.events.get(3), year + 80);
//        System.out.println(generation + " father " + father.getFirstName() + " mother " + mother.getFirstName() + "\nfather event " + fatherEvent.getEventType() + " mother event " + motherEvent.getEventType());
        ancestors.add(father);
        ancestors.add(mother);
        history.add(fatherEvent);
        history.add(motherEvent);
        history.add(fatherEventtwo);
        history.add(motherEventtwo);
        history.add(fatherEventthree);
        history.add(motherEventthree);
        history.add(fatherEventfour);
        history.add(motherEventfour);
        buildGen(father, descendant, generation - 1, year - 30, connector, ancestors, history);
        buildGen(mother, descendant, generation - 1, year - 30, connector, ancestors, history);
    }
    }

    public LoginResult registerSuccess(String authId, String userName, String personId){
        LoginResult result = new LoginResult();
        result.authToken = authId;
        result.userName = userName;
        result.personId = personId;
        return result;
    }
}
