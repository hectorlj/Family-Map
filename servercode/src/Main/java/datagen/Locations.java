package datagen;

import java.util.ArrayList;

/**
 * Created by Hector Lopez on 10/27/2017.
 */

public class Locations {
    public ArrayList<area> data;
    public String toString(){
        String temp = "";
        for(int x = 0 ; x < data.size(); x++){
            temp += data.get(x).toString() + "\n";
        }
        return temp;
    }
    public class area{
            public Double latitude;
            public Double longitude;
            public String city;
            public String country;
            public Double getLatitude(){return latitude;}
            public Double getLongitude(){return longitude;}
            public String getCity(){return city;}
            public String getCountry(){return country;}
            public String toString(){
                return latitude + " " + longitude + " " +
                        city + " " + country;
            }
    }
}
