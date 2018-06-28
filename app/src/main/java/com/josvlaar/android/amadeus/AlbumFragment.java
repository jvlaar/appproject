package com.josvlaar.android.amadeus;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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


public class AlbumFragment extends Fragment implements AdapterView.OnItemClickListener {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    private ListView songView;
    private PlaylistInterface playlistHandler;
    private MusicPlayerInterface musicPlayerHandler;
    private ArrayList<SmartPlaylist> playlistList;

    public AlbumFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static AlbumFragment newInstance() {
        AlbumFragment fragment = new AlbumFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_album, container, false);
        ListView playlistView = (ListView) rootView.findViewById(R.id.songListView);
        DatabaseHelper db = this.playlistHandler.getDatabaseHelper();
        Cursor result = db.getUniqueAlbums();
        Log.d("DEBUG", (String.valueOf(result.getCount())));
        this.playlistList = new ArrayList<SmartPlaylist>();
        for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {
            SmartPlaylist playlist = new SmartPlaylist();
            int index;
            index = result.getColumnIndex("album");
            playlist.setName(result.getString(index));
            playlist.setVariable1("album");
            playlist.setValue1(result.getString(index));
            this.playlistList.add(playlist);
        }
        ArrayAdapter playlistAdapter = new PlaylistAdapter(this.getActivity(), R.layout.playlist_view, this.playlistList);
        playlistView.setAdapter(playlistAdapter);
        playlistView.setOnItemClickListener(this);
        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SmartPlaylist playlist = this.playlistList.get(Integer.parseInt(view.getTag().toString()));
        // Create fragment and give it an argument specifying the article it should show
        SongFragment newFragment = SongFragment.newInstance(playlist);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.add(R.id.container, newFragment);
        transaction.addToBackStack(null);
        // Commit the transaction
        transaction.commit();
    }
}
