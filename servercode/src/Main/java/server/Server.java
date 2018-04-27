package server;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

import handlers.Connector;
import handlers.clearHandler;
import handlers.defaultHandler;
import handlers.eventHandler;
import handlers.fillHandler;
import handlers.loadHandler;
import handlers.loginUserHandler;
import handlers.personHandler;
import handlers.registerHandler;

/**
 * Created by Hector Lopez on 10/20/2017.
 */

class Server {
@SuppressWarnings("serial")
    public static void main(String[] args){
        Connector connector = new Connector();
        connector.openConnection();
        connector.checkTables();
        connector.closeConnection(true);
        try{
            int port = args.length > 0 ? Integer.parseInt(args[0]) : 8080;

            HttpServer httpServer = HttpServer.create(new InetSocketAddress(port), 0);
            httpServer.createContext("/", new defaultHandler());
            httpServer.createContext("/user/register", new registerHandler());
            httpServer.createContext("/clear", new clearHandler());
            httpServer.createContext("/user/login", new loginUserHandler());
            httpServer.createContext("/fill", new fillHandler());
            httpServer.createContext("/load", new loadHandler());
            httpServer.createContext("/person", new personHandler());
            httpServer.createContext("/event", new eventHandler());
            httpServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
