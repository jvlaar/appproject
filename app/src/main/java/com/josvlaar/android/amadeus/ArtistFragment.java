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
import java.util.List;


public class ArtistFragment extends Fragment implements AdapterView.OnItemClickListener {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    private ListView songView;
    private PlaylistInterface playlistHandler;
    private MusicPlayerInterface musicPlayerHandler;
    private ArrayList<SmartPlaylist> playlistList;

    public ArtistFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ArtistFragment newInstance() {
        ArtistFragment fragment = new ArtistFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_artist, container, false);
        ListView playlistView = (ListView) rootView.findViewById(R.id.songListView);
        DatabaseHelper db = this.playlistHandler.getDatabaseHelper();
        Cursor result = db.getUniqueArtists();
        this.playlistList = new ArrayList<SmartPlaylist>();
        for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {
            SmartPlaylist playlist = new SmartPlaylist();
            int index;
            index = result.getColumnIndex("artist");
            playlist.setName(result.getString(index));
            playlist.setVariable1("artist");
            playlist.setValue1(result.getString(index));
            this.playlistList.add(playlist);
        }
        Log.d("DEBUG", playlistList.toString());
        ArrayAdapter playlistAdapter = new PlaylistAdapter(this.getActivity(), R.layout.playlist_view, this.playlistList);
        playlistView.setAdapter(playlistAdapter);
        playlistView.setOnItemClickListener(this);
        List<Fragment> fragmentList = getChildFragmentManager().getFragments();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        for (Fragment fragment : fragmentList) {
            transaction.remove(fragment);
            Log.d("DEBUG", "REMOVING FRAGMENT");
        }
        transaction.commit();
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
        transaction.replace(R.id.rootLayout, newFragment, "ArtistSongFragment");
        transaction.addToBackStack(null);
        // Commit the transaction
        transaction.commit();
    }
}
