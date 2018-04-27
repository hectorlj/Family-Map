package services;

import org.junit.Test;

import requests.registerRequest;

import static org.junit.Assert.assertEquals;

/**
 * Created by Hector Lopez on 10/31/2017.
 */
public class registerServiceTest {
    @Test
    public void inputValidation() throws Exception {
        registerRequest register = new registerRequest();
        register.userName = "hlj";
        register.password = "password";
        register.password = "hlj239@byu.net";
        register.firstName = "hector";
        register.lastName = "lopez";
        register.gender = "M";
        registerService test = new registerService();
        boolean pass = test.inputValidation(register);
        assertEquals(true, pass);
    }

}