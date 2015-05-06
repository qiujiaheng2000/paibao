package com.play.treasure.adapter;
import java.util.Random;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.etsy.android.grid.util.DynamicHeightImageView;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.bitmap.callback.DefaultBitmapLoadCallBack;
import com.play.treasure.Functions;
import com.play.treasure.PlayApplication;
import com.play.treasure.R;
import com.play.treasure.activity.ModifyFirstActivity;
import com.play.treasure.activity.PlayDetailMineActivity;
import com.play.treasure.model.Waterfall;
import com.play.treasure.network.NetworkConfig;
import com.play.treasure.network.model.NetworkBean;
import com.play.treasure.utils.ToastUtil;

public class MinefallAdapter extends BaseListAdapter<Waterfall> implements OnClickListener
{
	private Context mContext;
	private final LayoutInflater mLayoutInflater;
	private final Random mRandom;
	private BitmapUtils bitmapUtils;
	private static final SparseArray<Double> sPositionHeightRatios = new SparseArray<Double>();
	private PlayApplication mApplication;
	
	public MinefallAdapter(Context context) 
	{
		super();
		this.mContext = context;
		this.mLayoutInflater = LayoutInflater.from(mContext);
		this.mRandom = new Random();
		bitmapUtils = Functions.initbimapxUtils(bitmapUtils, context);
		mApplication = PlayApplication.getApplication();
	}

	@Override
	public View getView(final int position, View convertView,final ViewGroup parent) 
	{
		ViewHolder mHolder;
		View view  = convertView;
		if (view == null) 
		{
			view = mLayoutInflater.inflate(R.layout.mine_item_water_fall,parent, false);
			mHolder = new ViewHolder();
			mHolder.imgView = (DynamicHeightImageView) view.findViewById(R.id.imgView);
			mHolder.imgView.setOnClickListener(this);
			mHolder.playCategory = (ImageView) view
					.findViewById(R.id.water_fall_category);
			mHolder.playName = (TextView) view
					.findViewById(R.id.water_fall_play_name);
			mHolder.playStack = (TextView) view
					.findViewById(R.id.water_fall_stack);
			mHolder.playComment = (TextView) view
					.findViewById(R.id.water_fall_comment);
			mHolder.playDistance = (TextView) view
					.findViewById(R.id.water_fall_distance);
			mHolder.playModify = (TextView)view.findViewById(R.id.water_fall_modify);
			mHolder.playDelete = (TextView)view.findViewById(R.id.water_fall__delete);

			view.setTag(mHolder);
		} 
		else 
		{
			mHolder = (ViewHolder) view.getTag();
		}

		final Waterfall waterFall = getItem(position);
		mHolder.imgView.setTag(position);
	//	double positionHeight = getPositionRatio(position);

	//	mHolder.imgView.setHeightRatio(positionHeight);
		//ImageLoader.getInstance().displayImage(NetworkConfig.baseImgUrl+waterFall.getImgUrl(), mHolder.imgView);
		bitmapUtils.display(mHolder.imgView, NetworkConfig.baseImgUrl+waterFall.getImgUrl(),
			 	new CustomBitmapLoadCallBack(mHolder.imgView));
		try {
			switch (Integer.parseInt(waterFall.getPlayCategory())) {
			case 1:
				mHolder.playCategory.setImageResource(R.drawable.tag_sale);
				break;
			case 2:
				mHolder.playCategory.setImageResource(R.drawable.tag_zhangyan);
				break;
			case 3:
				mHolder.playCategory.setImageResource(R.drawable.tag_play);
				break;
			case 4:
				mHolder.playCategory.setImageResource(R.drawable.tag_share);
				break;
			}
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
		}
		mHolder.playName.setText(waterFall.getPlayDes());
		mHolder.playComment.setText(waterFall.getCommentNum());
		mHolder.playStack.setText(waterFall.getStackNum());
		mHolder.playDistance.setText(waterFall.getDistance());

		if (TextUtils.isEmpty(waterFall.getCommentNum())) 
		{
			mHolder.playComment.setText("0");
		} 
		else 
		{
			mHolder.playComment.setText(waterFall.getCommentNum());
		}
		if (TextUtils.isEmpty(waterFall.getStackNum())) 
		{
			mHolder.playStack.setText("0");
		} 
		else 
		{
			mHolder.playStack.setText(waterFall.getStackNum());
		}
		if (waterFall.getDistance() == null) 
		{
			mHolder.playDistance.setText("0km");
		} 
		else 
		{
			mHolder.playDistance.setText(waterFall.getDistance() + "km");
		}
		
		mHolder.playModify.setOnClickListener(new HandleListener(position));
		mHolder.playDelete.setOnClickListener(new HandleListener(position));
		
		return view;
	}
	
