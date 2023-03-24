package com.videodown.allhd.app.AllStory.AllvideoEqulizer;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ProgressBar;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.core.view.ViewCompat;

import com.videodown.allhd.app.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class VerticalSeekBar extends AppCompatSeekBar {
    public static final int hdvideoplayer_ROTATION_ANGLE_CW_270 = 270;
    private boolean hdvideoplayer_mIsDragging;
    private Method hdvideoplayer_mMethodSetProgressFromUser;
    private int hdvideoplayer_mRotationAngle = hdvideoplayer_ROTATION_ANGLE_CW_270;
    private Drawable hdvideoplayer_mThumb_;

    private static boolean isValidRotationAngle(int i) {
        return i == 90 || i == 270;
    }

    public VerticalSeekBar(Context context) {
        super(context);
        AttributeSet attributeSet = null;
        initialize(context, (AttributeSet) null, 0, 0);
    }

    public VerticalSeekBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initialize(context, attributeSet, 0, 0);
    }

    public VerticalSeekBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initialize(context, attributeSet, i, 0);
    }

    private void initialize(Context context, AttributeSet attributeSet, int i, int i2) {
        ViewCompat.setLayoutDirection(this, ViewCompat.LAYOUT_DIRECTION_LTR);
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.VerticalSeekBar, i, i2);
            int integer = obtainStyledAttributes.getInteger(R.styleable.VerticalSeekBar_getInteger, 0);
            if (isValidRotationAngle(integer)) {
                this.hdvideoplayer_mRotationAngle = integer;
            }
            obtainStyledAttributes.recycle();
        }
    }

    public void setThumb(Drawable drawable) {
        this.hdvideoplayer_mThumb_ = drawable;
        super.setThumb(drawable);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (useViewRotation()) {
            return onTouchEventUseViewRotation(motionEvent);
        }
        return onTouchEventTraditionalRotation(motionEvent);
    }

    private boolean onTouchEventTraditionalRotation(MotionEvent motionEvent) {
        if (!isEnabled()) {
            return false;
        }
        int action = motionEvent.getAction();
        if (action == 0) {
            setPressed(true);
            onStartTrackingTouch();
            trackTouchEvent(motionEvent);
            attemptClaimDrag(true);
            invalidate();
        } else if (action == 1) {
            if (this.hdvideoplayer_mIsDragging) {
                trackTouchEvent(motionEvent);
                onStopTrackingTouch();
                setPressed(false);
            } else {
                onStartTrackingTouch();
                trackTouchEvent(motionEvent);
                onStopTrackingTouch();
                attemptClaimDrag(false);
            }
            invalidate();
        } else if (action != 2) {
            if (action == 3) {
                if (this.hdvideoplayer_mIsDragging) {
                    onStopTrackingTouch();
                    setPressed(false);
                }
                invalidate();
            }
        } else if (this.hdvideoplayer_mIsDragging) {
            trackTouchEvent(motionEvent);
        }
        return true;
    }

    private boolean onTouchEventUseViewRotation(MotionEvent motionEvent) {
        boolean onTouchEvent = super.onTouchEvent(motionEvent);
        if (onTouchEvent) {
            int action = motionEvent.getAction();
            if (action == 0) {
                attemptClaimDrag(true);
            } else if (action == 1 || action == 3) {
                attemptClaimDrag(false);
            }
        }
        return onTouchEvent;
    }

    private void trackTouchEvent(MotionEvent motionEvent) {
        int paddingLeft = super.getPaddingLeft();
        int paddingRight = super.getPaddingRight();
        int height = getHeight() - paddingLeft;
        int i = height - paddingRight;
        int y = (int) motionEvent.getY();
        int i2 = this.hdvideoplayer_mRotationAngle;
        float f = 0.0f;
        float f2 = i2 != 90 ? i2 != 270 ? 0.0f : (float) (height - y) : (float) (y - paddingLeft);
        if (f2 >= 0.0f && i != 0) {
            float f3 = (float) i;
            f = f2 > f3 ? 1.0f : f2 / f3;
        }
        _setProgressFromUser((int) (f * ((float) getMax())), true);
    }

    private void attemptClaimDrag(boolean z) {
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(z);
        }
    }

    private void onStartTrackingTouch() {
        this.hdvideoplayer_mIsDragging = true;
    }

    private void onStopTrackingTouch() {
        this.hdvideoplayer_mIsDragging = false;
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (!isEnabled() || (i != 21 && i != 22)) {
            return super.onKeyDown(i, keyEvent);
        }
        return false;
    }

    public synchronized void setProgress(int i) {
        super.setProgress(i);
        if (!useViewRotation()) {
            refreshThumb();
        }
    }

    private synchronized void _setProgressFromUser(int i, boolean z) {
        if (this.hdvideoplayer_mMethodSetProgressFromUser == null) {
            Class<ProgressBar> cls = ProgressBar.class;
            Method method = null;
            try {
                method = cls.getDeclaredMethod("setProgress", new Class[]{Integer.TYPE, Boolean.TYPE});
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            method.setAccessible(true);
            this.hdvideoplayer_mMethodSetProgressFromUser = method;
        }
        Method method2 = this.hdvideoplayer_mMethodSetProgressFromUser;
        if (method2 == null) {
            try {
                method2.invoke(this, new Object[]{Integer.valueOf(i), Boolean.valueOf(z)});
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException unused) {
            }
        } else {
            super.setProgress(i);
        }
        refreshThumb();
    }

    public synchronized void onMeasure(int i, int i2) {
        if (useViewRotation()) {
            super.onMeasure(i, i2);
        } else {
            super.onMeasure(i2, i);
            ViewGroup.LayoutParams layoutParams = getLayoutParams();
            if (isInEditMode() && layoutParams != null && layoutParams.height >= 0) {
                setMeasuredDimension(super.getMeasuredHeight(), layoutParams.height);
            }
            setMeasuredDimension(super.getMeasuredHeight(), super.getMeasuredWidth());
        }
    }

    public void onSizeChanged(int i, int i2, int i3, int i4) {
        if (useViewRotation()) {
            super.onSizeChanged(i, i2, i3, i4);
        } else {
            super.onSizeChanged(i2, i, i4, i3);
        }
    }

    public synchronized void onDraw(Canvas canvas) {
        if (!useViewRotation()) {
            int i = this.hdvideoplayer_mRotationAngle;
            if (i == 90) {
                canvas.rotate(90.0f);
                canvas.translate(0.0f, (float) (-super.getWidth()));
            } else if (i == 270) {
                canvas.rotate(-90.0f);
                canvas.translate((float) (-super.getHeight()), 0.0f);
            }
        }
        super.onDraw(canvas);
    }

    public int getRotationAngle() {
        return this.hdvideoplayer_mRotationAngle;
    }

    private void refreshThumb() {
        onSizeChanged(super.getWidth(), super.getHeight(), 0, 0);
    }

    public boolean useViewRotation() {
        return !isInEditMode();
    }

    private VerticalSeekBarWrapper getWrapper() {
        ViewParent parent = getParent();
        if (parent instanceof VerticalSeekBarWrapper) {
            return (VerticalSeekBarWrapper) parent;
        }
        return null;
    }
}
