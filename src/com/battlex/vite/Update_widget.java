package com.battlex.vite;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;

public class Update_widget extends Service {
  

  @Override
  public void onStart(Intent intent, int startId) {
    Log.v("VIT", "Widget: Called");
    // Create some random data

    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this
        .getApplicationContext());

    int[] allWidgetIds = intent
        .getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);
    
    //GET CURRENT POSITION IN ARRAY
    int back = intent.getIntExtra("back",0);
    

    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    SharedPreferences.Editor editor = preferences.edit();
    
    //CHECK IF BACK OR FRONT
    int wid_cur = preferences.getInt("wid_cur", 0);
    
    if (back == 1){
    	wid_cur -= 1;   	
    }
    else if (wid_cur == 0){} //LEAVE IT ZERO
    
    else wid_cur +=1;
        
    if (wid_cur > preferences.getInt("subs_length", 0)) wid_cur = 0 ;
    else if (wid_cur <= 0) wid_cur = preferences.getInt("subs_length", 0);
 
    	
    
    String sub = preferences.getString("subs_"+ wid_cur, "Loading..");
    
    String per = preferences.getString("percent_"+ wid_cur, "Loading");
    
    String slt = preferences.getString("slots_" + wid_cur, "Loading..");
    
   if (wid_cur == 0 ) wid_cur+=1;
  
   
    editor.putInt("wid_cur",wid_cur);
    editor.commit();
    
    
    
    
    for (int widgetId : allWidgetIds) {
      // Create some random data
     
      RemoteViews remoteViews = new RemoteViews(this
          .getApplicationContext().getPackageName(),
          R.layout.widget_layout);
      // Set the text
      remoteViews.setTextViewText(R.id.lbl_wid_sub , sub);
      remoteViews.setTextViewText(R.id.lbl_wid_per , per);
      remoteViews.setTextViewText(R.id.lbl_wid_slt , slt);

      // Register an onClickListener
      Intent clickIntent = new Intent(this.getApplicationContext(),
          Provider_widget.class);
      Intent clickIntent2 = new Intent(this.getApplicationContext(),
              Provider_widget.class);
      
      clickIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
      clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,
          allWidgetIds);
      clickIntent2.putExtra("back", 0);
      
      clickIntent2.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
      clickIntent2.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,
          allWidgetIds);
      clickIntent2.putExtra("back", 1);
      

      PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, clickIntent,
          PendingIntent.FLAG_UPDATE_CURRENT);
      
      PendingIntent pendingIntent2 = PendingIntent.getBroadcast(getApplicationContext(), 0, clickIntent2,
              PendingIntent.FLAG_UPDATE_CURRENT);
      
      remoteViews.setOnClickPendingIntent(R.id.img_wid_nxt , pendingIntent);
      remoteViews.setOnClickPendingIntent(R.id.img_wid_bck , pendingIntent2);
      
      appWidgetManager.updateAppWidget(widgetId, remoteViews);
    }
    stopSelf();

    super.onStart(intent, startId);
  }

  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }
}