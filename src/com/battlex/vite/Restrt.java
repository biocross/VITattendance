package com.battlex.vite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Restrt extends Activity {
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        Intent intn = new Intent(Restrt.this, Main.class);
    		 Restrt.this.startActivity(intn);
	        
	 }
	

}
