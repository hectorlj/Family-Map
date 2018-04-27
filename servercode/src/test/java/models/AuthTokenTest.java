package models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Hector Lopez on 11/3/2017.
 */
public class AuthTokenTest {
    AuthToken authToken;
    @Before
    public void setUp() throws Exception {
        authToken = new AuthToken("me", "token");
    }

    @Test
    public void getAuthToken() throws Exception {
        assertEquals(authToken.getAuthToken(), "token");
    }

    @Test
    public void getUserName() throws Exception {
        assertEquals(authToken.getUserName(), "me");
    }

    @Test
    public void setAuthToken() throws Exception {
        authToken.setAuthToken("no token");
        assertEquals(authToken.getAuthToken(), "no token");
    }

    @Test
    public void setUserName() throws Exception {
        authToken.setUserName("not me");
        assertEquals(authToken.getUserName(), "not me");
    }

    @Test
    public void testString() throws Exception {
        String authTokenString = authToken.toString();
        assertNotEquals("", authTokenString);
    }

}