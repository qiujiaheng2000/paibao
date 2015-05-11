package com.play.treasure.activity;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.play.treasure.FrameActivity;
import com.play.treasure.PlayApplication;
import com.play.treasure.R;
import com.play.treasure.network.model.NetworkBean;
import com.play.treasure.utils.CommonProgressDialog;

public class LoginActivity extends Activity implements OnClickListener 
{
	private ImageView titleLeft;
	
	private TextView titleCenter;
	
	/** 
	* 进度提示dialog
	*/ 
	private CommonProgressDialog progressDialog;

	/**
	 * 系统全局变量类
	 */
	private PlayApplication mApplication;

	/**
	 * 登录tag
	 */
	public static final String TAG = "LoginActivity";
	/**
	 * 用户名
	 */
	private EditText Etusername;
	private String username;
	/**
	 * 密码
	 */
	private EditText Etpwd;
	private String pwd;
	/**
	 * 登陆按钮
	 */
	private Button Login;

	/**
	 * 忘记密码
	 */
	private TextView Fgpwd;

	/**
	 * 注册
	 */
	private Button Register;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		mApplication = PlayApplication.getApplication();
		titleLeft = (ImageView)findViewById(R.id.title_bar_left);
		titleLeft.setImageResource(R.drawable.back480_03);
		titleLeft.setVisibility(View.VISIBLE);
		titleLeft.setOnClickListener(this);
		titleCenter = (TextView) findViewById(R.id.title_bar_center);
		titleCenter.setText("登陆");
		titleCenter.setVisibility(View.VISIBLE);
//		titleCenter.setTextSize(16);
		
		progressDialog = CommonProgressDialog.getInstance(this);
		
		Etusername = (EditText) findViewById(R.id.username);
		Etpwd = (EditText) findViewById(R.id.pwd);
		Login = (Button) findViewById(R.id.login);
		Login.setOnClickListener(this);
		Fgpwd = (TextView) findViewById(R.id.forgetpwd);
		Fgpwd.setOnClickListener(this);
		Register = (Button) findViewById(R.id.register);
		Register.setOnClickListener(this);
	}

	/**
	 * 功能描述: 登录控制 〈功能详细描述〉
	 *
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	private void LoginControll() {
		username = Etusername.getText().toString();
		pwd = Etpwd.getText().toString();
		if (TextUtils.isEmpty(username)) {
			Toast.makeText(LoginActivity.this, "请输入用户名", 1).show();
			return;
		}
		if (TextUtils.isEmpty(pwd)) {
			Toast.makeText(LoginActivity.this, "请输入密码", 1).show();
			return;
		} else {
			new LoginTask().execute();
			Log.d(TAG, "执行登录");
			return;

		}
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.title_bar_left:
			LoginActivity.this.finish();
			break;
		case R.id.login:
			LoginControll();
			break;
		case R.id.forgetpwd:
			break;
		case R.id.register:
			intent.setClass(LoginActivity.this, RegisterActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

	/**
	 * 登录线程 〈功能详细描述〉
	 *
	 * @author 王超
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	private class LoginTask extends AsyncTask<Void, Void, NetworkBean> 
	{
		@Override
		protected void onPreExecute() 
		{
			super.onPreExecute();
			progressDialog.show();
		}

		@Override
		protected NetworkBean doInBackground(Void... params) {
			try {
				return mApplication.getNetApi().login(username, pwd);
			} catch (Exception ex) {
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
				progressDialog.dismiss();
				String userId = null;
				String code = result.getCode();
				String msg = result.getMessage();
				if(code.equals("10000"))
				{
					Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
					try 
					{
						JSONObject jsonObject = result.getData();
						if (jsonObject.has("uid")) 
						{
							userId = jsonObject.getString("uid");
						    mApplication.setUserId(userId);
						}
					}
					catch (Exception ex) 
					{
						ex.printStackTrace();
					}
				mApplication.setSharedPreLoginName(username);
				mApplication.setSharedPreLoginPwd(pwd);
				Intent intent = new Intent();
				intent.setClass(LoginActivity.this, FrameActivity.class);
				startActivity(intent);
				LoginActivity.this.finish();
				}
				else
				{
					Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
					mApplication.setSharedPreLoginName("");
					mApplication.setSharedPreLoginPwd("");
				}
			} 
			else 
			{
				Toast.makeText(LoginActivity.this, "服务器出错", Toast.LENGTH_SHORT).show();
			}
		}
	}
}
