package models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by Hector Lopez on 11/3/2017.
 */
public class UserTest {
    User user;
    @Before
    public void setUp()throws Exception{
        user = new User("userId", "name", "password", "email", "me", "clan", "f");
    }
    @Test
    public void getUserId() throws Exception {
        assertTrue(user.getUserId() == "userId");
    }

    @Test
    public void setPersonId() throws Exception {
        user.setPersonId("thisId");
        assertTrue(user.getUserId() == "thisId");
    }

    @Test
    public void testString() throws Exception {
        assertTrue(user.toString() != "");
    }

}