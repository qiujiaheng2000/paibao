package com.play.treasure.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MyViewPage extends ViewPager {

	public MyViewPage(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public MyViewPage(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	private boolean enabled;
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	//触摸没有反应就可以了
    @Override
    public boolean onTouchEvent(MotionEvent event) {
  
        return false;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
 
        return false;
    }
}
