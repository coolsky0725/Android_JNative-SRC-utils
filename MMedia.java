package com.fidku.jeloubeta.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


import org.apache.http.HttpResponse;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;

import org.apache.http.client.methods.HttpGet;

import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;

import android.util.Log;

public class MMedia {
     
       
    static public String getbanner() throws JSONException
    {
    	
        HttpClient httpClient = new DefaultHttpClient();
        String str = null;
         String mmdid = Globals.getAndroidID();
         JSONObject ip_json = new JSONObject(MMedia.getURL("http://akahanga.jelou.com/api/getPublicIP"));
         String uip = ip_json.getString("ip");
         String ua = Globals.getUserAgent();
         
         String appid = "140920";
        try {
        	ua = URLEncoder.encode(ua,"utf-8");
        	String url = "http://ads.mydas.mobi/getAd" + "?apid="+appid+ "&mmdid="+mmdid+"&ua="+ua+"&uip="+uip;
        	Log.d("mmdid=", url);
            HttpGet httpget = new HttpGet(url);
            
            HttpResponse response = httpClient.execute(httpget);
            str = getString(response.getEntity().getContent());
            Log.d("result=", str);
         
            
            
        } catch (ClientProtocolException e) {
	        // TODO Auto-generated catch block
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
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