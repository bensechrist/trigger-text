package com.example.texttrigger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SMSReceiver extends BroadcastReceiver {

	public static SharedPreferences prefs;
	public static String trigger;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		prefs = context.getSharedPreferences("Trigger Word", 0);
		Bundle bundle = intent.getExtras();
		SmsMessage[] msgs = null;
		String str = "";
		if (bundle != null) {
			Object[] pdus = (Object[]) bundle.get("pdus");
			msgs = new SmsMessage[pdus.length];
			for (int i=0; i<msgs.length; i++) {
				msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
				str += msgs[i].getMessageBody().toString();
			}
			if (str.contains(prefs.getString("Trigger", "")) && prefs.getString("Trigger", "") != "") {
				AudioManager audiomanager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
				audiomanager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
				audiomanager.setStreamVolume(AudioManager.STREAM_RING, audiomanager.getStreamMaxVolume(AudioManager.STREAM_RING), 0);
			}
		}
	}

}
