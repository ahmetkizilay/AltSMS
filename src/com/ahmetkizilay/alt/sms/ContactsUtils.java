package com.ahmetkizilay.alt.sms;

import android.app.Activity;
import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;

public class ContactsUtils {
	final private static String[] PHOTO_ID_PROJECTION = new String[] {
		ContactsContract.Contacts.PHOTO_ID
	};
	
	final private static String[] PHOTO_BITMAP_PROJECTION = new String[] {
			ContactsContract.CommonDataKinds.Photo.PHOTO
	};
	
	private Activity activity;
	
	public ContactsUtils(Activity activity) {
		this.activity = activity;
	}
	
	public int fetchThumbnailId(String phoneNumber) {
	    final Uri uri = Uri.withAppendedPath(ContactsContract.CommonDataKinds.Phone.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
	    final Cursor cursor = this.activity.getContentResolver().query(uri, PHOTO_ID_PROJECTION, null, null, ContactsContract.Contacts.DISPLAY_NAME + " ASC");
	    
	    try {    
	        if (cursor.moveToFirst()) {
	            return cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_ID));
	        }
	    }
	    finally {
	        cursor.close();
	    }
	    
	    return -1;

	}

	public Bitmap fetchThumbnail(final int thumbnailId) {
		
	    final Uri uri = ContentUris.withAppendedId(ContactsContract.Data.CONTENT_URI, thumbnailId);
	    final Cursor cursor = this.activity.getContentResolver().query(uri, PHOTO_BITMAP_PROJECTION, null, null, null);

	    try {
	        Bitmap thumbnail = null;
	        if (cursor.moveToFirst()) {
	            final byte[] thumbnailBytes = cursor.getBlob(0);
	            if (thumbnailBytes != null) {
	                thumbnail = BitmapFactory.decodeByteArray(thumbnailBytes, 0, thumbnailBytes.length);
	            }
	        }
	        return thumbnail;
	    }
	    finally {
	        cursor.close();
	    }

	}
	
	public ContactHolder findContactByPhoneNumber(String phoneNumber) {
		
		final Cursor cursor = this.activity.getContentResolver().query(
        		Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber)), 
        	    new String[] {ContactsContract.PhoneLookup.DISPLAY_NAME, ContactsContract.PhoneLookup.PHOTO_ID},   
                null, null, null);

        ContactHolder cHolder = new ContactHolder();
        if(cursor.moveToFirst()) {
        	cHolder.username = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
        	cHolder.photoId = cursor.getInt(cursor.getColumnIndex(ContactsContract.PhoneLookup.PHOTO_ID));
        	cHolder.isContact = true;
        }

        cursor.close();

        return cHolder;
	}
	
	public static class ContactHolder {
		public String username = "";
		public int photoId = -1;
		public boolean isContact = false;
	}
}
