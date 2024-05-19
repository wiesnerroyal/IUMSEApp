package com.example.iu_mse_app.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.iu_mse_app.R;
import com.example.iu_mse_app.helper.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class NewsFragment extends Fragment {

    private ProgressBar progressBar;

    Handler handler = new Handler(msg -> {
        String message = (String) msg.obj;
        try {
            updateFragment(new JSONObject(message));
        } catch (Exception e) {
            Log.e("ERROR", "HANDLER: " + e);
        }
        return true;
    });

    public NewsFragment() {
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
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onStart(){
        super.onStart();

        progressBar = requireView().findViewById(R.id.newsProgressLoader);
        RequestHandler requestHandler = new RequestHandler(handler, getContext());
        requestHandler.setDestination("news");
        requestHandler.start();

    }

    private void updateFragment(JSONObject json) throws JSONException {

        if (json.getString("news").equals("ok")) {

            progressBar.setVisibility(View.INVISIBLE);

            JSONArray jsonHeaderArray = json.getJSONArray("header");
            JSONArray jsonContentArray = json.getJSONArray("content");

            LinearLayout linearLayout = requireView().findViewById(R.id.newsScrollViewLayout);
            linearLayout.removeAllViews();

            for (int i = 0; i < jsonHeaderArray.length(); i++){
                View view = LayoutInflater.from(
                        getContext()).inflate(R.layout.fragment_news_feed, linearLayout, false);

                TextView header = view.findViewById(R.id.fnfHeader);
                header.setText(jsonHeaderArray.getString(i));
                TextView content = view.findViewById(R.id.fnfContent);
                content.setText(jsonContentArray.getString(i));
                linearLayout.addView(view);

            }
        }
    }
}