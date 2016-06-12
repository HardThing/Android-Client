package com.android.hardthings.shetuanplus.http;

import java.util.Map;

import org.json.JSONObject;

import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;

public abstract class BaseJsonRequest extends JsonObjectRequest {

    public static final String TAG = "BaseJsonRequest";
    public static final String BASEURL = "http://123.57.85.244:8080/com-shetuanplus-controller/api/v1";    
	
	public BaseJsonRequest(int method, String url, JSONObject jsonRequest,
			Listener<JSONObject> listener, ErrorListener errorListener) {
		super(method, url, jsonRequest, listener, errorListener);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getCacheKey() {
		// TODO Auto-generated method stub
		String key = buildCacheKey();
		if(!TextUtils.isEmpty(key)){
			return key;
		}
		return super.getCacheKey();
	}
	
	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		// TODO Auto-generated method stub
		Map<String, String> header = buildHeader();
		return header;
	}
	protected abstract String buildCacheKey();
	protected abstract Map<String, String> buildHeader();
}
