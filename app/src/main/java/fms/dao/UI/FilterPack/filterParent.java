package fms.dao.UI.FilterPack;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.List;
import java.util.UUID;

/**
 * Created by Hector Lopez on 12/13/2017.
 */

public class filterParent implements ParentObject {
    public List<Object> childrenList;
    public UUID _ID;
    public String title;
    public filterParent(String title){
        this.title = title;
        _ID = UUID.randomUUID();
    }
    @Override
    public List<Object> getChildObjectList(){return childrenList;}
    @Override
    public void setChildObjectList(List<Object> list){childrenList = list;}
}
