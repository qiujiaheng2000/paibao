package com.play.treasure.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.etsy.android.grid.StaggeredGridView;
import com.play.treasure.FrameActivity;
import com.play.treasure.PlayApplication;
import com.play.treasure.R;
import com.play.treasure.model.Waterfall;
import com.play.treasure.network.model.NetworkBeanArray;
import com.play.treasure.utils.CommonProgressDialog;
import com.play.treasure.waterfall.WaterfallAdapter;

/** 
* @ClassName: HuntDetailActivity
* @Description: 寻宝结果页
* @author wangchao29
* @date 2014年12月7日 下午3:14:56
* 
*/ 
public class HuntDetailActivity extends Activity implements OnClickListener
{
	private ImageView titleLeft;
	
	private StaggeredGridView mGridView;

	private WaterfallAdapter mAdapter;

	private PlayApplication mApplication;

	private CommonProgressDialog progressDialog;
	
	private TextView titleCenter;
	private int currentPage = 1;
	
	private String mPostcate = "0";
	
	private String id;
	
	private String title;
	
	@SuppressLint("NewApi") @Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_huntdetail);
		mApplication = PlayApplication.getApplication();
		
		Intent intent  = getIntent();
		Bundle bundle = intent.getBundleExtra("category");
		
		id = bundle.getString("id");
		title = bundle.getString("title");
		
		titleLeft = (ImageView) findViewById(R.id.title_bar_left);
		titleLeft.setImageResource(R.drawable.back480_03);
		titleLeft.setVisibility(View.VISIBLE);
		titleLeft.setOnClickListener(this);
		
		titleCenter = (TextView) findViewById(R.id.title_bar_center);
		titleCenter.setText(title);
		titleCenter.setVisibility(View.VISIBLE);
		titleCenter.setTextSize(16);
		
		progressDialog = CommonProgressDialog.getInstance(this);
		
		mGridView = (StaggeredGridView)findViewById(R.id.grid_view);

		mAdapter = new WaterfallAdapter(this);
		new WaterfallTask().execute();

		mGridView.setAdapter(mAdapter);
		mGridView.setOnItemClickListener(mAdapter);
	}
	
	
	private class WaterfallTask extends AsyncTask<Void, Void, NetworkBeanArray> 
	{
		@Override
		protected void onPreExecute() {
			progressDialog.show();
			super.onPreExecute();
		}

		@Override
		protected NetworkBeanArray doInBackground(Void... params) 
		{
			try
			{
				return mApplication.getNetApi().huntDetial(String.valueOf(currentPage), mApplication.getLongitude(), mApplication.getLatitude(), id, mPostcate);
			}
			catch(NullPointerException ex)
			{
				notData();
				ex.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(NetworkBeanArray result) 
		{
			if(result!=null)
			{
				progressDialog.dismiss();
				try 
				{
					JSONArray jsonArray = new JSONArray(result.getResult());
					List<Waterfall> waterList = new ArrayList<Waterfall>();
					Waterfall water = null;
					for(int i=0;i<jsonArray.length();i++)
					{
						water = new Waterfall(jsonArray.getJSONObject(i));
						waterList.add(water);
					}
					mAdapter.addAll(waterList);
					mAdapter.notifyDataSetChanged();
					notData();
				} 
				catch (Exception e) 
				{
					notData();
					e.printStackTrace();
				}
			}
		}
	}
	
	public void notData(){
		if(mAdapter.getCount() == 0){
			((ViewStub)findViewById(R.id.not_data)).inflate();
			mGridView.setVisibility(View.GONE);
		}
		else{
			mGridView.setVisibility(View.VISIBLE);
		}
	}
	
	public void userPublic(View v){
		if(!TextUtils.isEmpty(mApplication.getUserId()))
		{
			Intent intent = new Intent();
			intent.setClass(this, PublicFirstActivity.class);
			startActivity(intent);
		}
		else
		{
			Intent intent = new Intent();
			intent.setClass(this, LoginActivity.class);
			startActivityForResult(intent, FrameActivity.GO_LOGIN);
		}
		
	}
	
	@Override
	public void onClick(View v) 
	{
		switch (v.getId()) 
		{
		case R.id.title_bar_left:
			HuntDetailActivity.this.finish();
			break;
			default:
				break;
		}
		
	}

}
