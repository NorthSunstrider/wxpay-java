package com.tronsis.wxpay.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.tronsis.wxpay.service.ScanQRCodePayService;
import com.tronsis.wxpay.service.UnifiedOrderService;
import com.tronsis.wxpay.util.Signature;
import com.tronsis.wxpay.util.Util;
import com.tronsis.wxpay.util.WxpayConfig;
import com.tronsis.wxpay.xmlobj.OrderQueryRequest;
import com.tronsis.wxpay.xmlobj.UnifiedOrderCallbackRequest;
import com.tronsis.wxpay.xmlobj.UnifiedOrderRequest;
import com.tronsis.wxpay.xmlobj.UnifiedOrderResponse;

public class Test {

	public static void main(String[] args) {
		// testUnifiedOrder();
		// testParseXml2Object();
		// testParseXml2UnifiedOrderCallback();
		// testZZ();
		// testSign();
		testScanQRCodePay();
	}

	public static void testSB() {
		StringBuilder sb = new StringBuilder("abcdefg&");
		sb.length();
		sb.delete(sb.length() - 1, sb.length());
		System.out.println(sb);

	}

	public static void testUnifiedOrder() {

		UnifiedOrderRequest unifiedOrderRequest = new UnifiedOrderRequest();

		unifiedOrderRequest.setAppid(WxpayConfig.APPID);
		unifiedOrderRequest.setMch_id(WxpayConfig.MCH_ID);
		unifiedOrderRequest.setDevice_info(WxpayConfig.DEVICE_INFO_WEB);
		unifiedOrderRequest.setNonce_str(Util.getRandomString(8));
		unifiedOrderRequest.setBody("goods description");
		unifiedOrderRequest.setOut_trade_no("EP201511270005");
		unifiedOrderRequest.setTotal_fee(1);
        // TODO 设置为请求发起的服务器ip
		unifiedOrderRequest.setSpbill_create_ip("1.1.1.1");
        // TODO 设置为回调链接
		unifiedOrderRequest.setNotify_url("test.com:8080/projectname/callback");
		unifiedOrderRequest.setTrade_type(WxpayConfig.TRADE_TYPE_NATIVE);
		unifiedOrderRequest.setProduct_id("EP2015120800");

		UnifiedOrderService unifiedOrderService = new UnifiedOrderService();
		try {
			UnifiedOrderResponse unifiedOrderResponse = unifiedOrderService.request(unifiedOrderRequest);
			System.out.println(unifiedOrderResponse.getReturn_code());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void testParseXml2Object() {
		String xml = "<xml><return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[OK]]></return_msg>"
				+ "<appid><![CDATA[wx9a2d9f715062e774]]></appid>" + "<mch_id><![CDATA[1285554201]]></mch_id>"
				+ "<device_info><![CDATA[WEB]]></device_info>" + "<nonce_str><![CDATA[v0j5RLZpY8yqnEOs]]></nonce_str>"
				+ "<sign><![CDATA[72F5F7F2627E161E24B0A1A0AE07A99A]]></sign>" + "<result_code><![CDATA[FAIL]]></result_code>"
				+ "<err_code><![CDATA[ORDERPAID]]></err_code>" + "<err_code_des><![CDATA[该订单已支付]]></err_code_des>" + "</xml>";

		UnifiedOrderResponse unifiedOrderResponse = (UnifiedOrderResponse) Util.getObjectFromXML(xml, UnifiedOrderResponse.class);
		System.out.println(unifiedOrderResponse.getReturn_code());

	}

	public static void testParseXml2UnifiedOrderCallback() {
		String xml = "</xml>" + "<xml><appid><![CDATA[wx9a2d9f715062e774]]></appid>" + "<bank_type><![CDATA[CFT]]></bank_type>"
				+ "<cash_fee><![CDATA[1]]></cash_fee>" + "<device_info><![CDATA[WEB]]></device_info>"
				+ "<fee_type><![CDATA[CNY]]></fee_type>" + "<is_subscribe><![CDATA[N]]></is_subscribe>"
				+ "<mch_id><![CDATA[1285554201]]></mch_id>" + "<nonce_str><![CDATA[wjmg7klg]]></nonce_str>"
				+ "<openid><![CDATA[o9knYtxYTulMA5tXZ55iNl24_23k]]></openid>" + "<out_trade_no><![CDATA[EP201511270002]]></out_trade_no>"
				+ "<result_code><![CDATA[SUCCESS]]></result_code>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
				+ "<sign><![CDATA[ED9F51F018F0582D00C67B7FBC2A7221]]></sign>" + "<time_end><![CDATA[20151130180446]]></time_end>"
				+ "<total_fee>1</total_fee>" + "<trade_type><![CDATA[NATIVE]]></trade_type>"
				+ "<transaction_id><![CDATA[1005150166201511301838917277]]></transaction_id>" + "</xml>";

		UnifiedOrderCallbackRequest unifiedOrderCallback = (UnifiedOrderCallbackRequest) Util.getObjectFromXML(xml,
				UnifiedOrderCallbackRequest.class);

		System.out.println(unifiedOrderCallback.getAppid());

	}

	public static void testZZ() {

		String testStr = "<!--show me-->";
		Pattern p = Pattern.compile("<!--(.*)-->");
		Matcher m = p.matcher(testStr);
		while (m.find()) {
			System.out.println(m.group(1));
		}
	}

	public static void testSign() {
		try {
			OrderQueryRequest orderQueryRequest = new OrderQueryRequest();
			orderQueryRequest.setAppid(WxpayConfig.APPID);
			orderQueryRequest.setMch_id(WxpayConfig.MCH_ID);
			orderQueryRequest.setTransaction_id("123");
			orderQueryRequest.setOut_trade_no(null);
			orderQueryRequest.setNonce_str(Util.getRandomString(8));

			String sign0 = Signature.getSign(orderQueryRequest);
			String sign1 = Signature.getSign(orderQueryRequest.toMap());
			System.out.println(sign0);
			System.out.println(sign1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void testScanQRCodePay() {
		try {
			ScanQRCodePayService scanQRCodePayService = new ScanQRCodePayService();
			String url = scanQRCodePayService.createPayment("商品描述", "EP2015150810", 1, "1.1.1.1");
			System.out.println(url);
		} catch (Exception e) {
			Util.log(e);
		}
	}

}
