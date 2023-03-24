package com.videodown.allhd.app.AllStory.AllvideodsModel;

import android.content.Context;
import android.content.SharedPreferences;


public class PrefShar {


    public static final String PREF_ADMOB_BANNER_ID = "PREF_ADMOB_BANNER_ID";
    public static final String PREF_ADMOB_INTER_ID = "PREF_ADMOB_INTER_ID";
    public static final String PREF_ADMOB_OPEN_ID = "PREF_ADMOB_OPEN_ID";
    public static final String PREF_ADMOB_NATIVE_ID = "PREF_ADMOB_NATIVE_ID";
    public static final String PREF_ADMOB_REWARD_ID = "PREF_ADMOB_REWARD_ID";
    public static final String PREF_APP_NAME_PREF = "PhototranslateData";
    public static final String TOKEN_USER = "TOKEN_USER";

    public static final String PREF_Country = "Country";
    public static final String PREF_Block_Country = "blockCountry";
    public static final String PREF_CARRIER_ID = "videoEditorBaseCarrierId";
    public static final String PREF_BASE_HOST = "videoEditorBestHost";

    private static PrefShar instance;
    public SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    public PrefShar(Context context) {
        instance = this;
        sharedPreferences = context.getSharedPreferences(PREF_APP_NAME_PREF, Context.MODE_PRIVATE);
    }

    public static PrefShar getPrefsHelper() {
        return instance;
    }


    public void delete(String key) {
        if (sharedPreferences.contains(key)) {
            editor.remove(key).commit();
        }
    }


    public void setData(String key, Object value) {

        editor = sharedPreferences.edit();
        delete(key);
        if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Enum) {
            editor.putString(key, value.toString());
        } else if (value != null) {
            throw new RuntimeException("Attempting to save non-primitive preference");
        }
        editor.commit();
    }

    @SuppressWarnings("unchecked")
    public <T> T getPref(String key) {
        return (T) sharedPreferences.getAll().get(key);
    }




}



