package edu.uoc.pra2;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import edu.uoc.pra2.library.JSONParser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.os.SystemClock;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/*PANTALLA LOGIN APLICACIÓN*/

public class Login extends Activity {
	
    EditText user;
    EditText pass;
    Button blogin;
    
	// JSON parser class
	JSONParser jsonParser = new JSONParser();

	// URL
    String url_login="http://192.168.1.36/pra2envios/acces.php"; 
 
	// JSON Node 
	private static final String TAG_SUCCESS = "success";
	private ProgressDialog pDialog;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        user= (EditText) findViewById(R.id.editTextUsuario);
        pass= (EditText) findViewById(R.id.editTextPassword);
        blogin= (Button) findViewById(R.id.buttonLogin);
                    
        // Botón Login
        blogin.setOnClickListener(new View.OnClickListener(){
       
        	public void onClick(View view){
        		// Obtenemos los datos de los EditText
        		String usuario=user.getText().toString();
        		String passw=pass.getText().toString();
        		
        		// Verificamos si estan en blanco
        		if( checklogindata( usuario , passw )==true){
        			new asyncLogin().execute(usuario,passw);  //Ejecutamos el asynctask pasando el usuario y clave como parametros		               
          		}else{
        			err_login();//Si se detecta un error en la primera validación vibra y mostramos un Toast con un mensaje de error.
        		}
        	}
        });
     }
    
    // Vibra y mostramos el mensage de error
    public void err_login(){
    	Vibrator vibrator =(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
	    vibrator.vibrate(200);
	    Toast toast1 = Toast.makeText(getApplicationContext(),"Error: Nombre de usuario o contraseña incorrectos", Toast.LENGTH_SHORT);
 	    toast1.show();    	
    }
    
    
    //Valida el estado del login (parametros = usuario y contraseña)
    public boolean loginstatus(String username ,String password ) {

		// Enviamos los parámetros
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("usuario", username));
		params.add(new BasicNameValuePair("password", password));
		
		// Obtenemos el JSON string de la URL
		JSONObject json = jsonParser.makeHttpRequest(url_login, "GET", params);
		
		try {
			Log.d("Login: ", json.toString());
			
			int success = json.getInt(TAG_SUCCESS); // Verificamos el parámetro SUCCESS
			
			if (success == 1) {
	   			 Log.e("loginstatus ", "Usuario correcto");
    			 return true;
 			}else{
				Log.e("loginstatus ", "Usuario incorrecto");
   			 	return false;
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}
      }
          
    // Validamos si no hay ningun campo en blanco
    public boolean checklogindata(String username ,String password ){
	    if 	(username.equals("") || password.equals("")){
	    	Log.e("Login UI", "Error validando usuario o contraseña");
	    	return false;
	    }else{
	    	return true;
	    }
    }           
    
	/* CLASE ASYNCTASK
	 * La usaremos para poder mostrar el dialogo de progreso mientras envíamos y obtenemos los datos de la consulta
	 */
    
    class asyncLogin extends AsyncTask< String, String, String > {
    	String user,pass;
    	
        protected void onPreExecute() {
        	// Progress dialog - Indicamos el mensaje a mostrar
            pDialog = new ProgressDialog(Login.this);
            pDialog.setMessage("Verificando usuario..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
 
		protected String doInBackground(String... params) {
			//Obtenemos usr (usuario) y pass (contraseña)
			user=params[0];
			pass=params[1];
            
			//Enviamos y recibimos la verificación, después analizamos la respuesta.
    		if (loginstatus(user,pass)==true){    		    		
    			return "OK";  // Login correcto
    		}else{    		
    			return "ERROR"; // Error en el login     	          	  
    		}
		}
       
		/*Una vez terminado doInBackground si todo es correcto pasamos a la siguiente activity o sino mostramos el error*/
        protected void onPostExecute(String result) {
	           pDialog.dismiss();	//Ocultamos progess dialog.
	           
	           Log.e("onPostExecute=",""+result);
	           
	           if (result.equals("OK")){
					Intent i=new Intent(Login.this, Menu.class);
					i.putExtra("user",user);
					startActivity(i); 
	            }else{
	            	err_login();
	            }
        }
   }
 }
    


