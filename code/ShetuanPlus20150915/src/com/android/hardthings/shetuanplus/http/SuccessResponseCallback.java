package com.android.hardthings.shetuanplus.http;

import org.json.JSONObject;

import com.android.volley.Response.Listener;

public class SuccessResponseCallback implements Listener<JSONObject>{

	IRequestCallback mCallback = null;
	
	public SuccessResponseCallback(IRequestCallback callback){
		
		mCallback = callback;
	}

	@Override
	public void onResponse(JSONObject response) {
		// TODO Auto-generated method stub
		if(null != mCallback){
			mCallback.requsetSuccess(response);
		}
	}
}
