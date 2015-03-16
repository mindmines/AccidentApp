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

public class ThirdParty extends BaseFragment implements OnClickListener {
	
	private EditText driver_name, driver_id,driver_address, driver_phone_no, driver_license;
	private CheckBox isOwener;
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
	
		View rootView = inflater.inflate(R.layout.third_party,
				container, false);
		mContext = (MainActivity) this.getActivity();
		dBhelper = new DBhelper(getActivity());
		driver_name =(EditText) rootView.findViewById(R.id.driver_name);
		driver_id =(EditText) rootView.findViewById(R.id.driver_id);
		driver_address =(EditText) rootView.findViewById(R.id.driver_address);
		driver_phone_no =(EditText) rootView.findViewById(R.id.driver_phone_no);
		driver_license =(EditText) rootView.findViewById(R.id.driver_license);
		
		//Heading
		 mClose = (ImageView)rootView.findViewById(R.id.close);
	     mDelete = (ImageView)rootView.findViewById(R.id.delete);
	     HeadingText = (TextView)rootView.findViewById(R.id.heading);
	     HeadingText.setText(getResources().getString(R.string.thirdparty));
		
		isOwener =(CheckBox)rootView.findViewById(R.id.is_owener);
		Save = (Button)rootView.findViewById(R.id.cas_save);
		Save.setOnClickListener(this);
		mClose.setOnClickListener(this);
		mDelete.setOnClickListener(this);
		
		return rootView;
	}
	
	private void CallSaveButton(){
		String full_name, id,address, phone_no, DriverLicense;
		
		full_name =driver_name.getText().toString().trim();
		id=driver_id.getText().toString().trim();
		address=driver_address.getText().toString().trim();
		phone_no=driver_phone_no.getText().toString().trim();
		DriverLicense=driver_license.getText().toString().trim();
		
		if(!full_name.equals("") && !id.equals("") && !address.equals("") && !phone_no.equals("") && !DriverLicense.equals("")){
			currentIDs = mActivity.getIds();
		dBhelper.insertThirdParty(currentIDs,full_name, id, address, phone_no, DriverLicense, isOwener.isChecked(), isUpdate());
		Toast.makeText(getActivity(), "Data Saved", Toast.LENGTH_SHORT).show();
		}else{
			Toast.makeText(getActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show();
		}
	}
	
	private boolean isUpdate(){
		list = dBhelper.getData(dBhelper.TABLE_NAME_THIRD_PARTY);
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
		driver_name.setText("");
		driver_id.setText("");
		driver_address.setText("");
		driver_license.setText("");
		driver_phone_no.setText("");
			break;
		}
	}
}
