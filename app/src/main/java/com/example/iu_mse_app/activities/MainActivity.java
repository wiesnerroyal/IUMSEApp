package com.example.iu_mse_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.iu_mse_app.R;
import com.example.iu_mse_app.fragments.ChatFragment;
import com.example.iu_mse_app.fragments.HistoryFragment;
import com.example.iu_mse_app.fragments.HomeFragment;
import com.example.iu_mse_app.fragments.InfoFragment;
import com.example.iu_mse_app.fragments.NewsFragment;
import com.example.iu_mse_app.fragments.VoteFragment;
import com.example.iu_mse_app.helper.AudioPlayer;
import com.example.iu_mse_app.helper.LoginHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity {

    private TextView toolbarTitle;
    public static int activeFragment = 0;
    private final ArrayList<Fragment> fragmentArrayList = new ArrayList<Fragment>() {{
        add(new HomeFragment());
        add(new VoteFragment());
        add(new NewsFragment());
        add(new ChatFragment());
        add(new HistoryFragment());
        add(new InfoFragment());
    }};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        FloatingActionButton floatingButton = findViewById(R.id.mainFloatingButton);
        floatingButton.setOnClickListener(v -> floatingButtonOnClick(floatingButton));

        Toolbar toolbar = findViewById(R.id.mainToolbar);
        toolbar.setOnClickListener(v -> toolbarOnClick());

        toolbarTitle = findViewById(R.id.mainToolbarTextView);

        setToolbarTitle();
        showActiveFragment();

    }

    @Override
    public void onPause() {
        super.onPause();
        if (AudioPlayer.isPlaying()) {
            AudioPlayer.stop();
            fabIconPause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (AudioPlayer.isPlaying()) {
            AudioPlayer.stop();
        }
    }

    public void fabIconPause(){
        FloatingActionButton floatingButton = findViewById(R.id.mainFloatingButton);
        floatingButton.setImageDrawable(ContextCompat.getDrawable(
                this.getApplicationContext(),
                R.drawable.pause_32)
        );
    }

    private void floatingButtonOnClick (FloatingActionButton fab) {

        if (AudioPlayer.isPlaying()){
            AudioPlayer.stop();
            fab.setImageDrawable(ContextCompat.getDrawable(
                    this.getApplicationContext(),
                    R.drawable.play_32)
            );

            Toast.makeText(getApplicationContext(),
                    getString(R.string.toast_pause),
                    Toast.LENGTH_SHORT
            ).show();
        } else {
            AudioPlayer.start(this);
            fab.setImageDrawable(ContextCompat.getDrawable(
                    this.getApplicationContext(),
                    R.drawable.pause_32)
            );

            Toast.makeText(
                    getApplicationContext(),
                    getString(R.string.toast_play),
                    Toast.LENGTH_SHORT
            ).show();
        }

    }

    private void toolbarOnClick(){
        if (activeFragment == 0) {
            Intent intent = new Intent(this, LoginActivity.class);
            LoginHandler.logout();
            startActivity(intent);
        } else {
            activeFragment = 0;
            setToolbarTitle();
            showActiveFragment();
        }
    }

    private void setToolbarTitle() {
        if (activeFragment == 0) {
            toolbarTitle.setText(getString(R.string.tb_logout));
        } else {
            toolbarTitle.setText(getString(R.string.tb_home));
        }
    }

    public void showActiveFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainFragmentContainer, fragmentArrayList.get(activeFragment));
        fragmentTransaction.commit();
    }
}