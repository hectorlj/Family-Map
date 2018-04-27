package fms.dao.Network;

import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import requests.dualInputRequest;
import requests.registerRequest;
import results.LoginResult;
import results.personResults;
import results.resultsBase;
import results.singleEvent;

/**
 * Created by Hector Lopez on 11/11/2017.
 */

public class serverProxy {
    private static serverProxy instance = null;
    private static Gson gson;
    private String authToken;
    private String host;
    private String port;
    public static serverProxy getInstance(){
        if(instance == null){
            instance = new serverProxy();
        }
        return instance;
    }
    static{
        gson = new Gson();
    }
    public serverProxy(){}
    public serverProxy(String host, String port){
        this.host = host;
        this.port = port;
    }

    public void setAuthToken(String authToken){
        this.authToken = authToken;
    }
    public void setHost(String host){
        this.host = host;
    }
    public void setPort(String port){
        this.port = port;
    }
    public String getAuthToken(){return authToken;}
    public String getHost(){return host;}
    public String getPort(){return port;}
    public LoginResult registerProxy(registerRequest user){
        try {
            URL url = new URL("http://" + host + ":" + port + "/user/register");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.addRequestProperty("Accept", "application/json");
            http.connect();
            String jsonBody = gson.toJson(user);
            OutputStream requestBody = http.getOutputStream();
            writeString(jsonBody, requestBody);
            requestBody.close();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                LoginResult registerResult = gson.fromJson(new InputStreamReader(http.getInputStream()), LoginResult.class);
                setAuthToken(registerResult.authToken);
                return registerResult;
            } else {
                Log.d("Error", http.getResponseMessage());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public resultsBase clearProxy(){
        try{
            URL url = new URL("http://" + host + ":" + port + "/clear");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(false);
            httpURLConnection.addRequestProperty("Accept", "application/json");
            httpURLConnection.connect();
            if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                return gson.fromJson(new InputStreamReader(httpURLConnection.getInputStream()), resultsBase.class);
            } else {
                Log.d("Error", httpURLConnection.getResponseMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public LoginResult loginProxy(dualInputRequest loginRequest) {
        try {
            URL url = new URL("http://" + host + ":" + port + "/user/login");

            HttpURLConnection http = (HttpURLConnection) url.openConnection();

            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.addRequestProperty("Accept", "application/json");
            http.setRequestProperty("Accept-Encoding","");
            http.connect();

            String jsonBody = gson.toJson(loginRequest);
            OutputStream requestBody = http.getOutputStream();
            writeString(jsonBody, requestBody);
            requestBody.close();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                LoginResult Results = gson.fromJson(new InputStreamReader(http.getInputStream()), LoginResult.class);
                setAuthToken(Results.authToken);
                return Results;
            } else {
                Log.d("Error", http.getResponseMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public resultsBase loadProxy(String loadRequest){
        try{
            URL url = new URL("http://" + host + ":" + port + "/load");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.addRequestProperty("Accept", "application/json");
            httpURLConnection.connect();
            OutputStream request = httpURLConnection.getOutputStream();
            writeString(loadRequest, request);
            request.close();
            if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                resultsBase result  = gson.fromJson(new InputStreamReader(httpURLConnection.getInputStream()), resultsBase.class);
                return result;
            } else {
                Log.d("Error", httpURLConnection.getResponseMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public personResults[] peopleProxy(){
        try{
            URL url = new URL("http://" + host + ":" + port + "/person/");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoOutput(false);
            httpURLConnection.addRequestProperty("Accept", "application/json");
            httpURLConnection.addRequestProperty("Authorization", getAuthToken());
            if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStream getMessage = httpURLConnection.getInputStream();
                String json = getStringFromInputStream(getMessage);
                Log.d("Json Body:---->", json);
                return gson.fromJson(json, personResults[].class);
            } else {
                Log.d("Error", httpURLConnection.getResponseMessage());
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public singleEvent[] eventsProxy(){
        try{
            URL url = new URL("http://" + host + ":" + port + "/event/");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoOutput(false);
            httpURLConnection.addRequestProperty("Accept", "application/json");
            httpURLConnection.addRequestProperty("Authorization", getAuthToken());
            if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                return gson.fromJson(new InputStreamReader(httpURLConnection.getInputStream()), singleEvent[].class);
            } else {
                Log.d("Error",httpURLConnection.getResponseMessage());
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    private static String getStringFromInputStream(InputStream inputStream){
        BufferedReader bufferedReader = null;
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        try{
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
    private static void writeString(String string, OutputStream outputStream){
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
        try {
            outputStreamWriter.write(string);
            outputStreamWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
