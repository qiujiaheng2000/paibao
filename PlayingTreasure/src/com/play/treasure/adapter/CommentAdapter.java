package com.play.treasure.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.play.treasure.R;
import com.play.treasure.model.Comment;
import com.play.treasure.network.NetworkConfig;
import com.play.treasure.widget.CircularImage;

public class CommentAdapter extends BaseListAdapter<Comment>
{
	private Context mContext;
	private LayoutInflater mInflater;
	
	public CommentAdapter(Context context) 
	{
		super();
		this.mContext = context;
		mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		View view = convertView;
		final ViewHolder mHolder;
		if (view == null) 
		{
			view = mInflater.inflate(R.layout.item_comment, null);
			mHolder = new ViewHolder();
			mHolder.TvName = (TextView) view.findViewById(R.id.item_comment_text_name);
			mHolder.TvTime = (TextView) view.findViewById(R.id.item_comment_text_time);
			mHolder.TvContent = (TextView) view.findViewById(R.id.item_comment_text_content);
			mHolder.cover_user_photo = (CircularImage)view.findViewById(R.id.cover_user_photo);
			view.setTag(mHolder);
		} 
		else 
		{
			mHolder = (ViewHolder) view.getTag();
		}
		final Comment comment = getItem(position);
		ImageLoader.getInstance().displayImage(NetworkConfig.baseImgUrl+comment.getHeadPic(), mHolder.cover_user_photo);
		mHolder.TvName.setText(comment.getUserName());
		mHolder.TvTime.setText(comment.getTime());
		mHolder.TvContent.setText(comment.getContent());
	
		return view;
	}

	public static class ViewHolder 
	{
		
		private TextView TvName;
		
		private TextView TvTime;
		
		private TextView TvContent;
		
		private CircularImage cover_user_photo;
	}

}
