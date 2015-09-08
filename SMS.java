package com.fidku.jeloubeta.utils;

import org.json.JSONException;
import org.json.JSONObject;

import com.fidku.jeloubeta.Jelou;
import com.fidku.jeloubeta.screens.Loader;
import com.fidku.jeloubeta.screens.Register;
import com.fidku.jeloubeta.screens.Validar;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.util.Log;

public class SMS {
	
    static private SmsManager smsm;
	static private PendingIntent piSent;
	static private PendingIntent piDelivered;
	static private BroadcastReceiver smsSentReceiver, smsDeliveredReceiver;
	static private Validar act;
	
	static public void sendSMS(Validar ctx)
	{
		act = ctx;
		
		if(smsm == null) {
			smsm = SmsManager.getDefault();
			piSent = PendingIntent.getBroadcast(act, 0, new Intent("SMS_SENT"), 0);
			piDelivered = PendingIntent.getBroadcast(act, 0, new Intent("SMS_DELIVERED"), 0);
		}
		
		smsm.sendTextMessage(Globals.config[0], null, "native,543211234567890", piSent, piDelivered);
		
		smsSentReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                // TODO Auto-generated method stub
                switch (getResultCode()) {
	                case Activity.RESULT_OK:
	                    //Toast.makeText(act.getBaseContext(), "SMS has been sent", Toast.LENGTH_SHORT).show();
	                    break;
	                case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
	                   //Toast.makeText(act.getBaseContext(), "Generic Failure", Toast.LENGTH_SHORT).show();
	                    break;
	                case SmsManager.RESULT_ERROR_NO_SERVICE:
	                    //Toast.makeText(act.getBaseContext(), "No Service", Toast.LENGTH_SHORT).show();
	                    break;
	                case SmsManager.RESULT_ERROR_NULL_PDU:
	                    //Toast.makeText(act.getBaseContext(), "Null PDU", Toast.LENGTH_SHORT).show();
	                    break;
	                case SmsManager.RESULT_ERROR_RADIO_OFF:
	                    //Toast.makeText(act.getBaseContext(), "Radio Off", Toast.LENGTH_SHORT).show();
	                    break;
	                default:
	                    break;
                }
                
            }
        };
        
        smsDeliveredReceiver = new BroadcastReceiver() {
            
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                // TODO Auto-generated method stub
                switch(getResultCode()) {
	                case Activity.RESULT_OK:
	                	act.unregisterReceiver(smsSentReceiver);
	                	act.unregisterReceiver(smsDeliveredReceiver);
	                	
	                	JSONObject json = API.validateUser();
	                	
	                	if(json != null) {
	                		Log.i("validar", json.toString());
	                		
	                		try {
								if(json.getBoolean("access")) {
									if(json.getBoolean("reg")) {
				                		{
				                			act.startActivity(new Intent(act,  Loader.class));
				                			act.finish();
				                		}
									} else if(!json.getBoolean("reg")){
										if(json.getString("real_number").equals("match")) {
											act.startActivity(new Intent(act,  Register.class));
				                			act.finish();
										}
									}
								}
								
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
	                	}
	                	
	                	//Toast.makeText(act.getBaseContext(), "SMS Delivered", Toast.LENGTH_SHORT).show();
	                    break;
	                case Activity.RESULT_CANCELED:
	                	//Toast.makeText(act.getBaseContext(), "SMS not delivered", Toast.LENGTH_SHORT).show();
	                    break;
                }
            }
        };
        
        act.registerReceiver(smsSentReceiver, new IntentFilter("SMS_SENT"));
        act.registerReceiver(smsDeliveredReceiver, new IntentFilter("SMS_DELIVERED"));
	}
}
