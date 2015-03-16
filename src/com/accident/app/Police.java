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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Police extends BaseFragment implements OnClickListener {
	
	private EditText event_number, case_number,unit_name, station_name;
	private Button Save;
	DBhelper dBhelper;
	ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
	int currentIDs;
	MainActivity mContext;
	
	//Heading
		TextView HeadingText;
		ImageView mClose,mDelete;
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	
		View rootView = inflater.inflate(R.layout.police,
				container, false);
		mContext = (MainActivity) this.getActivity();
		dBhelper = new DBhelper(getActivity());
		event_number =(EditText) rootView.findViewById(R.id.event_number);
		case_number =(EditText) rootView.findViewById(R.id.case_number);
		unit_name =(EditText) rootView.findViewById(R.id.unit_name);
		station_name =(EditText) rootView.findViewById(R.id.station_name);
		
		//Heading
		 mClose = (ImageView)rootView.findViewById(R.id.close);
	     mDelete = (ImageView)rootView.findViewById(R.id.delete);
	     HeadingText = (TextView)rootView.findViewById(R.id.heading);
	     HeadingText.setText(getResources().getString(R.string.police));
		
		Save = (Button)rootView.findViewById(R.id.cas_save);
		Save.setOnClickListener(this);
		mClose.setOnClickListener(this);
		mDelete.setOnClickListener(this);
		
		return rootView;
	}
	
	private void CallSaveButton(){
		String EventNumber, CaseNumber,UnitName, stationName;
		
		EventNumber =event_number.getText().toString().trim();
		CaseNumber=case_number.getText().toString().trim();
		UnitName=unit_name.getText().toString().trim();
		stationName=station_name.getText().toString().trim();
		
		if(!EventNumber.equals("") && !CaseNumber.equals("") && !UnitName.equals("") && !stationName.equals("")){
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
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.cas_save:
			CallSaveButton();
			break;
		case R.id.close:
			mContext.handleBackPressed();
			break;
		case R.id.delete:
		dBhelper.Delete(dBhelper.TABLE_NAME_DATE_TIME,mContext.getIds());
		Toast.makeText(getActivity(), "Data Deleted", Toast.LENGTH_SHORT).show();
		event_number.setText("");
		case_number.setText("");
		unit_name.setText("");
		station_name.setText("");
			break;
		}
	}
}
