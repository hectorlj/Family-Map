package dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import handlers.Connector;
import models.AuthToken;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Hector Lopez on 10/31/2017.
 */
public class AuthTokenDaoTest {
    AuthTokenDao authTokenDao = new AuthTokenDao();
    Connector connector = new Connector();
    @Before
    public void setUp() throws Exception {
        connector.openConnection();
        connector.checkTables();
        connector.clear();
        connector.checkTables();
        connector.closeConnection(true);
    }

    @After
    public void tearDown() throws Exception {
        connector.openConnection();
        connector.checkTables();
        connector.clear();
        connector.closeConnection(true);
    }

    @Test
    public void addAuthToken() throws Exception {
        AuthToken authToken = new AuthToken("Hector", "123");
        authTokenDao.addAuthToken(authToken, connector);
        String done = authTokenDao.getAuthToken(connector, "UserName", "AuthToken", authToken.getAuthToken());
        assertEquals("Hector", done);
    }

    @Test
    public void removeAuthToken() throws Exception {
        String authtoken = "123";
        //System.out.println(authTokenDao.getAuthToken(connector, "UserName", "AuthToken", authtoken));
        authTokenDao.removeAuthToken(authtoken, connector);
        String done = authTokenDao.getAuthToken(connector, "UserName", "AuthToken", authtoken);
        assertEquals("", done);
    }

    @Test
    public void updateAuthToken() throws Exception {
        AuthToken authToken = new AuthToken("Me", "012");
        authTokenDao.addAuthToken(authToken, connector);
        authTokenDao.updateAuthToken(authToken.getUserName(), "!@", connector);
        String inDatabase = authTokenDao.getAuthToken(connector, "AuthToken", "UserName", authToken.getUserName());
        authTokenDao.removeAuthToken(inDatabase, connector);
        assertNotEquals("012", inDatabase);
    }

    @Test
    public void readUserName() throws Exception {
        AuthToken authToken = new AuthToken("Hector", "1234");
        authTokenDao.addAuthToken(authToken, connector);
        boolean isUser = authTokenDao.readUserName("Hector", connector);
        authTokenDao.removeAuthToken(authToken.getAuthToken(), connector);
        assertTrue(isUser);
    }

    @Test
    public void readTok() throws Exception {
        AuthToken authToken = new AuthToken("Hector", "1234");
        authTokenDao.addAuthToken(authToken, connector);
        boolean isTok = authTokenDao.readTok("1234", connector);
        authTokenDao.removeAuthToken(authToken.getAuthToken(), connector);
        //System.out.println(isTok);
        assertTrue(isTok);
    }

    @Test
    public void getAuthToken() throws Exception {
        AuthToken authToken = new AuthToken("Hector", "1234");
        authTokenDao.addAuthToken(authToken, connector);
        String isUser = authTokenDao.getAuthToken(connector, "AuthToken", "UserName", authToken.getUserName());
        authTokenDao.removeAuthToken(authToken.getAuthToken(), connector);
        assertEquals(authToken.getAuthToken(), isUser);
    }

    @Test
    public void deleteFilledTable() throws Exception {
            AuthToken authToken = new AuthToken("Hector", "hector");
            authTokenDao.addAuthToken(authToken, connector);
            authTokenDao.deleteFilledTable(authToken.getUserName(), connector);
            boolean inDatabase = authTokenDao.readUserName(authToken.getUserName(), connector);
            assertTrue(!inDatabase);
    }

}