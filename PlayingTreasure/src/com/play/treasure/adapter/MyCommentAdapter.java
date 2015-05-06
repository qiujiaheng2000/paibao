package com.play.treasure.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.play.treasure.PlayApplication;
import com.play.treasure.R;
import com.play.treasure.activity.PlayDetailActivity;
import com.play.treasure.model.MyComment;

public class MyCommentAdapter extends BaseListAdapter<MyComment> implements OnItemClickListener {
	private Context mContext;
	private LayoutInflater mInflater;
	private PlayApplication mApplication;
	public MyCommentAdapter(Context context) 
	{
		super();
		this.mContext = context;
		mInflater = LayoutInflater.from(mContext);
		mApplication = PlayApplication.getApplication();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		View view = convertView;
		final ViewHolder mHolder;
		if (view == null) 
		{
			view = mInflater.inflate(R.layout.item_mycomment, null);
			mHolder = new ViewHolder();
			mHolder.TvTitle = (TextView) view.findViewById(R.id.item_comment_text_name);
			mHolder.TvMycomment = (TextView) view.findViewById(R.id.item_comment_text_content);
			mHolder.TvAddtime = (TextView)view.findViewById(R.id.item_comment_text_time);
			view.setTag(mHolder);
		} 
		else 
		{
			mHolder = (ViewHolder) view.getTag();
		}
		final MyComment mycomment = getItem(position);
		
		mHolder.TvTitle.setText(mycomment.getTitle());
		mHolder.TvMycomment.setText(mycomment.getMycomment());
		mHolder.TvAddtime.setText(mycomment.getAddtime());
		return view;
	}

	public static class ViewHolder 
	{
		
		
		private TextView TvTitle;
		
		private TextView TvMycomment;
		
		private TextView TvAddtime;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent();
		mApplication.setTid(getItem(position).getTid());
		mApplication.setBannerId(getItem(position).getTid());
		//intent.putExtra("tid", getItem(position).getTid());
		intent.setClass(mContext, PlayDetailActivity.class);
		mContext.startActivity(intent);
	}

	
}
