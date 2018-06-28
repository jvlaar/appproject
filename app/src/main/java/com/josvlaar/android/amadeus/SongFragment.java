package com.josvlaar.android.amadeus;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class SongFragment extends Fragment implements AdapterView.OnItemClickListener {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    private ListView songView;
    private PlaylistInterface playlistHandler;
    private MusicPlayerInterface musicPlayerHandler;
    private DatabaseHelper db;
    private SmartPlaylist playlist;

    public SongFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static SongFragment newInstance() {
        SongFragment fragment = new SongFragment();
        Bundle args = new Bundle();
        SmartPlaylist playlist = new SmartPlaylist();
        args.putSerializable("playlist", playlist);
        fragment.setArguments(args);
        return fragment;
    }

    public static SongFragment newInstance(SmartPlaylist playlist) {
        SongFragment fragment = new SongFragment();
        Bundle args = new Bundle();
        args.putSerializable("playlist", playlist);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.playlistHandler = (PlaylistInterface) context;
        this.musicPlayerHandler = (MusicPlayerInterface) context;
        this.db = this.playlistHandler.getDatabaseHelper();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_song, container, false);
        ListView songView = (ListView) rootView.findViewById(R.id.songListView);
        this.playlist = (SmartPlaylist) this.getArguments().getSerializable("playlist");
        ArrayAdapter songAdapter = new SongAdapter(this.getActivity(), R.layout.song_view, this.db.getSongsFromPlaylist(playlist));
        songView.setAdapter(songAdapter);
        songView.setOnItemClickListener(this);
        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.musicPlayerHandler.setSongList(this.getSongList(this.playlist));
        this.musicPlayerHandler.playSong(Integer.parseInt(view.getTag().toString()));
    }

    public ArrayList<Song> getSongList(SmartPlaylist playlist) {
        ArrayList<Song> songList;
        songList = this.db.getSongsFromPlaylist(playlist);
        return songList;
    }
}
