package com.example.pruebamonigote;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

public class ActividadLogin extends AppCompatActivity {

    EditText editPass;
    public static ActividadLogin actividadLogin;
    private static boolean preferenciasCargadas = false;

    private Context c = this;
    private Activity a = this;
    EditText editUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (!preferenciasCargadas){
            preferenciasCargadas=true;
            GestorIdiomas.cargarPreferencias(c,a);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_login);
        actividadLogin=this;

        editUser = findViewById(R.id.editUserLogin);
        editPass = findViewById(R.id.editPassLogin);

        //RESTRICCIONES para el editText de la contraseña
        editPass = findViewById(R.id.editPassLogin);
        editPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        if (savedInstanceState != null) {
            String user = savedInstanceState.getString("user");
            String pass = savedInstanceState.getString("pass");
            editUser.setText(user);
            editPass.setText(pass);
        }
    }

    public void logearse(View v) {
        //Identificarse contra la base de datos

        String user = editUser.getText().toString();
        String pass = editPass.getText().toString();

        // llamada http al recurso web php que valida el inicio de sesion pasando por parametro el nombre del usuario
        // toda esta gestion se realiza en la clase ValidarUsuarioTask
        String url = "http://ec2-54-93-62-124.eu-central-1.compute.amazonaws.com/ugarcia053/WEB/selectuser.php?user=" + user;
        TaskLoginUsuario task = new TaskLoginUsuario(url, pass, c);
        task.execute();

    }
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        // guardar el idioma seleccionado a ya que a la hora de rotar sino se pondria
        // por defecto el idioma predetermionado y no el elegido por el usuario
        super.onSaveInstanceState(savedInstanceState);
        preferenciasCargadas = false;
        savedInstanceState.putString("user", editUser.getText().toString());
        savedInstanceState.putString("pass", editPass.getText().toString());
    }
}