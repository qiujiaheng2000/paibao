package com.play.treasure.waterfall;

import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.bureak.common.UiUtils;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.bitmap.callback.DefaultBitmapLoadCallBack;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.play.treasure.Functions;
import com.play.treasure.PlayApplication;
import com.play.treasure.R;
import com.play.treasure.activity.PlayDetailActivity;
import com.play.treasure.adapter.BaseListAdapter;
import com.play.treasure.model.Waterfall;
import com.play.treasure.network.NetworkConfig;
import com.play.treasure.utils.PreferencesUtils;

public class WaterfallAdapter extends BaseListAdapter<Waterfall> implements
        OnItemClickListener {
    private Context mContext;
    private final LayoutInflater mLayoutInflater;
    private final Random mRandom;
    private static final SparseArray<Double> sPositionHeightRatios = new SparseArray<Double>();
    private BitmapUtils bitmapUtils;
    private WindowManager windowManager;
    private PlayApplication mApplication;
    private int sw = 0;
    private int sh = 0;
    PreferencesUtils sp;

    private int itemWidth;

    private DisplayImageOptions options;

    public WaterfallAdapter(Context context) {
        super();
        Log.e("test", "WaterfallAdapter()");
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(mContext);
        this.mRandom = new Random();
        /*this.bitmapUtils = Functions.initbimapxUtils(bitmapUtils, this.mContext);
        windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Functions.sw = windowManager.getDefaultDisplay().getWidth();
        Functions.sh = windowManager.getDefaultDisplay().getHeight();
        sw = windowManager.getDefaultDisplay().getWidth();
        sh = windowManager.getDefaultDisplay().getHeight();
        sp = new PreferencesUtils(context);

        mApplication = PlayApplication.getApplication();
        mApplication.sw = sw;
        mApplication.sh = sh;

        sp.putInt("sw", sw);
        sp.putInt("sh", sh);
        Log.e("test", "sw: " + sw + ", sh " + sh);*/

        int screenWidth = UiUtils.getScreenWidthAndSizeInPx(((Activity) context))[0];
        itemWidth = (screenWidth / 2 - 20);

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.default_icon)
                .showImageForEmptyUri(R.drawable.default_icon)
                .showImageOnFail(R.drawable.default_icon)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .displayer(new SimpleBitmapDisplayer()).build();
    }

    @Override
    public View getView(final int position, View convertView,
                        final ViewGroup parent) {
        final ViewHolder mHolder;
        if (convertView != null) {
            mHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = View.inflate(mContext, R.layout.item_grid_water_fall,
                    null);
            mHolder = new ViewHolder();
            mHolder.imgView = (ImageView) convertView
                    .findViewById(R.id.imgView);
            mHolder.playCategory = (TextView) convertView
                    .findViewById(R.id.water_fall_category);
            mHolder.auth = (ImageView) convertView.findViewById(R.id.auth);
            mHolder.playName = (TextView) convertView
                    .findViewById(R.id.water_fall_play_name);
            mHolder.playStack = (TextView) convertView
                    .findViewById(R.id.water_fall_stack);
            mHolder.playComment = (TextView) convertView
                    .findViewById(R.id.water_fall_comment);
            mHolder.playDistance = (TextView) convertView
                    .findViewById(R.id.water_fall_distance);

            convertView.setTag(mHolder);
        }
        final Waterfall mWaterfall = list.get(position);

        // 设置宽高
        ViewGroup.LayoutParams params = mHolder.imgView.getLayoutParams();
        int picHeight = itemWidth;
        int picWidth = picHeight;
        try {
            picHeight = mWaterfall.picHeight != 0 ? mWaterfall.picHeight : itemWidth;
            picWidth = mWaterfall.picWidth != 0 ? mWaterfall.picWidth : itemWidth;
        } catch (NumberFormatException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
//        Log.e("tag", "picHeight: " + picHeight + "picWidth " + picWidth);
        params.height = picHeight * itemWidth / picWidth;
        params.width = itemWidth;// 宽度为屏幕宽度-边距
        mHolder.imgView.setLayoutParams(params);
        ImageLoader.getInstance().displayImage(NetworkConfig.baseImgUrl + mWaterfall.getImgUrl(), mHolder.imgView, options);
//        Picasso.with(mContext)
//                .load(NetworkConfig.baseImgUrl + mWaterfall.getImgUrl())
//                .placeholder(R.color.white)
//                .error(R.color.white).into(mHolder.imgView);
        try {
            switch (Integer.parseInt(mWaterfall.getPlayCategory())) {
                case 1:
//                    mHolder.playCategory.setImageResource(R.drawable.tag_sale);
                    mHolder.playCategory.setBackgroundResource(R.color.sale_color);
                    mHolder.playCategory.setText(R.string.sale_text);
                    mHolder.auth
                            .setVisibility(mWaterfall.getAuth().equals("1") ? View.VISIBLE
                                    : View.GONE);
                    break;
                case 2:

//                    mHolder.playCategory.setImageResource(R.drawable.tag_zhangyan);
                    mHolder.playCategory.setBackgroundResource(R.color.zhangyan_color);
                    mHolder.playCategory.setText(R.string.zhangyan_text);

                    break;
                case 3:
//                    mHolder.playCategory.setImageResource(R.drawable.tag_play);
                    mHolder.playCategory.setBackgroundResource(R.color.bawan_color);
                    mHolder.playCategory.setText(R.string.bawan_text);
                    break;
                case 4:
//                    mHolder.playCategory.setImageResource(R.drawable.tag_share);
                    mHolder.playCategory.setBackgroundResource(R.color.share_color);
                    mHolder.playCategory.setText(R.string.share_text);
                    break;

            }
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        mHolder.playName.setText(mWaterfall.getPlayDes());
        mHolder.playComment.setText(mWaterfall.getCommentNum());
        mHolder.playStack.setText(mWaterfall.getStackNum());
        mHolder.playDistance.setText(mWaterfall.getDistance());

        if (TextUtils.isEmpty(mWaterfall.getCommentNum())) {
            mHolder.playComment.setText("0");
        } else {
            mHolder.playComment.setText(mWaterfall.getCommentNum());
        }
        if (TextUtils.isEmpty(mWaterfall.getStackNum())) {
            mHolder.playStack.setText("0");
        } else {
            mHolder.playStack.setText(mWaterfall.getStackNum());
        }
        if (mWaterfall.getDistance() == null) {
            mHolder.playDistance.setText("0km");
        } else {
            mHolder.playDistance.setText(mWaterfall.getDistance() + "km");
        }
        return convertView;
    }

    static class ViewHolder {
        ImageView imgView;
        ImageView auth;
        private TextView playCategory;
        private TextView playName;
        private TextView playStack;
        private TextView playComment;
        private TextView playDistance;
    }

    /**
     * private double getPositionRatio(final int position)
     * {
     * double ratio = sPositionHeightRatios.get(position, 0.0);
     * if (ratio == 0)
     * {
     * ratio = getRandomHeightRatio();
     * sPositionHeightRatios.append(position, ratio);
     * }
     * return ratio;
     * }
     * <p/>
     * private double getRandomHeightRatio()
     * {
     * return (mRandom.nextDouble() / 2.0) + 1.0;
     * }*
     */

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        try {
            mApplication.setBannerId(getItem(position).getPlayId());
            mApplication.setTid(getItem(position).getPlayId());

            Intent intent = new Intent();
            intent.putExtra("detail_distance", getItem(position).getDistance());
            intent.putExtra("detail_category", getItem(position).getPlayCategory());

            mApplication.setCategory(getItem(position).getPlayCategory());
            mApplication.setDistance(getItem(position).getDistance());

            intent.setClass(mContext, PlayDetailActivity.class);
            mContext.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            //   bitmap = PictureUtils.imageZoom(bitmap,30);
            Functions.fadeInDisplay(container, bitmap, sp.getInt("sw", 0));
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