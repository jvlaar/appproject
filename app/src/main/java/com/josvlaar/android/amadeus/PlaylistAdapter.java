package com.josvlaar.android.amadeus;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class PlaylistAdapter extends ArrayAdapter<SmartPlaylist> {

    private ArrayList<SmartPlaylist> playlistList;
    private LayoutInflater inflater;


    public PlaylistAdapter(@NonNull Context context, int resource, @NonNull ArrayList<SmartPlaylist> playlistList) {
        super(context, resource, playlistList);
        this.playlistList = playlistList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return this.playlistList.size();
    }

    @Override
    public SmartPlaylist getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout playlistLayout = (LinearLayout) this.inflater.inflate(R.layout.playlist_view, parent, false);
        TextView titleView = playlistLayout.findViewById(R.id.nameView);

        SmartPlaylist playlist = this.playlistList.get(position);

        titleView.setText(playlist.getName());
        playlistLayout.setTag(position);

        return  playlistLayout;
    }
}
