package com.tronsis.wxpay.service;

import com.tronsis.wxpay.util.Util;
import com.tronsis.wxpay.util.WxpayConfig;
import com.tronsis.wxpay.xmlobj.UnifiedOrderRequest;
import com.tronsis.wxpay.xmlobj.UnifiedOrderResponse;

public class ScanQRCodePayService {
	public String createPayment(String description, String orderSn, Integer totalPrice, String ip) throws Exception {
		try {
			String nonce_str = Util.getRandomString(8);
			String callBackUrl = WxpayConfig.UNIFIED_ORDER_CALLBACK_URL;
			UnifiedOrderService unifiedOrderService = new UnifiedOrderService();
			UnifiedOrderRequest unifiedOrderRequest = new UnifiedOrderRequest(WxpayConfig.APPID, WxpayConfig.MCH_ID,
					WxpayConfig.DEVICE_INFO_WEB, nonce_str, description, orderSn, totalPrice, ip, callBackUrl,
					WxpayConfig.TRADE_TYPE_NATIVE, orderSn);

			UnifiedOrderResponse unifiedOrderResponse = unifiedOrderService.request(unifiedOrderRequest);
			if (unifiedOrderResponse != null && WxpayConfig.RETURN_CODE_SUCCESS.equals(unifiedOrderResponse.getReturn_code())
					&& WxpayConfig.RETURN_CODE_SUCCESS.equals(unifiedOrderResponse.getResult_code())) {
				return unifiedOrderResponse.getCode_url();
			} else {
				return null;
			}
		} catch (Exception e) {
			Util.log(e);
			throw e;
		}
	}
}
