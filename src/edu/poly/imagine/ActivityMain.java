package edu.poly.imagine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.util.Log;


public class ActivityMain extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
        

        
        //record list button
        ImageButton record = (ImageButton) findViewById(R.id.record);
        record.setOnClickListener(new OnClickListener(){
 	
 			@Override
 			public void onClick(View v) {
 				Intent intent = new Intent(ActivityMain.this,Record.class);
 				startActivity(intent);
 				return;
 			}
     	   
        });
        
        //setting button
        ImageButton setting = (ImageButton) findViewById(R.id.setting);
        setting.setOnClickListener(new OnClickListener(){

 			@Override
 			public void onClick(View v) {
 				//Intent intent = new Intent(MainActivity.this,Setting.class);
 				//startActivity(intent);
 			}
        
        });
        
        //add a contact into the system
        ImageButton add = (ImageButton) findViewById(R.id.addcontact);
        add.setOnClickListener(new OnClickListener(){

 			@Override
 			public void onClick(View v) {
 				//Intent intent = new Intent(MainActivity.this,Add.class);
 				//startActivity(intent);
 			}
        
        });
     }
    
}