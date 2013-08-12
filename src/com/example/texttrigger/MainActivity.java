package com.example.texttrigger;

import android.app.Activity;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.triggertext.R;

public class MainActivity extends Activity {
	
	private EditText tv;
	private TextView mtv;
	private Button btn;
	private SMSReceiver receiver;

	@Override
	protected void onResume() {
		super.onResume();
		
		receiver = new SMSReceiver();
		IntentFilter ifilter = new IntentFilter();
		ifilter.addAction("android.provider.Telephony.SMS_RECEIVED");
		registerReceiver(receiver, ifilter);
		
		SMSReceiver.prefs = getSharedPreferences("Trigger Word", 0);
		
		try {
			Log.i("Trigger", "Trigger is " + SMSReceiver.prefs.getString("Trigger", null));
			mtv.setText(SMSReceiver.prefs.getString("Trigger", null));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				SMSReceiver.prefs = getSharedPreferences("Trigger Word", 0);
				SharedPreferences.Editor editor = SMSReceiver.prefs.edit();
				editor.putString("Trigger", tv.getText().toString());
				editor.commit();
				Toast.makeText(MainActivity.this, "Trigger changed to " + SMSReceiver.prefs.getString("Trigger", ""), Toast.LENGTH_SHORT).show();
				tv.setText(null);
				String mtrigger = SMSReceiver.prefs.getString("Trigger", null);
				if (mtrigger != null) {
					Log.i("trigger", "Trigger is " + mtrigger);
					mtv.setText(SMSReceiver.prefs.getString("Trigger", ""));
				}
			}
		});
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tv = (EditText) findViewById(R.id.trigger_word);
		mtv = (TextView) findViewById(R.id.cur_trigger);
		btn = (Button) findViewById(R.id.submit);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(receiver);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			Toast.makeText(MainActivity.this, "No Settings for You!", Toast.LENGTH_SHORT).show();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
