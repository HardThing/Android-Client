package com.android.hardthings.shetuanplus.login;

import com.android.hardthings.shetuanplus.BaseActionBarActivity;
import com.android.hardthings.shetuanplus.CommonUtil;
import com.android.hardthings.shetuanplus.MainActivity;
import com.android.hardthings.shetuanplus.R;
import com.android.hardthings.shetuanplus.http.DataRequestController;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FindPasswdActivity extends BaseActionBarActivity 
implements OnClickListener,LoginRequestCallback{

	public static final String TAG = "FindPasswdActivity";
	
	EditText mAccountEt = null;
	EditText mCapEt = null;
	Button mGetCapBtn = null;
	Button mCommitBtn = null;
	
	String mAccount = null;
	String mCap = null;

	Context mContext = null;

	private static final int STOPWATCH_WHAT = 1;
	private static final int STOPWATCH_DURATION = 1000;
	private static final int STOPWATCH_MAX_SECOND = 60;
	private int mCapTimeoutSecond = STOPWATCH_MAX_SECOND;

	String stopwatchUnit = null;

	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg) {
			mCapTimeoutSecond--;
			if(0 <= mCapTimeoutSecond){
				mGetCapBtn.setText(mCapTimeoutSecond + stopwatchUnit);
				mHandler.sendEmptyMessageDelayed(STOPWATCH_WHAT, STOPWATCH_DURATION);
			}else{
				mCapTimeoutSecond = STOPWATCH_MAX_SECOND;
				mGetCapBtn.setText(R.string.reg_get_captcha);
				mGetCapBtn.setEnabled(true);
			}
		};
	};

	LoginRequestController mLoginRequestController = null;	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate()");
		mContext = this;
		stopwatchUnit = mContext.getString(R.string.reg_get_captcha_stopwatch_unit);
		mLoginRequestController = new LoginRequestController(mContext,TAG,this);
		initView();
	}

	private void initView(){
		Log.d(TAG, "initView()");
		mAccountEt =  (EditText)findViewById(R.id.find_passwd_account_edt);
		mCapEt = (EditText)findViewById(R.id.find_passwd_captcha_edt);
		mGetCapBtn =  (Button)findViewById(R.id.find_passwd_get_captcha_btn);
		mCommitBtn =  (Button)findViewById(R.id.find_passwd_commit_btn);

		mGetCapBtn.setOnClickListener(this);
		mCommitBtn.setOnClickListener(this);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.d(TAG, "onDestroy()");
		DataRequestController.getInstance(mContext).cancelPendingRequests(TAG);
		removeCapStopwitch();
	}

	private void startCapStopwitch(){
		mHandler.sendEmptyMessageDelayed(STOPWATCH_WHAT, STOPWATCH_DURATION);
	}

	private void removeCapStopwitch(){
		mHandler.removeMessages(STOPWATCH_WHAT);
	}

	private void hideSoftInput(){
		Log.d(TAG, "hideSoftInput()");	
		View view = getCurrentFocus();
		Log.d(TAG, "hideSoftInput() view = " + view);	
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		if(null != view){
			imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
		}
	}

	private boolean isAccountLigal(){
		Log.d(TAG, "isAccountLigal()");
		mAccount = mAccountEt.getText().toString();
		if(TextUtils.isEmpty(mAccount)){
			Log.d(TAG, "isAllInputLegal() login_error_username_empty");
			CommonUtil.makeToast(mContext, R.string.login_error_username_empty);
			return false;
		}

		boolean isPhone = LoginUtil.isPhoneNumberLegal(mAccount);
		boolean isEmail = LoginUtil.isEmailLegal(mAccount);

		if(!isPhone & !isEmail){
			Log.d(TAG, "isAllInputLegal() login_error_username_illegal");
			CommonUtil.makeToast(mContext, R.string.login_error_username_illegal);
			return false;
		}
		return  true;
	}
	
	private boolean isAllInputLegal(){
		Log.d(TAG, "isAllInputLegal()");

		mAccount = mAccountEt.getText().toString();
		if(TextUtils.isEmpty(mAccount)){
			Log.d(TAG, "isAllInputLegal() login_error_username_empty");
			CommonUtil.makeToast(mContext, R.string.login_error_username_empty);
			return false;
		}

		boolean isPhone = LoginUtil.isPhoneNumberLegal(mAccount);
		boolean isEmail = LoginUtil.isEmailLegal(mAccount);

		if(!isPhone & !isEmail){
			Log.d(TAG, "isAllInputLegal() login_error_username_illegal");
			CommonUtil.makeToast(mContext, R.string.login_error_username_illegal);
			return false;
		}

		mCap = mCapEt.getText().toString();
		if(TextUtils.isEmpty(mCap)){
			Log.d(TAG, "isAllInputLegal() reg_error_captcha_empty");
			CommonUtil.makeToast(mContext, R.string.reg_error_captcha_empty);
			return false;
		}		
		if(!LoginUtil.isCapIegal(mCap)){
			Log.d(TAG, "isAllInputLegal() reg_error_captcha_illegal");
			CommonUtil.makeToast(mContext, R.string.reg_error_captcha_illegal);
			return false;
		}

		return  true;
	}
	
	private void getCap(String account){
		mLoginRequestController.getCap(account);;
	}

	private void findPasswd(String account,String cap){
		mLoginRequestController.findPasswd(account,cap);;
	}

	private void startResetPassedActivity(){
		Intent i = new Intent(this,ResetPasswdActivity.class);
		i.putExtra(LoginUtil.KEY_ACCOUNT, mAccount);
		startActivity(i);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		int id = arg0.getId();
		switch(id){

		case R.id.find_passwd_get_captcha_btn:
			Log.d(TAG, "onClick() get captcha");
			if(isAccountLigal()){
				getCap(mAccount);

			}

			break;

		case R.id.find_passwd_commit_btn:	
			Log.d(TAG, "onClick() commit ");
			hideSoftInput();
			if(isAllInputLegal()){
				findPasswd(mAccount,mCap);								
			}

			break;

		default:
			break;
		}

	}
	
	@Override
	protected int getTitleTextResource() {
		// TODO Auto-generated method stub
		return R.string.find_passwd;
	}

	@Override
	protected int getContentLayoutResource() {
		// TODO Auto-generated method stub
		return R.layout.find_passwd_activity;
	}
	
	@Override
	public void onAccountAvailableSuccess(boolean isAvailable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAccountAvailableFailed(String error) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGetCapSuccess(String captcha) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onGetCapSuccess() captcha = " + captcha);
		startCapStopwitch();
		mGetCapBtn.setEnabled(false);
	}

	@Override
	public void onGetCapFailed(String error) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onGetCapFailed() error = " + error);
		CommonUtil.makeToast(mContext, error);
	}

	@Override
	public void onRegisterSuccess(boolean isSuccess) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRegisterFailed(String error) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoginSuccess(boolean isSuccess) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoginFailed(String error) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFindPasswdSuccess(boolean isSuccess) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onRegisterSuccess() isSuccess = " + isSuccess);
		if(isSuccess){
			startResetPassedActivity();
		}
	}

	@Override
	public void onFindPasswdFailed(String error) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onFindPasswdFailed() error = " + error);
		CommonUtil.makeToast(mContext, error);
	}

	@Override
	public void onResetPasswdSuccess(boolean isSuccess) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onResetPasswdFailed(String error) {
		// TODO Auto-generated method stub
		
	}

}
