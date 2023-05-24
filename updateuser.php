<?php

$DB_SERVER="127.0.0.1"; //la dirección del servidor
$DB_USER="Xugarcia053"; //el usuario para esa base de datos
$DB_PASS="Y66Taf7TVA"; //la clave para ese usuario
$DB_DATABASE="Xugarcia053_usuarios"; //la base de datos a la que hay que conectarse

// Crear conexión
$conn = mysqli_connect($DB_SERVER, $DB_USER, $DB_PASS, $DB_DATABASE);

$user = $_POST['user'];
$pass = $_POST['pass'];
$genero = $_POST['genero'];
$edad = $_POST['edad'];
$peso = $_POST['peso'];
$altura = $_POST['altura'];
$fotoperfil = $_POST['fotoperfil'];
$cabecera= array(
'Authorization: BDalcnMncjRe1-0YCddTe-fKh_0ArGPBEeabdNwxo_6k71tPVFH52HCUMwf9jxyM8BRA9YTjqlxB0maOtk0iF3k',
'Content-Type: application/json'
);

// Verificar si la conexión fue exitosa
if ($conn->connect_error) {
    $response = array("status" => "error", "message" => "Error de conexión: " . $conn->connect_error);
    echo json_encode($response);
    exit();
}

// Sentencia SQL para actualizar los valores del usuario
$sql = "UPDATE usuarios SET contraseña='$pass', genero='$genero', edad='$edad', peso='$peso', altura='$altura', fotoperfil='$fotoperfil' WHERE user='$user'";

// Ejecutar la sentencia SQL y verificar si se realizó con éxito
if ($conn->query($sql) === TRUE) {
    $response = array("status" => "success", "message" => "Valores actualizados con exito.");
    echo json_encode($response);
} else {
    $response = array("status" => "error", "message" => "Error al actualizar valores: " . $conn->error);
    echo json_encode($response);
}

// Cerrar la conexión
$conn->close();

?>
