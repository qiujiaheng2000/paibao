package com.play.treasure.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.play.treasure.Functions;
import com.play.treasure.PlayApplication;
import com.play.treasure.R;
import com.play.treasure.adapter.CommentAdapter;
import com.play.treasure.adapter.MinefallAdapter;
import com.play.treasure.model.Comment;
import com.play.treasure.model.PlayDetail;
import com.play.treasure.network.model.NetworkBean;
import com.play.treasure.network.model.NetworkBeanArray;
import com.play.treasure.utils.CommonProgressDialog;
import com.play.treasure.utils.ToastUtil;
import com.play.treasure.view.SlideShowView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.system.email.Email;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.tencent.weibo.TencentWeibo;
import cn.sharesdk.wechat.favorite.WechatFavorite;

/**
 * @author wangchao29
 * @ClassName: PlayDetailActivity
 * @Description: 详情页面
 * @date 2014年12月5日 上午12:10:05
 */
public class PlayDetailMineActivity extends Activity implements View.OnClickListener {
    private ImageView titleLeft;

    private TextView titleCenter, qq, phone;

    private ImageView titleRight;

    private ListView mList;

    private CommentAdapter mAdapter;

    private MinefallAdapter mMineAdapter;

    private View headView;
    private TextView TvAddress;
    private TextView TvOwner;

    private TextView TvPraiseNum;
    private TextView TvCommentNum;

    private TextView product_detail;
    private TextView TvTitle;

    private TextView TvCommentCount;
    private Button BtLike;
    private Button BtComment;
    private CommonProgressDialog progressDialog;

    private LinearLayout linearlayout_qq;
    private LinearLayout linearlayout_phone;
    private String qqNum;

    private String phoneNum;

    private String title;

    private String address;
    private String owner;
    private String detail;
    private String praiseNum;

    private String commentNum;

    private String play_id;

    private PlayApplication mApplication;

    public static Handler mHanlder;

    private SlideShowView mView;
    private String isPraise = "0";
    private TextView TvCity;
    public BitmapUtils bu;

    public String getIsPraise() {
        return isPraise;
    }

    public void setIsPraise(String isPraise) {
        this.isPraise = isPraise;
    }

    private String code;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_detail_mine);

        headView = getLayoutInflater().inflate(R.layout.detail_comment_header, null);
        TvCity = (TextView) headView
                .findViewById(R.id.banner_distance_text_city);
        mApplication = PlayApplication.getApplication();

        titleLeft = (ImageView) findViewById(R.id.title_bar_left);
        titleLeft.setImageResource(R.drawable.back480_03);
        titleLeft.setVisibility(View.VISIBLE);

        titleLeft.setOnClickListener(this);
        titleCenter = (TextView) findViewById(R.id.title_bar_center);
        titleCenter.setText("宝贝详情");
        titleCenter.setVisibility(View.VISIBLE);
