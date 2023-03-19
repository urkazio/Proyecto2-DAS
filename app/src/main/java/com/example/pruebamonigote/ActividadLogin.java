package com.example.pruebamonigote;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class ActividadLogin extends AppCompatActivity {

    EditText editPass;
    public static ActividadLogin actividadLogin;
    private Context c = this;
    private Activity a = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_login);
        actividadLogin=this;

        //RESTRICCIONES para el editText de la contraseña
        editPass = findViewById(R.id.editPassLogin);
        editPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        // guardar el idioma seleccionado a ya que a la hora de rotar sino se pondria
        // por defecto el idioma predetermionado y no el elegido por el usuario
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("idioma", GestorIdiomas.storeLang);

    }
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        // recuperar el idioma guardado antes de destruir la actividad y aplicarlo
        super.onRestoreInstanceState(savedInstanceState);
        String idioma = savedInstanceState.getString("idioma");
        GestorIdiomas.cambiarIdioma(idioma,c,a);
    }

    public void logearse(View v) {
        //Identificarse contra la base de datos

        EditText editUser = findViewById(R.id.editUserLogin);
        String user = editUser.getText().toString();

        editPass = findViewById(R.id.editPassLogin);
        String pass = editPass.getText().toString();

        miBD GestorDB = new miBD(this, "fitproBD", null, 1);
        SQLiteDatabase bd = GestorDB.getWritableDatabase();
        String[] campos = new String[]{"Contraseña"};
        String[] argumentos = new String[]{user};
        Cursor c = bd.query("Usuarios", campos, "Usuario=?", argumentos, null, null, null);

        // si el resultado es vacio significa que no hay user con ese nombre
        if (c.getCount()==0){
            Toast.makeText(this, R.string.str119, Toast.LENGTH_LONG).show();

        }else if (c.moveToFirst()) {
            String contraseña = c.getString(0);

            if (contraseña.equals(pass)) {

                Intent intent = new Intent(this, ActividadPrincipal.class);
                intent.putExtra("User", user);
                startActivity(intent);
                ActividadLogin.actividadLogin.finish();

            } else {
                Toast.makeText(this, R.string.str108, Toast.LENGTH_LONG).show();
            }

        }

        c.close();
        bd.close();
    }
}