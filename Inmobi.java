package com.fidku.jeloubeta.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


import org.apache.http.HttpResponse;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Inmobi {
	
	static String url = "http://api.w.inmobi.com/showad/v2";
	
	static private String config(String lat, String lng) throws JSONException {

		JSONObject json = new JSONObject();
		json.put("responseformat", "axml");
       
		JSONObject banner = new JSONObject();
		banner.put("adsize", "15");
		banner.put("pos", "top");
		banner.put("api", 0);

		JSONObject imp_obj = new JSONObject();
		imp_obj.put("ads", 1);
		imp_obj.put("adtype", "int");
		imp_obj.put("banner", banner);
		
		json.put("imp", new JSONArray().put(imp_obj));
		
		JSONObject site = new JSONObject();
//		site.put("id", "804e7e806d96485186589025c1639ff3"); // REAL
		site.put("id", "a876ea112b104c1287a5a901e6f2d686"); // TEST
		json.put("site", site);
		
		JSONObject devicegeo = new JSONObject();
		devicegeo.put("lat", lat);
		devicegeo.put("lon", lng);
		devicegeo.put("accu", "0");
		
		
		JSONObject device = new JSONObject();
		JSONObject ip_json = new JSONObject(Inmobi.getURL("http://akahanga.jelou.com/api/getPublicIP"));
		device.put("ip", ip_json.getString("ip"));
		String ua = Globals.getUserAgent();
		try {
			ua = URLEncoder.encode(ua,"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		device.put("ua", ua);
		
		device.put("geo", devicegeo);
		
		json.put("device", device);
		return json.toString();
	}
	
	static public String getBanner(String lat, String lng) {
	    HttpClient httpclient = new DefaultHttpClient();
	    HttpPost httppost = new HttpPost(url);
 	   	String str = null;

	    try {
			Log.i("INMOBI", config(lat, lng));
	    	StringEntity se = new StringEntity(config(lat, lng));
	    	se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

	        httppost.setEntity(se);

			HttpResponse response = httpclient.execute(httppost);
    	    str = getString(response.getEntity().getContent());
	    } catch (ClientProtocolException e) {
	        // TODO Auto-generated catch block
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	    } catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	    
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
	
	 static private String getURL(String url) {
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
}
