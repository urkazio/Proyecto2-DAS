package com.example.pruebamonigote;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.InputFilter;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class FragmentoRegistro3 extends Fragment {
    EditText editEdad;
    public FragmentoRegistro3() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragmento_registro3, container, false);
    }

    @Override
    public void onViewCreated(@Nullable View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        //combobox
        Spinner spinner = getView().findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.genero, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //RESTRICCIONES para el editText de la edad
        editEdad = getView().findViewById(R.id.editEdad);
        editEdad.setInputType(InputType.TYPE_CLASS_NUMBER);
        editEdad.setFilters(new InputFilter[] { new InputFilter.LengthFilter(3) }); //limitar a 3 caracteres

        Button b = getView().findViewById(R.id.btnAnndirRutina);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Spinner spinner = getView().findViewById(R.id.spinner);
                String Genero = spinner.getSelectedItem().toString();

                editEdad = getView().findViewById(R.id.editEdad);

                if (editEdad.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "Hay campos vacios", Toast.LENGTH_LONG).show();

                }else{
                    int Edad = Integer.parseInt(editEdad.getText().toString());


                    String User = getArguments().getString("User");
                    String Contraseña = getArguments().getString("Contraseña");
                    System.out.println("User: "+User+"// pass: "+Contraseña+"// genero: "+Genero+"// edad: "+Edad);

                    Bundle bundle = new Bundle();
                    bundle.putString("User",User);
                    bundle.putString("Contraseña",Contraseña);
                    bundle.putString("Genero",Genero);
                    bundle.putInt("Edad",Edad);

                    Navigation.findNavController(view).navigate(R.id.action_fragmentoRegistro3_to_fragmentoRegistro4,bundle);

                }

            }
        });
    }
}