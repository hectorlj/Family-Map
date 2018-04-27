package fms.dao.Model;

/**
 * Created by Hector Lopez on 11/10/2017.
 */

public class loginRegisterModel {
    public loginRegisterModel(){}
    private String userName;
    private String password;
    private String port;
    private String host;
    private String firstName;
    private String lastName;
    private String email;
    private String gender;

    public String getUserName(){return userName;}
    public String getPassword(){return password;}
    public String getPort (){return port;}
    public String getHost(){return host;}
    public String getFirstName(){return firstName;}
    public String getLastName(){return lastName;}
    public String getEmail(){return email;}
    public String getGender(){return gender;}
    public void setUserName(String userName){
        this.userName = userName;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setPort(String port){
        this.port = port;
    }
    public void setHost(String host){
        this.host = host;
    }
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setGender(String gender){
        this.gender = gender;
    }
}
