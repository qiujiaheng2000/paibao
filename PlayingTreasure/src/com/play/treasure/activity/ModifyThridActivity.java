package com.play.treasure.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
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
import android.widget.Toast;

import com.play.treasure.PlayApplication;
import com.play.treasure.R;
import com.play.treasure.network.NetworkConfig;
import com.play.treasure.network.model.NetworkBean;
import com.play.treasure.utils.HttpFileUpTool;
import com.play.treasure.utils.ProgressDialog;
import com.play.treasure.utils.ToastUtil;

/** 
* @ClassName: ModifyThridActivity
* @Description: 发布流程最后
* @author wangchao29
* @date 2015年1月11日 下午5:42:20
* 
*/ 
public class ModifyThridActivity extends Activity implements OnClickListener 
{
	
	ProgressDialog progressDialog = null;
	
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modify_thrid);
		mApplication = PlayApplication.getApplication();
		progressDialog = new ProgressDialog(this, R.style.dialog);
		new LoadThridTask().execute();
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
			tid = intent.getStringExtra("tid");
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
					location_add.setText("");
					address1 = "";

					tag = 2;
					freshloc.setImageResource(R.drawable.location_lv_);
				} else {
					mApplication.mLocationClient.stop();
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
			Log.d("duanzhibo", address1);
			address = address1 + " " + EtAddress.getText().toString();
			new UploadTotalTask().execute();

			break;
		case R.id.title_bar_left:
			ModifyThridActivity.this.finish();
			break;
		default:
			break;

		}
	}

	public class LoadThridTask extends AsyncTask<Void, Void, NetworkBean> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected NetworkBean doInBackground(Void... params) {
			try {
				return mApplication.getNetApi().modifyThrid(
						mApplication.getTid());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(NetworkBean result) {
			super.onPostExecute(result);
			if (result != null) {
				try {
					JSONObject json = result.getData();
					String address = json.getString("address");
					String qq = json.getString("qq");
					String mobile = json.getString("mobile");
					EtQq.setText(qq);
					EtPhone.setText(mobile);
					String[] strs =  address.split(" ");
					if(strs.length > 0){
						EtAddress.setText(strs[strs.length - 1]);
					}
					else{
						EtAddress.setText("");
					}
					String mPostCate = json.getString("post_cate");
					if ("1".equals(mPostCate)) {
						radio1.setChecked(true);
						postCate = "1";
						EtQq.setVisibility(View.VISIBLE);
						EtPhone.setVisibility(View.VISIBLE);
					}
					if ("2".equals(mPostCate)) {
						radio2.setChecked(true);
						postCate = "2";
						EtQq.setVisibility(View.GONE);
						EtPhone.setVisibility(View.GONE);
					}
					if ("3".equals(mPostCate)) {
						radio3.setChecked(true);
						postCate = "3";
						EtQq.setVisibility(View.GONE);
						EtPhone.setVisibility(View.GONE);
					}
					if ("4".equals(mPostCate)) {
						radio4.setChecked(true);
						postCate = "4";
						EtQq.setVisibility(View.GONE);
						EtPhone.setVisibility(View.GONE);
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
				catch (NullPointerException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public class UploadTotalTask extends AsyncTask<Void, Void, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog.show();
		}

		@Override
		protected String doInBackground(Void... params) {
			String actionUrl = NetworkConfig.baseurl + "Update/index";

			Map<String, String> param = new HashMap<String, String>();
			param.put("tid", tid);
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
				String img = datas.get(i);
				Log.d("duanzhibo1", img);
				files.put(new StringBuilder().append(System.currentTimeMillis() + i).append(".jpg").toString(),
						new StringBuilder(img.indexOf("/") == -1 ? NetworkConfig.baseImgUrl : "").append(img).toString());
			}
			try {
				return HttpFileUpTool.post(actionUrl, param, files,progressDialog.getmHandler());

			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			Log.d("duanzhibo1","-------onPostExecute-------" + result);
			super.onPostExecute(result);
			if (result != null) {
				progressDialog.getmHandler().sendEmptyMessage(100);
				progressDialog.dismiss();
				try {
					JSONObject json = new JSONObject(result);
					String code = json.getString("code");
					String msg = json.getString("msg");
					
					if ("10000".equals(code)) {
						ToastUtil.showMessage("发布成功");
						Intent intent = new Intent();
						intent.setClass(ModifyThridActivity.this,
								ModifySuccessActivity.class);
						
						intent.putExtra("title",title);
						intent.putExtra("detail",detail);
						
						startActivity(intent);
						ModifyThridActivity.this.finish();
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
