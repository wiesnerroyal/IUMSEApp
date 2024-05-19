package com.example.iu_mse_app.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.iu_mse_app.R;
import com.example.iu_mse_app.helper.SocketClient;

import org.json.JSONException;
import org.json.JSONObject;

public class ChatFragment extends Fragment {

    private TextView history;
    private EditText msg;
    private SocketClient client;

    Handler handler = new Handler(msg -> {
        String message = (String) msg.obj;

        try {
            switch(msg.what) {
                case SocketClient.STATUS_MSG:
                    checkConnection(new JSONObject(message));
                    break;

                case SocketClient.NORMAL_MSG:
                    updateHistory(new JSONObject(message));
                    break;

                default:
                    Log.e("ERROR", "UNHANDLED MESSAGE ID " + msg.what);
            }
        } catch (Exception e) {
            Log.e("ERROR", "HANDLER: " + e);
        }
        return true;
    });

    public ChatFragment() {
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
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Button sendButton = requireView().findViewById(R.id.chatSendButton);
        sendButton.setOnClickListener(v -> buttonOnClick());
        history = requireView().findViewById(R.id.chatHistoryTextView);
        history.setMovementMethod(new ScrollingMovementMethod());
        msg = requireView().findViewById(R.id.chatEditText);

        client = new SocketClient(
                getString(R.string.socketServerAdr),
                Integer.parseInt(getString(R.string.socketServerPrt)),
                handler,
                getContext());
        client.start();
    }


    @Override
    public void onStop(){
        super.onStop();
        client.close();
    }

    private void checkConnection(JSONObject json) throws JSONException {
        if (json.getString("status").equals("ok")){
            requireView().findViewById(R.id.chatProgressLoader).setVisibility(View.INVISIBLE);
            requireView().findViewById(R.id.chatEditText).setEnabled(true);
            requireView().findViewById(R.id.chatSendButton).setEnabled(true);
        } else {
            requireView().findViewById(R.id.chatProgressLoader).setVisibility(View.VISIBLE);
            requireView().findViewById(R.id.chatEditText).setEnabled(false);
            requireView().findViewById(R.id.chatSendButton).setEnabled(false);
        }
    }

    private void buttonOnClick() {
        if (!msg.getText().toString().isEmpty()){
            String msgString = msg.getText().toString();
            msgString = msgString.replaceAll("[\\t\\n\\r]+"," ");
            msgString = msgString.replaceAll("\"","`");
            msgString = msgString.replaceAll("'","`");
            client.send("{'msg':'" + msgString + "'}");
        }
    }

    private void updateHistory(JSONObject json) throws JSONException {
        if (json.getString("status").equals("ok")) {

            history.append(getString(R.string.you) + " > ");
            history.append(msg.getText().toString());
            history.append("\r\n");
            msg.setText("");

            history.append(json.getString("moderator") + " > ");
            history.append(json.getString("msg"));
            history.append("\r\n");
        }
    }
}