package com.videodown.allhd.app.AUtils.XAWebFB;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBDownloadManager extends SQLiteOpenHelper {

    public static final String DBNAME = "freevideodownloader.db";
    public static final String DOWNLOAD_QUE_TABLE = "queDownloadFileDetails";
    public static final String DOWNLOAD_TABLE = "downloadFileDetails";

    public static final String f3ID = "_id";
    public static final String KEY_CURRENT_SIZE = "key_current_size";
    public static final String KEY_DOWNLOAD_ID = "key_down_id";
    public static final String KEY_DOWNLOAD_PERCENT = "key_percent";
    public static final String KEY_DOWNLOAD_PROGRESS = "key_progress";
    public static final String KEY_DOWNLOAD_SPEED = "key_speed";
    public static final String KEY_FILE_LOCATION = "key_file_location";
    public static final String KEY_ICON = "key_icon";
    public static final String KEY_IS_IN_PAUSE = "key_is_in_pause";
    public static final String KEY_LAST_MODIFICATION = "key_last_modification";
    public static final String KEY_NAME = "key_name";
    public static final String KEY_TOTAL_SIZE = "key_total_size";
    public static final String KEY_URL = "key_url";
    public static final int VERSION = 1;
    private static DBDownloadManager instance;

    public DBDownloadManager(Context context) {
        super(context, DBNAME, (SQLiteDatabase.CursorFactory) null, 1);
    }

    public static synchronized DBDownloadManager getInstance(Context context) {
        DBDownloadManager dBDownloadManager;
        synchronized (DBDownloadManager.class) {
            if (instance == null) {
                instance = new DBDownloadManager(context);
            }
            dBDownloadManager = instance;
        }
        return dBDownloadManager;
    }

    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE if not exists downloadFileDetails ( _id INTEGER PRIMARY KEY AUTOINCREMENT, key_name TEXT, key_icon TEXT, key_url TEXT, key_down_id NUMERIC, key_total_size NUMERIC, key_current_size NUMERIC, key_percent NUMERIC, key_progress NUMERIC, key_speed NUMERIC, key_is_in_pause TEXT, key_file_location TEXT, key_last_modification TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE if not exists queDownloadFileDetails ( _id INTEGER PRIMARY KEY AUTOINCREMENT, key_name TEXT, key_icon TEXT, key_url TEXT, key_down_id NUMERIC, key_total_size NUMERIC, key_current_size NUMERIC, key_percent NUMERIC, key_progress NUMERIC, key_speed NUMERIC, key_is_in_pause TEXT, key_file_location TEXT, key_last_modification TEXT)");
    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXIST downloadFileDetails");
        sqLiteDatabase.execSQL("DROP TABLE IF EXIST queDownloadFileDetails");
        onCreate(sqLiteDatabase);
    }

    public void addUpdateDownloadData(int downloadId, DownloadingItem item, boolean isUpdate) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_NAME, item.getName());
            contentValues.put(KEY_ICON, item.getIcon());
            contentValues.put(KEY_URL, item.getUrl());
            contentValues.put(KEY_DOWNLOAD_ID, Integer.valueOf(item.getDownloadId()));
            contentValues.put(KEY_TOTAL_SIZE, Double.valueOf(item.getTotalSize()));
            contentValues.put(KEY_CURRENT_SIZE, Double.valueOf(item.getCurrentSize()));
            contentValues.put(KEY_DOWNLOAD_PERCENT, Integer.valueOf(item.getPercent()));
            contentValues.put(KEY_DOWNLOAD_PROGRESS, Integer.valueOf(item.getProgress()));
            contentValues.put(KEY_DOWNLOAD_SPEED, Integer.valueOf(item.getSpeed()));
            contentValues.put(KEY_IS_IN_PAUSE, Integer.valueOf(item.getIsInPause()));
            contentValues.put(KEY_FILE_LOCATION, item.getLocalFilePath());
            contentValues.put(KEY_LAST_MODIFICATION, item.getLastModification());
            if (isUpdate) {
                sqLiteDatabase.update(DOWNLOAD_TABLE, contentValues, "key_down_id=?", new String[]{"" + downloadId});
                return;
            }
            sqLiteDatabase.insert(DOWNLOAD_TABLE, (String) null, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteDownloadData(long downloadId) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        try {
            sqLiteDatabase.delete(DOWNLOAD_TABLE, "key_down_id=?", new String[]{"" + downloadId});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("Range")
    public List<DownloadingItem> getDownloadingData() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        List<DownloadingItem> itemList = new ArrayList<>();
        try {
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM downloadFileDetails WHERE key_down_id!=0", (String[]) null);
            if (cursor == null || cursor.getCount() <= 0) {
                if (cursor != null && !cursor.isClosed()) {
                    cursor.close();
                }
                return itemList;
            }
            cursor.moveToFirst();
            do {
                itemList.add(new DownloadingItem(cursor.getString(cursor.getColumnIndex(KEY_NAME)), cursor.getString(cursor.getColumnIndex(KEY_ICON)), cursor.getString(cursor.getColumnIndex(KEY_URL)), cursor.getInt(cursor.getColumnIndex(KEY_DOWNLOAD_ID)), (double) cursor.getInt(cursor.getColumnIndex(KEY_TOTAL_SIZE)), (double) cursor.getInt(cursor.getColumnIndex(KEY_CURRENT_SIZE)), cursor.getInt(cursor.getColumnIndex(KEY_DOWNLOAD_PERCENT)), cursor.getInt(cursor.getColumnIndex(KEY_DOWNLOAD_PROGRESS)), cursor.getInt(cursor.getColumnIndex(KEY_DOWNLOAD_SPEED)), cursor.getInt(cursor.getColumnIndex(KEY_IS_IN_PAUSE)), cursor.getString(cursor.getColumnIndex(KEY_FILE_LOCATION)), cursor.getString(cursor.getColumnIndex(KEY_LAST_MODIFICATION))));
            } while (cursor.moveToNext());
            cursor.close();
            return itemList;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList();
        }
    }

    @SuppressLint("Range")
    public List<DownloadingItem> getDownloadedData() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        List<DownloadingItem> itemList = new ArrayList<>();
        try {
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM downloadFileDetails WHERE key_down_id=0", (String[]) null);
            if (cursor == null || cursor.getCount() <= 0) {
                if (cursor != null && !cursor.isClosed()) {
                    cursor.close();
                }
                return itemList;
            }
            cursor.moveToFirst();
            do {
                itemList.add(new DownloadingItem(cursor.getString(cursor.getColumnIndex(KEY_NAME)), cursor.getString(cursor.getColumnIndex(KEY_ICON)), cursor.getString(cursor.getColumnIndex(KEY_URL)), cursor.getInt(cursor.getColumnIndex(KEY_DOWNLOAD_ID)), (double) cursor.getInt(cursor.getColumnIndex(KEY_TOTAL_SIZE)), (double) cursor.getInt(cursor.getColumnIndex(KEY_CURRENT_SIZE)), cursor.getInt(cursor.getColumnIndex(KEY_DOWNLOAD_PERCENT)), cursor.getInt(cursor.getColumnIndex(KEY_DOWNLOAD_PROGRESS)), cursor.getInt(cursor.getColumnIndex(KEY_DOWNLOAD_SPEED)), cursor.getInt(cursor.getColumnIndex(KEY_IS_IN_PAUSE)), cursor.getString(cursor.getColumnIndex(KEY_FILE_LOCATION)), cursor.getString(cursor.getColumnIndex(KEY_LAST_MODIFICATION))));
            } while (cursor.moveToNext());
            cursor.close();
            return itemList;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList();
        }
    }

    @SuppressLint("Range")
    public List<DownloadingItem> getQuedDownloadData() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        List<DownloadingItem> itemList = new ArrayList<>();
        try {
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM downloadFileDetails WHERE key_is_in_pause=2", (String[]) null);
            if (cursor == null || cursor.getCount() <= 0) {
                if (cursor != null && !cursor.isClosed()) {
                    cursor.close();
                }
                return itemList;
            }
            cursor.moveToFirst();
            do {
                itemList.add(new DownloadingItem(cursor.getString(cursor.getColumnIndex(KEY_NAME)), cursor.getString(cursor.getColumnIndex(KEY_ICON)), cursor.getString(cursor.getColumnIndex(KEY_URL)), cursor.getInt(cursor.getColumnIndex(KEY_DOWNLOAD_ID)), (double) cursor.getInt(cursor.getColumnIndex(KEY_TOTAL_SIZE)), (double) cursor.getInt(cursor.getColumnIndex(KEY_CURRENT_SIZE)), cursor.getInt(cursor.getColumnIndex(KEY_DOWNLOAD_PERCENT)), cursor.getInt(cursor.getColumnIndex(KEY_DOWNLOAD_PROGRESS)), cursor.getInt(cursor.getColumnIndex(KEY_DOWNLOAD_SPEED)), cursor.getInt(cursor.getColumnIndex(KEY_IS_IN_PAUSE)), cursor.getString(cursor.getColumnIndex(KEY_FILE_LOCATION)), cursor.getString(cursor.getColumnIndex(KEY_LAST_MODIFICATION))));
            } while (cursor.moveToNext());
            cursor.close();
            return itemList;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList();
        }
    }

    @SuppressLint("Range")
    public DownloadingItem getDownloadingDataById(int downloadId) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        DownloadingItem downloadingItem = new DownloadingItem();
        try {
            if (!sqLiteDatabase.isOpen()) {
                sqLiteDatabase = getReadableDatabase();
            }
            String[] strArr = new String[1];
            StringBuilder sb = new StringBuilder();
            sb.append("");
            try {
                sb.append(downloadId);
                strArr[0] = sb.toString();
                Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM downloadFileDetails WHERE key_down_id=?", strArr);
                if (cursor == null || cursor.getCount() <= 0) {
                    if (cursor != null && !cursor.isClosed()) {
                        cursor.close();
                    }
                    return downloadingItem;
                }
                cursor.moveToFirst();
                do {
                    downloadingItem = new DownloadingItem(cursor.getString(cursor.getColumnIndex(KEY_NAME)), cursor.getString(cursor.getColumnIndex(KEY_ICON)), cursor.getString(cursor.getColumnIndex(KEY_URL)), cursor.getInt(cursor.getColumnIndex(KEY_DOWNLOAD_ID)), (double) cursor.getInt(cursor.getColumnIndex(KEY_TOTAL_SIZE)), (double) cursor.getInt(cursor.getColumnIndex(KEY_CURRENT_SIZE)), cursor.getInt(cursor.getColumnIndex(KEY_DOWNLOAD_PERCENT)), cursor.getInt(cursor.getColumnIndex(KEY_DOWNLOAD_PROGRESS)), cursor.getInt(cursor.getColumnIndex(KEY_DOWNLOAD_SPEED)), cursor.getInt(cursor.getColumnIndex(KEY_IS_IN_PAUSE)), cursor.getString(cursor.getColumnIndex(KEY_FILE_LOCATION)), cursor.getString(cursor.getColumnIndex(KEY_LAST_MODIFICATION)));
                } while (cursor.moveToNext());
                cursor.close();
                return downloadingItem;
            } catch (Exception e) {
                e = e;
                e.printStackTrace();
                return downloadingItem;
            }
        } catch (Exception e2) {
//            e = e2;
            int i = downloadId;
            e2.printStackTrace();
            return downloadingItem;
        }
    }

    @SuppressLint("Range")
    public List<DownloadingItem> getDownloadedDataById(int downloadId) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        List<DownloadingItem> itemList = new ArrayList<>();
        try {
            String[] strArr = new String[1];
            StringBuilder sb = new StringBuilder();
            sb.append("");
            try {
                sb.append(downloadId);
                strArr[0] = sb.toString();
                Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM downloadFileDetails WHERE key_down_id=?", strArr);
                if (cursor == null || cursor.getCount() <= 0) {
                    if (cursor != null && !cursor.isClosed()) {
                        cursor.close();
                    }
                    return itemList;
                }
                cursor.moveToFirst();
                do {
                    itemList.add(new DownloadingItem(cursor.getString(cursor.getColumnIndex(KEY_NAME)), cursor.getString(cursor.getColumnIndex(KEY_ICON)), cursor.getString(cursor.getColumnIndex(KEY_URL)), cursor.getInt(cursor.getColumnIndex(KEY_DOWNLOAD_ID)), (double) cursor.getInt(cursor.getColumnIndex(KEY_TOTAL_SIZE)), (double) cursor.getInt(cursor.getColumnIndex(KEY_CURRENT_SIZE)), cursor.getInt(cursor.getColumnIndex(KEY_DOWNLOAD_PERCENT)), cursor.getInt(cursor.getColumnIndex(KEY_DOWNLOAD_PROGRESS)), cursor.getInt(cursor.getColumnIndex(KEY_DOWNLOAD_SPEED)), cursor.getInt(cursor.getColumnIndex(KEY_IS_IN_PAUSE)), cursor.getString(cursor.getColumnIndex(KEY_FILE_LOCATION)), cursor.getString(cursor.getColumnIndex(KEY_LAST_MODIFICATION))));
                } while (cursor.moveToNext());
                cursor.close();
                return itemList;
            } catch (Exception e) {
                e = e;
                e.printStackTrace();
                return new ArrayList();
            }
        } catch (Exception e2) {
//            e = e2;
            int i = downloadId;
            e2.printStackTrace();
            return new ArrayList();
        }
    }

    @SuppressLint("Range")
    public DownloadingItem getItemByFilePath(String filePath) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        DownloadingItem downloadingItem = new DownloadingItem();
        try {
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM downloadFileDetails WHERE key_file_location=?", new String[]{filePath});
            if (cursor == null || cursor.getCount() <= 0) {
                if (cursor != null && !cursor.isClosed()) {
                    cursor.close();
                }
                return downloadingItem;
            }
            cursor.moveToFirst();
            do {
                downloadingItem = new DownloadingItem(cursor.getString(cursor.getColumnIndex(KEY_NAME)), cursor.getString(cursor.getColumnIndex(KEY_ICON)), cursor.getString(cursor.getColumnIndex(KEY_URL)), cursor.getInt(cursor.getColumnIndex(KEY_DOWNLOAD_ID)), (double) cursor.getInt(cursor.getColumnIndex(KEY_TOTAL_SIZE)), (double) cursor.getInt(cursor.getColumnIndex(KEY_CURRENT_SIZE)), cursor.getInt(cursor.getColumnIndex(KEY_DOWNLOAD_PERCENT)), cursor.getInt(cursor.getColumnIndex(KEY_DOWNLOAD_PROGRESS)), cursor.getInt(cursor.getColumnIndex(KEY_DOWNLOAD_SPEED)), cursor.getInt(cursor.getColumnIndex(KEY_IS_IN_PAUSE)), cursor.getString(cursor.getColumnIndex(KEY_FILE_LOCATION)), cursor.getString(cursor.getColumnIndex(KEY_LAST_MODIFICATION)));
            } while (cursor.moveToNext());
            cursor.close();
            return downloadingItem;
        } catch (Exception e) {
            e.printStackTrace();
            return downloadingItem;
        }
    }

    public int getCurrentDownloadingCount() {
        try {
            Cursor mCount = getReadableDatabase().rawQuery("select count(*) from downloadFileDetails where key_down_id!=0 and key_is_in_pause==0", (String[]) null);
            mCount.moveToFirst();
            int count = mCount.getInt(0);
            mCount.close();
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int getTotalDownloadingCount() {
        try {
            Cursor mCount = getReadableDatabase().rawQuery("select count(*) from downloadFileDetails where key_down_id!=0 and (key_is_in_pause==0 or key_is_in_pause==2)", (String[]) null);
            mCount.moveToFirst();
            int count = mCount.getInt(0);
            mCount.close();
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void addToQueDownload(DownloadingItem item) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_NAME, item.getName());
            contentValues.put(KEY_ICON, item.getIcon());
            contentValues.put(KEY_URL, item.getUrl());
            contentValues.put(KEY_DOWNLOAD_ID, Integer.valueOf(item.getDownloadId()));
            contentValues.put(KEY_TOTAL_SIZE, Double.valueOf(item.getTotalSize()));
            contentValues.put(KEY_CURRENT_SIZE, Double.valueOf(item.getCurrentSize()));
            contentValues.put(KEY_DOWNLOAD_PERCENT, Integer.valueOf(item.getPercent()));
            contentValues.put(KEY_DOWNLOAD_PROGRESS, Integer.valueOf(item.getProgress()));
            contentValues.put(KEY_DOWNLOAD_SPEED, Integer.valueOf(item.getSpeed()));
            contentValues.put(KEY_IS_IN_PAUSE, Integer.valueOf(item.getIsInPause()));
            contentValues.put(KEY_FILE_LOCATION, item.getLocalFilePath());
            contentValues.put(KEY_LAST_MODIFICATION, item.getLastModification());
            sqLiteDatabase.insert(DOWNLOAD_TABLE, (String) null, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteDownloadData(int downloadId) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        try {
            sqLiteDatabase.delete(DOWNLOAD_TABLE, "key_down_id=?", new String[]{"" + downloadId});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteDownloadDataByFilePath(String filePath) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        try {
            sqLiteDatabase.delete(DOWNLOAD_TABLE, "key_file_location=?", new String[]{"" + filePath});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isUrlContains(String url) {
        try {
            Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM downloadFileDetails WHERE key_url=?", new String[]{url});
            return cursor != null && cursor.getCount() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
