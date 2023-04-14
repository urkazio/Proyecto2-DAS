package com.example.pruebamonigote;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ValidarUsuarioTask extends AsyncTask<Void, Void, JSONObject> {

    private final String url;
    private final String pass;
    private final Context context;
    private String passBD;
    private String userBD;



    public ValidarUsuarioTask(String url, String pass, Context c) {
        this.url = url;
        this.pass = pass;
        this.context = c;
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

        if (jsonObject==null){
            // si la respuesta es vacia significa que no exoiste dicho usuario
            Toast.makeText(context, R.string.str119, Toast.LENGTH_LONG).show();
        }else{
            System.out.println("jsonobject "+jsonObject);
            try {
                userBD = jsonObject.getString("user");
                passBD = jsonObject.getString("pass");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            String passEncriptada = EncriptadorContraseñas.encrypt(pass);
            if (passBD.equals(passEncriptada)) {

                Intent intent = new Intent(context, ActividadPrincipal.class);
                intent.putExtra("User", userBD);
                context.startActivity(intent);
                ActividadLogin.actividadLogin.finish();

            } else {
                Toast.makeText(context, R.string.str108, Toast.LENGTH_LONG).show();
            }

        }
    }
}
