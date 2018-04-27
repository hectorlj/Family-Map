package fms.dao.UI.PersonPack.eventExtendableRecycler;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import fms.dao.UI.PersonPack.models.eventParent;

/**
 * Created by Hector Lopez on 11/22/2017.
 */

public class eventTitleCreator {
    static eventTitleCreator _eventTitleCreator;
    List<eventParent> _eventParents;
    public eventTitleCreator(Context context){
        _eventParents = new ArrayList<>();
        eventParent events = new eventParent(String .format("LIFE EVENTS"));
        _eventParents.add(events);
    }
    public static eventTitleCreator get(Context context){
        if(_eventTitleCreator == null){
            _eventTitleCreator = new eventTitleCreator(context);
            return _eventTitleCreator;
        }
        return _eventTitleCreator;
    }
    public List<eventParent> getAll(){
        return _eventParents;
    }
}
