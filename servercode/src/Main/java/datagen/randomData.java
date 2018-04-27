package datagen;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Hector Lopez on 10/27/2017.
 */

public class randomData {
    public ArrayList<String> events;
    private Males maleNames;
    private Females femaleNames;
    private Surnames lastNames;
    private Locations locations;

    public randomData() {
        setEvents();
        try {
            Gson gson = new Gson();
            String folder = "C:\\Users\\peque\\AndroidStudioProjects\\FamilyLIfeServer\\servercode\\src\\Main\\Assets";
            maleNames = gson.fromJson(new FileReader(folder + "\\mnames.json"), Males.class);
            femaleNames = gson.fromJson(new FileReader(folder + "\\fnames.json"), Females.class);
            lastNames = gson.fromJson(new FileReader(folder + "\\mnames.json"), Surnames.class);
            locations = gson.fromJson(new FileReader(folder + "\\locations.json"), Locations.class);
//            System.out.println(locations.toString());
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }
    private void setEvents(){
        events = new ArrayList<>();
        events.add("Death");
        events.add("Birth");
        events.add("Marriage");
        events.add("Baptism");
    }
    public Locations getLocations() {
        return locations;
    }

    public String randMales() {
        Random random = new Random();
        return maleNames.data.get(random.nextInt(maleNames.data.size()));
    }

    public Locations.area randLocation(){
        Random random = new Random();
        return locations.data.get(random.nextInt(locations.data.size()));
    }

    public String randFemales() {
        Random random = new Random();
        return femaleNames.data.get(random.nextInt(femaleNames.data.size()));
    }

    public String randSur() {
        Random random = new Random();
        return lastNames.data.get(random.nextInt(lastNames.data.size()));
    }
}
