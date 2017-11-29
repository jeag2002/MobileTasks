/******************************************************************/
/* Funciones de navegacion de los diferentes formularios de la 	  */
/* aplicacion													  */ 	
/******************************************************************/


/********************************************************************************************/
/* FUNCIONES DE LA PANTALLA DE LOGIN										    			*/
/********************************************************************************************/
function evaluateAccess(){
	
	var name_1 = document.getElementById("name").value;
	var pass_1 = document.getElementById("pass").value;    
	
	if ((name_1 == "") || (pass_1 == "")){
		areYouSure("user/pass invalid");
	}else{
			
		//simulamos la conexión remota con la aplicación
		if (globalV.isOnline == false){
				
				var str = '{"user":"'+name_1+'","pass":"'+pass_1+'"}';
				var json = JSON.parse(str);
				
				var info = remoteDT.usuariosLogin.where(json);
				console.log(info);
				
				if (info.length==0){
					areYouSure("user/pass incorrect");
				}else{
					var data = info[0];
					if (data.get("perfil") != "tramitador"){
						areYouSure("user no tramitador");
					}else{
						console.log("usuario encontrado");
						
						//guarda el usuario-perfil. borra el usuario de los campos de login
						globalV.User = name_1;
						globalV.Perfil = data.get("perfil");
						
						document.getElementById("name").value = "";
						document.getElementById("pass").value = "";
						
						//realizamos un set del usuario en la pantalla principal
						document.getElementById("usergeneral").innerHTML=globalV.User;
						
						//iniciamos el clock
						timer.Clock.Timer.toggle();
						
						//cargamos los pedidos de un usuario
						var vista = new view.vistaEstado();
						vista.render();
						vista = null;
						
						$.mobile.changePage("#pagethree");
					}
				}					
		}
	}
}


function Exit(){
	
	console.log("Salimos de la aplicacion");
	
	if(navigator.app){
        navigator.app.exitApp();
	}else if(navigator.device){
       navigator.device.exitApp();
	}
}


function returnToPageOne(){
	
	console.log("Volvemos a login");
	
	timer.Clock.resetStopwatch();
	$.mobile.changePage("#pagetwo");
	
}

function goToGPS(){
	$.mobile.changePage("#pageseven");
}

/********************************************************************************************/

/********************************************************************************************/
/* FUNCIONES DE TRANSFORMACION   															*/
/********************************************************************************************/

function typeToIdType(idType){
	var upperType = idType.toUpperCase();
	if (upperType == "EXPRESS")
		return 1;
	else if (upperType == "NORMAL")
		return 2;
	else
		return 3;
}


function IdTypeToType(Type){
	if (Type == 1)
		return "EXPRESS";
	else if (Type == 2)
		return "NORMAL";
	else
		return "PLANIFIED";
	
	
}


function stateToIdState(idState){
	var upperState = idState.toUpperCase();
	if (upperState == "PURCHASING")
		return 1;
	else if (upperState == "PACKETING")
		return 2;
	else if (upperState == "DELIVERING")
		return 3;
	else
		return 4;
}


function IdStateTostate(State){
	
	if (State == 1)
		return "PURCHASING";
	else if (State == 2)
		return "PACKETING";
	else if (State == 3)
		return "DELIVERING";
	else
		return "ARRIVING";
	
	
}

//convierte un numero en un string con 0's delante.

function pad(num, size) {
    var s = num+"";
    while (s.length < size) s = "0" + s;
    return s;
}

/********************************************************************************************/
/* FUNCIONES DE ALTA/EDICION/CONSULTA														*/
/********************************************************************************************/

