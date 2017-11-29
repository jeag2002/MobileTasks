<?php
$response = array();

// incluimos db connect class
require_once __DIR__ . '/db_connect.php';

$db = new DB_CONNECT(); //Conectamos con la BD
if (isset($_GET["usuario"])) { //Parametros recibidos
	$usuario = $_GET['usuario'];

	$result = mysql_query("SELECT * FROM envios WHERE username = '$usuario'") or die(mysql_error());
	
	// Comprobar si hay datos
	if (mysql_num_rows($result) > 0) {
		$response["envios"] = array();
		
		while ($row = mysql_fetch_array($result)) {
			$envio = array();
			$envio["username"] = $row["username"];
			$envio["id_envio"] = $row["id_envio"];
			$envio["nom_envio"] = $row["nom_envio"];
			$envio["estat_envio"] = $row["estat_envio"];
			$envio["data"] = $row["data"];
			
			// añadimos el array
			array_push($response["envios"], $envio);
		}
		// success
		$response["success"] = 1;
	
		// echoing JSON response
		$resultadosJson = json_encode($response);
	} else {
		// Usuario sin envios
		$response["success"] = 0;
		$response["message"] = "Usuario sin envios";
		//Respuesta JSON
		$resultadosJson = json_encode($response);
	}
}else
{
    $response["success"] = 0;
    $response["message"] = "Falta el parametro usuario";

	//Respuesta JSON
    $resultadosJson = json_encode($response);	
}
//echo $_GET['jsoncallback'] . '(' . $resultadosJson . ');';
echo $resultadosJson;
?>
