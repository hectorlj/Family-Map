package fms.dao.UI.SearchPack;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import fms.dao.Model.globalHelper;
import fms.dao.R;
import fms.dao.UI.MapPack.MapActivity;
import fms.dao.UI.PersonPack.PersonActivity;
import results.personResults;
import results.singleEvent;

/**
 * Created by Hector Lopez on 11/25/2017.
 */

public class searchViewHolder extends RecyclerView.ViewHolder {
    public TextView mainTxt;
    public TextView secondTxt;
    public ImageView image;
    public LinearLayout row;
    public Context context;
    public String type;
    public personResults person;
    public singleEvent event;

    public searchViewHolder(View itemView, final Context context){
        super(itemView);
        mainTxt = (TextView)itemView.findViewById(R.id.search_row_head_text);
        secondTxt = (TextView)itemView.findViewById(R.id.search_row_body_text);
        image = (ImageView)itemView.findViewById(R.id.search_row_image);
        row = (LinearLayout) itemView.findViewById(R.id.search_row_view);
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type.equals("male") || type.equals("female")){
                    globalHelper.getInstance().setMarkerEventSelectedUser(person.getPersonID());
                    Intent intent = new Intent(v.getContext(), PersonActivity.class);
                    v.getContext().startActivity(intent);
                } else {
                    globalHelper.getInstance().setMapActivity(true);
                    globalHelper.getInstance().setMarkerEventSelectedEvent(event.event.getEventId());
                    Intent intent = new Intent(v.getContext(), MapActivity.class);
                    v.getContext().startActivity(intent);
                }
            }
        });
    }

    public void bind(searchModel search){
        this.type = search.type;
        mainTxt.setText(search.mainTxt);
        secondTxt.setText(search.secondTxt);
        if(type.equals("location")){
            image.setImageResource(R.mipmap.ic_event);
            this.event = search.event;
        } else if(type.equals("male")){
            image.setImageResource(R.drawable.male);
            this.person = search.person;
        } else {
            image.setImageResource(R.drawable.female);
            this.person = search.person;
        }
    }
}
