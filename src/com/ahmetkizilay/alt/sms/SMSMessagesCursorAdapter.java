package com.ahmetkizilay.alt.sms;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SMSMessagesCursorAdapter extends CursorAdapter{

	private int idIndex;
	private int personIndex;
	private int addressIndex;
	private int bodyIndex;
	private int typeIndex;
	private int dateIndex;
	
	private Activity activity;
	
	public SMSMessagesCursorAdapter(Activity activity, Cursor cursor) {
		super(activity, cursor, false);
		
		this.activity = activity;
		
		this.idIndex = cursor.getColumnIndex("_id");
		this.personIndex = cursor.getColumnIndex("person");
		this.addressIndex = cursor.getColumnIndex("address");
		this.bodyIndex = cursor.getColumnIndex("body");
		this.typeIndex = cursor.getColumnIndex("type");
		this.dateIndex = cursor.getColumnIndex("date");
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		
		ViewHolder holder = (ViewHolder) view.getTag();
		String contactId = cursor.getString(this.personIndex);
		String sender = contactId != null ? this.getContactName(Integer.parseInt(contactId)) : cursor.getString(this.addressIndex);
		
		String htmlSource = "<p><b>" + sender + ":</b>&nbsp;" + cursor.getString(this.bodyIndex) + "</p>";
	
		holder.body.setText(Html.fromHtml(htmlSource));
		holder.photo.setImageResource(R.drawable.ic_launcher);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {

			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			View rowView = inflater.inflate(R.layout.altsms_message_list_item, null);
			
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.body = (TextView) rowView.findViewById(R.id.lblBody);
			viewHolder.photo = (ImageView) rowView.findViewById(R.id.imgPhoto);
			viewHolder.smsId = cursor.getInt(this.idIndex);
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
		public ImageView photo;
		public int smsId;
	}
}
