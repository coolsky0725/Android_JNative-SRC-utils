package com.fidku.jeloubeta.utils;


import java.util.Calendar;

import com.fidku.jeloubeta.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.CountDownTimer;


public class NavTimeDialog {

	static CountDownTimer mytimer;
	static public void shownavtimedialog(final int nav_time_interval, final int nav_time_min_times,final String nav_time_title,final String nav_time_body,final Context cxt)
	{
		   	 
		    mytimer =new CountDownTimer(nav_time_interval, 1000) {
			public void onTick(long millisUntilFinished) {
				
	         }

	         public void onFinish() {
	        	 Calendar c = Calendar.getInstance(); 
     	   	     final int currentminute =c.get(Calendar.MINUTE);
     	   	     
     	   	     
	   	         if(Globals.nav_time_beforetime == 0)
	   	         {
			        	 AlertDialog.Builder alert = new AlertDialog.Builder(cxt);
			        	 alert.setTitle(nav_time_title);
			     		 alert.setMessage(nav_time_body);
			     		 alert.setIcon(R.drawable.alerticon);
			     	     alert.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
			     		  public void onClick(DialogInterface dialog, int whichButton) {
			     		
			     	   	    	Globals.nav_time_beforetime = currentminute;
			     	   	    	NavTimeDialog.shownavtimedialog(nav_time_interval, nav_time_min_times, nav_time_title, nav_time_body, cxt);
			     	   	   
			     		  }
			     		});
			    		alert.show();
	   	         }
	   	         else
	   	         {
	   	        	 int differentminutes = Globals.nav_time_beforetime-currentminute;
		   	         differentminutes = (differentminutes < 0) ? -differentminutes : differentminutes;
		   	         if(differentminutes>=nav_time_min_times)
		   	         {
		   	        	 AlertDialog.Builder alert = new AlertDialog.Builder(cxt);
			        	 alert.setTitle(nav_time_title);
			     		 alert.setMessage(nav_time_body);
			     		 alert.setIcon(R.drawable.alerticon);
			     	     alert.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
			     		  public void onClick(DialogInterface dialog, int whichButton) {
			     		
			     	   	    	Globals.nav_time_beforetime = currentminute;
			     	   	    	NavTimeDialog.shownavtimedialog(nav_time_interval, nav_time_min_times, nav_time_title, nav_time_body, cxt);
			     	   	   
			     		  }
			     		});
			    		alert.show();
		   	         }
		   	         else
		   	        	 NavTimeDialog.shownavtimedialog(nav_time_interval, nav_time_min_times, nav_time_title, nav_time_body, cxt);
	   	         }
	         }
	          }.start();

	}
	static public void removetimer()
	{
		mytimer.cancel();
		mytimer = null;
	}
}
