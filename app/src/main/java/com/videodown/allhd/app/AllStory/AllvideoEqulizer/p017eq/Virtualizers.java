package com.videodown.allhd.app.AllStory.AllvideoEqulizer.p017eq;

import android.content.SharedPreferences;
import android.media.audiofx.Virtualizer;

import com.videodown.allhd.app.Activity.Other.PreferenceUtil;

public class Virtualizers {
    private static Virtualizer hdvideoplayer_virtualizer;

    public static void initVirtualizer(int i) {
        EndVirtual();
        try {
            hdvideoplayer_virtualizer = new Virtualizer(0, i);
            short s = (short) PreferenceUtil.getInstance().saveEq().getInt(PreferenceUtil.VIRTUAL_BOOST, 0);
            if (s > 0) {
                setVirtualizerStrength(s);
            } else {
                setVirtualizerStrength((short) 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setVirtualizerStrength(short s) {
        Virtualizer virtualizer = hdvideoplayer_virtualizer;
        if (virtualizer != null && virtualizer.getStrengthSupported() && s >= 0 && s <= 1000) {
            try {
                hdvideoplayer_virtualizer.setStrength(s);
                saveVirtual(Short.valueOf(s));
            } catch (RuntimeException  unused) {
            }
        }
    }

    public static void EndVirtual() {
        Virtualizer virtualizer = hdvideoplayer_virtualizer;
        if (virtualizer != null) {
            virtualizer.release();
            hdvideoplayer_virtualizer = null;
        }
    }

    public static void saveVirtual(Short sh) {
        SharedPreferences.Editor edit = PreferenceUtil.getInstance().saveEq().edit();
        edit.putInt(PreferenceUtil.VIRTUAL_BOOST, sh.shortValue());
        edit.apply();
    }

    public static void setEnabled(boolean z) {
        Virtualizer virtualizer = hdvideoplayer_virtualizer;
        if (virtualizer != null) {
            virtualizer.setEnabled(z);
        }
    }
}
