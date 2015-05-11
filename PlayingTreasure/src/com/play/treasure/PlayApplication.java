package com.play.treasure;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.impl.ext.LruDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.MemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.LRULimitedMemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.play.treasure.model.SharedPreferencesUserInfo;
import com.play.treasure.network.NetworkApi;
import com.play.treasure.network.impl.NetworkApiImpl;
import com.play.treasure.utils.LogUtil;
import com.play.treasure.utils.PreferencesUtils;

/**
 * 系统全局
 *
 * @author 王超
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class PlayApplication extends Application {
    private static PlayApplication playApplication;

    public static int sw, sh;

    private NetworkApi mNetwokApi;

    /**
     * 退栈式退出程序
     */
    private List<Activity> activityList = new LinkedList<Activity>();

    /**
     * 用户信息
     */
    private SharedPreferencesUserInfo mSharedPreferencesUserInfo;

    /**
     * DisplayMetrics
     */
    private DisplayMetrics mDisplayMetrics;

    /**
     * 宽度
     */
    private int mWidth;
    /**
     * 高度
     */
    private int mHeight;

    public LocationClient mLocationClient;
    public MyLocationListener mMyLocationListener;
    public TextView mLocationResult;
    public TextView trigger, exit;
    private String longitude = "0";
    private String latitude = "0";
    /**
     * 位置
     */
    public BDLocation location;

    private String address;

    /**
     * @Fields bannerId : 详情页bannerID
     */
    private String bannerId;

    private PackageInfo packageInfo;

    private String categoryId;

    private String tid;

    private String selected_small_id;

    private String category;

    private String distance;

    private List<String> imageUrls;
    ;

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public final static double maxConpressSize = 300;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public static final String HAIZHI_IAMGE_CACHE_SDCARD_PATH = "Play/cache";

    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化相关实例
        playApplication = this;
        mNetwokApi = new NetworkApiImpl();

        WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        Functions.sw = windowManager.getDefaultDisplay().getWidth();
        Functions.sh = windowManager.getDefaultDisplay().getHeight();
        sw = windowManager.getDefaultDisplay().getWidth();
        sh = windowManager.getDefaultDisplay().getHeight();
        PreferencesUtils sp = new PreferencesUtils(this);
        sp.putInt("sw", sw);
        sp.putInt("sh", sh);

        Log.e("test", "sw: " + sw + ", sh " + sh);

        File cacheDir = StorageUtils.getOwnCacheDirectory(getApplicationContext(),
                HAIZHI_IAMGE_CACHE_SDCARD_PATH);
        int memoryCacheSize = (int) (Runtime.getRuntime().maxMemory() / 8);

        MemoryCache memoryCache;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            memoryCache = new LruMemoryCache(memoryCacheSize);
        } else {
            memoryCache = new LRULimitedMemoryCache(memoryCacheSize);
        }

        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(this);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.memoryCache(memoryCache);
        config.diskCache(new UnlimitedDiscCache(cacheDir));
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app
        // Initialize ImageLoader with configuration.
        if (ImageLoader.getInstance().isInited())
            ImageLoader.getInstance().destroy();
        ImageLoader.getInstance().init(config.build());

        mSharedPreferencesUserInfo = new SharedPreferencesUserInfo(this);

        mDisplayMetrics = getResources().getDisplayMetrics();
        mHeight = mDisplayMetrics.heightPixels;
        mWidth = mDisplayMetrics.widthPixels;

        mMyLocationListener = new MyLocationListener();

        // 定位初始化
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(mMyLocationListener);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
        option.setScanSpan(1000);
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
        if (mLocationClient != null && mLocationClient.isStarted()) {
            mLocationClient.requestLocation();
        } else {
            Log.d("qjh", "locClient is null or not started");
        }
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (NameNotFoundException e) {
            LogUtil.logException(e);
        }
    }

    /**
     * @param @return 设定文件
     * @return
     * @throws
     * @Description: 获取全局单例
     */
    public static PlayApplication getApplication() {
        if (playApplication == null) {
            playApplication = new PlayApplication();
        }
        return playApplication;
    }

    /**
     * @param @return
     * @return int
     * @throws
     * @Title: getVersionCode
     * @Description: 获取版本号
     */
    public int getVersionCode() {
        return packageInfo.versionCode;
    }

    public String getVersionName() {
        return packageInfo.versionName;
    }

    /**
     * @param @return 设定文件
     * @return NetworkApi 返回类型
     * @throws
     * @Title: getNetApi
     * @Description: 获取网络api
     */
    public NetworkApi getNetApi() {
        return mNetwokApi;
    }

    /**
     * 功能描述: 获取用户id
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public String getUserId() {
        return mSharedPreferencesUserInfo.getUserId();
    }

    /**
     * 功能描述: 保存用户id
     *
     * @param mUserId
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public void setUserId(String mUserId) {
        mSharedPreferencesUserInfo.setUserId(mUserId);
    }

    /**
     * 功能描述: 取得用户密码
     * 〈功能详细描述〉
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public String getUserPwd() {
        return mSharedPreferencesUserInfo.getUserPwd();
    }

    /**
     * 功能描述: 保存用户密码
     * 〈功能详细描述〉
     *
     * @param pwd
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public void setUserPwd(String pwd) {
        mSharedPreferencesUserInfo.setUserPwd(pwd);
    }

    /**
     * 获取持久化用户名
     *
     * @return
     */
    public String getSharedPreLoginName() {
        return mSharedPreferencesUserInfo.getSharedPreLoginName();
    }

    /**
     * 设置持久化用户名
     *
     * @param sharedPreLoginName
     */
    public void setSharedPreLoginName(String sharedPreLoginName) {
        mSharedPreferencesUserInfo.setSharedPreLoginName(sharedPreLoginName);
    }

    /**
     * 获取持久化密码
     *
     * @return
     */
    public String getSharedPreLoginPwd() {
        return mSharedPreferencesUserInfo.getSharedPreLoginPwd();
    }

    /**
     * 设置持久化密码
     *
     * @param mSharedPreLoginPwd
     */
    public void setSharedPreLoginPwd(String mSharedPreLoginPwd) {
        mSharedPreferencesUserInfo.setSharedPreLoginPwd(mSharedPreLoginPwd);
    }

    /**
     * 功能描述: 将所有activity添加进去
     * 〈功能详细描述〉
     *
     * @param activity
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    /**
     * 功能描述: 将所有Activity杀死
     * 〈功能详细描述〉
     *
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        System.exit(0);
    }

    /**
     * 获取屏幕宽度
     *
     * @return
     */
    public int getWidth() {
        return mWidth;
    }

    /**
     * 获取屏幕高度
     *
     * @return
     */
    public int getHeigth() {
        return mHeight;
    }

    /**
     * 实现实位回调监听
     */
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //Receive Location
            longitude = String.valueOf(location.getLongitude());
            latitude = String.valueOf(location.getLatitude());
            address = location.getAddrStr();
            Log.d("qjh","lon = " + longitude+ ",  lat = "+latitude+",  address = "+address);
        }
    }


    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBannerId() {
        return bannerId;
    }

    public void setBannerId(String bannerId) {
        this.bannerId = bannerId;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getSelected_small_id() {
        return selected_small_id;
    }

    public void setSelected_small_id(String selected_small_id) {
        this.selected_small_id = selected_small_id;
    }

}
