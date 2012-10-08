package com.battlex.vite;

/**
 * @author Saurabh
 *
 *                     INFO IN DETAIL PAGE
 */
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class Info extends Activity {
	ArrayList<String> data = new ArrayList<String>();
	String subject_number;
	TextView txt10;
	
	
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
	        subject_number = getIntent().getStringExtra("sub");
	        
	        String values [] = (String []) data.toArray (new String [data.size ()]);
	        TextView txt1 = (TextView) findViewById (R.id.lblsub);
	        TextView txt2 = (TextView) findViewById (R.id.txtatt);
	        TextView txt3 = (TextView) findViewById (R.id.txtcon);
	        TextView txt4 = (TextView) findViewById (R.id.txtslot);
	        TextView txt5 = (TextView) findViewById (R.id.txtpercent);
	        TextView txt6 = (TextView) findViewById (R.id.txtone);
	        TextView txt7 = (TextView) findViewById (R.id.txttwo);
	        TextView txt8 = (TextView) findViewById (R.id.txt3);
	        TextView txt9 = (TextView) findViewById (R.id.txt4);
	        txt10 = (TextView) findViewById (R.id.txt6);
	        TextView txt11 = (TextView) findViewById (R.id.txt7);
	        TextView txt12 = (TextView) findViewById (R.id.txt8);
	        new LoadDate().execute();
	        txt1.setText(values[0]); //Subject
	        txt1.setTextColor(Color.RED);
	        
	        txt4.setText(values[2]); //SLOT
	        txt2.setText(values[4]); //ATTENDED
	        txt3.setText(values[5]); //CONDUCTED
	        txt5.setText(values[6]); //%
	        
	       
	        float one , two , three , four , five , six;
	        try{
	        float att = new Float(values[4]);
	        float cond = new Float(values[5]);
	        
	        one   = ((att/(cond +1)) *100);
	        two   = ((att/(cond +2)) *100);
	        five  = ((att/(cond +3))   *100);
	        three = (((att+1)/(cond+1))*100);
	        four  = (((att+2)/(cond+2))*100);
	        six  = (((att+3)/(cond+3))*100);

	          
	        txt6.setText(Integer.toString((int)(Math.round(one))).concat(" %"));
	        txt7.setText(Integer.toString((int)(Math.round(two))).concat(" %"));
	        txt8.setText(Integer.toString((int)(Math.round(three))).concat(" %"));
	        txt9.setText(Integer.toString((int)(Math.round(four))).concat(" %"));
	        txt11.setText(Integer.toString((int)(Math.round(five))).concat(" %"));
	        txt12.setText(Integer.toString((int)(Math.round(six))).concat(" %"));
	        }catch(Exception e ){
	        	txt6.setText("NA");
	        	txt7.setText("NA");
	        	txt8.setText("NA");
	        	txt9.setText("NA");
	        	txt10.setText("NA");
	        	txt11.setText("NA");
	        	txt12.setText("NA");
	        	
	        }
	        
	 }
	 
	 class LoadDate extends AsyncTask<Void,Void,Integer>{
		 String dte;
		@Override
		protected Integer doInBackground(Void... params) {
			try {
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
                String url = "http://vitattx.appspot.com/det/".concat(ret).concat("/").concat(date).concat(month).concat(year).concat("/").concat(subject_number);
				Document doc;
				
				doc = Jsoup.connect(url).timeout(0).get();
				dte = doc.body().text();
				} catch (IOException e) {
			}
			
			return 0;
		}
		protected void onPostExecute(Integer param){
			txt10.setText(dte);
			
		}

	
	}
     

}
