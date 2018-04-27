package services;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dao.UserDao;
import handlers.Connector;
import shared.Model.*;
import requests.dualInputRequest;

import static org.junit.Assert.assertEquals;
/**
 * Created by Hector Lopez on 10/29/2017.
 */
public class loginServiceTest {
    Connector connector = new Connector();
    loginService login = new loginService();
    @Before
    public void setUp() throws Exception {
        connector.openConnection();
        connector.clear();
        connector.checkTables();
        connector.closeConnection(true);
    }

    @After
    public void tearDown() throws Exception {
        connector.openConnection();
        connector.clear();
        connector.closeConnection(true);
    }
    @Test
    public void checkPassword() throws Exception {
        loginService login =  new loginService();
        dualInputRequest me = new dualInputRequest();
        me.userName = "hlj";
        me.password = "hector";
        UserDao userDao = new UserDao();
        User newUser = new User("a", "hlj", "hector", "e", "i", "o", "u");
        userDao.addUser(newUser, connector);
        boolean u = login.checkPassword(connector, me);
        assertEquals(true, u);
    }

    @Test
    public void checkInput() throws Exception {
        dualInputRequest loginRequest = new dualInputRequest();
        loginRequest.userName = "1";
        loginRequest.password = "1";
        loginService test = new loginService();
        boolean check = test.checkInput(loginRequest);
        assertEquals(true, check);
    }

    @Test
    public void checkDatabase() throws Exception {
        User me = new User("1234", "hlj", "hector", "hlj239@byu.net", "Hector", "Lopez", "M");
        UserDao temp = new UserDao();
        temp.addUser(me, connector);
        dualInputRequest request = new dualInputRequest();
        request.userName = "hlj";
        request.password = "hector";
        boolean done = login.checkDatabase(connector, request);
        assertEquals(true, done);
    }
}