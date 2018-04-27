package dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import handlers.Connector;
import models.User;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Hector Lopez on 11/1/2017.
 */
public class UserDaoTest {
    User user;
    Connector connector = new Connector();
    UserDao userDao = new UserDao();
    @Before
    public void setUp() throws Exception {
        user = new User("hlj239", "hlj", "password", "hlj239@byu.net", "Hector", "Lopez", "M");
        userDao.addUser(user, connector);
    }

    @After
    public void tearDown() throws Exception {
        connector.openConnection();
        connector.clear();
        connector.closeConnection(true);
    }

    @Test
    public void addUser() throws Exception {
        User test = new User("0" , "1", "2", "3", "4", "5", "6");
        userDao.addUser(test, connector);
        boolean inDatabase = userDao.readUser(test.getUserName(), connector);
        assertTrue(inDatabase);
    }

    @Test
    public void removeUser() throws Exception {
        userDao.removeUser(user.getUserName(), connector);
        boolean inDatabase = userDao.readUser(user.getUserName(), connector);
        assertFalse(inDatabase);
    }

    @Test
    public void readUser() throws Exception {
        boolean inDatabase = userDao.readUser("hlj", connector);
        assertTrue(inDatabase);
    }

    @Test
    public void getUser() throws Exception {
        String email = userDao.getUser(connector, "Email", user.getUserName());
        assertEquals("hlj239@byu.net", email);
    }

}