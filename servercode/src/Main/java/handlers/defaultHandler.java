package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Hector Lopez on 10/27/2017.
 */

public class defaultHandler implements HttpHandler{
    private int code = 500;
    @Override
    public void handle(HttpExchange httpExchange){
        try{
            String uri = httpExchange.getRequestURI().getPath();
            String pathString = uri.equals("/")?"gradle/assets/index.html" : "gradle/assets" + uri;
            File file = new File(pathString);
            if(file.isFile())
                code = 200;
            else{
                code = 404;
                file = new File("gradle/assets/404.html");
            }
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            DataInputStream dataInputStream = new DataInputStream(fileInputStream);
            dataInputStream.readFully(data);
            httpExchange.sendResponseHeaders(code, data.length);
            OutputStream outputStream = httpExchange.getResponseBody();
            outputStream.write(data);
            fileInputStream.close();
            dataInputStream.close();
            outputStream.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
