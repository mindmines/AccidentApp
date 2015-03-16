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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class WitnessFragment  extends BaseFragment implements OnClickListener {
	
	private EditText wit_full_name, wit_id,wit_address, wit_phone_no, wit_age;
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
		wit_full_name =(EditText) rootView.findViewById(R.id.cas_full_name);
		wit_id =(EditText) rootView.findViewById(R.id.cas_id);
		wit_address =(EditText) rootView.findViewById(R.id.cas_address);
		wit_phone_no =(EditText) rootView.findViewById(R.id.cas_phone_no);
		wit_age =(EditText) rootView.findViewById(R.id.cas_age);
		
		//Heading
		 mClose = (ImageView)rootView.findViewById(R.id.close);
	     mDelete = (ImageView)rootView.findViewById(R.id.delete);
	     HeadingText = (TextView)rootView.findViewById(R.id.heading);
	     HeadingText.setText(getResources().getString(R.string.witness));
		
		isHospitalized =(CheckBox)rootView.findViewById(R.id.is_hospitalized);
		isHospitalized.setVisibility(View.GONE);
		Save = (Button)rootView.findViewById(R.id.cas_save);
		Save.setOnClickListener(this);
		mClose.setOnClickListener(this);
		mDelete.setOnClickListener(this);
		
		return rootView;
	}
	
	private void CallSaveButton(){
		String full_name, id,address, phone_no, age;
		
		full_name =wit_full_name.getText().toString().trim();
		id=wit_id.getText().toString().trim();
		address=wit_address.getText().toString().trim();
		phone_no=wit_phone_no.getText().toString().trim();
		age=wit_age.getText().toString().trim();
		
		if(!full_name.equals("") && !id.equals("") && !address.equals("") && !phone_no.equals("") && !age.equals("")){
			currentIDs = mActivity.getIds();
		dBhelper.insertWitness(currentIDs,full_name, id, address, phone_no, age, isUpdate());
		Toast.makeText(getActivity(), "Data Saved", Toast.LENGTH_SHORT).show();
		
		wit_full_name.setText("");
		wit_id.setText("");
		wit_address.setText("");
		wit_phone_no.setText("");
		wit_age.setText("");
		
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
