package com.example.enviauoc6;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.Button;

public class GPSActivity extends Activity {

	private static final long MIN_TIME_BW_UPDATES = 200;
	private static final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gps);
		
		//volcamos datos de MainActivity
		Intent intent = getIntent();
		String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
		
		TextView loginUser = (TextView) findViewById(R.id.textView2);
		loginUser.setText("Wellcome " + message);
		
		//definimos los pedidos (siempre serán los mismos).
		
		/*******************************************************************************************/
		LinearLayout LL_1 =  (LinearLayout)findViewById(R.id.linearLayout1);
		
		//primera linea:
		LinearLayout LL_1_1 = new LinearLayout(this);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		
		LL_1_1.setOrientation(LinearLayout.HORIZONTAL);
		 
		//image
		ImageView view = new ImageView(this);
		view.setLayoutParams(params);
		view.setImageResource(R.drawable.user_1);
		//@drawable/user_1
		LL_1_1.addView(view);
		
		
		//textView
		TextView data = new TextView(this);
		String informacion = "";
		informacion += "Code:\t0000\n";
		informacion += "Type:\tNormal\n";
		informacion += "State:\tPurchasing\n";		
		data.setText(informacion);
		data.setId(5);
		data.setLayoutParams(params);
		LL_1_1.addView(data);
		
		//button
		ImageView view_2 = new ImageView(this);
		view_2.setLayoutParams(params);
		view_2.setImageResource(R.drawable.caratblack);
		LL_1_1.addView(view_2);
		
		LL_1.addView(LL_1_1);
		
		
		LinearLayout LL_1_2 = new LinearLayout(this);
		LL_1_2.setOrientation(LinearLayout.HORIZONTAL);
		 
		//image
		ImageView view_1 = new ImageView(this);
		view_1.setLayoutParams(params);
		view_1.setImageResource(R.drawable.user_1);
		LL_1_2.addView(view_1);
		
		//textView
		TextView data_1 = new TextView(this);
		String informacion_1 = "";
		informacion_1 += "Code:\t0001\n";
		informacion_1 += "Type:\tNormal\n";
		informacion_1 += "State:\tPurchasing\n";
		data_1.setText(informacion_1);
		data_1.setId(5);
		data.setLayoutParams(params);
		LL_1_2.addView(data_1);
		
		//button
		ImageView view_3 = new ImageView(this);
		view_3.setLayoutParams(params);
		view_3.setImageResource(R.drawable.caratblack);
		LL_1_2.addView(view_3);

		
		LL_1.addView(LL_1_2);
		/*******************************************************************************************/
	}
	
	
	//obtiene los datos del GPS y los muestra en el textField.
	public void getGPS(View view) {	
		
		String location = getLocation();
		TextView gps_txt = (TextView) findViewById(R.id.textView5);
		gps_txt.setText(location);
	}
	
	
	//retorna a la pantalla principal.
	public void returnMain(View view) {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
	
	//http://stackoverflow.com/questions/6330200/how-to-quit-android-application-programmatically
	//sale de la aplicacion
	
	
	//recupera la informacion de localizacion GPS.
	private String getLocation(){	
		String res ="";
		
		double latitude = 0;	
		double longitude = 0;
		
		LocationManager locationManager = (LocationManager) this.getBaseContext().getSystemService(LOCATION_SERVICE);
		
		Location location = null;
		
		LocationListener lListener = new LocationListener() {
	        public void onLocationChanged(Location location) {}
	        public void onProviderDisabled(String provider) {}
	        public void onProviderEnabled(String provider) {}
	        public void onStatusChanged(String provider, int status, Bundle extras) {}
	    };

		
		// getting GPS status
		boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

		if (isGPSEnabled) {
		    if (location == null) {
		        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_TIME_BW_UPDATES,MIN_DISTANCE_CHANGE_FOR_UPDATES, lListener, null);
		        if (locationManager != null) {
		             location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		        if (location != null) {
		            	latitude = location.getLatitude();
		            	longitude = location.getLongitude();
		            	
		            	res += "GPS - latitude: " + String.valueOf(latitude) + "\n";
		            	res += "GPS - longitude: " + String.valueOf(longitude) + "\n";
		            }
		        }
		    }
		}
		// getting network status
		boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

		if (isNetworkEnabled) {
		    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, lListener, null);
		    if (locationManager != null) {
		        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		        if (location != null) {                         
		            latitude = location.getLatitude();
		            longitude = location.getLongitude();
		            
	            	res += "Network - latitude: " + String.valueOf(latitude) + "\n";
	            	res += "Network - longitude: " + String.valueOf(longitude) + "\n";
		            
		        }
		    }
		}
		return res;
	}
	
	
	
}
