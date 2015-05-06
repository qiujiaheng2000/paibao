package com.play.treasure.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.play.treasure.R;

/** 
* @ClassName: ProgressDialog
* @Description: 进度提示
* @author wangchao29
* @date 2014年12月11日 下午11:21:02
* 
*/ 
public class ProgressDialog extends Dialog 
{
    private TextView msgText;
    
    private ProgressBar mProgressBar;
    
    public ProgressDialog(Context context, int style) 
    {
        super(context, style);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.layout_loading_progress);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        msgText = (TextView) findViewById(R.id.description);
    }
    
    private Handler mHandler = new Handler(Looper.getMainLooper()){
        public void handleMessage(android.os.Message msg){
        	 mProgressBar.setProgress(msg.what);
        };
    };
    
    public Handler getmHandler() {
		return mHandler;
	}

	public void setMsgText(String msgText) 
    {
    	this.msgText.setText(msgText);
    }
}
