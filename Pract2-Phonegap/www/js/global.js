/************************************************************************/
/* Elementos Globales del juego											*/
/************************************************************************/

function globalVariables(){
	
	//Offline-online (offline trabaja solo con el dispositivo/ online trabaja remotamente)
	this.isOnline = false;
	
	//GPS (en funcionamiento el GPS)
	this.IsGPS = true;
	
	//Actualizacion GPS automatica
	this.actGPS = false;
	
	//Act. GPS (5 minutos)
	this.GPSMin = 5; 
	
	//usuario Actual
	this.User = "";
	
	//pass Actual
	this.Pass = "";
	
	//perfil Actual
	this.Perfil = "";
	
	//numero de pedidos
	this.numeroPedidos = 1;
	
	//numero de estados
	this.numeroEstados = 1;
	
}

var globalV = new globalVariables();