package com.accident.app;


import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.accident.app.util.GPSService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Dialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LocationFragment extends BaseFragment implements LocationListener{
	MainActivity mContext;
	
	GoogleMap mGoogleMap;
	double latitude = 0, longitude= 0;
	Location location;
	JSONArray content = null;
	LatLng latLng;
	RelativeLayout LayoutMain,LayoutMap;
	
	ImageButton DeleteLocation,RefreshLocation;
	TextView CurrentLocationAdd;
	
	private static View rootView;
    @Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		 if (rootView != null) {
		        ViewGroup parent = (ViewGroup) rootView.getParent();
		        if (parent != null)
		            parent.removeView(rootView);
		    }
		
		 TranslateAnimation animation = new TranslateAnimation(0.0f, 400.0f,
		            0.0f, 0.0f);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
		    animation.setDuration(5000);  // animation duration 
		    animation.setRepeatCount(500);  // animation repeat count
		    //animation.setRepeatMode(2);   // repeat animation (left to right, right to left )
		    //animation.setFillAfter(true);   
		    
	try{
		rootView = inflater.inflate(R.layout.location,
				container, false);
		DeleteLocation = (ImageButton)rootView.findViewById(R.id.delete_location);
		RefreshLocation = (ImageButton)rootView.findViewById(R.id.refresh_location);
		CurrentLocationAdd = (TextView)rootView.findViewById(R.id.current_location_add);
		CurrentLocationAdd.setAnimation(animation);
	}catch(InflateException e){
		e.printStackTrace();
	}
	
	
		mContext = (MainActivity) this.getActivity();
		AppConstants.isFront = true;
		mContext.CallHeaderVisiblity();
		LayoutMain = (RelativeLayout)rootView.findViewById(R.id.location_layout);
		LayoutMap = (RelativeLayout)rootView.findViewById(R.id.location_layout_map);
		rootView.findViewById(R.id.click_hear).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				CallGetLocation();
			}
		});
		
		rootView.findViewById(R.id.click_hear_map).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				CallManScreenVisible();
			}
		});
		
		int status = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getActivity());

		// Showing status
		if (status != ConnectionResult.SUCCESS) { // Google Play Services are
													// not available

			int requestCode = 10;
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status,
					getActivity(), requestCode);
			dialog.show();

		}else{
			
		FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
		SupportMapFragment fragment = (SupportMapFragment) fragmentManager.findFragmentById(R.id.map_list);
		
		// Getting Google Map
		mGoogleMap = fragment.getMap();

		// Enabling MyLocation in Google Map
		mGoogleMap.setMyLocationEnabled(true);

		// Enable / Disable my location button
		//mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
		
		// Enable / Disable zooming controls
		mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
		
		// Enable / Disable zooming functionality
		mGoogleMap.getUiSettings().setZoomGesturesEnabled(true);
		
					
		location = new Location("Map");
		
		}
		return rootView;
	}

	private void CallGetLocation(){
		MarkerOptions markerOptions = new MarkerOptions();
		GPSService mGPSService = new GPSService(getActivity());
		mGPSService.getLocation();
		
		if (mGPSService.isLocationAvailable == false) {

			// Here you can ask the user to try again, using return; for that
			Toast.makeText(getActivity(), "Your location is not available,May be your Gps is Off please try again.", Toast.LENGTH_SHORT).show();
			return;

			// Or you can continue without getting the location, remove the return; above and uncomment the line given below
			// address = "Location not available";
		} else {
			// Getting location co-ordinates
			latitude = mGPSService.getLatitude();
			longitude = mGPSService.getLongitude();
			
			AppConstants.address = mGPSService.getLocationAddress();
			CurrentLocationAdd.setText(AppConstants.address);
			
			LatLng latLng = new LatLng(latitude,longitude);
			// Setting the position for the marker
			markerOptions.position(latLng);
			BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.car_crash);

			markerOptions.icon(icon);
			mGoogleMap.addMarker(markerOptions);
			
			location.setLatitude(latitude);
			location.setLongitude(longitude);
			
			onLocationChanged(location);
			
			LayoutMain.setVisibility(View.GONE);
			LayoutMap.setVisibility(View.VISIBLE);
			
		mGPSService.closeGPS();
	}
	
	}
	
	@Override
	public void onLocationChanged(Location location1) {
		// TODO Auto-generated method stub
		
		latitude = location1.getLatitude();
		longitude = location1.getLongitude();
		LatLng latLng1 = new LatLng(latitude, longitude);
		
		mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng1, 15));

		// Zoom in, animating the camera.
		mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null); 

		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	
	private void CallManScreenVisible(){
		LayoutMain.setVisibility(View.VISIBLE);
		LayoutMap.setVisibility(View.GONE);
	}

}
