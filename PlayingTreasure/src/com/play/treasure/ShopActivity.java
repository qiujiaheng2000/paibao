package com.play.treasure;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.etsy.android.grid.StaggeredGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshStaggeredGridView;
import com.lidroid.xutils.BitmapUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.play.treasure.activity.PlayDetailActivity;
import com.play.treasure.activity.PublicSuccessActivity;
import com.play.treasure.model.Waterfall;
import com.play.treasure.network.NetworkConfig;
import com.play.treasure.network.model.NetworkBeanArray;
import com.play.treasure.utils.CommonProgressDialog;
import com.play.treasure.waterfall.WaterfallAdapter;
import com.play.treasure.widget.CircularImage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.renren.Renren;
import cn.sharesdk.system.email.Email;
import cn.sharesdk.tencent.weibo.TencentWeibo;
import cn.sharesdk.wechat.favorite.WechatFavorite;

public class ShopActivity extends Activity {
    private String uid;
    private StaggeredGridView mGridView;
    private WaterfallAdapter mAdapter;
    private PlayApplication mApplication;
    private CommonProgressDialog progressDialog;
    private LinkedList<Waterfall> founctionlist = new LinkedList<Waterfall>();
    private LinkedList<Waterfall> temp = new LinkedList<Waterfall>();
    private BitmapUtils bitmapUtils;
    private PullToRefreshStaggeredGridView mPullRefreshListView;
    protected int scroll_y;
    private int p = 1;
    private TextView shopName, address, phone, qq, shopInfo, title;
    private CircularImage cover_user_photo;
    private ImageView mUserPhoto;
    private View auth;
    private DisplayImageOptions options;
    private View mHeaderView;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.color.white)
                .showImageOnLoading(R.color.white)
                .showImageOnFail(R.color.white)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .displayer(new RoundedBitmapDisplayer(100))
                .build();


        uid = getIntent().getStringExtra("uid");

        mHeaderView = getLayoutInflater().inflate(R.layout.activity_shop_header, null);


        title = (TextView) findViewById(R.id.title_bar_center);
        shopName = (TextView) mHeaderView.findViewById(R.id.shop_name);
        shopInfo = (TextView) mHeaderView.findViewById(R.id.content);
        mUserPhoto = (ImageView) mHeaderView.findViewById(R.id.cover_user_photo);
        address = (TextView) mHeaderView.findViewById(R.id.local);
        phone = (TextView) mHeaderView.findViewById(R.id.phone);
        qq = (TextView) mHeaderView.findViewById(R.id.qq);
        auth = mHeaderView.findViewById(R.id.auth);

        mApplication = PlayApplication.getApplication();
        progressDialog = new CommonProgressDialog(this, R.style.dialog);
        progressDialog.setMsg("加载中...");
        mPullRefreshListView = (PullToRefreshStaggeredGridView) findViewById(R.id.grid_view);
        initListView();
        //getData();
        mGridView.addHeaderView(mHeaderView, null, false);
        mAdapter = new WaterfallAdapter(this);
        mGridView.setAdapter(mAdapter);
