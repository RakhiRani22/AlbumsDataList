package com.test.app.mytestapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.test.app.mytestapp.model.Album;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class LocalDatabase extends SQLiteOpenHelper {
    private static final String ALBUM_ID = "id";
    private static final String ALBUM_TITLE = "title";
    private static final String ALBUM_USER_ID = "userid";
    private static final String[] COLUMNS = { ALBUM_ID, ALBUM_TITLE, ALBUM_USER_ID };
    private final String TAG = "LocalDatabase";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "AlbumDB";
    private static final String TABLE_NAME = "Albums";

    public LocalDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // This method is called when database is created.
    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL for creating the tables
        Log.d(TAG,"onCreate");
        String CREATION_TABLE = "CREATE TABLE Albums ( "
                + "id INTEGER, " + "title TEXT, "
                + "userid INTEGER )";

        db.execSQL(CREATION_TABLE);
    }

    // This method is called when database is upgraded like
    // modifying the table structure,
    // adding constraints to database, etc
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        // SQL for upgrading the tables
        Log.d(TAG,"onUpgrade");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    //This method returns all the albums data saved in the table.
    public List<Album> getAllAlbums() {
        List<Album> albumList = new LinkedList<Album>();
        String query = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Album album = null;

        if (cursor.moveToFirst()) {
            do {
                album = new Album();
                album.setId(Integer.parseInt(cursor.getString(0)));
                album.setTitle(cursor.getString(1));
                album.setUserId(Integer.parseInt(cursor.getString(2)));
                albumList.add(album);
            } while (cursor.moveToNext());
        }
        Log.d(TAG,"getAllAlbums:");
        return albumList;
    }

    //This method add the row in the table
    public void addAlbum(Album album) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ALBUM_ID, album.getId());
        values.put(ALBUM_TITLE, album.getTitle());
        values.put(ALBUM_USER_ID, album.getUserId());
        // insert
        db.insert(TABLE_NAME,null, values);
        db.close();
    }

    public void deleteAllRecords(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);

    }
}
