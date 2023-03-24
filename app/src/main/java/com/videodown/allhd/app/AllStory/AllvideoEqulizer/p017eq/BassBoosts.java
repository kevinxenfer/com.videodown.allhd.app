package com.videodown.allhd.app.AllStory.AllvideoEqulizer.p017eq;

import android.content.SharedPreferences;
import android.media.audiofx.BassBoost;

import com.videodown.allhd.app.Activity.Other.PreferenceUtil;

public class BassBoosts {
    private static BassBoost hdvideoplayer_bassBoost;

    public static void initBass(int i) {
        EndBass();
        try {
            hdvideoplayer_bassBoost = new BassBoost(0, i);
            short s = (short) PreferenceUtil.getInstance().saveEq().getInt(PreferenceUtil.BASS_BOOST, 0);
            if (s > 0) {
                setBassBoostStrength(s);
            } else {
                setBassBoostStrength((short) 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void EndBass() {
        BassBoost bassBoost = hdvideoplayer_bassBoost;
        if (bassBoost != null) {
            bassBoost.release();
            hdvideoplayer_bassBoost = null;
        }
    }

    public static void setBassBoostStrength(short s) {
        BassBoost bassBoost = hdvideoplayer_bassBoost;
        if (bassBoost != null && bassBoost.getStrengthSupported() && s >= 0 && s <= 1000) {
            try {
                hdvideoplayer_bassBoost.setStrength(s);
                saveBass(s);
            } catch (RuntimeException unused) {
            }
        }
    }

    public static void saveBass(short s) {
        SharedPreferences.Editor edit = PreferenceUtil.getInstance().saveEq().edit();
        edit.putInt(PreferenceUtil.BASS_BOOST, s);
        edit.apply();
    }

    public static void setEnabled(boolean z) {
        BassBoost bassBoost = hdvideoplayer_bassBoost;
        if (bassBoost != null) {
            bassBoost.setEnabled(z);
        }
    }
}
