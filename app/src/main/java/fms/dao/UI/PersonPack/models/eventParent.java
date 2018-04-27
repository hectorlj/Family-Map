package fms.dao.UI.PersonPack.models;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.List;
import java.util.UUID;

/**
 * Created by Hector Lopez on 11/22/2017.
 */

public class eventParent implements ParentObject {
    public List<Object> childrenList;
    public UUID _ID;
    public String title;
    public eventParent(String title){
        this.title = title;
        _ID = UUID.randomUUID();
    }
    @Override
    public List<Object> getChildObjectList() {
        return childrenList;
    }

    @Override
    public void setChildObjectList(List<Object> list) {
        childrenList = list;
    }
}
