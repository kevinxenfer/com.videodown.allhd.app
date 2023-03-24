package com.videodown.allhd.app.AUtils.XAWebFB;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public final  class HostNameVerifier implements HostnameVerifier {
    public static final HostNameVerifier INSTANCE = new HostNameVerifier();

    private HostNameVerifier() {
    }

    public final boolean verify(String str, SSLSession sSLSession) {
        return AndroidUtils.OkHttpclient(str, sSLSession);
    }
}
