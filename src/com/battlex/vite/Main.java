package com.battlex.vite;

/**
 * @author Saurabh
 * @author Sids
 *
 *                     MAIN STARTUP PAGE
 */




import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends ListActivity {
	
	Document doc = null;
	Element content = null;
    Elements links = null;
	ArrayList<String> Proces = new ArrayList<String>();
	ArrayList<String> percent = new ArrayList<String>();
	ArrayList<String> slts = new ArrayList<String>();
	
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
       
		//GET ATTENDANCE
        new DownloadFilesTask().execute();
       
		
    }
    
	//THE AWESOME ALGORITHM
	private void algo(int pos){
		
		int sub = pos-1;
		int j = 0;
		if(pos == 0){j = 2;}
		
		for (  ; pos>=2 ; pos-- ){
			j = j + 10;
		}
		ArrayList<String> data = new ArrayList<String>();
		data.add(links.get(j+3).text());
		
		for (int i = 4 ; i <= 10 ; i++){
			data.add(links.get(j+i).text());
		}
		
	
		
		Intent intn = new Intent(Main.this, Info.class);
		intn.putStringArrayListExtra("data", data);
		intn.putExtra("sub",Integer.toString(sub));
		 Main.this.startActivity(intn);
		
		
	}
	
	
	
    
    //ACTIONBAR CLICK LISTNERS
    private OnClickListener blits= new OnClickListener(){@Override public void onClick(View v) {Intent intn = new Intent(Main.this, Restrt.class);Main.this.startActivity(intn);Main.this.finish();}};
    private OnClickListener act_feed= new OnClickListener(){@Override public void onClick(View v) {Intent intn = new Intent(Main.this, Register.class);Main.this.startActivity(intn);}};
    private OnClickListener act_men = new OnClickListener(){@Override public void onClick(View v) {openOptionsMenu(); }};
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		algo(position);}
    
    private String readTxt(){
    	
        InputStream inputStream = getResources().openRawResource(R.raw.license);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int i;
     try {
      i = inputStream.read();
      while (i != -1)
         {
          byteArrayOutputStream.write(i);
          i = inputStream.read();
         }
         inputStream.close();
     } catch (IOException e) {
      
      e.printStackTrace();
     }
        return byteArrayOutputStream.toString();
       }

    
 String ur;
 //MENU
private void setcl(){Intent intn = new Intent(Main.this, Register.class);
Main.this.startActivity(intn);}
@Override
public boolean onPrepareOptionsMenu (Menu menu) {
	 SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
	ur = preferences.getString("url","notset");
    int upto = preferences.getInt("upto", 0);
    if (upto!=0){menu.getItem(4).setEnabled(false);}
    return true;
}
    
