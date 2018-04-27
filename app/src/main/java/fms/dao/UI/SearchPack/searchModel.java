package fms.dao.UI.SearchPack;

import fms.dao.Model.People;
import results.personResults;
import results.singleEvent;

/**
 * Created by Hector Lopez on 11/25/2017.
 */

public class searchModel {
    public String mainTxt;
    public String secondTxt;
    public String type;
    public personResults person;
    public singleEvent event;
    public searchModel(){
        this.mainTxt = mainTxt;
        this.secondTxt = secondTxt;
    }
    public searchModel(personResults person){
        this.person = person;
        mainTxt = person.getName();
        secondTxt = "";
        if(person.person.getGender().toLowerCase().equals("m")){
            type = "male";
        } else {
            type = "female";
        }
    }
    public searchModel(singleEvent event){
        mainTxt = event.customFill();
        secondTxt = People.getPersonById(event.event.getPersonId()).getName();
        type = "location";
        this.event = event;
    }
}
