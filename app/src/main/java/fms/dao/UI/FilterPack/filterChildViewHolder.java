package fms.dao.UI.FilterPack;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;

import fms.dao.Model.globalHelper;
import fms.dao.R;

/**
 * Created by Hector Lopez on 12/12/2017.
 */

public class filterChildViewHolder extends ChildViewHolder implements filterModel.sendEvent{
    public TextView type;
    public TextView desc;
    public Switch aSwitch;
    public filterChildViewHolder(View itemView){
        super(itemView);
        type = (TextView)itemView.findViewById(R.id.event_type);
        desc = (TextView)itemView.findViewById(R.id.event_desc);
        aSwitch = (Switch)itemView.findViewById(R.id.event_switch);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                globalHelper.getInstance().changeEventTypeMap(type.toString().toLowerCase(), isChecked);
            }
        });
    }
    @Override
    public String receiveEvent(String event){return event;}
}
