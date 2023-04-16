package com.example.pruebamonigote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ActividadRegistro2 extends AppCompatActivity {

    private EditText editPass;
    private EditText editPass2;
    private EditText editUser;
    private Context context;
    public static ActividadRegistro2 actividadRegistro2;
    private static boolean preferenciasCargadas = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        actividadRegistro2=this;
        if (!preferenciasCargadas){
            preferenciasCargadas=true;
            GestorIdiomas.cargarPreferencias(context,actividadRegistro2);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_registro2);

        //RESTRICCIONES para el editText de la contraseña
        editPass = findViewById(R.id.editPass);
        editPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        editPass2 = findViewById(R.id.editPass2);
        editPass2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        editUser = findViewById(R.id.editUser);


        if (savedInstanceState != null) {
            String username = savedInstanceState.getString("username");
            String pass = savedInstanceState.getString("pass");
            String pass2 = savedInstanceState.getString("pass2");
            editUser.setText(username);
            editPass.setText(pass);
            editPass2.setText(pass2);
        }
    }

    public void continuar(View v){
        editUser = findViewById(R.id.editUser);
        String user=editUser.getText().toString();
        String pass1=editPass.getText().toString();
        String pass2=editPass2.getText().toString();

        // comprobar que contraseñas no vacias e iguales
        if (pass1.equals(pass2) && !pass1.isEmpty() && !pass2.isEmpty() && !user.isEmpty() ){

            EditText edituser = findViewById(R.id.editUser);
            String User = edituser.getText().toString();

            String url = "http://ec2-54-93-62-124.eu-central-1.compute.amazonaws.com/ugarcia053/WEB/selectuser.php?user=" + user;
            TaskComprobarUserExiste task = new TaskComprobarUserExiste(url, pass1, User, context);
            task.execute();

        }else{

            Toast.makeText(this, R.string.str14, Toast.LENGTH_LONG).show();
        }

    }

    protected void onSaveInstanceState(Bundle savedInstanceState) {
        // guardar el idioma seleccionado a ya que a la hora de rotar sino se pondria
        // por defecto el idioma predetermionado y no el elegido por el usuario
        super.onSaveInstanceState(savedInstanceState);
        preferenciasCargadas=false;
        savedInstanceState.putString("username", editUser.getText().toString());
        savedInstanceState.putString("pass", editPass.getText().toString());
        savedInstanceState.putString("pass2", editPass2.getText().toString());
    }
}