package com.fidku.jeloubeta.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.regex.Pattern;

import org.json.JSONObject;

import com.fidku.jeloubeta.Home;
import com.fidku.jeloubeta.Jelou;
import com.fidku.jeloubeta.screens.Loader;
import com.fidku.jeloubeta.screens.Validar;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.Patterns;

@SuppressLint("NewApi")
public class Globals {
	static public Vector<Bitmap> sites = new Vector<Bitmap>();
	static public Vector<Bitmap> squares = new Vector<Bitmap>();
	static public Vector<Bitmap> browsetaps = new Vector<Bitmap>();
	static public Vector<String> browsetapurls = new Vector<String>();
	static public Vector<JSONObject>siteurlobject = new Vector<JSONObject>();
	static public Vector<JSONObject>siteadsobject = new Vector<JSONObject>();
	static public Vector<JSONObject>site_freq_alertobject = new Vector<JSONObject>();
	static public int site_freq_beforetime = 0;
	static public int nav_time_beforetime = 0;
	static public JSONObject getCongigObject = new JSONObject();
	static public JSONObject userUpdateStatusObject = new JSONObject();
	static public Boolean userUpdateStatusObjectflag = false;
	static public Vector<Integer> siteorder = new Vector<Integer>();
    static public Vector<Integer> squareorder = new Vector<Integer>();
    
    static public Vector<Bitmap> fixedfavoriteimages = new Vector<Bitmap>();
    static public Vector<String> fixedfavoriteimagesurl = new Vector<String>();
    
	static public String[] config = new String[8];
	static public String[] user = new String[5];
	static public String[] genders = {"Masculino", "Femenino"};
	static private TelephonyManager manager;
	static public Boolean browsetapgrid_homegrid_choiceflage = true;
	static public ArrayList<String> site_freq_siteid = new ArrayList<String>();
	
	static private TelephonyManager configTM() {
		if(manager == null) {
			manager = (TelephonyManager) Validar.validaract.getSystemService(Context.TELEPHONY_SERVICE);
		}
		
		return manager;
	}
    
	static public String getCarrier(){
		String carrierName = (configTM().getNetworkOperatorName().length() == 0) ? "Desconocido" : manager.getNetworkOperatorName();
		
		return carrierName;
	}
	
	static public String getCountry() {
		String codeCountry = (configTM().getSimCountryIso().length() == 0) ? "Desconocido" : manager.getSimCountryIso();
		
		return codeCountry;
	}
	
	static public String getEmailAccount(Activity act)
	{
		Pattern emailPattern = Patterns.EMAIL_ADDRESS;
		Account[] accounts = AccountManager.get(act).getAccounts();
		
		for(Account account : accounts) {
			if(emailPattern.matcher(account.name).matches())
				return account.name;
		}
		
		return null;
	}
	
	static public String getIMEI()
	{
		return configTM().getDeviceId();
	}
	
	static public String getUserAgent()
	{
		return System.getProperty("http.agent");
	}
	
	static public String getDeviceIP()
	{
		return Net.getIPAddress(true);
	}
	
	static public final boolean isValidEmail(CharSequence target) {
	    if (target == null) {
	        return false;
	    } else {
	        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
	    }
	}
	
	static public String getAndroidID()
	{
		String mmdid = Secure.getString(Home.homeactivity.getContentResolver(), Secure.ANDROID_ID);

		if(mmdid == null)
			return null;

		StringBuilder mmdidBuilder = new StringBuilder("mmh_");
		try
		{
			mmdidBuilder.append(Globals.MD5(mmdid));
			mmdidBuilder.append("_");
			mmdidBuilder.append(Globals.SHA1(mmdid));
		}
		catch(Exception e)
		{
			return null;
		}
		return mmdidBuilder.toString();
	}
	
	static public String getVersion() {
		return Build.VERSION.RELEASE;
	}
	
	static public String getModel() {
		return Build.MODEL;
	}
	
	static private String MD5(String md5) {
		   try {
		        java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
		        byte[] array = md.digest(md5.getBytes());
		        StringBuffer sb = new StringBuffer();
		        for (int i = 0; i < array.length; ++i) {
		          sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
		       }
		        return sb.toString().toUpperCase();
		    } catch (java.security.NoSuchAlgorithmException e) {
		    }
		    return null;
		}
	
	static private String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (byte b : data) {
            int halfbyte = (b >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                buf.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte) : (char) ('a' + (halfbyte - 10)));
                halfbyte = b & 0x0F;
            } while (two_halfs++ < 1);
        }
        
        return buf.toString().toUpperCase();
    }

	static private String SHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        byte[] sha1hash = md.digest();
        return convertToHex(sha1hash);
    }
	
	static public String getCurrentCountry(){
		if(configTM().getNetworkCountryIso().length() <= 0) return "null";
		
		return configTM().getNetworkOperator();
	}

	static public String getNetworkStatus(){
		String resp = "null";
		if(configTM().getNetworkCountryIso().length() <= 0) return "null";
		
		switch(configTM().getDataState()){
		case 0:	resp = "DATA_DISCONNECTED";
				break;
		case 1: resp = "DATA_CONNECTING";
				break;
		case 2: resp = "DATA_CONNECTED";
				break; 
		case 3: resp = "DATA_SUSPENDED";
				break;
		}
		
		return resp;
	}
	
	static public String getRoamingStatus(){
		if(configTM().getNetworkCountryIso().length() <= 0) return "null";
		
		if(configTM().isNetworkRoaming()){
			return "roaming_on";
		}else{
			return "roaming_off";
		}
	}
}
