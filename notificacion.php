<?php


$cabecera= array(
	'Authorization: key=AAAA4MCK3jM:APA91bESkoGEJvI3ij49uNKpUqBDx3EmDCXcRJIq9VQb3tP5JNW01-gbO5LXwBcl-n9SaKTI1lFL2LeMC5vdDu_sOIifaosdHhuznKa06gycuWCRhDRRxUzF_-u0vvUsXdqcInKWVsny',
	'Content-Type: application/json'
);

$msg= array(
	'to'=>'/topics/all',
	'notification' => array (
	'body' => "Es hora de entrenar!\nPrueba alguna rutina nueva en alguno de nuestros gimnasios!",
	'title' => 'Recordatorio de Fit Pro',
	'icon' => 'logo',
	'click_action'=>"AVISO"
	)
);

$msgJSON = json_encode($msg);

$ch = curl_init(); #inicializar el handler de curl
#indicar el destino de la petición, el servicio FCM de google
curl_setopt( $ch, CURLOPT_URL, 'https://fcm.googleapis.com/fcm/send');
#indicar que la conexión es de tipo POST
curl_setopt( $ch, CURLOPT_POST, true );
#agregar las cabeceras
curl_setopt( $ch, CURLOPT_HTTPHEADER, $cabecera);
#Indicar que se desea recibir la respuesta a la conexión en forma de string
curl_setopt( $ch, CURLOPT_RETURNTRANSFER, true );
#agregar los datos de la petición en formato JSON
curl_setopt( $ch, CURLOPT_POSTFIELDS, $msgJSON );
#ejecutar la llamada
$resultado= curl_exec( $ch );
#cerrar el handler de curl
curl_close( $ch );

if (curl_errno($ch)) {
	print curl_error($ch);
}

echo $resultado;

?>
