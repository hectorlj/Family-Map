package fms.dao.UI.FilterPack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.List;

import fms.dao.R;

/**
 * Created by Hector Lopez on 12/12/2017.
 */

public class filterAdapter extends ExpandableRecyclerAdapter<filterParentViewHolder, filterChildViewHolder>{
    LayoutInflater layoutInflater;
    public filterAdapter(Context context, List<ParentObject> filterItemList){
        super(context, filterItemList);
        layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public filterParentViewHolder onCreateParentViewHolder(ViewGroup viewGroup){
        View view = layoutInflater.inflate(R.layout.filter_list_parent, viewGroup, false);
        return new filterParentViewHolder(view);
    }
    @Override
    public filterChildViewHolder onCreateChildViewHolder(ViewGroup viewGroup){
        View view = layoutInflater.inflate(R.layout.filter_child_layout, viewGroup, false);
        return new filterChildViewHolder(view);
    }
    @Override
    public void onBindParentViewHolder(filterParentViewHolder filterParentViewHolder, int i, Object o){
        filterParent title = (filterParent)o;
        filterParentViewHolder._textView.setText(title.title);
    }
    @Override
    public void onBindChildViewHolder(filterChildViewHolder filterChildViewHolder, int i, Object o){
        filterModel title = (filterModel)o;
        filterChildViewHolder.type.setText(title.event);
        filterChildViewHolder.desc.setText(title.desc);
    }
}
