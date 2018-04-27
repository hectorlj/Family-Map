package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

import results.resultsBase;

/**
 * Created by Hector Lopez on 10/23/2017.
 */

public class clearHandler implements HttpHandler {
    private static final Connector connector;
    private static final Gson gson;
    static {
        connector = new Connector();
        gson = new Gson();
    }
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        if(httpExchange.getRequestMethod().toLowerCase().equals("post")) {
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            resultsBase clearResult = new resultsBase();
            connector.openConnection();
            connector.clear();
            connector.closeConnection(true);
            connector.openConnection();
            connector.checkTables();
            connector.closeConnection(true);
            clearResult.message = "Database has been cleared";
            String jsonBody = gson.toJson(clearResult);
            OutputStream respBody = httpExchange.getResponseBody();
            writeString(jsonBody, respBody);
            httpExchange.close();
            respBody.close();
        }else{
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            OutputStream respBody = httpExchange.getResponseBody();
            String json = gson.toJson(new resultsBase("Error in HTTP request"));
            writeString(json, respBody);
            httpExchange.close();
        }
    }
    private void writeString(String string, OutputStream os){
        try{
            OutputStreamWriter osw = new OutputStreamWriter(os);
            osw.write(string);
            osw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
