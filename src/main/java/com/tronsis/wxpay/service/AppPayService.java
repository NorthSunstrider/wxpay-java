package com.tronsis.wxpay.service;

import com.tronsis.wxpay.util.Signature;
import com.tronsis.wxpay.util.Util;
import com.tronsis.wxpay.util.WxpayConfig;
import com.tronsis.wxpay.xmlobj.AppPayRequest;
import com.tronsis.wxpay.xmlobj.UnifiedOrderRequest;
import com.tronsis.wxpay.xmlobj.UnifiedOrderResponse;

public class AppPayService {
	public AppPayRequest createPayment(String description, String orderSn, Integer totalPrice, String ip) throws Exception {

		UnifiedOrderService unifiedOrderService = new UnifiedOrderService();
		UnifiedOrderRequest unifiedOrderRequest = new UnifiedOrderRequest();
		unifiedOrderRequest.setAppid(WxpayConfig.APPID);
		unifiedOrderRequest.setMch_id(WxpayConfig.MCH_ID);
		unifiedOrderRequest.setNonce_str(Util.getRandomString(8));
		unifiedOrderRequest.setBody(description);
		unifiedOrderRequest.setOut_trade_no(orderSn);
		unifiedOrderRequest.setTotal_fee(totalPrice);
		unifiedOrderRequest.setSpbill_create_ip(ip);
		unifiedOrderRequest.setNotify_url(WxpayConfig.UNIFIED_ORDER_CALLBACK_URL);
		unifiedOrderRequest.setTrade_type(WxpayConfig.TRADE_TYPE_APP);

		UnifiedOrderResponse unifiedOrderResponse = unifiedOrderService.request(unifiedOrderRequest);
		if (unifiedOrderResponse != null && WxpayConfig.RETURN_CODE_SUCCESS.equals(unifiedOrderResponse.getReturn_code())
				&& WxpayConfig.RETURN_CODE_SUCCESS.equals(unifiedOrderResponse.getResult_code())) {

			AppPayRequest appPayRequest = new AppPayRequest();
			appPayRequest.setAppid(WxpayConfig.APPID);
			appPayRequest.setNonceStr(Util.getRandomString(8));
			appPayRequest.setPackageValue(WxpayConfig.PACKAGE_VALUE_APP);
			appPayRequest.setPartenerId(WxpayConfig.MCH_ID);
			appPayRequest.setPrepayId(unifiedOrderResponse.getPrepay_id());
			appPayRequest.setTimeStamp(System.currentTimeMillis() + "");
			String sign = Signature.getSign(appPayRequest.toMap());
			appPayRequest.setSign(sign);

			return appPayRequest;
		} else {
			return null;
		}
	}
}
