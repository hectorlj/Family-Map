package services;

import com.google.gson.Gson;

import org.junit.Test;

import java.io.FileReader;

import shared.Model.*;


/**
 * Created by Hector Lopez on 10/29/2017.
 */
public class loadServiceTest {
    @Test
    public void gsonConverter() throws Exception {
        loadService.allInfo me;
        Gson gson = new Gson();
        me = gson.fromJson(new FileReader("C:\\Users\\peque\\AndroidStudioProjects\\" +
                "FamilyLIfeServer\\servercode\\src\\Main\\Assets\\example.json"), loadService.allInfo.class);
        for(int i = 0; i < me.events.size(); i++){
            Event event = me.events.get(i);
        }
        for(int i = 0; i < me.persons.size(); i++){
            Person person = me.persons.get(i);
        }
        for(int i = 0; i < me.users.size(); i++){
            User user = me.users.get(i);
        }
    }

}