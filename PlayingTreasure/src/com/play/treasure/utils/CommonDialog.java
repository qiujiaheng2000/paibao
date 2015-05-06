package com.play.treasure.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.play.treasure.R;

/**
 *  通用普通对话框 
 * 〈功能详细描述〉
 *
 * @author 王超
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class CommonDialog extends Dialog
{
    /**
     * 标题
     */
    private String mTitle;

    /**
     * 内容
     */
    private String mContent;
    /**
     * 确认
     */
    private String mConfirm;
    /**
     * 取消
     */
    private String mCancel;
    /**
     * 是否是单按钮dialog
     */
    private boolean isSingle;
    private TextView content;

    /**
     * 按钮监听器对象
     */
    private OnButtonClickListener mOnButtonClickListener = new OnButtonClickListener()
    {
        @Override
        public void onButtonClick(Button confirm, Button cancel)
        {

        }
    };

    /**
     * 
     * @param context
     * @param titleId
     * @param contentId
     */
    private CommonDialog(Builder builder)
    {
        super(builder.context, R.style.FullHeightDialog);
        mTitle = builder.mTitle;
        mContent = builder.mContent;
        mConfirm = builder.mConfirm;
        mCancel = builder.mCancel;
        isSingle = builder.isSingle;
    }

    /**
     * build dialog 
     *
     * @author 王超
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    public static class Builder
    {
        private Context context;
        private String mTitle;
        private String mContent;
        private String mCancel;
        private String mConfirm;
        private boolean isSingle;

        /**
         * 
         * @param context
         */
        public Builder(Context context)
        {
            this.context = context;
            mConfirm = context.getResources().getString(R.string.confirm);
            mCancel = context.getResources().getString(R.string.cancel);
        }

        /**
         * 功能描述: title
         *
         * @param title
         * @return
         * @see [相关类/方法](可选)
         * @since [产品/模块版本](可选)
         */
        public Builder setTitle(String title)
        {
            mTitle = title;
            return this;
        }

        /**
         * 功能描述:title
         *
         * @param titleId
         * @return
         * @see [相关类/方法](可选)
         * @since [产品/模块版本](可选)
         */
        public Builder setTitle(int titleId)
        {
            mTitle = context.getResources().getString(titleId);
            return this;
        }

        /**
         * 功能描述: 设置内容
         * 〈功能详细描述〉
         *
         * @param content
         * @return
         * @see [相关类/方法](可选)
         * @since [产品/模块版本](可选)
         */
        public Builder setContent(String content)
        {
            mContent = content;
            return this;
        }

        /**
         * 功能描述: 设置内容
         * 〈功能详细描述〉
         *
         * @param contentId
         * @return
         * @see [相关类/方法](可选)
         * @since [产品/模块版本](可选)
         */
        public Builder setContent(int contentId)
        {
            mContent = context.getResources().getString(contentId);
            return this;
        }

        /**
         * 功能描述: 设置confirm
         * 〈功能详细描述〉
         *
         * @param confirm
         * @return
         * @see [相关类/方法](可选)
         * @since [产品/模块版本](可选)
         */
        public Builder setConfirm(String confirm)
        {
            mConfirm = confirm;
            return this;
        }

        /**
         * 功能描述: 设置confirm
         * 〈功能详细描述〉
         *
         * @param confirmId
         * @return
         * @see [相关类/方法](可选)
         * @since [产品/模块版本](可选)
         */
        public Builder setConfirm(int confirmId)
        {
            mConfirm = context.getResources().getString(confirmId);
            return this;
        }

        /**
         * 功能描述: 设置cancel
         * 〈功能详细描述〉
         *
         * @param cancel
         * @return
         * @see [相关类/方法](可选)
         * @since [产品/模块版本](可选)
         */
        public Builder setCancel(String cancel)
        {
            mCancel = cancel;
            return this;
        }

        /**
         * 功能描述: 设置cancel
         * 〈功能详细描述〉
         *
         * @param cancelId
         * @return
         * @see [相关类/方法](可选)
         * @since [产品/模块版本](可选)
         */
        public Builder setCancel(int cancelId)
        {
            mCancel = context.getResources().getString(cancelId);
            return this;
        }

        /**
         * 功能描述: 设置是否一个按钮
         * 〈功能详细描述〉
         *
         * @param isSingle
         * @return
         * @see [相关类/方法](可选)
         * @since [产品/模块版本](可选)
         */
        public Builder isSingle(boolean isSingle)
        {
            this.isSingle = isSingle;
            return this;
        }

        /**
         * 功能描述: bulid dialog
         * 〈功能详细描述〉
         *
         * @return
         * @see [相关类/方法](可选)
         * @since [产品/模块版本](可选)
         */
        public CommonDialog build()
        {
            return new CommonDialog(this);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog_layout);
        TextView title = (TextView) findViewById(R.id.title);
        content = (TextView) findViewById(R.id.content);
        Button cancel = (Button) findViewById(R.id.cancel);
        Button confirm = (Button) findViewById(R.id.confirm);
        TextView diver = (TextView) findViewById(R.id.tv_diver);
        title.setText(mTitle);
        setContent(mContent);
        confirm.setText(mConfirm);
        if (isSingle)
        {
            cancel.setVisibility(View.GONE);
            diver.setVisibility(View.GONE);
        }
        else
        {
            cancel.setText(mCancel);
        }
        mOnButtonClickListener.onButtonClick(confirm, cancel);
    }

    public void setContent(String contentString)
    {
        content.setText(contentString);
    }
   

    public void setOnButtonClickListener(OnButtonClickListener l)
    {
        mOnButtonClickListener = l;
    }

    public interface OnButtonClickListener
    {
        void onButtonClick(Button confirm, Button cancel);
    }
}
