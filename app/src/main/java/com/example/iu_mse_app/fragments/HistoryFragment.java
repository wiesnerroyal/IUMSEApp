package com.example.iu_mse_app.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.iu_mse_app.R;
import com.example.iu_mse_app.helper.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class HistoryFragment extends Fragment {

    private ProgressBar progressBar;

    Handler handler = new Handler(msg -> {
        String message = (String) msg.obj;
        try {
            updateHistory(new JSONObject(message));
        } catch (Exception e) {
            Log.e("ERROR", "HANDLER: " + e);
        }
        return true;
    });

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onStart(){
        super.onStart();
        progressBar = requireView().findViewById(R.id.historyProgressLoader);
        RequestHandler requestHandler = new RequestHandler(handler, getContext());
        requestHandler.setDestination("history");
        requestHandler.start();
    }


    private void updateHistory(JSONObject json) throws JSONException {

        if (json.getString("history").equals("ok")) {

            progressBar.setVisibility(View.INVISIBLE);

            JSONArray jsonTimeArray = json.getJSONArray("time");
            JSONArray jsonSongArray = json.getJSONArray("songs");

            TextView textView = requireView().findViewById(R.id.historyTextView);

            for (int i = 0; i < jsonTimeArray.length(); i++){
                textView.append(jsonTimeArray.getString(i));
                textView.append(" : ");
                textView.append(jsonSongArray.getString(i));
                textView.append("\r\n");
            }
        }

    }

}