package com.play.treasure.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.play.treasure.FrameActivity;
import com.play.treasure.R;
import com.play.treasure.service.UpdateService;

public class SplashActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		new Handler().postDelayed(new Runnable() 
		{
			@Override
			public void run() 
			{
				Intent intentUpdate = new Intent(SplashActivity.this,UpdateService.class);
				startService(intentUpdate);
				
				Intent intent = new Intent();
				intent.setClass(SplashActivity.this, FrameActivity.class);
				startActivity(intent);
				SplashActivity.this.finish();
			}
		} , 2000);
	}
	
}
