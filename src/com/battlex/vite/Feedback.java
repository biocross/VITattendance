/**
 * 
 */
package com.battlex.vite;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * @author Saurabh
 *
 */
public class Feedback extends Activity  {
	
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
		  
		  String url = "Your link here".concat(txt_feed.getText().toString());
		  
		  
	  }
}

