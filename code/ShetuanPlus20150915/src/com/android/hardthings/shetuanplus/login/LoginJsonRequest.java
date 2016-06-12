package com.android.hardthings.shetuanplus.login;

import org.json.JSONObject;

import com.android.hardthings.shetuanplus.http.BaseJsonRequest;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;

public class LoginJsonRequest extends BaseJsonRequest{

	private String mCacheKey = null;
	
	public LoginJsonRequest(int method, String url, JSONObject jsonRequest,
			Listener<JSONObject> listener, ErrorListener errorListener) {
		super(method, url, jsonRequest, listener, errorListener);
		// TODO Auto-generated constructor stub
		String key = null;
		if(null != jsonRequest){
			key = url + "/" + jsonRequest.toString();
		}else{
			key = url;
		}
		
		mCacheKey = String.valueOf(key.hashCode());
	}
	
	public enum RequestType{
		ACCOUNT_AVAILABLE,
		GET_CAP,
		REGISTER,
		LOGIN,
		FIND_PASSWD,
		RESET_PASSED
	}

	@Override
	protected String buildCacheKey() {
		// TODO Auto-generated method stub
		return mCacheKey;
	}
	
	public static String getUrl(RequestType type){
		String url = null;
		
		switch(type){
		case ACCOUNT_AVAILABLE:
			url = BASEURL + "/accounts/register/available";
			break;
		
		case GET_CAP:
			url = BASEURL + "/accounts/verificationCode";
			break;		
		
		case REGISTER:
			url = BASEURL + "/accounts/register";
			break;		
		
		case LOGIN:
			url = BASEURL + "/accounts/login";
			break;		

		case FIND_PASSWD:
			url = BASEURL + "/accounts/verifiedCode";
			break;		
		
		case RESET_PASSED:
			url = BASEURL + "/accounts/login/password";
			break;	
			
		default:
			break;		
		
		}
		
		return url;
	}
}
