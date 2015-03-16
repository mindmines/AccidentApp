package com.accident.app;

import java.util.ArrayList;

import android.app.Application;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;

public class AppConstants extends Application {
	
	public static final String TAB_DETAILS = "Details";
	public static final String TAB_LOCATION = "Location"; 
	public static final String TAB_PICTURES = "Pictures";
	public static final String TAB_IMAGES ="Images";
	
	public static String fullpath;
	
	public static final String ITEM0 = "item0";
	public static final String ITEM1 = "item1";
	public static final String ITEM2 = "item2";
	public static final String ITEM3 = "item3";
	public static final String ITEM4 = "item4";
	public static final String ITEM5 = "item5";
	public static final String ITEM6 = "item6";
	public static final String ITEM7 = "item7";
	public static final String ITEM8 = "item8";
	public static final String ITEM9 = "item9";
	
	public static boolean isFront = false;
	   //Your other constants, if you have them..
	
	public static String address = "";
	public static String mapImageUrl = "";
	
	public static Bitmap bitmap0;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
	}

}
