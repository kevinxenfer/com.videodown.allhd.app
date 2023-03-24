package com.videodown.allhd.app.AUtils.XAWebFB;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class AllDownloaderSharedpref {
    public static final String HISTORY = "history";
    public static final String PREF_NAME = "pref";
    public Context context;
    public SharedPreferences pref;
    public SharedPreferences.Editor editor;

    public AllDownloaderSharedpref(Context context2) {
        this.context = context2;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveHistoryList(String scoreString) {
        this.editor.putString(HISTORY, scoreString);
        this.editor.commit();
    }

    public void putString(String key, String scoreString) {
        this.editor.putString(key, scoreString);
        this.editor.commit();
    }

    public void putBoolean(String key, boolean value) {
        this.editor.putBoolean(key, value);
        this.editor.commit();
    }

    public void putInt(String key, int scoreString) {
        this.editor.putInt(key, scoreString);
        this.editor.commit();
    }

    public String getHistoryList() {
        return this.pref.getString(HISTORY, "");
    }

    public String getString(String key) {
        return this.pref.getString(key, "");
    }

    public boolean getBoolean(String key) {
        return this.pref.getBoolean(key, false);
    }

    public int getInt(String key) {
        return this.pref.getInt(key, 0);
    }

    public void clear() {
        this.pref.edit().clear().apply();
    }

    public void putListString(String key, List<String> stringList) {
        checkForNullKey(key);
        this.pref.edit().putString(key, TextUtils.join("‚‗‚", (String[]) stringList.toArray(new String[stringList.size()]))).apply();
    }

    public void checkForNullKey(String key) {
        if (key == null) {
            throw new NullPointerException();
        }
    }

    public void checkForNullValue(String value) {
        if (value == null) {
            throw new NullPointerException();
        }
    }

    public List<String> getListString(String key) {
        return new ArrayList(Arrays.asList(TextUtils.split(this.pref.getString(key, ""), "‚‗‚")));
    }

    public void remove(String key) {
        this.pref.edit().remove(key).apply();
    }

}
