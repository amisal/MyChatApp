package edu.nyit.anish.mychatapp;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by Anish on 4/10/2016.
 */
public class MessageTransmitter extends Thread {

    String message, hostname;
    int port;

    public MessageTransmitter(String message, String hostname, int port) {
        this.message = message;
        this.hostname = hostname;
        this.port = port;
    }

    public MessageTransmitter()
    {

    }


    @Override
    public void run() {

        try {
            Socket s= new Socket(hostname,port);
            s.getOutputStream().write(message.getBytes());
            s.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
