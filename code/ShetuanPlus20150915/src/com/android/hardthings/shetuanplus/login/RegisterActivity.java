package com.android.hardthings.shetuanplus.login;


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

import com.android.hardthings.shetuanplus.BaseActionBarActivity;
import com.android.hardthings.shetuanplus.CommonUtil;
import com.android.hardthings.shetuanplus.MainActivity;
import com.android.hardthings.shetuanplus.R;
import com.android.hardthings.shetuanplus.http.DataRequestController;

public class RegisterActivity extends BaseActionBarActivity 
implements OnClickListener,LoginRequestCallback{

	public static final String TAG = "RegisterActivity";

	LinearLayout mRegLayout = null;
	TextView mPhoneTv = null;
	TextView mEmailTv = null;    
	EditText mAccountEt = null;
	EditText mPasswdEt = null;    
	EditText mConfirmPdEt = null;    
	EditText mCapEt = null;
	Button mGetCapBtn = null;
	Button mRegBtn = null;    

	public final static int TYPE_REG_PHONE = 0;
	public final static int TYPE_REG_EMAIL = 1;
	private int mCurRegType = TYPE_REG_PHONE;

	String mAccount = null;
	String mPasswd = null;
	String mConfirmPd = null;	
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
		mRegLayout = (LinearLayout)findViewById(R.id.reg_phone_email_ll);
		mPhoneTv =  (TextView)findViewById(R.id.reg_phone_tv);
		mEmailTv = (TextView)findViewById(R.id.reg_email_tv);
		mAccountEt =  (EditText)findViewById(R.id.reg_input_account_edt);
		mPasswdEt = (EditText)findViewById(R.id.reg_input_passwd_edt);
		mConfirmPdEt =  (EditText)findViewById(R.id.reg_confirm_passwd_edt);
		mCapEt = (EditText)findViewById(R.id.reg_input_captcha_edt);
		mGetCapBtn =  (Button)findViewById(R.id.reg_get_captcha_btn);
		mRegBtn =  (Button)findViewById(R.id.reg_register_btn);

		mPhoneTv.setOnClickListener(this);
		mEmailTv.setOnClickListener(this);
		mGetCapBtn.setOnClickListener(this);
		mRegBtn.setOnClickListener(this);

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
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		int id = arg0.getId();
		switch(id){

		case R.id.reg_phone_tv:
			Log.d(TAG, "onClick() rigister phone");
			if(TYPE_REG_PHONE != mCurRegType){
				mRegLayout.setBackgroundResource(R.drawable.phone_select);
				mAccountEt.setHint(R.string.reg_input_phone);
				mCurRegType = TYPE_REG_PHONE;
			}
			break;

		case R.id.reg_email_tv:
			Log.d(TAG, "onClick() rigister email");
			if(TYPE_REG_EMAIL != mCurRegType){
				mRegLayout.setBackgroundResource(R.drawable.email_select);
				mAccountEt.setHint(R.string.reg_input_email);
				mCurRegType = TYPE_REG_EMAIL;
			}
			break;

		case R.id.reg_get_captcha_btn:
			Log.d(TAG, "onClick() get captcha");
			if(isAccountLigal()){
				getCap(mAccount);

			}

			break;

		case R.id.reg_register_btn:	
			Log.d(TAG, "onClick() rigister ");
			hideSoftInput();
			if(isAllInputLegal()){
				checkAccountAvailable(mAccount);
			}

			break;

		default:
			break;
		}

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

		if(TYPE_REG_PHONE == mCurRegType){
			if(TextUtils.isEmpty(mAccount)){
				Log.d(TAG, "isAllInputLegal() reg_error_phone_empty");
				CommonUtil.makeToast(mContext, R.string.reg_error_phone_empty);
				return false;
			}			
			if(!LoginUtil.isPhoneNumberLegal(mAccount)){
				Log.d(TAG, "isAllInputLegal() reg_error_phone_illegal");
				CommonUtil.makeToast(mContext, R.string.reg_error_phone_illegal);
				return false;
			}

		}else if(TYPE_REG_EMAIL == mCurRegType){
			if(TextUtils.isEmpty(mAccount)){
				Log.d(TAG, "isAllInputLegal() reg_error_email_empty");
				CommonUtil.makeToast(mContext, R.string.reg_error_email_empty);
				return false;
			}			
			if(!LoginUtil.isEmailLegal(mAccount)){
				Log.d(TAG, "isAllInputLegal() reg_error_email_illegal");
				CommonUtil.makeToast(mContext, R.string.reg_error_email_illegal);
				return false;
			}
		}
		return  true;
	}

	private boolean isAllInputLegal(){
		Log.d(TAG, "isAllInputLegal()");
		mAccount = mAccountEt.getText().toString();

		if(TYPE_REG_PHONE == mCurRegType){
			if(TextUtils.isEmpty(mAccount)){
				Log.d(TAG, "isAllInputLegal() reg_error_phone_empty");
				CommonUtil.makeToast(mContext, R.string.reg_error_phone_empty);
				return false;
			}			
			if(!LoginUtil.isPhoneNumberLegal(mAccount)){
				Log.d(TAG, "isAllInputLegal() reg_error_phone_illegal");
				CommonUtil.makeToast(mContext, R.string.reg_error_phone_illegal);
				return false;
			}

		}else if(TYPE_REG_EMAIL == mCurRegType){
			if(TextUtils.isEmpty(mAccount)){
				Log.d(TAG, "isAllInputLegal() reg_error_email_empty");
				CommonUtil.makeToast(mContext, R.string.reg_error_email_empty);
				return false;
			}			
			if(!LoginUtil.isEmailLegal(mAccount)){
				Log.d(TAG, "isAllInputLegal() reg_error_email_illegal");
				CommonUtil.makeToast(mContext, R.string.reg_error_email_illegal);
				return false;
			}
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

	private void checkAccountAvailable(String account){
		mLoginRequestController.checkAccountAvailable(account);
	}	

	private void getCap(String account){
		mLoginRequestController.getCap(account);;
	}

	private void register(String account,String passed,String cap){
		mLoginRequestController.register(account, passed, cap);;
	}

	private void startHomePageActivity(){
		Intent i = new Intent(this,MainActivity.class);
		startActivity(i);
	}
	
	@Override
	protected int getTitleTextResource() {
		// TODO Auto-generated method stub
		return R.string.reg_register;
	}

	@Override
	protected int getContentLayoutResource() {
		// TODO Auto-generated method stub
		return R.layout.register_activity;
	}

	@Override
	public void onAccountAvailableSuccess(boolean isAvailable) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onAccountAvailableSuccess() isAvailable = " + isAvailable);
		if(isAvailable){
			register(mAccount,mPasswd,mCap);
		}else{
			CommonUtil.makeToast(mContext, R.string.reg_error_account_has_reigster);
		}
	}

	@Override
	public void onAccountAvailableFailed(String error) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onAccountAvailableFailed() error = " + error);
		CommonUtil.makeToast(mContext, error);
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
		Log.d(TAG, "onRegisterSuccess() isSuccess = " + isSuccess);
		if(isSuccess){
			startHomePageActivity();
		}
	}

	@Override
	public void onRegisterFailed(String error) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onRegisterFailed() error = " + error);
		CommonUtil.makeToast(mContext, error);
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
		
	}

	@Override
	public void onResetPasswdFailed(String error) {
		// TODO Auto-generated method stub
		
	}
}
