package edu.poly.imagine;

import android.database.ContentObserver;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
import android.database.Cursor;

public class CallObserver extends ContentObserver {
	
	private static final String TAG = "CallObserver";
	
	private Cursor cursor;
	
	private Context context;
	
	public CallObserver(Context context, Cursor cursor, Handler handler) {
		super(handler);
		this.cursor = cursor;
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
		Toast.makeText(context, "Call:"+cursor.getString(6), Toast.LENGTH_SHORT).show();
		
		Log.i(TAG, "NewCall "+cursor.getString(0)+" "+cursor.getString(1)+" "+cursor.getString(2)+" "+cursor.getString(3)+" "+cursor.getString(4)
				+" "+cursor.getString(5)+" "+cursor.getString(6)+" "+cursor.getString(7)+" "+cursor.getString(8));
	}
	

}
