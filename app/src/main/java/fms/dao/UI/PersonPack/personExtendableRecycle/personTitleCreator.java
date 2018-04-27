package fms.dao.UI.PersonPack.personExtendableRecycle;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import fms.dao.UI.PersonPack.models.personParent;

/**
 * Created by Hector Lopez on 11/22/2017.
 */

public class personTitleCreator {
    static personTitleCreator _titleCreator;
    List<personParent> _titleParents;
    public personTitleCreator(Context context){
        _titleParents = new ArrayList<>();
        personParent family = new personParent(String.format("FAMILY"));
        _titleParents.add(family);
    }
    public static personTitleCreator get(Context context){
        if(_titleCreator == null){
            _titleCreator = new personTitleCreator(context);
            return _titleCreator;
        }
        return _titleCreator;
    }
    public List<personParent> getAll(){
        return _titleParents;
    }
}
