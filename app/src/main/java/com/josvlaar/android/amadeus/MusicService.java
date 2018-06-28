package com.josvlaar.android.amadeus;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentUris;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

public class MusicService extends Service implements
        MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
        MediaPlayer.OnCompletionListener {
    private MediaPlayer player;
    private ArrayList<Song> songList;
    private int songPos;
    private final IBinder musicBind = new MusicBinder();
    private boolean shuffle = false;
    private Random random;

    private String songTitle="";
    private static final int NOTIFY_ID=1;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return musicBind;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        this.player.stop();
        this.player.release();
        return false;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.songPos = 0;
        this.player = new MediaPlayer();
        initMusicPlayer();
        this.random = new Random();
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
        super.onDestroy();
    }

    public void initMusicPlayer() {
        this.player.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        this.player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        this.player.setOnPreparedListener(this);
        this.player.setOnCompletionListener(this);
        this.player.setOnErrorListener(this);
    }

    public void setSongList(ArrayList<Song> songs){
        this.songList = songs;
    }

    public class MusicBinder extends Binder {
        MusicService getService() {
            return MusicService.this;
        }
    }

    public void playSong() {
        this.player.reset();
        Song playSong = this.songList.get(this.songPos);
        this.songTitle = playSong.getTitle();
        Uri trackUri = playSong.getUri();

        try{
            player.setDataSource(getApplicationContext(), trackUri);
        }
        catch(Exception e){
            Log.e("MUSIC SERVICE", "Error setting data source", e);
        }
        player.prepareAsync();
    }

    public void setSong(int songIndex) {
        this.songPos = songIndex;
    }

    public Song getCurrentSong() {
        return this.songList.get(this.songPos);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();

        Intent notIntent = new Intent(this, PlayerActivity.class);
        notIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendIntent = PendingIntent.getActivity(this, 0,
                notIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(this);

        builder.setContentIntent(pendIntent)
                .setTicker(songTitle)
                .setOngoing(true)
                .setContentTitle("Playing").setContentText(songTitle);
        Notification not = builder.build();

        startForeground(NOTIFY_ID, not);
    }

    public int getPosition(){
        return player.getCurrentPosition();
    }

    public int getDuration(){
        return player.getDuration();
    }

    public boolean isPlaying(){
        return player.isPlaying();
    }

    public void pausePlayer(){
        player.pause();
    }

    public void seek(int posn){
        player.seekTo(posn);
    }

    public void go(){
        player.start();
    }

    public void playPrevious(){
        this.songPos--;
        if (this.songPos < 0) this.songPos = this.songList.size() - 1;
        playSong();
    }

    public void playNext(){
        if(shuffle){
            int newSong = this.songPos;
            while(newSong == this.songPos){
                newSong = this.random.nextInt(this.songList.size() - 1);
            }
            this.songPos = newSong;
        }
        else {
            this.songPos++;
            if (this.songPos > this.songList.size() - 1) this.songPos = 0;
        }
        playSong();
    }

    public void setShuffle(){
        this.shuffle = !this.shuffle;
    }
}
