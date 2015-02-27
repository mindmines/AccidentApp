package com.accident.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment {
	public MainActivity mActivity;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mActivity		=	(MainActivity) this.getActivity();
	}
	
	public boolean onBackPressed(){
		return false;
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data){
		
	}
}
