package results;

import shared.Model.Event;

/**
 * Created by Hector Lopez on 10/28/2017.
 */

public class singleEvent extends resultsBase {
    public Event event;
    public String personGender;
    public String familySide;
    public singleEvent(){event = new Event();}
    public singleEvent(Event e){
        event = e;
    }
    public String customFill(){
        StringBuilder fill = new StringBuilder();
        fill.append(event.getEventType()).append(": ").append(event.getCity())
                .append(", ").append(event.getCountry()).append(" (").append(event.getYear()).append(")");
        return fill.toString();
    }
}
