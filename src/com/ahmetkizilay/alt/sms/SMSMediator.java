package com.ahmetkizilay.alt.sms;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public class SMSMediator {
	private Context context;
	
	public SMSMediator(Context context) {
		this.context = context;
	}
	
	public Cursor getAllSmsThreads() {
		final Uri SMS_CONTENT_URI = Uri.parse("content://sms");
		final String[] PROJECTION = {"_id, count(*) as count", "thread_id", "person", "address", "body" };
		final String SELECTION = "thread_id IS NOT NULL) GROUP BY (thread_id";
		final String SORT = "date DESC limit 20";
		
		return context.getContentResolver().query(SMS_CONTENT_URI, PROJECTION, SELECTION, null, SORT);
	}
}
