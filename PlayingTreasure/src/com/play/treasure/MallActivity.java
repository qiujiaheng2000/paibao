package com.play.treasure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.bitmap.callback.DefaultBitmapLoadCallBack;
import com.play.treasure.activity.LoginActivity;
import com.play.treasure.activity.PlayDetailActivity;
import com.play.treasure.activity.PublicFirstActivity;
import com.play.treasure.network.NetworkConfig;
import com.play.treasure.network.model.NetworkBean;
import com.play.treasure.network.model.NetworkBeanArray;

@SuppressLint("NewApi")
public class MallActivity extends BackActivity {
    private LinearLayout ll_focus_indicator_container = null, first_header, header_ll;
    private RelativeLayout header_top2;
    private MyGallery gallery = null;
    List<Map<String, String>> list = new ArrayList<Map<String, String>>();
    public boolean timeFlag = true;
    WindowManager windowManager;
    int sw, sh;
    MyAdapter myAdapter;
    private int preSelImgIndex = 0;

    private ViewPager mPager;
    private ArrayList<Fragment> fragmentsList;
    private ImageView ivBottomLine, title_bar_left, title_bar_right;
    private TextView title_bar_center;
    private RadioButton tvTabWeina, tvTabOulaiya, tvTabKashi, tvTabShihuakou, tvTabMeiqishi;

    private RadioButton tvTabWeina2, tvTabOulaiya2, tvTabKashi2, tvTabShihuakou2, tvTabMeiqishi2;
    private ImageView ivBottomLine2;

    private RadioButton[] rg = new RadioButton[5];
    private RadioButton[] rg2 = new RadioButton[5];

    private RadioGroup top_radio_button_group2;
    private int position_one2;
    private int position_two2;
    private int position_three2;
    private int position_four2;

    //= {tvTabWeina, tvTabOulaiya, tvTabKashi,tvTabShihuakou,tvTabMeiqishi};
    private int currIndex = 0;

    private int position_one;
    private int position_two;
    private int position_three;
    private int position_four;
    private boolean secondornot = false;

    public static final int USERCENTER_POSITION = 4;
    public static final int GO_LOGIN = 1001;

    static String[] from = {"img", "name", "url"};

    public static BitmapUtils bitmapUtils;
    private RelativeLayout top_title_bar;
    private FrameLayout framelayout;
    public TranslateAnimation animation2;
    public static Handler controllHandler;
    //private WebView bannerView;
    private PlayApplication mApplication;
    private ImageView title_bar_right2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(R.drawable.abc_item_background_holo_light);
        setContentView(R.layout.activity_mall);
        mApplication = PlayApplication.getApplication();
        //resources = getResources();
        bitmapUtils = Functions.initbimapxUtils(bitmapUtils, this);

        windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        sw = windowManager.getDefaultDisplay().getWidth();
        //��Ļ�߶�
        sh = windowManager.getDefaultDisplay().getHeight();

        ll_focus_indicator_container = (LinearLayout) findViewById(R.id.ll_focus_indicator_container);
        InitFocusIndicatorContainer();

