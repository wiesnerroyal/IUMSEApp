package com.example.iu_mse_app.helper;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class SocketClient extends Thread {

    private final String host;
    private final int port;
    private final Handler handler;
    private final Context context;
    private SSLSocket clientSocket;
    public static final int STATUS_MSG = 0;
    public static final int NORMAL_MSG = 1;

    public SocketClient(String host, int port, Handler handler, Context context){
        this.host = host;
        this.port = port;
        this.handler = handler;
        this.context = context;
    }

    @Override
    public void run(){

        try {

            SSLContext sslContext = Utilities.getSSLContextFromKeyStore(context);
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            clientSocket =  (SSLSocket)sslSocketFactory.createSocket(host, port);

            sendHandlerMsg(Utilities.STATUS_OK_JSON_STRING, STATUS_MSG);

            while (clientSocket.isConnected() && !clientSocket.isClosed()){

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));

                String msgString;
                while ((msgString = in.readLine()) != null) {
                    sendHandlerMsg(msgString, NORMAL_MSG);
                    Log.d("RECV", msgString);
                }
            }
        } catch (Exception e) {
            Log.e("ERROR", "SOCKET: " + e);
        }
        close();
    }

    private void sendHandlerMsg(String msgString, int id){
        Message msg = Message.obtain(handler, id);
        msg.obj = msgString;
        handler.sendMessage(msg);
    }

    public void send(String msgString){
        new Thread(() -> {
            try {
                OutputStream os = clientSocket.getOutputStream();
                if (clientSocket.isConnected() && !clientSocket.isClosed()) {
                    byte[] msgBytes = msgString.getBytes(StandardCharsets.UTF_8);
                    os.write(msgBytes);
                    os.flush();
                }
            } catch (Exception e) {
                Log.e("ERROR", "SOCKET SEND: " + e);
                sendHandlerMsg(Utilities.STATUS_NOK_JSON_STRING, STATUS_MSG);
            }
        }).start();
    }

    public void close(){
        try {
            clientSocket.close();
        } catch (Exception e) {
            Log.e("ERROR", "SOCKET CLOSE: " + e);
        }
        sendHandlerMsg(Utilities.STATUS_NOK_JSON_STRING, STATUS_MSG);
    }
}
