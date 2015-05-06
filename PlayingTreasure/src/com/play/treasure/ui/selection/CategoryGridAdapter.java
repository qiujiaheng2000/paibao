package com.play.treasure.ui.selection;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.play.treasure.PlayApplication;
import com.play.treasure.R;
import com.play.treasure.adapter.BaseListAdapter;

/**
 * @ClassName: CategoryGridAdapter
 * @Description: 子分类gridview
 * @author wangchao29
 * @date 2014年12月7日 下午2:34:41
 * 
 */
public class CategoryGridAdapter extends BaseListAdapter<ChildCategoryInfo> {
	private Context mContext;

	/** 当前分类的父分类ID */
	private int mCurrentParentCategoryId = -1;
	
	private PlayApplication mApplication;

	public CategoryGridAdapter(Context mContext) {
		super();
		this.mContext = mContext;
		mApplication = PlayApplication.getApplication();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		TextView categroyName = null;
		if (convertView == null) {
			categroyName = new TextView(mContext);
			convertView = categroyName;
		} else {
			categroyName = (TextView) convertView;
		}
		final ChildCategoryInfo info = getItem(position);
		categroyName.setText(info.getChildTitle());
		categroyName.setTextColor(Color.BLACK);
		categroyName.setGravity(Gravity.CENTER);

		// 被选中位置的父分类ID
		int parentCategoryId = CategoryChecker.getCheckerPosition()
				.getParentCategoryId();
		// 被选中位置的子分类ID
		int childCategoryId = CategoryChecker.getCheckerPosition()
				.getChildCategoryId();
		if (parentCategoryId == mCurrentParentCategoryId
				&& position == childCategoryId) {
			categroyName.setBackgroundResource(R.drawable.checked);
		} else {
			categroyName.setBackgroundResource(R.drawable.unchecked);
		}

		categroyName.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				if (mOnItemClickListener != null) 
				{
					mOnItemClickListener.onItemClick(position);
					notifyDataSetChanged();
					mApplication.setCategoryId(info.getChildId());
				}
			}
		});

		return convertView;
	}

	/** item点击事件 */
	private OnItemClickListener mOnItemClickListener = null;

	public OnItemClickListener getOnItemClickListener() {
		return mOnItemClickListener;
	}

	/** 
	* @Title: setOnItemClickListener
	* @Description: 设置item点击事件
	* @param @param mOnItemClickListener
	* @return void
	* @throws 
	*/ 
	public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
		this.mOnItemClickListener = mOnItemClickListener;
	}

	/**
	 * @Title:
	 * @Description:item点击事件回调接口
	 * @Author:13075768 袁江
	 * @Since:2014-11-17
	 * @Version:
	 */
	public interface OnItemClickListener {
		void onItemClick(int position);
	}

	public int getCurrentParentCategoryId() {
		return mCurrentParentCategoryId;
	}

	/**
	 * @Description:设置父分类ID
	 * @Author:13075768 袁江
	 * @Date 2014-11-17
	 */
	public void setCurrentParentCategoryId(int mCurrentParentCategoryId) {
		this.mCurrentParentCategoryId = mCurrentParentCategoryId;
	}

}
