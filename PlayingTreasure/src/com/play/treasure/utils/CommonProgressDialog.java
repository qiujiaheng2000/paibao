package com.play.treasure.utils;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.play.treasure.R;

/** 
 * @ClassName: ProgressDialog
 * @Description: 进度加载提示框
 * @author wangchao29
 * @date 2014年12月21日 下午3:39:09
 * 
 */
public class CommonProgressDialog extends Dialog
{
	private static CommonProgressDialog dialog;
	public static CommonProgressDialog getInstance(Context context){
		if(dialog == null){
			dialog = new CommonProgressDialog(context, R.style.dialog);
			dialog.setMsg("加载中...");
		}
		return dialog;
	}
	private TextView loadingMsg;
	
	/** 
	* Title:
	* Description:
	* @param context
	*/ 
	private CommonProgressDialog(Context context) 
	{
		super(context);
	}
	
	/** 
	* Title:
	* Description:
	* @param context
	* @param theme
	*/ 
	public CommonProgressDialog(Context context, int theme) 
	{
		super(context, theme);
		setContentView(R.layout.common_loading_dialog);
		setCanceledOnTouchOutside(false);
		loadingMsg = (TextView)findViewById(R.id.common_progress_msg);
		
	}
	
	public void setMsg(String msg)
	{
		loadingMsg.setText(msg);
	}

}
