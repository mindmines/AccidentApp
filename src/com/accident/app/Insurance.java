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

public class Insurance extends BaseFragment implements OnClickListener {
	
	private EditText AgencyName, PolicyNumber,AgentName,AgentNumber;
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
	
		View rootView = inflater.inflate(R.layout.insurance,
				container, false);
		mContext = (MainActivity) this.getActivity();
		dBhelper = new DBhelper(getActivity());
		AgencyName =(EditText) rootView.findViewById(R.id.agency_name);
		PolicyNumber =(EditText) rootView.findViewById(R.id.policy_number);
		AgentName =(EditText) rootView.findViewById(R.id.agent_name);
		AgentNumber =(EditText) rootView.findViewById(R.id.agent_number);
		Save = (Button)rootView.findViewById(R.id.cas_save);
		
		//Heading
		 mClose = (ImageView)rootView.findViewById(R.id.close);
	     mDelete = (ImageView)rootView.findViewById(R.id.delete);
	     HeadingText = (TextView)rootView.findViewById(R.id.heading);
	     HeadingText.setText(getResources().getString(R.string.insurance));
		
	     Save.setOnClickListener(this);
			mClose.setOnClickListener(this);
			mDelete.setOnClickListener(this);
			
		return rootView;
	}
	
	private void CallSaveButton(){
		String agencyName, policyNumber,agentName, agentNumber;
		
		agencyName =AgencyName.getText().toString().trim();
		policyNumber=PolicyNumber.getText().toString().trim();
		agentName=AgentName.getText().toString().trim();
		agentNumber=AgentNumber.getText().toString().trim();
		
		if(!agencyName.equals("") && !policyNumber.equals("") && !agentName.equals("") && !agentNumber.equals("")){
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
		AgencyName.setText("");
		AgencyName.setText("");
		AgencyName.setText("");
		AgencyName.setText("");
			break;
		}
	}
}
