package services;

import java.util.ArrayList;

import dao.AuthTokenDao;
import dao.EventDao;
import handlers.Connector;

/**
 * Created by Hector Lopez on 10/29/2017.
 */

class eventsListService {
    public eventsListService(){
        eventDao = new EventDao();
        authTokenDao = new AuthTokenDao();
    }
    private final EventDao eventDao;
    private final AuthTokenDao authTokenDao;
    public boolean checkAuthToken(String authToken, Connector connector){
        return authTokenDao.readTok(authToken, connector);
    }
    public ArrayList<String> getEventIdList(String authToken, Connector connector){
        String userName = authTokenDao.getAuthToken(connector, "UserName", "AuthToken", authToken);
        return eventDao.getMultiEvent(userName, connector);
    }
}
