package com.android.hardthings.shetuanplus.login;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.hardthings.shetuanplus.http.DataRequestController;
import com.android.hardthings.shetuanplus.http.ErrorResponseCallback;
import com.android.hardthings.shetuanplus.http.IRequestCallback;
import com.android.hardthings.shetuanplus.http.SuccessResponseCallback;
import com.android.hardthings.shetuanplus.login.LoginJsonRequest.RequestType;
import com.android.volley.Request.Method;

import android.content.Context;
import android.util.Log;

public class LoginRequestController {
	
	public static final String TAG = "LoginRequestController";
	
	Context mContext = null;
	LoginRequestCallback mCallback = null;
	DataRequestController mDataRequestController = null;
	LoginJsonRequest mLoginJsonRequest = null;
	String mTAG = null;
	public static final int CODE_REQUEST_OK = 200;
	
	public LoginRequestController(Context context,String tag,LoginRequestCallback callback){
		mContext = context;
		mTAG = tag;
		mCallback = callback;
		mDataRequestController = DataRequestController.getInstance(mContext);
	}

	public void checkAccountAvailable(String account){
		
		String url = LoginJsonRequest.getUrl(RequestType.ACCOUNT_AVAILABLE);
		url += "?account=" + account;
		IRequestCallback requestCallback = new IRequestCallback(){

			@Override
			public void requsetSuccess(JSONObject data) {
				// TODO Auto-generated method stub
				parseAccountAvailableData(data);
			}

			@Override
			public void requsetFailed(String error) {
				// TODO Auto-generated method stub
				Log.e(TAG, "checkAccountAvailable requsetFailed() error = " + error);
			}
			
		};
		
		SuccessResponseCallback sucCallback = new SuccessResponseCallback(requestCallback);
		ErrorResponseCallback errorCallback = new ErrorResponseCallback(requestCallback);
		
		LoginJsonRequest req = new LoginJsonRequest(Method.GET,url,null,sucCallback,errorCallback);
		mDataRequestController.addToRequestQueue(req,mTAG);
	}

	public void getCap(String account){
		
		String url = LoginJsonRequest.getUrl(RequestType.GET_CAP);
		url += "?account=" + account;
		IRequestCallback requestCallback = new IRequestCallback(){

			@Override
			public void requsetSuccess(JSONObject data) {
				// TODO Auto-generated method stub
				parseGetCapData(data);
			}

			@Override
			public void requsetFailed(String error) {
				// TODO Auto-generated method stub
				Log.e(TAG, "getCap requsetFailed() error = " + error);
			}
			
		};
		
		SuccessResponseCallback sucCallback = new SuccessResponseCallback(requestCallback);
		ErrorResponseCallback errorCallback = new ErrorResponseCallback(requestCallback);
		
		LoginJsonRequest req = new LoginJsonRequest(Method.GET,url,null,sucCallback,errorCallback);
		mDataRequestController.addToRequestQueue(req,mTAG);
	}

	public void register(String account,String passwd,String cap){
		
		JSONObject request = new JSONObject();
		try {
			request.put("account", account);
			request.put("password", passwd);
			request.put("code", cap);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.d(TAG, "register() JSONException e = " + e);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			Log.d(TAG, "register() Exception e = " + e);
		}
		String url = LoginJsonRequest.getUrl(RequestType.REGISTER);
		IRequestCallback requestCallback = new IRequestCallback(){

			@Override
			public void requsetSuccess(JSONObject data) {
				// TODO Auto-generated method stub
				parseRegisterData(data);
			}

			@Override
			public void requsetFailed(String error) {
				// TODO Auto-generated method stub
				Log.e(TAG, "register requsetFailed() error = " + error);
			}
			
		};
		
		SuccessResponseCallback sucCallback = new SuccessResponseCallback(requestCallback);
		ErrorResponseCallback errorCallback = new ErrorResponseCallback(requestCallback);
		Log.d(TAG, "register() request = " + request.toString());		
		LoginJsonRequest req = new LoginJsonRequest(Method.POST,url,request,sucCallback,errorCallback);
		mDataRequestController.addToRequestQueue(req,mTAG);
	}

	public void login(String account,String passwd){
		
		JSONObject request = new JSONObject();
		try {
			request.put("account", account);
			request.put("password", passwd);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.d(TAG, "login() JSONException e = " + e);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			Log.d(TAG, "login() Exception e = " + e);
		}
		String url = LoginJsonRequest.getUrl(RequestType.LOGIN);
		IRequestCallback requestCallback = new IRequestCallback(){

			@Override
			public void requsetSuccess(JSONObject data) {
				// TODO Auto-generated method stub
				parseLoginData(data);
			}

			@Override
			public void requsetFailed(String error) {
				// TODO Auto-generated method stub
				Log.e(TAG, "login requsetFailed() error = " + error);
			}
			
		};
		
		SuccessResponseCallback sucCallback = new SuccessResponseCallback(requestCallback);
		ErrorResponseCallback errorCallback = new ErrorResponseCallback(requestCallback);
		
		LoginJsonRequest req = new LoginJsonRequest(Method.POST,url,request,sucCallback,errorCallback);
		mDataRequestController.addToRequestQueue(req,mTAG);
	}

