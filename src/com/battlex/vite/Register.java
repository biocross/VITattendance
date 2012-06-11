package com.battlex.vite;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);
	        setContentView(R.layout.register); 
	        EditText txt = (EditText) findViewById(R.id.txtreg);
	        txt.setTextColor(Color.BLACK);
	        Button btnSave = (Button) findViewById (R.id.btnsave);
	        btnSave.setOnClickListener(blits);
	        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String ret = preferences.getString("regnum","notset");
            if (ret!="notset"){
            	EditText txtreg = (EditText) findViewById (R.id.txtreg);
            	txtreg.setText(ret);
            }
	        
	        
	}
	private OnClickListener blits= new OnClickListener(){@Override public void onClick(View v) {saveit();}};
	public void saveit(){
		EditText txtreg;
		txtreg =(EditText) findViewById (R.id.txtreg);
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("regnum", txtreg.getText().toString());
		editor.commit();
		Toast toast = Toast.makeText(getApplicationContext(), "Registration number saved!", Toast.LENGTH_SHORT);
		toast.show();
		Intent intn = new Intent(Register.this, Main.class);
		Register.this.startActivity(intn);
	
	

}
}
