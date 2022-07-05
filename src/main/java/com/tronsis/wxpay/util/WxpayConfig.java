package com.tronsis.wxpay.util;

import java.io.InputStream;
import java.util.Properties;

public class WxpayConfig {
	public static final String KEY = "6nyuz5ughwgz84cqewzhtb76fsv2d5zb";
	public static final String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	public static String WECHAT_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid={0}&secret={1}&code={2}&grant_type=authorization_code";
	public static final String ORDER_QUERY_URL = "https://api.mch.weixin.qq.com/pay/orderquery";

	public static final String RETURN_CODE_SUCCESS = "SUCCESS";
	public static final String RETURN_CODE_FAIL = "FAIL";
	public static final String DEVICE_INFO_WEB = "WEB";
	public static final String TRADE_TYPE_NATIVE = "NATIVE";
	public static final String TRADE_TYPE_JSAPI = "JSAPI";
	public static final String TRADE_TYPE_APP = "APP";
	public static final String PREPAY_ID = "prepay_id";
	public static final String SIGN_TYPE_MD5 = "MD5";
	public static final String PACKAGE_VALUE_APP = "Sign=WXPay";

	// FIXME SECRET必须设置，等待审核通过
	public static String SECRET = "";
	public static String APPID = "";
	public static String MCH_ID = "";
	public static String UNIFIED_ORDER_CALLBACK_URL = "";

	static {
		Properties pro = new Properties();
		InputStream in = WxpayConfig.class.getClassLoader().getResourceAsStream("config.properties");
		try {
			pro.load(in);
			APPID = pro.getProperty("APPID");
			MCH_ID = pro.getProperty("MCH_ID");
			SECRET = pro.getProperty("SECRET");
			UNIFIED_ORDER_CALLBACK_URL = pro.getProperty("UNIFIED_ORDER_CALLBACK_URL");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
