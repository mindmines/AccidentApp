package com.accident.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A dummy fragment representing a section of the app, but that simply
 * displays dummy text.
 */
public class DummySectionFragment extends BaseFragment {

	public static final String ARG_SECTION_NUMBER = "section_number";
	MainActivity mContext;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_section_dummy,
				container, false);
		mContext = (MainActivity) this.getActivity();
		AppConstants.isFront = true;
		mContext.CallHeaderVisiblity();
		Bundle args = getArguments();
		/*((TextView) rootView.findViewById(android.R.id.text1))
				.setText(getString(R.string.dummy_section_text,
						args.getInt(ARG_SECTION_NUMBER)));*/
		
		
		rootView.findViewById(R.id.click_hear).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//CallGetLocation();
				//Toast.makeText(getActivity(), "Clicked", 1).show();
//				Intent i  = new Intent(getActivity(),DamageCarMarkActivity.class);
//				startActivity(i);
				
				mContext.pushFragments(AppConstants.TAB_IMAGES, new DamageCarMark(), false, true);
			}
		});
		
		return rootView;
	}
}