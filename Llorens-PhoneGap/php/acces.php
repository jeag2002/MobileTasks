<?php

// array JSON response
$response = array();

if (isset($_GET['usuario']) && isset($_GET['password'])) {

	$usuario = $_GET['usuario'];
	$passw = $_GET['password'];
	
	// include db connect class
	require_once __DIR__ . '/db_connect.php';
	
	// connecting to db
	$db = new DB_CONNECT();
	
	$result=mysql_query("SELECT COUNT(*) FROM usuaris WHERE username='$usuario' AND password='$passw'") or die(mysql_error()); 
	$count = mysql_fetch_row($result);
	
	if ($count[0]==0){
		// Login Incorrecto
        $response["success"] = 0;
        $response["message"] = "Login ERROR.";
	}else{
		// Login OK
        $response["success"] = 1;
        $response["message"] = "Login OK.";
	}
}else
{
	// Login Incorrecto
	$response["success"] = 0;
	$response["message"] = "Parámetros incorrectos.";
}

/*convierte los resultados a formato json*/
$resultadosJson = json_encode($response);

/*muestra el resultado en un formato que no da problemas de seguridad en browsers */
//echo $_GET['jsoncallback'] . '(' . $resultadosJson . ');';

echo $resultadosJson;
?>







