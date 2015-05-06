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

public class FeedBackActivity extends Activity implements OnClickListener {
	private PlayApplication mApplication;

	private ImageView titleLeft;

	private TextView titleCenter;
	private EditText EtFeedback;
	private String content;
	private Button Submit;
	private String msg;
	private String code;
	private CommonProgressDialog progressDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback);

		mApplication = PlayApplication.getApplication();

		titleLeft = (ImageView) findViewById(R.id.title_bar_left);
		titleLeft.setImageResource(R.drawable.back480_03);
		titleLeft.setVisibility(View.VISIBLE);
		titleLeft.setOnClickListener(this);

		titleCenter = (TextView) findViewById(R.id.title_bar_center);
		titleCenter.setText("意见反馈");
		titleCenter.setVisibility(View.VISIBLE);
		titleCenter.setTextSize(16);

		progressDialog = CommonProgressDialog.getInstance(this);
		EtFeedback = (EditText) findViewById(R.id.content);
		Submit = (Button) findViewById(R.id.submit);
		Submit.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.title_bar_left:
			FeedBackActivity.this.finish();
			break;

		case R.id.submit:
			setContent(EtFeedback.getText().toString());
			new feedBackTask().execute();
			
			

			break;
		default:
			break;
		}
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public class feedBackTask extends AsyncTask<Void, Void, NetworkBean> {

		@Override
		protected void onPreExecute() {
			progressDialog.show();
			super.onPreExecute();
		}

		@Override
		protected NetworkBean doInBackground(Void... params) {
			try {
				return mApplication.getNetApi().feedBack(
						mApplication.getUserId(), getContent());
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
				code = result.getCode();
				 msg = result.getMessage();
					Toast.makeText(FeedBackActivity.this, msg, Toast.LENGTH_LONG).show();
					if("10000".equals(code))
					{
						Intent intent = new Intent();
						intent.setClass(FeedBackActivity.this, SettingActivity.class);
						startActivity(intent);
						FeedBackActivity.this.finish();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

}
