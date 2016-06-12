package com.android.hardthings.shetuanplus.http;

import org.json.JSONObject;

public interface IRequestCallback {

	public void requsetSuccess(JSONObject data);
	
	public void requsetFailed(String error);
}
