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

import com.play.treasure.FrameActivity;
import com.play.treasure.PlayApplication;
import com.play.treasure.R;
import com.play.treasure.network.model.NetworkBean;
import com.play.treasure.utils.CommonProgressDialog;

import org.json.JSONObject;

public class RegisterActivity extends Activity implements OnClickListener {
    private ImageView titleLeft;

    private TextView titleCenter;

    private PlayApplication mApplication;

    private String phoneNumber;
    private EditText EtPhone;

    private String userName;
    private EditText EtUserName;

    private String passWord;
    private EditText EtPwd;

    private String confirmPwd;
    private EditText EtConfirmPwd;

    private String nickName;
    private EditText EtNickName;

    private String address;
    private EditText EtAddress;

    private Button mButton;

    private CommonProgressDialog progressDialog;

    private String mStringPhone ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mApplication = PlayApplication.getApplication();

        titleLeft = (ImageView) findViewById(R.id.title_bar_left);
        titleLeft.setImageResource(R.drawable.back480_03);
        titleLeft.setVisibility(View.VISIBLE);
        titleLeft.setOnClickListener(this);
        titleCenter = (TextView) findViewById(R.id.title_bar_center);
        titleCenter.setText("注册");
        titleCenter.setVisibility(View.VISIBLE);
        titleCenter.setTextSize(16);
        progressDialog = new CommonProgressDialog(this, R.style.dialog);
        progressDialog.setMsg("加载中...");

        mStringPhone = getIntent().getStringExtra("phone");

        EtPhone = (EditText) findViewById(R.id.phone);
        EtPhone.setText(mStringPhone);
        EtPhone.setEnabled(false);


        EtUserName = (EditText) findViewById(R.id.username);
        EtPwd = (EditText) findViewById(R.id.pwd);
        EtConfirmPwd = (EditText) findViewById(R.id.repwd);
        EtNickName = (EditText) findViewById(R.id.nickname);
        EtAddress = (EditText) findViewById(R.id.address);
        mButton = (Button) findViewById(R.id.register);
        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        phoneNumber = String.valueOf(EtPhone.getText().toString());
        userName = String.valueOf(EtUserName.getText().toString());
        passWord = String.valueOf(EtPwd.getText().toString());
        confirmPwd = String.valueOf(EtConfirmPwd.getText().toString());
        nickName = String.valueOf(EtNickName.getText().toString());
        address = String.valueOf(EtAddress.getText().toString());
        switch (v.getId()) {
            case R.id.title_bar_left:
                RegisterActivity.this.finish();
                break;
            case R.id.register:
                if (passWord.equals(confirmPwd)) {
                    new RegisterTask().execute();
                } else {
                    Toast.makeText(RegisterActivity.this, "两次密码不一样", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }

    }

    private class RegisterTask extends AsyncTask<Void, Void, NetworkBean> {
        @Override
        protected void onPreExecute() {
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected NetworkBean doInBackground(Void... params) {
            try {
                return mApplication.getNetApi().register(phoneNumber, userName, passWord, nickName, address);
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
                String msg = result.getMessage();
                String code = result.getCode();
                String userId = null;
                if (code.equals("10000")) {
                    Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_SHORT).show();
                    try {
                        JSONObject jsonObject = result.getData();
                        if (jsonObject.has("uid")) {
                            userId = jsonObject.getString("uid");
                            mApplication.setUserId(userId);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    mApplication.setSharedPreLoginName(userName);
                    mApplication.setSharedPreLoginPwd(passWord);
                    Intent intent = new Intent();
                    intent.setClass(RegisterActivity.this, FrameActivity.class);
                    startActivity(intent);
                    RegisterActivity.this.finish();

                } else {
                    Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_SHORT).show();
                    mApplication.setSharedPreLoginName("");
                    mApplication.setSharedPreLoginPwd("");
                }
            }
        }
    }
}
