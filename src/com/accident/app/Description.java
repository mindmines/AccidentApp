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

public class Description extends BaseFragment {
	
	private EditText Description;
	private Button Save;
	DBhelper dBhelper;
	ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
	int currentIDs;
	MainActivity mContext;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.description,
				container, false);
		mContext = (MainActivity) this.getActivity();
		AppConstants.isFront = false;
		mContext.CallHeaderVisiblity();
		mContext.HeadingText.setText(getResources().getString(R.string.des));	
		dBhelper = new DBhelper(getActivity());
		Description =(EditText) rootView.findViewById(R.id.description);
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
		String description;
		
		description =Description.getText().toString().trim();
		if(!description.equals("")){
			currentIDs = mActivity.getIds();
		dBhelper.insertDescription(currentIDs,description,isUpdate());
		Toast.makeText(getActivity(), "Data Saved", Toast.LENGTH_SHORT).show();
		mContext.handleBackPressed();
		}else{
			Toast.makeText(getActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show();
		}
	}
	
	private boolean isUpdate(){
		list = dBhelper.getData(DBhelper.TABLE_NAME_DESCRIPTION);
		for(int i=0;i<list.size();i++){
			int s = (Integer) list.get(i).get(AppConstants.ITEM0);
			if(currentIDs == s)
				return true;
		
		}
		return false;
	}
}
