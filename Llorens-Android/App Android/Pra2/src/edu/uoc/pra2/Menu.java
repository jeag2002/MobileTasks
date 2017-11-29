package edu.uoc.pra2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/*PANTALLA MENÚ PRINCIPAL*/

public class Menu extends Activity {
	 String user;
	 TextView txt_usr;
	 Button bsalir, bpunto_envio,bestado_envio;
	 
	 public void onCreate(Bundle savedInstanceState) {
		 
	       super.onCreate(savedInstanceState);
	       setContentView(R.layout.activity_menu);
            
	       txt_usr= (TextView) findViewById(R.id.tv_usuario);
	       
	       bpunto_envio = (Button) findViewById(R.id.buttonPuntoEnvio);
	       bestado_envio= (Button) findViewById(R.id.buttonEstadoEnvio);
	       bsalir= (Button) findViewById(R.id.buttonSalir);
	       
	       Bundle extras = getIntent().getExtras();
            
           //Obtenemos los datos enviados en el intent.
           if (extras != null) {
         	   user  = extras.getString("user"); //usuario
           }else{
         	   user="error";
           }
            
	       txt_usr.setText(user);//Ponemos el usuario que se ha logeado
	       
           bpunto_envio.setOnClickListener(new View.OnClickListener(){
	         	public void onClick(View view){
	         		Intent i = new Intent(getApplicationContext(), Localizacion.class);
	         		i.putExtra("paramUser",user);
					startActivity(i); 
	         								   }
	         	});
            
            bestado_envio.setOnClickListener(new View.OnClickListener(){
	         	public void onClick(View view){
	         		Intent i = new Intent(getApplicationContext(), Envios.class);
	         		i.putExtra("paramUser",user);
					startActivity(i); 
	         									}
	         	});
            bsalir.setOnClickListener(new View.OnClickListener(){
	         	public void onClick(View view){
	         		finish(); //Cerrar sesión -> volvemos a la ventana anterior
	         								  }
	         	});
	        
	 }
	 
	 //Definimos que para cuando se presione la tecla BACK no volvamos para atras  	 
	 @Override
	 public boolean onKeyDown(int keyCode, KeyEvent event)  {
	     if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
	         // no hacemos nada.
	         return true;
	     }
	     return super.onKeyDown(keyCode, event);
	 }
	
}
