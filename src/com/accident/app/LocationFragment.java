package com.accident.app;


import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.accident.app.util.GPSService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class LocationFragment extends BaseFragment implements LocationListener{
	MainActivity mContext;
	
	
	GoogleMap mGoogleMap;
	double latitude, longitude;
	Location location;
	JSONArray content = null;
	//String latA, lngA;
	LatLng latLng;
	RelativeLayout LayoutMain,LayoutMap;
	
private static final String TAG_RESULT = "results";
	
	private static final String TAG_GEOMETRY = "geometry";
	private static final String TAG_GEOMETRY_LOCATION = "location";
	private static final String TAG_GEOMETRY_LOCATION_LAT = "lat";
	private static final String TAG_GEOMETRY_LOCATION_LNG = "lng";
	
	private static final String RESTAURANT_TAG = "Restaurant";
	
	// Google Map
    private GoogleMap googleMap;
    
    
    @Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.location,
				container, false);
		mContext = (MainActivity) this.getActivity();
		AppConstants.isFront = true;
		mContext.CallHeaderVisiblity();
		LayoutMain = (RelativeLayout)rootView.findViewById(R.id.location_layout);
		LayoutMap = (RelativeLayout)rootView.findViewById(R.id.location_layout_map);
		rootView.findViewById(R.id.click_hear).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//CallGetLocation();
			}
		});
		
		/*rootView.findViewById(R.id.click_hear_map).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				CallManScreenVisible();
			}
		});*/
		
		/*FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
		SupportMapFragment fragment = (SupportMapFragment) fragmentManager.findFragmentById(R.id.map_list);
		
		// Getting Google Map
		mGoogleMap = fragment.getMap();

		// Enabling MyLocation in Google Map
		mGoogleMap.setMyLocationEnabled(true);

		// Enable / Disable my location button
		mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
		
		location = new Location("Map");*/
		
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
			
			LatLng latLng = new LatLng(latitude,longitude);
			// Setting the position for the marker
			markerOptions.position(latLng);
			mGoogleMap.addMarker(markerOptions);
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
		CameraPosition cameraPosition = new CameraPosition.Builder()
				.target(latLng1) // Sets the center of the map to Mountain View
				.zoom(15) // Sets the zoom
				.bearing(90) // Sets the orientation of the camera to east
				.tilt(30) // Sets the tilt of the camera to 30 degrees
				.build(); // Creates a CameraPosition from the builder
		
		mGoogleMap.animateCamera(CameraUpdateFactory
				.newCameraPosition(cameraPosition));

		mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng1, 15));
		
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
