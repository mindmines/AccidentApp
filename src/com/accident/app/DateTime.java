package com.accident.app;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import com.accident.app.dbhelper.DBhelper;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class DateTime extends BaseFragment implements OnClickListener{
	
	private EditText Date, Time;
	private Button Save;
	int mHour, mMinute,mYear, mMonth, mDay;
	DBhelper dBhelper;
	ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
	int currentIDs;
	MainActivity mContext;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	
		View rootView = inflater.inflate(R.layout.date_time,
				container, false);
		mContext = (MainActivity) this.getActivity();
		AppConstants.isFront = false;
		mContext.CallHeaderVisiblity();
		dBhelper = new DBhelper(getActivity());
		
		Date =(EditText) rootView.findViewById(R.id.date);
		Time =(EditText) rootView.findViewById(R.id.time);
		Save = (Button)rootView.findViewById(R.id.cas_save);
		
		Date.setOnClickListener(this);
		Time.setOnClickListener(this);
		Save.setOnClickListener(this);
		
		return rootView;
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.date:
			CallDatePicker();
			break;
		case R.id.time:
			CallTimePicker();
			break;
		case R.id.cas_save:
			CallSaveButton();
			break;
		}
	}
	
	private void CallDatePicker(){
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		 
		DatePickerDialog dpd = new DatePickerDialog(getActivity(),
		        new DatePickerDialog.OnDateSetListener() {
		 
		            @Override
		            public void onDateSet(DatePicker view, int year,
		                    int monthOfYear, int dayOfMonth) {
		            	Date.setText(""+dayOfMonth + "-"
		                        + (monthOfYear + 1) + "-" + year);
		 
		            }
		        }, mYear, mMonth, mDay);
		dpd.show();
	}
	
	private void CallTimePicker(){
		final Calendar c = Calendar.getInstance();
		mHour = c.get(Calendar.HOUR_OF_DAY);
		mMinute = c.get(Calendar.MINUTE);
		TimePickerDialog tpd = new TimePickerDialog(getActivity(),
		 new TimePickerDialog.OnTimeSetListener() {
			 
	            @Override
	            public void onTimeSet(TimePicker view, int hourOfDay,
	                    int minute) {
	            	Time.setText(hourOfDay + ":" + minute);
	            }
	        }, mHour, mMinute, true);
	tpd.show();
	}
	
	private void CallSaveButton(){
		String putDate =Date.getText().toString().trim();
		String putTime =  Time.getText().toString().trim();
		if(!putDate.equals(null) && !putTime.equals(null)){
			currentIDs = mActivity.getIds();
		dBhelper.insertDateTime(currentIDs,putDate,putTime,isUpdate());
		Toast.makeText(getActivity(), "Data Saved", Toast.LENGTH_SHORT).show();
		}else{
			Toast.makeText(getActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show();
		}
	}
	
	private boolean isUpdate(){
		list = dBhelper.getData(dBhelper.TABLE_NAME_DATE_TIME);
		
		for(int i=0;i<list.size();i++){
			int s = (Integer) list.get(i).get(AppConstants.ITEM0);
			if(currentIDs == s)
				return true;
		
		}
		return false;
	}
	
}
