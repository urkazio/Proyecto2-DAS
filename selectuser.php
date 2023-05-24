<?php

$DB_SERVER="127.0.0.1"; //la dirección del servidor
$DB_USER="Xugarcia053"; //el usuario para esa base de datos
$DB_PASS="Y66Taf7TVA"; //la clave para ese usuario
$DB_DATABASE="Xugarcia053_usuarios"; //la base de datos a la que hay que conectarse


// Crear conexión
$conn = mysqli_connect($DB_SERVER, $DB_USER, $DB_PASS, $DB_DATABASE);

$user = $_GET['user'];

// Verificar si la conexión fue exitosa
if ($conn->connect_error) {
    die("Error de conexión: " . $conn->connect_error);
}

// Sentencia SQL para seleccionar el user
$sql = "SELECT * FROM usuarios WHERE user = '$user'";

// Ejecutar la sentencia SQL y obtener los resultados
$resultado = $conn->query($sql);

// Verificar si se encontró la user
if ($resultado->num_rows > 0) {
    // Crear un array para almacenar los datos de el user
    $user = array();
    while($row = $resultado->fetch_assoc()) {
        // Agregar los datos de el user al array
        $user["user"] = $row["user"];
        $user["pass"] = $row["contraseña"];
        $user["genero"] = $row["genero"];
		$user["edad"] = $row["edad"];
		$user["peso"] = $row["peso"];
        $user["altura"] = $row["altura"];
		$user["fotoperfil"] = $row["fotoperfil"];

    }
    // Devolver los datos de el user en formato JSON
    echo json_encode($user);
} else {
    echo "No se encontró el user.";
}

// Cerrar la conexión
$conn->close();
?>