package com.play.treasure.ui.modify;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.play.treasure.adapter.BaseListAdapter;
import com.play.treasure.ui.modify.CategoryChecker.CheckerPosition;

/** 
* @ClassName: CategoryAdapter
* @Description: 父分类适配器
* @author wangchao29
* @date 2014年12月7日 下午4:24:18
* 
*/ 
public class CategoryAdapter extends BaseListAdapter<CategoryInfo>
{
    private Context mContext;

    public CategoryAdapter(Context mContext)
    {
        super();
        this.mContext = mContext;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        CategoryChecker categoryChecker = null;
        if (convertView == null)
        {
            categoryChecker = new CategoryChecker(mContext);
            convertView = categoryChecker;
        }
        else
        {
            categoryChecker = (CategoryChecker) convertView;
        }

        CategoryInfo categoryInfo = getItem(position);
        JSONArray json = categoryInfo.getChildJson();
        ChildCategoryInfo childInfo = null;
        List<ChildCategoryInfo> childList = new ArrayList<ChildCategoryInfo>();
        for(int i=0;i<json.length();i++)
        {
        	try 
        	{
				childInfo = new ChildCategoryInfo(json.getJSONObject(i));
				childList.add(childInfo);
			} 
        	catch (JSONException e) 
        	{
				e.printStackTrace();
			}
        }
        categoryChecker.setCatogoryList(childList);
        categoryChecker.setTitleText(categoryInfo.getParentTitle());
        // 告知这个item它的父分类是第几个item，方便做位置对比
        categoryChecker.setCurrentParentCategoryId(position);
        categoryChecker.setOnItemClickListener(new CategoryGridAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(int itemPosition)
            {
                // 记录当前选择的位置（父分类id即此处position，子分类ID为itemPosition）
                CategoryChecker.setCheckerPosition(new CheckerPosition(
                        position, itemPosition));
                // 记录位置后通知adapter刷新数据
                notifyDataSetChanged();
            }
        });
        return convertView;
    }
}
