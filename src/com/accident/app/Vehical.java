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

public class Vehical extends BaseFragment {
	
	private EditText vehical_type, manufacturer,model, color, year,license_plate;
	private Button Save;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	
		View rootView = inflater.inflate(R.layout.vehical,
				container, false);
		vehical_type =(EditText) rootView.findViewById(R.id.vehical_type);
		manufacturer =(EditText) rootView.findViewById(R.id.manufacturer);
		model =(EditText) rootView.findViewById(R.id.model);
		color =(EditText) rootView.findViewById(R.id.color);
		year =(EditText) rootView.findViewById(R.id.year);
		license_plate =(EditText) rootView.findViewById(R.id.license_plate);
		
		Save = (Button)rootView.findViewById(R.id.cas_save);
		
		
		return rootView;
	}
}
