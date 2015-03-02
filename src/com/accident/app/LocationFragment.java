package com.accident.app;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LocationFragment extends BaseFragment{
	MainActivity mContext;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.location,
				container, false);
		mContext = (MainActivity) this.getActivity();
		AppConstants.isFront = true;
		mContext.CallHeaderVisiblity();
		
		return rootView;
	}


}
