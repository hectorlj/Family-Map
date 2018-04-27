package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.sql.SQLException;

import dao.AuthTokenDao;
import requests.dualInputRequest;
import results.resultsBase;
import services.servicesLibrary;

/**
 * Created by Hector Lopez on 10/23/2017.
 */

public class loginUserHandler implements HttpHandler {
    private static final Gson gson;
    private static final Connector connector;
    private static final servicesLibrary helper;
    static{
        AuthTokenDao authTokenDao = new AuthTokenDao();
        gson = new Gson();
        helper = new servicesLibrary();
        connector = new Connector();
    }
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        boolean success = false;
        try{
            if(httpExchange.getRequestMethod().toLowerCase().equals("post")){
                InputStreamReader inputStreamReader = new InputStreamReader(httpExchange.getRequestBody());
                dualInputRequest login = gson.fromJson(inputStreamReader, dualInputRequest.class);
                resultsBase result = helper.Login(login, connector);
                String json = gson.toJson(result);
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                OutputStream resbody = httpExchange.getResponseBody();
                writeString(json, resbody);
                resbody.close();
                httpExchange.close();
                success = true;

            }
            if(!success){
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                OutputStream respbody = httpExchange.getResponseBody();
                String json = gson.toJson(new resultsBase("Error in validation"));
                writeString(json, respbody);
                httpExchange.close();
            }
        }catch (IOException e){
            e.printStackTrace();
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            String json = gson.toJson(new resultsBase("Server error IO Exception"));
            writeString(json, httpExchange.getResponseBody());
            httpExchange.close();
        } catch (SQLException e) {
            e.printStackTrace();
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            String json = gson.toJson(new resultsBase("Server error IO Database Exception"));
            writeString(json, httpExchange.getResponseBody());
            httpExchange.close();        }
    }
    private void writeString(String string, OutputStream os)throws IOException{
        OutputStreamWriter osw = new OutputStreamWriter(os);
        osw.write(string);
        osw.flush();
    }
}
