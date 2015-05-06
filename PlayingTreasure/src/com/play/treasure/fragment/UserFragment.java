package com.play.treasure.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.play.treasure.PlayApplication;
import com.play.treasure.R;
import com.play.treasure.activity.PublicFirstActivity;
import com.play.treasure.activity.SettingActivity;
import com.play.treasure.fragment.user.MyCommentFragment;
import com.play.treasure.fragment.user.MyFavoriteFragment;
import com.play.treasure.fragment.user.MyPlayFragment;
import com.play.treasure.fragment.user.OnSaleFragment;

/**
 * @ClassName: UserFragment
 * @Description: 个人中心
 * @author wangchao29
 * @date 2014年11月25日 下午9:45:35
 * 
 */
public class UserFragment extends Fragment implements OnClickListener 
{

	private ImageView titleLeft;

	private TextView titleCenter;

	private RelativeLayout BtPublic;

	private FragmentTabHost mTabHost;
	
	private PlayApplication mApplication;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		mApplication = PlayApplication.getApplication();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
		View view = inflater.inflate(R.layout.fragment_user, container, false);

		titleLeft = (ImageView) view.findViewById(R.id.title_bar_left);
		titleLeft.setImageResource(R.drawable.setting);
		titleLeft.setVisibility(View.VISIBLE);
		titleLeft.setOnClickListener(this);

		titleCenter = (TextView)view.findViewById(R.id.title_bar_center);
		titleCenter.setText("我的空间");
		titleCenter.setTextSize(16);
		titleCenter.setVisibility(View.VISIBLE);

		BtPublic = (RelativeLayout) view.findViewById(R.id.user_public_plays);
		BtPublic.setOnClickListener(this);

		mTabHost = (FragmentTabHost) view.findViewById(android.R.id.tabhost);
		mTabHost.setup(getActivity(), getChildFragmentManager(),R.id.realtabcontent);

		mTabHost.addTab(mTabHost.newTabSpec(getResString(R.string.myplay)).setIndicator(setTabView(R.drawable.bg_mine)),MyPlayFragment.class, null);

		mTabHost.addTab(mTabHost.newTabSpec(getResString(R.string.onsale)).setIndicator(setTabView(R.drawable.bg_sale)), OnSaleFragment.class,null);

		mTabHost.addTab(mTabHost.newTabSpec(getResString(R.string.favorite)).setIndicator(setTabView(R.drawable.bg_favorite)), MyFavoriteFragment.class,null);

		mTabHost.addTab(mTabHost.newTabSpec(getResString(R.string.comment)).setIndicator(setTabView(R.drawable.bg_comment)), MyCommentFragment.class,null);

		return view;
	}
	
	@Override
	public void onResume() 
	{
		mApplication.setCategoryId("");
		super.onResume();
	}
	
	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.title_bar_left:
			intent.setClass(getActivity(), SettingActivity.class);
			startActivity(intent);
			break;

		case R.id.user_public_plays:
			intent.setClass(getActivity(), PublicFirstActivity.class);
			startActivity(intent);
			break;

		default:
			break;
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
		ImageView imageView = new ImageView(getActivity());
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
}
