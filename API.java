package com.fidku.jeloubeta.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class API {
	
	static private String base = "http://akahanga.jelou.com/director/getDirectorInfo";
	
	static public Boolean getDirector(String carrier, String country)
	{
		//Log.d("getDirector", base + "?plmn=" + Globals.getCurrentCountry());
		JSONObject obj;
		
		try {
//			obj = new JSONObject(API.getURL(base + "?plmn=" + Globals.getCurrentCountry()));
			obj = new JSONObject(API.getURL(base + "?plmn=73003"));
			Globals.config[0] = obj.getString("short_code");
			Globals.config[1] = obj.getString("num_code");
			Globals.config[2] = obj.getString("plmn");
			Globals.config[3] = obj.getString("proxy_host");
			Globals.config[4] = obj.getString("proxy_port");
			Globals.config[5] = obj.getString("api_host");
			Globals.config[6] = obj.getString("updated");
			
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}
			
		return true;
	}
	
	static public JSONObject validateUser()
	{
		Log.i("validateUser", Globals.config[5] + "/validateUser?pcs=" + Globals.config[7] + "&imei=" + Globals.getIMEI() + "&carrier=" + Globals.getCarrier() + "&country=" + Globals.getCountry());
		JSONObject obj;
		
		try {
			obj = new JSONObject(API.getURL(Globals.config[5] + "/validateUser?pcs=" + Globals.config[7] + "&imei=" + Globals.getIMEI() + "&carrier=claro&country=cl"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		return obj;
	}
	static public JSONObject loadsiteimage()
	{
		//Log.i("siteimageurl", Globals.config[5] + "/getAppConfig?pcs=" + Globals.config[7]  + "&country=" + Globals.getCountry()+ "&carrier=" + Globals.getCarrier());
		JSONObject obj;
		//http://akahanga.jelou.com/api/getAppConfig?pcs=56992001083&country=cl&carrier=claro 
		try {
			//obj = new JSONObject(API.getURL(Globals.config[5] + "/getAppConfig?pcs=" + Globals.config[7]  + "&country=" + Globals.getCountry()+ "&carrier=" + Globals.getCarrier()));
			obj = new JSONObject(API.getURL("http://akahanga.jelou.com/api/getAppConfig?pcs=56992001083&country=cl&carrier=claro"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		return obj;
	}
	
	static public JSONArray loadfavoriteimage()
	{

		JSONArray obj;
		try {
			obj = new JSONArray(API.getURL("http://akahanga.jelou.com/api/getInternalAd?type=favorite_ad&density=160dpi&amount=3"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		return obj;
	}
	
	static public JSONObject register()
	{
		JSONObject obj;
		Log.i("register", Globals.config[5] + "/saveUser?carrier=" + Globals.getCarrier() + "&country=" + Globals.getCountry() + "&pcs=" + Globals.config[7] + "&imei=" + Globals.getIMEI() +"&device=Android&model=" + Globals.getModel() + "&os_version=" + Globals.getVersion() + "&name=" + Globals.user[0] +"&email=" + Globals.user[1] + "&born_date=" + Globals.user[3] + "&sex=" + Globals.user[2] + "&app_version=2.0");
		
		try {
			obj = new JSONObject(API.getURL(Globals.config[5] + "/saveUser?carrier=" + Globals.getCarrier() + "&country=" + Globals.getCountry() + "&pcs=" + Globals.config[7] + "&imei=" + Globals.getIMEI() +"&device=Android&model=" + Globals.getModel() + "&os_version=" + Globals.getVersion() + "&name=" + Globals.user[0] +"&email=" + Globals.user[1] + "&born_date=" + Globals.user[3] + "&sex=" + Globals.user[2] + "&app_version=2.0"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		return obj;
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
}
