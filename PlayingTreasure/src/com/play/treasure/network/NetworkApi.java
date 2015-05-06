package com.play.treasure.network;

import java.io.File;
import java.util.List;

import com.play.treasure.network.model.NetworkBean;
import com.play.treasure.network.model.NetworkBeanArray;


/**
 * 网络请求提交接口 
 *
 * @author 王超
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface NetworkApi 
{
	/** 
	* @Title: register
	* @Description: 注册
	* @param @param phone
	* @param @param username
	* @param @param password
	* @param @param nickname
	* @param @param address
	* @param @return
	* @return NetworkBean
	* @throws 
	*/ 
	NetworkBean register(String phone,String username,String password,String nickname,String address);

	/** 
	* @Title: login
	* @Description: 登录
	* @param @param username
	* @param @param pwd
	* @param @return
	* @return NetworkBean
	* @throws 
	*/ 
	NetworkBean login(String token, String pwd);
	
	/** 
	* @Title: search
	* @Description: 搜索
	* @param @param input
	* @param @param x
	* @param @param y
	* @param @param p
	* @param @param post_cate
	* @param @param dis_sort
	* @param @return
	* @return NetworkBeanArray
	* @throws 
	*/ 
	NetworkBeanArray search(String input,String x,String y,String p,String post_cate,String dis_sort);
	
	/** 
	* @Title: homeWaterfall
	* @Description: 商户数据
	* @param @param lantitude
	* @param @param latitude
	* @param @param page
	* @param @param uid
	* @param @return
	* @return NetworkBeanArray
	* @throws 
	*/ 
	NetworkBeanArray shopUser(String uid);
	/** 
	* @Title: homeWaterfall
	* @Description: 商户瀑布流
	* @param @param lantitude
	* @param @param latitude
	* @param @param page
	* @param @param uid
	* @param @return
	* @return NetworkBeanArray
	* @throws 
	*/ 
	NetworkBeanArray shopWaterfall(String lantitude,String latitude,String page,String uid);
	/** 
	* @Title: homeWaterfall
	* @Description: 首页瀑布流
	* @param @param lantitude
	* @param @param latitude
	* @param @param page
	* @param @param post_cate
	* @param @return
	* @return NetworkBeanArray
	* @throws 
	*/ 
	NetworkBeanArray homeWaterfall(String lantitude,String latitude,String page,String post_cate);
	/** 
	* @Title: mineWaterfall
	* @Description: 我的宝贝瀑布流
	* @param @param userId
	* @param @param page
	* @param @param post_cate
	* @param @return
	* @return NetworkBeanArray
	* @throws 
	*/ 
	NetworkBeanArray mineWaterfall(String userId,String page,String post_cate);
	
	/** 
	* @Title: bannerShow
	* @Description: 广告位
	* @param @return
	* @return NetworkBeanArray
	* @throws 
	*/ 
	NetworkBeanArray bannerShow();
	
	/** 
	* @Title: playComment
	* @Description: 根据id获取评论
	* @param @return
	* @return NetworkBeanArray
	* @throws 
	*/ 
	NetworkBeanArray playComment(String id);
	
	/**
	 * @Title: playDetail
	 * @Description: 根据id获取宝贝详情
	 * @param id
	 * @return NetworkBean
	 */
	NetworkBean playDetail(String id,String uid,String x,String y);
	/**
	 * @Description: 寻宝页
	 * @return
	 */
	NetworkBeanArray huntCategory();
	
 
	/** 
	* @Title: huntDetial
	* @Description: 寻宝详情页
	* @param @param p
	* @param @param x
	* @param @param y
	* @param @param small_cate
	* @param @param post_cate
	* @param @return
	* @return NetworkBeanArray
	* @throws 
	*/ 
	NetworkBeanArray huntDetial(String p,String x,String y,String small_cate,String post_cate);
	
	/**
	 * @Description: 我的评论
	 * @param uid
	 * @param p
	 * @return
	 */
	NetworkBeanArray myplayComment(String uid,String p);
	
	/** 
	* @Title: myFavorite
	* @Description: 我的喜欢
	* @param @param uid
	* @param @param x
	* @param @param y
	* @param @param p
	* @param @return
	* @return NetworkBeanArray
	* @throws 
	*/ 
	NetworkBeanArray myFavorite(String uid,String x,String y,String p);
	
	/** 
	* @Title: mySale
	* @Description: 我的出售
	* @param @param uid
	* @param @param p
	* @param @return
	* @return NetworkBeanArray
	* @throws 
	*/ 
	NetworkBeanArray mySale(String uid,String p);
	
	/** 
	* @Title: myHome
	* @Description: 我的宝贝
	* @param @param uid
	* @param @param p
	* @param @param post_cate
	* @param @return
	* @return NetworkBeanArray
	* @throws 
	*/ 
	NetworkBeanArray myHome(String uid,String p,String post_cate);
	/**
	 * @Description: 发布页分类
	 */
	NetworkBeanArray publicCategory();
	
	/** 
	* @Title: publicFirst
	* @Description: 发布宝贝第一步
	* @param @param id
	* @param @param uid
	* @param @return
	* @return NetworkBean
	* @throws 
	*/ 
	NetworkBean publicFirst(String id,String uid);
	/** 
	* @Title: publicThird
	* @Description: 发布第三步
	* @param @param tid
	* @param @param post_cate
	* @param @param x
	* @param @param y
	* @param @param address
	* @param @param qq
	* @param @param mobile
	* @param @return
	* @return NetworkBean
	* @throws 
	*/ 
	NetworkBean publicThird(String tid,String post_cate,String x,String y,String address,String qq,String mobile);
	/** 
	* @Title: detailImg
	* @Description: 详情页图片id
	* @param @param playId
	* @param @return
	* @return NetworkBeanArray
	* @throws 
	*/ 
	NetworkBeanArray detailImg(String playId);
	
	/** 
	* @Title: getVersion
	* @Description: 获取软件版本信息
	* @param @return
	* @return NetworkBeanArray
	* @throws 
	*/ 
	NetworkBean getVersion();
	
	/** 
	* @Title: treasureMall
	* @Description: 商城页
	* @param @param p
	* @param @param x
	* @param @param y
	* @param @param big_cate
	* @param @return
	* @return NetworkBeanArray
	* @throws 
	*/ 
	NetworkBeanArray treasureMall(String p,String x,String y,String big_cate);
	
	/** 
	* @Title: nearBy
	* @Description: 身边页
	* @param @param p
	* @param @param x
	* @param @param y
	* @param @param big_cate
	* @param @param post_cate
	* @param @return
	* @return NetworkBeanArray
	* @throws 
	*/ 
	NetworkBeanArray nearBy(String p,String x,String y,String big_cate,String post_cate);
	
	/** 
	* @Title: upLoadIcon
	* @Description: 上传头像
	* @param @param uid
	* @param @param file
	* @param @return
	* @return NetworkBean
	* @throws 
	*/ 
	NetworkBean upLoadIcon(String uid,File file);
	
	/** 
	* @Title: modifyIcon
	* @Description: 修改头像
	* @param @param uid
	* @param @return
	* @return NetworkBean
	* @throws 
	*/ 
	NetworkBean modifyIcon(String uid);
	
	/** 
	* @Title: modifyData
	* @Description: 修改昵称、地址
	* @param @param uid
	* @param @param nickname
	* @param @param address
	* @param @return
	* @return NetworkBean
	* @throws 
	*/ 
	NetworkBean modifyData(String uid,String nickname,String address);
	
	/** 
	* @Title: aboutMe
	* @Description:关于
	* @param @return
	* @return NetworkBean
	* @throws 
	*/ 
	NetworkBean aboutMe();
	
	/** 
	* @Title: upload
	* @Description: 上传多张图片
	* @param @param title
	* @param @param content
	* @param @param files
	* @param @return
	* @return NetworkBean
	* @throws 
	*/ 
	NetworkBean uploadImg(String tid,String title,String content,List<File> files);
	
	/** 
	* @Title: uploadData
	* @Description: 发布第二步上传数据
	* @param @param tid
	* @param @param title
	* @param @param content
	* @param @return
	* @return NetworkBean
	* @throws 
	*/ 
	NetworkBean uploadData(String tid,String title,String content);
	
	
	/** 
	* @Title: contactUs
	* @Description:联系我们
	* @param @return
	* @return NetworkBean
	* @throws 
	*/ 
	NetworkBean contactUs();
	
	/** 
	* @Title: feedBack
	* @Description: 意见反馈
	* @param @param uid
	* @param @param content
	* @param @return
	* @return NetworkBean
	* @throws 
	*/ 
	NetworkBean feedBack(String uid,String content);
	
	/** 
	* @Title: modifyPwd
	* @Description: 修改密码
	* @param @param uid
	* @param @param old_pwd
	* @param @param new_pwd
	* @param @param renew_pwd
	* @param @return
	* @return NetworkBean
	* @throws 
	*/ 
	NetworkBean modifyPwd(String uid,String old_pwd,String new_pwd,String renew_pwd);	
	/** 
	* @Title: commentTreasure
	* @Description: 评论宝贝
	* @param @param uid
	* @param @param tid
	* @param @param comment
	* @param @return
	* @return NetworkBean
	* @throws 
	*/ 
	NetworkBean commentTreasure(String uid,String tid,String content);	
	
	/** 
	* @Title: isPraise
	* @Description: 是否被赞
	* @param @param tid
	* @param @param uid
	* @param @return
	* @return NetworkBean
	* @throws 
	*/ 
	NetworkBean isPraise(String tid,String uid);
	/** 
	* @Title: deleteMine
	* @Description: 删除我的宝贝
	* @param @param tid
	* @param @return
	* @return NetworkBean
	* @throws 
	*/ 
	NetworkBean deleteMine(String tid);
	
	/** 
	* @Title: modifyFirst
	* @Description: 修改第一步请求
	* @param @param tid
	* @param @return
	* @return NetworkBean
	* @throws 
	*/ 
	NetworkBean modifyFirst(String tid);
	/** 
	* @Title: modifySecond
	* @Description:修改第二步请求
	* @param @param tid
	* @param @return
	* @return NetworkBean
	* @throws 
	*/ 
	NetworkBean modifySecond(String tid);
	/** 
	* @Title: modifyThrid
	* @Description: 修改第三步请求
	* @param @param tid
	* @param @return
	* @return NetworkBean
	* @throws 
	*/ 
	NetworkBean modifyThrid(String tid);

	String bannerUrl();

	NetworkBeanArray shopUserImage(String uid);
}