@SuppressLint("UseValueOf")
private void abt_bx(){
		
		Dialog dialog = new Dialog(this);

		dialog.setContentView(R.layout.di_abt);
		TextView text = (TextView) dialog.findViewById(R.id.txt_abt);
		
		PackageInfo pInfo = null;
		try {pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);} catch (NameNotFoundException e) {}
		Float curver = new Float(pInfo.versionName);
		
		//ABOUT BOX TEXT 
		text.setText("Version: ".concat(Float.toString(curver)).concat(" \n\n\nDevelopers: \n\nSaurabh \n(facebook.com/battlex2010)\n\nSid\n\n\nDevices Tested:\nXperia X8\nGalaxy S+\nGalaxy Ace\nGalaxy Y\nXperia P\n\n\n\n").concat(readTxt()));
		text.setMovementMethod(LinkMovementMethod.getInstance());
		ImageView image = (ImageView) dialog.findViewById(R.id.img_abt);
		image.setImageResource(R.drawable.diab);
		dialog.setTitle("About");
		dialog.show();
	}
    
    @Override
	 public boolean onCreateOptionsMenu(Menu menu) {
	     MenuInflater inflater = getMenuInflater();
	     inflater.inflate(R.menu.men, menu);
	     return true;}
    public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.set:
	        setcl();
	        return true;
	    case R.id.abt:
	    	abt_bx();
	    	return true;
	    case R.id.ref:
	    	Intent intn = new Intent(Main.this, Restrt.class);
	    	Main.this.startActivity(intn);
	    	Main.this.finish();
	    	return true;
	    case R.id.men_feed:
	    	intn = new Intent(Main.this, Feedback.class);
	    	Main.this.startActivity(intn);
	    	return true;
	    case R.id.men_end:
	    	System.exit(0);
	    case R.id.men_upd:
	    	Intent cur = new Intent(this,Update_service.class);
			cur.putExtra("url", ur);
			startService(cur);
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}
    
    //ASYNC TASK
        
    private class DownloadFilesTask extends AsyncTask<Void, Void, Void> {
    	
    	
    	
    	private ProgressDialog dialog ;
        String title = null;
        protected void onPreExecute (){
        	
        	dialog = new ProgressDialog(Main.this);
        	this.dialog.setMessage("Loading your attendance...");
        	this.dialog.setCancelable(false);
            this.dialog.show();
        }
        
        
            		@Override
    		protected Void doInBackground(Void... arg0) {
            			try{
            				 SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                             String ret = preferences.getString("regnum","notset");
                             String date = String.valueOf(preferences.getInt("date", 1));
                             String year = String.valueOf(preferences.getInt("year", 1990));
                             String month = String.valueOf(preferences.getInt("month", 0));
                             
                             if (month.length() == 1){
                            	 month = "0".concat(month);     
                             }
                             
                             if (date.length() == 1){
                            	 date = "0".concat(date);     
                             }
                             
                             String url = "http://vitattx.appspot.com/att/".concat(ret).concat("/").concat(date).concat(month).concat(year);
                             
                           
            				 doc = Jsoup.connect(url).timeout(0).get();
            				 //11bec0262
            				 
            				 
            				 //content = doc.getElementById("overview");
            	    	     links = doc.getElementsByTag("tr");
            	    	    	title = "Loading Complete :)";
            	    	    	
            	    	    	//GET SUBJECTS
            	    	    for (int i=3 ; i<links.size() ; i++ ){
            	    	    	Proces.add(links.get(i).text());
            	    	    	i = i + 9;
            	    	    		}
            	    	   
            	    	    //GET ATTENDANCE
            	    	    for (int i=9 ; i<links.size() ; i++ ){
            	    	    	percent.add(links.get(i).text().concat("  "));
            	    	    	i = i + 9;}
            	    	    
            	    	    //GET SLOT
            	    	    for (int i = 4 ; i<links.size() ; i++){
            	    	    	slts.add(links.get(i+1).text());
            	    	    	i = i + 9;
            	    	    }
            	    	   
            	        	
            	        	} catch(IOException e){ Log.e("VIT", "Background: " + e.getMessage());}
    			
    			return null;
    		}
            		protected void onPostExecute(Void result){
            			
            			 
            			 
            			Context context = getApplicationContext();
            			CharSequence text = title;
            			
            			ListView lv = getListView();
            			LayoutInflater inflater = getLayoutInflater();
            			
            			ViewGroup header = (ViewGroup)inflater.inflate(R.layout.header, lv , false);
            			lv.addHeaderView(header, null, false);
            			
            			ImageButton btn_ref = (ImageButton) findViewById (R.id.imgbtn3);
        				btn_ref.setOnClickListener(blits);
        				ImageButton btn_feed = (ImageButton) findViewById (R.id.imgbtn2);
        				btn_feed.setOnClickListener(act_feed);
        				ImageButton btn_men = (ImageButton) findViewById (R.id.imgbtn4);
        				btn_men.setOnClickListener(act_men);
            			
        				String values [] = (String []) Proces.toArray (new String [Proces.size ()]);
        				String per [] = (String []) percent.toArray (new String [percent.size ()]);   			
        				String slots [] = (String []) slts.toArray (new String [slts.size ()]);
        				MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(getApplicationContext(), values);           			
        				adapter.per = per;
        				adapter.slots = slots;
        				setListAdapter(adapter);
            			
            			if (title==null){
            				
            				text = "Network error! Please refresh or check widget for last update";  
            			}
            			
            			else if(title.equals("404 Not Found")){
            				text = "Please check your registration number / Date of Birth";
            			}
            			
            			else {
            				SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()); 
            				SharedPreferences.Editor editor = preferences.edit();
            				editor.putInt("subs_length", Proces.size()-1);
            				editor.commit();
            				array_break(values , "subs");
            				array_break(per , "percent");  
            				array_break(slots , "slots");
            			}
            			
            			
            		  
            			int duration = Toast.LENGTH_SHORT;
            			Toast toast = Toast.makeText(context, text, duration);
            			toast.show();
            			if (dialog.isShowing()) {
            	            dialog.dismiss();
            	        }
            		}
            		private void array_break(String arr[] , String key_name){
            			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            			SharedPreferences.Editor editor = preferences.edit();
            			
            			for (int i = 0 ; i <= arr.length-1 ; i++){
            				editor.putString(key_name + "_" + i , arr[i]);
            				
            			}
            				editor.commit();
            				
            			
            		}
        }
}
