package edu.poly.imagine;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;
import android.telephony.TelephonyManager;

public class BCReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub

//		SmsMessage[] msg = null;
//
//		if (intent.getAction()
//				.equals("android.provider.Telephony.SMS_RECEIVED")) {
//			// StringBuilder buf = new StringBuilder();
//			Bundle bundle = intent.getExtras();
//			if (bundle != null) {
//				Object[] pdusObj = (Object[]) bundle.get("pdus");
//				msg = new SmsMessage[pdusObj.length];
//				for (int i = 0; i < pdusObj.length; i++)
//					msg[i] = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
//			}
//
//			for (int i = 0; i < msg.length; i++) {
//				String msgTxt = msg[i].getMessageBody();
//				if (msgTxt.equals("Testing!")) {
//					Toast.makeText(context, "success!", Toast.LENGTH_LONG).show();
//					return;
//				} else {
//					Toast.makeText(context, msgTxt, Toast.LENGTH_LONG).show();
//					return;
//				}
//			}
//
//			return;
//		}
		
		//Toast.makeText(context, intent.getAction(), Toast.LENGTH_SHORT).show();
		
		if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
		{
			//Toast.makeText(context, "boot complete", Toast.LENGTH_SHORT).show();
			intent.setClass(context, BGService.class);
			context.startService(intent);
		}

		if (intent.getAction().equals("android.intent.action.PHONE_STATE")) {
			TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			
			//Toast.makeText(context, "phone state changed:"+tm.getCallState(), Toast.LENGTH_SHORT).show();
			if( tm.getCallState() == TelephonyManager.CALL_STATE_IDLE ){
				intent.setClass(context, BGService.class);
				context.startService(intent);
			}
		}
		
		if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
			intent.setClass(context, BGService.class);
			context.startService(intent);
		}
		return ;
	}

}
