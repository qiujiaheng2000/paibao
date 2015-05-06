package com.play.treasure.activity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.play.treasure.PlayApplication;
import com.play.treasure.R;
import com.play.treasure.network.model.NetworkBean;
import com.play.treasure.network.model.NetworkBeanArray;
import com.play.treasure.ui.selection.CategoryAdapter;
import com.play.treasure.ui.selection.CategoryInfo;
import com.play.treasure.utils.CommonProgressDialog;
import com.play.treasure.utils.ToastUtil;

public class PublicFirstActivity extends Activity implements OnClickListener
{
	private ImageView titleLeft;
	
	private TextView titleCenter;
	
	private ListView mListView;

	private CategoryAdapter mCategoryAdapter;
	
	private PlayApplication mApplication;

	private Button Next;
	
	private String categoryId;
	
	private String code;
	
	private String tid;
	
	private CommonProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_publicplays_selection);
		mApplication =PlayApplication.getApplication();
		mApplication.setCategoryId("");
		titleLeft = (ImageView)findViewById(R.id.title_bar_left);
		titleLeft.setImageResource(R.drawable.back480_03);
		titleLeft.setVisibility(View.VISIBLE);
		titleLeft.setOnClickListener(this);
		titleCenter = (TextView) findViewById(R.id.title_bar_center);
		titleCenter.setText("选择分类");
		titleCenter.setVisibility(View.VISIBLE);
		titleCenter.setTextSize(16);
		progressDialog = new CommonProgressDialog(this, R.style.dialog);
		progressDialog.setMsg("加载中...");
		Next = (Button) findViewById(R.id.next);
		Next.setOnClickListener(this);
		mListView = (ListView)findViewById(R.id.listview);
	    new PublicPlaysSelectionTask().execute();
	    
	    mCategoryAdapter = new CategoryAdapter(this);
		mListView.setAdapter(mCategoryAdapter);
	}
	private class PublicPlaysSelectionTask extends AsyncTask<Void, Void, NetworkBeanArray> 
	{

		
		@Override
		protected void onPreExecute() 
		{
			super.onPreExecute();
			progressDialog.show();
			
		}

		@Override
		protected NetworkBeanArray doInBackground(Void... params) 
		{
			try 
			{
				return mApplication.getNetApi().publicCategory();
			} 
			catch (Exception ex) 
			{
				ex.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(NetworkBeanArray result) 
		{
			super.onPostExecute(result);
			if (result != null) 
			{
				progressDialog.dismiss();
				try 
				{
					ArrayList<CategoryInfo> cateoryList = new ArrayList<CategoryInfo>();
					
					JSONArray jsonArray = new JSONArray(result.getResult());
					
					CategoryInfo categoryInfo = null;
					
					for (int i = 0; i < jsonArray.length(); i++) 
					{
						categoryInfo = new CategoryInfo(jsonArray.getJSONObject(i));
						cateoryList.add(categoryInfo);
					}
					mCategoryAdapter.addAll(cateoryList);
					mCategoryAdapter.notifyDataSetChanged();
				} 
				catch (JSONException e) 
				{
					e.printStackTrace();
				}
				catch (NullPointerException e) {
					e.printStackTrace();
				}
			}
		}
	}
	@Override
	public void onClick(View v) 
	{
		Intent intent = new Intent();
		switch (v.getId()) 
		{
		case R.id.title_bar_left:
			mApplication.setCategoryId("");
			PublicFirstActivity.this.finish();
			break;
		case R.id.next:
			categoryId = mApplication.getCategoryId();
			if(TextUtils.isEmpty(categoryId))
			{
				Toast.makeText(PublicFirstActivity.this, "请选择类别..", Toast.LENGTH_SHORT).show();
				return;
			}
			//new PublicFirstTask().execute();
			startActivity(intent.setClass(PublicFirstActivity.this, PublicSecondActivity.class));
			PublicFirstActivity.this.finish();
			break;
		default:
			break;

		}
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	/** 
	* @ClassName: SelectTask
	* @Description: 上传发布类别
	* @author wangchao29
	* @date 2014年12月3日 下午11:11:04
	* 
	*/ 
	private class PublicFirstTask extends AsyncTask<Void, Void, NetworkBean>
	{
		/** (非 Javadoc) 
		* Title: doInBackground
		* Description:
		* @param params
		* @return
		* @see android.os.AsyncTask#doInBackground(java.lang.Object[])
		*/ 
		@Override
		protected NetworkBean doInBackground(Void... params) 
		{
			try
			{
				return mApplication.getNetApi().publicFirst(categoryId, mApplication.getUserId());
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(NetworkBean result) 
		{
			super.onPostExecute(result);
			if (result != null) {
				try {
					setCode(result.getCode());
					//Toast.makeText(PublicFirstActivity.this, result.getCode(), Toast.LENGTH_SHORT).show();
					tid = result.getResult();
					if(TextUtils.isEmpty(tid))
					{
						ToastUtil.showMessage("请选择类别...");
						return;
					}
					Intent intent = new Intent();
					intent.putExtra("tid", tid);
					startActivity(intent.setClass(PublicFirstActivity.this, PublicSecondActivity.class));

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
