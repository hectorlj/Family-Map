package Client;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import requests.registerRequest;

/**
 * Created by Hector Lopez on 11/10/2017.
 */

public class client {
    private static Gson gson;
    static {
        gson = new Gson();
    }
    public static void main(String [] args){
        String host = args[0];
        String port = args[1];
    }

    private static void register(String host, String port){
        try {
            URL url = new URL("http://" + host + ":" + port + "/user/register");
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.addRequestProperty("Authorization", "");
            httpURLConnection.addRequestProperty("Accept", "application/json");
            httpURLConnection.connect();
            registerRequest request = new registerRequest();
            String json = gson.toJson(request);
            OutputStream requestBody = httpURLConnection.getOutputStream();
            writeString(json, requestBody);
            requestBody.close();
            if(httpURLConnection.getResponseCode() != HttpURLConnection.HTTP_OK){

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void login (String host, String post){
        try{
            URL url = new URL("http://" + host + ":" + post + "/user/login");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.addRequestProperty("Authorization", "");
            httpURLConnection.addRequestProperty("Accept", "application/json");
            httpURLConnection.connect();
            registerRequest request = new registerRequest();
            String json = gson.toJson(request);
            OutputStream requestBody = httpURLConnection.getOutputStream();
            writeString(json, requestBody);
            requestBody.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    private static void load(String host, String port){
        try{
            URL url = new URL("http://" + host + ":" + port + "/load");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoInput(true);
            http.addRequestProperty("Accept", "application/json");
            http.connect();
            registerRequest request = new registerRequest();
            String json = gson.toJson(request);
            OutputStream requestBody = http.getOutputStream();
            writeString(json, requestBody);
            requestBody.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void fill(String host, String port) {
        try {
            String username = "";
            URL url = new URL("http://" + host + ":" + port + "/fill/" + username);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(false);
            httpURLConnection.addRequestProperty("Accept", "application/json");
            httpURLConnection.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
