package fms.dao.Network;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import requests.dualInputRequest;
import requests.registerRequest;
import requests.singleInputRequest;
import results.LoginResult;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Hector Lopez on 12/14/2017.
 */
public class serverProxyTest {
    private serverProxy test;
    @Before
    public void setUp() throws Exception {
        test = new serverProxy("10.0.2.2", "8080");
    }

    @After
    public void tearDown() throws Exception {
        test.clearProxy();
    }

    /*also tests the loginProxy*/
    @Test
    public void registerProxy() throws Exception {
        registerRequest request = new registerRequest();
        request.setEmail("email@mail.com");
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setGender("m");
        request.setUserName("username");
        request.setPassword("password");
        LoginResult registerResult = test.registerProxy(request);
        System.out.println(test.registerProxy(request).authToken);
        test.setAuthToken(registerResult.authToken);
        dualInputRequest login = new dualInputRequest();
        login.password = "password";
        login.userName = "username";
        LoginResult end = test.loginProxy(login);
        assertEquals("username", end.userName);

    }

    @Test
    public void clearProxy() throws Exception {
        test.clearProxy();
    }

    @Test
    public void loadProxy() throws Exception {
        singleInputRequest request = new singleInputRequest();
        String data = "{\n" +
                "  \"users\": [\n" +
                "    {\n" +
                "      \"userName\": \"sheila\",\n" +
                "      \"password\": \"parker\",\n" +
                "      \"email\": \"sheila@parker.com\",\n" +
                "      \"firstName\": \"Sheila\",\n" +
                "      \"lastName\": \"Parker\",\n" +
                "      \"gender\": \"f\",\n" +
                "      \"personID\": \"Sheila_Parker\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"persons\": [\n" +
                "    {\n" +
                "      \"firstName\": \"Sheila\",\n" +
                "      \"lastName\": \"Parker\",\n" +
                "      \"gender\": \"f\",\n" +
                "      \"personID\": \"Sheila_Parker\",\n" +
                "      \"father\": \"Patrick_Spencer\",\n" +
                "      \"mother\": \"Im_really_good_at_names\",\n" +
                "      \"descendant\": \"sheila\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"firstName\": \"Patrick\",\n" +
                "      \"lastName\": \"Spencer\",\n" +
                "      \"gender\": \"m\",\n" +
                "      \"personID\":\"Patrick_Spencer\",\n" +
                "      \"spouse\": \"Im_really_good_at_names\",\n" +
                "      \"descendant\": \"sheila\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"firstName\": \"CS240\",\n" +
                "      \"lastName\": \"JavaRocks\",\n" +
                "      \"gender\": \"f\",\n" +
                "      \"personID\": \"Im_really_good_at_names\",\n" +
                "      \"spouse\": \"Patrick_Spencer\",\n" +
                "      \"descendant\": \"sheila\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"events\": [\n" +
                "    {\n" +
                "      \"eventType\": \"started family map\",\n" +
                "      \"personID\": \"Sheila_Parker\",\n" +
                "      \"city\": \"Salt Lake City\",\n" +
                "      \"country\": \"United States\",\n" +
                "      \"latitude\": 40.7500,\n" +
                "      \"longitude\": -110.1167,\n" +
                "      \"year\": 2016,\n" +
                "      \"eventID\": \"e5244e18-8fc6-4996-a457-aaa60c7ef066\",\n" +
                "      \"descendant\":\"sheila\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"eventType\": \"fixed this thing\",\n" +
                "      \"personID\": \"Patrick_Spencer\",\n" +
                "      \"city\": \"Provo\",\n" +
                "      \"country\": \"United States\",\n" +
                "      \"latitude\": 40.2338,\n" +
                "      \"longitude\": -111.6585,\n" +
                "      \"year\": 2017,\n" +
                "      \"eventID\": \"I_hate_formatting\",\n" +
                "      \"descendant\": \"sheila\"\n" +
                "    }\n" +
                "  ]\n" +
                "}\n";
        request.setData(data);
        assertTrue(!test.loadProxy(request.getData()).equals(null));
    }

    @Test
    public void peopleProxy() throws Exception {
        registerRequest request = new registerRequest();
        request.setGender("m");
        request.setPassword("password");
        request.setUserName("username");
        request.setFirstName("Gandalf");
        request.setLastName("The Grey");
        LoginResult results = test.registerProxy(request);
        test.setAuthToken(results.authToken);
        assertTrue(test.peopleProxy().length > 0);
    }

    @Test
    public void eventsProxy() throws Exception {
        registerRequest request = new registerRequest();
        request.setGender("m");
        request.setPassword("password");
        request.setUserName("username");
        request.setFirstName("Gandalf");
        request.setLastName("The Grey");
        LoginResult result = test.registerProxy(request);
        test.setAuthToken(result.authToken);
        assertTrue(test.eventsProxy().length > 0);
    }

}