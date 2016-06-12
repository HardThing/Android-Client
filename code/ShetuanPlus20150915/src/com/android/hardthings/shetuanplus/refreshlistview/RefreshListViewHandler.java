package com.android.hardthings.shetuanplus.refreshlistview;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public abstract class RefreshListViewHandler implements OnRefreshListener<ListView>,OnLastItemVisibleListener,OnItemClickListener {

	public static final String TAG = "RefreshListViewHandler";
	
	private Context mContext = null;
	private PullToRefreshListView mPullRefreshListView = null;
	private ListView mActualListView = null;
	private ListAdapter mAdapter = null;
	
	public RefreshListViewHandler(Context context,PullToRefreshListView refreshListView){
		Log.d(TAG, "RefreshListViewHandler()");
		mContext = context;
		mPullRefreshListView = refreshListView;
		mPullRefreshListView.setOnRefreshListener(this);
		mPullRefreshListView.setOnLastItemVisibleListener(this);
		
		mActualListView = mPullRefreshListView.getRefreshableView();
		
		mAdapter = getAdapter();
		mActualListView.setAdapter(mAdapter);
		mActualListView.setOnItemClickListener(this);
	}
	
	public void refreshComplete() {
		Log.d(TAG, "refreshComplete()");
		// TODO Auto-generated method stub
		mPullRefreshListView.onRefreshComplete();
	}
	
	@Override
	public void onLastItemVisible() {
		// TODO Auto-generated method stub
		onLastItemVisibleDoing();
	}

	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
		String label = DateUtils.formatDateTime(mContext, System.currentTimeMillis(),
				DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

		// Update the LastUpdatedLabel
		refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

		// Do work to refresh the list here.
		onRefreshDoing();
	}

	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		
	}
	
	public abstract void onLastItemVisibleDoing();
	
	public abstract void onRefreshDoing();
	
	public abstract ListAdapter getAdapter();
}
