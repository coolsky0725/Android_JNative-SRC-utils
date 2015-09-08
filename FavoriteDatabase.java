package com.fidku.jeloubeta.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class FavoriteDatabase extends SQLiteOpenHelper{
	
	private final String TAG = "SQLiteDataBaseHandler";
	private static final String DATABASE_NAME = "favoritedb";	
	private static final String TABLE_NAME    = "sitetable";
	private static final String COLUMN_ID     = "_id";
	private static final String SITE_NAME   = "siteindex";
	private static final String SITE_NAMESTR   = "siteindexstr";
	private static final String SITE_URL   = "siteurl";
	private static final String FAV_ID   = "favorid";
	private static final String CREATE_TABLE  = "CREATE TABLE " + TABLE_NAME + " (" + 
			COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
			SITE_NAME + " TEXT," +
			SITE_NAMESTR + " TEXT," +
			SITE_URL + " TEXT," +
			FAV_ID + " TEXT" +
			");";
		
		public FavoriteDatabase(Context context) {
			super(context, DATABASE_NAME, null, 1);
			
			Log.i(TAG, "Table created.");
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CREATE_TABLE);
		}
			
		public String getTableName() {
			return TABLE_NAME;
		}
		
		public String getRowIdName() {
			return COLUMN_ID;
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("Drop table if exists " + TABLE_NAME);
			onCreate(db);
		}

}
