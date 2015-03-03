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

public class Police extends BaseFragment {
	
	private EditText event_number, case_number,unit_name, station_name;
	private Button Save;
	DBhelper dBhelper;
	ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
	int currentIDs;
	MainActivity mContext;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	
		View rootView = inflater.inflate(R.layout.police,
				container, false);
		mContext = (MainActivity) this.getActivity();
		AppConstants.isFront = false;
		mContext.CallHeaderVisiblity();
		dBhelper = new DBhelper(getActivity());
		mContext.HeadingText.setText(getResources().getString(R.string.police));	
		event_number =(EditText) rootView.findViewById(R.id.event_number);
		case_number =(EditText) rootView.findViewById(R.id.case_number);
		unit_name =(EditText) rootView.findViewById(R.id.unit_name);
		station_name =(EditText) rootView.findViewById(R.id.station_name);
		
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
		String EventNumber, CaseNumber,UnitName, stationName;
		
		EventNumber =event_number.getText().toString().trim();
		CaseNumber=case_number.getText().toString().trim();
		UnitName=unit_name.getText().toString().trim();
		stationName=station_name.getText().toString().trim();
		
		if(!EventNumber.equals(null) && !CaseNumber.equals(null) && !UnitName.equals(null) && !stationName.equals(null)){
			currentIDs = mActivity.getIds();
		dBhelper.insertPolice(currentIDs,EventNumber, CaseNumber, UnitName, stationName, isUpdate());
		Toast.makeText(getActivity(), "Data Saved", Toast.LENGTH_SHORT).show();
		}else{
			Toast.makeText(getActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show();
		}
	}
	
	private boolean isUpdate(){
		list = dBhelper.getData(DBhelper.TABLE_NAME_POLICE);
		for(int i=0;i<list.size();i++){
			int s = (Integer) list.get(i).get(AppConstants.ITEM0);
			if(currentIDs == s)
				return true;
		
		}
		return false;
	}
}
