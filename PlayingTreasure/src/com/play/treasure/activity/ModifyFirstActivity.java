package com.play.treasure.activity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import com.play.treasure.model.Waterfall;
import com.play.treasure.network.model.NetworkBean;
import com.play.treasure.ui.modify.CategoryAdapter;
import com.play.treasure.ui.modify.CategoryInfo;
import com.play.treasure.utils.CommonProgressDialog;

public class ModifyFirstActivity extends Activity implements OnClickListener
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
		
		Intent intent = getIntent();
		Waterfall waterfall =  (Waterfall) intent.getSerializableExtra("WaterFall");
		if(waterfall != null){
			mApplication.setCategoryId(waterfall.getPlayCategory());
			mApplication.setTid(waterfall.getPlayId());
			mApplication.setBannerId(waterfall.getPlayId());
		}
		else{
			mApplication.setCategoryId("");
		}
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
	private class PublicPlaysSelectionTask extends AsyncTask<Void, Void, NetworkBean> 
	{

		CommonProgressDialog dialog = null;
		
		@Override
		protected void onPreExecute() 
		{
			super.onPreExecute();
			progressDialog.show();
			
		}

		@Override
		protected NetworkBean doInBackground(Void... params) 
		{
			try 
			{
				return mApplication.getNetApi().modifyFirst(mApplication.getTid());
			} 
			catch (Exception ex) 
			{
				ex.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(NetworkBean result) 
		{
			super.onPostExecute(result);
			if (result != null) 
			{
				progressDialog.dismiss();
				try 
				{
					JSONObject json = result.getData();
					String cate = json.getString("category");
					String selected_small_id = json.getString("selected_small_id");
					mApplication.setSelected_small_id(selected_small_id);
					ArrayList<CategoryInfo> cateoryList = new ArrayList<CategoryInfo>();
					
					JSONArray jsonArray = new JSONArray(cate);
					
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
				catch (NullPointerException e) 
				{
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
			ModifyFirstActivity.this.finish();
			break;
		case R.id.next:
			categoryId = mApplication.getCategoryId();
			if(TextUtils.isEmpty(categoryId))
			{
				Toast.makeText(ModifyFirstActivity.this, "请选择类别..", Toast.LENGTH_SHORT).show();
				return;
			}
			//new PublicFirstTask().execute();
			startActivity(intent.setClass(ModifyFirstActivity.this, ModifySecondActivity.class));
			ModifyFirstActivity.this.finish();
			break;
		default:
			break;

		}
	}
	
	
	
}
