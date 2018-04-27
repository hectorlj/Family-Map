package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import handlers.Connector;
import shared.Model.Event;

/**
 * Created by Hector Lopez on 10/20/2017.
 */

public class EventDao {
    private final static String CREATE_EVENT = "CREATE TABLE IF NOT EXISTS EVENTS " +
            "(" +
            "EventID VARCHAR NOT NULL UNIQUE CHECK(EventID<>''), " +
            "Descendant VARCHAR NOT NULL CHECK(Descendant<>''), " +
            "Latitude DOUBLE NOT NULL, " +
            "Longitude DOUBLE NOT NULL, " +
            "Country VARCHAR NOT NULL CHECK (Country<>''), " +
            "City VARCHAR NOT NULL CHECK (City<>''), " +
            "EventType VARCHAR NOT NULL CHECK(EventType<>''), " +
            "Year INTEGER NOT NULL, " +
            "FOREIGN KEY('PersonID') REFERENCES 'Persons'('PersonID'), " +
            "PRIMARY KEY('EventID') " +
            ")";
    public void addEvent(Event event, Connector connector){
        CreateEventTable(connector);
        connector.openConnection();
        boolean valid = false;
        PreparedStatement stmt;
        String sql = "INSERT INTO EVENTS (EventID, Descendant, PersonID, Latitude, Longitude, " +
                "Country, City, EventType, Year) VALUES (?,?,?,?,?,?,?,?,?)";
        try{
            valid = true;
            stmt = connector.getConnection().prepareStatement(sql);
            stmt.setString(1, event.getEventId());
            stmt.setString(2, event.getDescendant());
            stmt.setString(3, event.getPersonId());
            stmt.setString(4, String.valueOf(event.getLatitude()));
            stmt.setString(5, String.valueOf(event.getLongitude()));
            stmt.setString(6, event.getCountry());
            stmt.setString(7, event.getCity());
            stmt.setString(8, event.getEventType());
            stmt.setString(9, String.valueOf(event.getYear()));
            stmt.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
        connector.closeConnection(valid);
    }

    private void CreateEventTable(Connector connector) {
        Statement stmt;
        connector.openConnection();
        boolean valid = false;
        try{
            valid = true;
            stmt = connector.getConnection().createStatement();
            stmt.execute(CREATE_EVENT);
            stmt.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        connector.closeConnection(valid);
    }

    public void removeEvent(String UserName, Connector connector){
        CreateEventTable(connector);
        connector.openConnection();
        boolean valid = false;
        PreparedStatement stmt;
        String sql = "DELETE FROM EVENTS WHERE Descendant = '" + UserName + "'";
        try{
            valid = true;
            stmt = connector.getConnection().prepareStatement(sql);
            stmt.execute();
        }catch(SQLException e){
            e.printStackTrace();
        }
        connector.closeConnection(valid);
    }

    public String getEvent(String selection, String column, String data, Connector connector){
        CreateEventTable(connector);
        connector.openConnection();
        boolean valid = false;
        PreparedStatement stmt;
        ResultSet rs;
        String result = "Error";
        String sql = "SELECT "+ selection +" FROM EVENTS WHERE " + column + " = '" + data + "'";
        try{
            valid = true;
            stmt = connector.getConnection().prepareStatement(sql);
            rs = stmt.executeQuery();
            while(rs.next()) {
                result = rs.getString(1);
            }
            stmt.close();
            rs.close();
            connector.closeConnection(valid);
            return result;
        }catch (SQLException e){
            e.printStackTrace();
        }
        connector.closeConnection(valid);
        return "Error";
    }
    public ArrayList<String> getMultiEvent(String descendant, Connector connector){
        CreateEventTable(connector);
        connector.openConnection();
        boolean valid = false;
        PreparedStatement stmt;
        ResultSet rs;
        ArrayList<String> result = new ArrayList<>();
        String sql = "SELECT EventID FROM EVENTS WHERE Descendant = '" + descendant + "'";
        try{
            valid = true;
            stmt = connector.getConnection().prepareStatement(sql);
            rs = stmt.executeQuery();
            while(rs.next()){result.add(rs.getString(1));}
        }catch(SQLException e){
            e.printStackTrace();
        }
        connector.closeConnection(valid);
        return result;
    }
    public boolean readEvent(String EventId, Connector connector){
        CreateEventTable(connector);
        boolean valid = false;
        connector.openConnection();
        PreparedStatement stmt;
        ResultSet rs;
        String sql = "SELECT * FROM EVENTS WHERE EventId = '" + EventId + "'";
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
    public double getLatLong(String selection, String eventId, Connector connector){
        CreateEventTable(connector);
        connector.openConnection();
        boolean valid = false;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        double item = 0;
        String sql = "SELECT " + selection +
                " FROM EVENTS WHERE EventID = '" + eventId + "'";
        try{
            valid = true;
            stmt = connector.getConnection().prepareStatement(sql);
            rs = stmt.executeQuery();
            while(rs.next()) {
                item = rs.getDouble(selection);
            }
            connector.closeConnection(valid);
            return item;
        }catch (SQLException e){
            e.printStackTrace();
        }
        connector.closeConnection(valid);
        return 0;
    }
}
