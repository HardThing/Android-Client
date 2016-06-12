package com.android.hardthings.shetuanplus;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public abstract class BaseActionBarActivity extends Activity{
	
	public static final String TAG = "BaseActionBarActivity";
	
	private FrameLayout mContentFl = null;
	private LinearLayout mBackButtonLL = null;
	private TextView mTitleTv = null;
	private Context mContext = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate()");
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.base_actionbar_activity);
		
		mContext = getBaseContext();
		initBaseView();
	}
	
	private void initBaseView(){
		Log.d(TAG, "initBaseView()");
		mTitleTv = (TextView)findViewById(R.id.base_actionbar_title_tv);
		mTitleTv.setText(getTitleTextResource());
		mContentFl = (FrameLayout)findViewById(R.id.base_actionbar_framelayout);
		mContentFl.removeAllViews();
		int contentLayout = getContentLayoutResource();
		View child = LayoutInflater.from(mContext).inflate(contentLayout, null);
		mContentFl.addView(child, FrameLayout.LayoutParams.MATCH_PARENT);
		
		mBackButtonLL = (LinearLayout)findViewById(R.id.base_actionbar_back_button_ll);
		mBackButtonLL.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	
	protected abstract int getTitleTextResource();
	
	protected abstract int getContentLayoutResource();

}
