package com.accident.app;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class Police extends BaseFragment {
	
	private EditText event_number, case_number,unit_name, station_name;
	private Button Save;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	
		View rootView = inflater.inflate(R.layout.police,
				container, false);
		event_number =(EditText) rootView.findViewById(R.id.event_number);
		case_number =(EditText) rootView.findViewById(R.id.case_number);
		unit_name =(EditText) rootView.findViewById(R.id.unit_name);
		station_name =(EditText) rootView.findViewById(R.id.station_name);
		
		Save = (Button)rootView.findViewById(R.id.cas_save);
		
		
		return rootView;
	}
}
