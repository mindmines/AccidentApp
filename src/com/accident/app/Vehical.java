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

public class Vehical extends BaseFragment implements OnClickListener {
	
	private EditText vehical_type, manufacturer,model, color, year,license_plate;
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
	
		View rootView = inflater.inflate(R.layout.vehical,
				container, false);
		mContext = (MainActivity) this.getActivity();
		dBhelper = new DBhelper(getActivity());
		
		vehical_type =(EditText) rootView.findViewById(R.id.vehical_type);
		manufacturer =(EditText) rootView.findViewById(R.id.manufacturer);
		model =(EditText) rootView.findViewById(R.id.model);
		color =(EditText) rootView.findViewById(R.id.color);
		year =(EditText) rootView.findViewById(R.id.year);
		license_plate =(EditText) rootView.findViewById(R.id.license_plate);
		
		//Heading
		 mClose = (ImageView)rootView.findViewById(R.id.close);
	     mDelete = (ImageView)rootView.findViewById(R.id.delete);
	     HeadingText = (TextView)rootView.findViewById(R.id.heading);
	     HeadingText.setText(getResources().getString(R.string.vehicle));
		
		Save = (Button)rootView.findViewById(R.id.cas_save);
		Save.setOnClickListener(this);
		mClose.setOnClickListener(this);
		mDelete.setOnClickListener(this);
		
		return rootView;
	}
	
	private void CallSaveButton(){
		String vehicalType, Manufacturer,vModel, vColor, vYear,licensePlate;
		
		vehicalType =vehical_type.getText().toString().trim();
		Manufacturer=manufacturer.getText().toString().trim();
		vModel=model.getText().toString().trim();
		vColor=color.getText().toString().trim();
		vYear=year.getText().toString().trim();
		licensePlate = license_plate.getText().toString().trim();
		
		if(!vehicalType.equals("") && !Manufacturer.equals("") && !vModel.equals("") && !vColor.equals("") && !vYear.equals("") && !licensePlate.equals("")){
			currentIDs = mActivity.getIds();
		dBhelper.insertVehical(currentIDs,vehicalType, Manufacturer, vModel, vColor, vYear, licensePlate, isUpdate());
		Toast.makeText(getActivity(), "Data Saved", Toast.LENGTH_SHORT).show();
		}else{
			Toast.makeText(getActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show();
		}
	}
	
	private boolean isUpdate(){
		list = dBhelper.getData(dBhelper.TABLE_NAME_VEHICLE);
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
		vehical_type.setText("");
		manufacturer.setText("");
		model.setText("");
		color.setText("");
		year.setText("");
		license_plate.setText("");
			break;
		}
	}
}
