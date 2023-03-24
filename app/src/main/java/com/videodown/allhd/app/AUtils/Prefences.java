package com.videodown.allhd.app.AUtils;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefences {
    public static final String WA_TREE_URI = "wa_tree_uri";
    public static final String WB_TREE_URI = "wb_tree_uri";
    private static SharedPreferences mPreferences;

    public static SharedPreferences getInstance(Context context) {
        if (mPreferences == null) {
            mPreferences = context.getApplicationContext().getSharedPreferences("wa_data", 0);
        }
        return mPreferences;
    }

    public static String getLang(Context context) {
        return getInstance(context).getString("language", AppLangSessionManager.KEY_APP_LANGUAGE);
    }

    public static void setWATree(Context context, String str) {
        getInstance(context).edit().putString("wa_tree_uri", str).apply();
    }

    public static String getWATree(Context context) {
        return getInstance(context).getString("wa_tree_uri", "");
    }

    public static void setWBTree(Context context, String str) {
        getInstance(context).edit().putString("wb_tree_uri", str).apply();
    }

    public static String getWBTree(Context context) {
        return getInstance(context).getString("wb_tree_uri", "");
    }

    public static void setadmobid(Context context, String str, String str2) {
        getInstance(context).edit().putString(str, str2).apply();
    }

    public static String getadmobid(Context context, String str) {
        return getInstance(context).getString(str, "");
    }
}
