package models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by Hector Lopez on 11/3/2017.
 */
public class PersonTest {
    Person person;
    @Before
    public void setUp() throws Exception {
        person = new Person("id", "des", "first", "last", "m", "father", "mother", "spouse");
    }

    @Test
    public void getPersonId() throws Exception {
        assertTrue(person.getPersonId() == "id");
    }

    @Test
    public void setPersonId() throws Exception {
        person.setPersonId("person");
        assertTrue(person.getPersonId() == "person");
    }

    @Test
    public void testString() throws Exception {
        assertTrue(person.toString() != "");
    }
}