package shared.Model;

/**
 * Created by Hector Lopez on 10/19/2017.
 */

public class Person {
    private String personID = "";
    private String descendant = "";
    private String firstName = "";
    private String lastName = "";
    private String gender = "";
    private String father = "";
    private String mother = "";
    private String spouse = "";
    public Person(){}
    public Person(String inputPersonId, String inputDescendant,
                  String inputFirstName, String inputLastName, String inputGender,
                  String inputFather, String inputMother, String inputSpouse){
        setPersonId(inputPersonId);
        setDescendant(inputDescendant);
        setDescendant(inputDescendant);
        setFirstName(inputFirstName);
        setLastName(inputLastName);
        setGender(inputGender);
        setFather(inputFather);
        setMother(inputMother);
        setSpouse(inputSpouse);

    }
    public String getPersonId(){return personID;}
    public String getDescendant(){return descendant;}
    public String getFirstName(){return firstName;}
    public String getLastName(){return lastName;}
    public String getGender(){return gender;}
    public String getFather(){return father;}
    public String getMother(){return mother;}
    public String getSpouse(){return spouse;}

    public void setPersonId(String newPersonId){
        this.personID = newPersonId;
    }
    public void setDescendant(String newDescendant){
        this.descendant = newDescendant;
    }
    public void setFirstName(String newFirstName){
        this.firstName = newFirstName;
    }
    public void setLastName(String newLastName){
        this.lastName = newLastName;
    }
    public void setGender(String newGender){
        this.gender = newGender;
    }
    public void setFather(String newFather){
        this.father = newFather;
    }
    public void setMother(String newMother){
        this.mother = newMother;
    }
    public void setSpouse(String newSpouse){
        this.spouse = newSpouse;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Person:\n[\nPersonID: ").append(personID).append("\nDescendant: ").append(descendant).append("\nFirst Name: ").append(firstName).append("\nLast Name: ").
                append(lastName).append("\nGender: ").append(gender).append("\nFather: ").append(father).append("\nMother: ").
                append(mother).append("\nSpouse: ").append(spouse).append("\n]\n\n");
        return sb.toString();
    }
}
