package com.example.iu_mse_app.helper;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import com.example.iu_mse_app.R;

public class AudioPlayer {

    private static MediaPlayer mediaPlayer = new MediaPlayer();

    public static void start(Context context) {

        try {
            mediaPlayer.setDataSource(context.getString(R.string.audioStreamUrl));
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(MediaPlayer::start);
        } catch (Exception e) {
            Log.e("ERROR", "PLAYER: " + e);
        }
    }

    public static void stop() {
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = new MediaPlayer();
    }

    public static boolean isPlaying(){
        return mediaPlayer.isPlaying();
    }

}
