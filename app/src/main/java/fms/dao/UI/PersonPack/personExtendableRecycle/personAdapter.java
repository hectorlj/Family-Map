package fms.dao.UI.PersonPack.personExtendableRecycle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.List;

import fms.dao.R;
import fms.dao.UI.PersonPack.models.personModel;
import fms.dao.UI.PersonPack.models.personParent;

/**
 * Created by Hector Lopez on 11/22/2017.
 */

public class personAdapter extends ExpandableRecyclerAdapter<personParentViewHolder, personChildViewHolder>{
    LayoutInflater layoutInflater;
    public personAdapter(Context context, List<ParentObject> parentItemList){
        super(context, parentItemList);
        layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public personParentViewHolder onCreateParentViewHolder(ViewGroup viewGroup) {
        View view = layoutInflater.inflate(R.layout.person_list_parent, viewGroup, false);
        return new personParentViewHolder(view);
    }

    @Override
    public personChildViewHolder onCreateChildViewHolder(ViewGroup viewGroup) {
        View view = layoutInflater.inflate(R.layout.person_child_layout, viewGroup, false);
        return new personChildViewHolder(view);
    }

    @Override
    public void onBindParentViewHolder(personParentViewHolder personParentViewHolder, int i, Object o) {
        personParent title = (personParent)o;
        personParentViewHolder._textView.setText(title.title);
    }

    @Override
    public void onBindChildViewHolder(personChildViewHolder personChildViewHolder, int i, Object o) {
        personModel title = (personModel)o;
        personChildViewHolder.name.setText(title.fullName);
        personChildViewHolder.relationship.setText(title.relation);
        if(title.gender.toLowerCase().equals("m")){
            personChildViewHolder.gender.setImageResource(R.drawable.male);
        } else {
            personChildViewHolder.gender.setImageResource(R.drawable.female);
        }
        personChildViewHolder.personId = title.ID;
    }
}
