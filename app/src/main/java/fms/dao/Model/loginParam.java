package fms.dao.Model;

/**
 * Created by Hector Lopez on 11/11/2017.
 */

public class loginParam {
    private String userName;
    private String password;
    private String host;
    private String port;

    public loginParam(String userName, String password, String host, String port){
        this.password = password;
        this.userName = userName;
        this.host = host;
        this.port = port;
    }

    public String getUserName(){return userName;}
    public String getPassword(){return password;}
    public String getHost(){return host;}
    public String getPort(){return port;}
}
