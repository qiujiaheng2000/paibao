package com.play.treasure.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TabWidget;

import com.play.treasure.PlayApplication;
import com.play.treasure.R;
import com.play.treasure.fragment.HuntFragment;
import com.play.treasure.fragment.MainFragment;
import com.play.treasure.fragment.NearByFragment;
import com.play.treasure.fragment.TreasureMallFragment;
import com.play.treasure.fragment.UserFragment;

public class MainActivity extends FragmentActivity 
{
	/**
	 * 会员中心tab
	 */
	public static final int USERCENTER_POSITION = 4;
	
	/**
	 * 系统全局变量
	 */
	private PlayApplication mApplication;
	
	/**
	 * 底部跳转栏
	 */
	private FragmentTabHost mTabHost;
	
	/**
	 * 跳转到登录
	 */
	public static final int GO_LOGIN = 1001;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mApplication = PlayApplication.getApplication();

		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

//		mTabHost.addTab(mTabHost.newTabSpec(getResString(R.string.home)).setIndicator(setTabView(R.drawable.bg_home)),
//				MainSecondFragment.class, null);
		mTabHost.addTab(mTabHost.newTabSpec(getResString(R.string.home)).setIndicator(setTabView(R.drawable.bg_home)),
				MainFragment.class, null);
		
		mTabHost.addTab(mTabHost.newTabSpec(getResString(R.string.find)).setIndicator(setTabView(R.drawable.bg_find)),
				HuntFragment.class, null);

		mTabHost.addTab(mTabHost.newTabSpec(getResString(R.string.mall)).setIndicator(setTabView(R.drawable.bg_mall)),
				TreasureMallFragment.class, null);

		mTabHost.addTab(mTabHost.newTabSpec(getResString(R.string.nearby)).setIndicator(setTabView(R.drawable.bg_nearby)),
				NearByFragment.class, null);

		mTabHost.addTab(mTabHost.newTabSpec(getResString(R.string.user)).setIndicator(setTabView(R.drawable.bg_user)),
				UserFragment.class, null);
		
		TabWidget tabWidget = mTabHost.getTabWidget();
		tabWidget.getChildAt(USERCENTER_POSITION).setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				if(!TextUtils.isEmpty(mApplication.getUserId()))
				{
					mTabHost.setCurrentTab(USERCENTER_POSITION);
				}
				else
				{
					Intent intent = new Intent();
					intent.setClass(MainActivity.this, LoginActivity.class);
					startActivityForResult(intent, GO_LOGIN);
				}
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == GO_LOGIN && resultCode == RESULT_OK) 
		{
			mTabHost.setCurrentTab(USERCENTER_POSITION);
		}
	}

	/**
	 * @Title: getResString
	 * @Description: tab 文字
	 * @param @param resId
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	private String getResString(int resId) 
	{
		return getResources().getString(resId);
	}

	/**
	 * @Title: setTabView
	 * @Description: tab view
	 * @param @param resId
	 * @param @return 设定文件
	 * @return View 返回类型
	 * @throws
	 */
	private View setTabView(int resId) 
	{
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(resId);
		imageView.setPadding(-3, 10, 0, 0);
		return imageView;
	}
	
	@Override
	public void onDestroy() 
	{
		super.onDestroy();
		mTabHost = null;
	}
	
	private ArrayList<MyOnTouchListener> onTouchListeners = new ArrayList<MyOnTouchListener>(10);
	
	@Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        for (MyOnTouchListener listener : onTouchListeners) {
            listener.dispatchTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    public void registerMyOnTouchListener(MyOnTouchListener myOnTouchListener) {
        onTouchListeners.add(myOnTouchListener);
    }

    public void unregisterMyOnTouchListener(MyOnTouchListener myOnTouchListener) {
        onTouchListeners.remove(myOnTouchListener);
    }
	
	public interface MyOnTouchListener 
	{
        public boolean dispatchTouchEvent(MotionEvent ev);
    }
}
