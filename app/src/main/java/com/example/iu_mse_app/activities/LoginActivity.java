package com.example.iu_mse_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.iu_mse_app.R;
import com.example.iu_mse_app.helper.LoginHandler;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    Handler handler = new Handler(msg -> {

        String message = (String) msg.obj;

        try {
            switch (msg.what) {

                case LoginHandler.SESSION_MSG:
                    checkValidSession(new JSONObject(message));
                    break;

                case LoginHandler.CREDENTIAL_MSG:
                    checkCredentials(new JSONObject(message));
                    break;

                default:
                    Log.e("ERROR", "UNHANDLED MESSAGE ID " + msg.what);
            }

        } catch (Exception e) {
            Log.e("ERROR", "HANDLER: " + e);
        }
        return true;
    });

    private LoginHandler loginHandler;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private TextView textViewError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextUsername = findViewById(R.id.loginUsernameTextEdit);
        editTextPassword = findViewById(R.id.loginPasswordTextEdit);
        textViewError = findViewById(R.id.loginErrorTextView);

        Button buttonLoginNormal = findViewById(R.id.loginNormalButton);
        Button buttonLoginGoogle = findViewById(R.id.loginGoogleButton);
        Button buttonLoginFacebook = findViewById(R.id.loginFacebookButton);

        buttonLoginNormal.setOnClickListener(v -> loginButtonListener());
        buttonLoginGoogle.setOnClickListener(v -> loginButtonListener());
        buttonLoginFacebook.setOnClickListener(v -> loginButtonListener());

        loginHandler = new LoginHandler(handler, this);

        Intent intent = getIntent();
        if (Intent.ACTION_MAIN.equals(intent.getAction())) {
            // auto Login bei valider Session
            loginHandler.checkSession();
        }

    }

    private void loginButtonListener() {

        loginHandler.setCredentials(
                editTextUsername.getText().toString(),
                editTextPassword.getText().toString()
        );
        loginHandler.checkCredentials();
    }

    private void checkValidSession(JSONObject json) throws JSONException {
        if (json.getString("status").equals("ok")){
            loginOk();
        } else {
            textViewError.setText(getString(R.string.session_error));
            loginNok();
        }
    }

    private void checkCredentials(JSONObject json) throws JSONException {
        if (json.getString("status").equals("ok")){
            loginHandler.setSessionID(json.getString("sessionid"));
            loginHandler.setUsername(editTextUsername.getText().toString());
            loginOk();
        } else {
            textViewError.setText(getString(R.string.login_error));
            loginNok();
        }
    }

    private void loginNok(){
        Log.d("LOGIN", "login nok");
        textViewError.setVisibility(View.VISIBLE);
    }

    private void loginOk(){
        Log.d("LOGIN", "login ok");
        textViewError.setVisibility(View.INVISIBLE);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}