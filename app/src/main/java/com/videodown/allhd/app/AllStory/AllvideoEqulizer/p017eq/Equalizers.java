package com.videodown.allhd.app.AllStory.AllvideoEqulizer.p017eq;

import android.content.SharedPreferences;
import android.media.audiofx.Equalizer;

import com.videodown.allhd.app.Activity.Other.PreferenceUtil;

import org.apache.commons.lang3.StringUtils;

public class Equalizers {
    private static Equalizer hdvideoplayer_equalizer;
    private static short hdvideoplayer_preset;

    public static void initEq(int i) {
        EndEq();
        try {
            hdvideoplayer_equalizer = new Equalizer(0, i);
            short s = (short) PreferenceUtil.getInstance().saveEq().getInt(PreferenceUtil.SAVE_PRESET, 0);
            hdvideoplayer_preset = s;
            if (s < hdvideoplayer_equalizer.getNumberOfPresets()) {
                usePreset(hdvideoplayer_preset);
                return;
            }
            for (short s2 = 0; s2 < hdvideoplayer_equalizer.getNumberOfBands(); s2 = (short) (s2 + 1)) {
                SharedPreferences saveEq = PreferenceUtil.getInstance().saveEq();
                setBandLevel(s2, (short) saveEq.getInt(PreferenceUtil.BAND_LEVEL + s2, 0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void EndEq() {
        Equalizer equalizer = hdvideoplayer_equalizer;
        if (equalizer != null) {
            equalizer.release();
            hdvideoplayer_equalizer = null;
        }
    }

    public static short[] getBandLevelRange() {
        Equalizer equalizer = hdvideoplayer_equalizer;
        if (equalizer != null) {
            return equalizer.getBandLevelRange();
        }
        return null;
    }

    public static short getPresetNo() {
        Equalizer equalizer = hdvideoplayer_equalizer;
        if (equalizer != null) {
            return equalizer.getNumberOfPresets();
        }
        return 0;
    }

    public static String getPresetNames(short s) {
        Equalizer equalizer = hdvideoplayer_equalizer;
        return equalizer != null ? equalizer.getPresetName(s) : StringUtils.SPACE;
    }

    public static short getBandLevel(short s) {
        Equalizer equalizer = hdvideoplayer_equalizer;
        if (equalizer == null) {
            return 0;
        }
        return equalizer.getBandLevel(s);
    }

    public static void setEnabled(boolean z) {
        Equalizer equalizer = hdvideoplayer_equalizer;
        if (equalizer != null) {
            equalizer.setEnabled(z);
        }
    }

    public static void setBandLevel(short s, short s2) {
        Equalizer equalizer = hdvideoplayer_equalizer;
        if (equalizer != null) {
            equalizer.setBandLevel(s, s2);
        }
    }

    public static void usePreset(short s) {
        Equalizer equalizer = hdvideoplayer_equalizer;
        if (equalizer != null && s >= 0) {
            try {
                hdvideoplayer_preset = s;
                equalizer.usePreset(s);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static short getNumberOfBands() {
        Equalizer equalizer = hdvideoplayer_equalizer;
        if (equalizer != null) {
            return equalizer.getNumberOfBands();
        }
        return 0;
    }

    public static int getCenterFreq(short s) {
        Equalizer equalizer = hdvideoplayer_equalizer;
        if (equalizer != null) {
            return equalizer.getCenterFreq(s);
        }
        return 0;
    }

    public static void savePrefs(int i, int i2) {
        if (hdvideoplayer_equalizer != null) {
            SharedPreferences.Editor edit = PreferenceUtil.getInstance().saveEq().edit();
            edit.putInt(PreferenceUtil.BAND_LEVEL + i, i2);
            edit.apply();
        }
    }
}
