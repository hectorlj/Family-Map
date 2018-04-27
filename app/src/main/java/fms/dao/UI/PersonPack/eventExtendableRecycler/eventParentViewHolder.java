package fms.dao.UI.PersonPack.eventExtendableRecycler;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;

import fms.dao.R;

/**
 * Created by Hector Lopez on 11/22/2017.
 */

public class eventParentViewHolder extends ParentViewHolder {
    public TextView _textView2;
    public eventParentViewHolder(View itemView){
        super(itemView);
        _textView2 = (TextView)itemView.findViewById(R.id.event_parent_title);
    }
}
