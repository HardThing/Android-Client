package com.android.hardthings.shetuanplus.login;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.hardthings.shetuanplus.BaseActionBarActivity;
import com.android.hardthings.shetuanplus.CommonUtil;
import com.android.hardthings.shetuanplus.MainActivity;
import com.android.hardthings.shetuanplus.R;
import com.android.hardthings.shetuanplus.http.DataRequestController;

public class LoginActivity extends BaseActionBarActivity implements OnClickListener,LoginRequestCallback{

	public static final String TAG = "LoginActivity";


	EditText mAccountEt = null;
	EditText mPasswdEt = null;    
	Button mLoginBtn = null;    
	TextView mRegisterTv = null;
	TextView mForgetPasswdTv = null; 

	String mAccount = null;
	String mPasswd = null;

	Context mContext = null;

	LoginRequestController mLoginRequestController = null;	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate()");
		mContext = this;
		mLoginRequestController = new LoginRequestController(mContext,TAG,this);
		initView();
	}

	private void initView(){
		Log.d(TAG, "initView()");
		mAccountEt =  (EditText)findViewById(R.id.login_input_account_edt);
		mPasswdEt = (EditText)findViewById(R.id.login_input_passwd_edt);
		mLoginBtn =  (Button)findViewById(R.id.login_btn);
		mRegisterTv =  (TextView)findViewById(R.id.login_register_tv);
		mForgetPasswdTv = (TextView)findViewById(R.id.login_forget_passwd_tv);

		mRegisterTv.setOnClickListener(this);
		mForgetPasswdTv.setOnClickListener(this);
		mLoginBtn.setOnClickListener(this);

	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.d(TAG, "onPause()");
		DataRequestController.getInstance(mContext).cancelPendingRequests(TAG);
	}
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		int id = arg0.getId();
		switch(id){

		case R.id.login_btn:
			if(isAllInputLegal()){
				login(mAccount,mPasswd);				
			}

			break;

		case R.id.login_register_tv:
			startRegisterActivity();

			break;

		case R.id.login_forget_passwd_tv:
			startForgetPassedActivity();

			break;

		default:
			break;
		}

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

		mPasswd = mPasswdEt.getText().toString();
		if(TextUtils.isEmpty(mPasswd)){
			Log.d(TAG, "isAllInputLegal() reg_error_passwd_empty");
			CommonUtil.makeToast(mContext, R.string.reg_error_passwd_empty);
			return false;
		}		
		if(!LoginUtil.isPasswdIegal(mPasswd)){
			Log.d(TAG, "isAllInputLegal() reg_error_passwd_illegal");
			CommonUtil.makeToast(mContext, R.string.reg_error_passwd_illegal);
			return false;
		}

		return  true;
	}	

	private void login(String account,String passed){
		mLoginRequestController.login(account, passed);;
	}

	private void startRegisterActivity(){
		Intent i = new Intent(this,RegisterActivity.class);
		startActivity(i);
	}

	private void startForgetPassedActivity(){
		Intent i = new Intent(this,FindPasswdActivity.class);
		startActivity(i);
	}

	private void startHomePageActivity(){
		Intent i = new Intent(this,MainActivity.class);
		startActivity(i);
	}

	@Override
	protected int getTitleTextResource() {
		// TODO Auto-generated method stub
		return R.string.login;
	}

	@Override
	protected int getContentLayoutResource() {
		// TODO Auto-generated method stub
		return R.layout.login_activity;
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

	}

	@Override
	public void onGetCapFailed(String error) {
		// TODO Auto-generated method stub

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
		Log.d(TAG, "onLoginSuccess() isSuccess = " + isSuccess);
		if(isSuccess){
			startHomePageActivity();
		}
	}

	@Override
	public void onLoginFailed(String error) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onLoginFailed() error = " + error);
		CommonUtil.makeToast(mContext, error);
	}

	@Override
	public void onFindPasswdSuccess(boolean isSuccess) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFindPasswdFailed(String error) {
		// TODO Auto-generated method stub
		
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
