package services;

import com.google.gson.Gson;

import java.util.ArrayList;

import shared.Model.*;

/**
 * Created by Hector Lopez on 10/28/2017.
 */

public class loadService {
    private final Gson gson;
    public loadService() {
        gson = new Gson();
    }
    public class allInfo {
        public allInfo(){
            users = new ArrayList<>();
            persons = new ArrayList<>();
            events = new ArrayList<>();
        }
        public ArrayList<User> users;
        public ArrayList<Person> persons;
        public ArrayList<Event> events;
    }
}
