package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

import requests.registerRequest;
import results.resultsBase;
import services.servicesLibrary;

/**
 * Created by Hector Lopez on 10/23/2017.
 */

public class registerHandler implements HttpHandler {
    private static final Gson gson;
    private static final servicesLibrary helper;
    private static final Connector connector;
    static {
        connector = new Connector();
        helper = new servicesLibrary();
        gson = new Gson();
    }
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        boolean valid = false;
        connector.openConnection();
        connector.checkTables();
        connector.closeConnection(true);
        if(httpExchange.getRequestMethod().toLowerCase().equals("post")){
            InputStreamReader inputStreamReader = new InputStreamReader(httpExchange.getRequestBody());
            registerRequest register = gson.fromJson(inputStreamReader, registerRequest.class);
            resultsBase results = null;
            try {
                results = helper.Register(register, connector);
            } catch (Exception e) {
                e.printStackTrace();
            }
            valid = true;
            String json = gson.toJson(results);
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            OutputStream respBody = httpExchange.getResponseBody();
            writeString(json, respBody);
            respBody.close();
            httpExchange.close();

        }
        if(!valid){
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            OutputStream respBody = httpExchange.getResponseBody();
            String json = gson.toJson(new resultsBase("User name does not exist"));
            writeString(json, respBody);
            respBody.close();
            httpExchange.close();
        }
    }
    private void writeString(String string, OutputStream os) throws IOException {
        OutputStreamWriter osw = new OutputStreamWriter(os);
        osw.write(string);
        osw.flush();
    }
}
