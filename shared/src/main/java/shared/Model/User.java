package shared.Model;

/**
 * Created by Hector Lopez on 10/19/2017.
 */

public class User {
    private String personID = "";
    private String userName = "";
    private String password = "";
    private String email = "";
    private String firstName = "";
    private String lastName = "";
    private String gender = "";
    public User(){}
    public User(String inputPersonId, String inputUserName, String inputpassword,
                String inputEmail, String inputFirstName, String inputLastName,
                String inputGender){
        setPersonId(inputPersonId);
        setUserName(inputUserName);
        setPassword(inputpassword);
        setEmail(inputEmail);
        setFirstName(inputFirstName);
        setLastName(inputLastName);
        setGender(inputGender);
    }

    public String getUserId(){return personID;}
    public String getUserName(){return userName;}
    public String getPassword(){return password;}
    public String getEmail(){return email;}
    public String getFirstName(){return firstName;}
    public String getLastName(){return lastName;}
    public String getGender(){return gender;}

    public void setPersonId(String newPersonId){
        this.personID = newPersonId;
    }

    public void setUserName(String newUserName){
        this.userName = newUserName;
    }

    public void setPassword(String newPassword){
        this.password = newPassword;
    }

    public void setEmail(String newEmail){
        this.email = newEmail;
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

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("User:\n[\nPersonID: ").append(personID).append("\nUser Name: ").append(userName).append("\npassword: ").append(password).append("\nemail: ").
                append(email).append("\nFirst Name: ").append(firstName).append("\nLast Name: ").append(lastName).append("\ngender: ").
                append(gender).append("\n]\n\n");
        return sb.toString();
    }
}
