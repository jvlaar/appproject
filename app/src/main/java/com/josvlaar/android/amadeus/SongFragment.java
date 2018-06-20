package com.josvlaar.android.amadeus;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class SongFragment extends Fragment implements AdapterView.OnItemClickListener {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    private ListView songView;
    private PlaylistInterface playlistHandler;
    private MusicPlayerInterface musicPlayerHandler;

    public SongFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static SongFragment newInstance(int sectionNumber) {
        SongFragment fragment = new SongFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.playlistHandler = (PlaylistInterface) context;
        this.musicPlayerHandler = (MusicPlayerInterface) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_song, container, false);
        ListView songView = rootView.findViewById(R.id.songListView);
        Adapter songAdapter = new SongAdapter(this.getActivity(), playlistHandler.getSongList());
        songView.setOnItemClickListener(this);
        return rootView;
    }

    private static int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.musicPlayerHandler.playSong(Integer.parseInt(view.getTag().toString()));
    }
}
