package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

import results.resultsBase;
import services.servicesLibrary;

/**
 * Created by Hector Lopez on 10/23/2017.
 */

public class loadHandler implements HttpHandler {
    static private final servicesLibrary helper;
    static private final Gson gson;
    static private final Connector connector;
    static {
        helper = new servicesLibrary();
        gson = new Gson();
        connector = new Connector();
    }
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        boolean valid = false;
        if(httpExchange.getRequestMethod().toLowerCase().equals("post")){
            String json = readBody(httpExchange);
            resultsBase results = helper.load(json, connector);
            String afterServer = gson.toJson(results);
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            OutputStream respBody = httpExchange.getResponseBody();
            writeString(afterServer, respBody);
            respBody.close();
            httpExchange.close();
            valid = true;
        }
        if(!valid){
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            OutputStream respBody = httpExchange.getResponseBody();
            String json = gson.toJson(new resultsBase("User name does not exist"));
            writeString(json, respBody);
            httpExchange.close();
        }
    }
    private void writeString(String string, OutputStream os) throws IOException{
        OutputStreamWriter osw = new OutputStreamWriter(os);
        osw.write(string);
        osw.flush();
    }
    private String readBody(HttpExchange httpExchange) throws IOException{
        InputStreamReader inputStreamReader = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        int b;
        StringBuilder stringBuilder = new StringBuilder();
        while((b = bufferedReader.read()) != -1){
            stringBuilder.append((char)b);
        }
        bufferedReader.close();
        inputStreamReader.close();
        return stringBuilder.toString();
    }
}
