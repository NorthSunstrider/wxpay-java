package com.tronsis.wxpay.service;

import com.tronsis.wxpay.util.Signature;
import com.tronsis.wxpay.util.Util;
import com.tronsis.wxpay.util.WxpayConfig;
import com.tronsis.wxpay.xmlobj.OfficialAccountsPayRequest;
import com.tronsis.wxpay.xmlobj.UnifiedOrderRequest;
import com.tronsis.wxpay.xmlobj.UnifiedOrderResponse;

public class OfficialAccountsPayService {
    public OfficialAccountsPayRequest createPayment(String description, String orderSn, Integer totalPrice,
        String openid) throws Exception {
        String nonce_str = Util.getRandomString(8);
        String callbackUrl = WxpayConfig.UNIFIED_ORDER_CALLBACK_URL;
        // TODO 设置为请求发起的服务器ip
        String ip = "1.1.1.1";
        UnifiedOrderService unifiedOrderService = new UnifiedOrderService();
        UnifiedOrderRequest unifiedOrderRequest =
            new UnifiedOrderRequest(WxpayConfig.APPID, WxpayConfig.MCH_ID, WxpayConfig.DEVICE_INFO_WEB, nonce_str,
                description, orderSn, totalPrice, ip, callbackUrl, WxpayConfig.TRADE_TYPE_JSAPI, orderSn);
        unifiedOrderRequest.setOpenid(openid);

        UnifiedOrderResponse unifiedOrderResponse = unifiedOrderService.request(unifiedOrderRequest);
        if (unifiedOrderResponse != null
            && WxpayConfig.RETURN_CODE_SUCCESS.equals(unifiedOrderResponse.getResult_code())
            && WxpayConfig.RETURN_CODE_SUCCESS.equals(unifiedOrderResponse.getReturn_code())) {
            OfficialAccountsPayRequest brandWCPayRequest = new OfficialAccountsPayRequest();
            brandWCPayRequest.setAppId(unifiedOrderResponse.getAppid());
            brandWCPayRequest.setNonceStr(Util.getRandomString(8));
            brandWCPayRequest.setPackage_(WxpayConfig.PREPAY_ID + "=" + unifiedOrderResponse.getPrepay_id());
            brandWCPayRequest.setTimeStamp(System.currentTimeMillis() + "");
            brandWCPayRequest.setSignType(WxpayConfig.SIGN_TYPE_MD5);
            String sign = Signature.getSign(brandWCPayRequest.toMap());
            brandWCPayRequest.setPaySign(sign);

            return brandWCPayRequest;
        } else {
            return null;
        }

    }
}
