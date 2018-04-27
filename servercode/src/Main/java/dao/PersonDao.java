package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import handlers.Connector;
import shared.Model.Person;
/**
 * Created by Hector Lopez on 10/20/2017.
 */

public class PersonDao {
    private final static String CREATE_PERSONS = "CREATE TABLE IF NOT EXISTS PERSONS " +
            "(" +
            "PersonID VARCHAR NOT NULL UNIQUE CHECK(PersonID<>'')," +
            "Descendant VARCHAR NOT NULL CHECK(Descendant<>'')," +
            "FirstName VARCHAR NOT NULL CHECK (FirstName<>'')," +
            "LastName VARCHAR NOT NULL CHECK (LastName <>'')," +
            "Gender VARCHAR NOT NULL CHECK (Gender <> '')," +
            "Father VARCHAR, " +
            "Mother VARCHAR," +
            "Spouse VARCHAR," +
            "PRIMARY KEY ('PersonId')," +
            "FOREIGN KEY ('Descendant') REFERENCES 'User' ('UserName')" +
            ")";
    public void addPerson(Connector connector, Person newPerson){
        connector.openConnection();
        boolean valid = false;
        String sql = "INSERT INTO PERSONS (PersonID, Descendant, FirstName, LastName, " +
                "Gender, Father, Mother, Spouse) VALUES (?,?,?,?,?,?,?,?)";
        PreparedStatement stmt = null;
        try{
            valid = true;
            stmt = connector.getConnection().prepareStatement(sql);
            stmt.setString(1, newPerson.getPersonId());
            stmt.setString(2, newPerson.getDescendant());
            stmt.setString(3, newPerson.getFirstName());
            stmt.setString(4, newPerson.getLastName());
            stmt.setString(5, newPerson.getGender());
            stmt.setString(6, newPerson.getFather());
            stmt.setString(7, newPerson.getMother());
            stmt.setString(8, newPerson.getSpouse());
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connector.closeConnection(valid);
    }
    private void CreatePersonsTable(Connector connector) {
        connector.openConnection();
        boolean valid = false;
        Statement stmt;
        try{
            valid = true;
            stmt = connector.getConnection().createStatement();
            stmt.execute(CREATE_PERSONS);
            stmt.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        connector.closeConnection(valid);
    }

    public void removePerson(String UserName, Connector connector){
        CreatePersonsTable(connector);
        connector.openConnection();
        boolean valid = false;
        PreparedStatement stmt;
        String sql = "DELETE FROM PERSONS WHERE Descendant = '" + UserName + "'";
        try{
            valid = true;
            stmt = connector.getConnection().prepareStatement(sql);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connector.closeConnection(valid);
    }

    public boolean readPerson(String personId, Connector connector){
        CreatePersonsTable(connector);
        connector.openConnection();
        boolean valid = false;
        PreparedStatement stmt;
        ResultSet rs;
        String sql = "SELECT * FROM PERSONS WHERE PersonID = '" + personId + "'";
        try{
            valid = true;
            stmt = connector.getConnection().prepareStatement(sql);
            rs = stmt.executeQuery();
            if(rs.next()){
                connector.closeConnection(valid);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connector.closeConnection(valid);
        return false;
    }
    public String getPerson(String selection, String column, String data, Connector connector){
        connector.openConnection();
        boolean valid = false;
        String sql = "SELECT " + selection + " FROM PERSONS WHERE " + column + " = '" + data + "'";
        String item = "";
        PreparedStatement stmt = null;
        ResultSet rs = null ;
        try{
            valid = true;
            stmt = connector.getConnection().prepareStatement(sql);
            rs = stmt.executeQuery();
            while(rs.next()) {
                item = rs.getString(selection);
            }
            connector.closeConnection(valid);
            return item;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(stmt != null){
                try{
                    stmt.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(rs != null){
                try{
                    rs.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
        connector.closeConnection(valid);
        return "Error";
    }
    public ArrayList<String> readDescendants(String Descendant, Connector connector){
        connector.openConnection();
        boolean valid = false;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<String> result = new ArrayList<>();
        String sql = "SELECT * FROM PERSONS WHERE Descendant = '" + Descendant + "'";
        try{
            valid = true;
            stmt = connector.getConnection().prepareStatement(sql);
            rs = stmt.executeQuery();
            while(rs.next()){
                result.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            if(stmt != null){
                try{
                    stmt.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(rs != null){
                try{
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        connector.closeConnection(valid);
        return result;
    }
}
