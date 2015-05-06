package com.play.treasure.fragment.user;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.play.treasure.R;
import com.play.treasure.fragment.user.tab.TabPageIndicator;


/**
 * @author wangchao29
 */
public class MyPlayFragment extends Fragment
{
	/**
	 * Tab标题
	 */
	private static final String[] TITLE = new String[] { "求掌眼","分享","把玩","喜欢"};
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		Log.i("onCreate", "onCreate");
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
		View view = inflater.inflate(R.layout.fragment_my_play, container,false);
		
		FragmentPagerAdapter adapter = new TabPageIndicatorAdapter(getChildFragmentManager());
        ViewPager pager = (ViewPager)view.findViewById(R.id.pager);
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(3);

        TabPageIndicator indicator = (TabPageIndicator)view.findViewById(R.id.indicator);
        indicator.setViewPager(pager);
   /**  indicator.setOnPageChangeListener(new OnPageChangeListener() 
        {
			@Override
			public void onPageSelected(int arg0) 
			{
				
			//	turnToFragment(getChildFragmentManager().getFragments().get(arg0));
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) 
			{
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) 
			{
				
			}
		});**/
		
		return view;
	}
	
	private class TabPageIndicatorAdapter extends FragmentPagerAdapter 
    {
        public TabPageIndicatorAdapter(FragmentManager fm) 
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) 
        {
        	Fragment fragment = null;
        	if(position == 3){
        		fragment = new MyFavoriteFragment();
        	}
        	else{
        		fragment = new MineItemFragment();
            	
                Bundle args = new Bundle();
                int index = position;
                if(position == 1){
                	index = position + 1;
                }
                else if(position == 2){
                	index = position - 1;
                }
                args.putString("category", String.valueOf(index+2));  
                fragment.setArguments(args); 
        	}
        	
        	
            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) 
        {
            return TITLE[position % TITLE.length];
        }

        @Override
        public int getCount() 
        {
            return TITLE.length;
        }
    }
	
	 /**
	 2      * Fragment跳转
	 3      * @param fm
	 4      * @param fragmentClass
	 5      * @param tag
	 6      * @param args
	 7      */
 public void turnToFragment(Class<? extends Fragment> fromFragmentClass, Class<? extends Fragment> toFragmentClass, Bundle args) {

	FragmentManager fm = getChildFragmentManager();
	 //被切换的Fragment标签
	String fromTag = fromFragmentClass.getSimpleName();
	//切换到的Fragment标签
	String toTag = toFragmentClass.getSimpleName();
	//查找切换的Fragment
	Fragment fromFragment = fm.findFragmentByTag(fromTag);
	Fragment toFragment = fm.findFragmentByTag(toTag);
	//如果要切换到的Fragment不存在，则创建
	if (toFragment == null) {
		try {
			toFragment = toFragmentClass.newInstance();
			toFragment.setArguments(args);
			} catch (java.lang.InstantiationException e) {
				e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					}
		}
	//如果有参数传递，
	if( args != null && !args.isEmpty() ) {
		toFragment.getArguments().putAll(args);
		}
	//Fragment事务
	FragmentTransaction ft = fm.beginTransaction();
	//设置Fragment切换效果
	ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
			android.R.anim.fade_in, android.R.anim.fade_out);
	/**
	* 如果要切换到的Fragment没有被Fragment事务添加，则隐藏被切换的Fragment，添加要切换的Fragment
	* 否则，则隐藏被切换的Fragment，显示要切换的Fragment
	*/
	if( !toFragment.isAdded()) {
		ft.hide(fromFragment).add(R.id.pager, toFragment, toTag);
		}
	else {
     ft.hide(fromFragment).show(toFragment);
	}
	//添加到返回堆栈
	//        ft.addToBackStack(tag);
	//不保留状态提交事务
	ft.commitAllowingStateLoss();
	    }
}
