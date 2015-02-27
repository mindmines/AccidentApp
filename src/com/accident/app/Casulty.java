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

public class Casulty extends BaseFragment {
	
	private EditText cas_full_name, cas_id,cas_address, cas_phone_no, cas_age;
	private CheckBox isHospitalized;
	private Button Save;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	
		View rootView = inflater.inflate(R.layout.casulty,
				container, false);
		cas_full_name =(EditText) rootView.findViewById(R.id.cas_full_name);
		cas_id =(EditText) rootView.findViewById(R.id.cas_id);
		cas_address =(EditText) rootView.findViewById(R.id.cas_address);
		cas_phone_no =(EditText) rootView.findViewById(R.id.cas_phone_no);
		cas_age =(EditText) rootView.findViewById(R.id.cas_age);
		
		isHospitalized =(CheckBox)rootView.findViewById(R.id.is_hospitalized);
		Save = (Button)rootView.findViewById(R.id.cas_save);
		
		
		return rootView;
	}
}
