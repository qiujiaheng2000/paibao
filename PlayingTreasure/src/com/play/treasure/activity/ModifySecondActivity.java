package com.play.treasure.activity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.play.treasure.PlayApplication;
import com.play.treasure.R;
import com.play.treasure.network.NetworkConfig;
import com.play.treasure.network.model.NetworkBean;
import com.play.treasure.utils.BitmapUtil;
import com.play.treasure.utils.CommonProgressDialog;
import com.play.treasure.utils.ToastUtil;

public class ModifySecondActivity extends Activity implements OnClickListener {
	/***
	 * 使用照相机拍照获取图片
	 */
	public static final int SELECT_PIC_BY_TACK_PHOTO = 1;
	/***
	 * 使用相册中的图片
	 */
	public static final int SELECT_PIC_BY_PICK_PHOTO = 2;
	// 图片选择
	public static final int TO_SELECT_PIC1 = 1001;
	public static final int TO_SELECT_PIC2 = 1002;
	public static final int TO_SELECT_PIC3 = 1003;
	public static final int TO_SELECT_PIC4 = 1004;
	public static final int TO_SELECT_PIC5 = 1005;
	public static final int TO_SELECT_PIC6 = 1006;
	public static final int TO_SELECT_PIC7 = 1007;
	public static final int TO_SELECT_PIC8 = 1008;
	
	private GridView grv_content;

	public PlayApplication mApplication;

	private ArrayList<String> datas = new ArrayList<String>();
	BaseAdapter adapter;
	// 图片地址
	private String picPath = null;

	private TextView titleCenter;
	private Button Next;
	private ImageView titleLeft;
	private EditText EtTitle;
	private EditText EtDetail;
	private ImageView IvClear;
	private ImageView Iv1;
	private ImageView Iv2;
	private ImageView Iv3;
	private ImageView Iv4;
	private ImageView Iv5;
	private ImageView Iv6;
	private ImageView Iv7;
	private ImageView Iv8;
	

	private String tid;
	private String title;
	private String detail;
	
