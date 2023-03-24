package com.videodown.allhd.app.AllStory.AllvideoAPI;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "instagramDb";
    public static final int DATABASE_VERSION = 1;
    public static final String KEY_FILE = "File";
    public static final String KEY_ID = "_id";
    public static final String KEY_MODEL = "Model";
    public static final String KEY_NAME = "Name";
    public static final String KEY_PHOTO = "Photo";
    public static final String TABLE_RECENTLY = "recently";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("create table recently(_id integer primary key,Name text,Photo text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        sQLiteDatabase.execSQL("drop table if exists recently");
        onCreate(sQLiteDatabase);
    }
}
