package com.play.treasure;

import com.play.treasure.activity.PublicFirstActivity;
import com.play.treasure.activity.SettingActivity;
import com.play.treasure.fragment.user.MyCommentFragment;
import com.play.treasure.fragment.user.MyFavoriteFragment;
import com.play.treasure.fragment.user.MyPlayFragment;
import com.play.treasure.fragment.user.OnSaleFragment;

import android.os.Bundle;
import android.content.Intent;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class UserActivity extends BackActivity implements OnClickListener {

    private ImageView titleLeft;

    private TextView titleCenter;

    private RelativeLayout BtPublic;

    private FragmentTabHost mTabHost;

    private PlayApplication mApplication;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user);
        mApplication = PlayApplication.getApplication();
        titleLeft = (ImageView) findViewById(R.id.title_bar_left);
        titleLeft.setImageResource(R.drawable.setting);
        titleLeft.setVisibility(View.VISIBLE);
        titleLeft.setOnClickListener(this);

        titleCenter = (TextView) findViewById(R.id.title_bar_center);
        titleCenter.setText("我的空间");
//		titleCenter.setTextSize(16);
        titleCenter.setVisibility(View.VISIBLE);

        BtPublic = (RelativeLayout) findViewById(R.id.user_public_plays);
        BtPublic.setOnClickListener(this);

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);

        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        mTabHost.addTab(mTabHost.newTabSpec(getResString(R.string.myplay)).setIndicator(setTabView(R.drawable.bg_mine)), MyPlayFragment.class, null);

        mTabHost.addTab(mTabHost.newTabSpec(getResString(R.string.onsale)).setIndicator(setTabView(R.drawable.bg_sale)), OnSaleFragment.class, null);

        //mTabHost.addTab(mTabHost.newTabSpec(getResString(R.string.favorite)).setIndicator(setTabView(R.drawable.bg_favorite)), MyFavoriteFragment.class,null);
        TabSpec spec = mTabHost.newTabSpec(getResString(R.string.comment)).setIndicator(setTabView(R.drawable.bg_comment));
        //spec.set
        mTabHost.addTab(spec, MyCommentFragment.class, null);
        mTabHost.getChildAt(0).setBackground(null);
    }

    @Override
    public void onResume() {
        mApplication.setCategoryId("");
        super.onResume();
    }

    /**
     * @param @param  resId
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     * @Title: getResString
     * @Description: tab 文字
     */
    private String getResString(int resId) {
        return getResources().getString(resId);
    }

    /**
     * @param @param  resId
     * @param @return 设定文件
     * @return View 返回类型
     * @throws
     * @Title: setTabView
     * @Description: tab view
     */
    private View setTabView(int resId) {
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(resId);
        imageView.setPadding(-3, 10, 0, 0);
        return imageView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTabHost = null;
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.title_bar_left:
                intent.setClass(this, SettingActivity.class);
                startActivity(intent);
                break;

            case R.id.user_public_plays:
                intent.setClass(this, PublicFirstActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }
}
