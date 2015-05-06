package com.play.treasure.activity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.play.treasure.PlayApplication;
import com.play.treasure.R;
import com.play.treasure.utils.BitmapUtil;
import com.play.treasure.utils.FileUtils;
import com.play.treasure.utils.PictureUtils;
import com.play.treasure.utils.ToastUtil;

public class PublicSecondActivity extends Activity implements OnClickListener {
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_publicplays_details);
		mApplication = PlayApplication.getApplication();
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
			if(datas.isEmpty())
			{
				ToastUtil.showMessage("请至少上传一张图片");
			}
			else
			{
				intent.putExtra("title", title);
				intent.putExtra("detail", detail);
				intent.putExtra("datas", datas);
				intent.setClass(PublicSecondActivity.this,
						PublicThridActivity.class);
				startActivity(intent);
				PublicSecondActivity.this.finish();
			}
			
			break;
		case R.id.title_bar_left:
			PublicSecondActivity.this.finish();
			break;
		case R.id.clear:
			EtTitle.setText("");
			break;
		case R.id.iv1:
			intent.setClass(PublicSecondActivity.this,
					SelectPicActivity.class);
			intent.putExtra("index", TO_SELECT_PIC1);
			startActivityForResult(intent, TO_SELECT_PIC1);
			overridePendingTransition(R.anim.abc_slide_in_bottom, 0);
			break;
		case R.id.iv2:
			intent.setClass(PublicSecondActivity.this,
					SelectPicActivity.class);
			intent.putExtra("index", TO_SELECT_PIC2);
			startActivityForResult(intent, TO_SELECT_PIC2);
			overridePendingTransition(R.anim.abc_slide_in_bottom, 0);
			break;
		case R.id.iv3:
			intent.setClass(PublicSecondActivity.this,
					SelectPicActivity.class);
			intent.putExtra("index", TO_SELECT_PIC3);
			startActivityForResult(intent, TO_SELECT_PIC3);
			overridePendingTransition(R.anim.abc_slide_in_bottom, 0);
			break;
		case R.id.iv4:
			intent.setClass(PublicSecondActivity.this,
					SelectPicActivity.class);
			intent.putExtra("index", TO_SELECT_PIC4);
			startActivityForResult(intent, TO_SELECT_PIC4);
			overridePendingTransition(R.anim.abc_slide_in_bottom, 0);
			break;
		case R.id.iv5:
			intent.setClass(PublicSecondActivity.this,
					SelectPicActivity.class);
			intent.putExtra("index", TO_SELECT_PIC5);
			startActivityForResult(intent, TO_SELECT_PIC5);
			overridePendingTransition(R.anim.abc_slide_in_bottom, 0);
			break;
		case R.id.iv6:
			intent.setClass(PublicSecondActivity.this,
					SelectPicActivity.class);
			intent.putExtra("index", TO_SELECT_PIC6);
			startActivityForResult(intent, TO_SELECT_PIC6);
			overridePendingTransition(R.anim.abc_slide_in_bottom, 0);
			break;
		case R.id.iv7:
			intent.setClass(PublicSecondActivity.this,
					SelectPicActivity.class);
			intent.putExtra("index", TO_SELECT_PIC7);
			startActivityForResult(intent, TO_SELECT_PIC7);
			overridePendingTransition(R.anim.abc_slide_in_bottom, 0);
			break;
		case R.id.iv8:
			intent.setClass(PublicSecondActivity.this,
					SelectPicActivity.class);
			intent.putExtra("index", TO_SELECT_PIC8);
			startActivityForResult(intent, TO_SELECT_PIC8);
			overridePendingTransition(R.anim.abc_slide_in_bottom, 0);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK && requestCode == TO_SELECT_PIC1) 
		{
			picPath = data.getStringExtra(SelectPicActivity.KEY_PHOTO_PATH);
			Bitmap bm = BitmapUtil.getBitmap(picPath);
			bm = PictureUtils.imageZoom(bm, PlayApplication.maxConpressSize);
			Iv1.setScaleType(ScaleType.FIT_CENTER);
			Iv1.setImageBitmap(bm);
            try {
				FileUtils.saveImage(bm,picPath);
				Log.i("picPath", picPath+"");
	            Toast.makeText(
	                    PublicSecondActivity.this,
	                    "Save Succeed", 100).show();

	            MediaScannerConnection.scanFile(this,new String[]{Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath() + "/" + picPath}, null, null);
	          //  picPath = FileUtils.getCacheFile();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			datas.add(picPath);
		}
		if (resultCode == Activity.RESULT_OK && requestCode == TO_SELECT_PIC2) 
		{
			picPath = data.getStringExtra(SelectPicActivity.KEY_PHOTO_PATH);
			Bitmap bm = BitmapUtil.getBitmap(picPath);
			bm = PictureUtils.imageZoom(bm, PlayApplication.maxConpressSize);
			Iv2.setScaleType(ScaleType.FIT_CENTER);
			Iv2.setImageBitmap(bm);
            try {
				FileUtils.saveImage(bm,picPath);
	            Toast.makeText(
	                    PublicSecondActivity.this,
	                    "Save Succeed", 100).show();

	            MediaScannerConnection.scanFile(this,new String[]{Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath() + "/" + picPath}, null, null);
	          //  picPath = FileUtils.getCacheFile();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			datas.add(picPath);
		}
		if (resultCode == Activity.RESULT_OK && requestCode == TO_SELECT_PIC3) 
		{
			picPath = data.getStringExtra(SelectPicActivity.KEY_PHOTO_PATH);
			Bitmap bm = BitmapUtil.getBitmap(picPath);
			bm = PictureUtils.imageZoom(bm, PlayApplication.maxConpressSize);
			Iv3.setScaleType(ScaleType.FIT_CENTER);
			Iv3.setImageBitmap(bm);
            try {
				FileUtils.saveImage(bm,picPath);
	            Toast.makeText(
	                    PublicSecondActivity.this,
	                    "Save Succeed", 100).show();

	            MediaScannerConnection.scanFile(this,new String[]{Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath() + "/" + picPath}, null, null);
	          //  picPath = FileUtils.getCacheFile();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			datas.add(picPath);
		}
		if (resultCode == Activity.RESULT_OK && requestCode == TO_SELECT_PIC4) 
		{
			picPath = data.getStringExtra(SelectPicActivity.KEY_PHOTO_PATH);
			Bitmap bm = BitmapUtil.getBitmap(picPath);
			bm = PictureUtils.imageZoom(bm, PlayApplication.maxConpressSize);
			Iv4.setScaleType(ScaleType.FIT_CENTER);
			Iv4.setImageBitmap(bm);
            try {
				FileUtils.saveImage(bm,picPath);
	            Toast.makeText(
	                    PublicSecondActivity.this,
	                    "Save Succeed", 100).show();

	            MediaScannerConnection.scanFile(this,new String[]{Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath() + "/" + picPath}, null, null);
	          //  picPath = FileUtils.getCacheFile();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			datas.add(picPath);
		}
		if (resultCode == Activity.RESULT_OK && requestCode == TO_SELECT_PIC5) 
		{
			picPath = data.getStringExtra(SelectPicActivity.KEY_PHOTO_PATH);
			Bitmap bm = BitmapUtil.getBitmap(picPath);
			bm = PictureUtils.imageZoom(bm, PlayApplication.maxConpressSize);
			Iv5.setScaleType(ScaleType.FIT_CENTER);
			Iv5.setImageBitmap(bm);
            try {
				FileUtils.saveImage(bm,picPath);
	            Toast.makeText(
	                    PublicSecondActivity.this,
	                    "Save Succeed", 100).show();

	            MediaScannerConnection.scanFile(this,new String[]{Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath() + "/" + picPath}, null, null);
	          //  picPath = FileUtils.getCacheFile();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			datas.add(picPath);
		}
		if (resultCode == Activity.RESULT_OK && requestCode == TO_SELECT_PIC6) 
		{
			picPath = data.getStringExtra(SelectPicActivity.KEY_PHOTO_PATH);
			Bitmap bm = BitmapUtil.getBitmap(picPath);
			bm = PictureUtils.imageZoom(bm, PlayApplication.maxConpressSize);
			Iv6.setScaleType(ScaleType.FIT_CENTER);
			Iv6.setImageBitmap(bm);
            try {
				FileUtils.saveImage(bm,picPath);
	            Toast.makeText(
	                    PublicSecondActivity.this,
	                    "Save Succeed", 100).show();

	            MediaScannerConnection.scanFile(this,new String[]{Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath() + "/" + picPath}, null, null);
	          //  picPath = FileUtils.getCacheFile();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			datas.add(picPath);
		}
		if (resultCode == Activity.RESULT_OK && requestCode == TO_SELECT_PIC7) 
		{
			picPath = data.getStringExtra(SelectPicActivity.KEY_PHOTO_PATH);
			Bitmap bm = BitmapUtil.getBitmap(picPath);
			bm = PictureUtils.imageZoom(bm, PlayApplication.maxConpressSize);
			Iv7.setScaleType(ScaleType.FIT_CENTER);
			Iv7.setImageBitmap(bm);
            try {
				FileUtils.saveImage(bm,picPath);
	            Toast.makeText(
	                    PublicSecondActivity.this,
	                    "Save Succeed", 100).show();

	            MediaScannerConnection.scanFile(this,new String[]{Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath() + "/" + picPath}, null, null);
	          //  picPath = FileUtils.getCacheFile();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			datas.add(picPath);
		}
		if (resultCode == Activity.RESULT_OK && requestCode == TO_SELECT_PIC8) 
		{
			picPath = data.getStringExtra(SelectPicActivity.KEY_PHOTO_PATH);
			Bitmap bm = BitmapUtil.getBitmap(picPath);
			bm = PictureUtils.imageZoom(bm, PlayApplication.maxConpressSize);
			Iv8.setScaleType(ScaleType.FIT_CENTER);
			Iv8.setImageBitmap(bm);
            try {
				FileUtils.saveImage(bm,picPath);
	            Toast.makeText(
	                    PublicSecondActivity.this,
	                    "Save Succeed", 100).show();

	            MediaScannerConnection.scanFile(this,new String[]{Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath() + "/" + picPath}, null, null);
	          //  picPath = FileUtils.getCacheFile();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			datas.add(picPath);
		}
	}
    /**
     * 鏍规嵁Uri杩斿洖鏂囦欢璺緞
     * 
     * @Title: getInputString
     * @param uri
     * @return
     * @return String
     */
    private String getFilePath(Uri uri) {
        try {
            if (uri.getScheme().equals("file")) {
                return uri.getPath();
            } else {
                return getFilePathByUri(uri);
            }
        } catch (FileNotFoundException ex) {
            return null;
        }
    }

    /**
     * 
     * @Title: getFilePathByUri
     * @param mUri
     * @return
     * @return String
     */
    private String getFilePathByUri(Uri mUri) throws FileNotFoundException {
        String imgPath;
        Cursor cursor = getContentResolver()
                .query(mUri, null, null, null, null);
        cursor.moveToFirst();
        imgPath = cursor.getString(1);
        cursor.close();
        return imgPath;
    }

    private int getPictureSize(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();

        int size = cursor.getInt(2) / 1024;
        cursor.close();

        return size;
    }

    /**
     * 压缩图片。
     * 
     * @param path
     *            图片路径
     * @return 压缩后的图片
     * @throws IOException
     */
    private Bitmap compressImage(String path) throws IOException {
        // 获取压缩图片的最大尺寸
        double size = 200;
        // 压缩图片
        Bitmap bitmap = PictureUtils.compressImage(path, 800);

        bitmap = PictureUtils.imageZoom(bitmap, size);
        return bitmap;
    }
}
