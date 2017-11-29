package edu.uoc.pra2;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import edu.uoc.pra2.library.JSONParser;

/*PANTALLA MODIFICAR LOCALIZACI�N USUARIO*/

public class Localizacion extends Activity {
	
	private LocationManager locManager;
	private LocationListener locListener;
	private Location mobileLocation;

	private TextView txt_latitud;
	private TextView txt_longitud;
	private EditText editTextLocation;
	private Button buttonLocation;
	private Button buttonMapa;
	
	private String usuario;
	private String latitud;
	private String longitud;
	
	// Progress Dialog
	private ProgressDialog pDialog;
	
	// JSON parser class
	JSONParser jsonParser = new JSONParser();

	// URL
	private static String url_query="http://192.168.1.36/pra2envios/get_localizacion.php"; 
	private static String url_update="http://192.168.1.36/pra2envios/update_localizacion.php";
	
	// JSON Node 
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_LOCALIZACION = "localizacion";
	private static final String TAG_LATITUD = "latitud";
	private static final String TAG_LONGITUD = "longitud";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_localizacion);

		// Texto nueva localizaci�n
		editTextLocation = (EditText) findViewById(R.id.editTextLocation);
		
		// Bot�n obtener nueva localizaci�n
		buttonLocation = (Button) findViewById(R.id.buttonLocation);
		
		// Bot�n obtener nueva localizaci�n
		buttonMapa = (Button) findViewById(R.id.buttonMapa);

	    //** Obtenemos los datos enviados en el intent.
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
        	usuario  = extras.getString("paramUser");
        }else{
        	usuario="error";
        }
		
		new GetLocalizacion().execute(); //Obtenemos la localizaci�n actual

		buttonLocation.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				buttonObtenerLocalizacionClick();
			}
		});
		
		buttonMapa.setOnClickListener(new View.OnClickListener(){
         	public void onClick(View view){
         		Intent i = new Intent(getApplicationContext(), Mapa.class);
         		//i.putExtra("paramLatitud",txt_latitud.getText());
         		//i.putExtra("paramLongitud",txt_longitud.getText());
				startActivity(i); 
       		}
        });
				
		
	}

	/** Gets the current location and update the mobileLocation variable*/
	private void getCurrentLocation() {
		locManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		locListener = new LocationListener() {
			@Override
			public void onStatusChanged(String provider, int status,Bundle extras) {
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

			@Override
			public void onLocationChanged(Location location) {
				// TODO Auto-generated method stub
				mobileLocation = location;
			}
		};
		locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locListener);
	}

	private void buttonObtenerLocalizacionClick() {
		getCurrentLocation(); // Obtenemos la localizaci�n actual y modificamos la variable mobileLocation
		
		if (mobileLocation != null) {
			locManager.removeUpdates(locListener); // Se necesita para parar la obtenci�n de la localizaci�n y evitar consumo bateria.
			
			String londitude = "Longitud: " + mobileLocation.getLongitude();
			String latitude = "Latitud: " + mobileLocation.getLatitude();
			//String altitiude = "Altitud: " + mobileLocation.getAltitude();
			//String accuracy = "Accuracy: " + mobileLocation.getAccuracy();
			//String time = "Tiempo: " + mobileLocation.getTime();

			//editTextLocation.setText(londitude + "\n" + latitude + "\n"+ altitiude + "\n" + accuracy + "\n" + time);
			editTextLocation.setText(londitude + "\n" + latitude);
			
			latitud=""+mobileLocation.getLatitude();
			longitud=""+mobileLocation.getLongitude();
			
			new ModificarLocalizacion().execute();
		} else {
			editTextLocation.setText("No se ha encontrado la localizaci�n actual");
		}
	}
	
	public class GPS
	{
	    public String latitud;
	    public String longitud;
	}
	
	/**
	 * Background Async Task - Obtenemos la localizaci�n actual del usuario
	 * */
	class GetLocalizacion extends AsyncTask<String, String, GPS> {
		String latitud,longitud;
		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Localizacion.this);
			pDialog.setMessage("Obteniendo direcci�n env�o actual..");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Obtenemos la direcci�n de env�o actual del usuario
		 * */
		protected GPS doInBackground(String... args) {
			int success;
			Log.d("Usuario: ", usuario);
			
			// Enviamos los par�metros
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("usuario", usuario));
			
			// Obtenemos el JSON string de la URL
			JSONObject json = jsonParser.makeHttpRequest(url_query, "GET", params);
			
			try {

				Log.d("Localizaci�n: ", json.toString());
				
				success = json.getInt(TAG_SUCCESS); // Verificamos el par�metro SUCCESS
				
				if (success == 1) {
					// Obtenemos los datos de la localizaci�n JSON Array
					JSONArray localizacionObj = json.getJSONArray(TAG_LOCALIZACION);  
					
					// Obtenemos la localizaci�n object from JSON Array
					JSONObject localizacion = localizacionObj.getJSONObject(0);
					
					txt_longitud= (TextView) findViewById(R.id.TextViewLongitud);
					txt_latitud= (TextView) findViewById(R.id.TextViewLatitud);

					Log.d("Latitud: ", localizacion.getString(TAG_LATITUD));
					Log.d("Longitud: ", localizacion.getString(TAG_LONGITUD));

					GPS gps = new GPS();
			        gps.latitud = localizacion.getString(TAG_LATITUD);
			        gps.longitud = localizacion.getString(TAG_LONGITUD);
			        return gps;
				}else{
					// Localizaci�n del usuario no encontrada
					Log.d("Localizaci�n: ", "No encontrada");
					//return null;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(GPS gps) {
			txt_longitud.setText(gps.longitud);
			txt_latitud.setText(gps.latitud);
			
			// dismiss the dialog once got a Location
			pDialog.dismiss();
		}
	}
	
	/**
	 * Background Async - Actualizamos la localizaci�n de env�o del usuario
	 * */
	class ModificarLocalizacion extends AsyncTask<String,String,String> { 

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Localizacion.this);
			pDialog.setMessage("Modificando direcci�n env�o ..");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Modificar direcci�n env�o
		 * */
		protected String doInBackground(String... args) {
			Log.d("Usuario: ", usuario);
			Log.d("Latitud: ", latitud);
			Log.d("Longitud: ", longitud);
			
			// Enviamos los par�metros
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("usuario", usuario));
			params.add(new BasicNameValuePair("latitud", latitud));
			params.add(new BasicNameValuePair("longitud", longitud));
			
			// Obtenemos el JSON string de la URL
			JSONObject json = jsonParser.makeHttpRequest(url_update, "GET", params);
			try {
				int success = json.getInt(TAG_SUCCESS); // Verificamos la ejecuci�n
				if (success == 1) {
					Log.d("Update: ", "Update OK");
					finish();
				} else {
					// Error update localizaci�n
					Log.d("Update: ", "Error. No update");
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			return null;
		}

		/**
		 * Despues de completar el background task ocultamos el progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			pDialog.dismiss();//Ocultamos progess dialog.
		}
	}
}
