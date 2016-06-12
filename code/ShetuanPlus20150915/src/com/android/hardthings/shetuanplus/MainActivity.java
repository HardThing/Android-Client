package com.android.hardthings.shetuanplus;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.hardthings.shetuanplus.homepage.HomePageFragment;

public class MainActivity extends Activity implements OnClickListener{

	public static final String TAG = "MainActivity";

	private LinearLayout mPlazaLL = null;
	private ImageView mPlazaIV = null;
	private TextView mPlazaTV = null;
	
	private LinearLayout mActivityLL = null;
	private ImageView mActivityIV = null;
	private TextView mActivityTV = null;
	
	private LinearLayout mPublishLL = null;
	
	private LinearLayout mClubLL = null;
	private ImageView mClubIV = null;
	private TextView mClubTV = null;
	
	private LinearLayout mUserLL = null;
	private ImageView mUserIV = null;
	private TextView mUserTV = null;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate()");
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_activity);
		initView();
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.main_framelayout, new HomePageFragment())
                    .commit();
        }
	}

	private void initView(){
		Log.d(TAG, "initView()");
		mPlazaLL = (LinearLayout)findViewById(R.id.homepage_plaza_ll);
		mPlazaIV =  (ImageView)findViewById(R.id.homepage_plaza_iv);
		mPlazaTV = (TextView)findViewById(R.id.homepage_plaza_tv);
		
		mActivityLL = (LinearLayout)findViewById(R.id.homepage_activity_ll);
		mActivityIV =  (ImageView)findViewById(R.id.homepage_activity_iv);
		mActivityTV = (TextView)findViewById(R.id.homepage_activity_tv);
		
		mPublishLL = (LinearLayout)findViewById(R.id.homepage_publish_activity_ll);

		mClubLL = (LinearLayout)findViewById(R.id.homepage_club_ll);
		mClubIV =  (ImageView)findViewById(R.id.homepage_club_iv);
		mClubTV = (TextView)findViewById(R.id.homepage_club_tv);
		
		mUserLL = (LinearLayout)findViewById(R.id.homepage_user_ll);
		mUserIV =  (ImageView)findViewById(R.id.homepage_user_iv);
		mUserTV = (TextView)findViewById(R.id.homepage_user_tv);

		mPlazaLL.setOnClickListener(this);
		mActivityLL.setOnClickListener(this);
		mPublishLL.setOnClickListener(this);
		mClubLL.setOnClickListener(this);
		mUserLL.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		int id = arg0.getId();
        Resources res = getResources();
		switch(id){
			
		case R.id.homepage_plaza_ll:
			mPlazaIV.setBackgroundResource(R.drawable.homepage_plaza_selected);
			mPlazaTV.setTextColor(res.getColor(R.color.text_selected));
			mActivityIV.setBackgroundResource(R.drawable.homepage_activity);
			mActivityTV.setTextColor(res.getColor(R.color.text_unselected));
			mClubIV.setBackgroundResource(R.drawable.homepage_club);
			mClubTV.setTextColor(res.getColor(R.color.text_unselected));
			mUserIV.setBackgroundResource(R.drawable.homepage_user);
			mUserTV.setTextColor(res.getColor(R.color.text_unselected));
			break;
			
		case R.id.homepage_activity_ll:
			mPlazaIV.setBackgroundResource(R.drawable.homepage_plaza);
			mPlazaTV.setTextColor(res.getColor(R.color.text_unselected));
			mActivityIV.setBackgroundResource(R.drawable.homepage_activity_selected);
			mActivityTV.setTextColor(res.getColor(R.color.text_selected));
			mClubIV.setBackgroundResource(R.drawable.homepage_club);
			mClubTV.setTextColor(res.getColor(R.color.text_unselected));
			mUserIV.setBackgroundResource(R.drawable.homepage_user);
			mUserTV.setTextColor(res.getColor(R.color.text_unselected));
			break;
			
		case R.id.homepage_publish_activity_ll:

			break;
			
		case R.id.homepage_club_ll:
			mPlazaIV.setBackgroundResource(R.drawable.homepage_plaza);
			mPlazaTV.setTextColor(res.getColor(R.color.text_unselected));
			mActivityIV.setBackgroundResource(R.drawable.homepage_activity);
			mActivityTV.setTextColor(res.getColor(R.color.text_unselected));
			mClubIV.setBackgroundResource(R.drawable.homepage_club_selected);
			mClubTV.setTextColor(res.getColor(R.color.text_selected));
			mUserIV.setBackgroundResource(R.drawable.homepage_user);
			mUserTV.setTextColor(res.getColor(R.color.text_unselected));
			break;
			
		case R.id.homepage_user_ll:
			mPlazaIV.setBackgroundResource(R.drawable.homepage_plaza);
			mPlazaTV.setTextColor(res.getColor(R.color.text_unselected));
			mActivityIV.setBackgroundResource(R.drawable.homepage_activity);
			mActivityTV.setTextColor(res.getColor(R.color.text_unselected));
			mClubIV.setBackgroundResource(R.drawable.homepage_club);
			mClubTV.setTextColor(res.getColor(R.color.text_unselected));
			mUserIV.setBackgroundResource(R.drawable.homepage_user_selected);
			mUserTV.setTextColor(res.getColor(R.color.text_selected));
			break;
			
		default:
			break;
		}
	}

}
