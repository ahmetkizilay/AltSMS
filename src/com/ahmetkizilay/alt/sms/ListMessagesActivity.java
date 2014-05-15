package com.ahmetkizilay.alt.sms;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

public class ListMessagesActivity extends Activity {
	private final static String LOG_TAG = "AltSMS.ListMessagesActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_messages);
		
		Intent intent = getIntent();
		int threadId = intent.getIntExtra("thread_id", -1);

		SMSMediator smsMediator = new SMSMediator(this);
		Cursor smsCursor = smsMediator.getAllSmsByThreadId(threadId);
		
		final ListView listView = (ListView) findViewById(R.id.listview);
		listView.setAdapter(new SMSMessagesCursorAdapter(this, smsCursor));
		
	}
}
