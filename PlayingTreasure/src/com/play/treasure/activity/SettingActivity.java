package com.play.treasure.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.play.treasure.PlayApplication;
import com.play.treasure.R;
import com.play.treasure.service.UpdateService;

public class SettingActivity extends Activity implements OnClickListener
{
	private PlayApplication mApplication;
	
	private ImageView titleLeft;

	private TextView titleCenter;
	
	private TextView TvUserData;
	private TextView TvModifyPwd;
	private TextView TvFeedback;
	private TextView TvAbout;
	private TextView TvContact;
	private TextView TvUpdate;
	private Button SettingLogout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		
		mApplication = PlayApplication.getApplication();

		titleLeft = (ImageView)findViewById(R.id.title_bar_left);
		titleLeft.setImageResource(R.drawable.back480_03);
		titleLeft.setVisibility(View.VISIBLE);
		titleLeft.setOnClickListener(this);

		titleCenter = (TextView) findViewById(R.id.title_bar_center);
		titleCenter.setText("设置");
		titleCenter.setVisibility(View.VISIBLE);
		titleCenter.setTextSize(16);
		
		TvUserData = (TextView)findViewById(R.id.setting_user_data);
		TvUserData.setOnClickListener(this);
		TvModifyPwd = (TextView)findViewById(R.id.setting_modify_pwd);
		TvModifyPwd.setOnClickListener(this);
		TvFeedback = (TextView)findViewById(R.id.setting_feed_back);
		TvFeedback.setOnClickListener(this);
		TvAbout = (TextView)findViewById(R.id.setting_about_us);
		TvAbout.setOnClickListener(this);
		TvContact = (TextView)findViewById(R.id.setting_contact);
		TvContact.setOnClickListener(this);
		TvUpdate = (TextView)findViewById(R.id.setting_update);
		TvUpdate.setOnClickListener(this);
		SettingLogout = (Button)findViewById(R.id.setting_logout);
		SettingLogout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) 
	{
		Intent intent = new Intent();
		switch(v.getId())
		{
		case R.id.title_bar_left:
			SettingActivity.this.finish();
			break;
		case R.id.setting_user_data:
			intent.setClass(SettingActivity.this, UserDataActivity.class);
			startActivity(intent);
			break;
		case R.id.setting_modify_pwd:
			intent.setClass(SettingActivity.this, ModifyPwdActivity.class);
			startActivity(intent);
			break;
		case R.id.setting_feed_back:
			intent.setClass(SettingActivity.this, FeedBackActivity.class);
			startActivity(intent);
			break;
		case R.id.setting_about_us:
			intent.setClass(SettingActivity.this, AboutActivity.class);
			startActivity(intent);
			break;
		case R.id.setting_contact:
			intent.setClass(SettingActivity.this, ContactActivity.class);
			startActivity(intent);
			break;
		case R.id.setting_update:
			Log.d("update", "onclick");
			Intent intentUpdate = new Intent();
			intentUpdate.setClass(SettingActivity.this, UpdateService.class);
			intentUpdate.putExtra("show", true);
			startService(intentUpdate);
			break;
		case R.id.setting_logout:
			mApplication.setUserId("");
			mApplication.setSharedPreLoginName("");
			mApplication.setSharedPreLoginPwd("");
			intent.setClass(SettingActivity.this, LoginActivity.class);
			startActivity(intent);
			SettingActivity.this.finish();
			break;
		default:
			break;
		}
	}

}
