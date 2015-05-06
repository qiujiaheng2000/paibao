package com.play.treasure.service;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Environment;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;

import com.play.treasure.PlayApplication;
import com.play.treasure.R;
import com.play.treasure.model.SoftwareVersion;
import com.play.treasure.network.model.NetworkBean;
import com.play.treasure.utils.CommonDialog;
import com.play.treasure.utils.FunctionUtil;
import com.play.treasure.utils.LogUtil;
import com.play.treasure.utils.ToastUtil;

/** 
* @ClassName: UpdateService
* @Description: 版本更新
* @author wangchao29
* @date 2014年12月7日 上午12:11:14
* 
*/ 
public class UpdateService extends Service
{
    /** 新版本的名称 */
    private static final String APK_NAME = "paibao.apk";
    /** 全局变量类 */
    private PlayApplication mApplication;
    private UpdateService mContext;
    /** 控制是否给用户展示提示性信息 */
    private boolean isShowToast;
    private SoftwareVersion mApkVersion;
    /** Notification. */
    private NotificationManager mUpdateNotificationManager;
    /** The m update notification. */
    private Notification mUpdateNotification;
    /** The m update total size. */
    private int mUpdateTotalSize = 0;
    /** The m pre cur length. */
    private int mPreCurLength;
    private VersionTask mVersionTask;
    private DownLoadTask mDownLoadTask;

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        mApplication = PlayApplication.getApplication();
        mContext = this;
        mUpdateNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
    	if(intent == null){
    		return super.onStartCommand(intent, flags, startId);
    	}
        isShowToast = intent.getBooleanExtra("show", false);
        if (mVersionTask != null && mVersionTask.getStatus() != Status.FINISHED)
        {
            ToastUtil.showMessage(R.string.version_request_wait_toast_message);
        }
        if (mDownLoadTask != null
                && mDownLoadTask.getStatus() != Status.FINISHED)
        {
            // toast loading
            ToastUtil.showMessage(R.string.apk_download_dialog_message);
        }
        mVersionTask = new VersionTask();
        mVersionTask.execute();
        return super.onStartCommand(intent, flags, startId);
    }

    /** 
    * @ClassName: VersionTask
    * @Description: 版本检查线程
    * @author wangchao29
    * @date 2014年12月7日 上午12:10:37
    * 
    */ 
    private class VersionTask extends AsyncTask<Void, Void, NetworkBean>
    {

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            if (isShowToast)
            {
                ToastUtil.showMessage(R.string.version_request_wait_toast_message);
            }
        }

        @Override
        protected NetworkBean doInBackground(Void... params)
        {
            try
            {
                return mApplication.getNetApi().getVersion();
            }
            catch (Exception e)
            {
                LogUtil.logException(e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(NetworkBean result)
        {
            super.onPostExecute(result);
            if (result != null && result.getData() != null)
            {
                try 
                {
					mApkVersion = new SoftwareVersion(result.getData());
					String versionCode = mApkVersion.getVersionCode();
					int newVersionCode = -1;
					if (!TextUtils.isEmpty(versionCode))
					{
					    try
					    {
					        newVersionCode = Integer.parseInt(versionCode);
					    }
					    catch (Exception e)
					    {
					        newVersionCode = -1;
					    }

					}

					Log.d("update", String.valueOf(newVersionCode));
					Log.d("update", String.valueOf(mApplication.getVersionCode()));
					
					if (newVersionCode > mApplication.getVersionCode())
					{
					    diaplayDialog();
					}
					else
					{
					    if (isShowToast)
					    {
					        ToastUtil
					                .showMessage(R.string.no_need_update_toast_message);
					    }
					    stopSelf();
					}
				} 
                catch (JSONException e) 
                {
					e.printStackTrace();
				}
            }
            else
            {
                ToastUtil.showMessage(R.string.networkerror);
                stopSelf();
            }
        }

    }

    /** 
    * @Title: diaplayDialog
    * @Description: 展示弹出
    * @param 
    * @return void
    * @throws 
    */ 
    private void diaplayDialog()
    {
        String updateContent = mApkVersion.getUpdateContent();
        if (TextUtils.isEmpty(updateContent))
        {
            updateContent = mApplication.getResources().getString(
                    R.string.version_update_dialog_message);
        }
        else
        {
            updateContent = updateContent.replace("#@#", "\r\n");
        }
        final CommonDialog alertDialog = new CommonDialog.Builder(this)
                .setTitle("发现新版本").setContent(updateContent).build();

        if (mApkVersion.getIsForceUpdate().equals("1"))
        {
            alertDialog.setCancelable(false);
        }
        // set dialog all area display
        alertDialog.getWindow().setType(
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        alertDialog
                .setOnButtonClickListener(new CommonDialog.OnButtonClickListener()
                {
                    @Override
                    public void onButtonClick(Button confirm, Button cancel)
                    {
                        cancel.setOnClickListener(new OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                alertDialog.dismiss();
                                stopSelf();
                            }
                        });
                        confirm.setOnClickListener(new OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                alertDialog.dismiss();
                                // new DownLoadTask().execute();
                                if (mDownLoadTask != null
                                        && mDownLoadTask.getStatus() != Status.FINISHED)
                                {
                                    ToastUtil
                                            .showMessage(R.string.apk_download_dialog_message);
                                }
                                else
                                {
                                    File existFile = existfile(
                                            mApplication,
                                            FunctionUtil
                                                    .parseIntByString(mApkVersion
                                                            .getVersionCode()));
                                    if (null != existFile)
                                    {
                                        install(mContext);
                                        stopSelf();
                                    }
                                    else
                                    {
                                        mDownLoadTask = new DownLoadTask();
                                        mDownLoadTask.execute();
                                    }
                                }
                            }
                        });
                    }
                });

        alertDialog.setOnDismissListener(new OnDismissListener()
        {

            @Override
            public void onDismiss(DialogInterface dialog)
            {
                if (mApkVersion.getIsForceUpdate().equals("1"))
                {
                    mApplication.exit();
                }
            }
        });
        alertDialog.show();

    }

    /** 
    * @ClassName: DownLoadTask
    * @Description: 下载线程
    * @author wangchao29
    * @date 2014年12月7日 上午12:08:59
    * 
    */ 
    private class DownLoadTask extends AsyncTask<Void, Integer, Boolean>
    {

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            mUpdateNotification = new Notification();
            mUpdateNotification.flags |= Notification.FLAG_AUTO_CANCEL;
            // 设置通知栏显示内容
            mUpdateNotification.icon = R.drawable.icon_app;
            mUpdateNotification.tickerText = "开始下载";
            PendingIntent pendingintent = PendingIntent.getActivity(mContext,
                    0, new Intent(), PendingIntent.FLAG_CANCEL_CURRENT);
            mUpdateNotification.setLatestEventInfo(UpdateService.this, "拍宝",
                    "0%", pendingintent);
            // 发出通知
            mUpdateNotificationManager.cancelAll();
            mUpdateNotificationManager.notify(0, mUpdateNotification);
        }

        @Override
        protected void onProgressUpdate(Integer... values)
        {
            super.onProgressUpdate(values);
            mUpdateTotalSize = values[0];
            mUpdateNotification.setLatestEventInfo(mContext, "拍宝",
                    "正在下载     " + mUpdateTotalSize + "%", null);
            mUpdateNotificationManager.notify(0, mUpdateNotification);
        }

        @Override
        protected Boolean doInBackground(Void... params)
        {
            try
            {
                downLoadUrl(mApkVersion.getDownloadUrl());
            }
            catch (IOException e)
            {
                LogUtil.logException(e);
                // Download failed
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result)
        {
            super.onPostExecute(result);
            if (result)
            {
                // 点击安装PendingIntent
                boolean isHaveSDCard = Environment.getExternalStorageState()
                        .equals(Environment.MEDIA_MOUNTED);
                if (!isHaveSDCard)
                {
                    String[] args = { "chmod", "604",
                            getFile().getAbsolutePath() };
                    exec(args);
                }
                File file = getFile();
                Uri uri = Uri.fromFile(file);
                Intent installIntent = new Intent(Intent.ACTION_VIEW);
                installIntent.setDataAndType(uri,
                        "application/vnd.android.package-archive");
                PendingIntent mUpdatePendingIntent = PendingIntent.getActivity(
                        mContext, 0, installIntent, 0);

                mUpdateNotification.setLatestEventInfo(mContext, "拍宝",
                        "下载完成,点击安装。", mUpdatePendingIntent);
                mUpdateNotificationManager.notify(0, mUpdateNotification);

                install(mContext);
            }
            else
            {
                ToastUtil
                        .showMessage(R.string.apk_download_failure_toast_message);
            }
            stopSelf();
        }

        /** 
        * @Title: downLoadUrl
        * @Description: 下载地址
        * @param @param downLoadUrl
        * @param @throws ClientProtocolException
        * @param @throws IOException
        * @return void
        * @throws 
        */ 
        private void downLoadUrl(String downLoadUrl)
                throws ClientProtocolException, IOException
        {

            FileOutputStream fileOutputStream = null;
            BufferedInputStream bis = null;

            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(downLoadUrl);
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            InputStream in = entity.getContent();
            long totalLength = entity.getContentLength();
            File file = null;
            if (null != in)
            {
                file = getFile();
                fileOutputStream = new FileOutputStream(file);
                bis = new BufferedInputStream(in);
                byte[] buf = new byte[1024];
                int ch = -1;
                float total = 0;
                while ((ch = bis.read(buf)) != -1)
                {
                    fileOutputStream.write(buf, 0, ch);
                    total += ch;
                    int curLength = (int) ((total / totalLength) * 100 % 100);
                    if ((long) total == totalLength)
                    {
                        curLength = 100;
                    }
                    if (mPreCurLength != curLength)
                    {
                        // send progress
                        publishProgress(curLength);
                        mPreCurLength = curLength;
                    }
                }
            }

            if (fileOutputStream != null)
            {
                fileOutputStream.flush();
                fileOutputStream.close();
            }

            if (bis != null)
            {
                bis.close();
            }

        }

    }

    /** 
    * @Title: install
    * @Description: 安装应用
    * @param @param context
    * @return void
    * @throws 
    */ 
    private void install(Context context)
    {
        String[] args = { "chmod", "604", getFile().getAbsolutePath() };
        exec(args);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(getFile()),
                "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        if (mApkVersion.getIsForceUpdate().equals("1"))
        {
            mApplication.exit();
        }
    }

    /** 
    * @Title: exec
    * @Description: 执行权限
    * @param @param args
    * @param @return
    * @return String
    * @throws 
    */ 
    private static String exec(String[] args)
    {
        String result = "";
        ProcessBuilder processBuilder = new ProcessBuilder(args);
        Process process = null;
        InputStream errIs = null;
        InputStream inIs = null;
        try
        {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int read = -1;
            process = processBuilder.start();
            errIs = process.getErrorStream();
            while ((read = errIs.read()) != -1)
            {
                baos.write(read);
            }
            baos.write('\n');
            inIs = process.getInputStream();
            while ((read = inIs.read()) != -1)
            {
                baos.write(read);
            }
            byte[] data = baos.toByteArray();
            result = new String(data);
        }
        catch (IOException e)
        {
            LogUtil.logException(e);
        }
        catch (Exception e)
        {
            LogUtil.logException(e);
        }
        finally
        {
            try
            {
                if (errIs != null)
                {
                    errIs.close();
                }
                if (inIs != null)
                {
                    inIs.close();
                }
            }
            catch (IOException e)
            {
                LogUtil.logException(e);
            }
            if (process != null)
            {
                process.destroy();
            }
        }
        LogUtil.e(result);
        return result;
    }

    /** 
    * @Title: getFile
    * @Description: 获取文件路径
    * @param @return
    * @return File
    * @throws 
    */ 
    private File getFile()
    {
        File file = null;
        boolean isHaveSDCard = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
        if (isHaveSDCard)
        {
            file = new File(Environment.getExternalStorageDirectory() + "/"
                    + APK_NAME);
        }
        else
        {
            file = new File(mApplication.getFilesDir() + "/" + APK_NAME);
        }
        return file;
    }

    /** 
    * @Title: existfile
    * @Description:判断是否已经下载
    * @param @param ctx
    * @param @param newVerCode
    * @param @return
    * @return File
    * @throws 
    */ 
    private File existfile(Context ctx, int newVerCode)
    {
        try
        {
            String filePath = getFile().getAbsolutePath();
            int existVerCode = getApkVersionCode(ctx, filePath);
            if (existVerCode == newVerCode)
            {
                return new File(filePath);
            }
        }
        catch (Exception e)
        {
            LogUtil.logException(e);
        }
        return null;
    }

    /** 
    * @Title: getApkVersionCode
    * @Description: 获取apk版本信息
    * @param @param ctx
    * @param @param apkPath
    * @param @return
    * @param @throws Exception
    * @return int
    * @throws 
    */ 
    private int getApkVersionCode(Context ctx, String apkPath) throws Exception
    {
        File apkFile = new File(apkPath);
        if (!apkFile.exists()
                || !apkPath.toLowerCase(Locale.CHINA).endsWith(".apk"))
        {
            return -1;
        }
        int apkVersionCode = 0;
        String PATH_PackageParser = "android.content.pm.PackageParser";
        String PATH_AssetManager = "android.content.res.AssetManager";
        try
        {
            // 反射得到pkgParserCls对象并实例化,有参数
            Class<?> pkgParserCls = Class.forName(PATH_PackageParser);
            Class<?>[] typeArgs = { String.class };
            Constructor<?> pkgParserCt = pkgParserCls.getConstructor(typeArgs);
            Object[] valueArgs = { apkPath };
            Object pkgParser = pkgParserCt.newInstance(valueArgs);

            // 从pkgParserCls类得到parsePackage方法
            DisplayMetrics metrics = new DisplayMetrics();
            metrics.setToDefaults();// 这个是与显示有关的, 这边使用默认
            typeArgs = new Class<?>[] { File.class, String.class,
                    DisplayMetrics.class, int.class };
            Method pkgParser_parsePackageMtd = pkgParserCls.getDeclaredMethod(
                    "parsePackage", typeArgs);

            valueArgs = new Object[] { new File(apkPath), apkPath, metrics, 0 };

            // 执行pkgParser_parsePackageMtd方法并返回
            Object pkgParserPkg = pkgParser_parsePackageMtd.invoke(pkgParser,
                    valueArgs);

            // 从返回的对象得到名为"applicationInfo"的字段对象
            if (pkgParserPkg == null)
            {
                return 0;
            }
            Field appInfoFld = pkgParserPkg.getClass().getDeclaredField(
                    "applicationInfo");

            // 从对象"pkgParserPkg"得到字段"appInfoFld"的值
            if (appInfoFld.get(pkgParserPkg) == null)
            {
                return 0;
            }
            ApplicationInfo info = (ApplicationInfo) appInfoFld
                    .get(pkgParserPkg);

            // 反射得到assetMagCls对象并实例化,无参
            Class<?> assetMagCls = Class.forName(PATH_AssetManager);
            Object assetMag = assetMagCls.newInstance();
            // 从assetMagCls类得到addAssetPath方法
            typeArgs = new Class[1];
            typeArgs[0] = String.class;
            Method assetMag_addAssetPathMtd = assetMagCls.getDeclaredMethod(
                    "addAssetPath", typeArgs);
            valueArgs = new Object[1];
            valueArgs[0] = apkPath;
            // 执行assetMag_addAssetPathMtd方法
            assetMag_addAssetPathMtd.invoke(assetMag, valueArgs);

            // 得到Resources对象并实例化,有参数
            Resources res = ctx.getResources();
            typeArgs = new Class[3];
            typeArgs[0] = assetMag.getClass();
            typeArgs[1] = res.getDisplayMetrics().getClass();
            typeArgs[2] = res.getConfiguration().getClass();
            Constructor<Resources> resCt = Resources.class
                    .getConstructor(typeArgs);
            valueArgs = new Object[3];
            valueArgs[0] = assetMag;
            valueArgs[1] = res.getDisplayMetrics();
            valueArgs[2] = res.getConfiguration();
            res = (Resources) resCt.newInstance(valueArgs);

            // 读取apk文件的信息

            PackageManager pm = ctx.getPackageManager();
            PackageInfo packageInfo = pm.getPackageArchiveInfo(apkPath,
                    PackageManager.GET_ACTIVITIES);
            if (packageInfo != null
                    && info != null
                    && (info.packageName)
                            .equalsIgnoreCase("com.play.treasure"))
            {
                apkVersionCode = packageInfo.versionCode;// 软件版本码
            }

            return apkVersionCode;
        }
        catch (Exception e)
        {
            LogUtil.logException(e);
            throw e;
        }
    }
}
