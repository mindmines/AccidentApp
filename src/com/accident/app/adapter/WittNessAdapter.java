package com.accident.app.adapter;

import java.util.ArrayList;

import com.accident.app.R;
import com.accident.app.R.id;
import com.accident.app.R.layout;
import com.accident.app.util.Witness;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class WittNessAdapter extends ArrayAdapter<Witness> {

	private final Activity context;
	private final ArrayList<Witness> comment;
	RecordHolder holder = null;
	View row;

	public WittNessAdapter(Activity context, ArrayList<Witness> comment) {
		super(context, R.layout.commentrow, comment);
		this.context = context;
		this.comment = comment;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		row = convertView;
		holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(R.layout.wittness_row, parent, false);
			holder = new RecordHolder();
			holder.name = (TextView) row.findViewById(R.id.name);
			
			holder.id = (TextView) row.findViewById(R.id.id);
			holder.phone = (TextView) row.findViewById(R.id.phone);
			holder.address = (TextView) row.findViewById(R.id.address);
			holder.age = (TextView) row.findViewById(R.id.age);
			
			row.setTag(holder);
		} else {
			holder = (RecordHolder) row.getTag();
		}
		Witness cmt = comment.get(position);
		holder.name.setText(cmt.getName());
		holder.id.setText("ID: "+cmt.getId());
		holder.address.setText("Address: "+cmt.getAdress());
		holder.phone.setText("Phone number: "+cmt.getPhoneno());
		holder.age.setText("Age: "+cmt.getAge());

		

		return row;
	}

	static class RecordHolder {
		TextView id;
		TextView name;
		TextView address;
		TextView phone;
		TextView age;
		
	}
}