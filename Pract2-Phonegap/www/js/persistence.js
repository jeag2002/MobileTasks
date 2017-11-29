

/*Caja negra persistencia en modo local. Backbone permite la creación de modelos (Model,Collection)*/
/*vistas (gestion de datos de presentacion y plantillas) (view, templates)						   */
/* y controladores, permite la navegacion entre ventanas y mecanismos ajax y push con un servidor  */
/*(route)																						   */
/******************************************************************/

function RemoteDataIn(){
	
	//listado de usuarios
	var usuario1 = new remoteDB.Usuario({user: "usuario1", pass: "usuario1", perfil: "usuario"});
	var usuario2 = new remoteDB.Usuario({user: "usuario2", pass: "usuario2", perfil: "tramitador"});
	var usuarios = new remoteDB.Usuarios([usuario1, usuario2]);
	this.usuariosLogin = usuarios;
	
	//listado de pedidos-estados por pedido.	
	var estado1 = new remoteDB.Estado({user: "usuario2", code_command: "00000", code_state: "00000", state: "PURCHASING", situation: "Sit. Inicial", timestamp: "00/00/0000 00:00:00"});
	var estados = new remoteDB.Estados([estado1]);
	this.estadosLogin = estados;
	
	//listado de pedidos
	var pedido1 = new remoteDB.Comanda({user: "usuario2", code_command: "00000", type: "NORMAL", state: "PURCHASING"});
	var pedidos = new remoteDB.Comandas([pedido1]);
	this.pedidosLogin = pedidos;
	
}
var remoteDT = new RemoteDataIn();
/******************************************************************/


/*Primera propuesta para almacenamiento en local, utilizando una base de datos SQLite*/
/*No esta probado!																	 */
/******************************************************************/
function RemoteDataSQLite(){
	
	this.db = null; 					/*objeto connexion*/
	this.tx_1 = null; 					/*objeto datasource*/
	this.initialize = createDatabase; 	/*creamos la base de datos*/
	
	this.populateDB = populateDB; 	  	/*instrucciones de creacion de tablas/base de datos*/
	this.errorCB = errorCB;			  	/*mensajes de error*/	
	this.successCB = successCB;		  	/*query ejecutada correctamente*/	
	
	this.queryUsers = queryUsers;			/*funcion para realizar la consulta de usuarios*/
	this.queryDeliveries = queryDeliveries; /*funcion para realizar la consulta de pedidos*/
	this.queryStates = queryStates;			/*funcion para realizar la consulta de estados*/
	this.querySuccess = querySuccess;	 	/*funcion que recupera los resultados de la consulta*/	
	
	this.insertDeliveries = insertDeliveries;
}
var remoteSQL = new RemoteDataSQLite();

//creamos la base de datos
function createDatabase(){
	
	with(this){
		db = window.sqlitePlugin.openDatabase("Database", "1.0", "Demo", -1);
		db.transaction(populateDB, errorCB, successCB);
	}
	
}
//creamos las tablas
function populateDB(tx) {
	 
	with(this){
		 tx_1 = tx;
	 }
	 
	 //usuarios
	 tx.executeSql('DROP TABLE IF EXISTS users');
	 tx.executeSql('CREATE TABLE IF NOT EXISTS users (id integer primary key, user text, pass text, role text)');
	 tx.executeSql('INSERT INTO users (user, pass, role) VALUES (?,?,?)', ['usuario1', 'usuario1', 'usuario']);
	 tx.executeSql("INSERT INTO users (user, pass, role) VALUES (?,?,?)", ['usuario2', 'usuario2', 'tramitador']);
	 
	 //pedidos
	 tx.executeSql('DROP TABLE IF EXISTS deliveries');
	 tx.executeSql('CREATE TABLE IF NOT EXISTS deliveries (id integer primary key, user text, code_command text, type text, state text)');
	 tx.executeSql('INSERT INTO deliveries (user, code_command, type, state) VALUES (?,?,?,?)', ['usuario1', '0000', 'NORMAL', 'PURCHASING']);
	 
	 //estados
	 tx.executeSql('DROP TABLE IF EXISTS states');
	 tx.executeSql('CREATE TABLE IF NOT EXISTS states (id integer primary key, user text, code_command text, code_state text, state text, situation text, timestamp text)');
	 tx.executeSql('INSERT INTO states (user, code_command, code_state, state, situation, timestamp) VALUES (?,?,?,?,?,?)', ['usuario1', '0000', '0000', 'PURCHASING', 'Sit. Inicial', '00/00/0000 00:00:00']);
}	

//Error Callback
function errorCB(err) {
	console.log("Error SQL: " + err.code);
}

//Success Callback
function successCB() {
	console.log("Database created OK");	
}

//Query - Usuarios
function queryUsers(){
	with(this){
		tx_1.executeSql("select * from users", [], querySuccess, errorCB);
	}
}

//Query - Deliveries
function queryDeliveries(user){
	with(this){
		tx_1.executeSql("select * from deliveries where code_command = ?", [user], querySuccess, errorCB);
	}
}

//Query - States
function queryStates(user,cod_ped){
	with(this){
		tx_1.executeSql("select * from states where user = ? and code_command = ?", [user,cod_ped], querySuccess, errorCB);
	}
}

//insert Deliveries user, code_command, type, state
function insertDeliveries(user, code_command, type, state){
	with(this){
		 tx.executeSql('INSERT INTO deliveries (user, code_command, type, state) VALUES (?,?,?,?)', [user, code_command, type, state]);
	}
}

//result of Query
function querySuccess(tx,results){}

/******************************************************************/