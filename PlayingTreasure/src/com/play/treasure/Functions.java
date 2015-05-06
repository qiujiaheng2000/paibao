package com.play.treasure;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.LayoutParams;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ImageView.ScaleType;

import com.lidroid.xutils.BitmapUtils;
import com.play.treasure.R;
import com.play.treasure.activity.PlayDetailActivity;
import com.play.treasure.activity.PlayDetailMineActivity;
import com.play.treasure.network.NetworkConfig;
import com.play.treasure.utils.PictureUtils;

public class Functions {
    public static Dialog mDialog;

    public static void showRoundProcessDialog(Context mContext) {
        mDialog = new AlertDialog.Builder(mContext).create();
        mDialog.show();
        //   int layout = R.layout.loading_process_dialog_anim;
        //    mDialog.setContentView(layout);
        mDialog.setCancelable(true);
    }

    public static void hideProcessDialog() {

        mDialog.cancel();


    }

    public static void fadeInDisplay(ImageView imageView, Bitmap bitmap, int sw) {
        {
            // bitmap = PictureUtils.imageZoom(bitmap,30);
            Functions.setBsetBitmap(bitmap, imageView, sw);
              /*   final TransitionDrawable transitionDrawable =
    	               new TransitionDrawable(new Drawable[]{
    	                       TRANSPARENT_DRAWABLE,
    	                       new BitmapDrawable(imageView.getResources(), bitmap)
    	               });
    	     imageView.setImageDrawable(transitionDrawable);
    	       transitionDrawable.startTransition(500);*/
        }
    }

    public static void fadeInDisplayA(ImageView imageView, Context ctx, Bitmap bitmap, int sw) {
        {

            //bitmap = PictureUtils.imageZoom(bitmap,100);
            Functions.setAsetBitmap(bitmap, ctx, imageView, sw);
    	      /*   final TransitionDrawable transitionDrawable =
    	               new TransitionDrawable(new Drawable[]{
    	                       TRANSPARENT_DRAWABLE,
    	                       new BitmapDrawable(imageView.getResources(), bitmap)
    	               });
    	     imageView.setImageDrawable(transitionDrawable);
    	       transitionDrawable.startTransition(500);*/
        }
    }

    public static int sw, sh;
    public static final ColorDrawable TRANSPARENT_DRAWABLE = new ColorDrawable(android.R.color.transparent);

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

    /**
     * 在固定大小的ImageView中，保证图片不变形
     *
     * @param bitmap
     * @param imageView
     * @param width
     * @param height
     */
    public static void setAsetBitmap(Bitmap bitmap, Context ctx, ImageView imageView, int sw) {
        //计算最佳缩放倍数
//           float scaleX = (float) width / bitmap.getWidth();
//           float scaleY = (float) height / bitmap.getHeight();
//           float bestScale = scaleX < scaleY ? scaleX : scaleY;
        //计算最佳缩放倍数,以填充宽高为目标
        int width, height;
        width = (sw);
        height = bitmap.getHeight() * width / bitmap.getWidth();
        float scaleX = (float) width / bitmap.getWidth();
        float scaleY = (float) height / bitmap.getHeight();
        float bestScale = scaleX > scaleY ? scaleX : scaleY;
        //以填充高度的前提下，计算最佳缩放倍数
//           float bestScale = (float) height / bitmap.getHeight();
        LayoutParams lp = new LayoutParams();
        lp.width = width;
        lp.height = height;
        ///   imageView.setMaxHeight(height);
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
        //   Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, width-1,height-1, imgMatrix, true);
        imageView.setImageBitmap(bitmap);
        if (getActivity(ctx).equals("com.play.treasure.activity.PlayDetailActivity")) {
            Log.i("treasure", "PlayDetailActivity");
            Message msg = PlayDetailActivity.mHanlder.obtainMessage();
            msg.what = 1;
            msg.arg1 = height;
            msg.sendToTarget();

        }
        if (getActivity(ctx).equals("com.play.treasure.activity.PlayDetailMineActivity")) {
            Log.i("treasure", "PlayDetailMineActivity" + height);
            Message msg = PlayDetailMineActivity.mHanlder.obtainMessage();
            msg.what = 1;
            msg.arg1 = height;
            msg.sendToTarget();
        }

    }

