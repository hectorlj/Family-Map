package handlers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.UUID;

import datagen.Locations;
import datagen.randomData;
import shared.Model.*;


/**
 * Created by Hector Lopez on 10/26/2017.
 */

public class Connector {
    private Connection connection;
    private final static String DROP_AUTH = "DROP TABLE IF EXISTS AUTHTOKENS";
    private final static String DROP_USER = "DROP TABLE IF EXISTS USERS";
    private final static String DROP_EVENT = "DROP TABLE IF EXISTS EVENTS";
    private final static String DROP_PERSONS = "DROP TABLE IF EXISTS PERSONS";

    private final static String CREATE_AUTH = "CREATE TABLE IF NOT EXISTS AUTHTOKENS" +
            "(" +
            "AuthToken VARCHAR NOT NULL UNIQUE CHECK (AuthToken <> '')," +
            "UserName VARCHAR NOT NULL CHECK (UserName <> ''), " +
            "FOREIGN KEY ('UserName') REFERENCES 'User'('UserName')," +
            "PRIMARY KEY ('AuthToken')" +
            ")";
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

    private final static String CREATE_EVENT = "CREATE TABLE IF NOT EXISTS EVENTS " +
            "( " +
            "EventID VARCHAR NOT NULL UNIQUE CHECK (EventID <> ''), " +
            "Descendant VARCHAR NOT NULL CHECK (Descendant <> '') ," +
            "PersonID VARCHAR NOT NULL CHECK (PersonID <> '') ,"+
            "Latitude DOUBLE NOT NULL , " +
            "Longitude DOUBLE NOT NULL  , " +
            "Country VARCHAR NOT NULL CHECK (Country <> '') , " +
            "City VARCHAR NOT NULL CHECK (City <> '') , " +
            "EventType VARCHAR NOT NULL CHECK (EventType <> '') , " +
            "Year INTEGER NOT NULL ," +
            "FOREIGN KEY ('PersonID') REFERENCES 'Persons'('PersonID') ," +
            "PRIMARY KEY ('EventID') "+
            ")";

    public Connector(){
        openConnection();
        checkTables();
        closeConnection(true);
    }
    public Connection getConnection(){
        return connection;
    }
    static{
        try{
            Class.forName("org.sqlite.JDBC");
        } catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    private void CreateUserTable(){
        Statement stmt = null;
        try{
            stmt = connection.createStatement();
            stmt.execute(CREATE_USER);
        } catch (SQLException e) {
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
    }
    private void CreateEventTable(){
        Statement stmt = null;
        try{
            stmt = connection.createStatement();
            stmt.execute(CREATE_EVENT);
        } catch (SQLException e){
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
    }
    private void CreatePersonsTable(){
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            stmt.execute(CREATE_PERSONS);
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
    }
    private void CreateAuthTable(){
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            stmt.execute(CREATE_AUTH);
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
    }
    public void checkTables(){
        CreatePersonsTable();
        CreateAuthTable();
        CreateUserTable();
        CreateEventTable();
    }
    public void openConnection(){
        try{
            connection = DriverManager.getConnection("jdbc:sqlite:myDatabase.db");
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void closeConnection(boolean commit){
        try{
            if(commit){
                connection.commit();
            } else {
                connection.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            if(connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public String getUniqueIDString(){
        return UUID.randomUUID().toString();
    }
    public Event buildEvent(String personID, String userName, int year, String EventType){
        randomData Generator = new randomData();
        Locations locations = Generator.getLocations();
        Event event = new Event();
        Random random = new Random();
        int locationBounds = random.nextInt(locations.data.size() - 1);
        event.setEventId(UUID.randomUUID().toString());
        event.setPersonId(personID);
        event.setLongitude(locations.data.get(locationBounds).getLongitude());
        event.setLatitude(locations.data.get(locationBounds).getLatitude());
        event.setDescendant(userName);
        event.setCity(locations.data.get(locationBounds).getCity());
        event.setCountry(locations.data.get(locationBounds).getCountry());
        event.setEventType(EventType);
        event.setYear(year + random.nextInt(5));

        return event;
    }
    public AuthToken buildToken(String userName, String AuthToken){
        return  new AuthToken(userName, AuthToken);
    }

    public void clear(){
        Statement stmt = null;
        try{
            stmt = connection.createStatement();
            stmt.execute(DROP_USER);
            stmt.execute(DROP_AUTH);
            stmt.execute(DROP_EVENT);
            stmt.execute(DROP_PERSONS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            if(stmt != null){
                try{
                    stmt.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
