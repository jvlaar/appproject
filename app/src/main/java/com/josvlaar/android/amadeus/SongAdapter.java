package com.josvlaar.android.amadeus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class SongAdapter extends BaseAdapter {

    private ArrayList<Song> songList;
    private LayoutInflater inflater;

    public SongAdapter(Context context, ArrayList<Song> songList) {
        this.songList = songList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return this.songList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout songLayout = (LinearLayout) this.inflater.inflate(R.layout.song_view, parent, false);
        TextView titleView = songLayout.findViewById(R.id.titleView);
        TextView artistView = songLayout.findViewById(R.id.artistView);

        Song song = this.songList.get(position);

        titleView.setText(song.getTitle());
        artistView.setText(song.getArtist());
        songLayout.setTag(position);

        return  songLayout;
    }
}