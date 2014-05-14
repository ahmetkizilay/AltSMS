package com.ahmetkizilay.alt.sms;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
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
	
	private Activity activity;
	
	public SMSThreadCursorAdapter(Activity activity, Cursor cursor) {
		super(activity, cursor, false);
		
		this.activity = activity;
		this.addressIndex = cursor.getColumnIndex("address");
		this.bodyIndex = cursor.getColumnIndex("body");
		this.countIndex = cursor.getColumnIndex("count");
		this.personIndex = cursor.getColumnIndex("person");
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		
		ViewHolder holder = (ViewHolder) view.getTag();
		String contactId = cursor.getString(this.personIndex);
		String sender = contactId != null ? this.getContactName(Integer.parseInt(contactId)) : cursor.getString(this.addressIndex);
		
		holder.photo.setImageResource(R.drawable.ic_launcher);
		holder.sender.setText(sender + " (" + cursor.getInt(this.countIndex) + ")");
		holder.body.setText(cursor.getString(this.bodyIndex));
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {

			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			View rowView = inflater.inflate(R.layout.altsms_display_list_item, null);
			
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.body = (TextView) rowView.findViewById(R.id.lblBody);
			viewHolder.sender = (TextView) rowView.findViewById(R.id.lblSender);
			viewHolder.photo = (ImageView) rowView.findViewById(R.id.imgPhoto);
			rowView.setTag(viewHolder);
		
			return rowView;

	}
	
	private String getContactName(int contactId) {
		final Cursor cursor = this.activity.getContentResolver().query(
				ContactsContract.Contacts.CONTENT_URI,
				new String[] { ContactsContract.Contacts.DISPLAY_NAME },
				"_id = " + contactId,
                null, null);

        String userName = "";
        if(cursor.moveToFirst()) {
        	userName = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
        }

        cursor.close();

        return userName;
	}

	static class ViewHolder {
		public TextView body;
		public TextView sender;
		public ImageView photo;
	}
}
