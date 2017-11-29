package edu.uoc.pra2;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class Mapa extends FragmentActivity {
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        MostrarPosicionActualMapa();
    }
    
    private void MostrarPosicionActualMapa() {
 		GoogleMap mapa = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
	    if (mapa != null) { 

	    	mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL); //Definimos el tipo de mapa
	        //OPCIONES TIPOS MAPAS: GoogleMap.MAP_TYPE_HYBRID,GoogleMap.MAP_TYPE_SATELLITE,GoogleMap.MAP_TYPE_TERRAIN
	        mapa.setMyLocationEnabled(true); //Activamos la localización

	        Location miPosicion = mapa.getMyLocation();
	    	//LatLng miLatLng = new LatLng(42.3592169, 1.4594092);
	        if(miPosicion !=null){
		        LatLng miLatLng = new LatLng(miPosicion.getLatitude(),miPosicion.getLongitude());        
		        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(miLatLng, 13));
	
		        mapa.addMarker(new MarkerOptions()
		                .title("Mi posición")
		                .snippet("Punto localización GPS actual.")
		                .position(miLatLng));
	        }       
	    }
    }
}