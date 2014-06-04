package com.example.mycontactlist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class SensorActivity extends Activity{
	
	Location currentBestLocation;
	LocationManager locationManager;
	LocationListener gpsListener;
	LocationListener networkListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sensor_layout);
		initListButton();
		initMapButton();
		initSettingsButton();
		initGetLocation();
	}
	
	private boolean isBetterLocation(Location location){
		boolean isBetter = false;
		if(currentBestLocation == null){
			isBetter = true;
		}
		else if(location.getAccuracy() <= currentBestLocation.getAccuracy()){
			isBetter = true;
		}
		else if(location.getTime() - currentBestLocation.getTime() > 5*60*1000){
			isBetter = true;
		}
		return isBetter;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contact_map, menu);
		return true;
	}
	
	@Override
	public void onPause(){
		try{
			locationManager.removeUpdates(gpsListener);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		super.onPause();
	}
	
	private void initGetLocation(){
				try{
					locationManager = (LocationManager)getBaseContext().getSystemService(Context.LOCATION_SERVICE);
					gpsListener = new LocationListener(){
						public void onLocationChanged(Location location){
							TextView txtLatitudeGPS = (TextView)findViewById(R.id.textLatitudeGPS);
							TextView txtLongitudeGPS = (TextView)findViewById(R.id.textLongitudeGPS);
							TextView txtAccuracyGPS = (TextView)findViewById(R.id.textAccuracyGPS);
							txtLatitudeGPS.setText(String.valueOf(location.getLatitude()));
							txtLongitudeGPS.setText(String.valueOf(location.getLongitude()));
							txtAccuracyGPS.setText(String.valueOf(location.getAccuracy()));
							
							if(isBetterLocation(location)){
								currentBestLocation = location;
							}
							TextView txtLatitude = (TextView)findViewById(R.id.textLatitudeBest);
							TextView txtLongitude = (TextView)findViewById(R.id.textLongitudeBest);
							TextView txtAccuracy = (TextView)findViewById(R.id.textAccuracyBest);
							txtLatitude.setText(String.valueOf(location.getLatitude()));
							txtLongitude.setText(String.valueOf(location.getLongitude()));
							txtAccuracy.setText(String.valueOf(location.getAccuracy()));
						}
						public void onStatusChanged(String provider, int status, Bundle extras){}
						public void onProviderEnabled(String provider){}
						public void onProviderDisabled(String provider){}
					};
					networkListener = new LocationListener(){
						public void onLocationChanged(Location location){
							TextView txtLatitudeNet = (TextView)findViewById(R.id.textLatitudeNet);
							TextView txtLongitudeNet = (TextView)findViewById(R.id.textLongitudeNet);
							TextView txtAccuracyNet = (TextView)findViewById(R.id.textAccuracyNet);
							txtLatitudeNet.setText(String.valueOf(location.getLatitude()));
							txtLongitudeNet.setText(String.valueOf(location.getLongitude()));
							txtAccuracyNet.setText(String.valueOf(location.getAccuracy()));
							if(isBetterLocation(location)){
								currentBestLocation = location;
							}
							TextView txtLatitude = (TextView)findViewById(R.id.textLatitudeBest);
							TextView txtLongitude = (TextView)findViewById(R.id.textLongitudeBest);
							TextView txtAccuracy = (TextView)findViewById(R.id.textAccuracyBest);
							txtLatitude.setText(String.valueOf(location.getLatitude()));
							txtLongitude.setText(String.valueOf(location.getLongitude()));
							txtAccuracy.setText(String.valueOf(location.getAccuracy()));
						}
						public void onStatusChanged(String provider, int status, Bundle extras){}
						public void onProviderEnabled(String provider){}
						public void onProviderDisabled(String provider){}
					};
					locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, gpsListener);
					locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, networkListener);
				}
				catch(Exception e){
					Toast.makeText(getBaseContext(), "Error, Location not available", Toast.LENGTH_LONG);
				}
			}
	private void initListButton() {
        ImageButton list = (ImageButton) findViewById(R.id.imageButtonList);
        list.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
    			Intent intent = new Intent(SensorActivity.this, ContactListActivity.class);
    			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    			startActivity(intent);
            }
        });
	}
	
	private void initMapButton() {
        ImageButton list = (ImageButton) findViewById(R.id.imageButtonMap);
        list.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
			Intent intent = new Intent(SensorActivity.this, ContactMapActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
        }
        });
	}
	
	private void initSettingsButton() {
        ImageButton list = (ImageButton) findViewById(R.id.imageButtonSettings);
        list.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
    			Intent intent = new Intent(SensorActivity.this, ContactSettingsActivity.class);
    			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    			startActivity(intent);
            }
        });
	}
	
}
