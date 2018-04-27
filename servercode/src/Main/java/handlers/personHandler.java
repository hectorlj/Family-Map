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
import results.personResults;
import results.resultsBase;
import services.servicesLibrary;

/**
 * Created by Hector Lopez on 10/23/2017.
 */

public class personHandler implements HttpHandler {
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
            String json = gson.toJson(new resultsBase("No Authorization Token"));
            writeString(json, httpExchange.getResponseBody());
            httpExchange.close();
            return;
        }
        if(!validToken){
            String json = gson.toJson(new resultsBase("Invalid token"));
            writeString(json, httpExchange.getResponseBody());
            httpExchange.close();
            return;
        }
        boolean single = false;
        String personID = "";
        String[] split = uri.split("/");
        if (split.length == 3) {
            personID = split[2];
            single = true;
        }
        try {
            if (httpExchange.getRequestMethod().toLowerCase().equals("get")) {
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                if (single) {
                    resultsBase result = helper.person(authToken, personID, connector);
                    String json = gson.toJson(result);
                    OutputStream respBody = httpExchange.getResponseBody();
                    writeString(json, respBody);
                    respBody.close();
                    httpExchange.close();
                } else {
                    ArrayList<personResults> peopleList = helper.allPeople(authToken, connector);
                    if (peopleList.size() != 0) {
                        String json = gson.toJson(peopleList);
                        OutputStream respBody = httpExchange.getResponseBody();
                        writeString(json, respBody);
                        respBody.close();
                        httpExchange.close();
                    } else {
                        OutputStream respBody = httpExchange.getResponseBody();
                        String json = gson.toJson("Bad input none in Database");
                        writeString(json, respBody);
                        httpExchange.close();
                        respBody.close();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            String json = gson.toJson(new resultsBase("Server error"));
            writeString(json, httpExchange.getResponseBody());
            httpExchange.close();
        }
    }

    private void writeString(String string, OutputStream os) throws IOException {
        OutputStreamWriter osw = new OutputStreamWriter(os);
        osw.write(string);
        osw.flush();
    }
}
