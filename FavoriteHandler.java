package com.fidku.jeloubeta.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class FavoriteHandler {

	private final String TAG = "SQLiteDataBaseHandler";
	private static final String SITENAME   = "siteindex";
	private static final String SITENAMESTR   = "siteindexstr";
	private static final String SITEURL   = "siteurl";
	private static final String FAVID   = "favorid";

	private FavoriteDatabase dbHelper;
	private SQLiteDatabase database;
	
	public FavoriteHandler(Context context) {
		dbHelper = new FavoriteDatabase(context);
		Log.i(TAG, "Object created.");
	}
	
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}
	
	public void insertContact(FavoriteDatabaseHandler siteinfo) {
		ContentValues cv = new ContentValues();
		cv.put(SITENAME,  siteinfo.getName());
		cv.put(SITENAMESTR,  siteinfo.getNamestr());
		cv.put(SITEURL, siteinfo.getUrl());
		cv.put(FAVID, siteinfo.getfavId());
	    database.insert(dbHelper.getTableName(), null, cv);
	}
	
	public void deleteContact(long id) {
		database.delete(dbHelper.getTableName(), dbHelper.getRowIdName() + "=" + id, null);
	}
	
	public void updateSite(FavoriteDatabaseHandler siteinfo,long id) {
		ContentValues cv = new ContentValues();
		cv.put(SITENAME,  siteinfo.getName());
		cv.put(SITENAMESTR,  siteinfo.getNamestr());
		cv.put(SITEURL, siteinfo.getUrl());
		cv.put(FAVID, siteinfo.getfavId());
		
		database.update(dbHelper.getTableName(), cv, dbHelper.getRowIdName() + "=" + id, null);
	}
	
	public void clearTable() {
		database.delete(dbHelper.getTableName(), null, null);
	}
	
	public List<FavoriteDatabaseHandler> getAllSites() {
		List<FavoriteDatabaseHandler> contacts = new ArrayList<FavoriteDatabaseHandler>();
		
		Cursor cursor = database.query(dbHelper.getTableName(),	null, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			FavoriteDatabaseHandler contact = cursorToSong(cursor);
			contacts.add(contact);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		
		return contacts;
	}
	
	public long searchsitename(String index, String url)
	{
		
		Cursor cursor = database.query(dbHelper.getTableName(),	null, null, null, null, null, null);
		cursor.moveToFirst();
		long delete_id = -1;
		while (!cursor.isAfterLast()) {
			Log.d("name=",cursor.getString(1));
			if(index.equals(cursor.getString(1)) && url.equals(cursor.getString(3)))
			{
				delete_id = cursor.getLong(0);
				break;
			}
			cursor.moveToNext();
		}
    	return delete_id;
		
	}
	
	public FavoriteDatabaseHandler searchfavid(String index)
	{
		FavoriteDatabaseHandler site = new FavoriteDatabaseHandler();
		Cursor cursor = database.query(dbHelper.getTableName(),	null, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Log.d("name=",cursor.getString(1));
			if(index.equals(cursor.getString(4)))
			{
				site = cursorToSong(cursor);
				break;
			}
			cursor.moveToNext();
		}
    	return site;
		
	}
	
	public FavoriteDatabaseHandler updatesearchurl(String index)
	{
		FavoriteDatabaseHandler site = new FavoriteDatabaseHandler();
		Cursor cursor = database.query(dbHelper.getTableName(),	null, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Log.d("name=",cursor.getString(1));
			if(index.equals(cursor.getString(3)))
			{
				site = cursorToSong(cursor);
				break;
			}
			cursor.moveToNext();
		}
    	return site;
		
	}
	
	private FavoriteDatabaseHandler cursorToSong(Cursor cursor) {
		FavoriteDatabaseHandler site = new FavoriteDatabaseHandler();
		
		site.setId(cursor.getLong(0));
		site.setName(cursor.getString(1));
		site.setNamestr(cursor.getString(2));
		site.setUrl(cursor.getString(3));
		site.setfavId(cursor.getString(4));
		return site;
	}

}