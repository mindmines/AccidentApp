package com.accident.app;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.accident.app.adapter.WittNessAdapter;
import com.accident.app.util.Witness;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

public class WittnessHolder extends Activity {

	Button btnCapturePicture;
	ListView imageList;

	WittNessAdapter wtnessAdapter;
	AlertDialog.Builder alert;

	public static ArrayList<Witness> wtList = new ArrayList<Witness>();
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cameraimageholder);

		btnCapturePicture = (Button) findViewById(R.id.btnCapturePicture);
		imageList = (ListView) findViewById(R.id.listCapturePicture);

		btnCapturePicture.setText("Add witness");
		
		wtnessAdapter = new WittNessAdapter(WittnessHolder.this, wtList);
		imageList.setAdapter(wtnessAdapter);

		final AlertDialog.Builder adb = new AlertDialog.Builder(this);
		final View view = LayoutInflater.from(getApplicationContext()).inflate(
				R.layout.wittness_alert, null);
		adb.setView(view);
		final Dialog dialog;

		adb.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				EditText edit_id = (EditText) view.findViewById(R.id.input_id);
				EditText edit_name = (EditText) view
						.findViewById(R.id.input_name);
				EditText edit_phno = (EditText) view
						.findViewById(R.id.input_phno);
				EditText edit_address = (EditText) view
						.findViewById(R.id.input_address);
				EditText edit_age = (EditText) view
						.findViewById(R.id.input_age);
				String id = edit_id.getText().toString();
				String name = edit_name.getText().toString();
				String phno = edit_phno.getText().toString();
				String address = edit_address.getText().toString();
				String age = edit_age.getText().toString();
				
				wtList.add(new Witness(id,name,phno,address,age));
				wtnessAdapter.notifyDataSetChanged();
				// Do something with value!
			}
		});

		adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// Canceled.
				dialog.cancel();
			}
		});

		dialog = adb.create();
		dialog.show();

		/**
		 * Capture image button click event
		 */
		btnCapturePicture.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.show();
			}
		});

	}

}
