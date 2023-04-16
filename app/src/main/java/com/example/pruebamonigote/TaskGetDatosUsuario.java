package com.example.pruebamonigote;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;


public class TaskGetDatosUsuario extends AsyncTask<Void, Void, JSONObject> {
    private final String url;
    private TextView tvuser;
    private EditText editpass;
    private Spinner spinnerGenero;
    private EditText editedad;
    private EditText editpeso;
    private EditText editaltura;

    public TaskGetDatosUsuario(String url, TextView tvuser, EditText editpass, Spinner spinnerGenero, EditText editedad, EditText editpeso, EditText editaltura) {
        this.url = url;
        this.tvuser = tvuser;
        this.editpass = editpass;
        this.spinnerGenero = spinnerGenero;
        this.editedad = editedad;
        this.editpeso = editpeso;
        this.editaltura = editaltura;
    }

    @Override
    protected JSONObject doInBackground(Void... voids) {
        try {
            // Realizar la petición HTTP a la URL
            URL urlObj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            // Convertir la respuesta en formato JSON
            JSONObject jsonObject = new JSONObject(stringBuilder.toString());
            return jsonObject;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {

        try {
            // completar los campos del editext en funcion de los datos rescatados de la bbdd
            tvuser.setText("@" + jsonObject.getString("user"));   // si la pass es vacia, rescatarla y pushearla??
            editpass.setHint(R.string.str139);
            if (jsonObject.getString("genero").equals("Hombre")){
                spinnerGenero.setSelection(0);
            }else{
                spinnerGenero.setSelection(1);
            }
            editedad.setText(jsonObject.getString("edad"));
            editpeso.setText(jsonObject.getString("peso"));
            editaltura.setText(jsonObject.getString("altura"));
            ActividadPerfil.passActual=jsonObject.getString("pass");


        } catch (JSONException e) {
            e.printStackTrace();
        }


        System.out.println("salida "+jsonObject);
        //I/System.out: {"user":"mikel","pass":"345","genero":"Hombre","edad":"25","peso":"60","altura":"165"}

    }
}
