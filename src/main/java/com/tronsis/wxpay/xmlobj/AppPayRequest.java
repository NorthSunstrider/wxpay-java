package com.tronsis.wxpay.xmlobj;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class AppPayRequest {
	private String appid;
	private String partenerId;
	private String prepayId;
	private String packageValue;
	private String nonceStr;
	private String timeStamp;
	private String sign;

	public AppPayRequest() {
		super();
	}

	public AppPayRequest(String appid, String partenerId, String prepayId, String packageValue, String nonceStr, String timeStamp,
			String sign) {
		super();
		this.appid = appid;
		this.partenerId = partenerId;
		this.prepayId = prepayId;
		this.packageValue = packageValue;
		this.nonceStr = nonceStr;
		this.timeStamp = timeStamp;
		this.sign = sign;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getPartenerId() {
		return partenerId;
	}

	public void setPartenerId(String partenerId) {
		this.partenerId = partenerId;
	}

	public String getPrepayId() {
		return prepayId;
	}

	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
	}

	public String getPackageValue() {
		return packageValue;
	}

	public void setPackageValue(String packageValue) {
		this.packageValue = packageValue;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		Field[] fields = this.getClass().getDeclaredFields();
		for (Field field : fields) {
			Object obj;
			try {
				obj = field.get(this);
				if (obj != null) {
					map.put(field.getName(), obj);
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return map;
	}
}
