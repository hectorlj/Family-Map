package fms.dao.UI.PersonPack.eventExtendableRecycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.List;

import fms.dao.R;
import fms.dao.UI.PersonPack.models.eventModel;
import fms.dao.UI.PersonPack.models.eventParent;

/**
 * Created by Hector Lopez on 11/22/2017.
 */

public class eventAdapter extends ExpandableRecyclerAdapter<eventParentViewHolder, eventChildViewHolder> {
    LayoutInflater layoutInflater;
    public eventAdapter(Context context, List<ParentObject> parentItemList){
        super(context, parentItemList);
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public eventParentViewHolder onCreateParentViewHolder(ViewGroup viewGroup) {
        View view = layoutInflater.inflate(R.layout.event_list_parent, viewGroup, false);
        return new eventParentViewHolder(view);
    }

    @Override
    public eventChildViewHolder onCreateChildViewHolder(ViewGroup viewGroup) {
        View view = layoutInflater.inflate(R.layout.event_child_layout, viewGroup, false);
        return new eventChildViewHolder(view);
    }

    @Override
    public void onBindParentViewHolder(eventParentViewHolder eventParentViewHolder, int i, Object o) {
        eventParent title = (eventParent)o;
        eventParentViewHolder._textView2.setText(title.title);
    }

    @Override
    public void onBindChildViewHolder(eventChildViewHolder eventChildViewHolder, int i, Object o) {
        eventModel title = (eventModel)o;

        eventChildViewHolder.eventImg.setImageResource(R.mipmap.ic_event);
        eventChildViewHolder.description.setText(title.description.toLowerCase());
        eventChildViewHolder.country.setText(title.country);
        eventChildViewHolder.city.setText(title.city + ", ");
        eventChildViewHolder.year.setText("("+title.year+")");
        eventChildViewHolder.name.setText(title.name);
        eventChildViewHolder.id = title.eventId;


    }


}
