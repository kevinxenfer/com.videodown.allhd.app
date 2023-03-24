package com.videodown.allhd.app.AUtils;


import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

public class Resizer {
    public static int height, width;
    public static int SCALE_WIDTH = 1080;
    public static int SCALE_HEIGHT = 1920;

    public static void getheightandwidth(Context context) {

        getHeight(context);
        getwidth(context);
    }

    public static int getwidth(Context context) {
        width = context.getResources().getDisplayMetrics().widthPixels;
        return width;
    }

    public static int getHeight(Context context) {
        height = context.getResources().getDisplayMetrics().heightPixels;
        return height;
    }

    public static void setHeight(Context mContext, View view, int v_height) {
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        int height = dm.heightPixels * v_height / SCALE_HEIGHT;
        view.getLayoutParams().height = height;
    }

    public static void setWidth(Context mContext, View view, int v_Width) {
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        int width = dm.widthPixels * v_Width / SCALE_WIDTH;
        view.getLayoutParams().width = width;
    }

    public static int setHeight(int h) {

        return (height * h) / 1920;

    }

    public static int setWidth(int w) {
        return (width * w) / 1080;

    }

    public static void setSize(View view, int width, int height) {

        view.getLayoutParams().height = setHeight(height);
        view.getLayoutParams().width = setWidth(width);

    }

    public static void setHeightByWidth(Context mContext, View view, int v_height) {
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        int height = dm.widthPixels * v_height / SCALE_WIDTH;
        view.getLayoutParams().height = height;
    }

    public static void setSize(View view, int width, int height, boolean sameheightandwidth) {

        if (sameheightandwidth) {
            view.getLayoutParams().height = setWidth(height);
            view.getLayoutParams().width = setWidth(width);
        } else {
            view.getLayoutParams().height = setHeight(height);
            view.getLayoutParams().width = setHeight(width);
        }

    }

    public static void setMargins(View view, int left, int top, int right, int bottom) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        marginLayoutParams.setMargins(setWidth(left), setHeight(top), setWidth(right), setHeight(bottom));
    }

    public static void setPadding(View view, int left, int top, int right, int bottom) {
        view.setPadding(left, top, right, bottom);
    }

    public static void setHeightWidth(Context mContext, View view, int v_width, int v_height) {
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        int width = dm.widthPixels * v_width / SCALE_WIDTH;
        int height = dm.heightPixels * v_height / SCALE_HEIGHT;
        view.getLayoutParams().width = width;
        view.getLayoutParams().height = height;
    }

    public static void setHeightWidthAsWidth(Context mContext, View view, int v_width, int v_height) {
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        int width = dm.widthPixels * v_width / SCALE_WIDTH;
        int height = dm.widthPixels * v_height / SCALE_WIDTH;
        view.getLayoutParams().width = width;
        view.getLayoutParams().height = height;
    }

    public static void setMargins(Context mContext, View view, int m_left, int m_top, int m_right, int m_bottom) {
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        // margin
        int left = dm.widthPixels * m_left / SCALE_WIDTH;
        int top = dm.heightPixels * m_top / SCALE_HEIGHT;
        int right = dm.widthPixels * m_right / SCALE_WIDTH;
        int bottom = dm.heightPixels * m_bottom / SCALE_HEIGHT;

        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }

    public static int getWidth(Context context) {

        return context.getResources().getDisplayMetrics().widthPixels;

    }

    public static void setMarginLeft(Context mContext, View view, int m_left) {
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        // margin
        int left = dm.widthPixels * m_left / SCALE_WIDTH;

        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, 0, 0, 0);
            view.requestLayout();
        }
    }

    public static void setPadding(Context mContext, View view, int p_left, int p_top, int p_right, int p_bottom) {
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        int left = dm.widthPixels * p_left / SCALE_WIDTH;
        int top = dm.heightPixels * p_top / SCALE_HEIGHT;
        int right = dm.widthPixels * p_right / SCALE_WIDTH;
        int bottom = dm.heightPixels * p_bottom / SCALE_HEIGHT;
        view.setPadding(left, top, right, bottom);
    }

    public static void setHeightWidth2(Context mContext, View view, int v_width, int v_height) {
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        int width = dm.widthPixels * v_width / SCALE_WIDTH;
        int height = dm.widthPixels * v_height / SCALE_WIDTH;
        view.getLayoutParams().width = width;
        view.getLayoutParams().height = height;
    }

    public static void FS(Activity mActivity) {
        mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public static boolean isMyServiceRunning(Class<?> serviceClass, Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isconnectivity(Context context) {

//        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
//        if (connectivityManager != null) {
//            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
//                if (capabilities != null) {
//                    return true;
//                } else {
//                    return false;
//
//                }
//            } else {
//
//                try {
//                    NetworkInfo activenetwork = connectivityManager.getActiveNetworkInfo();
//                    if (activenetwork != null) {
//                        return true;
//                    } else {
//                        return false;
//                    }
//
//                } catch (Exception e) {
//                    Log.i("update_statut", "" + e.getMessage());
//                }
//            }
//        }
//
//        return false;
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}

