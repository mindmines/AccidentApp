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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Casulty extends BaseFragment implements OnClickListener{
	
	private EditText cas_full_name, cas_id,cas_address, cas_phone_no, cas_age;
	private CheckBox isHospitalized;
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
	
		View rootView = inflater.inflate(R.layout.casulty,
				container, false);
		mContext = (MainActivity) this.getActivity();
		dBhelper = new DBhelper(getActivity());
		cas_full_name =(EditText) rootView.findViewById(R.id.cas_full_name);
		cas_id =(EditText) rootView.findViewById(R.id.cas_id);
		cas_address =(EditText) rootView.findViewById(R.id.cas_address);
		cas_phone_no =(EditText) rootView.findViewById(R.id.cas_phone_no);
		cas_age =(EditText) rootView.findViewById(R.id.cas_age);
		
		//Heading
		 mClose = (ImageView)rootView.findViewById(R.id.close);
	     mDelete = (ImageView)rootView.findViewById(R.id.delete);
	     HeadingText = (TextView)rootView.findViewById(R.id.heading);
	     HeadingText.setText(getResources().getString(R.string.casulty));
		
		isHospitalized =(CheckBox)rootView.findViewById(R.id.is_hospitalized);
		Save = (Button)rootView.findViewById(R.id.cas_save);
		Save.setOnClickListener(this);
		mClose.setOnClickListener(this);
		mDelete.setOnClickListener(this);
		
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
		cas_full_name.setText("");
		cas_id.setText("");
		cas_address.setText("");
		cas_phone_no.setText("");
		cas_age.setText("");
		isHospitalized.setChecked(false);
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
		//dBhelper.Delete(dBhelper.TABLE_NAME_DATE_TIME,mContext.getIds());
			Toast.makeText(getActivity(), "not working yet", Toast.LENGTH_SHORT).show();
			break;
		}
	}
	
}
