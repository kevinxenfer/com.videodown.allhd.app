package com.videodown.allhd.app.Activity.StoryFragment;

import androidx.documentfile.provider.DocumentFile;

import java.util.Comparator;

public final  class loadDataWhatsapp implements Comparator {
    public static final loadDataWhatsapp INSTANCE = new loadDataWhatsapp();

    private loadDataWhatsapp() {
    }

    @Override
    public final int compare(Object obj, Object obj2) {
        int compare;
        compare = Long.compare(((DocumentFile) obj2).lastModified(), ((DocumentFile) obj).lastModified());
        return compare;
    }
}
