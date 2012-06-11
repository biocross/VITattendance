package com.battlex.vite;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

public class Info extends Activity {
	ArrayList<String> data = new ArrayList<String>();
	
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.info);
	        data = getIntent().getStringArrayListExtra("data");
	        String values [] = (String []) data.toArray (new String [data.size ()]);
	        TextView txt1 = (TextView) findViewById (R.id.lblsub);
	        TextView txt2 = (TextView) findViewById (R.id.txtatt);
	        TextView txt3 = (TextView) findViewById (R.id.txtcon);
	        TextView txt4 = (TextView) findViewById (R.id.txtslot);
	        TextView txt5 = (TextView) findViewById (R.id.txtpercent);
	        TextView txt6 = (TextView) findViewById (R.id.txtone);
	        TextView txt7 = (TextView) findViewById (R.id.txttwo);
	        
	        txt1.setText(values[0]); //Subject
	        txt1.setTextColor(Color.RED);
	        
	        txt4.setText(values[1]); //SLOT
	        txt2.setText(values[2]); //ATTENDED
	        txt3.setText(values[3]); //CONDUCTED
	        txt5.setText(values[4]); //%
	        
	        float one;
	        float two;
	        
	        float att = new Float(values[2]);
	        float cond = new Float(values[3]);
	        one = ((att/(cond +1)) *100);
	        two = ((att/(cond +2)) *100);
	        
	        txt6.setText(Float.toString(one).concat(" %"));
	        txt7.setText(Float.toString(two).concat(" %"));
	        
	        
	 }
     

}
