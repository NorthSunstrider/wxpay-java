package com.tronsis.wxpay.xmlobj;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class UnifiedOrderCallbackResponse {
	private String return_code;
	private String return_msg;

	public UnifiedOrderCallbackResponse() {
		super();
	}

	public UnifiedOrderCallbackResponse(String return_code, String return_msg) {
		super();
		this.return_code = return_code;
		this.return_msg = return_msg;
	}

	public String getReturn_code() {
		return return_code;
	}

	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}

	public String getReturn_msg() {
		return return_msg;
	}

	public void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
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
