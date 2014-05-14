package com.ahmetkizilay.alt.sms;

import com.ahmetkizilay.alt.sms.R;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

public class ListMessagesActivity extends Activity {
	private final String MY_TAG = "AltSMS.ListMessagesActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_messages);
				
		SMSMediator smsMediator = new SMSMediator(this);
		Cursor smsCursor = smsMediator.getAllSmsThreads();
		
		final ListView listView = (ListView) findViewById(R.id.listview);
		listView.setAdapter(new SMSThreadCursorAdapter(this, smsCursor));

	}
}
