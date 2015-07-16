package com.play.treasure;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.play.treasure.FindActivity;
import com.play.treasure.HomeActivity;
import com.play.treasure.MallActivity;
import com.play.treasure.NearByActivity;
import com.play.treasure.R;
import com.play.treasure.UserActivity;
import com.play.treasure.activity.LoginActivity;
import com.umeng.analytics.MobclickAgent;

public class FrameActivity extends ActivityGroup {

    private LinearLayout mMyBottemSearchBtn, mMyBottemTuanBtn,
            mMyBottemCheckinBtn, mMyBottemMyBtn, mMyBottemMoreBtn;
    private ImageView mMyBottemHome, mMyBottemFind,
            mMyBottemMall, mMyBottemNearBy, mMyBottemUser;
    private List<View> list = new ArrayList<View>();
    private View view = null;
    private View view1 = null;
    private View view2 = null;
    private View view3 = null;
    private View view4 = null;
    private ViewPager mViewPager;
    private PagerAdapter pagerAdapter = null;
    public static final int USERCENTER_POSITION = 4;
    private PlayApplication mApplication;
    public static final int GO_LOGIN = 1001;


    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_frame);
        mApplication = PlayApplication.getApplication();
        initView();

    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.FramePager);
        mMyBottemSearchBtn = (LinearLayout) findViewById(R.id.MyBottemSearchBtn);
        mMyBottemTuanBtn = (LinearLayout) findViewById(R.id.MyBottemTuanBtn);
        mMyBottemCheckinBtn = (LinearLayout) findViewById(R.id.MyBottemCheckinBtn);
        mMyBottemMyBtn = (LinearLayout) findViewById(R.id.MyBottemMyBtn);
        mMyBottemMoreBtn = (LinearLayout) findViewById(R.id.MyBottemMoreBtn);
        mMyBottemHome = (ImageView) findViewById(R.id.MyBottemSearchImg);
        mMyBottemFind = (ImageView) findViewById(R.id.MyBottemTuanImg);
        mMyBottemMall = (ImageView) findViewById(R.id.MyBottemCheckinImg);
        mMyBottemNearBy = (ImageView) findViewById(R.id.MyBottemMyImg);
        mMyBottemUser = (ImageView) findViewById(R.id.MyBottemMoreImg);
        createView();
        pagerAdapter = new PagerAdapter() {
            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                container.removeView(list.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                /*if(position==4){
				if(!TextUtils.isEmpty(mApplication.getUserId()))
				{

					//mViewPager.setCurrentItem(4);			
				//	initBottemBtn();
			 	  //  mMyBottemUser.setImageResource(R.drawable.user_selected);
					//mTabHost.setCurrentTab(USERCENTER_POSITION);
					View v = list.get(position);
					container.addView(v);
	 
					return v;
				}
				else
				{
					Intent intent = new Intent();
					intent.setClass(FrameActivity.this, LoginActivity.class);
					startActivityForResult(intent, GO_LOGIN);
					return null;
				}
				}
				else
				{*/
                View v = list.get(position);
                container.addView(v);

                return v;
                //}
            }
        };
        MyBtnOnclick mytouchlistener = new MyBtnOnclick();
        mMyBottemSearchBtn.setOnClickListener(mytouchlistener);
        mMyBottemTuanBtn.setOnClickListener(mytouchlistener);
        mMyBottemCheckinBtn.setOnClickListener(mytouchlistener);
        mMyBottemMyBtn.setOnClickListener(mytouchlistener);
        mMyBottemMoreBtn.setOnClickListener(mytouchlistener);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                initBottemBtn();
                int flag = (Integer) list.get((arg0)).getTag();
                if (flag == 0) {
                    mMyBottemHome
                            .setImageResource(R.drawable.home_selected);
                } else if (flag == 1) {
                    mMyBottemFind
                            .setImageResource(R.drawable.find_selected);
                } else if (flag == 2) {
                    mMyBottemMall
                            .setImageResource(R.drawable.mall_selected);
                } else if (flag == 3) {
                    mMyBottemNearBy
                            .setImageResource(R.drawable.nearby_selected);
                } else if (flag == 4) {
                    mMyBottemUser
                            .setImageResource(R.drawable.user_selected);
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
        mViewPager.setCurrentItem(0);
        mMyBottemHome.setImageResource(R.drawable.home_selected);

    }

    @SuppressWarnings("deprecation")
    private void createView() {
        view = this
                .getLocalActivityManager()
                .startActivity("search",
                        new Intent(FrameActivity.this, MallActivity.class))
                .getDecorView();
        view.setTag(0);
        list.add(view);
        view1 = FrameActivity.this
                .getLocalActivityManager()
                .startActivity("tuan",
                        new Intent(FrameActivity.this, FindActivity.class))
                .getDecorView();
        view1.setTag(1);
        list.add(view1);
        view2 = FrameActivity.this
                .getLocalActivityManager()
                .startActivity("sign",
                        new Intent(FrameActivity.this, HomeActivity.class))
                .getDecorView();
        view2.setTag(2);
        list.add(view2);
        view3 = FrameActivity.this
                .getLocalActivityManager()
                .startActivity("my",
                        new Intent(FrameActivity.this, NearByActivity.class))
                .getDecorView();
        view3.setTag(3);
        list.add(view3);
        view4 = FrameActivity.this
                .getLocalActivityManager()
                .startActivity("more",
                        new Intent(FrameActivity.this, UserActivity.class))
                .getDecorView();
        view4.setTag(4);
        list.add(view4);
    }

    private class MyBtnOnclick implements View.OnClickListener {

        @Override
        public void onClick(View arg0) {
            int mBtnid = arg0.getId();
            switch (mBtnid) {
                case R.id.MyBottemSearchBtn:
                    mViewPager.setCurrentItem(0);
                    initBottemBtn();
                    mMyBottemHome
                            .setImageResource(R.drawable.home_selected);
                    //		mMyBottemSearchTxt.setTextColor(Color.parseColor("#FF8C00"));
                    break;
                case R.id.MyBottemTuanBtn:
                    mViewPager.setCurrentItem(1);
                    initBottemBtn();
                    mMyBottemFind
                            .setImageResource(R.drawable.find_selected);
                    //		mMyBottemTuanTxt.setTextColor(Color.parseColor("#FF8C00"));
                    break;
                case R.id.MyBottemCheckinBtn:
                    mViewPager.setCurrentItem(2);
                    initBottemBtn();
                    mMyBottemMall
                            .setImageResource(R.drawable.mall_selected);
                    //		mMyBottemCheckinTxt.setTextColor(Color.parseColor("#FF8C00"));
                    break;
                case R.id.MyBottemMyBtn:
                    mViewPager.setCurrentItem(3);
                    initBottemBtn();
                    mMyBottemNearBy
                            .setImageResource(R.drawable.nearby_selected);
                    //		mMyBottemMyTxt.setTextColor(Color.parseColor("#FF8C00"));
                    break;
                case R.id.MyBottemMoreBtn:

                    if (!TextUtils.isEmpty(mApplication.getUserId())) {

                        mViewPager.setCurrentItem(4);
                        initBottemBtn();
                        mMyBottemUser.setImageResource(R.drawable.user_selected);
                        //mTabHost.setCurrentTab(USERCENTER_POSITION);
                    } else {
                        Intent intent = new Intent();
                        intent.setClass(FrameActivity.this, LoginActivity.class);
                        startActivityForResult(intent, GO_LOGIN);
                    }

                    //	mMyBottemMoreTxt.setTextColor(Color.parseColor("#FF8C00"));
                    break;
            }

        }

    }

    private void initBottemBtn() {
        mMyBottemHome.setImageResource(R.drawable.home_unselected);
        mMyBottemFind.setImageResource(R.drawable.find_unselected);
        mMyBottemMall.setImageResource(R.drawable.mall_unselected);
        mMyBottemNearBy.setImageResource(R.drawable.nearby_unselected);
        mMyBottemUser.setImageResource(R.drawable.user_unselected);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GO_LOGIN && resultCode == RESULT_OK) {
            mViewPager.setCurrentItem(USERCENTER_POSITION);
        } else {

        }
    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);       //统计时长
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }


	/*private static Boolean isExit = false;  
    private static Boolean hasTask = false;  
    Timer tExit = new Timer();  
    TimerTask task = new TimerTask() {  
           
        @Override  
        public void run() {  
            isExit = false;  
            hasTask = true;  
        }  
    };  */
/*	public boolean onKeyDown(int keyCode, KeyEvent event) {  
	                // TODO Auto-generated method stub  
	                if(keyCode == KeyEvent.KEYCODE_BACK){  
	                	Log.d("duanzhibo", "---------onKeyDown----------");
	//                        System.out.println("user back down");  
	                        if(isExit == false ) {  
	                                isExit = true;  
	                                Toast.makeText(this, "再按一下退出", Toast.LENGTH_SHORT).show();  
	                                if(!hasTask) {  
	                                        tExit.schedule(task, 2000);  
	                                }} else {   
	                                finish();  
	                                System.exit(0);  
	                        }  
	                }                          
	                return false;  
	}*/
}
