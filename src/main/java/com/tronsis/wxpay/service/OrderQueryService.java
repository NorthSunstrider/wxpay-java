package com.tronsis.wxpay.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.ParseException;
import org.apache.http.entity.StringEntity;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.tronsis.wxpay.util.Signature;
import com.tronsis.wxpay.util.Util;
import com.tronsis.wxpay.util.WxpayConfig;
import com.tronsis.wxpay.util.WxpayHttpUtil;
import com.tronsis.wxpay.xmlobj.OrderQueryRequest;
import com.tronsis.wxpay.xmlobj.OrderQueryResponse;

public class OrderQueryService {
	public OrderQueryResponse createQuery(String transactionId, String outTradeNo) throws ParseException, IOException {

		OrderQueryRequest orderQueryRequest = new OrderQueryRequest();
		orderQueryRequest.setAppid(WxpayConfig.APPID);
		orderQueryRequest.setMch_id(WxpayConfig.MCH_ID);
		orderQueryRequest.setTransaction_id(transactionId);
		orderQueryRequest.setOut_trade_no(outTradeNo);
		orderQueryRequest.setNonce_str(Util.getRandomString(8));

		String sign = Signature.getSign(orderQueryRequest.toMap());
		orderQueryRequest.setSign(sign);

		// 解决XStream对出现双下划线的bug
		XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
		xStreamForRequestPostData.alias("xml", OrderQueryRequest.class);
		// 将要提交给API的数据对象转换成XML格式数据Post给API
		String postDataXML = new String(xStreamForRequestPostData.toXML(orderQueryRequest).getBytes(), "iso-8859-1");
		System.out.println(postDataXML);

		StringEntity entity = new StringEntity(postDataXML);
		String response = WxpayHttpUtil.httpsPost(WxpayConfig.ORDER_QUERY_URL, entity);
		String temp = new String(response.getBytes("iso-8859-1"), "UTF-8");
		System.out.println(temp);

		OrderQueryResponse unifiedOrderResponse = (OrderQueryResponse) Util.getObjectFromXML(temp, OrderQueryResponse.class);
		System.out.println(unifiedOrderResponse.getReturn_code());

		return unifiedOrderResponse;

	}
}
