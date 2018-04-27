package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import dao.AuthTokenDao;
import results.resultsBase;
import results.singleEvent;
import services.servicesLibrary;

/**
 * Created by Hector Lopez on 10/29/2017.
 */

public class eventHandler implements HttpHandler {
    static private final Connector connector;
    static private final AuthTokenDao authTokenDao;
    static private final Gson gson;
    static private final servicesLibrary helper;
    static {
        connector = new Connector();
        authTokenDao = new AuthTokenDao();
        gson = new Gson();
        helper = new servicesLibrary();
    }
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String uri = httpExchange.getRequestURI().getPath();
        Headers reqHeaders = httpExchange.getRequestHeaders();
        boolean validToken;
        String authToken = reqHeaders.getFirst("Authorization");
        if(reqHeaders.containsKey("Authorization")){
            validToken = authTokenDao.readTok(authToken, connector);
        }
        else{
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            OutputStream respbody = httpExchange.getResponseBody();
            String json = gson.toJson(new resultsBase("No Authorization Token"));
            writeString(json, respbody);
            respbody.close();
            httpExchange.close();
            validToken = false;
        }
        if(!validToken){
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            OutputStream respbody = httpExchange.getResponseBody();
            String json = gson.toJson(new resultsBase("Unauthorized Token"));
            writeString(json, respbody);
            respbody.close();
            httpExchange.close();
            validToken = false;
        }
        boolean single = false;
        String eventID = "";
        String[] split = uri.split("/");
        if(split.length == 3){
            eventID = split[2];
            single = true;
        }
        try{
            if(httpExchange.getRequestMethod().toLowerCase().equals("get") && validToken){
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                if(single){
                    resultsBase result = helper.event(authToken, eventID, connector);
                    String json = gson.toJson(result);
                    OutputStream respBody = httpExchange.getResponseBody();
                    writeString(json, respBody);
                    respBody.close();
                    httpExchange.close();
                } else {
                    ArrayList<singleEvent> eventArrayList = helper.allEvents(authToken, connector);
                    if(eventArrayList.size() != 0){
                        String json = gson.toJson(eventArrayList);
                        OutputStream respbody = httpExchange.getResponseBody();
                        writeString(json, respbody);
                        respbody.close();
                        httpExchange.close();
                    } else {
                        OutputStream respbody = httpExchange.getResponseBody();
                        String json = gson.toJson(new resultsBase("Bad input"));
                        writeString(json, respbody);
                        respbody.close();
                        httpExchange.close();
                    }
                }
            }
        } catch (IOException e){
            e.printStackTrace();
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            String json = gson.toJson(new resultsBase("Server error IO Exception"));
            writeString(json, httpExchange.getResponseBody());
            httpExchange.close();
        }
    }
    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter osw = new OutputStreamWriter(os);
        osw.write(str);
        osw.flush();
    }
}
