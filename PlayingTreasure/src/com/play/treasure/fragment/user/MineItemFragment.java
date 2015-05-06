package com.play.treasure.fragment.user;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

import com.etsy.android.grid.StaggeredGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshStaggeredGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.play.treasure.FirstFragment;
import com.play.treasure.MallActivity;
import com.play.treasure.PlayApplication;
import com.play.treasure.R;
import com.play.treasure.FirstFragment.WaterfallTask;
import com.play.treasure.adapter.MinefallAdapter;
import com.play.treasure.model.Waterfall;
import com.play.treasure.network.model.NetworkBeanArray;
import com.play.treasure.utils.CommonProgressDialog;

public class MineItemFragment extends Fragment
{
	private StaggeredGridView mGridView;

	private MinefallAdapter mAdapter;

	private PlayApplication mApplication;

	private CommonProgressDialog progressDialog;
	
	private int currentPage = 1;
	
	private String tabId;
	
	PullToRefreshStaggeredGridView mPullRefreshListView;
	protected int scroll_y;
	
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		Log.i("onCreate", "onCreate");
		super.onCreate(savedInstanceState);
		
		mApplication = PlayApplication.getApplication();
		
	}

	@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
		View view = inflater.inflate(R.layout.view_category_all, container,false);
		Log.i("onCreateView", "onCreateView");
		Bundle mBundle = getArguments();
		tabId = mBundle.getString("category");
		mPullRefreshListView = (PullToRefreshStaggeredGridView) view.findViewById(R.id.grid_view);
		mGridView =mPullRefreshListView.getRefreshableView();
		//mGridView.setOnItemClickListener(mAdapter);
		progressDialog = CommonProgressDialog.getInstance(getActivity());
		mAdapter = new MinefallAdapter(getActivity());
	    mGridView.setAdapter(mAdapter);
	    
		mPullRefreshListView.setMode(Mode.BOTH);
		
		mPullRefreshListView.getLoadingLayoutProxy(true, false).setLastUpdatedLabel("上次刷新时间");
		mPullRefreshListView.getLoadingLayoutProxy(true, false).setPullLabel("");
		mPullRefreshListView.getLoadingLayoutProxy(true, false).setRefreshingLabel("正在刷新...");
		mPullRefreshListView.getLoadingLayoutProxy(true, false).setReleaseLabel("松开刷新");
		
		mPullRefreshListView.getLoadingLayoutProxy(false, true).setLastUpdatedLabel("上次加载时间");
		mPullRefreshListView.getLoadingLayoutProxy(false, true).setPullLabel("");
		mPullRefreshListView.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载...");
		mPullRefreshListView.getLoadingLayoutProxy(false, true).setReleaseLabel("松开加载");
		
		mGridView = mPullRefreshListView.getRefreshableView();
		mPullRefreshListView.setOnRefreshListener(new OnRefreshListener<StaggeredGridView>() {

			@SuppressLint("NewApi")
			@Override
			public void onRefresh(PullToRefreshBase<StaggeredGridView> refreshView) {
				String label = DateUtils.formatDateTime(MineItemFragment.this.getActivity(),System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

				if (refreshView.isHeaderShown()) {
					refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
					mAdapter.clear();
					mAdapter.notifyDataSetInvalidated();
					currentPage = 1;
					scroll_y = 0;
					new MinefallTask().execute();
				 	//getData();
					Log.i("", "刷新中... ");
					
					
				} else {
					Log.i("", "加载中... ");	
					//founctionlist.clear();
					//new GetBottomDataTask().execute();
					//scroll_y = founctionlist.size();
					currentPage++;
					new MinefallTask().execute();
				}

				// Update the LastUpdatedLabel
			}
		
		});
		//if(mAdapter==null||mAdapter.isEmpty())
		//{
			new MinefallTask().execute();
		//}
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
				return mApplication.getNetApi().mineWaterfall(mApplication.getUserId(), String.valueOf(currentPage), tabId);
			}
			catch(NullPointerException ex)
			{
				currentPage--;
				progressDialog.dismiss();
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
					if(waterList.size() == 0){
						currentPage--;
					}
					else{
						mAdapter.addAll(waterList);
						mAdapter.notifyDataSetChanged();
					}
					mPullRefreshListView.onRefreshComplete();
					
				} 
				catch (Exception e) 
				{	
					currentPage--;
					mPullRefreshListView.onRefreshComplete();
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		
	}
	
	
}