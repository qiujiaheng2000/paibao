package com.play.treasure.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.play.treasure.PlayApplication;
import com.play.treasure.R;
import com.play.treasure.network.model.NetworkBean;
import com.play.treasure.utils.CommonProgressDialog;

public class AboutActivity extends Activity implements OnClickListener{
	private ImageView titleLeft;

	private TextView titleCenter;
	
	private TextView TvAbout;
	
	private PlayApplication mApplication;
	
	/** 
	* 进度提示dialog
	*/ 
	private CommonProgressDialog progressDialog;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		mApplication = PlayApplication.getApplication();

		titleLeft = (ImageView)findViewById(R.id.title_bar_left);
		titleLeft.setImageResource(R.drawable.back480_03);
		titleLeft.setVisibility(View.VISIBLE);
		titleLeft.setOnClickListener(this);

		titleCenter = (TextView) findViewById(R.id.title_bar_center);
		titleCenter.setText("关于拍宝");
		titleCenter.setVisibility(View.VISIBLE);
		titleCenter.setTextSize(16);
		
		progressDialog = CommonProgressDialog.getInstance(this);
		
		TvAbout = (TextView)findViewById(R.id.aboutme);
		new loadAboutTask().execute();
	}

	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.title_bar_left:
			AboutActivity.this.finish();
			break;
		
		default:
			break;
		}
	}
	public class loadAboutTask extends AsyncTask<Void, Void, NetworkBean>
	{

		@Override
		protected void onPreExecute() {
			if(!progressDialog.isShowing()){
				progressDialog.show();
			}
			super.onPreExecute();
		}

		@Override
		protected NetworkBean doInBackground(Void... params) 
		{
			try
			{
				return mApplication.getNetApi().aboutMe();
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(NetworkBean result) 
		{
			super.onPostExecute(result);
			if(result!=null)
			{
				progressDialog.dismiss();
				try {
						
						String about = result.getResult();
						TvAbout.setText(about);
						
							
						
						
					}
				 catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
	}

}
