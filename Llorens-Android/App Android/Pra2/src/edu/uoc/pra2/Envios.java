package edu.uoc.pra2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.uoc.pra2.library.JSONParser;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.TextView;

/*PANTALLA LISTAR ENVÍOS USUARIO*/

public class Envios extends ListActivity {
	
	private String usuario;

	private ProgressDialog pDialog;
	
	JSONParser jParser = new JSONParser();
	
	ArrayList<HashMap<String, String>> enviosList;
		
	// URL obtener envíos
	private static String url_connect="http://192.168.1.36/pra2envios/get_envios.php";

	// JSON Node 
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_ENVIO = "envios";
	private static final String TAG_ID_ENVIO = "id_envio";
	private static final String TAG_NOM_ENVIO = "nom_envio";
	private static final String TAG_ESTAT_ENVIO = "estat_envio";
	private static final String TAG_DATA = "data";
	// JSONArray envíos
	JSONArray envios = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_list);
		
		Bundle extras = getIntent().getExtras();
		
        //Obtenemos los datos enviados en el intent.
        if (extras != null) {
        	usuario  = extras.getString("paramUser");
        }else{
        	usuario="error";
        }
         
		// Hashmap for ListView
		enviosList = new ArrayList<HashMap<String, String>>();

		// Cargando envíos in Background Thread
		new LoadEnvios().execute();

		// Obtenemos el listview
		ListView lv = getListView();

		// Seleccionamos un envío
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				// Obtenemos el valor de id_envio de la lista (Campo oculto)
				String id_envio = ((TextView) view.findViewById(R.id.id_envio)).getText().toString();
				Toast.makeText(Envios.this, "Número envío: "+id_envio, Toast.LENGTH_SHORT).show();
			}
		});

	}

	/**
	 * Background Async Task - Obtenemos todos los envíos hechos por HTTP Request
	 * */
	
	class LoadEnvios extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Envios.this);
			pDialog.setMessage("Cargando envíos "+usuario+"..");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		// Obtener envíos de un usuario
		protected String doInBackground(String... args) {
			
			Log.d("Usuario: ", usuario.toString());

			// Enviamos los parámetros
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("usuario", usuario));
			
			// Obtenemos el JSON string de la URL
			JSONObject json = jParser.makeHttpRequest(url_connect, "GET", params);

			Log.d("Envios usuario: ", json.toString());
			
			try {
				
				int success = json.getInt(TAG_SUCCESS); // Verificamos el parametro SUCCESS

				if (success == 1) { 					     
					
					envios = json.getJSONArray(TAG_ENVIO); // Obtenemos el Array de ENVIOS

					// Obtenemos los envíos del array
					for (int i = 0; i < envios.length(); i++) {
						JSONObject c = envios.getJSONObject(i);

						// Guardamos cada objecto JSON en una variable
						String id = c.getString(TAG_ID_ENVIO);
						String name = c.getString(TAG_NOM_ENVIO);
						String state = c.getString(TAG_ESTAT_ENVIO);
						String date = c.getString(TAG_DATA);
						
						// Creamos un nuevo HashMap
						HashMap<String, String> map = new HashMap<String, String>();

						//Añadimos cada variable
						map.put(TAG_ID_ENVIO, id);
						map.put(TAG_NOM_ENVIO, name);
						map.put(TAG_ESTAT_ENVIO, state+" "+date);

						// Añadimos el HashList al ArrayList
						enviosList.add(map);
					}
				} else {
					
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			return null;
		}

		//Ejecución después de completar background task
		protected void onPostExecute(String file_url) {
			pDialog.dismiss(); //Ocultamos progess dialog.

			// Modificamos el UI - Background Thread
			runOnUiThread(new Runnable() {
				public void run() {
					//Actualizacmos el parsed JSON data a la ListView
					ListAdapter adapter = new SimpleAdapter(
							Envios.this, enviosList,
							R.layout.activity_list_camps, new String[] {TAG_ID_ENVIO,TAG_NOM_ENVIO,TAG_ESTAT_ENVIO},new int[] { R.id.id_envio, R.id.nom_envio, R.id.estat_envio });
					//Actualizando listview
					setListAdapter(adapter);
				}
			});

		}
	}
}
