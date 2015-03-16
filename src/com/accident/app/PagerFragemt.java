package com.accident.app;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PagerFragemt extends BaseFragment{
	
	AppSectionsPagerAdapter mAppSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will display the three primary sections of the
	 * app, one at a time.
	 */
	ViewPager mViewPager;
	MainActivity mContext;
	Resources resorce;
	public static Fragment fragmentArray[] = 
	   {new DateTime(),
		new ThirdParty(),
		new Vehical(), 
		new Insurance(), 
		new Police(),
		new Description(), 
		new Casulty(), 
		new WitnessFragment()};
	public static String[] Striplist = {"Date Time","Third Party" , "Vehicle","Insurance",
		"Police","Description","Casualties","Witnesses"};
	public static ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
	int pos;
	
	public PagerFragemt(int pos){
		this.pos = pos;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	
		View rootView = inflater.inflate(R.layout.pager_fragement,
				container, false);
		
		mContext = (MainActivity) this.getActivity();
		mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(mContext.getSupportFragmentManager(), mContext);
		resorce = mContext.getResources();
		AppConstants.isFront = false;
		mContext.CallHeaderVisiblity();
		
		/*for(int m=0;m<800;m++){
		if(m<=7)
		fragmentList.add(fragmentArray[m]);
		else{
			fragmentList.add(fragmentArray[((m+1)%8)]);
		}
	}*/
		
		// Set up etween sections.
		mViewPager = (ViewPager)rootView.findViewById(R.id.pager);
		
		mViewPager.setAdapter(mAppSectionsPagerAdapter);
		mViewPager.setCurrentItem(pos);
		
		return rootView;
	}
	
	public static class AppSectionsPagerAdapter extends FragmentPagerAdapter {

		Context mContext;
		public AppSectionsPagerAdapter(FragmentManager fm, Context mContext) {
			super(fm);
			this.mContext = mContext;
		}

		@Override
		public Fragment getItem(int i) {
				Log.e("Pring i",""+i);
				//return AppConstants.fragmentList.get(i);
				return fragmentArray[i];
		}

		@Override
		public int getCount() {
			return fragmentArray.length;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			
			/*if(position > Striplist.length){
				position = position%Striplist.length;
			}*/
			return Striplist[position];
			//return fragmentList.get(position);
		}
	}

}
