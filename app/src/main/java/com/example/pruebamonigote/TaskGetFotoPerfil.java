package com.example.pruebamonigote;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TaskGetFotoPerfil extends AsyncTask<Void, Void, JSONObject> {

    private final String url;
    private ImageView fotoperfil;
    private Context context;

    public TaskGetFotoPerfil(String url, ImageView fotoperfil, Context context) {
        this.url = url;
        this.fotoperfil = fotoperfil;
        this.context = context;
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

    @SuppressLint("WrongThread")
    @Override
    protected void onPostExecute(JSONObject jsonObject) {

        System.out.println("salida "+jsonObject);

        // colocar la foto de perfil en el imageview
        try {
            //obtener la foto codificada en base64
            String foto64 = jsonObject.getString("fotoperfil");

            // si el string es vacio significa que no ha actualiazdo la foto de perfil por defecto
            if (foto64.equals("default")){

                // PARA DEMOSTRAR QUE LA CODIFICACION Y DECODIFICACION FUNCIONA
                // obtengo el bitmap de la foto por defecto --> lo codifico a base 64
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.perfil);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                String fotoen64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
                System.out.println(fotoen64.length());

                // decodifico de base64 a bitmap de nuevo
                byte[] decodedBytes = Base64.decode(fotoen64, Base64.DEFAULT);
                Bitmap bitmap2 = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                fotoperfil.setImageBitmap(bitmap2);


            }else{
                System.out.println(foto64.length());
                // en caso de haberla actualizado, hay que decodificar el strb64 a bitmap
                byte[] decodedBytes = Base64.decode(foto64, Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                fotoperfil.setImageBitmap(bitmap);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        //System.out.println("salida "+jsonObject);
        //I/System.out: {"user":"mikel","pass":"345","genero":"Hombre","edad":"25","peso":"60","altura":"165"}

    }
}
