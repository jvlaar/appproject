package com.josvlaar.android.amadeus;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PlaylistFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PlaylistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlaylistFragment extends Fragment implements AdapterView.OnItemClickListener {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    private ListView songView;
    private PlaylistInterface playlistHandler;
    private MusicPlayerInterface musicPlayerHandler;
    private ArrayList<SmartPlaylist> playlistList;

    public PlaylistFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaylistFragment newInstance() {
        PlaylistFragment fragment = new PlaylistFragment();
        Bundle args = new Bundle();
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
        View rootView = inflater.inflate(R.layout.fragment_playlist, container, false);
        ListView playlistView = (ListView) rootView.findViewById(R.id.songListView);
        DatabaseHelper db = this.playlistHandler.getDatabaseHelper();
        this.playlistList = db.selectAllPlaylists();
        ArrayAdapter playlistAdapter = new PlaylistAdapter(this.getActivity(), R.layout.playlist_view, this.playlistList);
        playlistView.setAdapter(playlistAdapter);
        playlistView.setOnItemClickListener(this);

        FloatingActionButton fab = rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddPlaylistActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SmartPlaylist playlist = this.playlistList.get(Integer.parseInt(view.getTag().toString()));
        // Create fragment and give it an argument specifying the article it should show
        SongFragment newFragment = SongFragment.newInstance(playlist);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.rootLayout, newFragment, "PlaylistSongFragment");
        transaction.addToBackStack(null);
        // Commit the transaction
        transaction.commit();
    }
}
