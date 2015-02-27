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

public class ThirdParty extends BaseFragment {
	
	private EditText driver_name, driver_id,driver_address, driver_phone_no, driver_license;
	private Button Save;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	
		View rootView = inflater.inflate(R.layout.third_party,
				container, false);
		driver_name =(EditText) rootView.findViewById(R.id.driver_name);
		driver_id =(EditText) rootView.findViewById(R.id.driver_id);
		driver_address =(EditText) rootView.findViewById(R.id.driver_address);
		driver_phone_no =(EditText) rootView.findViewById(R.id.driver_phone_no);
		driver_license =(EditText) rootView.findViewById(R.id.driver_license);
		
		Save = (Button)rootView.findViewById(R.id.cas_save);
		
		
		return rootView;
	}
}
