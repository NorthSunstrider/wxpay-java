package com.tronsis.wxpay.util;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class WxpayHttpUtil {
	public static String httpsPost(String url, HttpEntity data) throws ParseException, IOException {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost httpost = new HttpPost(url);
		httpost.setEntity(data);
		try {
			HttpResponse response = client.execute(httpost);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				return EntityUtils.toString(entity);
			} else
				return null;
		} finally {
			client.close();
		}
	}

	public static String httpsGet(String url) throws ParseException, IOException {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(url);
		try {
			HttpResponse response = client.execute(httpget);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				return EntityUtils.toString(entity);
			} else
				return null;
		} finally {
			client.close();
		}
	}
}
