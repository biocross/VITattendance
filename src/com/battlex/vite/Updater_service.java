package com.battlex.vite;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import android.app.IntentService;
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
			   }  
			});  
	}
	
	@Override
	protected void onHandleIntent(Intent intent) {
		try{
		float curver = intent.getFloatExtra("ver", 1);
		//toaster( Float.toString(curver));
		float latest;
		String url = "https://vita-biocross.rhcloud.com/feedback.php?feedback=update_checker";
		doc = Jsoup.connect(url).timeout(0).get();
		String res = doc.body().text();
		latest = new Float(res);
		if (curver < latest){
			upto(0);
			toaster("New version available!");
			
			//Toast.makeText(getApplicationContext(), "Update required!", Toast.LENGTH_LONG).show();
		}
		else{
			upto(1);
			//toaster("Up to date!");
			//Toast.makeText(getApplicationContext(), "Up to date!", Toast.LENGTH_LONG).show();
		}
		
		}catch(IOException e){}
	}

}
