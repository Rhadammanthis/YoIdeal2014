package com.cceo.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteTutorialControl extends SQLiteOpenHelper{

	private static final int DATABASE_VERSION = 2;
	private static final String TABLE_NAME = "tutorialcontrol";
	private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
            "id_tutorialcontrol" + " TEXT, " +
            "tutorial" + " TEXT, " +
            "button_tutorial" + " TEXT);";

	public SQLiteTutorialControl(Context context) {
		super(context, TABLE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
