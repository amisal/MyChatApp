package edu.nyit.anish.mychatapp;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class MainActivity extends AppCompatActivity implements WritableGUI{

    EditText inetAddress, targetport, listeningport, message;
    TextView chatwindow;
    Button listenBtn, sendbtn;
    String sentMassage,ipaddress,chatTxt="";
    WritableGUI gui;
    int lport, tport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(this,"Your IP Address is "+  getIpAddress(),Toast.LENGTH_LONG).show();

        inetAddress = (EditText) findViewById(R.id.inetAddtxt);
        targetport = (EditText) findViewById(R.id.targetportnotxt);
        listeningport = (EditText) findViewById(R.id.listeningportnotxt);
        message = (EditText) findViewById(R.id.msgtxt);
        chatwindow = (TextView) findViewById(R.id.chattxt);
        listenBtn = (Button) findViewById(R.id.listenbtn);
        sendbtn = (Button) findViewById(R.id.sendbtn);

        final Handler handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {

               displayMessaage(msg.obj.toString());

            }
        };
        listenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listen(handler);


            }


        });
        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sentMassage= message.getText().toString();
                ipaddress= inetAddress.getText().toString();
                tport= Integer.parseInt(targetport.getText().toString());

                MessageTransmitter transmitter= new MessageTransmitter(sentMassage,ipaddress,tport);
                transmitter.start();

            }
        });

    }


    private void listen(Handler handler) {

        lport= Integer.parseInt(listeningport.getText().toString());
        MessageListener listener = new MessageListener(this, lport,handler);
        listener.start();
       // write(listener.readline);



        // write();
    }


    private String getIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException e) {
            // Log.e(Constants.LOG_TAG, e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void write(String s) {

        //Log.d("MYCHATAPP", listener.getMessage());
                    chatTxt += s + "\n";
                    chatwindow.setText(chatTxt);
    }

    public void displayMessaage(String line) {

        chatTxt += line + "\n";
        chatwindow.setText(chatTxt);
    }
}
