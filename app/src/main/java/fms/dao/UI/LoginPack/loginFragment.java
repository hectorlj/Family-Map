package fms.dao.UI.LoginPack;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import fms.dao.Model.currentUser;
import fms.dao.Model.globalHelper;
import fms.dao.Model.loginParam;
import fms.dao.Model.loginRegisterModel;
import fms.dao.Model.registerParam;
import fms.dao.Network.serverProxy;
import fms.dao.R;
import fms.dao.UI.MapPack.MapFragment;
import requests.dualInputRequest;
import requests.registerRequest;
import results.LoginResult;
import results.personResults;
import results.singleEvent;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link loginFragment} interface
 * to handle interaction events.
 * Use the {@link loginFragment#} factory method to
 * create an instance of this fragment.
 */
public class loginFragment extends Fragment implements View.OnClickListener {
    private loginRegisterModel login;
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText userName;
    private EditText password;
    private EditText port;
    private EditText host;
    private Button loginButton;
    private Button registerButton;
    private boolean radioChecked;
    private currentUser user;
    private OnLoginComplete loginComplete;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        login = new loginRegisterModel();
        user = new currentUser();
        onAttach(getContext());
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        firstName = (EditText)view.findViewById(R.id.first_name);
        firstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                login.setFirstName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        lastName = (EditText)view.findViewById(R.id.last_name);
        lastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                login.setLastName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        email = (EditText)view.findViewById(R.id.email);
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                login.setEmail(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        userName = (EditText)view.findViewById(R.id.username);
        userName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                login.setUserName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        host = (EditText)view.findViewById(R.id.host_number);
        host.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                login.setHost(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        port = (EditText)view.findViewById(R.id.port_number);
        port.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                login.setPort(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        password = (EditText)view.findViewById(R.id.password);
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                login.setPassword(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        view.findViewById(R.id.radioMale).setOnClickListener(this);
        view.findViewById(R.id.radioFemale).setOnClickListener(this);
        loginButton = (Button)view.findViewById(R.id.login_button);
        registerButton = (Button)view.findViewById(R.id.register_button);

        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(userName.getText().toString().length() == 0 || password.getText().toString().length() == 0 ||
                        port.getText().toString().length() == 0 || host.getText().toString().length() == 0){
                    Toast.makeText(getContext(), R.string.empty_toast, Toast.LENGTH_SHORT).show();
                } else {

                    loginParam loginParam = new loginParam(login.getUserName(), login.getPassword(),
                            login.getHost(), login.getPort());
                    loginTask checkLogin = new loginTask();
                    checkLogin.execute(loginParam);
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userName.getText().toString().length() == 0 || password.getText().toString().length() == 0
                        || firstName.getText().toString().length() == 0 || lastName.getText().toString().length() == 0
                        || email.getText().toString().length() == 0 || host.getText().toString().length() == 0
                        || port.getText().toString().length() == 0 || !radioChecked){
                    Toast.makeText(getContext(), R.string.empty_toast, Toast.LENGTH_SHORT).show();
                } else {
                    registerParam register = new registerParam(login.getUserName(), login.getPassword(), login.getEmail(),
                            login.getFirstName(), login.getLastName(), login.getGender(), login.getHost(), login.getPort());
                    registerTask task = new registerTask();
                    task.execute(register);
                }
            }
        });
        return view;
    }

    public boolean isOnline(Context context){
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    @Override
    public void onClick(View v) {
        radioChecked = ((RadioButton)v).isChecked();
        switch (v.getId()){
            case R.id.radioMale:
                login.setGender("M");
                break;
            case R.id.radioFemale:
                login.setGender("F");
                break;
        }

    }

    private class loginTask extends AsyncTask<loginParam, Void, Integer> {
        private serverProxy server;
        private LoginResult result;
        public loginTask() {
            server = serverProxy.getInstance();
        }

        @Override
        protected Integer doInBackground(loginParam... loginParams) {
            loginParam login = loginParams[0];
            server.setHost(login.getHost());
            server.setPort(login.getPort());
            dualInputRequest request = new dualInputRequest();
            request.userName = login.getUserName();
            request.password = login.getPassword();
            result = server.loginProxy(request);
            if (result.authToken == null) {
                return 1;
            }
            return 0;
        }

        @Override
        protected void onPostExecute(Integer i) {
            if (i == 0) {
                Toast.makeText(getContext(), R.string.login_succeed_toast, Toast.LENGTH_LONG).show();
                user.setAuthToken(result.authToken);
                user.setPersonId(result.personId);
                user.setUserName(result.userName);
                user.setHost(login.getHost());
                user.setPort(login.getPort());
                makeFamily();
            } else {
                Toast.makeText(getContext(), R.string.login_fail_toast, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class registerTask extends AsyncTask<registerParam, Void, Integer>{
        private serverProxy server;
        private LoginResult results;
        public registerTask(){
            server = serverProxy.getInstance();
        }
        @Override
        protected Integer doInBackground(registerParam... registerParams) {
            registerParam register = registerParams[0];
            server.setHost(register.getHost());
            server.setPort(register.getPort());
            registerRequest request  = new registerRequest();
            request.firstName = register.getFirstName();
            request.lastName = register.getLastName();
            request.password = register.getPassword();
            request.email = register.getEmail();
            request.gender = register.getGender();
            request.userName = register.getUserName();
            results = server.registerProxy(request);
            server.setAuthToken(results.authToken);

            if(results.authToken == null){
                return 1;
            }
            return 0;
        }

        @Override
        protected void onPostExecute(Integer i){
            if(i == 0){
                user.setPersonId(results.personId);
                user.setUserName(results.userName);
                user.setAuthToken(results.authToken);
                user.setHost(server.getHost());
                user.setPort(server.getPort());
                Toast.makeText(getContext(), R.string.register_succeed_toast, Toast.LENGTH_SHORT).show();
                makeFamily();
            }else {
                Toast.makeText(getContext(), R.string.register_fail_toast, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void changeFragment(){
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
        loginComplete.sendUserData(user);
        getFragmentManager().beginTransaction().replace(R.id.main_container, new MapFragment()).commit();
    }

    private class getFamilyTask extends AsyncTask<Void, Void, Integer>{
        private serverProxy server;
        private personResults[] people;
        private String auth;
        public getFamilyTask(String auth){
            server = serverProxy.getInstance();
            this.auth = auth;
        }
        @Override
        protected Integer doInBackground(Void... params){
            server.setAuthToken(auth);
            people = server.peopleProxy();
            if(people.length < 1){
                return 1;
            }
            return 0;
        }

        @Override
        protected void onPostExecute(Integer i){
            if(i == 0){
                user.setFamily(people);
                user.setFirstName(user.getFamily()[0].person.getFirstName());
                user.setLastName(user.getFamily()[0].person.getLastName());
                String stuff = "Welcome, " + user.getFirstName() + " " +user.getLastName() + ", you are now logged in";
                Toast.makeText(getContext(), stuff , Toast.LENGTH_LONG).show();
                Toast.makeText(getContext(), R.string.family_fetched, Toast.LENGTH_SHORT).show();
                makeEvents();
            } else {
                Toast.makeText(getContext(), R.string.family_not_fetched, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class getEventsTask extends AsyncTask<Void, Void, Integer>{
        private serverProxy server;
        private singleEvent[] events;
        private String authToken;
        public getEventsTask(String authToken){
            server = serverProxy.getInstance();
            this.authToken = authToken;
        }
        @Override
        protected Integer doInBackground(Void... params){
            server.setAuthToken(authToken);
            events = server.eventsProxy();
            if(events.length < 1){
                return 1;
            }
            return 0;
        }
        @Override
        protected void onPostExecute(Integer i){
            if(i == 0){
                user.setEvents(events);
                changeFragment();
                Toast.makeText(getContext(), R.string.events_fetched, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), R.string.events_notFetched, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void makeFamily(){
        getFamilyTask getFamily = new getFamilyTask(user.getAuthToken());
        getFamily.execute();
    }

    private void makeEvents(){
        getEventsTask getEvent = new getEventsTask(user.getAuthToken());
        getEvent.execute();
    }

    @SuppressWarnings("deprecation")
    public void onAttach(Activity activity){
        super.onAttach(activity);
        try{
            loginComplete = (OnLoginComplete)activity;
        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString() + " must implement send user data");
        }
    }
    public interface OnLoginComplete {
        void sendUserData(currentUser user);
    }
}