    /**
     * 在固定大小的ImageView中，保证图片不变形
     *
     * @param bitmap
     * @param imageView
     * @param width
     * @param height
     */
    public static void setBsetBitmap(Bitmap bitmap, ImageView imageView, int sw) {
        //计算最佳缩放倍数,以填充宽高为目标
        int width, height;
        width = (sw - 10) / 2;
        height = bitmap.getHeight() * width / bitmap.getWidth();
        float scaleX = (float) width / bitmap.getWidth();
        float scaleY = (float) height / bitmap.getHeight();
        float bestScale = scaleX > scaleY ? scaleX : scaleY;
        //以填充高度的前提下，计算最佳缩放倍数
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width, height);
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

    public static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static String getString(Context ctx, String flag, String key) {
        SharedPreferences share = ctx.getSharedPreferences(flag, Context.MODE_PRIVATE);
        String uc = share.getString(key, "").toString();
        Log.i("get", "uc��" + uc);
        return uc;
    }

    public static void saveString(Context ctx, String flag, String key, String value) {
        SharedPreferences share = ctx.getSharedPreferences(flag, Context.MODE_PRIVATE);
        share.edit().putString(key, value + "").commit();
        Log.i("save", "uc��" + value + getString(ctx, key, value));
    }

    public static boolean getBoolean(Context ctx, String flag, String key) {
        SharedPreferences share = ctx.getSharedPreferences(flag, Context.MODE_PRIVATE);
        boolean uc = share.getBoolean(key, true);
        Log.i("get", "uc��" + uc);
        return uc;
    }

    public static void saveBoolean(Context ctx, String flag, String key, boolean value) {
        SharedPreferences share = ctx.getSharedPreferences(flag, Context.MODE_PRIVATE);
        share.edit().putBoolean(key, value).commit();
        //share.edit().commit();
        Log.i("save", "uc��" + value + getBoolean(ctx, flag, key));
    }

    public static int getInt(Context ctx, String flag, String key) {
        SharedPreferences share = ctx.getSharedPreferences(flag, Context.MODE_PRIVATE);
        int uc = share.getInt(key, 50);
        Log.i("get", "uc��" + uc);
        return uc;
    }

    public static void saveInt(Context ctx, String flag, String key, int value) {
        SharedPreferences share = ctx.getSharedPreferences(flag, Context.MODE_PRIVATE);
        share.edit().putInt(key, value).commit();
        //share.edit().commit();
        Log.i("save", "uc��" + value + getInt(ctx, flag, key));
    }

    public static String getNumbers(String content) {
        if (isEmpty(content)) {
            return "";
        } else {
            Pattern pattern = Pattern.compile("\\d+");
            Matcher matcher = pattern.matcher(content);
            while (matcher.find()) {
                return matcher.group(0);
            }
            return "";
        }
    }

    public static void Logit(String content) {
        Log.i("T++" + content, "" + content);
    }

    public static boolean isEmpty(String str) {
        if (str == null || str.equals("null") || str.length() == 0) {
            Functions.Logit(str);
            return true;
        }
        return false;
    }

    public static BitmapUtils initbimapxUtils(BitmapUtils bitmapUtils, Context ctx) {
        bitmapUtils = BitmapHelp.getBitmapUtils(ctx.getApplicationContext(), NetworkConfig.ALBUM_PATH).configThreadPoolSize(7);
        if (!(ctx instanceof MallActivity)) {
            bitmapUtils.configDefaultLoadingImage(R.drawable.loading_text);
            bitmapUtils.configDefaultLoadFailedImage(R.drawable.loading_text);
        }

        bitmapUtils.configDefaultBitmapMaxSize(400, 400);
        //   bitmapUtils.conF
        bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.ALPHA_8);
        bitmapUtils.configDiskCacheEnabled(true);
        bitmapUtils.configMemoryCacheEnabled(true);
        return bitmapUtils;
    }

    public static boolean isAvilible(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        List<String> pName = new ArrayList<String>();
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                pName.add(pn);
            }
        }
        return pName.contains(packageName);
    }

    private static String getActivity(Context ctx) {
        final ActivityManager am;
        am = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> taskinfos = am.getRunningTasks(1);
        //获得当前的任务信息
        RunningTaskInfo currenttask = taskinfos.get(0);
        Log.i("ctgn", "" + currenttask.topActivity.getClassName());
        return currenttask.topActivity.getClassName();
        //02-10 18:41:30.449: I/ctgn(21379): com.play.treasure.activity.PlayDetailActivity

    }
}
