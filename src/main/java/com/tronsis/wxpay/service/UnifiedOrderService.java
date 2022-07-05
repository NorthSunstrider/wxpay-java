package com.tronsis.wxpay.service;

import java.io.IOException;
import java.text.ParseException;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.entity.StringEntity;
import org.xml.sax.SAXException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.tronsis.wxpay.util.Signature;
import com.tronsis.wxpay.util.Util;
import com.tronsis.wxpay.util.WxpayConfig;
import com.tronsis.wxpay.util.WxpayHttpUtil;
import com.tronsis.wxpay.xmlobj.UnifiedOrderCallbackResponse;
import com.tronsis.wxpay.xmlobj.UnifiedOrderRequest;
import com.tronsis.wxpay.xmlobj.UnifiedOrderResponse;

public class UnifiedOrderService {
	public UnifiedOrderResponse request(UnifiedOrderRequest unifiedOrderRequest) throws Exception {
		try {
			String url = WxpayConfig.UNIFIED_ORDER_URL;

			unifiedOrderRequest.setSign(null);
			String sign = Signature.getSign(unifiedOrderRequest.toMap());
			unifiedOrderRequest.setSign(sign);

			// 解决XStream对出现双下划线的bug
			XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
			xStreamForRequestPostData.alias("xml", UnifiedOrderRequest.class);
			// 将要提交给API的数据对象转换成XML格式数据Post给API
			String postDataXML = new String(xStreamForRequestPostData.toXML(unifiedOrderRequest).getBytes(), "iso-8859-1");
			Util.log(postDataXML);

			StringEntity entity = new StringEntity(postDataXML);
			String response = WxpayHttpUtil.httpsPost(url, entity);
			String temp = new String(response.getBytes("iso-8859-1"), "UTF-8");
			Util.log(temp);

			UnifiedOrderResponse unifiedOrderResponse = (UnifiedOrderResponse) Util.getObjectFromXML(temp, UnifiedOrderResponse.class);
			return unifiedOrderResponse;
		} catch (Exception e) {
			throw e;
		}
	}

	public UnifiedOrderCallbackResponse response(String data) throws ParserConfigurationException, IOException, SAXException {
		UnifiedOrderCallbackResponse unifiedOrderCallbackResponse = new UnifiedOrderCallbackResponse();
		unifiedOrderCallbackResponse.setReturn_code(
				Signature.checkIsSignValidFromResponseString(data) ? WxpayConfig.RETURN_CODE_SUCCESS : WxpayConfig.RETURN_CODE_FAIL);
		return unifiedOrderCallbackResponse;
	}
}
