package edu.poly.imagine;

import android.database.ContentObserver;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
import android.telephony.SmsMessage;
import android.database.Cursor;



public class SMSObserver extends ContentObserver {

	private static final String TAG = "SMSObserver";
	
	private Cursor cursor;
	
	private Context context;

	public SMSObserver(Context context, Cursor cursor, Handler handler) {
		super(handler);
		
		this.cursor= cursor;
		this.context = context;
		
		Log.i(TAG, "num of rows"+cursor.getCount());
		Log.i(TAG, "number of column"+cursor.getColumnCount());
		for(int i=0;i<cursor.getColumnCount();i++){
			Log.i(TAG, "column name "+i+' '+cursor.getColumnName(i));
		}
	}

	public void onChange(boolean selfChange) {
		cursor.requery();
		cursor.moveToFirst();
		
		//Toast.makeText(Context.getApplicationContext(), "SMS:"+cursor.getString(2), Toast.LENGTH_SHORT).show();
		Toast.makeText(context, "SMS:"+cursor.getString(2), Toast.LENGTH_SHORT).show();
		
		Log.i(TAG, "NewSMS"+cursor.getString(0)+cursor.getString(11)+cursor.getString(4));
	}

}
