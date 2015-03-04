package com.accident.app;

import java.util.ArrayList;
import java.util.HashMap;

import com.accident.app.dbhelper.DBhelper;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class Casulty extends BaseFragment {
	
	private EditText cas_full_name, cas_id,cas_address, cas_phone_no, cas_age;
	private CheckBox isHospitalized;
	private Button Save;
	DBhelper dBhelper;
	ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
	int currentIDs;
	MainActivity mContext;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	
		View rootView = inflater.inflate(R.layout.casulty,
				container, false);
		mContext = (MainActivity) this.getActivity();
		AppConstants.isFront = false;
		mContext.CallHeaderVisiblity();
		dBhelper = new DBhelper(getActivity());
		mContext.HeadingText.setText(getResources().getString(R.string.casulty));	
		cas_full_name =(EditText) rootView.findViewById(R.id.cas_full_name);
		cas_id =(EditText) rootView.findViewById(R.id.cas_id);
		cas_address =(EditText) rootView.findViewById(R.id.cas_address);
		cas_phone_no =(EditText) rootView.findViewById(R.id.cas_phone_no);
		cas_age =(EditText) rootView.findViewById(R.id.cas_age);
		
		isHospitalized =(CheckBox)rootView.findViewById(R.id.is_hospitalized);
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
		String full_name, id,address, phone_no, age;
		
		full_name =cas_full_name.getText().toString().trim();
		id=cas_id.getText().toString().trim();
		address=cas_address.getText().toString().trim();
		phone_no=cas_phone_no.getText().toString().trim();
		age=cas_age.getText().toString().trim();
		
		//Toast.makeText(getActivity(), ""+full_name+"  "+ id +"  "+address+"  "+ phone_no+"  "+ age+"  "+isUpdate(), Toast.LENGTH_SHORT).show();
		if(!full_name.equals("") && !id.equals("") && !address.equals("") && !phone_no.equals("") && !age.equals("")){
			currentIDs = mActivity.getIds();
		dBhelper.insertCasulty(currentIDs,full_name, id, address, phone_no, age, isHospitalized.isChecked(), isUpdate());
		Toast.makeText(getActivity(), "Data Saved", Toast.LENGTH_SHORT).show();
		}else{
			Toast.makeText(getActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show();
		}
	}
	
	private boolean isUpdate(){
		list = dBhelper.getData(dBhelper.TABLE_NAME_CASUALTIES);
		for(int i=0;i<list.size();i++){
			int s = (Integer) list.get(i).get(AppConstants.ITEM0);
			if(currentIDs == s)
				return true;
		
		}
		return false;
	}
	
	
}
