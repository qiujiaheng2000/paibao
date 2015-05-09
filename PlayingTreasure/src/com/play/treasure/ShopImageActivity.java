package com.play.treasure;

import java.io.InputStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.JsonObject;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.bitmap.callback.DefaultBitmapLoadCallBack;
import com.play.treasure.activity.PlayDetailActivity;
import com.play.treasure.activity.PlayDetailMineActivity;
import com.play.treasure.network.NetworkConfig;
import com.play.treasure.network.model.NetworkBeanArray;
import com.play.treasure.utils.CommonProgressDialog;
import com.play.treasure.utils.PictureUtils;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.ViewPager.LayoutParams;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

public class ShopImageActivity extends Activity {
    private ArrayList<String> imageUrlStrings;
    private ImageAdapter adapter;
    private BitmapUtils bitmapUtils;
    private CommonProgressDialog progressDialog;
    private PlayApplication mApplication;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_image);
        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");
        ((TextView) findViewById(R.id.title_bar_center)).setText(intent.getStringExtra("title"));
        mApplication = PlayApplication.getApplication();
        ListView listView = (ListView) findViewById(R.id.image_list);
        this.bitmapUtils = Functions.initbimapxUtils(bitmapUtils, this);
        imageUrlStrings = new ArrayList<String>();
        adapter = new ImageAdapter();
        listView.setAdapter(adapter);
        progressDialog = new CommonProgressDialog(this, R.style.dialog);
        progressDialog.setMsg("加载中...");
        new ShopImageTask().execute();
    }

    public void back(View v) {
        finish();
    }

    class ImageAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return imageUrlStrings.size();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return arg0;
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return arg0;
        }

        @Override
        public View getView(int arg0, View arg1, ViewGroup arg2) {
            if (arg1 == null) {
                ImageView view = new ImageView(ShopImageActivity.this);
                DisplayMetrics metrics = getResources().getDisplayMetrics();
                view.setImageBitmap(readBitMap(ShopImageActivity.this, R.drawable.empty_photo, metrics.widthPixels, metrics.heightPixels / 3));
                view.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, metrics.heightPixels / 3));
                arg1 = view;
                arg1.setOnTouchListener(null);
            }
            ImageView imageView = (ImageView) arg1;
            bitmapUtils.display(imageView, NetworkConfig.baseImgUrl + imageUrlStrings.get(arg0),
                    new CustomBitmapLoadCallBack(imageView));
            return arg1;
        }
    }

    class CustomBitmapLoadCallBack extends DefaultBitmapLoadCallBack<ImageView> {
        private final View holder;

        public CustomBitmapLoadCallBack(View holder) {
            this.holder = holder;
        }

        @Override
        public void onLoading(ImageView container, String uri, BitmapDisplayConfig config, long total, long current) {

        }

        @Override
        public void onLoadCompleted(final ImageView container, String uri, final Bitmap bitmap, BitmapDisplayConfig config, BitmapLoadFrom from) {
            //super.onLoadCompleted(container, uri, bitmap, config, from);
            //   bitmap = PictureUtils.imageZoom(bitmap,30);

            Bitmap bitmap1 = PictureUtils.imageZoom(bitmap, 100);
            setAsetBitmap(bitmap1, ShopImageActivity.this, container, Functions.sw);
            // container.setImageBitmap(bitmap);
        }
    }

    /**
     * 以最省内存的方式读取本地资源的图片
     *
     * @param context
     * @param resId
     * @return
     */
    public Bitmap readBitMap(Context context, int resId, int width, int height) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        //获取资源图片
        InputStream is = context.getResources().openRawResource(resId);

        Bitmap bitmap = BitmapFactory.decodeStream(is, null, opt);
        Matrix matrix = new Matrix();
        int bWidth = bitmap.getWidth();
        int bHeight = bitmap.getHeight();
        matrix.setScale(width * 1.0f / bWidth, height * 1.0f / bHeight);
        Bitmap temp = Bitmap.createBitmap(bitmap, 0, 0, bWidth, bHeight, matrix, true);
        bitmap.recycle();
        return temp;
    }

    /**
     * 在固定大小的ImageView中，保证图片不变形
     *
     * @param bitmap
     * @param imageView
     * @param width
     * @param height
     */
    public void setAsetBitmap(Bitmap bitmap, Context ctx, ImageView imageView, int sw) {
        //计算最佳缩放倍数
//	           float scaleX = (float) width / bitmap.getWidth();
//	           float scaleY = (float) height / bitmap.getHeight();
//	           float bestScale = scaleX < scaleY ? scaleX : scaleY;
        //计算最佳缩放倍数,以填充宽高为目标
        int width, height;
        width = (sw);
        height = bitmap.getHeight() * width / bitmap.getWidth();
        float scaleX = (float) width / bitmap.getWidth();
        float scaleY = (float) height / bitmap.getHeight();
        float bestScale = scaleX > scaleY ? scaleX : scaleY;
        //以填充高度的前提下，计算最佳缩放倍数
//	           float bestScale = (float) height / bitmap.getHeight();
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(width, height);
        imageView.setLayoutParams(lp);

        float subX = (width - bitmap.getWidth() * bestScale) / 2;
        float subY = (height - bitmap.getHeight() * bestScale) / 2;

        Matrix imgMatrix = new Matrix();
        imageView.setScaleType(ScaleType.MATRIX);
        //缩放最佳大小
        imgMatrix.postScale(bestScale, bestScale);
        //移动到居中位置显示
        imgMatrix.postTranslate(subX, subY);
        //设置矩阵
        imageView.setImageMatrix(imgMatrix);
        imageView.setImageBitmap(bitmap);
    }

    public class ShopImageTask extends AsyncTask<Void, Void, NetworkBeanArray> {
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
                return mApplication.getNetApi().shopUserImage(uid);
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
                    for (int i = 0, length = jsonArray.length(); i < length; i++) {
                        imageUrlStrings.add(jsonArray.getJSONObject(i).getString("img"));
                    }
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
