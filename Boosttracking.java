package com.fidku.jeloubeta.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.util.Log;

public class Boosttracking{

	static Context boostcxt;
	static public void countdownremaintime(final int remaintime,final Context cxt)
	{
	     
    		new CountDownTimer(remaintime, 1000) {
			public void onTick(long millisUntilFinished) {
				
	         }

	         public void onFinish() {
	        	 boostcxt = cxt;
	        	 new Boosttracking().new LoadBoostData().execute();
	         }
	     }.start();

	}
	
	static public JSONObject boostresponse()
	{
		JSONObject obj;
		try {
			//obj = new JSONObject(API.getURL(Globals.config[5] + "/getAppConfig?pcs=" + Globals.config[7]  + "&country=" + Globals.getCountry()+ "&carrier=" + Globals.getCarrier()));
			obj = new JSONObject(Boosttracking.getboostURL("http://akahanga.jelou.com/api/updateUserStatus?pcs=56992001083"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return obj;
	}
	
	static private String getboostURL(String url)
	{
	  String str = null;
  	  
  	  try {
  	    HttpClient httpclient = new DefaultHttpClient();
  	    HttpGet hget = new HttpGet(url);
  	    Log.d("url", url);
  	    hget.setHeader("Content-type", "application/json");
  	    HttpResponse response = httpclient.execute(hget);
  	    str = getString(response.getEntity().getContent());
  	  } catch (Exception e) {
  	    Log.i("[GET REQUEST]", "Network exception");
  	  }
  	  Log.d("str = ", str);
  	  
  	  return str;
	}
	
	 static private String getString(InputStream is) {
	        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	        StringBuilder sb = new StringBuilder();

	        String line = null;
	        try {
	            while ((line = reader.readLine()) != null) {
	                sb.append(line + "\n");
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                is.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        return sb.toString();
	        
	    }
	 
	 class LoadBoostData extends AsyncTask<Void, Void, Void> 
	    {
		 
		 	String responsemessage="";
		 	String responsetitle="";
	        protected void onPreExecute() 
	        {
	                //   dialog = ProgressDialog.show(cxt, "Loading...", null);
	        }
	        protected Void doInBackground(Void... unused) 
		    {
	        	 JSONObject boostjsonobject = boostresponse();
	        	 try {
	        		
					boostjsonobject = boostjsonobject.getJSONObject("trans");
					Globals.userUpdateStatusObject = boostjsonobject.getJSONObject("phones");
	        		Globals.userUpdateStatusObjectflag = true ;
	        		
					boostjsonobject = boostjsonobject.getJSONObject("msg");
					
					responsemessage = boostjsonobject.getString("body");
					responsetitle = boostjsonobject.getString("title");
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	return null;
		    }
	        protected void onProgressUpdate(Void... unused) 
	        {
	        	
	        }

	        protected void onPostExecute(Void unused) 
	        {
	        	AlertDialog.Builder alert = new AlertDialog.Builder(boostcxt);

				alert.setTitle(responsetitle);
				alert.setMessage(responsemessage);
				
				alert.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
				  public void onClick(DialogInterface dialog, int whichButton) {
					  
				  }
				});
				alert.show();
	        }
	    }
	 
	 
	 
}
