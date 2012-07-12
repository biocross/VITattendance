package com.battlex.vite;

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
import android.os.SystemClock;
import android.preference.PreferenceManager;

public class PushNews extends IntentService {

	public PushNews(String name) {
		super("PushNews");
		// TODO Auto-generated constructor stub
	}
	String ur =  "http://vita-biocross.rhcloud.com/android/news.php";
	
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
		Intent cur = new Intent(this,Feedback.class);
		cur.putExtra("url", ur);
		
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, cur, 0);

		notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
		final int HELLO_ID = 1;

		mNotificationManager.notify(HELLO_ID, notification);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		while(true){
			Document doc = null;
			try {
				doc = Jsoup.connect(ur).timeout(0).get();
				String title = doc.title();
				Integer titleI = Integer.parseInt(title);
				Integer newsId = 0;
				
				if (titleI == 1){
				String strnewsId = doc.getElementById("notificationId").text();
				newsId = Integer.parseInt(strnewsId);
				String newsTitle = doc.getElementById("title").text();
				String newsContent = doc.getElementById("content").text();
				
				SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
				Integer id_check = preferences.getInt("newsId",0);
				
				if (newsId > id_check){
					SharedPreferences.Editor editor = preferences.edit();
					editor.putInt("newsId", newsId);
					editor.commit();
					shownoti(newsTitle , newsContent);
				}
				
				}
				
				//if the current notifId > notifiDin prefs {
				//show notification }
				
				//update the pref with the new nothificstionId
				
				
				
				
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			SystemClock.sleep(12000);
		}
		// TODO Auto-generated method stub
		
	}
	 

}
