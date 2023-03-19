package com.example.pruebamonigote;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FragmentoRegistro2 extends Fragment {

    EditText editPass;
    EditText editPass2;
    EditText editUser;
    public FragmentoRegistro2() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActividadRegistrarse.numeroFragmento=2;


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragmento_registro2, container, false);
    }

    @Override
    public void onViewCreated(@Nullable View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        //RESTRICCIONES para el editText de la contraseña
        editPass = getView().findViewById(R.id.editPass);
        editPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        editPass2 = getView().findViewById(R.id.editPass2);
        editPass2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        Button b = getView().findViewById(R.id.btnAnndirRutina);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editUser = getView().findViewById(R.id.editUser);
                String user=editUser.getText().toString();
                String pass1=editPass.getText().toString();
                String pass2=editPass2.getText().toString();

                // comprobar que contraseñas no vacias e iguales
                if (pass1.equals(pass2) && !pass1.isEmpty() && !pass2.isEmpty() && !user.isEmpty() ){

                    EditText edituser = getView().findViewById(R.id.editUser);
                    String User = edituser.getText().toString();

                    // se debe comprobar que no exista un usuario con dicho nombre en la bbdd
                    // para ello hago un select cualquiera con clausula WHERE metiendo el usuario introducido en el editText
                    miBD GestorDB = new miBD (getActivity(), "fitproBD", null, 1);
                    SQLiteDatabase bd = GestorDB.getWritableDatabase();
                    String[] campos = new String[] {"Altura"};
                    String[] argumentos = new String[] {User};
                    Cursor c = bd.query("Usuarios",campos,"Usuario=?",argumentos,null,null,null);

                    if (c.moveToFirst()){

                        Toast.makeText(getActivity(), R.string.str115, Toast.LENGTH_LONG).show();

                    }else{
                        Bundle bundle = new Bundle();
                        bundle.putString("User",User);
                        bundle.putString("Contraseña",pass1);
                        Navigation.findNavController(view).navigate(R.id.action_fragmentoRegistro2_to_fragmentoRegistro3,bundle);

                    }
                    c.close();
                    bd.close();

                }else{

                    Toast.makeText(getActivity(), R.string.str14, Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}