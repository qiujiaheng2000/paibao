package com.play.treasure.activity;

import java.io.File;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.play.treasure.PlayApplication;
import com.play.treasure.R;
import com.play.treasure.network.NetworkConfig;
import com.play.treasure.network.model.NetworkBean;
import com.play.treasure.utils.BitmapUtil;
import com.play.treasure.utils.CommonProgressDialog;
import com.play.treasure.widget.CircularImage;

public class UserDataActivity extends Activity implements OnClickListener {

	private ImageView titleLeft;
	private TextView titleCenter;

	/**
	 * 系统全局变量类
	 */
	private PlayApplication mApplication;
	/**
	 * 头像
	 */
	private RelativeLayout Changeicongroup;
	/**
	 * 昵称
	 */
	private EditText Etnickname;
	private String nickname;
	private EditText Etaddress;
	private String address;
	private String icon;
	/**
	 * 确定按钮
	 */
	private Button Confirm;
	private ImageView IvIcon;
	private CircularImage cover_user_photo;
	/***
	 * 使用照相机拍照获取图片
	 */
	public static final int SELECT_PIC_BY_TACK_PHOTO = 1;
	/***
	 * 使用相册中的图片
	 */
	public static final int SELECT_PIC_BY_PICK_PHOTO = 2;
	// 图片选择
	public static final int TO_SELECT_ICON = 1001;

	// 图片地址
	private String filePath =  null;
	// 图片上传
	public static final int TO_UPLOAD_ICON = 1002;
	private CommonProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_userdata);
		mApplication = PlayApplication.getApplication();
		titleLeft = (ImageView)findViewById(R.id.title_bar_left);
		titleLeft.setImageResource(R.drawable.back480_03);
		titleLeft.setVisibility(View.VISIBLE);
		titleLeft.setOnClickListener(this);
		titleCenter = (TextView) findViewById(R.id.title_bar_center);
		titleCenter.setText("个人资料设置");
		titleCenter.setVisibility(View.VISIBLE);
		titleCenter.setTextSize(16);
		progressDialog = new CommonProgressDialog(this, R.style.dialog);
		progressDialog.setMsg("加载中...");
		
		Changeicongroup = (RelativeLayout) findViewById(R.id.changeicongroup);
		Changeicongroup.setOnClickListener(this);
		cover_user_photo = (CircularImage) findViewById(R.id.cover_user_photo);
		cover_user_photo.setImageResource(R.drawable.touxiang);
		Etnickname = (EditText) findViewById(R.id.nickname);
		Etaddress = (EditText) findViewById(R.id.address);
		Confirm = (Button) findViewById(R.id.confirm);
		Confirm.setOnClickListener(this);
		IvIcon = (ImageView)findViewById(R.id.icon);
		new LoadDataTask().execute();

	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.title_bar_left:
			UserDataActivity.this.finish();
			break;
		case R.id.changeicongroup:
			intent.setClass(UserDataActivity.this,
					SelectPicActivity.class);
			startActivityForResult(intent, TO_SELECT_ICON);
			overridePendingTransition(R.anim.abc_slide_in_bottom, 0);
			break;
		case R.id.confirm:
			new ModifyDataTask().execute();
			break;
		default:
			break;

		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK && requestCode == TO_SELECT_ICON) {
			filePath = data.getStringExtra(SelectPicActivity.KEY_PHOTO_PATH);
			Bitmap bm = BitmapUtil.getHeadBitmap(filePath);
			cover_user_photo.setScaleType(ScaleType.FIT_CENTER);
			cover_user_photo.setImageBitmap(bm);
			Log.d("upload", filePath);
			new UploadIconTask().execute();
			
		}
	}
	@Override
	public void onBackPressed() 
	{
		super.onBackPressed();
	}
	
	/**
	 *  上传头像 
	 *
	 * @author 王超
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	private class UploadIconTask extends AsyncTask<Void, Void, NetworkBean>
	{
		@Override
		protected void onPreExecute() 
		{
			super.onPreExecute();
			progressDialog.setMsg("头像上传中...");
			progressDialog.show();
		}

		@Override
		protected NetworkBean doInBackground(Void... params) 
		{
			try
			{
				File file = new File(filePath);
				return mApplication.getNetApi().upLoadIcon(mApplication.getUserId(), file);
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
				progressDialog.setMsg("");
				String msg = result.getMessage();
			    Toast.makeText(UserDataActivity.this, msg, Toast.LENGTH_SHORT).show();
			}
		}
	}
	/** 
	* @ClassName: LoadDataTask
	* @Description: 加载数据
	* @author xieqiang
	* @date 2014年12月7日 下午5:28:39
	* 
	*/ 
	private class LoadDataTask extends AsyncTask<Void, Void, NetworkBean>
	{
		@Override
		protected void onPreExecute() {
			
			progressDialog.setMsg("加载中...");
			progressDialog.show();
			super.onPreExecute();
		}

		@Override
		protected NetworkBean doInBackground(Void... params) 
		{
			try
			{
				return mApplication.getNetApi().modifyIcon(mApplication.getUserId());
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
				progressDialog.setMsg("");
				try {
						JSONObject json = result.getData();
						if(json.has("photo"))
						{
							icon = json.getString("photo");
							ImageLoader.getInstance().displayImage(NetworkConfig.baseImgUrl+icon, cover_user_photo);
						}
						if(json.has("nickname"))
						{
							nickname = json.getString("nickname");
							Etnickname.setText(nickname);
						}
						if(json.has("address"))
						{
							address = json.getString("address");
							Etaddress.setText(address);
						}
					}
				 catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	/** 
	* @ClassName: ModifyDataTask
	* @Description: 修改数据
	* @author xieqiang
	* @date 2014年12月7日 下午5:28:39
	* 
	*/ 
	private class ModifyDataTask extends AsyncTask<Void, Void, NetworkBean>
	{
		@Override
		protected void onPreExecute() {
			progressDialog.setMsg("修改中...");
			progressDialog.show();
			super.onPreExecute();
		}

		@Override
		protected NetworkBean doInBackground(Void... params) 
		{
			try
			{
				return mApplication.getNetApi().modifyData(mApplication.getUserId(),Etnickname.getText().toString(),Etaddress.getText().toString());
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
				progressDialog.setMsg("");
				try {
						JSONObject json = result.getData();
						if(json.has("photo"))
						{
							icon = json.getString("photo");
							ImageLoader.getInstance().displayImage(NetworkConfig.baseImgUrl+icon, cover_user_photo);
						}
						if(json.has("nickname"))
						{
							nickname = json.getString("nickname");
							Etnickname.setText(nickname);
						}
						if(json.has("address"))
						{
							address = json.getString("address");
							Etaddress.setText(address);
						}
					}
				 catch (Exception e) {
					e.printStackTrace();
				}
				String msg = result.getMessage();
			    Toast.makeText(UserDataActivity.this, msg, Toast.LENGTH_SHORT).show();
			}
		}
	}
}