        title_bar_left = (ImageView) findViewById(R.id.title_bar_left);
        title_bar_right = (ImageView) findViewById(R.id.title_bar_right);
        title_bar_right2 = (ImageView) findViewById(R.id.title_bar_right2);
        title_bar_center = (TextView) findViewById(R.id.title_bar_center);
        title_bar_center.setText("拍宝 ●文玩秀 ");
        title_bar_right.setBackgroundResource(R.drawable.takephoto);
        title_bar_right2.setBackgroundResource(R.drawable.takephoto);
        title_bar_center.setVisibility(View.VISIBLE);
        title_bar_right.setVisibility(View.VISIBLE);
        title_bar_right2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                if (!TextUtils.isEmpty(mApplication.getUserId())) {

                    //mViewPager.setCurrentItem(4);
                    //	initBottemBtn();
                    //  mMyBottemUser.setImageResource(R.drawable.user_selected);
                    //mTabHost.setCurrentTab(USERCENTER_POSITION);
                    //		View v = list.get(position);
                    //	container.addView(v);

                    //	return v;
                    Intent i = new Intent(MallActivity.this, PublicFirstActivity.class);
                    startActivity(i);
                } else {
                    Intent intent = new Intent();
                    intent.setClass(MallActivity.this, LoginActivity.class);
                    startActivityForResult(intent, GO_LOGIN);
                    //return null;
                }


            }
        });
        title_bar_right.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                if (!TextUtils.isEmpty(mApplication.getUserId())) {

                    //mViewPager.setCurrentItem(4);
                    //	initBottemBtn();
                    //  mMyBottemUser.setImageResource(R.drawable.user_selected);
                    //mTabHost.setCurrentTab(USERCENTER_POSITION);
                    //		View v = list.get(position);
                    //	container.addView(v);

                    //	return v;
                    Intent i = new Intent(MallActivity.this, PublicFirstActivity.class);
                    startActivity(i);
                } else {
                    Intent intent = new Intent();
                    intent.setClass(MallActivity.this, LoginActivity.class);
                    startActivityForResult(intent, GO_LOGIN);
                    //return null;
                }


            }
        });

        first_header = (LinearLayout) findViewById(R.id.first_header);
        header_ll = (LinearLayout) findViewById(R.id.header_ll);
        gallery = (MyGallery) findViewById(R.id.banner_gallery);
        top_title_bar = (RelativeLayout) findViewById(R.id.top_title_bar);
        header_top2 = (RelativeLayout) findViewById(R.id.header_top2);
        framelayout = (FrameLayout) findViewById(R.id.framelayout);
        top_radio_button_group2 = (RadioGroup) findViewById(R.id.top_radio_button_group2);

		/*bannerView = (WebView) findViewById(R.id.main_banner);

		WebSettings webSettings = bannerView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

		bannerView.loadUrl(NetworkConfig.baseurl + NetworkConfig.bannerUrl);

		bannerView.addJavascriptInterface(new MyJavascriptInterface(this), "imagelistner");
		bannerView.setWebViewClient(new MyWebViewClient());
		bannerView.setWebChromeClient(new WebChromeClient());*/


        gallery.setDrawingCacheBackgroundColor(Color.TRANSPARENT);
        controllHandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                if (msg.what == 1) {
                    framelayout.setVisibility(View.GONE);
                    //framelayout.animate().y(framelayout.getTop() - framelayout.getHeight() - top_title_bar.getHeight()).setDuration(300).start();
                    top_title_bar.setVisibility(View.GONE);
                    first_header.setVisibility(View.GONE);
                    header_top2.setVisibility(View.VISIBLE);
                    secondornot = true;
                } else {
                    framelayout.setVisibility(View.VISIBLE);
                    top_title_bar.setVisibility(View.VISIBLE);
                    first_header.setVisibility(View.VISIBLE);
                    header_top2.setVisibility(View.GONE);
                    secondornot = false;
                }
                super.handleMessage(msg);
            }


        };
        gallery.setFocusable(true);
        new Thread() {
            @Override
            public void run() {
                getCate();
                Message msg = mHandler.obtainMessage();
                msg.what = 1;
                msg.sendToTarget();
            }
        }.start();
        gallery.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Map<String, String> map = list.get(arg2);
                Log.d("qjh", "map = " + map);

                Intent intent = new Intent();
                mApplication.setTid(map.get("tid"));
                mApplication.setBannerId(map.get("tid"));
                intent.setClass(MallActivity.this, PlayDetailActivity.class);
                startActivity(intent);
            }

        });
        gallery.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int selIndex, long arg3) {
                selIndex = selIndex % list.size();

                ImageView preSelImg = (ImageView) ll_focus_indicator_container
                        .findViewById(preSelImgIndex);
                preSelImg.setImageDrawable(MallActivity.this.getResources()
                        .getDrawable(R.drawable.ic_focus));
                ImageView curSelImg = (ImageView) ll_focus_indicator_container
                        .findViewById(selIndex);
                curSelImg.setImageDrawable(MallActivity.this.getResources()
                        .getDrawable(R.drawable.ic_focus_select));
                preSelImgIndex = selIndex;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        InitTextView2();
        InitWidth_t();

        InitWidth();
        InitTextView();
        InitViewPager();
        tvTabOulaiya2.setTextColor(getResources().getColor(R.color.white));
        tvTabOulaiya2.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
    }


    private static final ColorDrawable TRANSPARENT_DRAWABLE = new ColorDrawable(android.R.color.transparent);

    private void InitWidth() {
        //��ȡ���
        ivBottomLine = (ImageView) findViewById(R.id.iv_bottom_line);
        position_one = (int) (sw / 5.0);
        LayoutParams para;
        para = ivBottomLine.getLayoutParams();
        para.width = position_one;
        para.height = 2;
        ivBottomLine.setLayoutParams(para);
        position_two = position_one * 2;
        position_three = position_one * 3;
        position_four = position_one * 4;
    }

    private void InitWidth_t() {
        //��ȡ���
        ivBottomLine2 = (ImageView) findViewById(R.id.iv_bottom_line2);
        position_one2 = (int) (header_ll.getWidth() / 5.0);
        LayoutParams para;
        para = ivBottomLine2.getLayoutParams();
        para.width = position_one2;
        para.height = 2;
        ivBottomLine2.setLayoutParams(para);
        position_two2 = position_one2 * 2;
        position_three2 = position_one2 * 3;
        position_four2 = position_one2 * 4;
    }

    private void InitTextView2() {
        tvTabWeina2 = (RadioButton) findViewById(R.id.tv_tab_activity2);
        tvTabOulaiya2 = (RadioButton) findViewById(R.id.tv_tab_groups2);
        tvTabKashi2 = (RadioButton) findViewById(R.id.tv_tab_friends2);
        tvTabShihuakou2 = (RadioButton) findViewById(R.id.tv_tab_chat2);
        tvTabMeiqishi2 = (RadioButton) findViewById(R.id.tv_tab_mei2);

        tvTabWeina2.getPaint().setFakeBoldText(true);
        tvTabOulaiya2.getPaint().setFakeBoldText(true);
        tvTabKashi2.getPaint().setFakeBoldText(true);
        tvTabShihuakou2.getPaint().setFakeBoldText(true);
        tvTabMeiqishi2.getPaint().setFakeBoldText(true);

        rg2[0] = tvTabWeina2;
        rg2[1] = tvTabOulaiya2;
        rg2[2] = tvTabKashi2;
        rg2[3] = tvTabShihuakou2;
        rg2[4] = tvTabMeiqishi2;

        tvTabWeina2.setOnClickListener(new MyOnClickListener(0));
        tvTabOulaiya2.setOnClickListener(new MyOnClickListener(1));
        tvTabKashi2.setOnClickListener(new MyOnClickListener(2));
        tvTabShihuakou2.setOnClickListener(new MyOnClickListener(3));
        //	tvTabShihuakou.setVisibility(View.GONE);
        tvTabMeiqishi2.setOnClickListener(new MyOnClickListener(4));
        //	tvTabMeiqishi.setVisibility(View.GONE);
    }

    private void InitTextView() {
        tvTabWeina = (RadioButton) findViewById(R.id.tv_tab_activity);
        tvTabOulaiya = (RadioButton) findViewById(R.id.tv_tab_groups);
        tvTabKashi = (RadioButton) findViewById(R.id.tv_tab_friends);
        tvTabShihuakou = (RadioButton) findViewById(R.id.tv_tab_chat);
        tvTabMeiqishi = (RadioButton) findViewById(R.id.tv_tab_mei);
        rg[0] = tvTabWeina;
        rg[1] = tvTabOulaiya;
        rg[2] = tvTabKashi;
        rg[3] = tvTabShihuakou;
        rg[4] = tvTabMeiqishi;
        tvTabWeina.setOnClickListener(new MyOnClickListener(0));
        tvTabOulaiya.setOnClickListener(new MyOnClickListener(1));
        tvTabKashi.setOnClickListener(new MyOnClickListener(2));
        tvTabShihuakou.setOnClickListener(new MyOnClickListener(3));
        //	tvTabShihuakou.setVisibility(View.GONE);
        tvTabMeiqishi.setOnClickListener(new MyOnClickListener(4));
        //	tvTabMeiqishi.setVisibility(View.GONE);
    }

    private void InitViewPager() {
        mPager = (ViewPager) findViewById(R.id.vPager);
        fragmentsList = new ArrayList<Fragment>();

        Fragment weinaFragment = FirstFragment.newInstance("0");
        Fragment oulaiyaFragment = FirstFragment.newInstance("1");
        Fragment kashiFragment = FirstFragment.newInstance("2");
        Fragment shihuakouFragment = FirstFragment.newInstance("3");
        Fragment meiqishiFragment = FirstFragment.newInstance("4");

        fragmentsList.add(weinaFragment);
        fragmentsList.add(oulaiyaFragment);
        fragmentsList.add(kashiFragment);
        fragmentsList.add(shihuakouFragment);
        fragmentsList.add(meiqishiFragment);
        //new FragmentTabAdapter(IndexActivity.this, fragmentsList, currIndex, null)
        mPager.setOffscreenPageLimit(5);
        mPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentsList));
        mPager.setCurrentItem(0);
        mPager.setOnPageChangeListener(new MyOnPageChangeListener());

    }


    public class MyOnClickListener implements View.OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            mPager.setCurrentItem(index);
        }
    }

    ;

    public class MyOnPageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageSelected(int arg0) {
            Animation animation = null;
            switch (arg0) {

                case 0:

                    if (currIndex == 1) {
                        animation = new TranslateAnimation(position_one, 0, 0, 0);
                        tvTabOulaiya.setTextColor(getResources().getColor(R.color.black));
                        tvTabOulaiya.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        //        tvTabOulaiya.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.special_icon), null,null, null);
                    } else if (currIndex == 2) {
                        animation = new TranslateAnimation(position_two, 0, 0, 0);
                        tvTabKashi.setTextColor(getResources().getColor(R.color.black));
                        tvTabKashi.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        //  tvTabKashi.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.recommend_icon), null,null, null);
                    } else if (currIndex == 3) {
                        animation = new TranslateAnimation(position_three, 0, 0, 0);
                        tvTabShihuakou.setTextColor(getResources().getColor(R.color.black));
                        tvTabShihuakou.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        //   tvTabShihuakou.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.tuan_icon), null,null, null);
                    } else if (currIndex == 4) {
                        animation = new TranslateAnimation(position_four, 0, 0, 0);
                        tvTabMeiqishi.setTextColor(getResources().getColor(R.color.black));
                        tvTabMeiqishi.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        //    tvTabMeiqishi.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.mei_icon), null,null, null);
                    }
                    tvTabWeina.setTextColor(getResources().getColor(R.color.dialog_button));
                    tvTabWeina.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    // tvTabWeina.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.fashion_icon_s),null, null, null);
                    break;
                case 1:

                    if (currIndex == 0) {
                        animation = new TranslateAnimation(0, position_one, 0, 0);
                        tvTabWeina.setTextColor(getResources().getColor(R.color.black));
                        tvTabWeina.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        //  tvTabWeina.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.fashion_icon),null, null, null);

                    } else if (currIndex == 2) {
                        animation = new TranslateAnimation(position_two, position_one, 0, 0);
                        tvTabKashi.setTextColor(getResources().getColor(R.color.black));
                        tvTabKashi.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        //  tvTabKashi.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.recommend_icon), null,null, null);
                    } else if (currIndex == 3) {
                        animation = new TranslateAnimation(position_three, position_one, 0, 0);
                        tvTabShihuakou.setTextColor(getResources().getColor(R.color.black));
                        tvTabShihuakou.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        //  tvTabShihuakou.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.tuan_icon), null,null, null);
                    } else if (currIndex == 4) {
                        animation = new TranslateAnimation(position_four, position_one, 0, 0);
                        tvTabMeiqishi.setTextColor(getResources().getColor(R.color.black));
                        tvTabMeiqishi.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        //tvTabMeiqishi.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.mei_icon), null,null, null);
                    }
                    tvTabOulaiya.setTextColor(getResources().getColor(R.color.dialog_button));
                    tvTabOulaiya.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    // tvTabOulaiya.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.special_icon_s),null, null, null);
                    break;
                case 2:
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(0, position_two, 0, 0);
                        tvTabWeina.setTextColor(getResources().getColor(R.color.black));
                        tvTabWeina.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        //   tvTabWeina.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.fashion_icon),null, null, null);

                    } else if (currIndex == 1) {
                        animation = new TranslateAnimation(position_one, position_two, 0, 0);
                        tvTabOulaiya.setTextColor(getResources().getColor(R.color.black));
                        tvTabOulaiya.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        //    tvTabOulaiya.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.special_icon), null,null, null);

                    } else if (currIndex == 3) {
                        animation = new TranslateAnimation(position_three, position_two, 0, 0);
                        tvTabShihuakou.setTextColor(getResources().getColor(R.color.black));
                        tvTabShihuakou.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        //  tvTabShihuakou.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.tuan_icon), null,null, null);

                    } else if (currIndex == 4) {
                        animation = new TranslateAnimation(position_four, position_two, 0, 0);
                        tvTabMeiqishi.setTextColor(getResources().getColor(R.color.black));
                        tvTabMeiqishi.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        //   tvTabMeiqishi.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.mei_icon), null,null, null);
                    }
                    tvTabKashi.setTextColor(getResources().getColor(R.color.dialog_button));
                    tvTabKashi.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    //     tvTabKashi.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.recommend_icon_s),null, null, null);
                    break;
                case 3:

                    if (currIndex == 0) {
                        animation = new TranslateAnimation(0, position_three, 0, 0);
                        tvTabWeina.setTextColor(getResources().getColor(R.color.black));
                        tvTabWeina.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        //    tvTabWeina.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.fashion_icon),null, null, null);

                    } else if (currIndex == 1) {
                        animation = new TranslateAnimation(position_one, position_three, 0, 0);
                        tvTabOulaiya.setTextColor(getResources().getColor(R.color.black));
                        tvTabOulaiya.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        //  setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.fashion_icon),null, null, null);
                        //    tvTabOulaiya.setButtonDrawable(R.drawable.special_icon);

                    } else if (currIndex == 2) {
                        animation = new TranslateAnimation(position_two, position_three, 0, 0);
                        tvTabKashi.setTextColor(getResources().getColor(R.color.black));
                        tvTabKashi.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        // tvTabKashi.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.recommend_icon), null,null, null);

                    } else if (currIndex == 4) {
                        animation = new TranslateAnimation(position_four, position_three, 0, 0);
                        tvTabMeiqishi.setTextColor(getResources().getColor(R.color.black));
                        tvTabMeiqishi.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        //  tvTabMeiqishi.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.mei_icon), null,null, null);
                    }
                    tvTabShihuakou.setTextColor(getResources().getColor(R.color.dialog_button));
                    tvTabShihuakou.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    //   tvTabShihuakou.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.tuan_icon_s),null, null, null);

                    break;
                case 4:

                    if (currIndex == 0) {
                        animation = new TranslateAnimation(0, position_four, 0, 0);
                        tvTabWeina.setTextColor(getResources().getColor(R.color.black));
                        tvTabWeina.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        // tvTabWeina.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.fashion_icon),null, null, null);

                    } else if (currIndex == 1) {
                        animation = new TranslateAnimation(position_one, position_four, 0, 0);
                        tvTabOulaiya.setTextColor(getResources().getColor(R.color.black));
                        tvTabOulaiya.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        ///   setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.fashion_icon),null, null, null);
                        //   tvTabOulaiya.setButtonDrawable(R.drawable.special_icon);

                    } else if (currIndex == 2) {
                        animation = new TranslateAnimation(position_two, position_four, 0, 0);
                        tvTabKashi.setTextColor(getResources().getColor(R.color.black));
                        tvTabKashi.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        //  tvTabKashi.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.recommend_icon), null,null, null);

                    } else if (currIndex == 3) {
                        animation = new TranslateAnimation(position_three, position_four, 0, 0);
                        tvTabShihuakou.setTextColor(getResources().getColor(R.color.black));
                        tvTabShihuakou.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        //    tvTabShihuakou.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.tuan_icon), null,null, null);

                    }
                    tvTabMeiqishi.setTextColor(getResources().getColor(R.color.dialog_button));
                    tvTabMeiqishi.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    // tvTabMeiqishi.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.mei_icon_s),null, null, null);

                    break;

            }

            switch (arg0) {

                case 0:

                    if (currIndex == 1) {
                        animation2 = new TranslateAnimation(position_one2 + header_ll.getX(), 0 + header_ll.getX(), 0, 0);
                        tvTabOulaiya2.setTextColor(getResources().getColor(R.color.white));
                        tvTabOulaiya2.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        //        tvTabOulaiya2.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.special_icon), null,null, null);
                    } else if (currIndex == 2) {
                        animation2 = new TranslateAnimation(position_two2 + header_ll.getX(), 0 + header_ll.getX(), 0, 0);
                        tvTabKashi2.setTextColor(getResources().getColor(R.color.white));
                        tvTabKashi2.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        //  tvTabKashi2.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.recommend_icon), null,null, null);
                    } else if (currIndex == 3) {
                        animation2 = new TranslateAnimation(position_three2 + header_ll.getX(), 0 + header_ll.getX(), 0, 0);
                        tvTabShihuakou2.setTextColor(getResources().getColor(R.color.white));
                        tvTabShihuakou2.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        //   tvTabShihuakou2.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.tuan_icon), null,null, null);
                    } else if (currIndex == 4) {
                        animation2 = new TranslateAnimation(position_four2 + header_ll.getX(), 0 + header_ll.getX(), 0, 0);
                        tvTabMeiqishi2.setTextColor(getResources().getColor(R.color.white));
                        tvTabMeiqishi2.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        //    tvTabMeiqishi2.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.mei_icon), null,null, null);
                    }
                    tvTabWeina2.setTextColor(getResources().getColor(R.color.white));
                    tvTabWeina2.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    // tvTabWeina2.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.fashion_icon_s),null, null, null);
                    break;
                case 1:

                    if (currIndex == 0) {
                        animation2 = new TranslateAnimation(0 + header_ll.getX(), position_one2 + header_ll.getX(), 0, 0);
                        tvTabWeina2.setTextColor(getResources().getColor(R.color.white));
                        tvTabWeina2.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        //  tvTabWeina2.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.fashion_icon),null, null, null);

                    } else if (currIndex == 2) {
                        animation2 = new TranslateAnimation(position_two2 + header_ll.getX(), position_one2 + header_ll.getX(), 0, 0);
                        tvTabKashi2.setTextColor(getResources().getColor(R.color.white));
                        tvTabKashi2.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        //  tvTabKashi2.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.recommend_icon), null,null, null);
                    } else if (currIndex == 3) {
                        animation2 = new TranslateAnimation(position_three2 + header_ll.getX(), position_one2 + header_ll.getX(), 0, 0);
                        tvTabShihuakou2.setTextColor(getResources().getColor(R.color.white));
                        tvTabShihuakou2.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        //  tvTabShihuakou2.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.tuan_icon), null,null, null);
                    } else if (currIndex == 4) {
                        animation2 = new TranslateAnimation(position_four2 + header_ll.getX(), position_one2 + header_ll.getX(), 0, 0);
                        tvTabMeiqishi2.setTextColor(getResources().getColor(R.color.white));
                        tvTabMeiqishi2.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        //tvTabMeiqishi2.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.mei_icon), null,null, null);
                    }
                    tvTabOulaiya2.setTextColor(getResources().getColor(R.color.white));
                    tvTabOulaiya2.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    // tvTabOulaiya2.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.special_icon_s),null, null, null);
                    break;
                case 2:
                    if (currIndex == 0) {
                        animation2 = new TranslateAnimation(0 + header_ll.getX(), position_two2 + header_ll.getX(), 0, 0);
                        tvTabWeina2.setTextColor(getResources().getColor(R.color.white));
                        tvTabWeina2.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        //   tvTabWeina2.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.fashion_icon),null, null, null);

                    } else if (currIndex == 1) {
                        animation2 = new TranslateAnimation(position_one2 + header_ll.getX(), position_two2 + header_ll.getX(), 0, 0);
                        tvTabOulaiya2.setTextColor(getResources().getColor(R.color.white));
                        tvTabOulaiya2.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        //    tvTabOulaiya2.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.special_icon), null,null, null);

                    } else if (currIndex == 3) {
                        animation2 = new TranslateAnimation(position_three2 + header_ll.getX(), position_two2 + header_ll.getX(), 0, 0);
                        tvTabShihuakou2.setTextColor(getResources().getColor(R.color.white));
                        tvTabShihuakou2.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        //  tvTabShihuakou2.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.tuan_icon), null,null, null);

                    } else if (currIndex == 4) {
                        animation2 = new TranslateAnimation(position_four2 + header_ll.getX(), position_two2 + header_ll.getX(), 0, 0);
                        tvTabMeiqishi2.setTextColor(getResources().getColor(R.color.white));
                        tvTabMeiqishi2.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        //   tvTabMeiqishi2.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.mei_icon), null,null, null);
                    }
                    tvTabKashi2.setTextColor(getResources().getColor(R.color.white));
                    tvTabKashi2.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    //     tvTabKashi2.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.recommend_icon_s),null, null, null);
                    break;
                case 3:

                    if (currIndex == 0) {
                        animation2 = new TranslateAnimation(0 + header_ll.getX(), position_three2 + header_ll.getX(), 0, 0);
                        tvTabWeina2.setTextColor(getResources().getColor(R.color.white));
                        tvTabWeina2.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        //    tvTabWeina2.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.fashion_icon),null, null, null);

                    } else if (currIndex == 1) {
                        animation2 = new TranslateAnimation(position_one2 + header_ll.getX(), position_three2 + header_ll.getX(), 0, 0);
                        tvTabOulaiya2.setTextColor(getResources().getColor(R.color.white));
                        tvTabOulaiya2.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        //  setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.fashion_icon),null, null, null);
                        //    tvTabOulaiya2.setButtonDrawable(R.drawable.special_icon);

                    } else if (currIndex == 2) {
                        animation2 = new TranslateAnimation(position_two2 + header_ll.getX(), position_three2 + header_ll.getX(), 0, 0);
                        tvTabKashi2.setTextColor(getResources().getColor(R.color.white));
                        tvTabKashi2.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        // tvTabKashi2.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.recommend_icon), null,null, null);

                    } else if (currIndex == 4) {
                        animation2 = new TranslateAnimation(position_four2 + header_ll.getX(), position_three2 + header_ll.getX(), 0, 0);
                        tvTabMeiqishi2.setTextColor(getResources().getColor(R.color.white));
                        tvTabMeiqishi2.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        //  tvTabMeiqishi2.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.mei_icon), null,null, null);
                    }
                    tvTabShihuakou2.setTextColor(getResources().getColor(R.color.white));
                    tvTabShihuakou2.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    //   tvTabShihuakou2.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.tuan_icon_s),null, null, null);

                    break;
                case 4:

                    if (currIndex == 0) {
                        animation2 = new TranslateAnimation(0 + header_ll.getX(), position_four2 + header_ll.getX(), 0, 0);
                        tvTabWeina2.setTextColor(getResources().getColor(R.color.white));
                        tvTabWeina2.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        // tvTabWeina2.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.fashion_icon),null, null, null);

                    } else if (currIndex == 1) {
                        animation2 = new TranslateAnimation(position_one2 + header_ll.getX(), position_four2 + header_ll.getX(), 0, 0);
                        tvTabOulaiya2.setTextColor(getResources().getColor(R.color.white));
                        tvTabOulaiya2.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        ///   setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.fashion_icon),null, null, null);
                        //   tvTabOulaiya2.setButtonDrawable(R.drawable.special_icon);

                    } else if (currIndex == 2) {
                        animation2 = new TranslateAnimation(position_two2 + header_ll.getX(), position_four2 + header_ll.getX(), 0, 0);
                        tvTabKashi2.setTextColor(getResources().getColor(R.color.white));
                        tvTabKashi2.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        //  tvTabKashi2.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.recommend_icon), null,null, null);

                    } else if (currIndex == 3) {
                        animation2 = new TranslateAnimation(position_three2 + header_ll.getX(), position_four2 + header_ll.getX(), 0, 0);
                        tvTabShihuakou2.setTextColor(getResources().getColor(R.color.white));
                        tvTabShihuakou2.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        //    tvTabShihuakou2.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.tuan_icon), null,null, null);

                    }
                    tvTabMeiqishi2.setTextColor(getResources().getColor(R.color.white));
                    tvTabMeiqishi2.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    // tvTabMeiqishi2.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.mei_icon_s),null, null, null);

                    break;

            }
            for (int i = 0; i < 5; i++) {
                if (i != arg0) {
                    rg2[i].setTextSize(12);
                } else {
                    rg2[i].setTextSize(20);
                }

            }
            Fragment fragment = fragmentsList.get(arg0);
            for (int i = 0; i < fragmentsList.size(); i++) {
                if (i != arg0) {
                    fragmentsList.get(i).onPause();
                } else {
                    if (fragment.isAdded()) {
//                fragment.onStart(); // ����Ŀ��tab��onStart()
                        fragment.onResume(); // ����Ŀ��tab��onResume()
                    } else {

                        FragmentTransaction ft = obtainFragmentTransaction(arg0, currIndex);
                        ft.add(currIndex, fragment);
                        ft.commit();
                    }
                }
            }
            showTab(arg0, currIndex);
            currIndex = arg0;

            animation.setFillAfter(true);
            animation.setDuration(150);

            ivBottomLine.startAnimation(animation);
            ivBottomLine2.startAnimation(animation2);

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }

    public Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            if (msg.what == 1) {
                myAdapter = new MyAdapter();
                gallery.setAdapter(myAdapter);
                new GallGalleryTask().execute();
            }
            super.handleMessage(msg);
        }

    };
    public String cid;
    public int flag;

    private void removeAllIndicator() {
        this.ll_focus_indicator_container.removeAllViews();
    }

    private void InitFocusIndicatorContainer() {

        for (int i = 0; i < list.size(); i++) {
            ImageView localImageView = new ImageView(this);
            localImageView.setId(i);
            ImageView.ScaleType localScaleType = ImageView.ScaleType.FIT_XY;
            localImageView.setScaleType(localScaleType);
            LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(
                    30, 30);
            localImageView.setLayoutParams(localLayoutParams);
            localImageView.setPadding(6, 6, 6, 6);
            localImageView.setImageResource(R.drawable.ic_focus);
            /*localImageView.setBackgroundColor(getResources().getColor(R.color.dialog_button));*/
            this.ll_focus_indicator_container.addView(localImageView);
        }
    }

    public List<Map<String, String>> getCate() {

        String url = "index.php?controller=site&action=getads";
        return list;

    }

    private static String JSONTokener(String in) {
        if (in != null && in.startsWith("\ufeff")) {
            in = in.substring(1);
        }
        return in;
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return list.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            if (convertView == null) {
                convertView = new ImageView(MallActivity.this);
                int width = getResources().getDisplayMetrics().widthPixels;
                Gallery.LayoutParams params = new Gallery.LayoutParams(width, (int) Math.ceil((width * 33) / 72));
                //params.width = sw;
			  /*  params.height=(int) Math.ceil( (sw * 28) / 72);  	*/
                convertView.setLayoutParams(params);
            }
            @SuppressWarnings("unchecked")
            HashMap<String, String> record = (HashMap<String, String>) list.get(position);


            if (Functions.isEmpty(record.get("img").toString()) || record.get("img").toString().equals("waitfor")) {
                //imageView.setBackgroundResource(R.drawable.loading_text);
            } else {
                bitmapUtils.display((ImageView) convertView, NetworkConfig.baseImgUrl + record.get("img").toString(), new CustomBitmapLoadCallBack(convertView));
            }

            return convertView;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

    }

    private void showTab(int idx, int current) {
        for (int i = 0; i < fragmentsList.size(); i++) {
            Fragment fragment = fragmentsList.get(i);
            FragmentTransaction ft = obtainFragmentTransaction(idx, current);

            if (idx == i) {
                ft.show(fragment);
            } else {
                ft.hide(fragment);
            }
            //  ft.commit();
        }
    }

    private class ImageItemHolder {
        private ImageView imgItem;

        private ProgressBar imgPb;
    }

    public class CustomBitmapLoadCallBack extends DefaultBitmapLoadCallBack<ImageView> {
        private final View holder;

        public CustomBitmapLoadCallBack(View holder) {
            this.holder = holder;
        }

        @Override
        public void onLoading(ImageView container, String uri, BitmapDisplayConfig config, long total, long current) {

        }

        @Override
        public void onLoadCompleted(ImageView container, String uri, Bitmap bitmap, BitmapDisplayConfig config, BitmapLoadFrom from) {
            //super.onLoadCompleted(container, uri, bitmap, config, from);

            fadeInDisplay(container, bitmap);
        }
    }

    private void fadeInDisplay(ImageView imageView, Bitmap bitmap) {
        final TransitionDrawable transitionDrawable =
                new TransitionDrawable(new Drawable[]{
                        TRANSPARENT_DRAWABLE,
                        new BitmapDrawable(imageView.getResources(), bitmap)
                });
        imageView.setBackgroundDrawable(transitionDrawable);
        transitionDrawable.startTransition(500);
    }


    private FragmentTransaction obtainFragmentTransaction(int index, int currentTab) {
        FragmentTransaction ft = MallActivity.this.getSupportFragmentManager().beginTransaction();
        //
        if (index > currentTab) {
            ft.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out);
        } else {
            ft.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_right_out);
        }
        return ft;
    }

    protected void tosearch(String keyword) {

    }

    @JavascriptInterface
    public void addImageClickListner() {
		/*bannerView.loadUrl("javascript:(function(){"
				+ "var objs = document.getElementsByTagName(\"img\"); "
				+ "for(var i=0;i<objs.length;i++)  " + "{"
				+ "    objs[i].onclick=function()  " + "    {  "
				+ "        window.imagelistner.getImgId(this.id);  "
				+ "    }  " + "}" + "})()");*/
    }

    public class MyJavascriptInterface {
        private Context context;

        public MyJavascriptInterface(Context context) {
            this.context = context;
        }

        @JavascriptInterface
        public void getImgId(String id) {
            Intent intent = new Intent();
            //intent.putExtra("play_id", id);
            mApplication.setTid(id);
            mApplication.setBannerId(id);
            intent.setClass(context, PlayDetailActivity.class);
            context.startActivity(intent);
        }
    }

    public class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            view.getSettings().setJavaScriptEnabled(true);

            super.onPageFinished(view, url);
            // html加载完成之后，添加监听图片的点击js函数
            addImageClickListner();

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            view.getSettings().setJavaScriptEnabled(true);
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }
    }

    protected void tocata(String cid) {

    }

    private void link(String url) {

        Intent intent = new Intent(Intent.ACTION_VIEW);

        intent.setData(Uri.parse(url));

        startActivity(intent);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private class GallGalleryTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            try {
                return mApplication.getNetApi().bannerUrl();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            try {
                JSONObject json = new JSONObject(result);
                JSONArray jsonArray = json.getJSONArray("result");
                list.clear();
                myAdapter.notifyDataSetChanged();
                for (int i = 0, lenght = jsonArray.length(); i < lenght; i++) {
                    json = jsonArray.getJSONObject(i);
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("img", json.getString("img"));
                    map.put("id", json.getString("id"));
                    map.put("tid", json.getString("tid"));
                    list.add(map);
                }
                removeAllIndicator();
                InitFocusIndicatorContainer();
                myAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        gallery.destroyDrawingCache();
        gallery = null;
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GO_LOGIN && resultCode == RESULT_OK) {
            ////mTabHost.setCurrentTab(USERCENTER_POSITION);
            Intent i = new Intent(MallActivity.this, PublicFirstActivity.class);
            startActivity(i);
        }
    }
}
