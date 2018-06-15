package com.josvlaar.android.amadeus;

public class Song {
    private long id;
    private String title;
    private String artist;

    public Song(long songId, String title, String artist) {
        this.id = songId;
        this.title = title;
        this.artist = artist;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}
