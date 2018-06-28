package com.josvlaar.android.amadeus;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper instance;
    private Context context;

    private DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    public static DatabaseHelper getInstance(Context context) {
        if (instance != null) {
            return instance;
        } else {
            return new DatabaseHelper(context, "database", null, 1);
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE songs( _id INTEGER PRIMARY KEY, title VARCHAR(100), artist VARCHAR(100), album VARCHAR(100), genre VARCHAR(100), uri VARCHAR(500)); " +
                "CREATE TABLE playlists( _id INTEGER PRIMARY KEY, title VARCHAR(100), condition1_variable VARCHAR(100), condition1_value VARCHAR(100), condition2_variable VARCHAR(100));";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE songs;";
        db.execSQL(query);
        this.onCreate(db);
        this.fillDatabase();
    }

    public ArrayList<Song> selectAllSongs() {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM songs;";
        Cursor result = db.rawQuery(query, null);
        ArrayList<Song> songs = new ArrayList<Song>();
        for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {
            int titleColumn = result.getColumnIndex("title");
            int artistColumn = result.getColumnIndex("artist");
            int albumColumn = result.getColumnIndex("album");
            int uriColumn = result.getColumnIndex("uri");

            Uri currentUri = Uri.parse(result.getString(uriColumn));
            String currentTitle = result.getString(titleColumn);
            String currentArtist = result.getString(artistColumn);
            Song currentSong = new Song(currentTitle, currentArtist, currentUri);
            currentSong.setAlbum(result.getString(albumColumn));
            songs.add(currentSong);
        }
        return songs;
    }

    public Cursor getUniqueArtists() {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT DISTINCT artist FROM songs;";
        Cursor result = db.rawQuery(query, null);
        return result;
    }

    public Cursor getUniqueAlbums() {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT DISTINCT album FROM songs;";
        Cursor result = db.rawQuery(query, null);
        return result;
    }

    public Cursor getUniqueGenres() {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT DISTINCT genre FROM songs;";
        Cursor result = db.rawQuery(query, null);
        return result;
    }

    public ArrayList<Song> getSongsFromPlaylist(SmartPlaylist playlist) {
        if (this.selectAllSongs().size() < 1) this.fillDatabase();

        String query = "SELECT * FROM songs";
        if (playlist.getValue1() != null) {
            query += " WHERE " + playlist.getVariable1() + " = '" + playlist.getValue1() + "'";
        }
        if (playlist.getValue1() != null && playlist.getValue2() != null) {
            query += " AND ";
        }
        if (playlist.getValue2() != null) {
            query += playlist.getVariable2() + " = '" + playlist.getValue2() + "'";
        }
        query += ";";

        SQLiteDatabase db = getWritableDatabase();
        Cursor result = db.rawQuery(query, null);
        ArrayList<Song> songs = new ArrayList<Song>();
        for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {
            int titleColumn = result.getColumnIndex("title");
            int artistColumn = result.getColumnIndex("artist");
            int albumColumn = result.getColumnIndex("album");
            int uriColumn = result.getColumnIndex("uri");

            Uri currentUri = Uri.parse(result.getString(uriColumn));
            String currentTitle = result.getString(titleColumn);
            String currentArtist = result.getString(artistColumn);
            Song currentSong = new Song(currentTitle, currentArtist, currentUri);
            currentSong.setAlbum(result.getString(albumColumn));
            songs.add(currentSong);
        }
        return songs;
    }

    public void insert(Song song) {
        ContentValues cv = new ContentValues();
        cv.put("title", song.getTitle());
        cv.put("artist", song.getArtist());
        cv.put("album", song.getAlbum());
        cv.put("genre", song.getGenre());
        cv.put("uri", song.getUri().toString());

        SQLiteDatabase db = getWritableDatabase();
        db.insert("songs", null, cv);
    }

    public void delete(long id) {
        Log.d("DELETE", "deleting id: " + id);
        SQLiteDatabase db = getWritableDatabase();
        String query = "DELETE FROM songs WHERE _id=?";
        db.delete("songs", "_id=?", new String[] {Long.toString(id)});
        Log.d("DELETE", "deleted id: " + id);
    }

    public ArrayList<Song> fillDatabase() {
        ArrayList<Song> songList = new ArrayList<Song>();
        ContentResolver musicResolver = this.context.getContentResolver();
        Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);

        if(musicCursor != null && musicCursor.moveToFirst()) {
            // get columns
            int titleColumn = musicCursor.getColumnIndex(android.provider.MediaStore.Audio.Media.TITLE);
            int idColumn = musicCursor.getColumnIndex(android.provider.MediaStore.Audio.Media._ID);
            int artistColumn = musicCursor.getColumnIndex(android.provider.MediaStore.Audio.Media.ARTIST);
            int albumColumn = musicCursor.getColumnIndex(android.provider.MediaStore.Audio.Media.ALBUM);

            do {
                Uri currentUri = ContentUris.withAppendedId(
                        android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        musicCursor.getLong(idColumn));
                String currentTitle = musicCursor.getString(titleColumn);
                String currentArtist = musicCursor.getString(artistColumn);
                Song currentSong = new Song(currentTitle, currentArtist, currentUri);
                currentSong.setAlbum(musicCursor.getString(albumColumn));
                songList.add(currentSong);
                this.insert(currentSong);
            }
            while (musicCursor.moveToNext());
        }
        return songList;
    }
}
