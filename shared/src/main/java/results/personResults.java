package results;

import shared.Model.Person;
/**
 * Created by Hector Lopez on 10/28/2017.
 */

public class personResults extends resultsBase {
    public Person person;
    public personResults(){person = new Person();}
    public personResults(Person p){
        person = p;
    }
    public String getName(){
        return person.getFirstName() + " " + person.getLastName();
    }
    public String getPersonID(){
        return person.getPersonId();
    }
}
