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

public class Insurance extends BaseFragment {
	
	private EditText AgencyName, PolicyNumber,AgentName,AgentNumber;
	private Button Save;
	DBhelper dBhelper;
	ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
	int currentIDs;
	MainActivity mContext;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	
		View rootView = inflater.inflate(R.layout.insurance,
				container, false);
		mContext = (MainActivity) this.getActivity();
		AppConstants.isFront = false;
		mContext.CallHeaderVisiblity();
		dBhelper = new DBhelper(getActivity());
		AgencyName =(EditText) rootView.findViewById(R.id.agency_name);
		PolicyNumber =(EditText) rootView.findViewById(R.id.policy_number);
		AgentName =(EditText) rootView.findViewById(R.id.agent_name);
		AgentNumber =(EditText) rootView.findViewById(R.id.agent_number);
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
		String agencyName, policyNumber,agentName, agentNumber;
		
		agencyName =AgencyName.getText().toString().trim();
		policyNumber=PolicyNumber.getText().toString().trim();
		agentName=AgentName.getText().toString().trim();
		agentNumber=AgentNumber.getText().toString().trim();
		
		if(!agencyName.equals(null) && !policyNumber.equals(null) && !agentName.equals(null) && !agentNumber.equals(null)){
			currentIDs = mActivity.getIds();
		dBhelper.insertInsurance(currentIDs,agencyName, policyNumber, agentName, agentNumber, isUpdate());
		Toast.makeText(getActivity(), "Data Saved", Toast.LENGTH_SHORT).show();
		}else{
			Toast.makeText(getActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show();
		}
	}
	
	private boolean isUpdate(){
		list = dBhelper.getData(DBhelper.TABLE_NAME_INSURANCE);
		for(int i=0;i<list.size();i++){
			int s = (Integer) list.get(i).get(AppConstants.ITEM0);
			if(currentIDs == s)
				return true;
		
		}
		return false;
	}
}
