package com.example.pruebamonigote;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class TaskUpdateUsuario extends AsyncTask<Void, Void, JSONObject> {

    private final String url;
    private final String payload;
    private final Context context;

    public TaskUpdateUsuario(String url, String payload, Context context) {
        this.url = url;
        this.payload = payload;
        this.context = context;
    }

    @Override
    protected JSONObject doInBackground(Void... voids) {
        try {
            // Realizar la petición HTTP a la URL
            URL urlObj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
            connection.setRequestMethod("POST");

            // Enviar el payload si existe
            if (payload != null) {
                connection.setDoOutput(true);
                connection.getOutputStream().write(payload.getBytes());
            }

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

        if (jsonObject != null) {
            try {
                String status = jsonObject.getString("status");
                if (status.equals("success")) {
                    Toast.makeText(context, R.string.str140, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "ERROR", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        } else {
            Toast.makeText(context, "ERROR", Toast.LENGTH_LONG).show();
        }
    }
}
