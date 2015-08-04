package com.bureak.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.play.treasure.PlayApplication;
import com.play.treasure.R;
import com.play.treasure.activity.PlayDetailActivity;
import com.play.treasure.model.Waterfall;
import com.play.treasure.network.NetworkConfig;

import java.util.ArrayList;

public class ImageListAdapter extends IncreaseAdatper<Waterfall> implements
        OnItemClickListener {
    private Context mContext;
    private int itemWidth;
    private PlayApplication mApplication;
    private DisplayImageOptions options;

    public ImageListAdapter(Context activity,
                            ArrayList<Waterfall> mImageList) {
        super(mImageList);
        this.mApplication = PlayApplication.getApplication();
        this.mContext = activity;
        int screenWidth = UiUtils.getScreenWidthAndSizeInPx((Activity) activity)[0];
        itemWidth = (screenWidth / 2 - 20);
        options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.default_icon)
                .showImageOnLoading(R.drawable.default_icon)
                .showImageOnFail(R.drawable.default_icon)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .displayer(new FadeInBitmapDisplayer(200))
                .build();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
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
        final Waterfall mWaterfall = array.get(position);
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
//		Log.e("tag", "picHeight: "+picHeight +", picWidth "+picWidth);
        params.height = picHeight * itemWidth / picWidth;
        params.width = itemWidth;// 宽度为屏幕宽度-边距
        mHolder.imgView.setLayoutParams(params);
        ImageLoader.getInstance().displayImage(NetworkConfig.baseImgUrl + mWaterfall.getImgUrl(), mHolder.imgView, options);
        try {
            switch (Integer.parseInt(mWaterfall.getPlayCategory())) {
                case 1:
                    mHolder.playCategory.setBackgroundResource(R.color.sale_color);
                    mHolder.playCategory.setText(R.string.sale_text);
                    mHolder.auth
                            .setVisibility(mWaterfall.getAuth().equals("1") ? View.VISIBLE
                                    : View.GONE);
                    break;
                case 2:
                    mHolder.playCategory.setBackgroundResource(R.color.zhangyan_color);
                    mHolder.playCategory.setText(R.string.zhangyan_text);
                    break;
                case 3:
                    mHolder.playCategory.setBackgroundResource(R.color.bawan_color);
                    mHolder.playCategory.setText(R.string.bawan_text);
                    break;
                case 4:
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
}
