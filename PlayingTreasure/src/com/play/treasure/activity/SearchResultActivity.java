package com.play.treasure.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.etsy.android.grid.StaggeredGridView;
import com.play.treasure.PlayApplication;
import com.play.treasure.R;
import com.play.treasure.fragment.CategoryPopWindow;
import com.play.treasure.model.Waterfall;
import com.play.treasure.utils.CommonProgressDialog;
import com.play.treasure.waterfall.WaterfallAdapter;

public class SearchResultActivity extends Activity implements OnClickListener{

	private ImageView titleLeft;

	private TextView titleCenter;

	private ImageView titleRightCategory;

	private ImageView titleRightLocation;

	private StaggeredGridView mGridView;

	private WaterfallAdapter mAdapter;

	private PlayApplication mApplication;

	private CommonProgressDialog progressDialog;
	
	private int currentPage = 1;

	private CategoryPopWindow menuWindow;

	private String mPostCate = "0";

	private String nPostCate;

	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_result);

		titleLeft = (ImageView) findViewById(R.id.title_bar_left);
		titleLeft.setImageResource(R.drawable.back480_03);
		titleLeft.setVisibility(View.VISIBLE);
		titleLeft.setOnClickListener(this);

		titleCenter = (TextView) findViewById(R.id.title_bar_center);
		titleCenter.setText("搜索结果");
		titleCenter.setVisibility(View.VISIBLE);
		titleCenter.setTextSize(16);

		titleRightCategory = (ImageView) findViewById(R.id.title_bar_category);
		titleRightCategory.setBackgroundResource(R.drawable.category);
		titleRightCategory.setVisibility(View.VISIBLE);
		titleRightCategory.setOnClickListener(this);

		titleRightLocation = (ImageView) findViewById(R.id.title_bar_location);
		titleRightLocation.setBackgroundResource(R.drawable.location_title);
		titleRightLocation.setVisibility(View.VISIBLE);
		titleRightLocation.setOnClickListener(this);

		mGridView = (StaggeredGridView)findViewById(R.id.grid_view);

		mAdapter = new WaterfallAdapter(this);

		mGridView.setAdapter(mAdapter);
		mGridView.setOnItemClickListener(mAdapter);

		try {
			Intent intent = getIntent();
			String result = intent.getStringExtra("result");
			JSONArray jsonArray = new JSONArray(result);
			List<Waterfall> waterList = new ArrayList<Waterfall>();
			Waterfall water = null;
			for (int i = 0; i < jsonArray.length(); i++) {
				water = new Waterfall(jsonArray.getJSONObject(i));
				waterList.add(water);
			}
			mAdapter.addAll(waterList);
			mAdapter.notifyDataSetChanged();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_bar_left:
			SearchResultActivity.this.finish();
			break;
		case R.id.title_bar_category:
			// 实例化SelectPicPopupWindow
			menuWindow = new CategoryPopWindow(SearchResultActivity.this,
					itemsOnClick);
			// 显示窗口
			menuWindow.showAsDropDown(SearchResultActivity.this
					.findViewById(R.id.title_bar_category), 0, 17);
			break;
		case R.id.title_bar_location:
			
				mAdapter.clear();
				try {
					Intent intent = getIntent();
					String result = intent.getStringExtra("result");
					JSONArray jsonArray = new JSONArray(result);
					List<Waterfall> waterList = new ArrayList<Waterfall>();
					Waterfall water = null;
					for (int i = 0; i < jsonArray.length(); i++) {
						water = new Waterfall(jsonArray.getJSONObject(i));
						waterList.add(water);
					}
					mAdapter.addAll(waterList);
					mAdapter.notifyDataSetChanged();

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			
			break;
		}
	}

	// 为弹出窗口实现监听类
	private OnClickListener itemsOnClick = new OnClickListener() {

		public void onClick(View v) {
			menuWindow.dismiss();
			switch (v.getId()) {
			case R.id.btn_1:
					mAdapter.clear();
					try {
						Intent intent = getIntent();
						String result = intent.getStringExtra("result");
						JSONArray jsonArray = new JSONArray(result);
						List<Waterfall> waterList = new ArrayList<Waterfall>();
						Waterfall water = null;
						for (int i = 0; i < jsonArray.length(); i++) {
							if ("1".equals(jsonArray.getJSONObject(i)
									.getString("category"))) {
								water = new Waterfall(
										jsonArray.getJSONObject(i));
								waterList.add(water);
							}

						}
						mAdapter.addAll(waterList);
						mAdapter.notifyDataSetChanged();

					} catch (Exception ex) {
						ex.printStackTrace();
					}
				break;
			case R.id.btn_2:
					mAdapter.clear();
					try {
						Intent intent = getIntent();
						String result = intent.getStringExtra("result");
						JSONArray jsonArray = new JSONArray(result);
						List<Waterfall> waterList = new ArrayList<Waterfall>();
						Waterfall water = null;
						for (int i = 0; i < jsonArray.length(); i++) {
							if ("2".equals(jsonArray.getJSONObject(i)
									.getString("category"))) {
								water = new Waterfall(
										jsonArray.getJSONObject(i));
								waterList.add(water);
							}

						}
						mAdapter.addAll(waterList);
						mAdapter.notifyDataSetChanged();

					} catch (Exception ex) {
						ex.printStackTrace();
					}
				break;
			case R.id.btn_3:
					mAdapter.clear();
					try {
						Intent intent = getIntent();
						String result = intent.getStringExtra("result");
						JSONArray jsonArray = new JSONArray(result);
						List<Waterfall> waterList = new ArrayList<Waterfall>();
						Waterfall water = null;
						for (int i = 0; i < jsonArray.length(); i++) {
							if ("3".equals(jsonArray.getJSONObject(i)
									.getString("category"))) {
								water = new Waterfall(
										jsonArray.getJSONObject(i));
								waterList.add(water);
							}

						}
						mAdapter.addAll(waterList);
						mAdapter.notifyDataSetChanged();

					} catch (Exception ex) {
						ex.printStackTrace();
					}
				break;
			case R.id.btn_4:
					mAdapter.clear();
					try {
						Intent intent = getIntent();
						String result = intent.getStringExtra("result");
						JSONArray jsonArray = new JSONArray(result);
						List<Waterfall> waterList = new ArrayList<Waterfall>();
						Waterfall water = null;
						for (int i = 0; i < jsonArray.length(); i++) {
							if ("4".equals(jsonArray.getJSONObject(i)
									.getString("category"))) {
								water = new Waterfall(
										jsonArray.getJSONObject(i));
								waterList.add(water);
							}

						}
						mAdapter.addAll(waterList);
						mAdapter.notifyDataSetChanged();

					} catch (Exception ex) {
						ex.printStackTrace();
					}
				break;
			default:
				break;
			}

		}

	};
}
