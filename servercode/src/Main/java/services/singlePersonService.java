package services;

import dao.AuthTokenDao;
import dao.PersonDao;
import handlers.Connector;
import shared.Model.*;
import results.personResults;

/**
 * Created by Hector Lopez on 10/28/2017.
 */

class singlePersonService {
    private final PersonDao personDao = new PersonDao();
    private final AuthTokenDao authTokenDao = new AuthTokenDao();
    public boolean checkAuthToken(String auth, String personID, Connector connector){
        String userName = personDao.getPerson("Descendant", "PersonID", personID, connector);
        String currentAuth = authTokenDao.getAuthToken(connector,"AuthToken", "UserName", userName);
        return currentAuth.equals(auth);
    }
    public boolean checkPersonID(String personID, Connector connector){
        return personDao.readPerson(personID, connector);
    }
    public personResults buildPerson(String personID, Connector connector){
        Person person = new Person(personID,
                personDao.getPerson("Descendant", "PersonID", personID, connector),
                personDao.getPerson("FirstName", "PersonID", personID, connector),
                personDao.getPerson("LastName", "PersonID", personID, connector),
                personDao.getPerson("Gender", "PersonID", personID, connector),
                personDao.getPerson("Father", "PersonID", personID, connector),
                personDao.getPerson("Mother", "PersonID", personID, connector),
                personDao.getPerson("Spouse", "PersonID", personID, connector));
        return new personResults(person);
    }
    public boolean checkGender(String gender){
        gender = gender.toLowerCase();
        return (gender.equals("f") || gender.equals("m"));
    }

}
