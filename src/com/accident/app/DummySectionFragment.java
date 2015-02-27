package com.accident.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A dummy fragment representing a section of the app, but that simply
 * displays dummy text.
 */
public class DummySectionFragment extends BaseFragment {

	public static final String ARG_SECTION_NUMBER = "section_number";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_section_dummy,
				container, false);
		Bundle args = getArguments();
		/*((TextView) rootView.findViewById(android.R.id.text1))
				.setText(getString(R.string.dummy_section_text,
						args.getInt(ARG_SECTION_NUMBER)));*/
		return rootView;
	}
}