package fms.dao.UI.MapPack;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fms.dao.Model.People;
import fms.dao.Model.currentUser;
import fms.dao.Model.globalHelper;
import fms.dao.R;
import fms.dao.UI.PersonPack.PersonActivity;
import results.personResults;
import results.singleEvent;

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener{
    private GoogleMap googleMap;
    private List<Marker> allMarkers;
    private MapView mapView;
    private currentUser data;
    private LinearLayout bottomInfo;
    private String personMarkerClicked;
    private Map personEventPairs;
    private TextView currentPerson;
    private TextView currentEvent;
    private ImageView gender;
    private boolean isMapActivity;
    private int mapLineWidth = 10;
    private ArrayList<PolylineOptions> currentLines;
    private Map<String, LinkedList<Marker>> eventTypeMap = new HashMap<>();

    @Override
    public void onMapReady(GoogleMap googleMap){
        this.googleMap = googleMap;
        setMapType(globalHelper.getInstance().mapTypeArray.get(globalHelper.getInstance().getMapType()));
        this.googleMap.setOnMarkerClickListener(this);
        if(globalHelper.getInstance().isMapActivity){
            setMapAppView();
        } else {
            gender.setImageResource(R.mipmap.ic_welcome);
            currentPerson.setText(R.string.default_map_greeting);
            currentEvent.setText(R.string.default_map_two);
            addEventMarkers();
            globalHelper.getInstance().setCurrentMap(this.googleMap);
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        globalHelper.getInstance().buildFamilyTree();
        data = globalHelper.getInstance().getUser();
        personEventPairs = new HashMap();
        currentLines = new ArrayList<>();
        isMapActivity = globalHelper.getInstance().isMapActivity();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        View v = inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        currentPerson = (TextView)v.findViewById(R.id.current_Person_mapText);
        currentEvent = (TextView) v.findViewById(R.id.current_Event_mapText);
        gender = (ImageView) v.findViewById(R.id.gender_imageView);
        bottomInfo = (LinearLayout)v.findViewById(R.id.bottomMapLayout);
        bottomInfo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(personMarkerClicked != null){
                    Intent intent = new Intent(getActivity(), PersonActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "No person selected", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return v;
    }
    private void setMapType(String type){
        switch (type){
            case "Terrain":
                googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            case "Hybrid":
                googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            case "Satellite":
                googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            default:
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
    }

    private PolylineOptions addSpouseLine(personResults person){
        PolylineOptions polylineOptions = setLineColor(globalHelper.getInstance().lineColorList.get(globalHelper.getInstance().getSpouseLineColor()));
        if(person.person.getSpouse() != null){
            personResults spouse = People.getPersonById(person.person.getSpouse());
            if(spouse != null){
                singleEvent earliestEvent = globalHelper.getInstance().getEarliestDate(person.getPersonID());
                singleEvent spouseEvent = globalHelper.getInstance().getEarliestDate(spouse.getPersonID());
                LatLng spouseLocation = new LatLng(spouseEvent.event.getLatitude(), spouseEvent.event.getLongitude());
                LatLng location = new LatLng(earliestEvent.event.getLatitude(), earliestEvent.event.getLongitude());
                polylineOptions.add(location);
                polylineOptions.add(spouseLocation);
                return polylineOptions;
            }
        }
        return polylineOptions;
    }

    private void addFamilyLine(String personId, int firstYear){
        Set<String > parents = globalHelper.getInstance().getFamilyTree().get(personId);
        if(parents != null){
            for (String parent : parents){
                singleEvent event = globalHelper.getInstance().getEarliestDate(parent);
                if(event != null){
                    singleEvent childEvent = globalHelper.getInstance().getEarliestDate(personId);
                    singleEvent parentEvent = globalHelper.getInstance().getEarliestDate(parent);
                    PolylineOptions polylineOptions = setLineColor(globalHelper.getInstance().lineColorList.get(globalHelper.getInstance().getFamilyLineColor()));
                    polylineOptions.add(new LatLng(childEvent.event.getLatitude(), childEvent.event.getLongitude()));
                    polylineOptions.add(new LatLng(parentEvent.event.getLatitude(), parentEvent.event.getLongitude()));
                    if(firstYear - parentEvent.event.getYear() < 20){
                        mapLineWidth = 12;
                    } else if(firstYear - parentEvent.event.getYear() < 30){
                        mapLineWidth = 10;
                    } else if (firstYear - parentEvent.event.getYear() < 40){
                        mapLineWidth = 8;
                    } else if (firstYear - parentEvent.event.getYear() < 50
                            || firstYear - parentEvent.event.getYear() < 75){
                        mapLineWidth = 4;
                    } else if (firstYear - parentEvent.event.getYear() < 100){
                        mapLineWidth = 2;
                    } else {
                        mapLineWidth = 1;
                    }
                    polylineOptions.width(mapLineWidth);
                    googleMap.addPolyline(polylineOptions);
                    addFamilyLine(parent, firstYear);
                }
            }
        }
    }

    private void addLifeStoryLines(String personId){
        List<singleEvent> personEvents =  globalHelper.getInstance().getPersonEventsMap().get(personId);
        if(personEvents.size() > 1){
            globalHelper.getInstance().sortEvents(personEvents);
            for (int i = 0; i < personEvents.size() - 1; i++){
                PolylineOptions polylineOptions = setLineColor(globalHelper.getInstance().lineColorList.get(globalHelper.getInstance().getEventLineColor()));
                polylineOptions.add(new LatLng(personEvents.get(i).event.getLatitude(), personEvents.get(i).event.getLongitude()));
                polylineOptions.add(new LatLng(personEvents.get(i + 1).event.getLatitude(), personEvents.get(i + 1).event.getLongitude()));
                googleMap.addPolyline(polylineOptions);

            }
        }
    }

    private void addEventMarkers(){
        data.setGender();
        Map<String , Float> colorCode = globalHelper.getInstance().getEventColored();
        Set<String > fe = globalHelper.getInstance().buildFatherSide(data.getFamily()[0].person.getFather());
        Set<String > me = globalHelper.getInstance().buildMotherSide(data.getFamily()[0].person.getMother());
        for(String type : globalHelper.getInstance().getEventDescriptions()){
            if(!eventTypeMap.containsKey(type))
                eventTypeMap.put(type, new LinkedList<Marker>());
        }
        for(int i = 0; i < data.getEvents().length; i++){
            double latitude = data.getEvents()[i].event.getLatitude();
            double longitude = data.getEvents()[i].event.getLongitude();
            Float color = colorCode.get(data.getEvents()[i].event.getEventType().toLowerCase());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(latitude, longitude));
            markerOptions.title(data.getEvents()[i].event.getEventId());
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(color));

            Marker temp = googleMap.addMarker(markerOptions);
            if(fe.contains(data.getEvents()[i].event.getEventId())){
                String stuff = "fatherEvent " + data.getEvents()[i].personGender + "Event";
                temp.setTag(stuff);
            } else if(me.contains(data.getEvents()[i].event.getEventId())){
                String stuff = "motherEvent " + data.getEvents()[i].personGender + "Event";
                temp.setTag(stuff);
            } else {
                temp.setTag(data.getEvents()[i].personGender + "Event");
            }
            eventTypeMap.get(data.getEvents()[i].event.getEventType().toLowerCase()).add(temp);
        }
        filterMarkers();
    }
    private void filterMarkers(){
        for(String type : eventTypeMap.keySet()){

            for(Marker event : eventTypeMap.get(type)) {

                if(event.getTag() == null){
                    continue;
                }
                if (!globalHelper.getInstance().isMotherLines() &&
                        event.getTag().toString().contains("motherEvent")) {
                    event.setVisible(false);
                }
                if (!globalHelper.getInstance().isFatherLines()&&
                        event.getTag().toString().contains("fatherEvent")) {
                    event.setVisible(false);
                }
                if (!globalHelper.getInstance().isMaleLines() &&
                        event.getTag().toString().contains("mEvent")) {
                    event.setVisible(false);
                }
                if (!globalHelper.getInstance().isFemaleLines() &&
                        event.getTag().toString().contains("fEvent")) {
                    event.setVisible(false);
                }
            }
        }
    }

    private void setMapAppView(){
        String eventId = globalHelper.getInstance().getMarkerEventSelectedEvent();
        ArrayList<singleEvent> getEvent = (ArrayList<singleEvent>)globalHelper.getInstance().getAllEventsList();
        singleEvent event = new singleEvent();
        for(int i = 0; i < getEvent.size(); i++){
            if(getEvent.get(i).event.getEventId().equals(eventId)){
                event = getEvent.get(i);
            }
        }
        LatLng currentMark = new LatLng(event.event.getLatitude(), event.event.getLongitude());
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentMark, 4.0f));
        googleMap.clear();
        addEventMarkers();
        String eventString = buildEventInfo(event);
        personResults personClicked = People.getPersonById(event.event.getPersonId());
        String personString = personClicked.getName();
        currentPerson.setText(personString);
        currentEvent.setText(eventString);
        if(personClicked.person.getGender().toLowerCase().equals("m")){
            gender.setImageResource(R.drawable.male);
        } else {
            gender.setImageResource(R.drawable.female);
        }
        if(globalHelper.getInstance().isSpouseLinesVisible()){
            googleMap.addPolyline(addSpouseLine(personClicked));
        }
        if(globalHelper.getInstance().isFamilyLinesVisible()){
            addFamilyLine(personClicked.getPersonID(), globalHelper.getInstance().getEarliestDate(personClicked.getPersonID()).event.getYear());
        }
        if(globalHelper.getInstance().isEventLinesVisible()){
            addLifeStoryLines(personClicked.getPersonID());
        }
        personMarkerClicked = personClicked.getPersonID();
        globalHelper.getInstance().setMarkerEventSelectedUser(personMarkerClicked);

    }

    private PolylineOptions setLineColor(String color){
        PolylineOptions polylineOptions = new PolylineOptions().width(10);
        switch (color){
            case "Red":
                polylineOptions.color(Color.RED);
                break;
            case "Black":
                polylineOptions.color(Color.BLACK);
                break;
            case "Yellow":
                polylineOptions.color(Color.YELLOW);
                break;
            case "Green":
                polylineOptions.color(Color.GREEN);
                break;
            case "Blue":
                polylineOptions.color(Color.BLUE);
                break;
            case "Gray":
                polylineOptions.color(Color.GRAY);
                break;
            default:
                polylineOptions.color(Color.WHITE);
        }
        return polylineOptions;
    }
    @Override
    public boolean onMarkerClick(Marker marker){
        personResults personClicked = new personResults();
        singleEvent event = new singleEvent();
        boolean found = false;
        for (int i = 0; i < data.getEvents().length; i++){
            if(marker.getTitle().equals(data.getEvents()[i].event.getEventId())){
                event = data.getEvents()[i];
                found = true;
            }
        }
        if(!found) {
            return false;
        }
        googleMap.clear();
        addEventMarkers();
        String eventString = buildEventInfo(event);
        personClicked = People.getPersonById(event.event.getPersonId());
        String personString = personClicked.getName();
        currentPerson.setText(personString);
        currentEvent.setText(eventString);
        if(personClicked.person.getGender().toLowerCase().equals("m")){
            gender.setImageResource(R.drawable.male);
        } else {
            gender.setImageResource(R.drawable.female);
        }
        if(globalHelper.getInstance().isSpouseLinesVisible()){
            googleMap.addPolyline(addSpouseLine(personClicked));
        }
        if(globalHelper.getInstance().isFamilyLinesVisible()){
            addFamilyLine(personClicked.getPersonID(), globalHelper.getInstance().getEarliestDate(personClicked.getPersonID()).event.getYear());
        }
        if(globalHelper.getInstance().isEventLinesVisible()){
            addLifeStoryLines(personClicked.getPersonID());
        }
        personMarkerClicked = personClicked.getPersonID();
        globalHelper.getInstance().setMarkerEventSelectedUser(personMarkerClicked);
        return true;
    }

    private String buildEventInfo(singleEvent event){
        return event.event.getEventType() + ": " +
                event.event.getCity() + ", " +
                event.event.getCountry() + " (" +
                event.event.getYear() + ")";
    }
}
