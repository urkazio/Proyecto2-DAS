package com.example.pruebamonigote;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import android.Manifest;
import android.widget.TextView;
import android.widget.Toast;

public class ActividadMapaGimnasios extends FragmentActivity implements OnMapReadyCallback {
    GoogleMap elmapa;
    private FusedLocationProviderClient fusedLocationProviderClient;
    float distanciaKms = 0;
    private static boolean preferenciasCargadas = false;
    float distanciatotalKms = 0;
    Marker markerInicial;
    float distMin = 2147483647;
    Marker markerMasCercano;
    private boolean seteado = false;
    private LatLng miLatLang;
    private Context c = this;
    private Activity a = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (!preferenciasCargadas){
            preferenciasCargadas=true;
            GestorIdiomas.cargarPreferencias(c,a);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_mapa_gimnasios);

        // Para poder trabajar con el mapa deberemos utilizar el identificador que le hayamos asignado
        // al Fragment (en el layout) donde se encuentra el mapa y luego llamar al método getMapAsync(…)
        SupportMapFragment elfragmento = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentoMapa);
        elfragmento.getMapAsync(this);

        TextView tv1 = findViewById(R.id.km1);
        tv1.setText(R.string.str131);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Verifica si se tienen permisos de ubicación
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            obtenerUbicacionActual();
        } else {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
        }
    }

    private void obtenerUbicacionActual() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            miLatLang = new LatLng(location.getLatitude(), location.getLongitude());
                            MarkerOptions markerOptions = new MarkerOptions().position(miLatLang).title("tú");
                            elmapa.addMarker(markerOptions);
                            inicializarMapa();

                        } else {
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        elmapa = googleMap;
        elmapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        CameraUpdate actualizarCoord = CameraUpdateFactory.newLatLngZoom(new LatLng(43.26, -2.95),9);
        elmapa.moveCamera(actualizarCoord);
        obtenerUbicacionActual();

        /**##################################################################################
         // LISTENERS PARA --> DETECTOR DE CAMBIOS DE POSICION, EVENTOS AL CLICKAR MAPA...
         #################################################################################**/

        elmapa.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                return false;
            }
        });
        elmapa.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
            }
        });
    }

    public void inicializarMapa(){
        /**##################################################################################
         // Para el posicionamiento de la cámara se usa un objeto de tipo CameraUpdate
         // para la actualización de la latitud y longitud o zoom, se usa CameraUpdateFactory
         #################################################################################**/
        elmapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        CameraUpdate actualizarCoord = CameraUpdateFactory.newLatLngZoom(new LatLng(43.26, -2.95),11);
        elmapa.moveCamera(actualizarCoord);

        //añadir un marcador rojo donde se encuentra el usuario
        MarkerOptions markerOptions = new MarkerOptions().position(miLatLang).title("tú");
        markerInicial = elmapa.addMarker(markerOptions);

        if (!seteado){
            añadirMarcadorGimnasios(43.29013519321173,-2.988746613264084, "Fit Pro Barakaldo");
            añadirMarcadorGimnasios(43.2503566206128,-2.9496071487665176, "Fit Pro Rekalde");
            añadirMarcadorGimnasios(43.27718262116089,-2.9541129246354103, "Fit Pro Deustu");
            añadirMarcadorGimnasios(43.25991840890319,-2.937147617340088, "Fit Pro Alondiga");
            añadirMarcadorGimnasios(43.25889070710799,-2.9234234243631363, "Fit Pro Casco");
            añadirMarcadorGimnasios(43.26078641151814,-2.9411404207348824, "Fit Pro Indautxu");
            seteado=true;

        }

        //dibujar la linea entre el telefono y el gimnasio mas cercano
        PolylineOptions polylineOptions = new PolylineOptions()
                .add(markerInicial.getPosition(), markerMasCercano.getPosition())
                .color(Color.RED)
                .width(5f);
        elmapa.addPolyline(polylineOptions);


        //colorar de azul el marcador mas cercano respecto a la posicion acual
        float azul = 240; // Valor de `hue` para el color azul
        BitmapDescriptor icon = BitmapDescriptorFactory.defaultMarker(azul);
        markerMasCercano.setIcon(icon);
        String nombre = markerMasCercano.getTitle();

        TextView tv1 = findViewById(R.id.km1);
        tv1.setText(getString(R.string.str129)+" "+nombre);
        TextView tv2 = findViewById(R.id.km2);
        tv2.setText(getString(R.string.str130)+" "+distanciaKms+" kms");
    }


    public void añadirMarcadorGimnasios(double lat, double lng, String nombre){

        LatLng latLng = new LatLng(lat, lng);
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.defaultMarker(30))
                .alpha(0.8f)
                .title(nombre);
        Marker markerActual = elmapa.addMarker(markerOptions);
        actualizarMasCercano(markerActual);
    }

    public void actualizarMasCercano(Marker markerActual){

        /**
         * pre: recibe una posicion y un nombre para el marcador
         * post: coloca el marcador en el mapa, dibuja una linea hasta el anterior marcador y calcula la distancia total del recorrido (del inicio al fin)
         */

        // calcular distancia total de la ruta
        // primero obtener las coordenadas de los markers
        Location location1 = new Location("");
        location1.setLatitude(markerInicial.getPosition().latitude);
        location1.setLongitude(markerInicial.getPosition().longitude);

        Location location2 = new Location("");
        location2.setLatitude(markerActual.getPosition().latitude);
        location2.setLongitude(markerActual.getPosition().longitude);

        // calcular la diastancia entre markers en kms
        float distanciaMetros = location1.distanceTo(location2);
        if (distanciaMetros<distMin){
            distMin=distanciaMetros;
            markerMasCercano = markerActual;
            distanciaKms = distanciaMetros / 1000f;
            distanciatotalKms+=distanciaKms;
        }
    }


    public void zoomIn(View v){

        CameraPosition currentCameraPosition = elmapa.getCameraPosition();
        CameraPosition newCameraPosition = CameraPosition.builder(currentCameraPosition)
                .tilt(70) // angulo de la camara segun te acercas (0-90)
                //.bearing(54) //orientacion de la camara siendo 0 el norte
                .zoom(elmapa.getCameraPosition().zoom+1)
                .build();
        elmapa.animateCamera(CameraUpdateFactory.newCameraPosition(newCameraPosition));
    }

    public void zoomOut(View v){

        CameraPosition currentCameraPosition = elmapa.getCameraPosition();
        CameraPosition newCameraPosition = CameraPosition.builder(currentCameraPosition)
                .tilt(70)
                //.bearing(54)
                .zoom(elmapa.getCameraPosition().zoom-1)
                .build();
        elmapa.animateCamera(CameraUpdateFactory.newCameraPosition(newCameraPosition));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Si se conceden permisos, obtén la ubicación actual
            obtenerUbicacionActual();
        } else {
            // Si no se conceden permisos, muestra un mensaje de error
            Toast.makeText(this, R.string.str134, Toast.LENGTH_SHORT).show();
        }
    }

    protected void onSaveInstanceState(Bundle savedInstanceState) {
        // guardar el idioma seleccionado a ya que a la hora de rotar sino se pondria
        // por defecto el idioma predetermionado y no el elegido por el usuario
        super.onSaveInstanceState(savedInstanceState);
        preferenciasCargadas = false;
    }
}