function getDataConsultaEdicion(idPedido,idType,idState,index){
	
	document.getElementById("codEnvio").value = idPedido;
	document.getElementById("delSit").value = "<IDLE>";
	document.getElementById("delType").selectedIndex = typeToIdType(idType)-1;
	document.getElementById("delState").selectedIndex = stateToIdState(idState)-1;
	
	if (document.getElementById("sel_"+idPedido).checked == true){
		
		console.log("EDICION");
		document.getElementById("codEnvio").setAttribute("disabled","disabled");
		document.getElementById("ActionDel").innerHTML = "MODIFY";
		
		/*
		document.getElementById("delSit").removeAttribute("disabled");
		document.getElementById("delType").removeAttribute("disabled");
		document.getElementById("delState").removeAttribute("disabled");
		document.getElementById("linkGPS").removeAttribute("disabled");
		*/
		/*
		document.getElementById("linkGPS").removeAttribute("disabled");
		document.getElementById("SaveData").removeAttribute("disabled");
		document.getElementById("SaveState").removeAttribute("disabled");
		*/
		
		$('.enableDisableGPS').show();
		$('.enableDisableSaveData').show();
		$('.enableDisableSaveState').show();
		
	}else{
		console.log("CONSULTA");
		document.getElementById("codEnvio").setAttribute("disabled","disabled");
		document.getElementById("ActionDel").innerHTML = "QUERY";
		
		/*
		document.getElementById("delSit").setAttribute("disabled","disabled");	
		document.getElementById("delType").setAttribute("disabled","disabled");
		document.getElementById("delState").setAttribute("disabled","disabled");
		document.getElementById("linkGPS").setAttribute("disabled","disabled");
		*/	
		/*
		document.getElementById("linkGPS").setAttribute("disabled","disabled");
		document.getElementById("SaveData").setAttribute("disabled","disabled");
		document.getElementById("SaveState").setAttribute("disabled","disabled");
		*/
		
		$('.enableDisableGPS').hide();
		$('.enableDisableSaveData').hide();
		$('.enableDisableSaveState').hide();
		
	}
	
	//cargamos los estados de un pedido
	var vista = new view.vistaEstado_1();
	vista.render(idPedido);
	vista = null;

	$.mobile.changePage("#pagesix");	
	
}


function getDataConsulta(idPedido,idType,idState){
	
	document.getElementById("codEnvio").value = idPedido;
	document.getElementById("delSit").value = "<IDLE>";
	document.getElementById("delType").selectedIndex = typeToIdType(idType)-1;
	document.getElementById("delState").selectedIndex = stateToIdState(idState)-1;
	document.getElementById("ActionDel").innerHTML = "QUERY";
	
	document.getElementById("codEnvio").setAttribute("disabled","disabled");
	
	
	/*
	document.getElementById("delSit").setAttribute("disabled","disabled");
	document.getElementById("codEnvio").setAttribute("disabled","disabled");
	document.getElementById("delType").setAttribute("disabled","disabled");
	document.getElementById("delState").setAttribute("disabled","disabled");
	document.getElementById("linkGPS").setAttribute("disabled","disabled");
	*/
	/*
	document.getElementById("linkGPS").setAttribute("disabled","disabled");
	document.getElementById("SaveData").setAttribute("disabled","disabled");
	document.getElementById("SaveState").setAttribute("disabled","disabled");
	*/
	
	$('.enableDisableGPS').hide()
	$('.enableDisableSaveData').hide();
	$('.enableDisableSaveState').hide();
	
	//cargamos los estados de un pedido
	var vista = new view.vistaEstado_1();
	vista.render(idPedido);
	vista = null;

	$.mobile.changePage("#pagesix");	
}

function altaEnvio(){
	
	console.log("ALTA DE COMANDA");
	
	document.getElementById("codEnvio").value = "";
	document.getElementById("codEnvio").value = pad(globalV.numeroPedidos,4);
	document.getElementById("ActionDel").innerHTML = "NEW";
	
	
	//inicializo variables
	document.getElementById("delSit").value = " ";
	document.getElementById("delType").selectedIndex = 0;
	document.getElementById("delState").selectedIndex = 0;
	
	//bloqueo campos que no se deben pulsar
	document.getElementById("codEnvio").setAttribute("disabled","disabled");
	
	$('.enableDisableGPS').show();
	$('.enableDisableSaveData').show();
	$('.enableDisableSaveState').hide();
	
	
	/*
	document.getElementById("linkGPS").removeAttribute("disabled");
	document.getElementById("SaveData").removeAttribute("disabled");
	document.getElementById("SaveState").setAttribute("disabled","disabled");
	*/
	//desbloqueo campos que se deben pulsar
	/*
	document.getElementById("delType").removeAttribute("disabled");
	document.getElementById("delState").removeAttribute("disabled");
	document.getElementById("linkGPS").removeAttribute("disabled");
	document.getElementById("SaveData").removeAttribute("disabled");
	*/
	
	
	document.getElementById("states").innerHTML = "";	
	
	$.mobile.changePage("#pagesix");
	
}


