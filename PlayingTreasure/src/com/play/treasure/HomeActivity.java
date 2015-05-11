package com.play.treasure;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.play.treasure.activity.SearchResultActivity;
import com.play.treasure.fragment.item.ShopItemFragment;
import com.play.treasure.network.model.NetworkBeanArray;
import com.play.treasure.ui.tab.TabPageIndicator;
import com.play.treasure.utils.CommonProgressDialog;

public class HomeActivity extends BackActivity
{
	private TextView titleCenter;
	
	private EditText EtSearch;
	
	private String mInput;
	
	private PlayApplication mApplication;

	/**
	 * Tab标题
	 */
	private static final String[] TITLE = new String[] { "核桃", "菩提", "红木", "核雕","珠宝玉石", "琥珀蜜蜡", "玛瑙珊瑚", "牙骨佛牌" };

	private CommonProgressDialog progressDialog;
	
	private ImageView arrowLeft;
	
	private ImageView arrowRight;
	
	
	
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_mall);
		//View view = inflater.inflate(R.layout.fragment_mall, container,false);
		mApplication = PlayApplication.getApplication();


        final ViewPager pager = (ViewPager)findViewById(R.id.pager1);
        if(pager==null)
        {
        	Log.i("pager", "pager");
        }
		titleCenter = (TextView)findViewById(R.id.title_bar_center);
		titleCenter.setText("商  城");
//		titleCenter.setTextSize(16);
		titleCenter.setVisibility(View.VISIBLE);
		
		progressDialog = CommonProgressDialog.getInstance(this);
		arrowLeft = (ImageView)findViewById(R.id.arrow_left);
		arrowLeft.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//if(pager.getCurrentItem() != 0){
					pager.setCurrentItem(pager.getCurrentItem() - 1);
				//}
			}
		});
		arrowRight = (ImageView)findViewById(R.id.arrow_right);
		arrowRight.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//if(pager.getCurrentItem() != pager.getChildCount() - 1){
					pager.setCurrentItem(pager.getCurrentItem() + 1);
				//}
			}
		});
		EtSearch = (EditText)findViewById(R.id.search_edit);
		EtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() { 
			

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId==EditorInfo.IME_ACTION_SEND ||(event!=null&&event.getKeyCode()== KeyEvent.KEYCODE_ENTER)) 
				{
					SearchControll();
					return true;
				}
				return false;
			}       
			});

	    FragmentPagerAdapter adapter = new TabPageIndicatorAdapter(HomeActivity.this.getSupportFragmentManager());
	    pager.setAdapter(adapter);
        TabPageIndicator indicator = (TabPageIndicator)findViewById(R.id.indicator1);
        indicator.setViewPager(pager);
        
        indicator.setOnPageChangeListener(new OnPageChangeListener() 
        {
			@Override
			public void onPageSelected(int arg0) 
			{
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) 
			{
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) 
			{
				
			}
		});
	
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
        	ShopItemFragment fragment = new ShopItemFragment();
        	
            Bundle args = new Bundle();  
            args.putString("category", String.valueOf(position+1));   
            fragment.setArguments(args); 
        	
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
	 * 功能描述: 搜索控制
	 * 〈功能详细描述〉
	 *
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	private void SearchControll()
	{
		mInput = EtSearch.getText().toString();
		
		if(TextUtils.isEmpty(mInput))
		{
			Toast.makeText(HomeActivity.this, "请输入搜索内容", Toast.LENGTH_SHORT).show();
			return;
		}
		else
		{
			new SearchTask().execute();
		}
	}
	
	/**
	 *  搜索线程
	 *
	 * @author 王超
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	@SuppressLint("NewApi")
	private class SearchTask extends AsyncTask<Void, Void, NetworkBeanArray>
	{
		@Override
		protected void onPreExecute() 
		{
			super.onPreExecute();
			if(!HomeActivity.this.isFinishing()&&!progressDialog.isShowing()){
				progressDialog.show();
			}
		}

		@Override
		protected NetworkBeanArray doInBackground(Void... params) 
		{
			try
			{
				mInput = EtSearch.getText().toString();
				return mApplication.getNetApi().search(mInput, "1", "1", "1", "0", "0");
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(NetworkBeanArray result) 
		{
			super.onPostExecute(result);
			if(result!=null)
			{
				progressDialog.dismiss();
				String data = result.getResult();
				if(null!=data)
				{
					Intent intent = new Intent();
					intent.putExtra("result", data);
					intent.setClass(HomeActivity.this, SearchResultActivity.class);
					startActivity(intent);
				}
				else
				{
					Toast.makeText(HomeActivity.this, "sorry 没有找到您想要的结果..", Toast.LENGTH_SHORT).show();
				}
			}
		}
	}
}