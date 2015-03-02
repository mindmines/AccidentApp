package com.accident.app;

import java.util.ArrayList;
import java.util.HashMap;

import com.accident.app.dbhelper.DBhelper;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class WitnessFragment  extends BaseFragment {
	
	private EditText wit_full_name, wit_id,wit_address, wit_phone_no, wit_age;
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
		
		wit_full_name =(EditText) rootView.findViewById(R.id.cas_full_name);
		wit_id =(EditText) rootView.findViewById(R.id.cas_id);
		wit_address =(EditText) rootView.findViewById(R.id.cas_address);
		wit_phone_no =(EditText) rootView.findViewById(R.id.cas_phone_no);
		wit_age =(EditText) rootView.findViewById(R.id.cas_age);
		
		isHospitalized =(CheckBox)rootView.findViewById(R.id.is_hospitalized);
		isHospitalized.setVisibility(View.GONE);
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
		
		full_name =wit_full_name.getText().toString().trim();
		id=wit_id.getText().toString().trim();
		address=wit_address.getText().toString().trim();
		phone_no=wit_phone_no.getText().toString().trim();
		age=wit_age.getText().toString().trim();
		
		if(!full_name.equals(null) && !id.equals(null) && !address.equals(null) && !phone_no.equals(null) && !age.equals(null)){
			currentIDs = mActivity.getIds();
		dBhelper.insertWitness(currentIDs,full_name, id, address, phone_no, age, isUpdate());
		Toast.makeText(getActivity(), "Data Saved", Toast.LENGTH_SHORT).show();
		}else{
			Toast.makeText(getActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show();
		}
	}
	
	private boolean isUpdate(){
		list = dBhelper.getData(dBhelper.TABLE_NAME_WITNESSES);
		for(int i=0;i<list.size();i++){
			int s = (Integer) list.get(i).get(AppConstants.ITEM0);
			if(currentIDs == s)
				return true;
		
		}
		return false;
	}
}
