package com.play.treasure;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Gallery;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by jhqiu on 15/5/2.
 */
public class MyGallery extends Gallery {

    private static final int timerAnimation = 1;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case timerAnimation:
                    int position = getSelectedItemPosition();
                    if (position < (getCount() - 1)) {
                        onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
                    } else {
                        onKeyDown(KeyEvent.KEYCODE_DPAD_LEFT, null);
                    }
                    if (position == (getCount() - 1)) {
                        setSelection(0);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private final Timer timer = new Timer();
    private final TimerTask task = new TimerTask() {
        @Override
        public void run() {
            mHandler.sendEmptyMessage(timerAnimation);
        }
    };


    public MyGallery(Context context) {
        super(context);
        timer.schedule(task,5000,5000);
    }

    public MyGallery(Context context, AttributeSet attrs) {
        super(context, attrs);
        timer.schedule(task, 5000, 5000);
    }

    public MyGallery(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        timer.schedule(task,5000,5000);
    }

    private boolean isScrollingLeft(MotionEvent paramMotionEvent1,
                                    MotionEvent paramMotionEvent2) {
        float f2 = paramMotionEvent2.getX();
        float f1 = paramMotionEvent1.getX();
        if (f2 > f1) {
            return true;
        }
        return false;
    }

    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (velocityX > 0) {
            //往左边滑动
            super.onKeyDown(KeyEvent.KEYCODE_DPAD_LEFT, null);
        } else {
            super.onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
        }
        return false;
    }
    public void destroy() {
        timer.cancel();
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            return true;
        } else {
            return false;
        }
    }
}
