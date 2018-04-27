package fms.dao.UI.PersonPack.models;

/**
 * Created by Hector Lopez on 11/22/2017.
 */

public class personModel {
    public String ID;
    public String fullName;
    public String gender;
    public String relation;
    public personModel(String fullName, String gender, String relation, String ID){
        this.fullName = fullName;
        this.gender = gender;
        this.ID = ID;
        this.relation = relation;
    }
    public interface sendID{
        public String receiveID(String Id);
    }
}
