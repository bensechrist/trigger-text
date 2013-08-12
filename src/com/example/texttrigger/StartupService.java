package com.example.texttrigger;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

public class StartupService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		Log.i("Service", "Service Started!");
		
		SMSReceiver receiver = new SMSReceiver();
		IntentFilter ifilter = new IntentFilter();
		ifilter.addAction("android.provider.Telephony.SMS_RECEIVED");
		registerReceiver(receiver, ifilter);
	}

}
