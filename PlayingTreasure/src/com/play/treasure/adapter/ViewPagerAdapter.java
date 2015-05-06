package com.play.treasure.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * @author wangchao29
 * 
 * 首页顶部tab 适配器
 *
 */
public class ViewPagerAdapter extends FragmentPagerAdapter 
{

	private List<Fragment> fragments;
	
	private List<String> titleList;

	public ViewPagerAdapter(FragmentManager fm, List<Fragment> fragments,List<String> titleList) 
	{
		super(fm);
		this.fragments = fragments;
		this.titleList = titleList;
	}

	@Override
	public int getCount() 
	{
		return fragments == null ? 0 : fragments.size();
	}

	@Override
	public Fragment getItem(int arg0) 
	{
		 return (fragments == null || fragments.size() == 0) ? null : fragments.get(arg0);
	}
	
	@Override  
    public CharSequence getPageTitle(int position) 
	{  
        return titleList.get(position);  
    } 
}
