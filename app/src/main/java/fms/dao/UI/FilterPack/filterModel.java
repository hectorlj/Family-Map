package fms.dao.UI.FilterPack;

/**
 * Created by Hector Lopez on 12/12/2017.
 */

public class filterModel {
    public String event;
    public String desc;
    public boolean isChecked;
    public filterModel(String event, boolean isChecked){
        this.event = event;
        this.desc = "FILTER BY " + event.toUpperCase() + " EVENTS";
        this.isChecked = isChecked;
    }
    public interface sendEvent{
        public String receiveEvent(String event);
    }
}
