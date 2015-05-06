package com.play.treasure.activity;

import com.play.treasure.PlayApplication;
import com.play.treasure.R;
import com.play.treasure.network.model.NetworkBean;
import com.play.treasure.utils.CommonProgressDialog;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactActivity extends Activity implements OnClickListener{
	private ImageView titleLeft;

	private TextView titleCenter;
	
	private TextView TvContact;
	
	private PlayApplication mApplication;
	
	private CommonProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_textcontact);
		mApplication = PlayApplication.getApplication();

		titleLeft = (ImageView)findViewById(R.id.title_bar_left);
		titleLeft.setImageResource(R.drawable.back480_03);
		titleLeft.setVisibility(View.VISIBLE);
		titleLeft.setOnClickListener(this);

		titleCenter = (TextView) findViewById(R.id.title_bar_center);
		titleCenter.setText("业务合作及联系");
		titleCenter.setVisibility(View.VISIBLE);
		titleCenter.setTextSize(16);
		
		progressDialog = CommonProgressDialog.getInstance(this);
		TvContact = (TextView)findViewById(R.id.contactus);
		new loadContactTask().execute();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_bar_left:
			ContactActivity.this.finish();
			break;

		}

	}
	public class loadContactTask extends AsyncTask<Void, Void, NetworkBean>
	{

		CommonProgressDialog dialog = null;
		@Override
		protected void onPreExecute() {
			
			if(!progressDialog.isShowing()){
				progressDialog.show();
			}
			super.onPreExecute();
		}

		@Override
		protected NetworkBean doInBackground(Void... params) 
		{
			try
			{
				return mApplication.getNetApi().contactUs();
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
				try {
						
						String contact = result.getResult();
						TvContact.setText(contact);
						
							
						
						
					}
				 catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
	}


}
