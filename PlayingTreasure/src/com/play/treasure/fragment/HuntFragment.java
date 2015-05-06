package com.play.treasure.fragment;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.play.treasure.PlayApplication;
import com.play.treasure.R;
import com.play.treasure.activity.SearchResultActivity;
import com.play.treasure.network.model.NetworkBeanArray;
import com.play.treasure.ui.hunt.CategoryAdapter;
import com.play.treasure.ui.hunt.CategoryInfo;
import com.play.treasure.utils.CommonProgressDialog;

/** 
* @ClassName: HuntFragment
* @Description: 寻宝页面
* @author wangchao29
* @date 2015年1月11日 下午4:10:00
* 
*/ 
public class HuntFragment extends Fragment
{
	private TextView titleCenter;

	private View view;

	private ListView mListView;

	private CategoryAdapter mCategoryAdapter;
	
	private EditText EtSearch;
	
	private String mInput;
	
	private PlayApplication mApplication;
	
	private CommonProgressDialog progressDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
		view = inflater.inflate(R.layout.fragment_hunt_category, container,false);
		mApplication = PlayApplication.getApplication();

		titleCenter = (TextView)view.findViewById(R.id.title_bar_center);
		titleCenter.setText("寻  宝");
		titleCenter.setTextSize(16);
		titleCenter.setVisibility(View.VISIBLE);
		
		progressDialog = CommonProgressDialog.getInstance(getActivity());

		mListView = (ListView) view.findViewById(R.id.listview);
		mCategoryAdapter = new CategoryAdapter(getActivity());
		mListView.setAdapter(mCategoryAdapter);
		
		EtSearch = (EditText)view.findViewById(R.id.search_edit);
		EtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() { 
			

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId==EditorInfo.IME_ACTION_SEND ||(event!=null&&event.getKeyCode()== KeyEvent.KEYCODE_ENTER)) 
				{
					SearchControll();
					return true;
				}
				return false;
			}       
			});

		

			new HuntTask().execute();

		return view;
	}
	
	private class HuntTask extends AsyncTask<Void, Void, NetworkBeanArray> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog.show();
		}

		
		@Override
		protected NetworkBeanArray doInBackground(Void... params) {
			try {
				return mApplication.getNetApi().huntCategory();
			} catch (Exception ex) {
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
				catch (NullPointerException e) 
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	 /**
		 * 功能描述: 搜索控制
		 * 〈功能详细描述〉
		 *
		 * @see [相关类/方法](可选)
		 * @since [产品/模块版本](可选)
		 */
		private void SearchControll()
		{
			mInput = EtSearch.getText().toString();
			
			if(TextUtils.isEmpty(mInput))
			{
				Toast.makeText(getActivity(), "请输入搜索内容", Toast.LENGTH_SHORT).show();
				return;
			}
			else
			{
				new SearchTask().execute();
			}
		}
		
		/**
		 *  搜索线程
		 *
		 * @author 王超
		 * @see [相关类/方法]（可选）
		 * @since [产品/模块版本] （可选）
		 */
		private class SearchTask extends AsyncTask<Void, Void, NetworkBeanArray>
		{
			@Override
			protected void onPreExecute() 
			{
				super.onPreExecute();
				progressDialog.setMsg("查询中...");
			}

			@Override
			protected NetworkBeanArray doInBackground(Void... params) 
			{
				try
				{
					mInput = EtSearch.getText().toString();
					return mApplication.getNetApi().search(mInput, "1", "1", "1", "0", "0");
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(NetworkBeanArray result) 
			{
				super.onPostExecute(result);
				if(result!=null)
				{
					progressDialog.dismiss();
					String data = result.getResult();
					if(null!=data)
					{
						Intent intent = new Intent();
						intent.putExtra("result", data);
						intent.setClass(getActivity(), SearchResultActivity.class);
						startActivity(intent);
					}
					else
					{
						Toast.makeText(getActivity(), "sorry 没有找到您想要的结果..", Toast.LENGTH_SHORT).show();
					}
				}
			}
		}
}
