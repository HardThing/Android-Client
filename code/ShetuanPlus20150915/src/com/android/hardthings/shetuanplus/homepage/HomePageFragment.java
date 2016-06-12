package com.android.hardthings.shetuanplus.homepage;

import java.util.ArrayList;

import me.angeldevil.autoscrollviewpager.AutoScrollViewPager;

import com.android.hardthings.shetuanplus.CommonUtil;
import com.android.hardthings.shetuanplus.R;
import com.android.hardthings.shetuanplus.login.LoginActivity;
import com.android.hardthings.shetuanplus.refreshlistview.RefreshListViewHandler;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.viewpagerindicator.CirclePageIndicator;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hardthings.shetuanplus.database.STActivity;

public class HomePageFragment extends Fragment implements OnClickListener{

	public static final String TAG = "HomePageFragment";
	private String[] mAdList = {"http://h.hiphotos.baidu.com/image/w%3D1920%3Bcrop%3D0%2C0%2C1920%2C1080/sign=fed1392e952bd40742c7d7f449b9a532/e4dde71190ef76c6501a5c2d9f16fdfaae5167e8.jpg",
			"http://a.hiphotos.baidu.com/image/w%3D1920%3Bcrop%3D0%2C0%2C1920%2C1080/sign=25d477ebe51190ef01fb96d6fc2ba675/503d269759ee3d6df51a20cd41166d224e4adedc.jpg",
	"http://c.hiphotos.baidu.com/image/w%3D1920%3Bcrop%3D0%2C0%2C1920%2C1080/sign=70d2b81e60d0f703e6b291d53aca6a5e/0ff41bd5ad6eddc4ab1b5af23bdbb6fd5266333f.jpg"};

	private DisplayImageOptions mOptions = new DisplayImageOptions.Builder().cacheInMemory(true)
			.cacheOnDisk(true).imageScaleType(ImageScaleType.IN_SAMPLE_INT).build();

	private static final int AD_AUTO_SCROLL_DURATION = 2000;

	TextView mLoginTv = null;
	ImageView mMessageIv = null;
	ImageView mSearchIv = null;	

	AutoScrollViewPager mAdViewPager = null;
	CirclePageIndicator mAdPageIndicator = null;

	PullToRefreshListView mPTRListView = null;
	ListView mListView = null;
	HomePageActivityListViewHandler mHPALVHandler = null;

	private Toast toast;
	Context mContext = null;
	ImageLoader mImageLoader = null;

	private ArrayList<STActivity> mActivityList = null;

	public HomePageFragment(){
		Log.d(TAG, "HomePageFragment()");
		mActivityList = new ArrayList<STActivity>();
		for(int i = 0;i < CommonUtil.IMAGES.length; i++)
			mActivityList.add(new STActivity("activity",CommonUtil.IMAGES[i],"founder"));
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		Log.d(TAG, "onAttach()");	
		mContext = activity;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onCreateView()");	
		View rootView = inflater.inflate(R.layout.homepage_fragment, container, false);
		initView(rootView);
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	private void initView(View rootView){
		Log.d(TAG, "initView()");		
		initActionBar(rootView);
		initAdView(rootView);
		initListView(rootView);
	}

	private void initActionBar(View rootView){
		Log.d(TAG, "initActionBar()");	
		mLoginTv = (TextView)rootView.findViewById(R.id.homepage_actionbar_login_tv);
		mMessageIv = (ImageView)rootView.findViewById(R.id.homepage_actionbar_message_iv);
		mSearchIv = (ImageView)rootView.findViewById(R.id.homepage_actionbar_search_iv);
		mLoginTv.setOnClickListener(this);
		mMessageIv.setOnClickListener(this);
		mSearchIv.setOnClickListener(this);
	}

	private void initAdView(View rootView){
		Log.d(TAG, "initAdView()");	
		mImageLoader = ImageLoader.getInstance();
		mImageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
		mAdViewPager = (AutoScrollViewPager)rootView.findViewById(R.id.homepage_ad_scroll_pager);
		mAdPageIndicator = (CirclePageIndicator)rootView.findViewById(R.id.homepage_ad_indicator);

		mAdViewPager.setAdapter(new PagerAdapter() {
			@Override
			public int getCount() {
				return mAdList.length;
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				ImageView view = new ImageView(container.getContext());
				view.setScaleType(ImageView.ScaleType.CENTER_CROP);
				ImageLoader.getInstance().displayImage(mAdList[position], view, mOptions);
				container.addView(view);
				return view;
			}

			@Override
			public void destroyItem(ViewGroup container, int position, Object object) {
				container.removeView((View) object);
			}

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				// TODO Auto-generated method stub
				return arg0 == arg1;
			}
		});

		mAdPageIndicator.setViewPager(mAdViewPager);
		mAdPageIndicator.setSnap(true);

		mAdViewPager.setScrollFactgor(5);
		mAdViewPager.setOffscreenPageLimit(4);
		mAdViewPager.startAutoScroll(AD_AUTO_SCROLL_DURATION);
		mAdViewPager.setOnPageClickListener(new AutoScrollViewPager.OnPageClickListener() {
			@Override
			public void onPageClick(AutoScrollViewPager autoScrollPager, int position) {
				if (toast != null) {
					toast.cancel();
				}
				toast = Toast.makeText(mContext, "You clicked page: " + (position + 1),
						Toast.LENGTH_SHORT);
				toast.show();
			}
		});
	}

	private void initListView(View rootView){
		Log.d(TAG, "initListView()");	
		mPTRListView = (PullToRefreshListView)rootView.findViewById(R.id.homepage_pull_refresh_list);
		mHPALVHandler = new HomePageActivityListViewHandler(mContext,mPTRListView);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		int id = arg0.getId();

		switch(id){

		case R.id.homepage_actionbar_login_tv:
			Intent i = new Intent(mContext,LoginActivity.class);
			startActivity(i);
			break;

		case R.id.homepage_actionbar_message_iv:

			break;

		case R.id.homepage_actionbar_search_iv:

			break;

		default:
			break;
		}
	}

	private class HomePageActivityListViewHandler extends RefreshListViewHandler{

		public HomePageActivityListViewHandler(Context context,
				PullToRefreshListView refreshListView) {
			super(context, refreshListView);
			// TODO Auto-generated constructor stub
			Log.d(TAG, "HomePageActivityListViewHandler()");	
		}

		@Override
		public void onLastItemVisibleDoing() {
			// TODO Auto-generated method stub
			Log.d(TAG, "onLastItemVisibleDoing()");	
		}

		@Override
		public void onRefreshDoing() {
			// TODO Auto-generated method stub
			Log.d(TAG, "onRefreshDoing()");	
		}

		@Override
		public ListAdapter getAdapter() {
			// TODO Auto-generated method stub
			Log.d(TAG, "getAdapter()");	
			return new HomePageActivityAdapter(mContext,R.layout.homepage_activity_item,mActivityList);
		}

	}
}