//		titleCenter.setTextSize(16);

        titleRight = (ImageView) findViewById(R.id.title_bar_right);
        titleRight.setImageResource(R.drawable.share);
        titleRight.setVisibility(View.VISIBLE);
        titleRight.setOnClickListener(this);

        progressDialog = CommonProgressDialog.getInstance(this);
        bu = Functions.initbimapxUtils(bu, getApplicationContext());

        String result = getIntent().getStringExtra("result");
        // 初始化分享sdk控件
        ShareSDK.initSDK(this);

        try {
            Intent intent = getIntent();
            play_id = intent.getStringExtra("play_id");

            new CommentTask().execute();
            if (result != null) {
                update(new JSONObject(result).getJSONObject("result"));
            } else {
                new PlayDetailTask().execute();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        mList = (ListView) findViewById(R.id.play_detail_comment_list);
        mList.addHeaderView(headView);

        mAdapter = new CommentAdapter(this);
        mList.setAdapter(mAdapter);

        linearlayout_qq = (LinearLayout) findViewById(R.id.linearlayout_qq);
        qq = (TextView) linearlayout_qq.findViewById(R.id.qq);
        linearlayout_qq.setOnClickListener(this);
        linearlayout_phone = (LinearLayout) findViewById(R.id.linearlayout_phone);
        linearlayout_phone.setOnClickListener(this);
        phone = (TextView) linearlayout_phone.findViewById(R.id.phone);

        TvTitle = (TextView) findViewById(R.id.play_name);
        TvOwner = (TextView) findViewById(R.id.play_shop);
        TvAddress = (TextView) findViewById(R.id.play_address);
        TvPraiseNum = (TextView) findViewById(R.id.praise_count);
        TvCommentNum = (TextView) findViewById(R.id.comment_count);
        product_detail = (TextView) findViewById(R.id.product_detail);
        TvCommentCount = (TextView) findViewById(R.id.comment_counts);
        BtLike = (Button) findViewById(R.id.btn_like);
        BtLike.setOnClickListener(this);
        BtComment = (Button) findViewById(R.id.btn_comment);
        BtComment.setOnClickListener(this);

        mView = (SlideShowView) headView.findViewById(R.id.slideshowView);

        mHanlder = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                if (msg.what == 1) {
                    Log.i("treasure", "treasure-abc");
                    mView.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            msg.arg1));
                }
                super.handleMessage(msg);
            }


        };

        mView.setHandlerParentView(mHanlder);
        //DisplayMetrics display = this.getResources().getDisplayMetrics();
        //	int bannerwidth = display.widthPixels;
        //	int bannerheight = 450;
        //	float height = this.getResources().getDimension(R.dimen.detail_banner_head);
        //	mView.setLayoutParams(new LinearLayout.LayoutParams(android.widget.FrameLayout.LayoutParams.MATCH_PARENT, (int)bannerheight));
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.title_bar_left:
                PlayDetailMineActivity.this.finish();
                break;
            case R.id.linearlayout_qq:

                /**final CommonDialog alertDialogQq = new CommonDialog.Builder(this)
                 .setTitle("QQ号码").setContent(qqNum).setConfirm("复制").build();

                 alertDialogQq.setCancelable(false);
                 alertDialogQq.getWindow().setType(
                 WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                 alertDialogQq.setOnButtonClickListener(new CommonDialog.OnButtonClickListener()
                 {
                 @Override public void onButtonClick(Button confirm, Button cancel)
                 {
                 cancel.setOnClickListener(new OnClickListener() {

                 @Override public void onClick(View v) {
                 alertDialogQq.dismiss();
                 }
                 });
                 confirm.setOnClickListener(new OnClickListener()
                 {
                 @SuppressLint("NewApi")
                 @SuppressWarnings("deprecation")
                 @Override public void onClick(View v)
                 {
                 if(qqNum==null||qqNum.equals("无号码"))
                 {
                 ToastUtil.showMessage("无号码");
                 }
                 else
                 {
                 alertDialogQq.dismiss();
                 ClipboardManager cmb = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                 cmb.setText(qqNum);
                 ToastUtil.showMessage("您复制了QQ号码："+qqNum);
                 }

                 }
                 });
                 }
                 });
                 alertDialogQq.show();**/
                if (qqNum == null || qqNum.equals("无号码")) {
                    ToastUtil.showMessage("无号码");
                } else if (Functions.isAvilible(PlayDetailMineActivity.this, "com.tencent.mobileqq")) {
                    String QQUrl = "mqqwpa://im/chat?chat_type=wpa&uin=" + qqNum + "&version=1";
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(QQUrl)));
                }
                break;
            case R.id.linearlayout_phone:
                /**final CommonDialog alertDialogPhone = new CommonDialog.Builder(this)
                 .setTitle("电话号码").setContent(phoneNum).setConfirm("呼叫").build();
                 alertDialogPhone.setCancelable(false);
                 alertDialogPhone.getWindow().setType(
                 WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                 alertDialogPhone.setOnButtonClickListener(new CommonDialog.OnButtonClickListener()
                 {
                 @Override public void onButtonClick(Button confirm, Button cancel)
                 {
                 cancel.setOnClickListener(new OnClickListener() {

                 @Override public void onClick(View v) {
                 alertDialogPhone.dismiss();
                 }
                 });
                 confirm.setOnClickListener(new OnClickListener()
                 {
                 @Override public void onClick(View v)
                 {
                 if(phoneNum.equals("无号码"))
                 {
                 ToastUtil.showMessage("无号码");
                 }
                 else
                 {
                 alertDialogPhone.dismiss();
                 Intent intent = new Intent(Intent.ACTION_DIAL, // 创建一个意图
                 Uri.parse("tel:" + phoneNum));
                 startActivity(intent);// 开始意图(及拨打电话)
                 }

                 }
                 });
                 }
                 });
                 alertDialogPhone.show();**/
                if (phoneNum == null || phoneNum.equals("无号码")) {
                    ToastUtil.showMessage("无号码");
                } else {
                    //alertDialogPhone.dismiss();
                    Intent intent2 = new Intent(
                            Intent.ACTION_DIAL, // 创建一个意图
                            Uri.parse("tel:" + phoneNum));
                    startActivity(intent2);// 开始意图(及拨打电话)
                }
                break;
            case R.id.title_bar_right:
                share(false, null);
                break;
            case R.id.btn_comment:

                if (mApplication.getSharedPreLoginName() == "" && mApplication.getSharedPreLoginPwd() == "" && mApplication.getUserId() == "") {
                    ToastUtil.showMessage("请先登录您的账户");
                } else {
                    intent.setClass(PlayDetailMineActivity.this, CommentActivity.class);
                    startActivity(intent);
                    PlayDetailMineActivity.this.finish();
                }

                break;
            case R.id.btn_like:

                if (mApplication.getSharedPreLoginName() == "" && mApplication.getSharedPreLoginPwd() == "" && mApplication.getUserId() == "") {
                    ToastUtil.showMessage("请先登录您的账户");
                } else {
                    int count = Integer.parseInt(praiseNum);
                    TvPraiseNum.setText("( " + String.valueOf(count + 1) + " )");
                    new IsPraiseTask().execute();
                }


                break;
            default:
                break;
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private class IsPraiseTask extends AsyncTask<Void, Void, NetworkBean> {
        @Override
        protected void onPreExecute() {
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected NetworkBean doInBackground(Void... params) {
            try {
                return mApplication.getNetApi().isPraise(mApplication.getTid(), mApplication.getUserId());
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
                setCode(result.getCode());
                String msg = result.getMessage();
                ToastUtil.showMessage(msg);
                if ("10000".equals(result.getCode())) {
                    BtLike.setEnabled(false);
                    BtLike.setClickable(false);
                    BtLike.setBackgroundColor(Color.GRAY);
                }
            }
        }
    }

    /**
     * @author wangchao29
     * @ClassName: CommentTask
     * @Description: 获取评论
     * @date 2014年11月24日 上午12:07:35
     */
    private class CommentTask extends AsyncTask<Void, Void, NetworkBeanArray> {

        @Override
        protected void onPreExecute() {
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected NetworkBeanArray doInBackground(Void... params) {
            try {
                return mApplication.getNetApi().playComment(mApplication.getTid());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(NetworkBeanArray result) {
            super.onPostExecute(result);
            if (result != null) {
                progressDialog.dismiss();
                try {
                    JSONArray json = new JSONArray(result.getResult());
                    List<Comment> commentList = new ArrayList<Comment>();
                    Comment comment = null;
                    for (int i = 0; i < json.length(); i++) {
                        comment = new Comment(json.getJSONObject(i));
                        commentList.add(comment);
                    }
                    mAdapter.addAll(commentList);
                    mAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private void update(JSONObject result) {
        Log.d("duanzhibo", "111111111---------" + result.toString());
        progressDialog.dismiss();
        try {
            PlayDetail pd = new PlayDetail(result);
            title = pd.getTitle();
            TvTitle.setText(title);
            owner = pd.getOwner();
            TvOwner.setText(owner);
            address = pd.getAddress();
            int index = 0;
            if (address != null) {
                index = address.indexOf("市") + 1;
            }
            if (index == 0) {
                TvCity.setText(address);
            } else {
                TvCity.setText(address.substring(0, index));
            }
            TvAddress.setText(address);
            praiseNum = pd.getPraiseNum();
            TvPraiseNum.setText("( " + praiseNum + " )");
            commentNum = pd.getCommentNum();
            TvCommentNum.setText("( " + commentNum + " )");
            detail = pd.getDetail();
            product_detail.setText(detail);
            phoneNum = pd.getPhoneNum();
            if (phoneNum.equals("")) {
                phoneNum = "无号码";
                linearlayout_phone.setEnabled(false);
                phone.setTextColor(Color.rgb(176, 176, 176));
            }
            qqNum = pd.getQqNum();
            if (qqNum.equals("")) {
                qqNum = "无号码";
                linearlayout_qq.setEnabled(false);
                qq.setTextColor(Color.rgb(176, 176, 176));
            }
            TvCommentCount.setText("( " + commentNum + " )");
            setIsPraise(pd.getIspraise());
            if ("1".equals(getIsPraise())) {
                BtLike.setEnabled(false);
                BtLike.setClickable(false);
                BtLike.setBackgroundColor(Color.GRAY);
            } else {
                BtLike.setEnabled(true);
                BtLike.setClickable(true);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private class PlayDetailTask extends AsyncTask<Void, Void, NetworkBean> {

        @Override
        protected void onPreExecute() {
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected NetworkBean doInBackground(Void... params) {
            try {
                return mApplication.getNetApi().playDetail(mApplication.getTid(), mApplication.getUserId(), mApplication.getLongitude(), mApplication.getLatitude());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(NetworkBean result) {
            super.onPostExecute(result);
            if (result != null) {
                update(result.getData());
            }
        }
    }

    // 一键分享的点击事件
    public void share(boolean silent, String platform) {
        // 实例化一个OnekeyShare对象
        OnekeyShare oks = new OnekeyShare();
        // 设置Notification的显示图标和显示文字
//			oks.setNotification(R.drawable.icon_app,
//					this.getString(R.string.app_name));
        // 分享内容的标题
        oks.setTitle(TvTitle.getText().toString());
        // 标题对应的网址，如果没有可以不设置
        oks.setTitleUrl("http://182.92.240.52/play/apk/paibao.apk");
        // 设置分享的文本内容
        oks.setText(product_detail.getText().toString());
        // 设置分享照片的本地路径，如果没有可以不设置
//        oks.setImagePath(bu.getBitmapFileFromDiskCache(mApplication.getImageUrls().get(0)).getAbsolutePath());
        // 微信和易信的分享的网络连接，如果没有可以不设置
        // oks.setUrl("http://sharesdk.cn");
        // 人人平台特有的评论字段，如果没有可以不设置
        // imageUrl是图片的网络路径，新浪微博、人人网、QQ空间、
        // 微信的两个平台、Linked-In支持此字段
        //拮抗剂
        //	private TextView product_detail;
        //  private TextView TvTitle;
        oks.setUrl("http://182.92.240.52/play/index.php/Home/Share/index.html?tid=" + mApplication.getTid());
        oks.setImageUrl(mApplication.getImageUrls().get(0));
        //oks.setComment("comment");
        // 令编辑页面显示为Dialog模式
        oks.setDialogMode();
        // 为EditPage设置一个背景的View
        //oks.setEditPageBackground(initPage());
        // 为EditPage设置一个背景的View
        oks.addHiddenPlatform(TencentWeibo.NAME);
        oks.addHiddenPlatform(WechatFavorite.NAME);
        oks.addHiddenPlatform(Email.NAME);
        oks.addHiddenPlatform(SinaWeibo.NAME);
        oks.addHiddenPlatform(QZone.NAME);
        oks.addHiddenPlatform(QQ.NAME);
        // 设置是否是直接分享
        oks.setSilent(false);
        if (platform != null) {
            oks.setPlatform(platform);
        }
        // 显示
        oks.show(PlayDetailMineActivity.this);
    }
}
