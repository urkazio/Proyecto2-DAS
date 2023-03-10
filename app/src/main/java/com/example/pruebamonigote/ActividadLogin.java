package com.example.pruebamonigote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ActividadLogin extends AppCompatActivity {

    EditText editPass;
    public static ActividadLogin actividadLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_login);
        actividadLogin=this;

        //RESTRICCIONES para el editText de la contraseña
        editPass = findViewById(R.id.editPassLogin);
        editPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
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

        if (c.moveToFirst()) {
            String contraseña = c.getString(0);


            if (contraseña.equals(pass)) {

                Intent intent = new Intent(this, ActividadPrincipal.class);
                intent.putExtra("User", user);
                startActivity(intent);
                ActividadLogin.actividadLogin.finish();

            } else {
                Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_LONG).show();
            }

        }
        c.close();
        bd.close();
    }
}