package requests;

/**
 * Created by Hector Lopez on 10/28/2017.
 */

public class singleInputRequest {
    private String data = null;
    public String getData(){return data;}
    public void setData(String data){this.data = data;}
    public singleInputRequest(){}
    public singleInputRequest(String input){
        data = input;
    }
}
