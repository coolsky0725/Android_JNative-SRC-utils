package com.fidku.jeloubeta.utils;

import com.fidku.jeloubeta.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Loader;
import android.os.Handler;
import android.util.Log;

public class AlerDialog {
	
	@SuppressWarnings("deprecation")
	static public void showalert(final int alertcount, final String title, final String message,final String alertstyle, final Context cxt) {
		AlertDialog alert = new AlertDialog.Builder(
                cxt).create();

		alert.setTitle(title);
		alert.setMessage(message);
		alert.setIcon(R.drawable.alerticon);
		alert.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
			
		             int alert_count = alertcount;
		            	if(alertcount>0)
	                	{
	                		alert_count--;
	                		AlerDialog.showalert(alert_count,title, message, alertstyle, cxt);
	                	}
		            	else
		            	{
		            		Log.d("style=", alertstyle);
		            		if(alertstyle.equals("app_boot"))
		            				timerapp_bootevent();
		            	}
            	}
		  
		});
		alert.show();
		
	}
	static public void timerapp_bootevent()
	{
		com.fidku.jeloubeta.Home.homeactivity.app_bootalertupdae();
	}
}
