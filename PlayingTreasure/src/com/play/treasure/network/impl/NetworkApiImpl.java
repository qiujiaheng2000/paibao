package com.play.treasure.network.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;

import com.play.treasure.network.NetworkApi;
import com.play.treasure.network.NetworkConfig;
import com.play.treasure.network.model.NetworkBean;
import com.play.treasure.network.model.NetworkBeanArray;
import com.play.treasure.network.utils.Caller;

/**
 * 网络请求实现类  
 * 
 * @author 王超
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class NetworkApiImpl implements NetworkApi 
{
	private Caller mCaller;

	/**
	 * 构造方法
	 */
	public NetworkApiImpl() 
	{
		mCaller = new Caller();
	}

	@Override
	public NetworkBean register(String phone,String username,String password,String nickname,String address) 
	{
		NetworkBean networkBean = null;
		List<NameValuePair> mList = new ArrayList<NameValuePair>();
		mList.add(new BasicNameValuePair("mobile", phone));
		mList.add(new BasicNameValuePair("username", username));
		mList.add(new BasicNameValuePair("password", password));
		mList.add(new BasicNameValuePair("nickname", nickname));
		mList.add(new BasicNameValuePair("address", address));

		String result = mCaller.httpPost(mList, NetworkConfig.registerUrl);
		Log.d("result", result);
		if (result != null) 
		{
			networkBean = new NetworkBean(result);
			return networkBean;
		}
		return networkBean;
	}

	@Override
	public NetworkBean login(String token, String pwd) 
	{
		NetworkBean networkBean = null;
		List<NameValuePair> mList = new ArrayList<NameValuePair>();
		mList.add(new BasicNameValuePair("token", token));
		mList.add(new BasicNameValuePair("password", pwd));

		String result = mCaller.httpPost(mList, NetworkConfig.loginUrl);
		Log.d("result", result);
		if (result != null) 
		{
			networkBean = new NetworkBean(result);
			return networkBean;
		}
		return networkBean;
	}

	@Override
	public NetworkBeanArray homeWaterfall(String lantitude,String latitude,String page,String post_cate)
	{
		NetworkBeanArray networkBean = null;
		List<NameValuePair> mList = new ArrayList<NameValuePair>();
		mList.add(new BasicNameValuePair("x", lantitude));
		mList.add(new BasicNameValuePair("y", latitude));
		mList.add(new BasicNameValuePair("p", page));
		mList.add(new BasicNameValuePair("post_cate", post_cate));

		String result = mCaller.httpPost(mList, NetworkConfig.homeWaterfallUrl);
		Log.d("result", result);
		if (result != null) 
		{
			networkBean = new NetworkBeanArray(result);
			return networkBean;
		}
		return networkBean;
	}
	
	@Override
	public NetworkBeanArray shopWaterfall(String lantitude,String latitude,String page,String uid)
	{
		NetworkBeanArray networkBean = null;
		List<NameValuePair> mList = new ArrayList<NameValuePair>();
		mList.add(new BasicNameValuePair("x", lantitude));
		mList.add(new BasicNameValuePair("y", latitude));
		mList.add(new BasicNameValuePair("p", page));
		mList.add(new BasicNameValuePair("uid", uid));

		String result = mCaller.httpPost(mList, NetworkConfig.shopWaterfallUrl);
		Log.d("result", result);
		if (result != null) 
		{
			networkBean = new NetworkBeanArray(result);
			return networkBean;
		}
		return networkBean;
	}

	/** (非 Javadoc) 
	* Title: mineWaterfall
	* Description:
	* @param userId
	* @param page
	* @param post_cate
	* @return
	* @see com.play.treasure.network.NetworkApi#mineWaterfall(java.lang.String, java.lang.String, java.lang.String)
	*/ 
	@Override
	public NetworkBeanArray mineWaterfall(String userId, String page,String post_cate) 
	{
		NetworkBeanArray networkBean = null;
		List<NameValuePair> mList = new ArrayList<NameValuePair>();
		mList.add(new BasicNameValuePair("uid", userId));
		mList.add(new BasicNameValuePair("p", page));
		mList.add(new BasicNameValuePair("post_cate", post_cate));

		String result = mCaller.httpPost(mList, NetworkConfig.myHomeUrl);
		Log.d("result", result);
		if (result != null) 
		{
			networkBean = new NetworkBeanArray(result);
			return networkBean;
		}
		return networkBean;
	}

	@Override
	public NetworkBeanArray search(String input,String x,String y,String p,String post_cate,String dis_sort) {
		NetworkBeanArray networkBean = null;
		List<NameValuePair> mList = new ArrayList<NameValuePair>();
		mList.add(new BasicNameValuePair("word", input));
		mList.add(new BasicNameValuePair("x", x));
		mList.add(new BasicNameValuePair("y", y));
		mList.add(new BasicNameValuePair("p", p));
		mList.add(new BasicNameValuePair("post_cate", post_cate));
		mList.add(new BasicNameValuePair("dis_sort", dis_sort));
		String result = mCaller.httpPost(mList, NetworkConfig.searchUrl);
		Log.d("result", result);
		if (result != null) 
		{
			networkBean = new NetworkBeanArray(result);
			return networkBean;
		}
		return networkBean;
		
	}

	/** (非 Javadoc) 
	* Title: bannerShow
	* Description:
	* @return
	* @see com.play.treasure.network.NetworkApi#bannerShow()
	*/ 
	@Override
	public NetworkBeanArray bannerShow() 
	{
		NetworkBeanArray networkBean = null;

		String result = mCaller.httpPost(null, NetworkConfig.bannerUrl);
		Log.d("result", result);
		if (result != null) 
		{
			networkBean = new NetworkBeanArray(result);
			return networkBean;
		}
		return networkBean;
	}

	/** (非 Javadoc) 
	* Title: playComment
	* Description:
	* @return
	* @see com.play.treasure.network.NetworkApi#playComment()
	*/ 
	@Override
	public NetworkBeanArray playComment(String id) 
	{
		NetworkBeanArray networkBean = null;

		List<NameValuePair> mList = new ArrayList<NameValuePair>();
		mList.add(new BasicNameValuePair("tid", id));
		String result = mCaller.httpPost(mList, NetworkConfig.commentUrl);
		Log.d("result", result);
		if (result != null) 
		{
			networkBean = new NetworkBeanArray(result);
			return networkBean;
		}
		return networkBean;
	}
	public NetworkBean playDetail(String id,String uid,String x,String y)
	{
		NetworkBean networkBean = null;

		List<NameValuePair> mList = new ArrayList<NameValuePair>();
		mList.add(new BasicNameValuePair("tid", id));
		mList.add(new BasicNameValuePair("uid", uid));
		mList.add(new BasicNameValuePair("x", x));
		mList.add(new BasicNameValuePair("y", y));
		String result = mCaller.httpPost(mList, NetworkConfig.detailUrl);
		Log.d("result", result);
		if (result != null) 
		{
			networkBean = new NetworkBean(result);
			return networkBean;
		}
		return networkBean;
		
	}
	public NetworkBeanArray huntCategory()
	{
		
		NetworkBeanArray networkBean = null;

		String result = mCaller.httpPost(null, NetworkConfig.findCategoryUrl);
		Log.d("result", result);
		if (result != null) 
		{
			networkBean = new NetworkBeanArray(result);
			return networkBean;
		}
		return networkBean;
	}
	public NetworkBeanArray myplayComment(String uid,String p) 
	{
		NetworkBeanArray networkBean = null;

		List<NameValuePair> mList = new ArrayList<NameValuePair>();
		mList.add(new BasicNameValuePair("uid", uid));
		mList.add(new BasicNameValuePair("p", p));
		String result = mCaller.httpPost(mList, NetworkConfig.mycommentUrl);
		Log.d("result", result);
		if (result != null) 
		{
			networkBean = new NetworkBeanArray(result);
			return networkBean;
		}
		return networkBean;
	}

	
	public String bannerUrl()
	{

		return mCaller.httpPost(null, NetworkConfig.bannerUrl);
	}
	/** (非 Javadoc) 
	* Title: myFavorite
	* Description:
	* @param uid
	* @return
	* @see com.play.treasure.network.NetworkApi#myFavorite(java.lang.String)
	*/ 
	@Override
	public NetworkBeanArray myFavorite(String uid,String x,String y,String p) 
	{
		NetworkBeanArray networkBean = null;
		List<NameValuePair> mList = new ArrayList<NameValuePair>();
		mList.add(new BasicNameValuePair("uid", uid));
		mList.add(new BasicNameValuePair("x", x));
		mList.add(new BasicNameValuePair("y", y));
		mList.add(new BasicNameValuePair("p", p));

		String result = mCaller.httpPost(mList, NetworkConfig.myFavoriteUrl);
		Log.d("result", result);
		if (result != null) 
		{
			networkBean = new NetworkBeanArray(result);
			return networkBean;
		}
		return networkBean;
	}

	/** (非 Javadoc) 
	* Title: mySale
	* Description:
	* @param uid
	* @param p
	* @return
	* @see com.play.treasure.network.NetworkApi#mySale(java.lang.String, java.lang.String)
	*/ 
	@Override
	public NetworkBeanArray mySale(String uid, String p) 
	{
		NetworkBeanArray networkBean = null;
		List<NameValuePair> mList = new ArrayList<NameValuePair>();
		mList.add(new BasicNameValuePair("uid", uid));
		mList.add(new BasicNameValuePair("p", p));

		String result = mCaller.httpPost(mList, NetworkConfig.mySaleUrl);
		Log.d("result", result);
		if (result != null) 
		{
			networkBean = new NetworkBeanArray(result);
			return networkBean;
		}
		return networkBean;
	}

	/** (非 Javadoc) 
	* Title: myHome
	* Description:
	* @param uid
	* @param p
	* @param post_cate
	* @return
	* @see com.play.treasure.network.NetworkApi#myHome(java.lang.String, java.lang.String, java.lang.String)
	*/ 
	@Override
	public NetworkBeanArray myHome(String uid, String p, String post_cate) 
	{
		NetworkBeanArray networkBean = null;
		List<NameValuePair> mList = new ArrayList<NameValuePair>();
		mList.add(new BasicNameValuePair("uid", uid));
		mList.add(new BasicNameValuePair("p", p));
		mList.add(new BasicNameValuePair("post_cate", post_cate));

		String result = mCaller.httpPost(mList, NetworkConfig.myHomeUrl);
		Log.d("result", result);
		if (result != null) 
		{
			networkBean = new NetworkBeanArray(result);
			return networkBean;
		}
		return networkBean;
	}
	
	/**
	 * @发布宝贝分类
	 * @return
	 */
	@Override
	public NetworkBeanArray publicCategory()
	{
		
		NetworkBeanArray networkBean = null;

		String result = mCaller.httpPost(null, NetworkConfig.findCategoryUrl);
		Log.d("result", result);
		if (result != null) 
		{
			networkBean = new NetworkBeanArray(result);
			return networkBean;
		}
		return networkBean;
		
	}

	/** (非 Javadoc) 
	* Title: publicFirst
	* Description:
	* @param id
	* @param uid
	* @return
	* @see com.play.treasure.network.NetworkApi#publicFirst(java.lang.String, java.lang.String)
	*/ 
	@Override
	public NetworkBean publicFirst(String id, String uid) 
	{
		NetworkBean networkBean = null;
		List<NameValuePair> mList = new ArrayList<NameValuePair>();
		mList.add(new BasicNameValuePair("small_id", id));
		mList.add(new BasicNameValuePair("uid", uid));

		String result = mCaller.httpPost(mList, NetworkConfig.pFirstUrl);
		Log.d("result", result);
		if (result != null) 
		{
			networkBean = new NetworkBean(result);
			return networkBean;
		}
		return networkBean;
	}

	/** (非 Javadoc) 
	* Title: detailImg
	* Description:
	* @param playId
	* @return
	* @see com.play.treasure.network.NetworkApi#detailImg(java.lang.String)
	*/ 
	@Override
	public NetworkBeanArray detailImg(String playId) 
	{
		NetworkBeanArray networkBean = null;
		List<NameValuePair> mList = new ArrayList<NameValuePair>();
		mList.add(new BasicNameValuePair("tid", playId));

		String result = mCaller.httpPost(mList, NetworkConfig.detailImgUrl);
		Log.d("result", result);
		if (result != null) 
		{
			networkBean = new NetworkBeanArray(result);
			return networkBean;
		}
		return networkBean;
	}

	/** (非 Javadoc) 
	* Title: getVersion
	* Description:
	* @return
	* @see com.play.treasure.network.NetworkApi#getVersion()
	*/ 
	@Override
	public NetworkBean getVersion() 
	{
		NetworkBean networkBean = null;
		Log.d("update", "update");
		String result = mCaller.httpPost(null, NetworkConfig.getVersionUrl);
		if(result!=null)
		{
			networkBean = new NetworkBean(result);
			return networkBean;
		}
		return networkBean;
	}
	@Override
	public NetworkBeanArray treasureMall(String p, String x, String y,String big_cate) 
	{
		NetworkBeanArray networkBean = null;
		List<NameValuePair> mList = new ArrayList<NameValuePair>();
		mList.add(new BasicNameValuePair("p", p));
		mList.add(new BasicNameValuePair("x", x));
		mList.add(new BasicNameValuePair("y", y));
		mList.add(new BasicNameValuePair("big_cate", big_cate));

		String result = mCaller.httpPost(mList, NetworkConfig.shopUrl);
		Log.d("result", result);
		if (result != null) 
		{
			networkBean = new NetworkBeanArray(result);
			return networkBean;
		}
		return networkBean;
	}
	@Override
	public NetworkBeanArray nearBy(String p, String x, String y,String big_cate,String post_cate) 
	{
		NetworkBeanArray networkBean = null;
		List<NameValuePair> mList = new ArrayList<NameValuePair>();
		mList.add(new BasicNameValuePair("p", p));
		mList.add(new BasicNameValuePair("x", x));
		mList.add(new BasicNameValuePair("y", y));
		mList.add(new BasicNameValuePair("big_cate", big_cate));
		mList.add(new BasicNameValuePair("post_cate", post_cate));

		String result = mCaller.httpPost(mList, NetworkConfig.asideUrl);
		Log.d("result", result);
		if (result != null) 
		{
			networkBean = new NetworkBeanArray(result);
			return networkBean;
		}
		return networkBean;
	}
	@Override
	public NetworkBean upLoadIcon(String UserId, File file) 
	{
		NetworkBean networkBean = null;
		String result = mCaller.httpPost(UserId,file,NetworkConfig.upLoadIconUrl);
		Log.d("result", result);
		if(result!=null)
		{
			networkBean = new NetworkBean(result);
			return networkBean;
		}
		return networkBean;
	}

	/** (非 Javadoc) 
	* Title: modifyIcon
	* Description:
	* @param uid
	* @return
	* @see com.play.treasure.network.NetworkApi#modifyIcon(java.lang.String)
	*/ 
	@Override
	public NetworkBean modifyIcon(String uid) {
		NetworkBean networkBean = null;
		List<NameValuePair> mList = new ArrayList<NameValuePair>();
		mList.add(new BasicNameValuePair("uid", uid));
		String result = mCaller.httpPost(mList,NetworkConfig.loadDataUrl);
		Log.d("result", result);
		if(result!=null)
		{
			networkBean = new NetworkBean(result);
			return networkBean;
		}
		return networkBean;
		

	}
	/** (非 Javadoc) 
	* Title: modifyData
	* Description:
	* @param uid
	* @param nickname
	* @param address
	* @return
	* @see com.play.treasure.network.NetworkApi#modifyData(java.lang.String, java.lang.String, java.lang.String)
	*/ 
	@Override
	public NetworkBean modifyData(String uid,String nickname,String address) {
		NetworkBean networkBean = null;
		List<NameValuePair> mList = new ArrayList<NameValuePair>();
		mList.add(new BasicNameValuePair("uid", uid));
		mList.add(new BasicNameValuePair("nickname", nickname));
		mList.add(new BasicNameValuePair("address", address));
		String result = mCaller.httpPost(mList,NetworkConfig.modifyDataUrl);
		Log.d("result", result);
		if(result!=null)
		{
			networkBean = new NetworkBean(result);
			return networkBean;
		}
		return networkBean;
		

	}

	@Override
	public NetworkBean aboutMe() {
		NetworkBean networkBean =null;
		String result = mCaller.httpPost(null, NetworkConfig.aboutMeUrl);
		if(result!=null)
		{
			networkBean = new NetworkBean(result);
			return networkBean;
		}
		return networkBean;
	}

	@Override
	public NetworkBean contactUs() {
		NetworkBean networkBean =null;
		String result = mCaller.httpPost(null, NetworkConfig.contactUsUrl);
		if(result!=null)
		{
			networkBean = new NetworkBean(result);
			return networkBean;
		}
		return networkBean;
	}

	@Override
	public NetworkBean feedBack(String uid, String content) {
		NetworkBean networkBean = null;
		List<NameValuePair> mList = new ArrayList<NameValuePair>();
		mList.add(new BasicNameValuePair("uid", uid));
		mList.add(new BasicNameValuePair("content", content));
		String result = mCaller.httpPost(mList,NetworkConfig.feedBackUrl);
		Log.d("result", result);
		if(result!=null)
		{
			networkBean = new NetworkBean(result);
			return networkBean;
		}
		return networkBean;
	}

	@Override
	public NetworkBean modifyPwd(String uid, String old_pwd, String new_pwd,
			String renew_pwd) {
		NetworkBean networkBean = null;
		List<NameValuePair> mList = new ArrayList<NameValuePair>();
		mList.add(new BasicNameValuePair("uid", uid));
		mList.add(new BasicNameValuePair("old_pwd", old_pwd));
		mList.add(new BasicNameValuePair("new_pwd", new_pwd));
		mList.add(new BasicNameValuePair("renew_pwd", renew_pwd));
		String result = mCaller.httpPost(mList,NetworkConfig.changePwdUrl);
		Log.d("result", result);
		if(result!=null)
		{
			networkBean = new NetworkBean(result);
			return networkBean;
		}
		return networkBean;
	}

	@Override
	public NetworkBean commentTreasure(String uid, String tid, String content) {
		NetworkBean networkBean = null;
		List<NameValuePair> mList = new ArrayList<NameValuePair>();
		mList.add(new BasicNameValuePair("uid", uid));
		mList.add(new BasicNameValuePair("tid", tid));
		mList.add(new BasicNameValuePair("content", content));
		String result = mCaller.httpPost(mList,NetworkConfig.commentTreasureUrl);
		Log.d("result", result);
		if(result!=null)
		{
			networkBean = new NetworkBean(result);
			return networkBean;
		}
		return networkBean;	}

	@Override
	public NetworkBean uploadData(String tid, String title, String detail) {
		NetworkBean networkBean = null;
		List<NameValuePair> mList = new ArrayList<NameValuePair>();
		mList.add(new BasicNameValuePair("tid", tid));
		mList.add(new BasicNameValuePair("title", title));
		mList.add(new BasicNameValuePair("detail", detail));
		String result = mCaller.httpPost(mList, NetworkConfig.pSecondDataUrl);
		Log.d("result", result);
		if (result != null) 
		{
			networkBean = new NetworkBean(result);
			return networkBean;
		}
		return networkBean;
	}

	@Override
	public NetworkBean uploadImg(String tid, String title, String content,List<File> files) 
	{
		NetworkBean networkBean = null;
		List<NameValuePair> mList = new ArrayList<NameValuePair>();
		mList.add(new BasicNameValuePair("tid", tid));
		mList.add(new BasicNameValuePair("title", title));
		mList.add(new BasicNameValuePair("detail", content));
		String result = mCaller.uploadPost(mList,files,NetworkConfig.pSecondImgUrl);
		Log.d("result", result);
		if(result!=null)
		{
			networkBean = new NetworkBean(result);
			return networkBean;
		}
		return networkBean;
	}

	@Override
	public NetworkBean publicThird(String tid, String post_cate, String x,
			String y, String address, String qq, String mobile) {
		NetworkBean networkBean = null;
		List<NameValuePair> mList = new ArrayList<NameValuePair>();
		mList.add(new BasicNameValuePair("tid", tid));
		mList.add(new BasicNameValuePair("post_cate", post_cate));
		mList.add(new BasicNameValuePair("x", x));
		mList.add(new BasicNameValuePair("y", y));
		mList.add(new BasicNameValuePair("address", address));
		mList.add(new BasicNameValuePair("qq", qq));
		mList.add(new BasicNameValuePair("mobile", mobile));
		String result = mCaller.httpPost(mList,NetworkConfig.pThirdUrl);
		Log.d("result", result);
		if(result!=null)
		{
			networkBean = new NetworkBean(result);
			return networkBean;
		}
		return networkBean;
	}

	/** (非 Javadoc) 
	* Title: huntDetial
	* Description:
	* @param p
	* @param x
	* @param y
	* @param small_cate
	* @param post_cate
	* @return
	* @see com.play.treasure.network.NetworkApi#huntDetial(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	*/ 
	@Override
	public NetworkBeanArray huntDetial(String p, String x, String y,
			String small_cate, String post_cate) {
		NetworkBeanArray networkBean = null;
		List<NameValuePair> mList = new ArrayList<NameValuePair>();
		mList.add(new BasicNameValuePair("p", p));
		mList.add(new BasicNameValuePair("x", x));
		mList.add(new BasicNameValuePair("y", y));
		mList.add(new BasicNameValuePair("small_cate", small_cate));
		mList.add(new BasicNameValuePair("post_cate", post_cate));

		String result = mCaller.httpPost(mList, NetworkConfig.findDetailgoryUrl);
		Log.d("result", result);
		if (result != null) 
		{
			networkBean = new NetworkBeanArray(result);
			return networkBean;
		}
		return networkBean;
	}

	@Override
	public NetworkBean isPraise(String tid, String uid) {
		NetworkBean networkBean = null;
		List<NameValuePair> mList = new ArrayList<NameValuePair>();
		mList.add(new BasicNameValuePair("tid", tid));
		mList.add(new BasicNameValuePair("uid", uid));
		
		String result = mCaller.httpPost(mList,NetworkConfig.isPraiseUrl);
		Log.d("result", result);
		if(result!=null)
		{
			networkBean = new NetworkBean(result);
			return networkBean;
		}
		return networkBean;
	}

	@Override
	public NetworkBean deleteMine(String tid) {
		NetworkBean networkBean = null;
		List<NameValuePair> mList = new ArrayList<NameValuePair>();
		mList.add(new BasicNameValuePair("tid", tid));
		String result = mCaller.httpPost(mList,NetworkConfig.deleteMine);
		Log.d("result", result);
		if(result!=null)
		{
			networkBean = new NetworkBean(result);
			return networkBean;
		}
		return networkBean;
	}

	@Override
	public NetworkBean modifyFirst(String tid) {
		NetworkBean networkBean = null;
		List<NameValuePair> mList = new ArrayList<NameValuePair>();
		mList.add(new BasicNameValuePair("tid", tid));
		String result = mCaller.httpPost(mList,NetworkConfig.modifyFirstUrl);
		Log.d("result", result);
		if(result!=null)
		{
			networkBean = new NetworkBean(result);
			return networkBean;
		}
		return networkBean;
	}

	@Override
	public NetworkBean modifySecond(String tid) {
		NetworkBean networkBean = null;
		List<NameValuePair> mList = new ArrayList<NameValuePair>();
		mList.add(new BasicNameValuePair("tid", tid));
		String result = mCaller.httpPost(mList,NetworkConfig.modifySecondUrl);
		Log.d("result", result);
		if(result!=null)
		{
			networkBean = new NetworkBean(result);
			return networkBean;
		}
		return networkBean;
	}

	@Override
	public NetworkBean modifyThrid(String tid) {
		NetworkBean networkBean = null;
		List<NameValuePair> mList = new ArrayList<NameValuePair>();
		mList.add(new BasicNameValuePair("tid", tid));
		String result = mCaller.httpPost(mList,NetworkConfig.modifyThridUrl);
		Log.d("result", result);
		if(result!=null)
		{
			networkBean = new NetworkBean(result);
			return networkBean;
		}
		return networkBean;
	}

	@Override
	public NetworkBeanArray shopUser(String uid) {
		NetworkBeanArray beanArray = null;
		List<NameValuePair> mList = new ArrayList<NameValuePair>();
		mList.add(new BasicNameValuePair("uid", uid));
		String result = mCaller.httpPost(mList,NetworkConfig.shopUser);
		if(result!=null){
			beanArray = new NetworkBeanArray(result);
		}
		return beanArray;
	}
	@Override
	public NetworkBeanArray shopUserImage(String uid) {
		NetworkBeanArray beanArray = null;
		List<NameValuePair> mList = new ArrayList<NameValuePair>();
		mList.add(new BasicNameValuePair("uid", uid));
		String result = mCaller.httpPost(mList,NetworkConfig.shopUserImage);
		if(result!=null){
			beanArray = new NetworkBeanArray(result);
		}
		return beanArray;
	}
}
