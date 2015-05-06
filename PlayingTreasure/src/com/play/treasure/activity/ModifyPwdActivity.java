package com.play.treasure.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.play.treasure.PlayApplication;
import com.play.treasure.R;
import com.play.treasure.network.model.NetworkBean;
import com.play.treasure.utils.CommonProgressDialog;

public class ModifyPwdActivity extends Activity implements OnClickListener {
	private ImageView titleLeft;
	private TextView titleCenter;
	private EditText Etcurrentpwd;
	private String currentpwd;
	private EditText Etnewpwd;
	private String newpwd;
	private EditText Etconfirmnewpwd;
	private String confirmpwd;
	private Button Confirm;
	private PlayApplication mApplication;
	private CommonProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modifypwd);
		mApplication = PlayApplication.getApplication();
		titleLeft = (ImageView)findViewById(R.id.title_bar_left);
		titleLeft.setImageResource(R.drawable.back480_03);
		titleLeft.setVisibility(View.VISIBLE);
		titleLeft.setOnClickListener(this);
		titleCenter = (TextView) findViewById(R.id.title_bar_center);
		titleCenter.setText("修改账户密码");
		titleCenter.setVisibility(View.VISIBLE);
		titleCenter.setTextSize(16);
		Etcurrentpwd = (EditText) findViewById(R.id.currentpwd);
		Etnewpwd = (EditText) findViewById(R.id.newpwd);
		Etconfirmnewpwd = (EditText) findViewById(R.id.confirmnewpwd);
		Confirm = (Button) findViewById(R.id.confirm);
		Confirm.setOnClickListener(this);
		progressDialog = new CommonProgressDialog(this, R.style.dialog);
		progressDialog.setMsg("加载中...");
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.title_bar_left:
			ModifyPwdActivity.this.finish();
			break;
		case R.id.confirm:
			setCurrentpwd(Etcurrentpwd.getText().toString());
			setNewpwd(Etnewpwd.getText().toString());
			setConfirmpwd(Etconfirmnewpwd.getText().toString());
			new modifyPwdTask().execute();
			break;
		default:
			break;

		}

	}
	public String getCurrentpwd() {
		return currentpwd;
	}

	public void setCurrentpwd(String currentpwd) {
		this.currentpwd = currentpwd;
	}
	public String getNewpwd() {
		return newpwd;
	}

	public void setNewpwd(String newpwd) {
		this.newpwd = newpwd;
	}
	public String getConfirmpwd() {
		return confirmpwd;
	}

	public void setConfirmpwd(String confirmpwd) {
		this.confirmpwd = confirmpwd;
	}
	public class modifyPwdTask extends AsyncTask<Void, Void, NetworkBean> {

		@Override
		protected void onPreExecute() {
			progressDialog.show();
			super.onPreExecute();
		}

		@Override
		protected NetworkBean doInBackground(Void... params) {
			try {
				return mApplication.getNetApi().modifyPwd(mApplication.getUserId(), getCurrentpwd(), getNewpwd(), getConfirmpwd());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(NetworkBean result) {
			super.onPostExecute(result);
			if (result != null) {
				progressDialog.dismiss();
				try {
					String code = result.getCode();
					if("10000".equals(code))
					{
						ModifyPwdActivity.this.finish();
						Intent intent = new Intent(ModifyPwdActivity.this,LoginActivity.class);
						startActivity(intent);
					}
					String msg = result.getMessage();
					Toast.makeText(ModifyPwdActivity.this, msg, Toast.LENGTH_LONG).show();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}
}
