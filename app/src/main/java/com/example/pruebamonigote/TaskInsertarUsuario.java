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

public class TaskInsertarUsuario extends AsyncTask<Void, Void, JSONObject> {

    private final String url;
    private final String user;
    private final Context context;




    public TaskInsertarUsuario(String url, String user, Context c) {
        this.url = url;
        this.user = user;
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

        if (jsonObject!=null){
            try {
                String status = jsonObject.getString("status");
                if (status.equals("success")){
                    Intent intent = new Intent(context, ActividadPrincipal.class);
                    intent.putExtra("User", user);
                    context.startActivity(intent);
                    //cerrar la pila de actividades tras registrarse
                    ActividadRegistro1.actividadRegistro1.finish();
                    ActividadRegistro2.actividadRegistro2.finish();
                    ActividadRegistro3.actividadRegistro3.finish();
                    ActividadRegistro4.actividadRegistro4.finish();
                }else{
                    Toast.makeText(context, "ERROR", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }else{
            Toast.makeText(context, "ERROR", Toast.LENGTH_LONG).show();

        }
    }
}
