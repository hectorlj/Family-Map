package fms.dao.UI.PersonPack;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import fms.dao.Model.globalHelper;
import fms.dao.R;
import fms.dao.UI.LoginPack.MainActivity;
import fms.dao.UI.PersonPack.eventExtendableRecycler.eventAdapter;
import fms.dao.UI.PersonPack.eventExtendableRecycler.eventTitleCreator;
import fms.dao.UI.PersonPack.models.eventModel;
import fms.dao.UI.PersonPack.models.eventParent;
import fms.dao.UI.PersonPack.models.personModel;
import fms.dao.UI.PersonPack.models.personParent;
import fms.dao.UI.PersonPack.personExtendableRecycle.personAdapter;
import fms.dao.UI.PersonPack.personExtendableRecycle.personTitleCreator;
import results.personResults;
import results.singleEvent;

import static fms.dao.Model.People.getPersonById;

public class PersonActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView eventsRecycled;
    private String selectedPerson;
    TextView bigTopName;
    TextView bigTopGender;
    MenuItem doubleUpArrow;

    @Override
    protected void onSaveInstanceState(Bundle bundle){
        super.onSaveInstanceState(bundle);
        ((personAdapter)recyclerView.getAdapter()).onSaveInstanceState(bundle);
        ((eventAdapter)eventsRecycled.getAdapter()).onSaveInstanceState(bundle);
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        selectedPerson = globalHelper.getInstance().getMarkerEventSelectedUser();
        bigTopGender = (TextView) findViewById(R.id.gender_person_clicked);
        bigTopName = (TextView) findViewById(R.id.fullname_title_personActivity);
        eventsRecycled = (RecyclerView) findViewById(R.id.event_recycler_view);
        eventsRecycled.setLayoutManager(new LinearLayoutManager(this));
        recyclerView = (RecyclerView) findViewById(R.id.parent_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        personAdapter personAdapter = new personAdapter(this, initData());
        personAdapter.setParentClickableViewAnimationDefaultDuration();
        personAdapter.setParentAndIconExpandOnClick(true);
        eventAdapter eventAdapter = new eventAdapter(this, initEventData());
        eventAdapter.setParentClickableViewAnimationDefaultDuration();
        eventAdapter.setParentAndIconExpandOnClick(true);
        String name = nameBuilder(selectedPerson);
        bigTopName.setText(name);
        personResults personResult = getPersonById(selectedPerson);
        String gender = "";
        if (personResult != null && !personResult.person.getGender().equals("")){
            gender = personResult.person.getGender();
        }
        if(gender.toLowerCase().equals("m")){
            bigTopGender.setText(R.string.male);
        } else {
            bigTopGender.setText(R.string.female);
        }
        recyclerView.setAdapter(personAdapter);
        eventsRecycled.setAdapter(eventAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_person, menu);
        doubleUpArrow = menu.findItem(R.id.double_up_arrow);
        Drawable doubleUpIcon = new IconDrawable(this, Iconify.IconValue.fa_arrow_up).sizeDp(24).color(Color.WHITE);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.double_up_arrow:
                globalHelper.getInstance().setMapActivity(false);
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }
    private List<ParentObject> initData(){
        personTitleCreator creator = personTitleCreator.get(this);
        List<personParent> titles = creator.getAll();
        List<ParentObject> parentObjects = new ArrayList<>();
        for(personParent title : titles){
            List<Object> childList = new ArrayList<>();
            ArrayList<String > children = globalHelper.getInstance().getChildren(selectedPerson);
            personResults person = getPersonById(selectedPerson);
            personResults temp;
            if (person != null && person.person.getFather() != null) {
                temp = getPersonById(person.person.getFather());
                if (temp != null) {
                    String name = nameBuilder(person.person.getFather());
                    childList.add(new personModel(name, temp.person.getGender(), "Father", temp.getPersonID()));
                }
            }

            if (person != null && person.person.getMother() != null) {
                temp = getPersonById(person.person.getMother());
                if (temp != null) {
                    String name = nameBuilder(person.person.getMother());
                    childList.add(new personModel(name, temp.person.getGender(), "Mother", temp.getPersonID()));
                }
            }

            if (person != null && person.person.getSpouse() != null) {
                temp = getPersonById(person.person.getSpouse());
                if (temp != null) {
                    String name = nameBuilder(person.person.getSpouse());
                    childList.add(new personModel(name, temp.person.getGender(), "Spouse", temp.getPersonID()));
                }
            }
            for(int i = 0; i < children.size(); i++){
                String name = nameBuilder(children.get(i));
                temp = getPersonById(children.get(i));
                if (temp != null) {
                    childList.add(new personModel(name, temp.person.getGender(), "Child", temp.getPersonID()));
                }
            }

            title.setChildObjectList(childList);
            parentObjects.add(title);
        }
        return parentObjects;
    }

    private List<ParentObject> initEventData(){
        eventTitleCreator titleCreator = eventTitleCreator.get(this);
        List<eventParent> titlesList = titleCreator.getAll();
        List<ParentObject> objects = new ArrayList<>();
        for(eventParent title : titlesList){
            ArrayList<singleEvent> events = (ArrayList<singleEvent>)globalHelper.getInstance().getPersonEventsMap().get(selectedPerson);
            List<Object> childList = new ArrayList<>();
            if(events != null && events.size() > 0){
                for(int i = 0; i < events.size(); i++){
                    Integer year = events.get(i).event.getYear();
                    eventModel builder = new eventModel(events.get(i).event.getEventType(),
                            events.get(i).event.getCity(), events.get(i).event.getCountry(),
                            year.toString(),  nameBuilder(selectedPerson), events.get(i).event.getEventId());
                    childList.add(builder);
                }
            }
            title.setChildObjectList(childList);
            objects.add(title);
        }
        return objects;
    }

    private String nameBuilder(String id) {
        personResults person = getPersonById(id);
        if (person != null && !Objects.equals(person.getName(), "")) {
            return person.getName();
        }
        return "";
    }

}
