package com.battlex.vite;

/**
 * @author Saurabh
 * @author Sids
 *
 *                     CHECK FOR UPDATES SERVICE!!
 */

import com.crittercism.app.Crittercism;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.widget.Toast;



public class Updater_service extends IntentService {
	Document doc =null;

	public Updater_service() {
		super("Update_service");
		// TODO Auto-generated constructor stub
	}
	private Handler handler;  
	  
	@Override  
	public int onStartCommand(Intent intent, int flags, int startId) {  
	   handler = new Handler();  
	   return super.onStartCommand(intent, flags, startId);  
	}  
	void toaster(final String mssg){
		handler.post(new Runnable() {  
			   @Override  
			   public void run() {  
			      Toast.makeText(getApplicationContext(), mssg, Toast.LENGTH_SHORT).show();  
			   }  
			});  
	}
	void upto(final int isit){
		handler.post(new Runnable() {  
			   @Override  
			   public void run() {  
				   
				   SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
				   SharedPreferences.Editor editor = preferences.edit();
				   editor.putInt("upto", isit);  
				   editor.commit();
			   }  
			});  
	}
	
	void ur(final String url){
		handler.post(new Runnable() {  
			   @Override  
			   public void run() {  
				   System.out.println(url);
				   SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
				   SharedPreferences.Editor editor = preferences.edit();
				   editor.putString("url", url);    
				   editor.commit();
			   }  
			});  
	}
	
	private void shownoti(String title, String text){
		
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
		int icon = R.drawable.ic_launcher;
		CharSequence tickerText = text;
		long when = System.currentTimeMillis();

		Notification notification = new Notification(icon, tickerText, when);
		notification.flags = notification.flags | Notification.FLAG_AUTO_CANCEL;
		Context context = getApplicationContext();
		CharSequence contentTitle = title;
		CharSequence contentText = text;
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		String ur;
		ur = preferences.getString("url","notset");
		Intent cur = new Intent(this,Update_service.class);
		cur.putExtra("url", ur);
		
		PendingIntent contentIntent = PendingIntent.getService(this, 0, cur, 0);

		notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
		final int HELLO_ID = 1;

		mNotificationManager.notify(HELLO_ID, notification);
	}
	
	@Override
	protected void onHandleIntent(Intent intent) {
		try{
		float curver = intent.getFloatExtra("ver", 1);
		//toaster( Float.toString(curver));
		@SuppressWarnings("unused")
		int latest ;
		String url = "http://vita-biocross.rhcloud.com/android/update.php?cv=".concat(Float.toString(curver));
		doc = Jsoup.connect(url).timeout(0).get();
		String res = doc.body().text();
		try{
		latest = Integer.parseInt(res);
		upto(1);
		String breadcrumb = "Update check has completed.";
		Crittercism.leaveBreadcrumb(breadcrumb);
		
		}catch(NumberFormatException e){upto(0);shownoti("VITattendance", "An update is available!");ur(res);}
		
		}catch(IOException e){}
	}

}