//		mGridView.setOnItemClickListener(mAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    mApplication.setBannerId(((Waterfall) parent.getAdapter().getItem(position)).getPlayId());
                    mApplication.setTid(((Waterfall) parent.getAdapter().getItem(position)).getPlayId());

                    Intent intent = new Intent();
                    intent.putExtra("detail_distance", ((Waterfall) parent.getAdapter().getItem(position)).getDistance());
                    intent.putExtra("detail_category", ((Waterfall) parent.getAdapter().getItem(position)).getPlayCategory());

                    mApplication.setCategory(((Waterfall) parent.getAdapter().getItem(position)).getPlayCategory());
                    mApplication.setDistance(((Waterfall) parent.getAdapter().getItem(position)).getDistance());

                    intent.setClass(ShopActivity.this, PlayDetailActivity.class);
                    ShopActivity.this.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        new WaterfallTask().execute();
        new ShopUserTask().execute();
    }

    @SuppressLint("NewApi")
    public void initListView() {
        mPullRefreshListView.setMode(Mode.BOTH);

        mPullRefreshListView.getLoadingLayoutProxy(true, false).setLastUpdatedLabel("上次刷新时间");
        mPullRefreshListView.getLoadingLayoutProxy(true, false).setPullLabel("");
        mPullRefreshListView.getLoadingLayoutProxy(true, false).setRefreshingLabel("正在刷新...");
        mPullRefreshListView.getLoadingLayoutProxy(true, false).setReleaseLabel("松开刷新");

        mPullRefreshListView.getLoadingLayoutProxy(false, true).setLastUpdatedLabel("上次加载时间");
        mPullRefreshListView.getLoadingLayoutProxy(false, true).setPullLabel("");
        mPullRefreshListView.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载...");
        mPullRefreshListView.getLoadingLayoutProxy(false, true).setReleaseLabel("松开加载");

        mGridView = mPullRefreshListView.getRefreshableView();
        mPullRefreshListView.setOnRefreshListener(new OnRefreshListener<StaggeredGridView>() {

            @SuppressLint("NewApi")
            @Override
            public void onRefresh(PullToRefreshBase<StaggeredGridView> refreshView) {
                String label = DateUtils.formatDateTime(ShopActivity.this, System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

                if (refreshView.isHeaderShown()) {
                    refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                    mAdapter.clear();
                    mAdapter.notifyDataSetInvalidated();
                    p = 1;
                    scroll_y = 0;
                    new WaterfallTask().execute();
                } else {
                    p++;
                    scroll_y = founctionlist.size();
                    new WaterfallTask().execute();
                }

            }

        });
    }

    // 一键分享的点击事件
    public void share(View v) {
        // 实例化一个OnekeyShare对象
        OnekeyShare oks = new OnekeyShare();
        // 设置Notification的显示图标和显示文字
        oks.setNotification(R.drawable.icon_app,
                this.getString(R.string.app_name));
        // 分享内容的标题
        oks.setTitle(shopName.getText().toString() + " 入驻拍宝");
        // 标题对应的网址，如果没有可以不设置
        oks.setTitleUrl("http://182.92.240.52/play/apk/paibao.apk");
        // 设置分享的文本内容
        oks.setText(shopInfo.getText().toString());
        // 设置分享照片的本地路径，如果没有可以不设置
        oks.setImagePath(PublicSuccessActivity.TEST_IMAGE);
        // 微信和易信的分享的网络连接，如果没有可以不设置
        // oks.setUrl("http://sharesdk.cn");
        // 人人平台特有的评论字段，如果没有可以不设置
        // imageUrl是图片的网络路径，新浪微博、人人网、QQ空间、
        // 微信的两个平台、Linked-In支持此字段
        //	oks.setImageUrl("http://diy.qqjay.com/u/files/2012/0523/f466c38e1c6c99ee2d6cd7746207a97a.jpg");
        //oks.setComment("comment");
        // 令编辑页面显示为Dialog模式
        oks.setDialogMode();
        // 为EditPage设置一个背景的View
        // oks.setEditPageBackground(initPage());
        oks.addHiddenPlatform(TencentWeibo.NAME);
        oks.addHiddenPlatform(WechatFavorite.NAME);
        oks.addHiddenPlatform(Email.NAME);
        oks.addHiddenPlatform(Renren.NAME);
        // 设置是否是直接分享
        oks.setSilent(false);

        // 显示
        oks.show(this);
    }

    public void back(View v) {
        finish();
    }

    public void shopPic(View v) {
        Intent intent = new Intent(this, ShopImageActivity.class);
        intent.putExtra("uid", uid);
        intent.putExtra("title", title.getText());
        startActivity(intent);
    }

    public class ShopUserTask extends AsyncTask<Void, Void, NetworkBeanArray> {
        @Override
        protected void onPreExecute() {
            if (!progressDialog.isShowing()) {
                progressDialog.show();
            }
            super.onPreExecute();
        }

        @Override
        protected NetworkBeanArray doInBackground(Void... arg0) {
            try {
                Log.d("duanzhibo", "-------------uid--------" + uid);
                return mApplication.getNetApi().shopUser(uid);
            } catch (NullPointerException ex) {
                ex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(NetworkBeanArray result) {
            // TODO Auto-generated method stub
            progressDialog.dismiss();
            if (result != null) {
                try {
                    JSONArray jsonArray = new JSONArray(result.getResult());
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    Log.i("jsonArray", result.getResult() + "");
                    String name = jsonObject.getString("nickname");
                    shopName.setText(name);
                    title.setText(name);
                    shopInfo.setText(jsonObject.getString("abstract"));
                    address.setText(jsonObject.getString("address"));
                    phone.setText(jsonObject.getString("mobile"));
                    qq.setText(jsonObject.getString("qq"));
                    auth.setVisibility(jsonObject.getString("auth").equals("1") ? View.VISIBLE : View.GONE);
                    ImageLoader.getInstance().displayImage(NetworkConfig.baseImgUrl + jsonObject.getString("photo"), mUserPhoto, options);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            super.onPostExecute(result);
        }

    }


    public class WaterfallTask extends AsyncTask<Void, Void, NetworkBeanArray> {
        @Override
        protected void onPreExecute() {
            if (!progressDialog.isShowing()) {
                progressDialog.show();
            }
            super.onPreExecute();
        }

        @Override
        protected NetworkBeanArray doInBackground(Void... params) {
            try {
                return mApplication.getNetApi().shopWaterfall(mApplication.getLongitude(), mApplication.getLatitude(), String.valueOf(p), uid);
            } catch (NullPointerException ex) {
                ex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(NetworkBeanArray result) {
            if (result != null) {
                progressDialog.dismiss();
                try {
                    JSONArray jsonArray = new JSONArray(result.getResult());
                    List<Waterfall> waterList = new ArrayList<Waterfall>();
                    Waterfall water = null;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        water = new Waterfall(jsonArray.getJSONObject(i));
                        waterList.add(water);
                    }
                    if (waterList.size() == 0) {
                        p--;
                    } else {
                        mAdapter.addAll(waterList);
                        mAdapter.notifyDataSetChanged();

                    }
                    mPullRefreshListView.onRefreshComplete();
                    mPullRefreshListView.scrollTo(0, scroll_y);
                } catch (Exception e) {
                    mPullRefreshListView.onRefreshComplete();
                    e.printStackTrace();
                }
            }
        }
    }
}
