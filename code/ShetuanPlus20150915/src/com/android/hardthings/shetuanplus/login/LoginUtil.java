package com.android.hardthings.shetuanplus.login;

import java.util.regex.Matcher;
import java.util.regex.Pattern; 

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class LoginUtil {
	
	public static final String TAG = "LoginUtil";

	public static final String KEY_ACCOUNT = "user_account";
	
 /**
  * 验证邮箱
  * @param email
  * @return
  */
	public final static int PASSWD_MIN = 6;
	public final static int PASSWD_MAX = 20;
	public final static int CAP_LENGTH = 6;
	
 public static boolean isEmailLegal(String email){
  boolean flag = false;
  try{
    String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    Pattern regex = Pattern.compile(check);
    Matcher matcher = regex.matcher(email);
    flag = matcher.matches();
   }catch(Exception e){
    flag = false;
   }
  return flag;
 }

 /**
  * 验证手机号码
  * @param mobiles
  * @return
  */
 public static boolean isPhoneNumberLegal(String mobileNumber){
  boolean flag = false;
  try{
    Pattern regex = Pattern.compile("^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
    Matcher matcher = regex.matcher(mobileNumber);
    flag = matcher.matches();
   }catch(Exception e){
    flag = false;
   }
  return flag;
 }

 public static boolean isPasswdIegal(String passwd){
	  boolean flag = false;
	  int length = passwd.length();
		//Log.d(TAG, "isPasswdIegal() length = " + length);
	  if(PASSWD_MIN <= length && PASSWD_MAX >= length ){
		  flag = true;
	  }
	  return flag;
	 } 
 
 public static boolean isCapIegal(String cap){
	  boolean flag = false;
	  int length = cap.length();
		//Log.d(TAG, "isPasswdIegal() length = " + length);
	  if(CAP_LENGTH == length){
		  flag = true;
	  }
	  return flag;
	 } 
 

}
