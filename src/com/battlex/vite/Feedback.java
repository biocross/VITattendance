/**
 * 
 */
package com.battlex.vite;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author Saurabh
 *
 */
public class Feedback extends Activity  {
	 String url;
	
	  @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.feedback);
	        Button btnSave = (Button) findViewById (R.id.feed_send);
	        btnSave.setOnClickListener(blits);
	  }
	  private OnClickListener blits= new OnClickListener(){@Override public void onClick(View v) {snd_feed();}};
	
	  
	  public void snd_feed(){
		  EditText txt_feed = (EditText) findViewById (R.id.txt_feed);
		  
		 url = "Your link here".concat(txt_feed.getText().toString());
		 new SendFeedback().execute();
		 
	  }
	  
	  private class SendFeedback extends AsyncTask <Void, Void, Void>{
		  private ProgressDialog dialog ;
		  
		  protected void onPreExecute (){
	        	dialog = new ProgressDialog(Feedback.this);
	        	this.dialog.setMessage("Sending your feedback...");
	        	this.dialog.setCancelable(false);
	            this.dialog.show();
	        }

		@Override
		protected Void doInBackground(Void... arg0) {
			
			for(int k = 0 ; k<=5 ; k++){
				
			}
			return null;
		}
		
		protected void onPostExecute(Void result){
			Context context = getApplicationContext();
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(context, "Feedback sent! :)", duration);
			if (dialog.isShowing()) {dialog.dismiss();}
			toast.show();
			//Intent intn;
			//intn = new Intent(Feedback.this, Main.class);
	    	//Feedback.this.startActivity(intn);
		}
		  
		  
	  }
}

