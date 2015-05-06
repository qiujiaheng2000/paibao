package com.play.treasure.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;

import com.play.treasure.PlayApplication;
import com.play.treasure.R;
 
public class CategoryPopWindow extends PopupWindow {
 
   private Button btn_0; 
   private Button btn_1; 
   private Button btn_2;
   private Button btn_3;
   private Button btn_4;
   private View mMenuView;
   private PlayApplication mApplication;
 
   public CategoryPopWindow(Activity context,OnClickListener itemsOnClick) {
       super(context);
       mMenuView = context.getLayoutInflater().inflate(R.layout.alert_dialog, null);
       mApplication = PlayApplication.getApplication();
       btn_0 = (Button) mMenuView.findViewById(R.id.btn_0);
       btn_1 = (Button) mMenuView.findViewById(R.id.btn_1);
       btn_2 = (Button) mMenuView.findViewById(R.id.btn_2);
       btn_3 = (Button) mMenuView.findViewById(R.id.btn_3);
       btn_4 = (Button) mMenuView.findViewById(R.id.btn_4);
      // btn_cancel = (Button) mMenuView.findViewById(R.id.btn_cancel);
       //取消按钮
       /*btn_cancel.setOnClickListener(new OnClickListener() {
 
           public void onClick(View v) {
               //销毁弹出框
               dismiss();
           }
       });*/
       //设置按钮监听
       btn_0.setOnClickListener(itemsOnClick);
       btn_1.setOnClickListener(itemsOnClick);
       btn_2.setOnClickListener(itemsOnClick);
       btn_3.setOnClickListener(itemsOnClick);
       btn_4.setOnClickListener(itemsOnClick);
       //设置SelectPicPopupWindow的View
       this.setContentView(mMenuView);
       //设置SelectPicPopupWindow弹出窗体的宽
       this.setWidth(mApplication.getWidth()/2);
       //设置SelectPicPopupWindow弹出窗体的高
       this.setHeight(LayoutParams.WRAP_CONTENT);
       //设置SelectPicPopupWindow弹出窗体可点击
       this.setFocusable(true);
       //设置SelectPicPopupWindow弹出窗体动画效果
       this.setAnimationStyle(R.style.AnimBottom);
       //实例化一个ColorDrawable颜色为半透明
       ColorDrawable dw = new ColorDrawable(Color.TRANSPARENT);
       //设置SelectPicPopupWindow弹出窗体的背景
       this.setBackgroundDrawable(dw);
       //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
       mMenuView.setOnTouchListener(new OnTouchListener() {
            
           public boolean onTouch(View v, MotionEvent event) {
                
               int height = mMenuView.findViewById(R.id.pop_layout).getTop();
               int y=(int) event.getY();
               if(event.getAction()==MotionEvent.ACTION_UP){
                   if(y<height){
                       dismiss();
                   }
               }               
               return true;
           }
       });
 
   }
 
}