	private class HandleListener implements OnClickListener
	{
		private int position;
		
		public HandleListener(int position) 
		{
			this.position = position;
		}

		@Override
		public void onClick(final View v) 
		{
			Intent intent = new Intent();
			switch(v.getId())
			{
			case R.id.water_fall_modify:
				intent.setClass(mContext, ModifyFirstActivity.class);
				intent.putExtra("WaterFall", getItem(position));
				mContext.startActivity(intent);
				break;
			case R.id.water_fall__delete:
				AlertDialog alert = new AlertDialog.Builder(mContext).create();
				alert.setTitle("删除宝贝？");
				alert.setMessage("确定吗？");
				alert.setButton(DialogInterface.BUTTON_NEGATIVE, "否",new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
					}
				});
				alert.setButton(DialogInterface.BUTTON_POSITIVE, "是",new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) 
					{
						new DeleteMineTask().execute(position);
					}
				});
				alert.show();
			    break;
			}
		}
	}

	static class ViewHolder 
	{
		DynamicHeightImageView imgView;
		
		/**
		 * 宝贝类别
		 */
		private ImageView playCategory;

		/**
		 * 宝贝名称
		 */
		private TextView playName;

		/**
		 * 点赞数目
		 */
		private TextView playStack;

		/**
		 * 评论数目
		 */
		private TextView playComment;

		/**
		 * 宝贝名称
		 */
		private TextView playDistance;
		
		/** 
		* 宝贝修改
		*/ 
		private TextView playModify;
		
		/** 
		* 宝贝删除
		*/ 
		private TextView playDelete;
	}

	private double getPositionRatio(final int position) 
	{
		double ratio = sPositionHeightRatios.get(position, 0.0);
		if (ratio == 0) 
		{
			ratio = getRandomHeightRatio();
			sPositionHeightRatios.append(position, ratio);
		}
		return ratio;
	}

	private double getRandomHeightRatio() 
	{
		return (mRandom.nextDouble() / 2.0) + 1.0;
	}
	
	/** (非 Javadoc) 
	* Title: onItemClick
	* Description:
	* @param parent
	* @param view
	* @param position
	* @param id
	* @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
	*/ 
	@Override
	public void onClick(View view) {
		int position = (Integer) view.getTag();
		try 
		{
			Intent intent = new Intent();
			intent.setClass(mContext, PlayDetailMineActivity.class);
			mContext.startActivity(intent);
			mApplication.setBannerId(getItem(position).getPlayId());
			mApplication.setTid(getItem(position).getPlayId());
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	 public class CustomBitmapLoadCallBack extends DefaultBitmapLoadCallBack<ImageView> {
	       private final View holder;

	       public CustomBitmapLoadCallBack(View holder) {
	           this.holder = holder;
	       }
	       @Override
	       public void onLoading(ImageView container, String uri, BitmapDisplayConfig config, long total, long current) {
	       
	       }

	       @Override
	       public void onLoadCompleted(ImageView container, String uri, Bitmap bitmap, BitmapDisplayConfig config, BitmapLoadFrom from) {
	           //super.onLoadCompleted(container, uri, bitmap, config, from);
	           Functions.fadeInDisplay(container, bitmap, Functions.sw);
	       }
	   }
	/** 
	* @ClassName: DeleteMineTask
	* @Description: 删除我的宝贝
	* @author wangchao29
	* @date 2015年1月10日 下午12:53:48
	* 
	*/ 
	private class DeleteMineTask extends AsyncTask<Integer, Void, NetworkBean>
	{
		private int position;
		@Override
		protected void onPreExecute() 
		{
			super.onPreExecute();
		}
		@Override
		protected NetworkBean doInBackground(Integer... params) 
		{
			position = params[0];
			try 
			{
				return mApplication.getNetApi().deleteMine(list.get(position).getPlayId());
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
				String msg = result.getMessage();
				String code = result.getCode();
				if(code.equals("10000"))
				{
					list.remove(position);
					notifyDataSetChanged();
					ToastUtil.showMessage("删除成功");
				}
				else
				{
					ToastUtil.showMessage("删除失败");
				}
				
			}
		}
		
		
	}
}