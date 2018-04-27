package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import handlers.Connector;
import shared.Model.User;

/**
 * Created by Hector Lopez on 10/20/2017.
 */

public class UserDao {
    private final static String CREATE_USER = "CREATE TABLE IF NOT EXISTS USERS " +
            "(" +
            "UserName VARCHAR NOT NULL UNIQUE CHECK (UserName <>''), " +
            "Password VARCHAR NOT NULL CHECK (Password<>''), " +
            "Email VARCHAR NOT NULL CHECK(Email<>''), " +
            "FirstName VARCHAR NOT NULL CHECK (FirstName <>''), " +
            "LastName VARCHAR NOT NULL CHECK (LastName <>''), " +
            "Gender VARCHAR NOT NULL CHECK (Gender <>''), " +
            "PersonID VARCHAR NOT NULL CHECK (PersonID <>''), " +
            "FOREIGN KEY ('PersonID') REFERENCES 'Persons'('PersonID'), " +
            "PRIMARY KEY ('UserName')" +
            ")";
    public void addUser(User newUser, Connector connector){
        connector.openConnection();
        boolean valid = false;
        PreparedStatement stmt;
        String sql = "INSERT INTO USERS (UserName, Password, Email, FirstName, LastName, "+
                "Gender, PersonID) VALUES (?,?,?,?,?,?,?)";
        try{
            valid = true;
            stmt = connector.getConnection().prepareStatement(sql);
            stmt.setString(1, newUser.getUserName());
            stmt.setString(2, newUser.getPassword());
            stmt.setString(3, newUser.getEmail());
            stmt.setString(4, newUser.getFirstName());
            stmt.setString(5, newUser.getLastName());
            stmt.setString(6, newUser.getGender());
            stmt.setString(7, newUser.getUserId());
            stmt.execute();
        }catch(SQLException e){
            e.printStackTrace();
        }
        connector.closeConnection(valid);
    }
    private void CreateUsersTable(Connector connector) {
        connector.openConnection();
        boolean valid = false;
        Statement stmt;
        try{
            valid = true;
            stmt = connector.getConnection().createStatement();
            stmt.execute(CREATE_USER);
            stmt.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        connector.closeConnection(valid);
    }
    public void removeUser(String userName, Connector connector){
        CreateUsersTable(connector);
        connector.openConnection();
        boolean valid = false;
        PreparedStatement stmt;
        String sql = "DELETE FROM USERS WHERE UserName = '" + userName + "'";
        try{
            valid = true;
            stmt = connector.getConnection().prepareStatement(sql);
            stmt.execute();
        } catch(SQLException e){
            e.printStackTrace();
        }
        connector.closeConnection(valid);
    }
    public boolean readUser(String userName, Connector connector){
        CreateUsersTable(connector);
        connector.openConnection();
        boolean valid = false;
        PreparedStatement stmt;
        ResultSet rs;
        try{
            valid = true;
            String sql = "SELECT * FROM USERS WHERE UserName = '" + userName + "'";
            stmt = connector.getConnection().prepareStatement(sql);
            rs = stmt.executeQuery();
            if(rs.next()){
                connector.closeConnection(valid);
                return true;
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        connector.closeConnection(valid);
        return false;
    }
        public String getUser(Connector connector, String selection, String data) throws SQLException {
        connector.openConnection();
        boolean valid = false;
        Statement stmt = null;
        ResultSet rs = null;
        String results = "";
        try{
            valid = true;
            String sql = "SELECT " + selection + " FROM USERS WHERE " + "UserName" + " = '"+ data +"'";
            stmt = connector.getConnection().createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next()) {
                results = rs.getString(1);
            }
            stmt.close();
            rs.close();
            connector.closeConnection(valid);
            return results;
        }catch(SQLException e){
            e.printStackTrace();
        }
        finally {
            if(stmt != null)
                stmt.close();
            if(rs!= null)
                rs.close();
        }
        connector.closeConnection(valid);
        return "Error";
    }
}