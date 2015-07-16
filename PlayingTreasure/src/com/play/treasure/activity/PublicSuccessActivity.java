package com.play.treasure.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.tools.utils.UIHandler;
import com.play.treasure.FrameActivity;
import com.play.treasure.R;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.system.email.Email;
import cn.sharesdk.tencent.weibo.TencentWeibo;
import cn.sharesdk.wechat.favorite.WechatFavorite;

public class PublicSuccessActivity extends Activity implements
		OnClickListener,Callback {
	private TextView titleCenter;
	//private ImageView titleRight;
	private TextView Success;
	//private ImageView titleLeft;
	private static final String FILE_NAME = "pic_beauty_on_sofa1.jpg";
	public static String TEST_IMAGE;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_publicplays_success);
		/*titleLeft = (ImageView)findViewById(R.id.title_bar_left);
		titleLeft.setImageResource(R.drawable.back480_03);
		titleLeft.setVisibility(View.VISIBLE);
		titleLeft.setOnClickListener(this);*/
		titleCenter = (TextView) findViewById(R.id.title_bar_center);
		titleCenter.setText("发帖成功");
		titleCenter.setVisibility(View.VISIBLE);
		titleCenter.setTextSize(16);
		/*titleRight = (ImageView)findViewById(R.id.title_bar_right);
		titleRight.setImageResource(R.drawable.next);
		titleRight.setVisibility(View.VISIBLE);
		titleRight.setOnClickListener(this);*/
		Success = (TextView) findViewById(R.id.success);
		Success.setOnClickListener(this);

		Button sharebutton = (Button) findViewById(R.id.sharebutton);
		sharebutton.setOnClickListener(this);
		// 初始化分享sdk控件
		ShareSDK.initSDK(this);
		new Thread() {
			public void run() {
				initImagePath();
			}
		}.start();
		result = getIntent().getStringExtra("result");
		detail = getIntent().getStringExtra("detail");
		title = getIntent().getStringExtra("title");
		
	}
	private String result;
	private String title;
	private String detail;
	
	public void shopDisplay(View view){
		Intent intent = new Intent(this,PlayDetailMineActivity.class);
		intent.putExtra("result", result);
		
		startActivity(intent);
	}
	
	protected View initPage() {
		return LayoutInflater.from(this)
				.inflate(R.layout.activity_publicplays_success, null);
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.title_bar_left:
			PublicSuccessActivity.this.finish();
			break;
		case R.id.success:
			//intent.setClass(PublicSuccessActivity.this, MainActivity.class);
			//startActivity(intent);
			break;
		case R.id.title_bar_right:
			intent.setClass(PublicSuccessActivity.this, FrameActivity.class);
			
			startActivity(intent);
			PublicSuccessActivity.this.finish();
			break;
		case R.id.sharebutton:
			share(false, null);
			break;

		default:
			break;

		}
	}

	// 一键分享的点击事件
	public void share(boolean silent, String platform) {
		// 实例化一个OnekeyShare对象
		OnekeyShare oks = new OnekeyShare();
		// 设置Notification的显示图标和显示文字
//		oks.setNotification(R.drawable.icon_app,
//				this.getString(R.string.app_name));
		// 分享内容的标题
		oks.setTitle(title+"##快来拍宝看我的宝贝吧##");
		// 标题对应的网址，如果没有可以不设置
		oks.setTitleUrl("http://182.92.240.52/play/apk/paibao.apk");
		// 设置分享的文本内容
		oks.setText(detail);
		// 设置分享照片的本地路径，如果没有可以不设置
		oks.setImagePath( PublicSuccessActivity.TEST_IMAGE);
		// 微信和易信的分享的网络连接，如果没有可以不设置
		// oks.setUrl("http://sharesdk.cn");
		// 人人平台特有的评论字段，如果没有可以不设置
		// imageUrl是图片的网络路径，新浪微博、人人网、QQ空间、
        // 微信的两个平台、Linked-In支持此字段
       // oks.setImageUrl("http://diy.qqjay.com/u/files/2012/0523/f466c38e1c6c99ee2d6cd7746207a97a.jpg");
	//	oks.setComment("comment");
		// 令编辑页面显示为Dialog模式
		oks.setDialogMode();
		// 为EditPage设置一个背景的View
		oks.setEditPageBackground(initPage());
		oks.addHiddenPlatform(TencentWeibo.NAME);
		oks.addHiddenPlatform(WechatFavorite.NAME);
//		oks.addHiddenPlatform(Renren.NAME);
		oks.addHiddenPlatform(Email.NAME);
		// 设置是否是直接分享
		oks.setSilent(false);
		if (platform != null) {
			oks.setPlatform(platform);
		}
		// 显示
		oks.show(PublicSuccessActivity.this);
	}

	private void initImagePath() {
		try {
			if (Environment.MEDIA_MOUNTED.equals(Environment
					.getExternalStorageState())
					&& Environment.getExternalStorageDirectory().exists()) {
				TEST_IMAGE = Environment.getExternalStorageDirectory()
						.getAbsolutePath() + FILE_NAME;
			} else {
				TEST_IMAGE = getApplication().getFilesDir().getAbsolutePath()
						+ FILE_NAME;
			}
			// 创建图片文件夹
			File file = new File(TEST_IMAGE);
			if (!file.exists()) {
				file.createNewFile();
				Bitmap pic = BitmapFactory.decodeResource(getResources(),
						R.drawable.pic);
				FileOutputStream fos = new FileOutputStream(file);
				pic.compress(CompressFormat.JPEG, 100, fos);
				fos.flush();
				fos.close();
			}
		} catch (Throwable t) {
			t.printStackTrace();
			TEST_IMAGE = null;
		}
	}

	
	/** 将action转换为String */
	public static String actionToString(int action) {
		switch (action) {
			case Platform.ACTION_AUTHORIZING: return "ACTION_AUTHORIZING";
			case Platform.ACTION_GETTING_FRIEND_LIST: return "ACTION_GETTING_FRIEND_LIST";
			case Platform.ACTION_FOLLOWING_USER: return "ACTION_FOLLOWING_USER";
			case Platform.ACTION_SENDING_DIRECT_MESSAGE: return "ACTION_SENDING_DIRECT_MESSAGE";
			case Platform.ACTION_TIMELINE: return "ACTION_TIMELINE";
			case Platform.ACTION_USER_INFOR: return "ACTION_USER_INFOR";
			case Platform.ACTION_SHARE: return "ACTION_SHARE";
			default: {
				return "UNKNOWN";
			}
		}
	}
	public void onComplete(Platform plat, int action,
			HashMap<String, Object> res) {

		Message msg = new Message();
		msg.arg1 = 1;
		msg.arg2 = action;
		msg.obj = plat;
		UIHandler.sendMessage(msg, this);
	}

	public void onCancel(Platform palt, int action) {
		Message msg = new Message();
		msg.arg1 = 3;
		msg.arg2 = action;
		msg.obj = palt;
		UIHandler.sendMessage(msg, this);
	}

	public void onError(Platform palt, int action, Throwable t) {
		t.printStackTrace();

		Message msg = new Message();
		msg.arg1 = 2;
		msg.arg2 = action;
		msg.obj = palt;
		UIHandler.sendMessage(msg, this);
	}

	public boolean handleMessage(Message msg) {
		Platform plat = (Platform) msg.obj;
		String text = actionToString(msg.arg2);
		switch (msg.arg1) {
			case 1: {
				// 成功
				text = plat.getName() + " completed at " + text;
			}
			break;
			case 2: {
				// 失败
				text = plat.getName() + " caught error at " + text;
			}
			break;
			case 3: {
				// 取消
				text = plat.getName() + " canceled at " + text;
			}
			break;
		}

		Toast.makeText(PublicSuccessActivity.this, text, Toast.LENGTH_SHORT).show();
		return false;
	}

}



