package com.example.iu_mse_app.helper;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import java.security.MessageDigest;

public class LoginHandler {

    private String username;
    private String password;
    private final Handler handler;
    private final Context context;

    public static final int SESSION_MSG = 0;
    public static final int CREDENTIAL_MSG = 1;
    public static final String KEY_SESSION_ID = "SESSION_ID";
    public static final String KEY_USERNAME = "USERNAME";
    private static SharedPrefHandler sharedPrefHandler;

    public LoginHandler(Handler handler, Context context) {
        this.handler = handler;
        this.context = context;
        sharedPrefHandler = new SharedPrefHandler(context);
    }

    public void setCredentials(String username, String password){
        this.username = username;
        this.password = password;
    }

    public void checkSession() {
        if(getUsername() != null && getSessionID() != null) {
            RequestHandler requestHandler = new RequestHandler(handler, context);
            requestHandler.setDestination("check_session");
            requestHandler.addParameter("username", getUsername());
            requestHandler.addParameter("sessionid", getSessionID());
            requestHandler.setMessagesId(SESSION_MSG);
            requestHandler.start();
        }
    }

    public void checkCredentials() {

        String hashString;
        byte[] passwordBytes = password.getBytes();
        RequestHandler requestHandler = new RequestHandler(handler, context);

        try {

            MessageDigest hash = MessageDigest.getInstance("SHA-256");
            byte[] digest = hash.digest(passwordBytes);
            hashString = Utilities.bytesToHex(digest);

            requestHandler.setDestination("login");
            requestHandler.addParameter("username", username);
            requestHandler.addParameter("password", hashString);
            requestHandler.setMessagesId(CREDENTIAL_MSG);
            requestHandler.start();

        } catch (Exception e) {
            Log.e("ERROR", "checkCredentials: " + e);
        }
    }

    public static void logout(){
        sharedPrefHandler.setValue(KEY_SESSION_ID, null);
    }

    public void setSessionID(String id) {
        sharedPrefHandler.setValue(KEY_SESSION_ID, id);
    }

    public void setUsername(String name) {
        sharedPrefHandler.setValue(KEY_USERNAME, name);
    }

    private String getSessionID(){
        return sharedPrefHandler.getValue(KEY_SESSION_ID);
    }

    private String getUsername(){
        return sharedPrefHandler.getValue(KEY_USERNAME);
    }

}
