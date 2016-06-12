package com.android.hardthings.shetuanplus.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.hardthings.shetuanplus.BaseActionBarActivity;
import com.android.hardthings.shetuanplus.CommonUtil;
import com.android.hardthings.shetuanplus.MainActivity;
import com.android.hardthings.shetuanplus.R;
import com.android.hardthings.shetuanplus.http.DataRequestController;

public class ResetPasswdActivity extends BaseActionBarActivity 
implements OnClickListener,LoginRequestCallback{


	public static final String TAG = "ResetPasswdActivity";
	
	EditText mPasswdEt = null;    
	EditText mConfirmPdEt = null;
	Button mCommitBtn = null; 

	String mAccount = null;	
	String mPasswd = null;
	String mConfirmPd = null;	

	Context mContext = null;

	LoginRequestController mLoginRequestController = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate()");
		mContext = this;
		mLoginRequestController = new LoginRequestController(mContext,TAG,this);
		Intent i = getIntent();
		if(null != i){
			mAccount = i.getStringExtra(LoginUtil.KEY_ACCOUNT);
		}
		initView();
	}

	private void initView(){
		Log.d(TAG, "initView()");
		mPasswdEt = (EditText)findViewById(R.id.input_new_passed_edt);
		mConfirmPdEt =  (EditText)findViewById(R.id.input_new_passed_confirm_edt);
		mCommitBtn =  (Button)findViewById(R.id.new_passed_commit_btn);

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
	}

	private void resetPasswd(String account,String newPwd){
		mLoginRequestController.resetPasswd(account,newPwd);;
	}

	private void startHomePageActivity(){
		Intent i = new Intent(this,MainActivity.class);
		startActivity(i);
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
	
	private boolean isAllInputLegal(){
		Log.d(TAG, "isAllInputLegal()");

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

		mConfirmPd = mConfirmPdEt.getText().toString();
		if(TextUtils.isEmpty(mConfirmPd)){
			Log.d(TAG, "isAllInputLegal() reg_error_confirm_passwd_empty");
			CommonUtil.makeToast(mContext, R.string.reg_error_confirm_passwd_empty);
			return false;
		}		
		if(!mConfirmPd.equals(mPasswd)){
			Log.d(TAG, "isAllInputLegal() reg_error_confirm_passed_not_equal");
			CommonUtil.makeToast(mContext, R.string.reg_error_confirm_passed_not_equal);
			return false;
		}

		return  true;
	}	
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		int id = arg0.getId();
		switch(id){

		case R.id.new_passed_commit_btn:	
			Log.d(TAG, "onClick() commit ");
			hideSoftInput();
			if(isAllInputLegal()){
				resetPasswd(mAccount,mPasswd);								
			}

			break;

		default:
			break;
		}
	}

	@Override
	protected int getTitleTextResource() {
		// TODO Auto-generated method stub
		return R.string.set_new_passwd;
	}

	@Override
	protected int getContentLayoutResource() {
		// TODO Auto-generated method stub
		return R.layout.reset_passwd_activity;
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
		
	}

	@Override
	public void onLoginFailed(String error) {
		// TODO Auto-generated method stub
		
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
		Log.d(TAG, "onResetPasswdSuccess() isSuccess = " + isSuccess);
		if(isSuccess){
			startHomePageActivity();
		}
	}

	@Override
	public void onResetPasswdFailed(String error) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onResetPasswdFailed() error = " + error);
		CommonUtil.makeToast(mContext, error);
	}


}
