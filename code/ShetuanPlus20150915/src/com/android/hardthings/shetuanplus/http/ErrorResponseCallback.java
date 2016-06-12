package com.android.hardthings.shetuanplus.http;

import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;


public class ErrorResponseCallback implements ErrorListener{

	IRequestCallback mCallback = null;
	
	public ErrorResponseCallback(IRequestCallback callback){
		
		mCallback = callback;
	}

	@Override
	public void onErrorResponse(VolleyError error) {
		// TODO Auto-generated method stub
		if(null != mCallback){
			mCallback.requsetFailed(error.toString());;
		}
	}

}