	private CommonProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_publicplays_details);
		mApplication = PlayApplication.getApplication();
		progressDialog = new CommonProgressDialog(this, R.style.dialog);
		progressDialog.setMsg("加载中...");
		new LoadSecondTask().execute();
		titleLeft = (ImageView) findViewById(R.id.title_bar_left);
		titleLeft.setImageResource(R.drawable.back480_03);
		titleLeft.setVisibility(View.VISIBLE);
		titleLeft.setOnClickListener(this);
		titleCenter = (TextView) findViewById(R.id.title_bar_center);
		titleCenter.setText("宝贝详细信息");
		titleCenter.setVisibility(View.VISIBLE);
		titleCenter.setTextSize(16);
		EtTitle = (EditText) findViewById(R.id.title);
		IvClear = (ImageView) findViewById(R.id.clear);
		IvClear.setOnClickListener(this);
		EtDetail = (EditText) findViewById(R.id.detail);
		Next = (Button) findViewById(R.id.next);
		Next.setOnClickListener(this);
		Iv1 = (ImageView)findViewById(R.id.iv1);
		Iv1.setOnClickListener(this);
		Iv2 = (ImageView)findViewById(R.id.iv2);
		Iv2.setOnClickListener(this);
		Iv3 = (ImageView)findViewById(R.id.iv3);
		Iv3.setOnClickListener(this);
		Iv4 = (ImageView)findViewById(R.id.iv4);
		Iv4.setOnClickListener(this);
		Iv5 = (ImageView)findViewById(R.id.iv5);
		Iv5.setOnClickListener(this);
		Iv6 = (ImageView)findViewById(R.id.iv6);
		Iv6.setOnClickListener(this);
		Iv7 = (ImageView)findViewById(R.id.iv7);
		Iv7.setOnClickListener(this);
		Iv8 = (ImageView)findViewById(R.id.iv8);
		Iv8.setOnClickListener(this);
		
		try {
			Intent intent = getIntent();
			tid = intent.getStringExtra("tid");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {

		case R.id.next:
			title = EtTitle.getText().toString();
			detail = EtDetail.getText().toString();

			//new UploadDataTask().execute();
			//new UploadImgTask().execute();
			//intent.putExtra("tid", tid);
			if(datas.isEmpty())
			{
				ToastUtil.showMessage("请至少上传一张图片");
			}
			else
			{
				intent.putExtra("tid", mApplication.getTid());
				intent.putExtra("title", title);
				intent.putExtra("detail", detail);
				intent.putExtra("datas", datas);
				intent.setClass(ModifySecondActivity.this,
						ModifyThridActivity.class);
				startActivity(intent);
				ModifySecondActivity.this.finish();
			}
			
			break;
		case R.id.title_bar_left:
			ModifySecondActivity.this.finish();
			break;
		case R.id.clear:
			EtTitle.setText("");
			break;
		case R.id.iv1:
			intent.setClass(ModifySecondActivity.this,
					SelectPicActivity.class);
			intent.putExtra("index", TO_SELECT_PIC1);
			startActivityForResult(intent, TO_SELECT_PIC1);
			overridePendingTransition(R.anim.abc_slide_in_bottom, 0);
			break;
		case R.id.iv2:
			intent.setClass(ModifySecondActivity.this,
					SelectPicActivity.class);
			intent.putExtra("index", TO_SELECT_PIC2);
			startActivityForResult(intent, TO_SELECT_PIC2);
			overridePendingTransition(R.anim.abc_slide_in_bottom, 0);
			break;
		case R.id.iv3:
			intent.setClass(ModifySecondActivity.this,
					SelectPicActivity.class);
			intent.putExtra("index", TO_SELECT_PIC3);
			startActivityForResult(intent, TO_SELECT_PIC3);
			overridePendingTransition(R.anim.abc_slide_in_bottom, 0);
			break;
		case R.id.iv4:
			intent.setClass(ModifySecondActivity.this,
					SelectPicActivity.class);
			intent.putExtra("index", TO_SELECT_PIC4);
			startActivityForResult(intent, TO_SELECT_PIC4);
			overridePendingTransition(R.anim.abc_slide_in_bottom, 0);
			break;
		case R.id.iv5:
			intent.setClass(ModifySecondActivity.this,
					SelectPicActivity.class);
			intent.putExtra("index", TO_SELECT_PIC5);
			startActivityForResult(intent, TO_SELECT_PIC5);
			overridePendingTransition(R.anim.abc_slide_in_bottom, 0);
			break;
		case R.id.iv6:
			intent.setClass(ModifySecondActivity.this,
					SelectPicActivity.class);
			intent.putExtra("index", TO_SELECT_PIC6);
			startActivityForResult(intent, TO_SELECT_PIC6);
			overridePendingTransition(R.anim.abc_slide_in_bottom, 0);
			break;
		case R.id.iv7:
			intent.setClass(ModifySecondActivity.this,
					SelectPicActivity.class);
			intent.putExtra("index", TO_SELECT_PIC7);
			startActivityForResult(intent, TO_SELECT_PIC7);
			overridePendingTransition(R.anim.abc_slide_in_bottom, 0);
			break;
		case R.id.iv8:
			intent.setClass(ModifySecondActivity.this,
					SelectPicActivity.class);
			intent.putExtra("index", TO_SELECT_PIC8);
			startActivityForResult(intent, TO_SELECT_PIC8);
			overridePendingTransition(R.anim.abc_slide_in_bottom, 0);
			break;
		default:
			break;

		}
	}

	public class LoadSecondTask extends AsyncTask<Void, Void, NetworkBean>
	{

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog.show();
		}

		

		@Override
		protected NetworkBean doInBackground(Void... params) {
			try 
			{
				return mApplication.getNetApi().modifySecond(mApplication.getTid());
			} 
			catch (Exception ex) 
			{
				ex.printStackTrace();
			}
			return null;
		}
		@Override
		protected void onPostExecute(NetworkBean result) {
			super.onPostExecute(result);
			if (result != null) 
			{
				progressDialog.dismiss();
				try {
					JSONObject json = result.getData();
					String title = json.getString("title");
					String detail = json.getString("detail");
					EtTitle.setText(title);
					EtDetail.setText(detail);
					String img = json.getString("img");
					JSONArray jsonArray = new JSONArray(img);
					ArrayList<String> imgList = new ArrayList<String>();
					for (int i = 0; i < jsonArray.length(); i++) {
						String src = jsonArray.getJSONObject(i).getString("src");
						imgList.add(src);
					}
					datas.addAll(imgList);
					switch(imgList.size())
					{
					case 1:
						ImageLoader.getInstance().displayImage(NetworkConfig.baseImgUrl+imgList.get(0), Iv1 );
						break;
					case 2:
						ImageLoader.getInstance().displayImage(NetworkConfig.baseImgUrl+imgList.get(0), Iv1 );
						ImageLoader.getInstance().displayImage(NetworkConfig.baseImgUrl+imgList.get(1), Iv2 );
						
						break;
					case 3:
						ImageLoader.getInstance().displayImage(NetworkConfig.baseImgUrl+imgList.get(0), Iv1 );
						ImageLoader.getInstance().displayImage(NetworkConfig.baseImgUrl+imgList.get(1), Iv2 );
						ImageLoader.getInstance().displayImage(NetworkConfig.baseImgUrl+imgList.get(2), Iv3 );
						break;
					case 4:
						ImageLoader.getInstance().displayImage(NetworkConfig.baseImgUrl+imgList.get(0), Iv1 );
						ImageLoader.getInstance().displayImage(NetworkConfig.baseImgUrl+imgList.get(1), Iv2 );
						ImageLoader.getInstance().displayImage(NetworkConfig.baseImgUrl+imgList.get(2), Iv3 );
						ImageLoader.getInstance().displayImage(NetworkConfig.baseImgUrl+imgList.get(3), Iv4 );
						break;
					case 5:
						ImageLoader.getInstance().displayImage(NetworkConfig.baseImgUrl+imgList.get(0), Iv1 );
						ImageLoader.getInstance().displayImage(NetworkConfig.baseImgUrl+imgList.get(1), Iv2 );
						ImageLoader.getInstance().displayImage(NetworkConfig.baseImgUrl+imgList.get(2), Iv3 );
						ImageLoader.getInstance().displayImage(NetworkConfig.baseImgUrl+imgList.get(3), Iv4 );
						ImageLoader.getInstance().displayImage(NetworkConfig.baseImgUrl+imgList.get(4), Iv5 );
						break;
					case 6:
						ImageLoader.getInstance().displayImage(NetworkConfig.baseImgUrl+imgList.get(0), Iv1 );
						ImageLoader.getInstance().displayImage(NetworkConfig.baseImgUrl+imgList.get(1), Iv2 );
						ImageLoader.getInstance().displayImage(NetworkConfig.baseImgUrl+imgList.get(2), Iv3 );
						ImageLoader.getInstance().displayImage(NetworkConfig.baseImgUrl+imgList.get(3), Iv4 );
						ImageLoader.getInstance().displayImage(NetworkConfig.baseImgUrl+imgList.get(4), Iv5 );
						ImageLoader.getInstance().displayImage(NetworkConfig.baseImgUrl+imgList.get(5), Iv6 );
						break;
					case 7:
						ImageLoader.getInstance().displayImage(NetworkConfig.baseImgUrl+imgList.get(0), Iv1 );
						ImageLoader.getInstance().displayImage(NetworkConfig.baseImgUrl+imgList.get(1), Iv2 );
						ImageLoader.getInstance().displayImage(NetworkConfig.baseImgUrl+imgList.get(2), Iv3 );
						ImageLoader.getInstance().displayImage(NetworkConfig.baseImgUrl+imgList.get(3), Iv4 );
						ImageLoader.getInstance().displayImage(NetworkConfig.baseImgUrl+imgList.get(4), Iv5 );
						ImageLoader.getInstance().displayImage(NetworkConfig.baseImgUrl+imgList.get(5), Iv6 );
						ImageLoader.getInstance().displayImage(NetworkConfig.baseImgUrl+imgList.get(6), Iv7 );
						break;
					case 8:
						ImageLoader.getInstance().displayImage(NetworkConfig.baseImgUrl+imgList.get(0), Iv1 );
						ImageLoader.getInstance().displayImage(NetworkConfig.baseImgUrl+imgList.get(1), Iv2 );
						ImageLoader.getInstance().displayImage(NetworkConfig.baseImgUrl+imgList.get(2), Iv3 );
						ImageLoader.getInstance().displayImage(NetworkConfig.baseImgUrl+imgList.get(3), Iv4 );
						ImageLoader.getInstance().displayImage(NetworkConfig.baseImgUrl+imgList.get(4), Iv5 );
						ImageLoader.getInstance().displayImage(NetworkConfig.baseImgUrl+imgList.get(5), Iv6 );
						ImageLoader.getInstance().displayImage(NetworkConfig.baseImgUrl+imgList.get(6), Iv7 );
						ImageLoader.getInstance().displayImage(NetworkConfig.baseImgUrl+imgList.get(7), Iv8 );
						break;
					}
						
					
					

					
				} catch (JSONException e)
				{
					e.printStackTrace();
				}
				catch(NullPointerException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
		
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK && requestCode == TO_SELECT_PIC1) 
		{
			picPath = data.getStringExtra(SelectPicActivity.KEY_PHOTO_PATH);
			Bitmap bm = BitmapUtil.getBitmap(picPath);
			Iv1.setScaleType(ScaleType.FIT_CENTER);
			Iv1.setImageBitmap(bm);
			if(datas.size()>0)
			{
				datas.remove(0);
				datas.add(0, picPath);
			}
		}
		if (resultCode == Activity.RESULT_OK && requestCode == TO_SELECT_PIC2) 
		{
			picPath = data.getStringExtra(SelectPicActivity.KEY_PHOTO_PATH);
			Bitmap bm = BitmapUtil.getBitmap(picPath);
			Iv2.setScaleType(ScaleType.FIT_CENTER);
			Iv2.setImageBitmap(bm);
			if(datas.size()>1)
			{
				datas.remove(1);
				datas.add(1, picPath);
			}
			else
			{
				datas.add(picPath);
			}
		}
		if (resultCode == Activity.RESULT_OK && requestCode == TO_SELECT_PIC3) 
		{
			picPath = data.getStringExtra(SelectPicActivity.KEY_PHOTO_PATH);
			Bitmap bm = BitmapUtil.getBitmap(picPath);
			Iv3.setScaleType(ScaleType.FIT_CENTER);
			Iv3.setImageBitmap(bm);
			if(datas.size()>2)
			{
				datas.remove(2);
				datas.add(2, picPath);
			}
			else
			{
				datas.add(picPath);
			}
		}
		if (resultCode == Activity.RESULT_OK && requestCode == TO_SELECT_PIC4) 
		{
			picPath = data.getStringExtra(SelectPicActivity.KEY_PHOTO_PATH);
			Bitmap bm = BitmapUtil.getBitmap(picPath);
			Iv4.setScaleType(ScaleType.FIT_CENTER);
			Iv4.setImageBitmap(bm);
			if(datas.size()>3)
			{
				datas.remove(3);
				datas.add(3, picPath);
			}
			else
			{
				datas.add(picPath);
			}
		}
		if (resultCode == Activity.RESULT_OK && requestCode == TO_SELECT_PIC5) 
		{
			picPath = data.getStringExtra(SelectPicActivity.KEY_PHOTO_PATH);
			Bitmap bm = BitmapUtil.getBitmap(picPath);
			Iv5.setScaleType(ScaleType.FIT_CENTER);
			Iv5.setImageBitmap(bm);
			if(datas.size()>4)
			{
				datas.remove(4);
				datas.add(4, picPath);
			}
			else
			{
				datas.add(picPath);
			}
		}
		if (resultCode == Activity.RESULT_OK && requestCode == TO_SELECT_PIC6) 
		{
			picPath = data.getStringExtra(SelectPicActivity.KEY_PHOTO_PATH);
			Bitmap bm = BitmapUtil.getBitmap(picPath);
			Iv6.setScaleType(ScaleType.FIT_CENTER);
			Iv6.setImageBitmap(bm);
			if(datas.size()>5)
			{
				datas.remove(5);
				datas.add(5, picPath);
			}
			else
			{
				datas.add(picPath);
			}
		}
		if (resultCode == Activity.RESULT_OK && requestCode == TO_SELECT_PIC7) 
		{
			picPath = data.getStringExtra(SelectPicActivity.KEY_PHOTO_PATH);
			Bitmap bm = BitmapUtil.getBitmap(picPath);
			Iv7.setScaleType(ScaleType.FIT_CENTER);
			Iv7.setImageBitmap(bm);
			if(datas.size()>6)
			{
				datas.remove(6);
				datas.add(6, picPath);
			}
			else
			{
				datas.add(picPath);
			}
			
		}
		if (resultCode == Activity.RESULT_OK && requestCode == TO_SELECT_PIC8) 
		{
			picPath = data.getStringExtra(SelectPicActivity.KEY_PHOTO_PATH);
			Bitmap bm = BitmapUtil.getBitmap(picPath);
			Iv8.setScaleType(ScaleType.FIT_CENTER);
			Iv8.setImageBitmap(bm);
			if(datas.size()>7)
			{
				datas.remove(7);
				datas.add(7, picPath);
			}
			else
			{
				datas.add(picPath);
			}
			
		}

	}

	

	

	

}
