package com.prohacker.busapp.services;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.widget.Toast;

import com.prohacker.busapp.R;

public class SoundPlayer {
    private MediaPlayer player;
    private Context context;

    public SoundPlayer(Context context){
        this.context = context;
    }

    public void play(int resource){
        if(player==null){
            player = MediaPlayer.create(context, resource);
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    stopPlayer();
                }
            });
            player.start();
        }
    }

    public void stop(){
        stopPlayer();
    }
    private void stopPlayer() {
        if (player != null) {
            player.release();
            player = null;
            Toast.makeText(context, "Player released", Toast.LENGTH_SHORT).show();
        }
    }
}
