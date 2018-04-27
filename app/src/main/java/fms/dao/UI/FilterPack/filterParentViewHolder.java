package fms.dao.UI.FilterPack;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;

import fms.dao.R;

/**
 * Created by Hector Lopez on 12/12/2017.
 */

public class filterParentViewHolder extends ParentViewHolder {
    public TextView _textView;
    public filterParentViewHolder(View itemView){
        super(itemView);
        _textView = (TextView) itemView.findViewById(R.id.settings_parent_title);
    }
}
