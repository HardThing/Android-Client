package com.android.hardthings.shetuanplus.http;

import android.content.Context;
import android.text.TextUtils;

import com.android.hardthings.shetuanplus.MyApplication;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;

public class DataRequestController {

	public static final String TAG = "DataRequestController";

	private RequestQueue mRequestQueue = null;

	private static DataRequestController sInstance = null;

	/**
	 * @return The Volley Request queue, the queue will be created if it is null
	 */
	public static DataRequestController getInstance(Context context) {
		// lazy initialize the request queue, the queue instance will be
		// created when it is accessed for the first time
		if (sInstance == null) {
			synchronized (DataRequestController.class) {
				if (sInstance == null) {
					sInstance = new DataRequestController(context);
				}
			}
		}
			
	return sInstance;
}

private DataRequestController(Context context) {

	mRequestQueue = Volley.newRequestQueue(context);
}
/**
 * Adds the specified request to the global queue, if tag is specified
 * then it is used else Default TAG is used.
 * 
 * @param req
 * @param tag
 */
public <T> void addToRequestQueue(Request<T> req, String tag) {
	// set the default tag if tag is empty
	req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);

	VolleyLog.d("Adding request to queue: %s", req.getUrl());

	mRequestQueue.add(req);
}

/**
 * Adds the specified request to the global queue using the Default TAG.
 * 
 * @param req
 * @param tag
 */
public <T> void addToRequestQueue(Request<T> req) {
	// set the default tag if tag is empty
	req.setTag(TAG);

	mRequestQueue.add(req);
}

/**
 * Cancels all pending requests by the specified TAG, it is important
 * to specify a TAG so that the pending/ongoing requests can be cancelled.
 * 
 * @param tag
 */
public void cancelPendingRequests(Object tag) {
	if (mRequestQueue != null) {
		mRequestQueue.cancelAll(tag);
	}
}
}
