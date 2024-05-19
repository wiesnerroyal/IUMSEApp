package com.example.iu_mse_app.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.iu_mse_app.R;
import com.example.iu_mse_app.helper.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VoteFragment extends Fragment {

    private RequestHandler requestHandler;
    private GridLayout gridLayout;
    private Spinner spinner;
    private TextView textView;
    private Button button;
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

    public VoteFragment() {
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
        return inflater.inflate(R.layout.fragment_vote, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        progressBar = requireView().findViewById(R.id.voteProgressLoader);
        gridLayout = requireView().findViewById(R.id.voteGrid);
        spinner = requireView().findViewById(R.id.voteSpinner);
        textView = requireView().findViewById(R.id.voteTextView);
        button = requireView().findViewById(R.id.voteSubmitButton);
        button.setOnClickListener(v -> buttonOnClick());

        requestHandler = new RequestHandler(handler, getContext());
        requestHandler.setDestination("voting");
        requestHandler.start();

    }

    private void buttonOnClick(){
        requestHandler = new RequestHandler(handler, getContext());
        requestHandler.setDestination("vote");
        requestHandler.addParameter("song", spinner.getSelectedItem().toString());
        requestHandler.start();

        Toast.makeText(getContext(),
                getString(R.string.toast_submitted),
                Toast.LENGTH_SHORT
        ).show();

        button.setEnabled(false);
        textView.setText(getString(R.string.vote_error));

    }

    private void updateFragment(JSONObject json) throws JSONException {

        if (json.getString("voting").equals("ok")){

            textView.setText(getString(R.string.vote_voting));
            button.setEnabled(true);

            List<String> spinnerArray = new ArrayList<>();
            JSONArray jsonSongsArray = json.getJSONArray("songs");

            for (int i = 0; i < jsonSongsArray.length(); i++){
                spinnerArray.add(jsonSongsArray.getString(i));
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                    android.R.layout.simple_spinner_item, spinnerArray);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);


            progressBar.setVisibility(View.INVISIBLE);
            gridLayout.setVisibility(View.VISIBLE);


        } else if (json.getString("voting").equals("done")){
            progressBar.setVisibility(View.INVISIBLE);
            gridLayout.setVisibility(View.VISIBLE);
            button.setEnabled(false);
            spinner.setEnabled(false);
            textView.setText(getString(R.string.vote_error));
        }
    }

}