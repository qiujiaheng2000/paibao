package com.play.treasure.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.play.treasure.PlayApplication;
import com.play.treasure.R;
import com.play.treasure.activity.SearchResultActivity;
import com.play.treasure.fragment.item.NearItemFragment;
import com.play.treasure.network.model.NetworkBeanArray;
import com.play.treasure.ui.tab.TabPageIndicator;
import com.play.treasure.utils.CommonProgressDialog;

public class NearByFragment extends Fragment implements OnClickListener {
	private TextView titleCenter;
	private ImageView titleRightCategory;
	private ImageView titleRightLocation;
	/**
	 * 经度
	 */
	public String mLongtitude;

	/**
	 * 纬度
	 */
	public String mLatitude;
	private PlayApplication mApplication;
	private CategoryPopWindow menuWindow;

	private EditText EtSearch;

	private String mInput;

	private String mPostCate = "0";

	private static final String[] TITLE = new String[] { "核桃", "菩提", "红木",
			"核雕", "珠宝玉石", "琥珀蜜蜡", "玛瑙珊瑚", "牙骨佛牌" };

	FragmentPagerAdapter adapter;
	ViewPager pager;

	private CommonProgressDialog progressDialog;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.fragment_nearby, container, false);
		mApplication = PlayApplication.getApplication();

		titleCenter = (TextView) view.findViewById(R.id.title_bar_center);
		titleCenter.setText("身  边");
		titleCenter.setTextSize(16);
		titleCenter.setVisibility(View.VISIBLE);

		titleRightCategory = (ImageView) view
				.findViewById(R.id.title_bar_category);
		titleRightCategory.setBackgroundResource(R.drawable.category);
		titleRightCategory.setVisibility(View.VISIBLE);
		titleRightCategory.setOnClickListener(this);

		titleRightLocation = (ImageView) view
				.findViewById(R.id.title_bar_location);
		titleRightLocation.setBackgroundResource(R.drawable.location_title);
		titleRightLocation.setVisibility(View.VISIBLE);
		titleRightLocation.setOnClickListener(this);
		
		progressDialog = CommonProgressDialog.getInstance(getActivity());
		
		EtSearch = (EditText)view.findViewById(R.id.search_edit);
		EtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEND
						|| (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
					SearchControll();
					return true;
				}
				return false;
			}
		});

		adapter = new TabPageIndicatorAdapter(getChildFragmentManager());
		pager = (ViewPager) view.findViewById(R.id.pager);
		pager.setAdapter(adapter);

		TabPageIndicator indicator = (TabPageIndicator) view
				.findViewById(R.id.indicator);
		indicator.setViewPager(pager);

		indicator.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_bar_category:
			
			menuWindow = new CategoryPopWindow(getActivity(),itemsOnClick);
			// 显示窗口
			menuWindow.showAsDropDown(
					getActivity().findViewById(R.id.title_bar_category), 0, 17);
			
			// 新增popwindow关闭监听
			 menuWindow.setOnDismissListener(new PopupWindow.OnDismissListener()
             {

                 @Override
                 public void onDismiss()
                 {
                     // 获取当前展示的fragment并刷新
                     Fragment frgmt = (Fragment) adapter
                             .instantiateItem(pager,
                                     pager.getCurrentItem());
                     if (frgmt instanceof NearItemFragment)
                     {
                         ((NearItemFragment) frgmt).refresh(mPostCate);
                     }
                 }
             });
			break;
		case R.id.title_bar_location:
			Fragment frgmt = (Fragment) adapter.instantiateItem(pager,pager.getCurrentItem());
			if (frgmt instanceof NearItemFragment) 
			{
				((NearItemFragment) frgmt).refresh();
			}
			break;
		}
	}
	
	// 为弹出窗口实现监听类
		private OnClickListener itemsOnClick = new OnClickListener() {

			public void onClick(View v) {
				switch (v.getId()) 
				{
				case R.id.btn_1:
					mPostCate = "1";
					menuWindow.dismiss();
					break;
				case R.id.btn_2:
					mPostCate = "2";
					menuWindow.dismiss();
					break;
				case R.id.btn_3:
					mPostCate = "3";
					menuWindow.dismiss();
					break;
				case R.id.btn_4:
					mPostCate = "4";
					menuWindow.dismiss();
					break;
				default:
					mPostCate = "0";
					menuWindow.dismiss();
					break;
				}
			}
		};

	class TabPageIndicatorAdapter extends FragmentPagerAdapter {
		public TabPageIndicatorAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) 
		{
            NearItemFragment fragment = new NearItemFragment();

            Bundle args = new Bundle();
            args.putString("category", String.valueOf(position + 1));
            fragment.setArguments(args);

			return fragment;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return TITLE[position % TITLE.length];
		}

		@Override
		public int getCount() {
			return TITLE.length;
		}
	}

	/**
	 * 功能描述: 搜索控制 〈功能详细描述〉
	 *
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	private void SearchControll() {
		mInput = EtSearch.getText().toString();

		if (TextUtils.isEmpty(mInput)) {
			Toast.makeText(getActivity(), "请输入搜索内容", Toast.LENGTH_SHORT).show();
			return;
		} else {
			new SearchTask().execute();
		}
	}

	/**
	 * 搜索线程
	 *
	 * @author 王超
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	private class SearchTask extends AsyncTask<Void, Void, NetworkBeanArray> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog.show();
			progressDialog.setCancelable(true);
		}

		@Override
		protected NetworkBeanArray doInBackground(Void... params) {
			try {
				mInput = EtSearch.getText().toString();
				return mApplication.getNetApi().search(mInput, "1", "1", "1",
						"0", "0");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(NetworkBeanArray result) {
			super.onPostExecute(result);
			if (result != null) {
				progressDialog.dismiss();
				String data = result.getResult();
				if (null != data) {
					Intent intent = new Intent();
					intent.putExtra("result", data);
					intent.setClass(getActivity(), SearchResultActivity.class);
					startActivity(intent);
				} else {
					Toast.makeText(getActivity(), "sorry 没有找到您想要的结果..",
							Toast.LENGTH_SHORT).show();
				}
			}
		}
	}
}
