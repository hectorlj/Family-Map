package fms.dao.UI.PersonPack.personExtendableRecycle;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;

import fms.dao.Model.globalHelper;
import fms.dao.R;
import fms.dao.UI.PersonPack.PersonActivity;
import fms.dao.UI.PersonPack.models.personModel;

/**
 * Created by Hector Lopez on 11/22/2017.
 */

public class personChildViewHolder extends ChildViewHolder implements personModel.sendID {
    public TextView name, relationship;
    public ImageView gender;
    public String personId;
    public personChildViewHolder(View itemView) {
        super(itemView);
        name = (TextView)itemView.findViewById(R.id.first_name_child);
        relationship = (TextView)itemView.findViewById(R.id.relationship_child);
        gender = (ImageView) itemView.findViewById(R.id.image_child);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Loading new person", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), PersonActivity.class);
                globalHelper.getInstance().setMarkerEventSelectedUser(personId);
                v.getContext().startActivity(intent);
            }
        });
    }
    @Override
    public String receiveID(String Id) {
        return Id;
    }
}
