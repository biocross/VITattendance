package com.battlex.vite;

/**
 * @author Saurabh
 * @author Sids
 *
 *                     SPLASH SCREEN
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import com.crittercism.app.Crittercism;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class Splash extends Activity {
	private ImageView splash;
	
	 private static final int STOPSPLASH = 0;
     //time in milliseconds
     private static final long SPLASHTIME = 3000;
     private Handler splashHandler = new Handler() {
         @Override
         public void handleMessage(Message msg) {
                 switch (msg.what) {
                 case STOPSPLASH:
                         //remove SplashScreen from view
                         splash.setVisibility(View.GONE);
                         SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                         String ret = preferences.getString("regnum","notset");
                         if (ret == "notset"){
                        	 Intent intn = new Intent(Splash.this, Register.class);
                     		 Splash.this.startActivity(intn);
                         }
                         else {
                        	 Intent intn = new Intent(Splash.this, Main.class);
                     		 Splash.this.startActivity(intn); }
                         
                 		 }
                       
                 super.handleMessage(msg);
         }
 };
    
 
	
	
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	

        super.onCreate(savedInstanceState);
        Crittercism.init(getApplicationContext(), "4feb55b9be790e162a000003");
        String breadcrumb = "Crittercism has init, splash displayed.";
        Crittercism.leaveBreadcrumb(breadcrumb);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main);
        //START SERVICE
        PackageInfo pInfo = null;
		try {
			pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
		} catch (NameNotFoundException e) {}
		//Toast.makeText(getApplicationContext(), pInfo.versionName, Toast.LENGTH_LONG).show();
			Float curver = new Float(pInfo.versionName);
			Intent cur = new Intent(this,Updater_service.class);
			cur.putExtra("ver", curver);
			startService(cur);
			
        splash = (ImageView) findViewById(R.id.splashscreen);
        Message msg = new Message();
        msg.what = STOPSPLASH;
        splashHandler.sendMessageDelayed(msg, SPLASHTIME);
    }
}