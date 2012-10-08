package com.battlex.vite;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

public class Provider_widget extends AppWidgetProvider {

  

  @Override
  public void onUpdate(Context context, AppWidgetManager appWidgetManager,
      int[] appWidgetIds) {

    
    // Get all ids
    ComponentName thisWidget = new ComponentName(context,
        Provider_widget.class);
    int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);

    // Build the intent to call the service
    Intent intent = new Intent(context.getApplicationContext(),
        Update_widget.class);
    intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, allWidgetIds);

    // Update the widgets via the service
    context.startService(intent);
  }
} 