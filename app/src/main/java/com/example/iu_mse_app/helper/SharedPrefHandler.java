package com.example.iu_mse_app.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.iu_mse_app.R;

public class SharedPrefHandler {

    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    public SharedPrefHandler(Context context){
        sharedPreferences =context.getSharedPreferences(
                context.getString(R.string.app_name),
                Context.MODE_PRIVATE
        );
        editor = sharedPreferences.edit();
    }

    public void setValue(String key, String value){
        editor.putString(key, value);
        editor.apply();
    }

    public String getValue(String key){
        return sharedPreferences.getString(key, null);
    }

}
