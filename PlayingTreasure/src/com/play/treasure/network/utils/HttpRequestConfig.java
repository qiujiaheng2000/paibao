package com.play.treasure.network.utils;

/**
 * 〈一句话功能简述〉HTTP请求配置. 〈功能详细描述〉所有数据及说明来自接口文档.
 * 
 * @author 王超
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface HttpRequestConfig {
	/**
	 * Sets the timeout until a connection is etablished. A value of zero means
	 * the timeout is not used. The default value is zero.
	 */
	int CONNECTION_TIME_OUT = 50000;

	/**
	 * Sets the default socket timeout (SO_TIMEOUT) in milliseconds which is the
	 * timeout for waiting for data. A timeout value of zero is interpreted as
	 * an infinite timeout. This value is used when no socket timeout is set in
	 * the method parameters.
	 */
	int SO_TIME_OUT = 50000;

	/**
	 * socket buffer size.
	 */
	int SOCKET_BUFFER_SIZE = 8192;

	/**
	 * 请求重试最多次数.
	 */
	int MAX_RETRY_NUM = 3;

	/**
	 * 〈一句话功能简述〉请求调用结果 〈功能详细描述〉
	 * 
	 * @author 王超
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	public interface Result {
		/** 请求成功. **/
		int OK = 0;

		/** 请求参数错误. **/
		int ERROR_PARAM = 1;

		/** 服务器内部错误. **/
		int ERROR_500 = 3;

	}

	/**
	 * 〈一句话功能简述〉异常信息 〈功能详细描述〉
	 * 
	 * @author 王超
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	public interface ErrorMsg {
		/** 终端网络异常. **/
		String ERROR_NETWORK = "网络异常,请检查网络连接.";

		/** 数据解析异常. **/
		String ERROR_DATA_PARSER = "数据获取异常,请重试.";

		/** 登录错误. **/
		String ERROT_LOGIN = "未登录";
	}
}
