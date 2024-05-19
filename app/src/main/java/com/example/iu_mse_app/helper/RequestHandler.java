package com.example.iu_mse_app.helper;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.iu_mse_app.R;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;


public class RequestHandler extends Thread {

    private final ArrayList<String> keys = new ArrayList<>();
    private final ArrayList<String> parameters = new ArrayList<>();
    private final Handler handler;
    private int messagesId = 99;
    private String destination = "";
    private final Context context;

    public RequestHandler(Handler handler, Context context){
        this.handler = handler;
        this.context = context;
    }

    public void addParameter(String key, String param){
        this.keys.add(key);
        this.parameters.add(param);
    }

    public void setDestination(String destination){
        this.destination = destination;
    }

    public void setMessagesId(int id){
        this.messagesId = id;
    }


    @Override
    public void run(){

        String received = Utilities.STATUS_NOK_JSON_STRING;

        try{

            SSLContext sslContext = Utilities.getSSLContextFromKeyStore(context);
            URL url = new URL( context.getString(R.string.serverDomain) + buildRequestString());
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setSSLSocketFactory(sslContext.getSocketFactory());
            urlConnection.setConnectTimeout(1000);
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            received = Utilities.convertInputStreamToString(in);

            Log.d("REQUEST", "RESPONSE: " + received);
        } catch (Exception e) {
            Log.e("ERROR", "RequestHandler: " + e);
        }

        Message msg = Message.obtain(handler, messagesId);
        msg.obj = received;
        handler.sendMessage(msg);

    }

    private String buildRequestString(){
        StringBuilder request = new StringBuilder(destination);

        for (int i = 0; i < keys.size(); i++) {
            if (i != 0) {
                request.append("&");
            } else {
                request.append("?");
            }
            request.append(keys.get(i)).append("=").append(parameters.get(i));
        }

        Log.d("REQUEST", "REQUEST: " + request);
        keys.clear();
        parameters.clear();
        return request.toString();
    }
}
