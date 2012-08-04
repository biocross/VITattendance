package com.battlex.vite;

/**
 * @author Saurabh
 *
 *                     INFO IN DETAIL PAGE
 */
import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class Info extends Activity {
	ArrayList<String> data = new ArrayList<String>();
	
	private OnClickListener act_back= new OnClickListener(){@Override public void onClick(View v) {finish();}};
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
	        setContentView(R.layout.info);
	        ImageButton btn_ref = (ImageButton) findViewById (R.id.btn_back);
	        btn_ref.setOnClickListener(act_back);
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
	        try{
	        float att = new Float(values[2]);
	        float cond = new Float(values[3]);
	        one = ((att/(cond +1)) *100);
	        two = ((att/(cond +2)) *100);
	        
	        txt6.setText(Integer.toString((int)(Math.round(one))).concat(" %"));
	        txt7.setText(Integer.toString((int)(Math.round(two))).concat(" %"));
	        }catch(Exception e ){
	        	txt6.setText("NA");
	        	txt7.setText("NA");
	        }
	        
	 }
     

}
