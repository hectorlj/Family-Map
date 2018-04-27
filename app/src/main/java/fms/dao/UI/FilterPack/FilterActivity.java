package fms.dao.UI.FilterPack;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import fms.dao.Model.globalHelper;
import fms.dao.R;

public class FilterActivity extends AppCompatActivity {
    private Switch father;
    private Switch mother;
    private Switch female;
    private Switch male;

    private RecyclerView recyclerView;
    @Override
    protected void onSaveInstanceState(Bundle bundle){
        super.onSaveInstanceState(bundle);
        ((filterAdapter)recyclerView.getAdapter()).onSaveInstanceState(bundle);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        father = (Switch) findViewById(R.id.father_switch);
        mother = (Switch) findViewById(R.id.mother_switch);
        male = (Switch) findViewById(R.id.male_switch);
        female = (Switch) findViewById(R.id.female_switch);
//        birth = (Switch) findViewById(R.id.birth_switch);
//        death = (Switch) findViewById(R.id.death_switch);
//        marriage = (Switch)findViewById(R.id.marriage_switch);
//        baptism = (Switch) findViewById(R.id.baptism_switch);
        father.setChecked(globalHelper.getInstance().isFatherLines());
        mother.setChecked(globalHelper.getInstance().isMotherLines());
        male.setChecked(globalHelper.getInstance().isMaleLines());
        female.setChecked(globalHelper.getInstance().isFemaleLines());
        recyclerView = (RecyclerView) findViewById(R.id.filter_recycler_view);
        filterAdapter filterAdapter = new filterAdapter(this, initData());
        recyclerView.setAdapter(filterAdapter);
        filterAdapter.setParentClickableViewAnimationDefaultDuration();
        filterAdapter.setParentAndIconExpandOnClick(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
    @Override
    protected void onResume(){
        super.onResume();
        father.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                globalHelper.getInstance().setFatherLinesCheck(isChecked);
            }
        });
        mother.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                globalHelper.getInstance().setMotherLinesChecked(isChecked);
            }
        });
        male.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                globalHelper.getInstance().setMaleLinesCheck(isChecked);
            }
        });
        female.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                globalHelper.getInstance().setFemaleLinesCheck(isChecked);
            }
        });
//        birth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                globalHelper.getInstance().setBirthLines(isChecked);
//            }
//        });
//        death.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                System.out.println(isChecked);
//                globalHelper.getInstance().setDeathLines(isChecked);
//            }
//        });
//        marriage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                globalHelper.getInstance().setMarriageLines(isChecked);
//            }
//        });
//        baptism.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                globalHelper.getInstance().setBaptismLines(isChecked);
//            }
//        });
        List<String > list = new ArrayList<>();
        for(String stuff : globalHelper.getInstance().getEventDescriptions()){
            System.out.println(stuff);
        }
    }
    private List<ParentObject> initData(){
        filterTitleCreator titleCreator = filterTitleCreator.get(this);
        List<filterParent> titlesList = titleCreator.getAll();
        List<ParentObject> objects = new ArrayList<>();
        for(filterParent title : titlesList){
            List<Object> childList = new ArrayList<>();
            Set<String> events = globalHelper.getInstance().getEventDescriptions();
            for(String event : events){
                childList.add(event);
            }
            title.setChildObjectList(childList);
            objects.add(title);
        }
        return objects;
    }
}
