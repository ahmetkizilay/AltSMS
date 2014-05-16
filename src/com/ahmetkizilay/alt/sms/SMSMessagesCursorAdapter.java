package com.ahmetkizilay.alt.sms;

import com.ahmetkizilay.alt.sms.ContactsUtils.ContactHolder;
import com.ahmetkizilay.alt.sms.SMSThreadCursorAdapter.ViewHolder;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
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
	private ContactsUtils contactUtils;
	
	public SMSMessagesCursorAdapter(Activity activity, Cursor cursor) {
		super(activity, cursor, false);
		
		this.activity = activity;
		this.contactUtils = new ContactsUtils(this.activity);
		
		this.idIndex = cursor.getColumnIndex("_id");
		this.personIndex = cursor.getColumnIndex("person");
		this.addressIndex = cursor.getColumnIndex("address");
		this.bodyIndex = cursor.getColumnIndex("body");
		this.typeIndex = cursor.getColumnIndex("type");
		this.dateIndex = cursor.getColumnIndex("date");
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		
		String phoneNumber = cursor.getString(this.addressIndex);
		int type = cursor.getInt(this.typeIndex);
		ViewHolder holder = (ViewHolder) view.getTag();
		ContactHolder cHolder = this.contactUtils.findContactByPhoneNumber(phoneNumber);
		String htmlSource = "";
		
		if(type == 2) {
			htmlSource = "<b>Me:</b>";
		}else {
			htmlSource = cHolder.isContact ? "<b>" + cHolder.username + ":</b>" : "<b>" + phoneNumber + ":</b>";
		}
		
		htmlSource += "&nbsp;" + cursor.getString(this.bodyIndex);
		holder.body.setText(Html.fromHtml(htmlSource));
		
		if(cHolder.isContact && cHolder.photoId != 0 && type != 2) {
			holder.photo.setImageBitmap(this.contactUtils.fetchThumbnail(cHolder.photoId));
		}
		else {
			holder.photo.setImageResource(R.drawable.person);
		}
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {

			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			View rowView = inflater.inflate(R.layout.altsms_message_list_item, null);
			
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.body = (TextView) rowView.findViewById(R.id.lblBody);
			viewHolder.photo = (ImageView) rowView.findViewById(R.id.imgPhoto);
			viewHolder.smsId = cursor.getInt(this.idIndex);
			viewHolder.address = cursor.getString(this.addressIndex);
			rowView.setTag(viewHolder);
		
			return rowView;

	}
	
	static class ViewHolder {
		public TextView body;
		public ImageView photo;
		public int smsId;
		public String address;
	}
}
