package com.example.texttrigger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class StartMyAppAtBootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
			Intent serviceintent = new Intent("com.example.StartupService");
			context.startService(serviceintent);
		}
	}

}
