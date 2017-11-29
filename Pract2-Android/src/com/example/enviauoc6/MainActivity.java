package com.example.enviauoc6;

/*
 * http://stackoverflow.com/questions/1513485/how-do-i-get-the-current-gps-location-programmatically-in-android?lq=1
 * http://stackoverflow.com/questions/25563364/android-4-4-2-get-device-location-coordinates
 * http://www.programcreek.com/java-api-examples/android.location.LocationListener
 * http://stackoverflow.com/questions/2279647/how-to-emulate-gps-location-in-the-android-emulator
 * https://developers.google.com/maps/documentation/android/start#getting_the_google_maps_android_api_v2
 */


import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;


public class MainActivity extends Activity{
	
	public final static String EXTRA_MESSAGE = "com.example.enviauoc6.MESSAGE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		 EditText usuario = (EditText) findViewById(R.id.user);
		 EditText password = (EditText) findViewById(R.id.pass);
		 TextView error = (TextView) findViewById(R.id.textView1);
		 
		 usuario.setText("");
		 password.setText("");
		 error.setText("");
		
		
		
	}
	
	public void sendMessage(View view) {
		  Intent intent = new Intent(this, GPSActivity.class);
		  EditText usuario = (EditText) findViewById(R.id.user);
		  EditText password = (EditText) findViewById(R.id.pass);
		  
		  //aqui comprobar que ambos, usuario/password son los correctos
		  String user = usuario.getText().toString();
		  String pass = password.getText().toString();
		  
		  if (user.equalsIgnoreCase("usuario2") && pass.equalsIgnoreCase("usuario2")){
			  intent.putExtra(EXTRA_MESSAGE, user);
			  startActivity(intent);
		  }else{
			  TextView error = (TextView) findViewById(R.id.textView1);
			  error.setText("Usuario NO válido!");
		  }
		  
	}
	
	public void exitApp(View view){
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		finish();
		System.exit(0);
	}
	
	
}
