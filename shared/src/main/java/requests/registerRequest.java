package requests;

/**
 * Created by Hector Lopez on 10/28/2017.
 */

public class registerRequest {
    public registerRequest(){}
    public String userName;
    public String password;
    public String email;
    public String firstName;
    public String lastName;
    public String gender;

    public void setUserName(String userName){this.userName = userName;}
    public void setPassword(String password){this.password = password;}
    public void setEmail(String email){this.email = email;}
    public void setFirstName(String firstName){this.firstName = firstName;}
    public void setLastName(String lastName){this.lastName = lastName;}
    public void setGender(String gender){this.gender = gender;}
}
