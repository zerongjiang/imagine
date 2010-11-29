package edu.poly.imagine;

import java.util.Calendar;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBUtil {

	public static final String column_id = "contact_id";
	public static final String column_name = "contact_name";
	public static final String column_nums = "contact_nums";
	public static final String column_duration= "contact_duration";
	public static final String column_txtnum= "contact_txtnum";
	public static final String column_times="contact_times";
	public static final String column_lasttime = "contact_lasttime";

	private static final String TAG = "DBUtil";
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;

	private static final String DATABASE_CREATE = "create table call (" +
			"contact_id integer primary key," +
			"contact_name text not null, " +
			"contact_nums text not null, " +
			"contact_duration int not null, " +
			"contact_txtnum integer not null," +
			"contact_times integer not null," +
			"contact_lasttime text not null);";

	private static final String DATABASE_NAME = "ringbuddy";
	private static final String DATABASE_TABLE = "call";
	private static final int DATABASE_VERSION = 1;

	private final Context mCtx;

	private static class DatabaseHelper extends SQLiteOpenHelper {

		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS call");
			onCreate(db);
		}
	}
	
	//constructor
	public DBUtil(Context ctx) {
		this.mCtx = ctx;
	}

	public DBUtil open() throws SQLException {
		//create a databasehelper
		mDbHelper = new DatabaseHelper(mCtx);
		
		//writableDatabase
		mDb = mDbHelper.getWritableDatabase();
		
		
		return this;
	}

	public void close() {
		mDbHelper.close();
	}

	public long createNote(int contact_id,String contact_name,String contact_nums,int contact_duration, int contact_txtnum,
			int contact_times, long contact_lasttime) {
		
		
		
		ContentValues initialValues= new ContentValues();
		initialValues.put(column_id, contact_id);
		initialValues.put(column_name, contact_name);
		initialValues.put(column_nums, contact_nums);
		initialValues.put(column_duration,contact_duration );
		initialValues.put(column_txtnum,contact_txtnum );
		initialValues.put(column_times, contact_times);
		
		Date last_date = new Date(contact_lasttime);
		String date_string = last_date.toString();
		
		initialValues.put(column_lasttime, date_string);
		
		return mDb.insertWithOnConflict(DATABASE_TABLE, null, initialValues,SQLiteDatabase.CONFLICT_REPLACE);
	}

	public boolean deleteNote(int contact_id) {

		return mDb.delete(DATABASE_TABLE, column_id + "=" + contact_id, null) > 0;
	}

	public Cursor getAllNotes() {

		return mDb.query(DATABASE_TABLE, new String[] { column_id, column_name ,column_nums,
				column_nums, column_duration,column_txtnum,column_times,column_lasttime}, null, null, null, null, null);
	}

	/*public Cursor rgetAllNotes() {

		return mDb.query(DATABASE_TABLE, new String[] { KEY_ID, KEY_CONTACT_ID,KEY_CONTACT_TYPE,
				KEY_CALL_TYPE, KEY_LAST_TIME }, , null, null, null, "_id desc");
	}*/
	
	public Cursor getNote(int contact_id) throws SQLException {

		Cursor mCursor =
		mDb.query(true, DATABASE_TABLE, new String[] { column_id, column_name ,column_nums,
				column_nums, column_duration,column_txtnum,column_times,column_lasttime}, column_id + "=" + contact_id, null, null,
				null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;

	}
	
	
	public boolean updateNote(int contact_id,String contact_name,String contact_nums,int contact_duration,
			int contact_txtnum, int contact_times, long contact_lasttime) {
		
		ContentValues args = new ContentValues();
		args.put(column_id, contact_id);
		args.put(column_name, contact_name);
		args.put(column_nums, contact_nums);
		args.put(column_duration,contact_duration );
		args.put(column_txtnum,contact_txtnum );
		args.put(column_times, contact_times);
		
		Date last_date = new Date(contact_lasttime);
		String date_string = last_date.toString();
		
		args.put(column_lasttime, date_string);
	

		return mDb.update(DATABASE_TABLE, args, column_id + "=" + contact_id, null) > 0;
	}

	
	public boolean reminder(){
		//TODO 
		return false;
		
	}
	
}
