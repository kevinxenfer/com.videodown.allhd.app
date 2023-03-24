package com.videodown.allhd.app.Activity.Other;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.videodown.allhd.app.R;


public class PreferenceUtil {
    public static final String AUTOPLAYNEXT = "autoplayon";
    public static final String BAND_LEVEL = "level";
    public static final String BASS_BOOST = "BassBoost";
    public static final String BATTERYLOCK = "batterylock";
    private static final String BLOCKADS = "block_ads";
    public static final String EQSWITCH = "eqswitch";
    private static final String FOLDER_SORT_ORDER = "folder_sort_order";
    private static final String FOLDER_VIEW_TYPE = "folder_view_type";
    public static final int GAIN_MAX = 100;
    public static final String GENERAL_THEME = "general_theme";
    private static final String LAST_BRIGHTNESS = "last_brightness";
    public static final String LAST_SLEEP_TIMER_VALUE = "last_sleep_timer_value";
    private static final String LAST_SPEED = "last_speed";
    private static final String LOCK = "lock";
    private static final String LOCK_VIDEO = "lock_lock";
    public static final String LOUD_BOOST = "Loud";
    private static final String List_VIEW_TYPE = "list_view_type";
    public static final String NEXT_SLEEP_TIMER_ELAPSED_REALTIME = "next_sleep_timer_elapsed_real_time";
    private static final String ORIENTATION = "orientation";
    private static final String PIN_LOCK = "pin_lock";
    public static final String PRESET_POS = "spinner_position";
    private static final String RECYCLE_VIDEO = "recycle_lock";
    private static final String REPEAT_ONE_VIDEO = "repeat_one";
    private static final String RESBOOl = "resumebool";
    private static final String RESUMEVID = "resumevideo";
    public static final String SAVE_EQ = "Equalizers";
    public static final String SAVE_PRESET = "preset";
    public static final String SLEEP_TIMER_FINISH_SONG = "sleep_timer_finish_music";
    private static final String SORT_ORDER = "sort_order";
    private static final String THEME = "theme";
    public static final String VIDEOPOSITION = "videoposition";
    public static final String VIDEOURL = "videourl";
    private static final String VIEW_TYPE = "view_type";
    public static final String VIRTUAL_BOOST = "VirtualBoost";
    private static SharedPreferences eqPref;
    private static SharedPreferences mPreferences;
    private static PreferenceUtil sInstance;

