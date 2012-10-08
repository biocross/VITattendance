package com.battlex.vite;
/**
 * @author Saurabh
 *
 *                     REFRESH PAGE
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

public class Restrt extends Activity {
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
		 requestWindowFeature(Window.FEATURE_NO_TITLE);
	        super.onCreate(savedInstanceState);
	        Intent intn = new Intent(Restrt.this, Main.class);
    		 Restrt.this.startActivity(intn);
	        
	 }
	

}
