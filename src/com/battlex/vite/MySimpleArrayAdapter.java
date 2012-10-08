package com.battlex.vite;
/**
 * @author Saurabh
 *
 *                   ARRAY LIST ADAPTER CLASS
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MySimpleArrayAdapter extends ArrayAdapter<String> {
	private final Context context;
	//private final String[] percent;
	private final String[] values;
	public String[] per;
	public String[] slots;

	public MySimpleArrayAdapter(Context context, String[] values) {
		super(context, R.layout.rowlayout, values);
		this.context = context;
		this.values = values;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.rowlayout, parent, false);
		//ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		TextView txt_per = (TextView) rowView.findViewById(R.id.a_percent);
		TextView txt_slot = (TextView) rowView.findViewById(R.id.a_slot);
		textView.setText(values[position]);
		txt_per.setText(per[position]);
		txt_slot.setText(slots[position]);
		
		//String s = values[position];
		//imageView.setImageResource(R.drawable.frwd);
		return rowView;
	}
}
