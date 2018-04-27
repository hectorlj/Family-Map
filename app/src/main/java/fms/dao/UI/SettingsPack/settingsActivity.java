package fms.dao.UI.SettingsPack;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import fms.dao.Model.currentUser;
import fms.dao.Model.globalHelper;
import fms.dao.Network.serverProxy;
import fms.dao.R;
import fms.dao.UI.LoginPack.MainActivity;
import results.personResults;
import results.singleEvent;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class settingsActivity extends AppCompatActivity {

    /**
     * A preference value change listener that updates the preference's summary
     * to reflect its new value.
     */

    private Spinner spouseSpinner;
    private Spinner familySpinner;
    private Spinner eventSpinner;
    private Switch spouseLines;
    private Switch familyLines;
    private Switch eventsLine;
    private Button logout;
    private Button sync;
    private TextView progressBar;
    private ProgressBar waitingBar;

    private Spinner mapType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        spouseLines = (Switch)findViewById(R.id.spouse_setting);
        familyLines = (Switch)findViewById(R.id.family_tree_setting);
        eventsLine = (Switch)findViewById(R.id.life_story_setting);
        logout = (Button)findViewById(R.id.logout_button_setting);
        sync = (Button) findViewById(R.id.sync_button_setting);
        mapType = (Spinner) findViewById(R.id.map_spinner);
        progressBar = (TextView)findViewById(R.id.loading_text);
        waitingBar = (ProgressBar)findViewById(R.id.waitingBar);
        eventSpinner = (Spinner) findViewById(R.id.events_color_setting);
        spouseSpinner = (Spinner) findViewById(R.id.spouse_color_setting);
        familySpinner = (Spinner)findViewById(R.id.family_color_setting);
        eventSpinner.setSelection(globalHelper.getInstance().getEventLineColor());
        spouseSpinner.setSelection(globalHelper.getInstance().getSpouseLineColor());
        familySpinner.setSelection(globalHelper.getInstance().getFamilyLineColor());
        mapType.setSelection(globalHelper.getInstance().getMapType());
        eventsLine.setChecked(globalHelper.getInstance().isEventLinesVisible());
        familyLines.setChecked(globalHelper.getInstance().isFamilyLinesVisible());
        spouseLines.setChecked(globalHelper.getInstance().isSpouseLinesVisible());
        spouseLines.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                globalHelper.getInstance().setSpouseLinesVisible(isChecked);
            }
        });
        familyLines.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                globalHelper.getInstance().setFamilyLinesVisible(isChecked);
            }
        });
        eventsLine.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                globalHelper.getInstance().setEventLinesVisible(isChecked);
            }
        });
        sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waitingBar.setVisibility(View.VISIBLE);
                waitingBar.setProgress(0);
                resyncTask resync = new resyncTask();
                resync.execute();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalHelper.getInstance().eraseData();
                Toast.makeText(getApplicationContext(), "Logged Out", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        mapType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                switch (selectedItem){
                    case "Satellite":
                        globalHelper.getInstance().setMapType(2);
                        break;
                    case "Hybrid":
                        globalHelper.getInstance().setMapType(1);
                        break;
                    case "Terrain":
                        globalHelper.getInstance().setMapType(3);
                        break;
                    default:
                        globalHelper.getInstance().setMapType(0);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spouseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                switch (selectedItem){
                    case "Red":
                        globalHelper.getInstance().setSpouseLineColor(2);
                        break;
                    case "Yellow":
                        globalHelper.getInstance().setSpouseLineColor(4);
                        break;
                    case "Green":
                        globalHelper.getInstance().setSpouseLineColor(1);
                        break;
                    case "Black":
                        globalHelper.getInstance().setSpouseLineColor(3);
                        break;
                    case "White":
                        globalHelper.getInstance().setSpouseLineColor(5);
                        break;
                    default:
                        globalHelper.getInstance().setSpouseLineColor(0);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        eventSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                switch (selectedItem){
                    case "Red":
                        globalHelper.getInstance().setEventLineColor(2);
                        break;
                    case "Yellow":
                        globalHelper.getInstance().setEventLineColor(4);
                        break;
                    case "Blue":
                        globalHelper.getInstance().setEventLineColor(0);
                        break;
                    case "Black":
                        globalHelper.getInstance().setEventLineColor(3);
                        break;
                    case "White":
                        globalHelper.getInstance().setEventLineColor(5);
                        break;
                    default:
                        globalHelper.getInstance().setEventLineColor(1);
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        familySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                switch (selectedItem){
                    case "Green":
                        globalHelper.getInstance().setFamilyLineColor(1);
                        break;
                    case "Yellow":
                        globalHelper.getInstance().setFamilyLineColor(4);
                        break;
                    case "Blue":
                        globalHelper.getInstance().setFamilyLineColor(0);
                        break;
                    case "Black":
                        globalHelper.getInstance().setFamilyLineColor(3);
                        break;
                    case "White":
                        globalHelper.getInstance().setFamilyLineColor(5);
                        break;
                    default:
                        globalHelper.getInstance().setFamilyLineColor(2);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spouseLines.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                globalHelper.getInstance().setSpouseLinesVisible(isChecked);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    protected void onResume(){
        super.onResume();

    }

    private class resyncTask extends AsyncTask<Void, Integer, Integer>{
        private serverProxy server;
        private personResults[] people;
        private singleEvent[] events;
        private String auth;
        private currentUser user;
        private globalHelper helper;
        public resyncTask(){
            helper = globalHelper.getInstance();
            user = new currentUser();
            user.setPort(helper.getUser().getPort());
            user.setHost(helper.getUser().getHost());
            user.setAuthToken(helper.getUser().getAuthToken());
            server = serverProxy.getInstance();
            auth = user.getAuthToken();
        }
        @Override
        protected Integer doInBackground(Void... params){
            server.setAuthToken(auth);
            people = server.peopleProxy();
            events = server.eventsProxy();
            if(people.length < 1 || events.length < 1){
                return 1;
            }
            return 0;
        }

        @Override
        protected void onPreExecute(){
            progressBar.setText(R.string.sync_in_progress);
        }

        @Override
        protected void onProgressUpdate(Integer... progress){
            progressBar.setText("Re-Sync in progress..." + progress[0]);
            waitingBar.setProgress(progress[0]);
        }
        @Override
        protected void onPostExecute(Integer i){
            if(i == 0){
                user.setEvents(events);
                user.setFamily(people);
                updateData(user);
                Toast.makeText(getApplicationContext(), "Sync Successful", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Sync Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void updateData(currentUser user){
        user.setFamilyToArrayList();
        user.setEventsToArrayList();
        globalHelper.getInstance().setAllPeopleList(user.getPersonResultsArrayList());
        globalHelper.getInstance().buildFamilyTree();
        globalHelper.getInstance().setAllEventsList(user.getEventArrayList());
        globalHelper.getInstance().setEventDescriptions();
        globalHelper.getInstance().buildEventColored();
        globalHelper.getInstance().buildAllEventMap();
        globalHelper.getInstance().buildPersonEventsMap();
        globalHelper.getInstance().setLoggedIn(true);
        globalHelper.getInstance().setUser(user);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onPause(){
        super.onPause();
    }
}
