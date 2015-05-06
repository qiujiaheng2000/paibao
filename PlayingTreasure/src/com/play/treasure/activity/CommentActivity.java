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

public class CommentActivity extends Activity implements OnClickListener {
	private PlayApplication mApplication;
	private ImageView titleLeft;
	private String tid;
	private TextView titleCenter;
	private EditText EtComment;
	private String content;
	private Button Submit;
	private CommonProgressDialog progressDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment);
		mApplication = PlayApplication.getApplication();
		titleLeft = (ImageView) findViewById(R.id.title_bar_left);
		titleLeft.setImageResource(R.drawable.back480_03);
		titleLeft.setVisibility(View.VISIBLE);
		titleLeft.setOnClickListener(this);

		titleCenter = (TextView) findViewById(R.id.title_bar_center);
		titleCenter.setText("评论");
		titleCenter.setVisibility(View.VISIBLE);
		titleCenter.setTextSize(16);
		
		progressDialog = CommonProgressDialog.getInstance(this);

		EtComment = (EditText) findViewById(R.id.content);
		Submit = (Button) findViewById(R.id.submit);
		Submit.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_bar_left:
			CommentActivity.this.finish();
			break;

		case R.id.submit:
			content = EtComment.getText().toString();
			tid = mApplication.getTid();
			new commentTask().execute();
			/*Intent intent = new Intent();
			intent.setClass(CommentActivity.this, PlayDetailActivity.class);
			startActivity(intent);
			CommentActivity.this.finish();*/
			

			break;
		default:
			break;
		}
		
	}

	
	public class commentTask extends AsyncTask<Void, Void, NetworkBean> {

		@Override
		protected void onPreExecute() {
			if(!progressDialog.isShowing()){
				progressDialog.show();
			}
			super.onPreExecute();
		}

		@Override
		protected NetworkBean doInBackground(Void... params) {
			try {
				return mApplication.getNetApi().commentTreasure(mApplication.getUserId(), tid, content);
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

					String msg = result.getMessage();
					String code = result.getCode();
					
					if(code.equals("10000"))
					{
						Toast.makeText(CommentActivity.this, msg, Toast.LENGTH_LONG).show();
						Intent intent = new Intent();
						intent.setClass(CommentActivity.this, PlayDetailActivity.class);
						startActivity(intent);
						CommentActivity.this.finish();
					}
					else
					{
						Toast.makeText(CommentActivity.this, msg, Toast.LENGTH_LONG).show();
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

}
