package com.battlex.vite;

import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
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
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        new DownloadFilesTask().execute();
        }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		//Toast toast = Toast.makeText(getApplicationContext(), Integer.toString(position), Toast.LENGTH_SHORT);
		//toast.show();
		int pos = position + 1;
		int j = 0;
		for ( j = 0 ; pos>=2 ; pos-- ){
			j = j + 5;
		}
		
		//if (position==1){j = 0;}
		System.out.println(j);
		ArrayList<String> data = new ArrayList<String>();
		for (int i=j  ; i<=j +4 ; i++ ){
	    	data.add(links.get(i).text());
	    		}
		Intent intn = new Intent(Main.this, Info.class);
		intn.putStringArrayListExtra("data", data);
		 Main.this.startActivity(intn);
		}
 
 //MENU
private void setcl(){Intent intn = new Intent(Main.this, Register.class);
Main.this.startActivity(intn);}
    
private void abt_bx(){
		
		Dialog dialog = new Dialog(this);

		dialog.setContentView(R.layout.di_abt);
		TextView text = (TextView) dialog.findViewById(R.id.txt_abt);
		text.setText("Developer: \nSau\nBiocross\n\n\nBeta Testers:\nUD\nSid\n\nDevices Tested:\nAVD\nXperia X8\nGalaxy S+\nGalaxy Ace ");
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
	    	return true;
	    case R.id.men_feed:
	    	intn = new Intent(Main.this, Feedback.class);
	    	Main.this.startActivity(intn);
	    	return true;
	    case R.id.men_end:
	    	System.exit(0);
	    	
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
                            
                             String url = "http://vitinfo.ws/m/".concat(ret);
                            
            				 doc = Jsoup.connect(url).timeout(0).get();
            				 //11bec0262
            				
            				 content = doc.getElementById("overview");
            	    	     links = content.getElementsByTag("td");
            	    	    	title = "Job Complete :)";
            	    	    	
            	    	    for (int i=0 ; i<links.size() ; i++ ){
            	    	    	Proces.add(links.get(i).text());
            	    	    	i = i + 4;
            	    	    		}
            	    	    for (int i=4 ; i<links.size() ; i++ ){
            	    	    	percent.add(links.get(i).text().concat("  "));
            	    	    	i = i + 4;}
            	    	    for (int i = 1 ; i<links.size() ; i++){
            	    	    	slts.add(links.get(i).text());
            	    	    	i = i + 4;
            	    	    }
            	    	   
            	        	
            	        	} catch(IOException e){};
    			// TODO Auto-generated method stub
    			return null;
    		}
            		protected void onPostExecute(Void result){
            			Context context = getApplicationContext();
            			CharSequence text = title;
            			if (title==null){
            				text = "Network error! Please relaunch app!";           			
            			}
            			int duration = Toast.LENGTH_SHORT;
            			Toast toast = Toast.makeText(context, text, duration);
            			toast.show();
            			String values [] = (String []) Proces.toArray (new String [Proces.size ()]);
            			String per [] = (String []) percent.toArray (new String [percent.size ()]);
            			String slots [] = (String []) slts.toArray (new String [slts.size ()]);
            			
            			ListView lv = getListView();
            			LayoutInflater inflater = getLayoutInflater();
            			
            			ViewGroup header = (ViewGroup)inflater.inflate(R.layout.header, lv, false);
            			
            			lv.addHeaderView(header, null, false);
            			
            			
            			MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(getApplicationContext(), values);
            			adapter.per = per;
            			adapter.slots = slots;
            		
            			setListAdapter(adapter);
            			
            			
            			
            			if (dialog.isShowing()) {
            	            dialog.dismiss();
            	        }
            		}
        }
}
