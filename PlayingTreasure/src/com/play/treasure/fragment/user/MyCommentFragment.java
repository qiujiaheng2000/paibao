package com.play.treasure.fragment.user;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.WebView.FindListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.play.treasure.PlayApplication;
import com.play.treasure.R;
import com.play.treasure.adapter.MyCommentAdapter;
import com.play.treasure.model.MyComment;
import com.play.treasure.network.model.NetworkBeanArray;
import com.play.treasure.utils.CommonProgressDialog;

public class MyCommentFragment extends Fragment  
{
	private ListView mlist;
	private PlayApplication mApplication;
	private MyCommentAdapter mAdapter ;
	private static final int LOAD_MORE_SUCCESS = 1;
	private ProgressBar moreProgressBar;
	private int currentPage = 1;
	private CommonProgressDialog progressDialog;
	private View footerView;
	
	TextView tvmore;
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		mApplication = PlayApplication.getApplication();
		View view = inflater.inflate(R.layout.fragment_mycomment, container, false);
		currentPage = 1;
		progressDialog = CommonProgressDialog.getInstance(getActivity());
		new MyCommentTask().execute();
		mlist = (ListView)view.findViewById(R.id.mycomment);
		// 添加listview底部获取更多按钮（可自定义）
		footerView = inflater.inflate(R.layout.list_footview, null,false);
		moreProgressBar = (ProgressBar) footerView.findViewById(R.id.footer_progress);
		tvmore = (TextView)footerView.findViewById(R.id.text_view);
		mAdapter = new MyCommentAdapter(getActivity());
		
		mlist.addFooterView(footerView);
		mlist.setAdapter(mAdapter);
		
		mlist.setOnItemClickListener(mAdapter);
		// 获取更多监听器
				footerView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						moreProgressBar.setVisibility(View.VISIBLE);
						++currentPage;
						new MyCommentTask().execute();
					}
				});
		
		return view;
	}
	private class MyCommentTask extends AsyncTask<Void, Void, NetworkBeanArray> {
		
		@Override
		protected void onPreExecute() {
			progressDialog.show();
			super.onPreExecute();
		}

		@Override
		protected NetworkBeanArray doInBackground(Void... params) {
			try {
				return mApplication.getNetApi().myplayComment(mApplication.getUserId(), String.valueOf(currentPage));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(NetworkBeanArray result) {
			super.onPostExecute(result);
			moreProgressBar.setVisibility(View.GONE);
			
			if (result != null) 
			{
				progressDialog.dismiss();
				try 
				{
					JSONArray json = new JSONArray(result.getResult());
					List<MyComment> commentList = new ArrayList<MyComment>();
					MyComment comment = null;
					for (int i = 0; i < json.length(); i++) 
					{
						comment = new MyComment(json.getJSONObject(i));
						commentList.add(comment);
					}
					if(commentList.size() == 0){
						currentPage--;
					}
					mAdapter.addAll(commentList);
					mAdapter.notifyDataSetChanged();
				} 
				catch (JSONException e) 
				{
					currentPage--;
					e.printStackTrace();
				}
				catch (NullPointerException ex)
				{
					currentPage--;
					ex.printStackTrace();
				}
			}
		}
	}



	

}
