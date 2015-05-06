package com.play.treasure.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.play.treasure.PlayApplication;
import com.play.treasure.R;
import com.play.treasure.network.NetworkConfig;
import com.play.treasure.network.model.NetworkBean;
import com.play.treasure.utils.HttpFileUpTool;
import com.play.treasure.utils.ProgressDialog;
import com.play.treasure.utils.ToastUtil;

public class PublicThridActivity extends Activity implements OnClickListener {
	private TextView titleCenter;
	private ImageView titleLeft;
	private CompoundButton.OnCheckedChangeListener checkBox_listener;
	private Button PublicPlays;
	private TextView location_add;
	private ImageView freshloc;
	PlayApplication mApplication;
	static int tag = 1;
	private RadioGroup group;
	private RadioButton radio1, radio2, radio3, radio4;
	private EditText EtQq;
	private EditText EtPhone;
	private EditText EtAddress;
	private String tid;
	private String detail;
	private String title;
	private ArrayList<String> datas = new ArrayList<String>();
	private String qq;
	private String mobile;
	private String address;
	private String postCate = "1";
	private String address1 = "";
	private ProgressDialog progressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_publicplays_sort);
		mApplication = PlayApplication.getApplication();
		titleLeft = (ImageView) findViewById(R.id.title_bar_left);
		titleLeft.setImageResource(R.drawable.back480_03);
		titleLeft.setVisibility(View.VISIBLE);
		titleLeft.setOnClickListener(this);
		titleCenter = (TextView) findViewById(R.id.title_bar_center);
		titleCenter.setText("发布你的宝贝");
		titleCenter.setVisibility(View.VISIBLE);
		titleCenter.setTextSize(16);
		location_add = (TextView) findViewById(R.id.location_add);
		freshloc = (ImageView) findViewById(R.id.location);
		location_add = (TextView)findViewById(R.id.location_add);
		freshloc = (ImageView)findViewById(R.id.location);
		freshloc.setOnClickListener(this);
		PublicPlays = (Button) findViewById(R.id.publicplays);
		PublicPlays.setOnClickListener(this);
		EtQq = (EditText) findViewById(R.id.input_qq);
		EtPhone = (EditText) findViewById(R.id.input_phone);
		EtAddress = (EditText) findViewById(R.id.address);
		group = (RadioGroup) findViewById(R.id.radioGroup);
		radio1 = (RadioButton) findViewById(R.id.chushou);
		radio1.setChecked(true);
		radio2 = (RadioButton) findViewById(R.id.qiuzhangyan);
		radio3 = (RadioButton) findViewById(R.id.bawan);
		radio4 = (RadioButton) findViewById(R.id.fenxiang);
		try {
			Intent intent = getIntent();
			// tid = intent.getStringExtra("tid");
			title = intent.getStringExtra("title");
			detail = intent.getStringExtra("detail");
			datas = intent.getStringArrayListExtra("datas");

		} catch (Exception e) {
			e.printStackTrace();
		}

		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == radio1.getId()) {
					postCate = "1";
					EtQq.setVisibility(View.VISIBLE);
					EtPhone.setVisibility(View.VISIBLE);
					EtAddress.setVisibility(View.VISIBLE);
				}
				if (checkedId == radio2.getId()) {
					postCate = "2";
					EtQq.setVisibility(View.GONE);
					EtPhone.setVisibility(View.GONE);
					EtAddress.setVisibility(View.GONE);
				}
				if (checkedId == radio3.getId()) {
					postCate = "3";
					EtQq.setVisibility(View.GONE);
					EtPhone.setVisibility(View.GONE);
					EtAddress.setVisibility(View.GONE);
				}
				if (checkedId == radio4.getId()) {
					postCate = "4";
					EtQq.setVisibility(View.GONE);
					EtPhone.setVisibility(View.GONE);
					EtAddress.setVisibility(View.GONE);
				}
			}
		});

		checkBox_listener = new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					mApplication.mLocationClient.stop();
					// Toast.makeText(PublicThridActivity.this, "关闭定位",
					// Toast.LENGTH_SHORT).show();
					location_add.setText("自动获取位置信息");
					address1 = "";

					tag = 2;
					freshloc.setImageResource(R.drawable.location_lv_);
				} else {
					mApplication.mLocationClient.stop();
					// Toast.makeText(PublicThridActivity.this, "打开定位",
					// Toast.LENGTH_SHORT).show();
					location_add.setText(mApplication.getAddress());
					address1 = mApplication.getAddress();
					freshloc.setImageResource(R.drawable.freshloca);
					tag = 1;
				}
			}
		};
		final CheckBox checkbox = (CheckBox) findViewById(R.id.on_off);
		checkbox.setOnCheckedChangeListener(checkBox_listener);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.location:

			if (tag == 1) {
				location_add.setText(mApplication.getAddress());
			}
			break;
		case R.id.publicplays:
			qq = EtQq.getText().toString();
			mobile = EtPhone.getText().toString();
			address = address1 + " " + EtAddress.getText().toString();
			// new UploadTask().execute();
			new UploadTotalTask().execute();

			break;
		case R.id.title_bar_left:
			PublicThridActivity.this.finish();
			break;
		default:
			break;

		}
	}

	public class UploadTask extends AsyncTask<Void, Void, NetworkBean> {

		protected void onPreExecute() {
			super.onPreExecute();
			
		}

		@Override
		protected NetworkBean doInBackground(Void... params)
		{
			try
			{
				return mApplication.getNetApi().publicThird(tid, postCate, mApplication.getLongitude(), mApplication.getLatitude(), address, qq, mobile);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			return null;
		}

	}

	public class UploadTotalTask extends AsyncTask<Void, Void, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(PublicThridActivity.this, R.style.dialog);
			progressDialog.show();
			progressDialog.setCancelable(false);
		}

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			String actionUrl = NetworkConfig.baseurl + "Release/index";

			Map<String, String> param = new HashMap<String, String>();
			// param.put("tid", tid);
			param.put("uid", mApplication.getUserId());
			param.put("title", title);
			param.put("detail", detail);
			param.put("post_cate", postCate);
			param.put("x", mApplication.getLongitude());
			param.put("y", mApplication.getLatitude());
			param.put("address", address);
			param.put("qq", qq);
			param.put("mobile", mobile);
			param.put("small_id", mApplication.getCategoryId());

			Map<String, String> files = new HashMap<String, String>();
			for (int i = 0; i < datas.size(); i++) {
				Log.i("datas", datas.get(i)+"");
				files.put(System.currentTimeMillis() + i + ".jpg", datas.get(i));
				// ToastUtil.showMessage(tid+title+detail+datas.get(i));
			}
			try {
				
				return HttpFileUpTool.post(actionUrl, param, files,progressDialog.getmHandler());
				/*
				 * intent.setClass(PublicSecondActivity.this,PublicThridActivity.
				 * class); startActivity(intent);
				 */

			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			Log.d("duanzhibo1","-------onPostExecute-------" + result);
			if (result != null) {
				progressDialog.getmHandler().sendEmptyMessage(100);
				progressDialog.dismiss();
				try {
					JSONObject json = new JSONObject(result);
					String code = json.getString("code");
					String msg = json.getString("msg");
					Log.i("result",""+result);
					
					if ("10000".equals(code)) {
						ToastUtil.showMessage("发布成功");
						Intent intent = new Intent();
						intent.putExtra("result", result);
						
						intent.putExtra("title", title);
						intent.putExtra("detail", detail);
						intent.setClass(PublicThridActivity.this,
								PublicSuccessActivity.class);
						startActivity(intent);
						PublicThridActivity.this.finish();
					}
					else
					{
						ToastUtil.showMessage("发布失败");
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
	}

}
