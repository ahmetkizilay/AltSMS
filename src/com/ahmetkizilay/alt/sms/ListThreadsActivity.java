package com.ahmetkizilay.alt.sms;

import com.ahmetkizilay.alt.sms.R;
import com.ahmetkizilay.alt.sms.SMSThreadCursorAdapter.ViewHolder;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ListThreadsActivity extends Activity {
	private final String MY_TAG = "AltSMS.ListThreadsActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.altsms_list_threads_activity);
				
		SMSMediator smsMediator = new SMSMediator(this);
		Cursor smsCursor = smsMediator.getAllSmsThreads();
		
		final ListView listView = (ListView) findViewById(R.id.listview);
		listView.setAdapter(new SMSThreadCursorAdapter(this, smsCursor));
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
				ViewHolder viewHolder = (ViewHolder) view.getTag();
				
				Intent intent = new Intent(ListThreadsActivity.this, ListMessagesActivity.class);
				intent.putExtra("thread_id", viewHolder.threadId);
				
				startActivity(intent);
				
			}
		});
	}
}
