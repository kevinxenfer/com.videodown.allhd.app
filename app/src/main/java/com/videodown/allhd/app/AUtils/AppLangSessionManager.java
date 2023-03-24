package com.videodown.allhd.app.AUtils;

import android.content.Context;
import android.content.SharedPreferences;

public class AppLangSessionManager {
    public static final String KEY_APP_LANGUAGE = "en";
    private static final String PREFER_NAME = "AppLangPref";
    Context _context;
    SharedPreferences.Editor editor;
    SharedPreferences pref;

    public AppLangSessionManager(Context context) {
        this._context = context;
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFER_NAME, 0);
        this.pref = sharedPreferences;
        this.editor = sharedPreferences.edit();
    }

    public String getLanguage() {
        return this.pref.getString(KEY_APP_LANGUAGE, "");
    }
}
