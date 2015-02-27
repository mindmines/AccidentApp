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

public class Description extends BaseFragment {
	
	private EditText Description;
	private Button Save;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.description,
				container, false);
		Description =(EditText) rootView.findViewById(R.id.description);
		Save = (Button)rootView.findViewById(R.id.cas_save);
		
		return rootView;
	}
}
