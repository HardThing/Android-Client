package com.android.hardthings.shetuanplus.homepage;

import java.util.ArrayList;
import java.util.List;

import com.android.hardthings.shetuanplus.R;
import com.android.hardthings.shetuanplus.database.STActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HomePageActivityAdapter extends ArrayAdapter<STActivity>{

	public static final String TAG = "HomePageActivityAdapter";

	public static final int COLNUM = 2;

	private ArrayList<STActivity> mActivityList = null;

	private LayoutInflater mLayoutInflater = null;

	private int mResourceId;

	View.OnClickListener mOnClickListener = null;

	private DisplayImageOptions mOptions;

	public HomePageActivityAdapter(Context context, int resource,
			List<STActivity> objects) {
		super(context, resource, objects);
		Log.d(TAG, "HomePageActivityAdapter()");
		// TODO Auto-generated constructor stub
		mActivityList = (ArrayList<STActivity>)objects;
		mLayoutInflater = LayoutInflater.from(context);
		mResourceId = resource;
		mOptions = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.homepage_activity)
		.showImageForEmptyUri(R.drawable.homepage_activity)
		.showImageOnFail(R.drawable.homepage_activity)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(true)
		.build();
	}

	@Override
	public int getCount() {
		Log.d(TAG, "getCount() mActivityList.size() = " + mActivityList.size() );
		// TODO Auto-generated method stub
		int row = mActivityList.size() / COLNUM;
		int remain = mActivityList.size() % COLNUM;
		int count = row + ( remain>0?1:0 );
		Log.d(TAG, "getCount() count = " + count );
		return count;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Log.d(TAG, "getView() convertView = " + convertView + ",position = " + position);
		ViewHolder holder;
		View view = mLayoutInflater.inflate(mResourceId,parent, false);
		holder = new ViewHolder();
		holder.leftLl = (LinearLayout)view.findViewById(R.id.homepage_activity_left_ll);
		holder.leftTitleTv = (TextView)view.findViewById(R.id.homepage_activity_left_title_tv);
		holder.leftLogoIv = (ImageView)view.findViewById(R.id.homepage_activity_left_logo_iv);
		holder.leftFounderTv = (TextView)view.findViewById(R.id.homepage_activity_left_founder_tv);

		holder.rightLl = (LinearLayout)view.findViewById(R.id.homepage_activity_right_ll);
		holder.rightTitleTv = (TextView)view.findViewById(R.id.homepage_activity_right_title_tv);
		holder.rightLogoIv = (ImageView)view.findViewById(R.id.homepage_activity_right_logo_iv);
		holder.rightFounderTv = (TextView)view.findViewById(R.id.homepage_activity_right_founder_tv);


		if(mActivityList.size() > position * COLNUM){
			STActivity activity = mActivityList.get(position * COLNUM);

			holder.leftLl.setOnClickListener(mOnClickListener);
			holder.leftTitleTv.setText(activity.title +(position * COLNUM + 1));
			holder.leftFounderTv.setText(activity.founder);
			ImageLoader.getInstance().displayImage(activity.logoUrl, holder.leftLogoIv, mOptions);
		}

		if(mActivityList.size() > position * COLNUM + 1){
			STActivity activity = getItem(position * COLNUM + 1);

			holder.rightLl.setOnClickListener(mOnClickListener);
			holder.rightTitleTv.setText(activity.title +(position * COLNUM + 2));
			holder.rightFounderTv.setText(activity.founder);
			ImageLoader.getInstance().displayImage(activity.logoUrl, holder.rightLogoIv, mOptions);
		}

		return view;
	}

	public void setOnClickListener(View.OnClickListener listener){
		Log.d(TAG, "setOnClickListener()");
		mOnClickListener = listener;
	}

	private class ViewHolder{
		LinearLayout leftLl;
		TextView leftTitleTv;
		ImageView leftLogoIv;
		TextView leftFounderTv;	

		LinearLayout rightLl;
		TextView rightTitleTv;
		ImageView rightLogoIv;
		TextView rightFounderTv;	
	}
}
