package com.accident.app;

import java.util.ArrayList;

import com.accident.app.util.GridItem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;

public class GridFragment extends BaseFragment {

	GridView gridView;
	ArrayList<GridItem> gridArray = new ArrayList<GridItem>();
	CustomGridViewAdapter customGridAdapter;
	AlertDialog.Builder alert;
	MainActivity mContext;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.grid, container, false);
		
		mContext = (MainActivity) this.getActivity();
		AppConstants.isFront = true;
		mContext.CallHeaderVisiblity();
		gridArray.clear();

		Bitmap fluIcon = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.dateandtime);
		Bitmap reportIcon = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.third_party);
		Bitmap newsIcon = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.vehicle);
		Bitmap vacIcon = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.insurance);
		Bitmap aboutIcon = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.police);
		Bitmap feedbackIcon = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.description);
		Bitmap casualIcon = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.casualties);
		Bitmap witnessIcon = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.witnesses);

		gridArray.add(new GridItem(fluIcon, getResources().getString(R.string.datetime)));
		gridArray.add(new GridItem(reportIcon, getResources().getString(R.string.thirdparty)));
		gridArray.add(new GridItem(newsIcon, getResources().getString(R.string.vehical)));
		gridArray.add(new GridItem(vacIcon, getResources().getString(R.string.insurance)));
		gridArray.add(new GridItem(aboutIcon, getResources().getString(R.string.police)));
		gridArray.add(new GridItem(feedbackIcon, getResources().getString(R.string.des)));

		gridArray.add(new GridItem(casualIcon, getResources().getString(R.string.casulty)));
		gridArray.add(new GridItem(witnessIcon, getResources().getString(R.string.witness)));

		gridView = (GridView) rootView.findViewById(R.id.gridView1);
		customGridAdapter = new CustomGridViewAdapter(getActivity(),
				R.layout.row_grid, gridArray);
		gridView.setAdapter(customGridAdapter);

		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
				switch (arg2) {
				
				case 0:
					mContext.pushFragments(AppConstants.TAB_DETAILS, new DateTime(), false, true);
					break;

				case 1:
					mContext.pushFragments(AppConstants.TAB_DETAILS, new ThirdParty(), false, true);
					
					break;

				case 2:
					mContext.pushFragments(AppConstants.TAB_DETAILS, new Vehical(), false, true);
				
					break;

				case 3:
					mContext.pushFragments(AppConstants.TAB_DETAILS, new Insurance(), false, true);
					
					break;

				case 4:
					mContext.pushFragments(AppConstants.TAB_DETAILS, new Police(), false, true);
					break;

				case 5:
					mContext.pushFragments(AppConstants.TAB_DETAILS, new Description(), false, true);
					break;

				case 6:
					mContext.pushFragments(AppConstants.TAB_DETAILS, new Casulty(), false, true);
					break;
				case 7:
					mContext.pushFragments(AppConstants.TAB_DETAILS, new WitnessFragment(), false, true);
					//i = new Intent(getActivity(),WittnessHolder.class);
					//startActivity(i);
					break;

				
				}

			}
		});

		

		return rootView;
	}
}
