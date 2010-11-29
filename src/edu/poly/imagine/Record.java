package edu.poly.imagine;

import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class Record extends ListActivity {

	private static final int ACTIVITY_CREATE = 0;
	private static final int ACTIVITY_EDIT = 1;

	private static final int INSERT_ID = Menu.FIRST;
	private static final int DELETE_ID = Menu.FIRST + 1;
	private static final int UPDATE_ID = Menu.FIRST + 2;

	private DBUtil mDbHelper;
	private Cursor mDiaryCursor;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.record);
		mDbHelper = new DBUtil(this);
		mDbHelper.open();
		renderListView();
		
		Log.i("TableViewer", "record on create");
	}

	private void renderListView() {

		
		
		ContentResolver cr = getContentResolver();
		Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
				null, null, "times_contacted DESC");

		while (cursor.moveToNext()) {
			int tmp_id=0;
			String tmp_name;
			String tmp_nums="";
			int tmp_duration = 0;
			int tmp_txtnum = 0;
			int tmp_times=0;
			long tmp_lasttime=0;
			

			tmp_id = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID));
			tmp_name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

			ContentResolver cr2 = getContentResolver();
			Cursor phone_cursor = cr2.query(
					ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
					ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "="
							+ tmp_id, null, null);

			while (phone_cursor.moveToNext()) {
				String tmp_phonenum = phone_cursor.getString(phone_cursor.getColumnIndex(
						ContactsContract.CommonDataKinds.Phone.NUMBER));

				ContentResolver cr3 = getContentResolver();
				Cursor callcursor = cr3.query(Uri.parse("content://call_log/calls"), null,
						CallLog.Calls.NUMBER + "=" + tmp_phonenum, null, null);
				
				while (callcursor.moveToNext()) {
					tmp_duration = tmp_duration
							+ callcursor.getInt(callcursor
									.getColumnIndex(CallLog.Calls.DURATION));
				}
				
				ContentResolver cr4 = getContentResolver();
				Cursor smscursor = cr4.query(Uri.parse("content://sms"), null,
						"address ="+ tmp_phonenum, null, null);
				while (smscursor.moveToNext()) {
					tmp_txtnum++;
				}

				tmp_nums = tmp_nums + tmp_phonenum;

			}

			tmp_times = cursor.getInt(cursor.getColumnIndex("times_contacted"));
			tmp_lasttime = cursor.getLong(cursor.getColumnIndex("last_time_contacted"));

			Log.i("TableViewer", "|" + tmp_id + ' ' + tmp_name + ' ' + tmp_nums + ' '
					+ tmp_duration + ' ' + tmp_txtnum + ' ' + tmp_times + ' '
					+ tmp_lasttime);
			
			mDbHelper.createNote(tmp_id, tmp_name, tmp_nums, tmp_duration, tmp_txtnum, tmp_times, tmp_lasttime);
		}
			
			
		Log.i("TableViewer", "successed !");
			

		mDiaryCursor = mDbHelper.getAllNotes();
		
		 startManagingCursor(mDiaryCursor);
		 String[] from = new String[] {
		 DBUtil.column_name,
		 DBUtil.column_nums,
		DBUtil.column_duration,
		DBUtil.column_txtnum,
		 DBUtil.column_times,
		 DBUtil.column_lasttime};
		 
		 int[] to = new int[] {
		 R.id.record_name,R.id.record_nums,R.id.record_duration,R.id.record_txtnum, R.id.record_times,R.id.record_lasttime};
		 
		 
		 
		 String ColumnNames="";;
		 for(int i=0;i<mDiaryCursor.getColumnCount();i++){
				 ColumnNames=ColumnNames+mDiaryCursor.getColumnName(i)+'\t';
				        
		 }
		 Log.i("TableViewer",ColumnNames);
						
		 while(mDiaryCursor.moveToNext()){
		 String ColumnValues = "";
		 for(int i=0;i<mDiaryCursor.getColumnCount();i++){
			 ColumnValues=ColumnValues+mDiaryCursor.getString(i)+'\t';
			 }
			Log.i("TableViewer",ColumnValues);
		 }
		 Log.i("TableViewer", "successed 2 !");
		 
		 SimpleCursorAdapter notes = new SimpleCursorAdapter(this,R.layout.user, mDiaryCursor, from, to);//R.layout.record_row
		 setListAdapter(notes);
		 
		 Log.i("TableViewer", "successed 3 !");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, INSERT_ID, 0, "insert");
		menu.add(0, DELETE_ID, 0, "delete");
		menu.add(0, UPDATE_ID, 0, "delete");
		return true;
	}

	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case INSERT_ID:
			createNote();
			return true;

		case DELETE_ID:
//			mDbHelper.deleteNote(getListView().getSelectedItemId());
//			renderListView();
			return true;
			
		case UPDATE_ID:
//			mDbHelper.deleteNote(getListView().getSelectedItemId());
//			renderListView();
			return true;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	private void createNote() {
		// Intent i = new Intent(this, Add.class);
		// startActivity(i);
	}

	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Cursor c = mDiaryCursor;
		c.moveToPosition(position);
		// Intent i = new Intent(this, ShowChart.class);
		// i.putExtra(DBUtil.KEY_ID, id);
		// i.putExtra(DBUtil.KEY_CONTACT_ID,
		// c.getString(c.getColumnIndexOrThrow(DBUtil.KEY_CONTACT_ID)));
		// i.putExtra(DBUtil.KEY_CONTACT_TYPE, c.getString(c
		// .getColumnIndexOrThrow(DBUtil.KEY_CONTACT_TYPE)));
		// i.putExtra(DBUtil.KEY_CALL_TYPE, c.getString(c
		// .getColumnIndexOrThrow(DBUtil.KEY_CALL_TYPE)));
		// startActivityForResult(i, ACTIVITY_EDIT);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		renderListView();
	}
}
