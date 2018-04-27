package fms.dao.UI.PersonPack.eventExtendableRecycler;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;

import fms.dao.Model.globalHelper;
import fms.dao.R;
import fms.dao.UI.MapPack.MapActivity;
import fms.dao.UI.PersonPack.models.eventModel;

/**
 * Created by Hector Lopez on 11/22/2017.
 */

public class eventChildViewHolder extends ChildViewHolder implements eventModel.sendEventId {
    public TextView description;
    public TextView city;
    public TextView country;
    public TextView year;
    public TextView name;
    public ImageView eventImg;
    public String id;
    public eventChildViewHolder(View itemView){
        super(itemView);
        eventImg = (ImageView)itemView.findViewById(R.id.event_img_child);
        description = (TextView)itemView.findViewById(R.id.child_description);
        city = (TextView) itemView.findViewById(R.id.child_city);
        country = (TextView) itemView.findViewById(R.id.child_country);
        year = (TextView) itemView.findViewById(R.id.child_year);
        name = (TextView) itemView.findViewById(R.id.child_event_name);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalHelper.getInstance().setMapActivity(true);
                Toast.makeText(v.getContext(), "Loading map", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(v.getContext(), MapActivity.class);
                globalHelper.getInstance().setMarkerEventSelectedEvent(id);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public String receiveEventId(String ID){
        return ID;
    }
}
