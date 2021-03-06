package com.play.treasure.fragment.user;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.etsy.android.grid.StaggeredGridView;
import com.handmark.pulltorefresh.library.PullToRefreshStaggeredGridView;
import com.play.treasure.PlayApplication;
import com.play.treasure.R;
import com.play.treasure.adapter.MinefallAdapter;
import com.play.treasure.model.Waterfall;
import com.play.treasure.network.model.NetworkBeanArray;
import com.play.treasure.utils.CommonProgressDialog;
import com.play.treasure.waterfall.WaterfallAdapter;

public class OnSaleFragment extends Fragment
{
	private StaggeredGridView mGridView;

	private MinefallAdapter mAdapter;

	PullToRefreshStaggeredGridView mPullRefreshListView;
	
	private int currentPage = 1;
	
	private PlayApplication mApplication;
	private CommonProgressDialog progressDialog;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		mApplication = PlayApplication.getApplication();
		progressDialog = CommonProgressDialog.getInstance(getActivity());
	}

	@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
		View view = inflater.inflate(R.layout.view_category_all, container,false);
		
		mPullRefreshListView=(PullToRefreshStaggeredGridView) view.findViewById(R.id.grid_view);
		mGridView = mPullRefreshListView.getRefreshableView(); 
		mAdapter = new MinefallAdapter(getActivity());
		new MinefallTask().execute();

		mGridView.setAdapter(mAdapter);
		//mGridView.setOnItemClickListener(mAdapter);
		
		return view;
	}
	
	private class MinefallTask extends AsyncTask<Void, Void, NetworkBeanArray> 
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
				return mApplication.getNetApi().mySale(mApplication.getUserId(), String.valueOf(currentPage));
			}
			catch(NullPointerException ex)
			{
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
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		}
	}

}