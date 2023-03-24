package com.videodown.allhd.app.AVideo.VideoFragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import io.reactivex.disposables.CompositeDisposable;
//import androidx.fragment.app.C0277Fragment;
//import p011io.reactivex.disposables.CompositeDisposable;

public class BaseFragment extends Fragment {
    protected CompositeDisposable hdvideoplayer_subscriptions = new CompositeDisposable();

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        ((Activity) getContext()).getWindow();
    }

    public void setupUI(View view) {
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    BaseFragment.this.hideKeyboard();
                    return false;
                }
            });
        }
        if (view instanceof ViewGroup) {
            int i = 0;
            while (true) {
                ViewGroup viewGroup = (ViewGroup) view;
                if (i < viewGroup.getChildCount()) {
                    setupUI(viewGroup.getChildAt(i));
                    i++;
                } else {
                    return;
                }
            }
        }
    }

    public void hideKeyboard() {
        try {
            ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        } catch (Exception unused) {
        }
    }

    public void onDestroy() {
        this.hdvideoplayer_subscriptions.clear();
        super.onDestroy();
    }
}
