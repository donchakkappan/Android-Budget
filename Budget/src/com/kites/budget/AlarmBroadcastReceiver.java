package com.kites.budget;



import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;


/**
 * @author Prabu
 *
 */
public class AlarmBroadcastReceiver extends BroadcastReceiver {
	 MediaPlayer mp=null ;
	 public static String message="";
	 private static final int NOTIFY_ME_ID=1337;
	 
	@Override
	public void onReceive(Context context, Intent intent) {
		
		 mp = MediaPlayer.create(context,R.raw.mesdeliv);
		 playSound(context, getAlarmUri());
		 
		Toast.makeText(context, "Time is up!!!!.",Toast.LENGTH_LONG).show();
		// Vibrate the mobile phone
		Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.vibrate(2000);
		
		final NotificationManager mgr=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
	    Notification note=new Notification(R.drawable.updates,"Budget Notification",System.currentTimeMillis());
	         
	        // This pending intent will open after notification click
	        PendingIntent i=PendingIntent.getActivity(context, 0,new Intent(context,HomePage.class),0);
	         
	        note.setLatestEventInfo(context, "Budget Informs. . . ",message, i);
	         
	        //After uncomment this line you will see number of notification arrived
	        //note.number=2;
	        mgr.notify(NOTIFY_ME_ID, note);
		
	}
	
//-----------------------------------------------------------------------------------------------------------------------------------------

	
    private void playSound(final Context context, Uri alert) {
        
        
        Thread background = new Thread(new Runnable() {
			public void run() {
				try {
					
	               mp.start();
	               
				} catch (Throwable t) {
					Log.i("Animation", "Thread  exception "+t);
				}	
	        }
	 });
	 background.start();
   }
	
//-----------------------------------------------------------------------------------------------------------------------------------------
    
    private Uri getAlarmUri() {
    	
        Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alert == null) {
            alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            if (alert == null) {
                alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            }
        }
        return alert;
    }


}
