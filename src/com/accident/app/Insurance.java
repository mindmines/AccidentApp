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

public class Insurance extends BaseFragment {
	
	private EditText AgencyName, PolicyNumber,AgentName,AgentNumber;
	private Button Save;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	
		View rootView = inflater.inflate(R.layout.insurance,
				container, false);
		AgencyName =(EditText) rootView.findViewById(R.id.agency_name);
		PolicyNumber =(EditText) rootView.findViewById(R.id.policy_number);
		AgentName =(EditText) rootView.findViewById(R.id.agent_name);
		AgentNumber =(EditText) rootView.findViewById(R.id.agent_number);
		Save = (Button)rootView.findViewById(R.id.cas_save);
		
		return rootView;
	}
}
