package com.josvlaar.android.amadeus;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.TextView;

public class PlayerActivity extends AppCompatActivity implements MediaController.MediaPlayerControl {

    private MusicService musicService;
    private Intent playIntent;
    private boolean musicBound = false;

    private MusicController controller;

    private ServiceConnection musicConnection = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder)service;
            //get service
            PlayerActivity.this.musicService = binder.getService();
            PlayerActivity.this.musicBound = true;
            PlayerActivity.this.onServiceConnected();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            PlayerActivity.this.musicBound = false;
        }
    };

    private void onServiceConnected() {
        TextView title = findViewById(R.id.SongTitleView);
        TextView artist = findViewById(R.id.SongArtistView);
        title.setText(this.musicService.getCurrentSong().getTitle());
        artist.setText(this.musicService.getCurrentSong().getArtist());
        try {
            this.controller.show();
        } catch (Exception e) {}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        // Get the service
        if(playIntent==null){
            playIntent = new Intent(this, MusicService.class);
            bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);
            startService(playIntent);
        }
        this.setController();

        Button backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void playNext(){
        this.musicService.playNext();
        this.onServiceConnected();
    }

    @Override
    public void start() {
        this.musicService.go();
    }

    @Override
    public void pause() {
        this.musicService.pausePlayer();
    }

    @Override
    public int getDuration() {
        if (this.musicService != null && musicBound && this.musicService.isPlaying())
            return this.musicService.getDuration();
        else return 0;
    }

    @Override
    public int getCurrentPosition() {
        if (this.musicService != null && musicBound && this.musicService.isPlaying())
            return this.musicService.getPosition();
        else return 0;
    }

    @Override
    public void seekTo(int pos) {
        this.musicService.seek(pos);
    }

    @Override
    public boolean isPlaying() {
        if (this.musicService != null && musicBound)
            return this.musicService.isPlaying();
        else return false;
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }

    private void playPrev(){
        this.musicService.playPrevious();
        this.onServiceConnected();
    }

    private void setController(){
        this.controller = new MusicController(this);
        this.controller.setPrevNextListeners(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playNext();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playPrev();
            }
        });

        this.controller.setMediaPlayer(this);
        this.controller.setAnchorView(findViewById(R.id.playerLayout));
        this.controller.setEnabled(true);
    }
}
