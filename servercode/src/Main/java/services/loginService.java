package services;

import java.sql.SQLException;

import dao.UserDao;
import handlers.Connector;
import requests.dualInputRequest;

/**
 * Created by Hector Lopez on 10/28/2017.
 */

class loginService {
    public boolean checkInput(dualInputRequest loginRequest){
        String userName = loginRequest.userName;
        String password = loginRequest.password;
        if (userName.equals("") || userName == null){return false;}
        return !(password.equals("") || password == null);
    }
    public boolean checkDatabase(Connector connector, dualInputRequest loginRequest){
        return  new UserDao().readUser(loginRequest.userName, connector);
    }
    public boolean checkPassword(Connector connector, dualInputRequest loginRequest) throws SQLException {
        String databasePassword = new UserDao().getUser(connector, "Password", loginRequest.userName);
        return databasePassword.equals(loginRequest.password);
    }
}
