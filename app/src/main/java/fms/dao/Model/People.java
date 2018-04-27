package fms.dao.Model;

import results.personResults;
/**
 * Created by Hector Lopez on 11/4/2017.
 */

public class People {
    public personResults person;
    public People(personResults person){
        this.person = person;
    }
    public static personResults getPersonById(String PersonId){
        for(personResults person:globalHelper.getInstance().getAllPeopleList()){
            if(person.getPersonID().equals(PersonId))
                return person;
        }
        return null;
    }
}
