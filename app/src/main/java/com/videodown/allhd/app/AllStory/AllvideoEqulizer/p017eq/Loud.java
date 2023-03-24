package com.videodown.allhd.app.AllStory.AllvideoEqulizer.p017eq;

import android.content.SharedPreferences;
import android.media.audiofx.LoudnessEnhancer;

import com.videodown.allhd.app.Activity.Other.PreferenceUtil;

public class Loud {
    private static LoudnessEnhancer hdvideoplayer_loudnessEnhancer;

    public static void initLoudnessEnhancer(int i) {
        EndLoudnessEnhancer();
        try {
            hdvideoplayer_loudnessEnhancer = new LoudnessEnhancer(i);
            int i2 = PreferenceUtil.getInstance().saveEq().getInt(PreferenceUtil.LOUD_BOOST, 0);
            if (i2 > 0) {
                setLoudnessEnhancerGain(i2);
            } else {
                setLoudnessEnhancerGain(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setLoudnessEnhancerGain(int i) {
        LoudnessEnhancer loudnessEnhancer = hdvideoplayer_loudnessEnhancer;
        if (loudnessEnhancer != null && i >= 0 && i <= 100) {
            try {
                loudnessEnhancer.setTargetGain(i);
                saveLoudnessEnhancer(i);
            } catch (RuntimeException unused) {
            }
        }
    }

    public static void EndLoudnessEnhancer() {
        LoudnessEnhancer loudnessEnhancer = hdvideoplayer_loudnessEnhancer;
        if (loudnessEnhancer != null) {
            loudnessEnhancer.release();
            hdvideoplayer_loudnessEnhancer = null;
        }
    }

    public static void saveLoudnessEnhancer(int i) {
        SharedPreferences.Editor edit = PreferenceUtil.getInstance().saveEq().edit();
        edit.putInt(PreferenceUtil.LOUD_BOOST, i);
        edit.apply();
    }

    public static void setEnabled(boolean z) {
        LoudnessEnhancer loudnessEnhancer = hdvideoplayer_loudnessEnhancer;
        if (loudnessEnhancer != null) {
            loudnessEnhancer.setEnabled(z);
        }
    }
}
