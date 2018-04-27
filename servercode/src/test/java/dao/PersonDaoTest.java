package dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import handlers.Connector;
import models.Person;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Hector Lopez on 11/1/2017.
 */
public class PersonDaoTest {
    Connector connector = new Connector();
    PersonDao personDao = new PersonDao();
    Person person;
    @Before
    public void setUp()throws Exception{
        person = new Person("123", "hlj", "Hector", "Lopez", "M", "father", "mother", "wife");
        personDao.addPerson(connector, person);
    }
    @After
    public void tearDown() throws Exception {
        connector.openConnection();
        connector.clear();
        connector.closeConnection(true);
    }

    @Test
    public void removePerson() throws Exception {
        personDao.removePerson(person.getDescendant(), connector);
        boolean inDatabase = personDao.readPerson(person.getPersonId(), connector);
        assertFalse(inDatabase);
    }

    @Test
    public void readPerson() throws Exception {
        boolean inDatabase = personDao.readPerson(person.getPersonId(), connector);
        assertTrue(inDatabase);
    }

    @Test
    public void getPerson() throws Exception {
        String personID = personDao.getPerson("PersonID","Descendant", person.getDescendant(), connector);
        assertEquals("123", personID);
    }

    @Test
    public void readDescendants() throws Exception {
        ArrayList<String> personList = personDao.readDescendants( person.getDescendant(), connector);
        assertFalse(personList.isEmpty());
    }

}