	public void findPasswd(String account,String cap){
		
		String url = LoginJsonRequest.getUrl(RequestType.FIND_PASSWD);
		url += "?account=" + account + "&code=" + cap;;
		IRequestCallback requestCallback = new IRequestCallback(){

			@Override
			public void requsetSuccess(JSONObject data) {
				// TODO Auto-generated method stub
				parseFindPasswdData(data);
			}

			@Override
			public void requsetFailed(String error) {
				// TODO Auto-generated method stub
				Log.e(TAG, "findPasswd requsetFailed() error = " + error);
			}
			
		};
		
		SuccessResponseCallback sucCallback = new SuccessResponseCallback(requestCallback);
		ErrorResponseCallback errorCallback = new ErrorResponseCallback(requestCallback);
		
		LoginJsonRequest req = new LoginJsonRequest(Method.GET,url,null,sucCallback,errorCallback);
		mDataRequestController.addToRequestQueue(req,mTAG);
	}
	

	public void resetPasswd(String account,String newPwd){
		
		if(null == account){
			Log.d(TAG, "resetPasswd()  account = null");			
		}
		
		JSONObject request = new JSONObject();
		try {
			request.put("account", account);
			request.put("newPwd", newPwd);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.d(TAG, "resetPasswd() JSONException e = " + e);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			Log.d(TAG, "resetPasswd() Exception e = " + e);
		}
		String url = LoginJsonRequest.getUrl(RequestType.RESET_PASSED);
		IRequestCallback requestCallback = new IRequestCallback(){

			@Override
			public void requsetSuccess(JSONObject data) {
				// TODO Auto-generated method stub
				parseResetPasswdData(data);
			}

			@Override
			public void requsetFailed(String error) {
				// TODO Auto-generated method stub
				Log.e(TAG, "resetPasswd requsetFailed() error = " + error);
			}
			
		};
		
		SuccessResponseCallback sucCallback = new SuccessResponseCallback(requestCallback);
		ErrorResponseCallback errorCallback = new ErrorResponseCallback(requestCallback);
		
		LoginJsonRequest req = new LoginJsonRequest(Method.POST,url,request,sucCallback,errorCallback);
		mDataRequestController.addToRequestQueue(req,mTAG);
	}
	
	private void parseAccountAvailableData(JSONObject data){
		Log.d(TAG, "parseAccountAvailableData() data = " + data);
		
		try {
			int code = data.getInt("code");
			if(CODE_REQUEST_OK == code){
				Boolean isAvailable = data.getBoolean("data");				
			    mCallback.onAccountAvailableSuccess(isAvailable);
				
			}else{
				String msg = data.getString("msg");
				mCallback.onAccountAvailableFailed(msg);
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.d(TAG, "parseAccountAvailableData() JSONException e = " + e);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			Log.d(TAG, "parseAccountAvailableData() Exception e = " + e);
		}
		
	}
	
	private void parseGetCapData(JSONObject data){
		Log.d(TAG, "parseGetCapData() data = " + data);
		
		try {
			int code = data.getInt("code");
			if(CODE_REQUEST_OK == code){
				String cap = data.getString("data");				
			    mCallback.onGetCapSuccess(cap);
				
			}else{
				String msg = data.getString("msg");
				mCallback.onGetCapFailed(msg);
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.d(TAG, "parseGetCapData() JSONException e = " + e);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			Log.d(TAG, "parseGetCapData() Exception e = " + e);
		}
		
	}
	
	private void parseRegisterData(JSONObject data){
		Log.d(TAG, "parseRegisterData() data = " + data);
		
		try {
			int code = data.getInt("code");
			if(CODE_REQUEST_OK == code){
				Boolean isSuccess = data.getBoolean("data");				
			    mCallback.onRegisterSuccess(isSuccess);
				
			}else{
				String msg = data.getString("msg");
				mCallback.onRegisterFailed(msg);
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.d(TAG, "parseRegisterData() JSONException e = " + e);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			Log.d(TAG, "parseRegisterData() Exception e = " + e);
		}
		
	}	
	
	private void parseLoginData(JSONObject data){
		Log.d(TAG, "parseLoginData() data = " + data);
		
		try {
			int code = data.getInt("code");
			if(CODE_REQUEST_OK == code){
				JSONObject loginInfo = data.getJSONObject("data");
				String userId  = loginInfo.getString("userId");
				String token  = loginInfo.getString("token");
			    mCallback.onLoginSuccess(true);
				
			}else{
				String msg = data.getString("msg");
				mCallback.onLoginFailed(msg);
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.d(TAG, "parseLoginData() JSONException e = " + e);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			Log.d(TAG, "parseLoginData() Exception e = " + e);
		}
		
	}

	private void parseFindPasswdData(JSONObject data){
		Log.d(TAG, "parseFindPasswdData() data = " + data);
		
		try {
			int code = data.getInt("code");
			if(CODE_REQUEST_OK == code){
				Boolean isAvailable = data.getBoolean("data");				
			    mCallback.onFindPasswdSuccess(isAvailable);
				
			}else{
				String msg = data.getString("msg");
				mCallback.onFindPasswdFailed(msg);
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.d(TAG, "parseFindPasswdData() JSONException e = " + e);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			Log.d(TAG, "parseFindPasswdData() Exception e = " + e);
		}
		
	}
	
	private void parseResetPasswdData(JSONObject data){
		Log.d(TAG, "parseResetPasswdData() data = " + data);
		
		try {
			int code = data.getInt("code");
			if(CODE_REQUEST_OK == code){
				Boolean isAvailable = data.getBoolean("data");				
			    mCallback.onResetPasswdSuccess(isAvailable);
				
			}else{
				String msg = data.getString("msg");
				mCallback.onResetPasswdFailed(msg);
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.d(TAG, "parseResetPasswdData() JSONException e = " + e);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			Log.d(TAG, "parseResetPasswdData() Exception e = " + e);
		}
		
	}
}
