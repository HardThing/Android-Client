package com.android.hardthings.shetuanplus.login;

public interface LoginRequestCallback {
	
	public void onAccountAvailableSuccess(boolean isAvailable);
	
	public void onAccountAvailableFailed(String error);
	
	public void onGetCapSuccess(String captcha);
	
	public void onGetCapFailed(String error);
	
	public void onRegisterSuccess(boolean isSuccess);
	
	public void onRegisterFailed(String error);
	
	public void onLoginSuccess(boolean isSuccess);
	
	public void onLoginFailed(String error);

	public void onFindPasswdSuccess(boolean isSuccess);
	
	public void onFindPasswdFailed(String error);

	public void onResetPasswdSuccess(boolean isSuccess);
	
	public void onResetPasswdFailed(String error);
}
