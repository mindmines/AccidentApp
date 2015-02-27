package com.accident.app;

import java.util.ArrayList;
import java.util.HashMap;

import com.accident.app.dbhelper.DBhelper;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class ThirdParty extends BaseFragment {
	
	private EditText driver_name, driver_id,driver_address, driver_phone_no, driver_license;
	private CheckBox isOwener;
	private Button Save;
	DBhelper dBhelper;
	ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	
		View rootView = inflater.inflate(R.layout.third_party,
				container, false);
		dBhelper = new DBhelper(getActivity());
		
		driver_name =(EditText) rootView.findViewById(R.id.driver_name);
		driver_id =(EditText) rootView.findViewById(R.id.driver_id);
		driver_address =(EditText) rootView.findViewById(R.id.driver_address);
		driver_phone_no =(EditText) rootView.findViewById(R.id.driver_phone_no);
		driver_license =(EditText) rootView.findViewById(R.id.driver_license);
		
		isOwener =(CheckBox)rootView.findViewById(R.id.is_owener);
		Save = (Button)rootView.findViewById(R.id.cas_save);
		Save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				CallSaveButton();
				
			}
		});
		
		return rootView;
	}
	
	private void CallSaveButton(){
		String full_name, id,address, phone_no, DriverLicense;
		
		full_name =driver_name.getText().toString().trim();
		id=driver_id.getText().toString().trim();
		address=driver_address.getText().toString().trim();
		phone_no=driver_phone_no.getText().toString().trim();
		DriverLicense=driver_license.getText().toString().trim();
		
		if(!full_name.equals(null) && !id.equals(null) && !address.equals(null) && !phone_no.equals(null) && !DriverLicense.equals(null)){
			
		dBhelper.insertThirdParty(full_name, id, address, phone_no, DriverLicense, isOwener.isChecked(), isUpdate());
		Toast.makeText(getActivity(), "Data Saved", Toast.LENGTH_SHORT).show();
		}else{
			Toast.makeText(getActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show();
		}
	}
	
	private boolean isUpdate(){
		list = dBhelper.getData(dBhelper.TABLE_NAME_THIRD_PARTY);
		boolean value;
		if(list.size()>0)
			value = true;
		else 
			value =false;
		return value;
	}
}
