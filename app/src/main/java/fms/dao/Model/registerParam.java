package fms.dao.Model;

/**
 * Created by Hector Lopez on 11/11/2017.
 */

public class registerParam {
    public registerParam(String username, String password,
                         String email, String firstname,
                         String lastname, String gender,
                         String host, String port){
        setFirstName(firstname);
        setLastName(lastname);
        setUserName(username);
        setEmail(email);
        setGender(gender);
        setPassword(password);
        setHost(host);
        setPort(port);

    }

    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String gender;
    private String host;
    private String port;
    private String email;
    public void setPort(String port){
        this.port = port;
    }
    public void setHost(String host){
        this.host = host;
    }
    public void setUserName(String userName){
        this.userName = userName;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setFirstName(String firstName){this.firstName = firstName;}
    public void setLastName(String lastName){this.lastName = lastName;}
    public void setGender(String gender){this.gender = gender;}
    public void setEmail(String email){this.email = email;}

    public String getPassword(){return password;}
    public String getFirstName(){return firstName;}
    public String getLastName(){return lastName;}
    public String getGender(){return gender;}
    public String getUserName(){return userName;}
    public String getPort(){return port;}
    public String getHost(){return host;}
    public String getEmail(){return email;}
}
