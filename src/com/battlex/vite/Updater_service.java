package com.battlex.vite;
import com.crittercism.app.Crittercism;
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
				   editor.commit();
			   }  
			});  
	}
	
	void ur(final String url){
		handler.post(new Runnable() {  
			   @Override  
			   public void run() {  
				   SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
				   SharedPreferences.Editor editor = preferences.edit();
				   editor.putString("url", url);    
				   editor.commit();
			   }  
			});  
	}
	
	@Override
	protected void onHandleIntent(Intent intent) {
		try{
		float curver = intent.getFloatExtra("ver", 1);
		//toaster( Float.toString(curver));
		@SuppressWarnings("unused")
		int latest ;
		String url = "http://vita-biocross.rhcloud.com/update.php?cv=".concat(Float.toString(curver));
		doc = Jsoup.connect(url).timeout(0).get();
		String res = doc.body().text();
		try{
		latest = Integer.parseInt(res);
		upto(1);
		String breadcrumb = "Update check has completed.";
		Crittercism.leaveBreadcrumb(breadcrumb);
		}catch(NumberFormatException e){upto(0);toaster("New version available!");ur(res);}
		
		}catch(IOException e){}
	}

}
