package com.example.pruebamonigote;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ActividadContactos extends AppCompatActivity {

    private static final int REQUEST_READ_CONTACTS = 1000;
    public static Context context;
    private Context c = this;
    private Activity a = this;
    private static boolean preferenciasCargadas = false;
    private static String sms;

    RecyclerView lalista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (!preferenciasCargadas){
            preferenciasCargadas=true;
            GestorIdiomas.cargarPreferencias(c,a);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_contactos);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            sms = extras.getString("sms_body");
        }

        context = this;
        lalista= findViewById(R.id.elreciclerview);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.READ_CONTACTS }, REQUEST_READ_CONTACTS);
        }else{
            Toast.makeText(this, R.string.str144, Toast.LENGTH_LONG).show();
            obtenerContactos();
        }

    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso concedido, podemos acceder a los contactos
                obtenerContactos();
            } else {
                // Permiso denegado, no podemos acceder a los contactos
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void obtenerContactos() {


        ArrayList<String> nombres = new ArrayList<String>();
        ArrayList<String> telefonos = new ArrayList<String>();
        ArrayList<Integer> imagenes = new ArrayList<Integer>();
        int idImagen = getResources().getIdentifier("contactos", "drawable", getPackageName());


        // Definimos las columnas que queremos obtener del ContentProvider
        String[] projection = new String[]{
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME
        };

        // Creamos una instancia de CursorLoader para obtener los datos del ContentProvider en segundo plano
        Cursor cursor = getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI,
                projection,
                null,
                null,
                ContactsContract.Contacts.DISPLAY_NAME + " ASC"
        );

        // Creamos una lista de listas para almacenar los contactos y sus números de teléfono
        List<List<String>> contactos = new ArrayList<>();

        // Iteramos a través de los contactos del cursor y agregamos sus nombres y números de teléfono a la lista de listas
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String nombre = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

            Cursor phoneCursor = getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                    new String[]{id},
                    null
            );
            if (phoneCursor.moveToFirst()) {
                @SuppressLint("Range") String telefono = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                contactos.add(Arrays.asList(nombre, telefono));

                nombres.add(nombre);
                telefonos.add(telefono);
                imagenes.add(idImagen);

            }
            phoneCursor.close();
        }

        // APLICAMOS EL ADAPTADOR DEL RECYCLER
        ElAdaptadorRecycler eladaptador = new ElAdaptadorRecycler(nombres.toArray(new String[nombres.size()]), imagenes.stream().mapToInt(i -> i).toArray() , telefonos.toArray(new String[telefonos.size()]),"");
        lalista.setAdapter(eladaptador);

        /** definir el aspecto del RecyclerView --> horizontal, vertical, grid... **/
        LinearLayoutManager elLayoutLineal= new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        lalista.setLayoutManager(elLayoutLineal);

        cursor.close();

    }

    protected void onSaveInstanceState(Bundle savedInstanceState) {
        // guardar el idioma seleccionado a ya que a la hora de rotar sino se pondria
        // por defecto el idioma predetermionado y no el elegido por el usuario
        super.onSaveInstanceState(savedInstanceState);
        preferenciasCargadas=false;
    }

    public static String getSmsBody() {
        return sms;
    }
}