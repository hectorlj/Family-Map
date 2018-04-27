package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.sql.SQLException;

import results.resultsBase;
import services.servicesLibrary;

/**
 * Created by Hector Lopez on 10/23/2017.
 */

public class fillHandler implements HttpHandler {
    private static final Connector connector;
    private static final Gson gson;
    private static final servicesLibrary helper;
    static{
        connector = new Connector();
        gson = new Gson();
        helper = new servicesLibrary();
    }
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        boolean valid = false;
        String userName;
        Integer generations = 4;
        String uri = httpExchange.getRequestURI().getPath();
        try {
            String[] parts = uri.split("/");

            userName = parts[2];
            if (parts.length == 4)
                generations = Integer.parseInt(parts[3]);
            if (generations >= 0) {
                if (httpExchange.getRequestMethod().toLowerCase().equals("post")) {
                    resultsBase result;
                    result = helper.fill(userName, generations, connector);
                    String json = gson.toJson(result);
                    httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    OutputStream respBody = httpExchange.getResponseBody();
                    writeString(json, respBody);
                    respBody.close();
                    httpExchange.close();
                    valid = true;
                }
            }
            if (!valid) {
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                OutputStream respBody = httpExchange.getResponseBody();
                String json = gson.toJson(new resultsBase("User name does not exist"));
                writeString(json, respBody);
                httpExchange.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void writeString(String string, OutputStream os){
        try{
            OutputStreamWriter osw = new OutputStreamWriter(os);
            osw.write(string);
            osw.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
