package services;

import java.util.ArrayList;

import dao.AuthTokenDao;
import dao.PersonDao;
import handlers.Connector;

/**
 * Created by Hector Lopez on 10/29/2017.
 */

class personsListService {
    private final PersonDao personDao;
    private final AuthTokenDao authTokenDao;
    public personsListService(){
        personDao = new PersonDao();
        authTokenDao = new AuthTokenDao();
    }
    public ArrayList<String> getPersonsIdLIst(String authToken, Connector connector){
        String userName = authTokenDao.getAuthToken(connector, "UserName", "AuthToken", authToken);
        return personDao.readDescendants(userName, connector);
    }
}
