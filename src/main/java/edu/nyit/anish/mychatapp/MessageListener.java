package edu.nyit.anish.mychatapp;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Anish on 4/10/2016.
 */
public class MessageListener extends Thread {

    ServerSocket server;
    int port= 8877;
    WritableGUI gui;
    String line, readline;
    MainActivity activity;
    Handler handler;

    public MessageListener(WritableGUI gui, int port, Handler handler)
    {
        this.port= port;
        this.gui=gui;
        this.handler=handler;

        try {
            server= new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void run() {

        Socket clientSocket;


        try {
            while((clientSocket= server.accept())!= null)
            {
                InputStream is = clientSocket.getInputStream(); // gets input stream in binary

                BufferedReader br= new BufferedReader(new InputStreamReader(is)); // gets it in readable format


                line= br.readLine();

                //Toast.makeText(activity,line,Toast.LENGTH_LONG).show();

                Log.d("MYCHATAPP", line);

                try
                {
                    if(line != null)
                    {
                        Message msg=Message.obtain();
                        msg.obj=line;
                        handler.sendMessage(msg);
                       //gui.write(line);
                        //getMessage(line);
                    }
                }
                catch (NullPointerException npe)
                {
                    Log.d("MYCHATAPP", "NULL POINTER EXP");
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
