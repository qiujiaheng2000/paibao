package com.play.treasure.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.bitmap.callback.DefaultBitmapLoadCallBack;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.play.treasure.Functions;
import com.play.treasure.PlayApplication;
import com.play.treasure.R;
import com.play.treasure.activity.PlayDetailActivity;
import com.play.treasure.model.Banner;
import com.play.treasure.network.NetworkConfig;
import com.play.treasure.network.model.NetworkBeanArray;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author wangchao29
 * @ClassName: SlideShowView
 * @Description: 详情页banner
 * @date 2014年12月4日 下午11:07:54
 */
public class SlideShowView extends LinearLayout {

    //
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private final static boolean isAutoPlay = true;

    private List<String> imageUrls;

    private List<ImageView> imageViewsList;

    private List<View> dotViewsList;

    private ViewPager viewPager;

    private int currentItem = 0;

    private ScheduledExecutorService scheduledExecutorService;

    private Context context;

    private PlayApplication mApplication;

    private BitmapUtils bitmapUtils;
    private DisplayImageOptions options;

    public Handler getHandlerParentView() {
        return mHandlerParentView;
    }

    public void setHandlerParentView(Handler handlerParentView) {
        mHandlerParentView = handlerParentView;
    }

    private Handler mHandlerParentView;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            viewPager.setCurrentItem(currentItem);
        }
    };

    public SlideShowView(Context context) {
        this(context, null);
    }

    public SlideShowView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    @SuppressLint("NewApi")
    public SlideShowView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        // initImageLoader(context);
        bitmapUtils = Functions.initbimapxUtils(bitmapUtils, context);
        options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.default_icon)
                .showImageOnLoading(R.drawable.default_icon)
                .showImageOnFail(R.drawable.default_icon)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .displayer(new FadeInBitmapDisplayer(200))
                .build();
        initData();
        if (isAutoPlay) {
            startPlay();
        }
    }

    private void startPlay() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new SlideShowTask(), 1, 4,
                TimeUnit.SECONDS);
    }

    private void initData() {
        imageViewsList = new ArrayList<ImageView>();
        dotViewsList = new ArrayList<View>();
        imageUrls = new ArrayList<String>();

        new GetListTask().execute();
    }

    private void initUI(Context context) {
        if (imageUrls == null || imageUrls.size() == 0)
            return;

        View v = LayoutInflater.from(context).inflate(
                R.layout.layout_slideshow, null);
        addView(v);
        int size = imageUrls.size();
        if (size > 1) {
            // v.setLayoutParams(new
            // LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            // context.getResources().getDisplayMetrics().heightPixels * 2 /
            // 5));
        }
        LinearLayout dotLayout = (LinearLayout) findViewById(R.id.dotLayout);
        dotLayout.removeAllViews();

        for (int i = 0; i < size; i++) {
            ImageView view = new ImageView(context);
            view.setTag(imageUrls.get(i));
            view.setScaleType(ScaleType.FIT_XY);
            view.measure(MeasureSpec.AT_MOST, MeasureSpec.AT_MOST);
            imageViewsList.add(view);

            ImageView dotView = new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.leftMargin = 4;
            params.rightMargin = 4;
            dotLayout.addView(dotView, params);
            dotViewsList.add(dotView);
        }

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setFocusable(true);

        viewPager.setAdapter(new MyPagerAdapter());
        viewPager.setOnPageChangeListener(new MyPageChangeListener());
    }

    private class MyPagerAdapter extends PagerAdapter {
        @Override
        public void destroyItem(View container, int position, Object object) {
            ((ViewPager) container).removeView(imageViewsList.get(position));
        }

        @Override
        public Object instantiateItem(View container, int position) {
            final ImageView imageView = imageViewsList.get(position);
            // Picasso.with(context)
            // .load(imageView.getTag().toString())
            // .placeholder(R.color.white)
            // .error(R.color.white).into(imageView);
//			bitmapUtils.display(imageView, imageView.getTag().toString(),
//					new CustomBitmapLoadCallBack(imageView));
            ImageLoader.getInstance().displayImage(imageView.getTag().toString(), imageView, options, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap bitmap) {
                    ImageView imageView1 = (ImageView) view;
                    int width, height;
                    width = (Functions.sw);
                    height = bitmap.getHeight() * width / bitmap.getWidth();
                    float scaleX = (float) width / bitmap.getWidth();
                    float scaleY = (float) height / bitmap.getHeight();
                    float bestScale = scaleX > scaleY ? scaleX : scaleY;
                    //以填充高度的前提下，计算最佳缩放倍数
                    ViewPager.LayoutParams lp = new ViewPager.LayoutParams();
                    lp.width = width;
                    lp.height = height;
                    imageView1.setLayoutParams(lp);

                    float subX = (width - bitmap.getWidth() * bestScale) / 2;
                    float subY = (height - bitmap.getHeight() * bestScale) / 2;

                    Matrix imgMatrix = new Matrix();
                    imageView1.setScaleType(ScaleType.MATRIX);
                    //缩放最佳大小
                    imgMatrix.postScale(bestScale, bestScale);
                    //移动到居中位置显示
                    imgMatrix.postTranslate(subX, subY);
                    //设置矩阵
                    imageView1.setImageMatrix(imgMatrix);
                    FadeInBitmapDisplayer.animate(imageView1, 300);
                    Message msg = mHandlerParentView.obtainMessage();
                    msg.what = 1;
                    msg.arg1 = height;
                    msg.sendToTarget();
                }
            });
            ((ViewPager) container).addView(imageView);
            return imageView;
        }

        @Override
        public int getCount() {
            return imageViewsList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {

        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {

        }

        @Override
        public void finishUpdate(View arg0) {
        }

    }

    private class MyPageChangeListener implements OnPageChangeListener {
        boolean isAutoPlay = false;

        @Override
        public void onPageScrollStateChanged(int arg0) {
            switch (arg0) {
                case 1:
                    isAutoPlay = false;
                    break;
                case 2:
                    isAutoPlay = true;
                    break;
                case 0:
                    if (viewPager.getCurrentItem() == viewPager.getAdapter()
                            .getCount() - 1 && !isAutoPlay) {
                        viewPager.setCurrentItem(0);
                    } else if (viewPager.getCurrentItem() == 0 && !isAutoPlay) {
                        viewPager
                                .setCurrentItem(viewPager.getAdapter().getCount() - 1);
                    }
                    break;
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int pos) {
            currentItem = pos;
            for (int i = 0; i < dotViewsList.size(); i++) {
                if (i == pos) {
                    dotViewsList.get(pos)
                            .setBackgroundResource(R.drawable.dot_focus);
                } else {
                    dotViewsList.get(i)
                            .setBackgroundResource(R.drawable.dot_blur);
                }
            }
        }

    }

    private class SlideShowTask implements Runnable {
        @Override
        public void run() {
            synchronized (viewPager) {
                currentItem = (currentItem + 1) % imageViewsList.size();
                handler.obtainMessage().sendToTarget();
            }
        }
    }

    private class GetListTask extends AsyncTask<Void, Void, NetworkBeanArray> {

        /**
         * (非 Javadoc) Title: doInBackground Description:
         *
         * @param params
         * @return
         * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
         */
        @Override
        protected NetworkBeanArray doInBackground(Void... params) {
            mApplication = PlayApplication.getApplication();
            try {
                return mApplication.getNetApi().detailImg(
                        mApplication.getBannerId());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(NetworkBeanArray result) {
            super.onPostExecute(result);
            if (result != null) {
                try {
                    if (result.getResult() == null) {
                        return;
                    }
                    System.out.println(result.getResult());
                    JSONArray json = new JSONArray(result.getResult());
                    Banner banner = null;
                    List<Banner> bannerList = new ArrayList<Banner>();
                    List<String> urlList = new ArrayList<String>();
                    for (int i = 0; i < json.length(); i++) {
                        banner = new Banner(json.getJSONObject(i));
                        bannerList.add(banner);
                        urlList.add(NetworkConfig.baseImgUrl
                                + bannerList.get(i).getImgUrl());
                    }
                    imageUrls.addAll(urlList);
                    mApplication.setImageUrls(imageUrls);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                initUI(context);
            }
        }

    }

    public class CustomBitmapLoadCallBack extends
            DefaultBitmapLoadCallBack<ImageView> {
        private final View holder;

        public CustomBitmapLoadCallBack(View holder) {
            this.holder = holder;
        }

        @Override
        public void onLoading(ImageView container, String uri,
                              BitmapDisplayConfig config, long total, long current) {

        }

        @Override
        public void onLoadCompleted(ImageView container, String uri,
                                    Bitmap bitmap, BitmapDisplayConfig config, BitmapLoadFrom from) {
            // super.onLoadCompleted(container, uri, bitmap, config, from);
            Log.e("tag", "SilideShowView  sw: " + Functions.sw + ", sh " + Functions.sh);
            Functions.fadeInDisplayA(container, context, bitmap, Functions.sw);
        }
    }

    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }
}