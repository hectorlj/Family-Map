package fms.dao.UI.FilterPack;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hector Lopez on 12/13/2017.
 */

public class filterTitleCreator {
    static filterTitleCreator _titleCreator;
    List<filterParent> _titleParents;
    public filterTitleCreator(Context context){
        _titleParents = new ArrayList<>();
        filterParent filter = new filterParent(String.format("More filters"));
        _titleParents.add(filter);
    }
    public static filterTitleCreator get(Context context){
        if(_titleCreator == null){
            _titleCreator = new filterTitleCreator(context);
            return _titleCreator;
        }
        return _titleCreator;
    }
    public List<filterParent> getAll(){return _titleParents;}
}