//creamos el pedido y el estado inicial
function guardarAltaDelivery(){
	
	console.log("GUARDAR EL ALTA");
	
	var usuario = globalV.User;
	var codEnvio = document.getElementById("codEnvio").value;
	var codState = pad(globalV.numeroEstados,4)
	var type = IdTypeToType(document.getElementById("delType").selectedIndex+1);
	var state = IdStateTostate(document.getElementById("delState").selectedIndex+1);
	var sit = document.getElementById("delSit").value;
	
	if(sit.trim()==""){
		sit = "Situacion Inicial";
	}else if (sit.indexOf("Codigo Error")!=-1){
		sit = "Situacion Inicial";
	}
	
	var jsonStrState = '{"user":"'+usuario+'","code_command":"'+codEnvio+'","code_state":"'+codState+'","state":"'+state+'","situation":"'+sit+'","timestamp":"00/00/0000 00:00:00"}';
	var jsonState = JSON.parse(jsonStrState);
	var estado1 = new remoteDB.Estado(jsonState);
	remoteDT.estadosLogin.add(estado1);
	
	var jsonStrComanda = '{"user":"'+usuario+'","code_command":"'+codEnvio+'","type":"'+type+'","state":"'+state+'"}';
	var jsonComanda = JSON.parse(jsonStrComanda);
	var pedido1 = new remoteDB.Comanda(jsonComanda);
	remoteDT.pedidosLogin.add(pedido1);
	
	globalV.numeroPedidos+=1;
	globalV.numeroEstados+=1;
		
	//renderizamos el alta
	var vista = new view.vistaEstado();
	vista.render();
	vista = null;
	
	$.mobile.changePage("#pagefive");
}

//damos de alta un nuevo estado
function guardarAltaState(){
	
	console.log("GUARDAR UN ESTADO");
	
	var usuario = globalV.User;
	var codEnvio = document.getElementById("codEnvio").value;
	var codState = pad(globalV.numeroEstados,4)
	
	var index_type = document.getElementById("delType").selectedIndex;
	var type = IdTypeToType(index_type+1);
	
	var index_state = document.getElementById("delState").selectedIndex;
	var state = IdStateTostate(index_state+1);
	
	var sit = document.getElementById("delSit").value;	
	
	
	var jsonStrState = '{"user":"'+usuario+'","code_command":"'+codEnvio+'","code_state":"'+codState+'","state":"'+state+'","situation":"'+sit+'","timestamp":"00/00/0000 00:00:00"}';
	var jsonState = JSON.parse(jsonStrState);
	
	var estado1 = new remoteDB.Estado(jsonState);
	remoteDT.estadosLogin.add(estado1);
	
	globalV.numeroEstados+=1;
	
	var idPedido = document.getElementById("codEnvio").value
	var vista = new view.vistaEstado_1();
	vista.render(idPedido);
	vista = null;
}

//guardar la modificacion (cambio de estado)

function guardarModificacionDelivery(){
	
	console.log("MODIFICACION COMANDA");
	
	var usuario = globalV.User;
	var codEnvio = document.getElementById("codEnvio").value;
	var type = IdTypeToType(document.getElementById("delType").selectedIndex+1);
	var state = IdStateTostate(document.getElementById("delState").selectedIndex+1);
	
	//consulta del pedido
	var jsonStrSearch = '{"user":"'+usuario+'","code_command":"'+codEnvio+'"}';
	var jsonSearch = JSON.parse(jsonStrSearch);
	var info = remoteDT.pedidosLogin.where(jsonSearch);
	var model = info[0];
	
	//modificacion del pedido 
	model.set("type",type);
	model.set("state",state);
	remoteDT.pedidosLogin.set([model],{remove: false});
	
	//renderizamos la modificacion
	document.getElementById("deliveries").innerHTML = "";
	document.getElementById("deliveries_1").innerHTML = "";
	
	var vista = new view.vistaEstado();
	vista.render();
	vista = null;
	
	$.mobile.changePage("#pagefive");
}


function processDataDelivery(){
	
	var codEnvio = document.getElementById("codEnvio").value;
	
	if (document.getElementById("sel_"+codEnvio)!=null){
		if(document.getElementById("sel_"+codEnvio).checked==true){
			console.log("guardamos la modificacion!");
			guardarModificacionDelivery();
		}
	}else{
		console.log("guardamos el alta");
		guardarAltaDelivery();
	}
}

/********************************************************************************************/
/* FUNCIONES GENERALES														    			*/
/********************************************************************************************/
//funcion que crea un popup general.
function areYouSure(text1) {
	  $("#sure .sure-2").text(text1);
	  $.mobile.changePage("#sure");
}

//return general del dialog de aviso
function goBack(){
    history.back();
    return false
}


