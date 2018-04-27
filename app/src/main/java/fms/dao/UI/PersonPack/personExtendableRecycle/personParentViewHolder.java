package fms.dao.UI.PersonPack.personExtendableRecycle;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;

import fms.dao.R;

/**
 * Created by Hector Lopez on 11/22/2017.
 */

public class personParentViewHolder extends ParentViewHolder{
    public TextView _textView;
    public personParentViewHolder(View itemView){
        super(itemView);
        _textView = (TextView)itemView.findViewById(R.id.person_parent_title);
    }
}
