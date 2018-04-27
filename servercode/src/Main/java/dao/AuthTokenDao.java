package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import handlers.Connector;
import shared.Model.*;

/**
 * Created by Hector Lopez on 10/20/2017.
 */

public class AuthTokenDao {
    private final static String CREATE_AUTH = "CREATE TABLE IF NOT EXISTS AUTHTOKENS " +
            "(" +
            "AuthToken VARCHAR NOT NULL UNIQUE CHECK(AuthToken <>'')," +
            "UserName VARCHAR NOT NULL CHECK(UserName<>'')," +
            "FOREIGN KEY ('UserName') REFERENCES 'User'('UserName')," +
            "PRIMARY KEY ('AuthToken')" +
            ")";


    public void addAuthToken(AuthToken newAuthToken, Connector connector){
        connector.openConnection();
        PreparedStatement stmt = null;
        boolean valid = false;
        String sql = "INSERT INTO AUTHTOKENS(AuthToken, UserName) VALUES(?,?)";
        try{
            valid = true;
            stmt = connector.getConnection().prepareStatement(sql);
            stmt.setString(1, newAuthToken.getAuthToken());
            stmt.setString(2, newAuthToken.getUserName());
            stmt.execute();
        } catch(SQLException e){
            e.printStackTrace();
        }finally{
            if(stmt != null) {
                try {
                    stmt.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        connector.closeConnection(valid);
    }
    public void removeAuthToken(String authToken, Connector connector){
        connector.openConnection();
        boolean valid = false;
        PreparedStatement stmt = null;
        String sql = "DELETE FROM AUTHTOKENS WHERE AuthToken = '" + authToken + "'";
        try{
            valid = true;
            stmt = connector.getConnection().prepareStatement(sql);
            stmt.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }finally{
            if(stmt != null) {
                try {
                    stmt.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        connector.closeConnection(valid);
    }
    public void updateAuthToken(String userName, String authToken, Connector connector){
        CreateAuthTable(connector);
        connector.openConnection();
        boolean valid = false;
        String sql = "UPDATE AUTHTOKENS SET AuthToken ='" + authToken + "' " +
                "WHERE UserName = '" + userName + "'";
        PreparedStatement stmt = null;
        try{
            valid = true;
            stmt = connector.getConnection().prepareStatement(sql);
            stmt.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }finally{
            if(stmt != null) {
                try {
                    stmt.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        connector.closeConnection(valid);
    }
    public boolean readUserName(String userId, Connector connector){
        connector.openConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM AUTHTOKENS WHERE UserName = '" + userId + "'";
        boolean valid = false;
        try{
            valid = true;
            stmt = connector.getConnection().prepareStatement(sql);
            rs = stmt.executeQuery();
            if(rs.next()){
                connector.closeConnection(valid);
                return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally{
            if(stmt != null) {
                try {
                    stmt.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(rs != null){
                try{
                    rs.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        connector.closeConnection(valid);
        return false;
    }
    public boolean readTok(String authToken, Connector connector){
        connector.openConnection();
        boolean valid = false;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM AUTHTOKENS WHERE AuthToken = '" + authToken + "'";
        try{
            valid = true;
            stmt = connector.getConnection().prepareStatement(sql);
            rs = stmt.executeQuery();
            if(rs.next()){
                connector.closeConnection(valid);
                return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally{
            if(stmt != null) {
                try {
                    stmt.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(rs != null){
                try{
                    rs.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        connector.closeConnection(valid);
        return false;
    }
    public String getAuthToken(Connector connector, String identification, String column, String data){
        CreateAuthTable(connector);
        connector.openConnection();
        boolean valid = false;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String item = "";
        String sql = "SELECT "+ identification +" FROM AUTHTOKENS WHERE " + column + " = '" + data + "'";
        try{
            valid = true;
            stmt = connector.getConnection().prepareStatement(sql);
            rs = stmt.executeQuery();
            while(rs.next()){item = rs.getString(1);}
            connector.closeConnection(valid);
            return item;
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            if(stmt != null) {
                try {
                    stmt.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        connector.closeConnection(valid);
        return "Error";
    }

    private void CreateAuthTable(Connector connector){
        Statement stat;
        connector.openConnection();
        boolean valid = false;
        try{
            valid = true;
            stat = connector.getConnection().createStatement();
            stat.execute(CREATE_AUTH);
        } catch (SQLException e){
            e.printStackTrace();
        }
        connector.closeConnection(valid);
    }

    public void deleteFilledTable(String UserName, Connector connector){
        CreateAuthTable(connector);
        boolean valid = false;
        connector.openConnection();
        PreparedStatement stmt;
        String sql = "DELETE FROM AUTHTOKENS WHERE UserName = '" + UserName + "'";
        try{
            valid = true;
            stmt = connector.getConnection().prepareStatement(sql);
            stmt.execute();
        } catch (SQLException e){
            e.printStackTrace();
        }
        connector.closeConnection(valid);
    }
}
