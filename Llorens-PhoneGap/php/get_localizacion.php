<?php
// array JSON response
$response = array();

// incluimos db connect class
require_once __DIR__ . '/db_connect.php';

$db = new DB_CONNECT(); //Conectamos con la BD

if (isset($_GET["usuario"])) { //Parametros recibidos
	$usuario = $_GET['usuario'];

    // get a product from products table
    $result = mysql_query("SELECT * FROM usuaris WHERE username = '$usuario'");

    if (!empty($result)) {
        // check for empty result
        if (mysql_num_rows($result) > 0) {

            $result = mysql_fetch_array($result);

            $localizacion = array();
            $localizacion["username"] = $result["username"];
            $localizacion["password"] = $result["password"];
            $localizacion["latitud"] = $result["latitud"];
            $localizacion["longitud"] = $result["longitud"];
 
             // success
            $response["success"] = 1;

            // user node
            $response["localizacion"] = array();

            array_push($response["localizacion"], $localizacion);

            // echoing JSON response
            echo json_encode($response);
        } else {
            // no product found
            $response["success"] = 0;
            $response["message"] = "Usuario sin localización";

            // echo no users JSON
            echo json_encode($response);
        }
    } else {
        // no product found
        $response["success"] = 0;
        $response["message"] = "Usuario no encontrado";

        // echo no users JSON
        echo json_encode($response);
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Falta el parametro usuario";

    // echoing JSON response
    echo json_encode($response);
}
?>






