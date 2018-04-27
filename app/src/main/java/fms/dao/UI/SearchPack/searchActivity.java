package fms.dao.UI.SearchPack;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import fms.dao.Model.globalHelper;
import fms.dao.R;
import results.personResults;
import results.singleEvent;

public class searchActivity extends AppCompatActivity {
    private EditText searchText;
    private RecyclerView recycler;
    private List<searchModel> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        list = new LinkedList<>();
        recycler = (RecyclerView)findViewById(R.id.search_recycle_view);
        searchText = (EditText)findViewById(R.id.search_bar);
        searchAdapter adapter = new searchAdapter(list, getApplicationContext());
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                list = new LinkedList<>();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                list =  search(s.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable s) {
                searchAdapter adapter = new searchAdapter(list, getBaseContext());
                recycler.setAdapter(adapter);
                Log.d("Info", adapter.toString());
                recycler.setLayoutManager(new LinearLayoutManager(getBaseContext()));

                Log.d("Info", recycler.toString());
            }
        });
   }

    public List<searchModel> search(String search) {
        List<searchModel> results = new LinkedList<>();
        search.toLowerCase();
        List<String> matching = new LinkedList<>();
        Set<String> eventids = new HashSet<>();
        for (personResults person : globalHelper.getInstance().getAllPeopleList()) {
            if (person.getName().toLowerCase().contains(search)) {
                results.add(new searchModel(person));
                matching.add(person.getPersonID());
            }
        }
        for(int i = 0; i < matching.size(); i++){
            if(globalHelper.getInstance().getPersonEventsMap().containsKey(matching.get(i))){
                for(singleEvent temp : globalHelper.getInstance().getPersonEventsMap().get(matching.get(i))){
                    results.add(new searchModel(temp));
                    eventids.add(temp.event.getEventId());
                }
            }
        }
        for (singleEvent event : globalHelper.getInstance().getAllEventsList()) {
            if (event.event.toString().toLowerCase().contains(search) && !eventids.contains(event.event.getEventId())) {
                results.add(new searchModel(event));
                eventids.add(event.event.getEventId());
            }
        }
        return results;
    }

}
