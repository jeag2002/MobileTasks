<?php

// array JSON response
$response = array();

// verificamos los parametros
if (isset($_POST['usuario']) && isset($_POST['latitud']) && isset($_POST['longitud'])) {
    
    $usuario = $_POST['usuario'];
    $latitud = $_POST['latitud'];
    $longitud = $_POST['longitud'];
 
    require_once __DIR__ . '/db_connect.php'; // incluimos db connect class

    $db = new DB_CONNECT(); //Conectamos con la BD

    // instruccio SQL update
    $result = mysql_query("UPDATE usuaris SET latitud = '$latitud', longitud = '$longitud' WHERE username = '$usuario'");

    // verificamos la ejecución
    if ($result) {
        // update OK
        $response["success"] = 1;
        $response["message"] = "Localización actualizada OK.";
        
        // echoing JSON response
        echo json_encode($response);
    } else {
		// parametros incorrectos
		$response["success"] = 0;
		$response["message"] = "Update ERROR";
		// echoing JSON response
		echo json_encode($response);   
	}
} else {
    // parametros incorrectos
    $response["success"] = 0;
    $response["message"] = "Parametros incorrectos";

    // echoing JSON response
    echo json_encode($response);
}
?>
