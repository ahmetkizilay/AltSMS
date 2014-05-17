package com.ahmetkizilay.alt.sms;


import com.ahmetkizilay.alt.sms.ContactsUtils.ContactHolder;

import android.app.Activity;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SMSThreadCursorAdapter extends CursorAdapter{

	private int addressIndex;
	private int bodyIndex;
	private int countIndex;
	private int personIndex;
	private int threadIdIndex;
	
	private Activity activity;
	private ContactsUtils contactUtils;
	
	public SMSThreadCursorAdapter(Activity activity, Cursor cursor) {
		super(activity, cursor, false);
		
		this.activity = activity;
		this.addressIndex = cursor.getColumnIndex("address");
		this.bodyIndex = cursor.getColumnIndex("body");
		this.countIndex = cursor.getColumnIndex("count");
		this.personIndex = cursor.getColumnIndex("person");
		this.threadIdIndex = cursor.getColumnIndex("thread_id");
		
		this.contactUtils = new ContactsUtils(this.activity);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		String phoneNumber = cursor.getString(this.addressIndex);
		int messageCount = cursor.getInt(this.countIndex);
		ViewHolder holder = (ViewHolder) view.getTag();
		ContactHolder cHolder = this.contactUtils.findContactByPhoneNumber(phoneNumber);
		
		if(cHolder.isContact && cHolder.photoId != 0) {
			holder.photo.setImageBitmap(this.contactUtils.fetchThumbnail(cHolder.photoId));
		}
		else {
			holder.photo.setImageResource(R.drawable.person);
		}
		
		if(cHolder.isContact) {
			holder.sender.setText(Html.fromHtml("<b>" + cHolder.username + "</b>&nbsp;(" + messageCount + ")"));
		}
		else {
			holder.sender.setText(Html.fromHtml("<b>" + phoneNumber + "</b>&nbsp;(" + messageCount + ")"));
		}
		

		holder.body.setText(cursor.getString(this.bodyIndex));
		holder.threadId = cursor.getInt(this.threadIdIndex);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {

			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			View rowView = inflater.inflate(R.layout.altsms_thread_list_item, null);
			
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.body = (TextView) rowView.findViewById(R.id.lblBody);
			viewHolder.sender = (TextView) rowView.findViewById(R.id.lblSender);
			viewHolder.photo = (ImageView) rowView.findViewById(R.id.imgPhoto);
			viewHolder.threadId = cursor.getInt(this.threadIdIndex);
			rowView.setTag(viewHolder);
		
			return rowView;

	}
	
	static class ViewHolder {
		public TextView body;
		public TextView sender;
		public ImageView photo;
		public int threadId;
	}
}
