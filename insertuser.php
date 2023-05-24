<?php

$DB_SERVER="127.0.0.1"; //la dirección del servidor
$DB_USER="Xugarcia053"; //el usuario para esa base de datos
$DB_PASS="Y66Taf7TVA"; //la clave para ese usuario
$DB_DATABASE="Xugarcia053_usuarios"; //la base de datos a la que hay que conectarse

// Crear conexión
$conn = mysqli_connect($DB_SERVER, $DB_USER, $DB_PASS, $DB_DATABASE);

$user = $_GET['user'];
$pass = $_GET['pass'];
$genero = $_GET['genero'];
$edad = $_GET['edad'];
$peso = $_GET['peso'];
$altura = $_GET['altura'];
$fotoperfil = $_GET['fotoperfil']; // nuevo campo

// Verificar si la conexión fue exitosa
if ($conn->connect_error) {
    $response = array("status" => "error", "message" => "Error de conexión: " . $conn->connect_error);
    echo json_encode($response);
    exit();
}

// Sentencia SQL para insertar un nuevo usuario
$sql = "INSERT INTO usuarios (user, contraseña, genero, edad, peso, altura, fotoperfil) 
        VALUES ('$user', '$pass', '$genero', '$edad', '$peso', '$altura', '$fotoperfil')";

// Ejecutar la sentencia SQL y verificar si se realizó con éxito
if ($conn->query($sql) === TRUE) {
    $response = array("status" => "success", "message" => "Usuario insertado con exito.");
    echo json_encode($response);
} else {
    $response = array("status" => "error", "message" => "Error al insertar usuario: " . $conn->error);
    echo json_encode($response);
}

// Cerrar la conexión
$conn->close();


?>
