package shared.Model;

/**
 * Created by Hector Lopez on 10/19/2017.
 */

public class AuthToken {
    private String userName ="";
    private String authToken = "";
    public AuthToken(){}
    public AuthToken(String inputUserName, String inputAuthToken){
        setAuthToken(inputAuthToken);
        setUserName(inputUserName);
    }
    public String getAuthToken(){return authToken;}
    public String getUserName(){return userName;}
    public void setAuthToken (String newToken){
        this.authToken = newToken;
    }
    public void setUserName (String newUserName){
        this.userName = newUserName;
    }
    public String toString(){
        return "[\n" +
                "AuthToken: " + authToken +
                "\nUserName: " + userName +
                "\n]";
    }
}
