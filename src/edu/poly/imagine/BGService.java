package edu.poly.imagine;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;
import android.content.Context;
import android.app.NotificationManager;
import android.app.Notification;
import android.content.ContentResolver;
import android.database.Cursor;


public class BGService extends Service {

	private NotificationManager mNM;
	
	private Context mcontext;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		// Display a notification about us starting. We put an icon in the
		// status bar.
		// showNotification();

		// initial SQLite database operator
		
		Uri SMS_URI 	= Uri.parse("content://sms");
		Uri Call_URI 	= Uri.parse("content://call_log/calls");
		
		
		//Toast.makeText(getApplicationContext(), "service on create!", Toast.LENGTH_SHORT).show();
		
		ContentResolver smsCR 	= getContentResolver();
		ContentResolver callCR 	= getContentResolver();
		
		Cursor smsCursor	= smsCR.query(SMS_URI, null, null, null, null);
		Cursor callCursor	= callCR.query(Call_URI, null, null, null, "date DESC");
		
		mcontext =getApplicationContext();
		SMSObserver mSMSObserver	= new SMSObserver(mcontext,smsCursor, new Handler());
		CallObserver mCallObserver	= new CallObserver(mcontext,callCursor,new Handler());
		
		smsCR.registerContentObserver(Uri.parse("content://sms"),true, mSMSObserver);
		
		callCR.registerContentObserver(Uri.parse("content://call_log/calls"), true, mCallObserver);
		
		

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		//Log.i("LocalService", "Received start id " + startId + ": " + intent);
		// We want this service to continue running until it is explicitly
		// stopped, so return sticky.
		
		//Toast.makeText(getApplicationContext(), "intent received!", Toast.LENGTH_SHORT).show();
		return START_STICKY;
	}

}
