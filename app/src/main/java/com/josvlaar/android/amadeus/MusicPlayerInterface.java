package com.josvlaar.android.amadeus;

import java.util.ArrayList;

public interface MusicPlayerInterface {
    public void playSong(int songId);
    public void setSongList(ArrayList<Song> songList);
}
