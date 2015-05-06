package com.play.treasure.ui.selection;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.play.treasure.R;
import com.play.treasure.ui.hunt.MGridView;

/** 
* @ClassName: CategoryChecker
* @Description: 寻宝listview中Item
* @author wangchao29
* @date 2014年12月7日 下午4:06:58
* 
*/ 
public class CategoryChecker extends RelativeLayout
{
    private Context mContext;

    /** 标题 */
    private TextView mTitle;

    /** 子分类 */
    private MGridView mGridView;

    /** 
    * 子分类适配器
    */ 
    private CategoryGridAdapter mCategoryGridAdapter;
    
    /** 被选中的位置 */
    private static CheckerPosition mCheckerPosition = new CheckerPosition(-1,-1);
    
    public CategoryChecker(Context context)
    {
        super(context);
        mContext = context;
        init();
    }

    public CategoryChecker(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        mContext = context;
        init();
    }

    public CategoryChecker(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        mContext = context;
        init();
    }

    private void init()
    {
        removeAllViews();
        LayoutInflater.from(mContext).inflate(R.layout.view_category_checker,this);
        mTitle = (TextView) findViewById(R.id.title);
        mGridView = (MGridView) findViewById(R.id.category_grid);
    }

    /** 
    * @Title: setTitleText
    * @Description: 设置listview标题
    * @param @param titleText
    * @return void
    * @throws 
    */ 
    public void setTitleText(String titleText)
    {
        mTitle.setText(titleText);
    }

    /** 
    * @Title: setCatogoryList
    * @Description: 填充子分类数据
    * @param @param data
    * @return void
    * @throws 
    */ 
    public void setCatogoryList(List<ChildCategoryInfo> data)
    {
    	mCategoryGridAdapter = new CategoryGridAdapter(mContext);
        mGridView.setAdapter(mCategoryGridAdapter);
    	mCategoryGridAdapter.addAll(data);
        mCategoryGridAdapter.notifyDataSetChanged();
    }
    
    public int getCurrentParentCategoryId()
    {
        return mCategoryGridAdapter.getCurrentParentCategoryId();
    }

    /** 
    * @Title: setCurrentParentCategoryId
    * @Description: 设置当前父分类id
    * @param @param mCurrentParentCategoryId
    * @return void
    * @throws 
    */ 
    public void setCurrentParentCategoryId(int mCurrentParentCategoryId)
    {
        mCategoryGridAdapter.setCurrentParentCategoryId(mCurrentParentCategoryId);
    }

    public static CheckerPosition getCheckerPosition()
    {
        return mCheckerPosition;
    }

    /**
     * @Description:设置选择的位置
     * @Author:13075768 袁江
     * @Date 2014-11-17
     */
    public static void setCheckerPosition(CheckerPosition mCheckerPosition)
    {
        CategoryChecker.mCheckerPosition = mCheckerPosition;
    }

    /** 
    * @ClassName: CheckerPosition
    * @Description: 被选中的Item的位置
    * @author wangchao29
    * @date 2014年12月14日 下午11:51:06
    * 
    */ 
    public static class CheckerPosition
    {
        /** 父分类id:这里就是listView中的position */
        private int parentCategoryId;

        /** 子分类id:这里就是gridView中的position */
        private int childCategoryId;

        public CheckerPosition(int parentCategoryId, int childCategoryId)
        {
            this.parentCategoryId = parentCategoryId;
            this.childCategoryId = childCategoryId;
        }

        public int getParentCategoryId()
        {
            return parentCategoryId;
        }

        public void setParentCategoryId(int parentCategoryId)
        {
            this.parentCategoryId = parentCategoryId;
        }

        public int getChildCategoryId()
        {
            return childCategoryId;
        }

        public void setChildCategoryId(int childCategoryId)
        {
            this.childCategoryId = childCategoryId;
        }

    }

    /**
     * @Description:设置每个item的点击事件
     * @Author:13075768 袁江
     * @Date 2014-11-17
     */
    public void setOnItemClickListener(CategoryGridAdapter.OnItemClickListener onItemClickListener)
    {
        mCategoryGridAdapter.setOnItemClickListener(onItemClickListener);
    }
}