    public PreferenceUtil(Context context) {
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static PreferenceUtil getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PreferenceUtil(context.getApplicationContext());
        }
        return sInstance;
    }

    public static PreferenceUtil getInstance() {
        return sInstance;
    }

    public void saveLastBrightness(float f) {
        mPreferences.edit().putFloat(LAST_BRIGHTNESS, f).apply();
    }

    public void setAutoplaynext(Boolean bool) {
        mPreferences.edit().putBoolean(AUTOPLAYNEXT, bool.booleanValue()).apply();
    }

    public void setBatterylock(Boolean bool) {
        mPreferences.edit().putBoolean(BATTERYLOCK, bool.booleanValue()).apply();
    }

    public void setBlockads(Boolean bool) {
        mPreferences.edit().putBoolean(BLOCKADS, bool.booleanValue()).apply();
    }

    public boolean getBlockads() {
        return mPreferences.getBoolean(BLOCKADS, true);
    }

    public boolean getAutoplaynext() {
        return mPreferences.getBoolean(AUTOPLAYNEXT, true);
    }

    public boolean getBatterylock() {
        return mPreferences.getBoolean(BATTERYLOCK, true);
    }

    public float getLastBrightness() {
        return mPreferences.getFloat(LAST_BRIGHTNESS, 0.5f);
    }

    public void saveLastSpeed(float f) {
        mPreferences.edit().putFloat(LAST_SPEED, f).apply();
    }

    public float getLastSpeed() {
        return mPreferences.getFloat(LAST_SPEED, 1.0f);
    }

    public void saveSortOrder(int i) {
        mPreferences.edit().putInt(SORT_ORDER, i).apply();
    }

    public int getSortOrder() {
        return mPreferences.getInt(SORT_ORDER, 0);
    }

    public void saveOrientation(int i) {
        mPreferences.edit().putInt(ORIENTATION, i).apply();
    }

    public int getOrientation() {
        return mPreferences.getInt(ORIENTATION, 2);
    }

    public int getResumestatus() {
        return mPreferences.getInt(RESUMEVID, 2);
    }

    public void saveResumestatus(int i) {
        mPreferences.edit().putInt(RESUMEVID, i).apply();
    }

    public void saveFolderSortOrder(int i) {
        mPreferences.edit().putInt(FOLDER_SORT_ORDER, i).apply();
    }

    public int getFolderSortOrder() {
        return mPreferences.getInt(FOLDER_SORT_ORDER, 0);
    }

    public void saveFolderAsc(Boolean bool) {
        mPreferences.edit().putBoolean(FOLDER_VIEW_TYPE, bool.booleanValue()).apply();
    }

    public void saveResumBool(Boolean bool) {
        mPreferences.edit().putBoolean(RESBOOl, bool.booleanValue()).apply();
    }

    public boolean getResumBool() {
        return mPreferences.getBoolean(RESBOOl, true);
    }

    public boolean getFolderAsc() {
        return mPreferences.getBoolean(FOLDER_VIEW_TYPE, true);
    }

    public void saveListAsc(Boolean bool) {
        mPreferences.edit().putBoolean(List_VIEW_TYPE, bool.booleanValue()).apply();
    }

    public boolean getListAsc() {
        return mPreferences.getBoolean(List_VIEW_TYPE, true);
    }

    public void saveRepeatOne(Boolean bool) {
        mPreferences.edit().putBoolean(REPEAT_ONE_VIDEO, bool.booleanValue()).apply();
    }

    public boolean getRepeatOne() {
        return mPreferences.getBoolean(REPEAT_ONE_VIDEO, false);
    }

    public void saveViewType(Boolean bool) {
        mPreferences.edit().putBoolean(VIEW_TYPE, bool.booleanValue()).apply();
    }

    public boolean getViewType() {
        return mPreferences.getBoolean(VIEW_TYPE, true);
    }

    public void setTheme(int i) {
        mPreferences.edit().putInt(THEME, i).apply();
    }

    public int getTheme() {
        return mPreferences.getInt(THEME, 11);
    }

    public void setLock(Boolean bool) {
        mPreferences.edit().putBoolean(LOCK, bool.booleanValue()).apply();
    }

    public boolean getLock() {
        return mPreferences.getBoolean(LOCK, false);
    }

    public void setVideoURL(String str) {
        mPreferences.edit().putString(VIDEOURL, str).apply();
    }

    public String getVideoURL() {
        return mPreferences.getString(VIDEOURL, "");
    }

    public void setLockVideo(String str) {
        mPreferences.edit().putString(LOCK_VIDEO, str).apply();
    }

    public void setRecycleVideo(String str) {
        mPreferences.edit().putString(RECYCLE_VIDEO, str).apply();
    }

    public String getLockVideo() {
        return mPreferences.getString(LOCK_VIDEO, "");
    }

    public String getRecycleVideo() {
        return mPreferences.getString(RECYCLE_VIDEO, "");
    }

    public void setVideoPosition(int i) {
        mPreferences.edit().putInt(VIDEOPOSITION, i).apply();
    }

    public int getVideoPosition() {
        return mPreferences.getInt(VIDEOPOSITION, 0);
    }

    public SharedPreferences saveEq() {
        return getEqPref();
    }

    public static SharedPreferences getEqPref() {
        return eqPref;
    }

    public void eqSwitch(Boolean bool) {
        SharedPreferences.Editor edit = saveEq().edit();
        edit.putBoolean(EQSWITCH, bool.booleanValue());
        edit.apply();
    }

    public boolean geteqSwitch() {
        return saveEq().getBoolean(EQSWITCH, false);
    }

    public int getPresetPos() {
        return getmPreferences().getInt(PRESET_POS, 0);
    }

    public void savePresetPos(int i) {
        SharedPreferences.Editor edit = getmPreferences().edit();
        edit.putInt(PRESET_POS, i);
        edit.apply();
    }

    public static SharedPreferences getmPreferences() {
        return mPreferences;
    }

    public void setResumeVideotime(Context context, Long l, String str) {
        SharedPreferences.Editor edit = mPreferences.edit();
        edit.putLong("r_" + str, l.longValue());
        edit.apply();
    }

    public long getresumeVideotime(Context context, String str) {
        SharedPreferences sharedPreferences = mPreferences;
        return sharedPreferences.getLong("r_" + str, 0);
    }

    public void setIsPlayVideo(Context context, Boolean bool, String str) {
        SharedPreferences.Editor edit = mPreferences.edit();
        edit.putBoolean("isplay" + str, bool.booleanValue());
        edit.apply();
    }

    public boolean getIsPlayVideo(Context context, String str) {
        SharedPreferences sharedPreferences = mPreferences;
        return sharedPreferences.getBoolean("isplay" + str, false);
    }

    public String getPinLock() {
        return mPreferences.getString(PIN_LOCK, "");
    }

    public void setVideoPosition(String str) {
        mPreferences.edit().putString(PIN_LOCK, str).apply();
    }

    public int getGeneralTheme() {
        return getThemeResFromPrefValue(mPreferences.getString(GENERAL_THEME, "light"));
    }

    public int getGeneralTheme1() {
        return getThemeResFromPrefValue1(mPreferences.getString(GENERAL_THEME, "light"));
    }

    public static int getThemeResFromPrefValue1(String str) {
        str.hashCode();
        return str.equals("dark") ? 1 : 0;
    }

    public void setGeneralTheme(String str) {
        SharedPreferences.Editor edit = mPreferences.edit();
        edit.putString(GENERAL_THEME, str);
        edit.apply();
    }

    public static int getThemeResFromPrefValue(String str) {
        str.hashCode();
        return R.style.Theme_Phonograph;
    }

    public int getLastSleepTimerValue() {
        return mPreferences.getInt(LAST_SLEEP_TIMER_VALUE, 30);
    }

    public void setLastSleepTimerValue(int i) {
        SharedPreferences.Editor edit = mPreferences.edit();
        edit.putInt(LAST_SLEEP_TIMER_VALUE, i);
        edit.apply();
    }

    public boolean getSleepTimerFinishMusic() {
        return mPreferences.getBoolean(SLEEP_TIMER_FINISH_SONG, false);
    }

    public void setSleepTimerFinishMusic(boolean z) {
        SharedPreferences.Editor edit = mPreferences.edit();
        edit.putBoolean(SLEEP_TIMER_FINISH_SONG, z);
        edit.apply();
    }

    public long getNextSleepTimerElapsedRealTime() {
        return mPreferences.getLong(NEXT_SLEEP_TIMER_ELAPSED_REALTIME, -1);
    }

    public void setNextSleepTimerElapsedRealtime(long j) {
        SharedPreferences.Editor edit = mPreferences.edit();
        edit.putLong(NEXT_SLEEP_TIMER_ELAPSED_REALTIME, j);
        edit.apply();
    }
}
