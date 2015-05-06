package com.play.treasure.activity;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.play.treasure.PlayApplication;
import com.play.treasure.R;
import com.play.treasure.activity.LoginActivity;
import com.play.treasure.activity.PlayDetailActivity;
import com.play.treasure.activity.PublicFirstActivity;
import com.play.treasure.adapter.ViewPagerAdapter;
import com.play.treasure.fragment.category.AllFragment;
import com.play.treasure.fragment.category.PlayFragment;
import com.play.treasure.fragment.category.SaleFragment;
import com.play.treasure.fragment.category.ShareFragment;
import com.play.treasure.fragment.category.ZhangYanFragment;
import com.play.treasure.network.NetworkConfig;

/**
 * @ClassName: MainSecondFragment
 * @author wangchao29
 * @date 2014年11月23日 下午1:57:35
 * 
 */

@SuppressLint({ "SetJavaScriptEnabled", "JavascriptInterface" })
public class MainSecondFragment extends Fragment implements OnClickListener 
{
	private ViewPager viewPager;

	private ViewPagerAdapter mAdapter;

	private ImageView underLine;

	private List<Fragment> viewList;

	private TextView tab_one, tab_two, tab_three, tab_four, tab_five;

	private int offset = 0;

	private int currIndex = 2;
	
	private int bmpWidth;

	private ImageView titleRight;
	
	private PlayApplication mApplication;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		mApplication = PlayApplication.getApplication();
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_main_second, container, false);


		titleRight = (ImageView) view.findViewById(R.id.title_bar_right);
		titleRight.setImageResource(R.drawable.takephoto);
		titleRight.setVisibility(View.VISIBLE);
		titleRight.setOnClickListener(this);


		initTab(view);

		initUnderLine(view);

		viewPager = (ViewPager) view.findViewById(R.id.viewpager);

		AllFragment allFragment = new AllFragment();
		SaleFragment saleFragment = new SaleFragment();
		ZhangYanFragment zhangFragment = new ZhangYanFragment();
		PlayFragment playFragment = new PlayFragment();
		ShareFragment shareFragment = new ShareFragment();

		viewList = new ArrayList<Fragment>();
		viewList.add(allFragment);
		viewList.add(saleFragment);
		viewList.add(zhangFragment);
		viewList.add(playFragment);
		viewList.add(shareFragment);

		setCurrentTab(currIndex);

		mAdapter = new ViewPagerAdapter(getChildFragmentManager(), viewList,
				null);
		viewPager.setAdapter(mAdapter);

		viewPager.setCurrentItem(currIndex);
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());

		return view;
	}

	private void initTab(View view) {
		tab_one = (TextView) view.findViewById(R.id.tab_one);
		tab_two = (TextView) view.findViewById(R.id.tab_two);
		tab_three = (TextView) view.findViewById(R.id.tab_three);
		tab_four = (TextView) view.findViewById(R.id.tab_four);
		tab_five = (TextView) view.findViewById(R.id.tab_five);

		tab_one.setOnClickListener(listener);
		tab_two.setOnClickListener(listener);
		tab_three.setOnClickListener(listener);
		tab_four.setOnClickListener(listener);
		tab_five.setOnClickListener(listener);

	}

	private void initUnderLine(View view) {
		underLine = (ImageView) view.findViewById(R.id.under_line);
		/*bmpWidth = BitmapFactory.decodeResource(getResources(),
				R.drawable.tab_under_line_white).getWidth();*/// 获取图片宽度
		bmpWidth = 60;
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;// 获取分辨率宽度
		//offset = (screenW / 5 - bmpWidth) / 2;// 计算偏移量
		offset = (screenW / 5 - bmpWidth) / 2;
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		underLine.setImageMatrix(matrix);// 设置动画初始位置
	}
	public void setCurrentTab(int index) {
		switch (index) {
		case 0:
			viewPager.setCurrentItem(0);
			break;
		case 1:
			viewPager.setCurrentItem(1);
			break;
		case 2:
			viewPager.setCurrentItem(2);
			break;
		case 3:
			viewPager.setCurrentItem(3);
			break;
		case 4:
			viewPager.setCurrentItem(4);
			break;
		}
	}

	OnClickListener listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.tab_one:
				setCurrentTab(0);
				break;
			case R.id.tab_two:
				setCurrentTab(1);
				break;
			case R.id.tab_three:
				setCurrentTab(2);
				break;
			case R.id.tab_four:
				setCurrentTab(3);
				break;
			case R.id.tab_five:
				setCurrentTab(4);
				break;

			default:
				break;
			}
		}
	};

	public class MyOnPageChangeListener implements OnPageChangeListener {
		int one = offset * 2 + bmpWidth;

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int arg0) {
			Animation animation = new TranslateAnimation(one * currIndex, one
					* arg0, 0, 0);// 显然这个比较简洁，只有一行代码。
			currIndex = arg0;
			animation.setFillAfter(true);
			animation.setDuration(300);
			underLine.startAnimation(animation);
		}
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.title_bar_right:
			if(TextUtils.isEmpty(mApplication.getUserId()))
			{
				intent.setClass(getActivity(), LoginActivity.class);
				startActivity(intent);
			}
			else
			{
				intent.setClass(getActivity(), PublicFirstActivity.class);
				startActivity(intent);
			}
			
			break;

		}

	}
}
