package com.example.pruebamonigote;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ActividadVideo extends AppCompatActivity {

    private WebView mWebView;
    private String nombre = "";
    private String videoUrl = ""; // Aquí va la url del video de YouTube

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_video);

        mWebView = findViewById(R.id.webview_video);

        // obtener el ejercciio en el que se ha pinchado
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            nombre = extras.getString("nombre_ejercicio");
        }

        // Este método se llama cada vez que se carga una URL en el WebView
        // permite que el WebView cargue una URL sin que se produzca una redirección a otra página
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });


        // obtener la url del video dependiendo del ejercicio en el que hayamos pinchado
        if (nombre.equals("Adductor") || nombre.equals("Aductor")) {
            videoUrl = "https://www.youtube.com/watch?v=GmRSV_n2E_0";
        } else if (nombre.equals("Banca Inclinado") || nombre.equals("Inclined Bench")) {
            videoUrl = "https://www.youtube.com/watch?v=DbFgADa2PL8";
        } else if (nombre.equals("Calentamiento") || nombre.equals("Warm up") || nombre.equals("Vuelta a la Calma") || nombre.equals("Back to Calm")) {
            videoUrl = "https://www.youtube.com/watch?v=OENxC3bgAEs";
        } else if (nombre.equals("Cardio Ligero") || nombre.equals("Light cardio")) {
            videoUrl = "https://www.youtube.com/watch?v=fV1MRb87bOU";
        } else if (nombre.equals("Cruces") || nombre.equals("Flys")) {
            videoUrl = "https://www.youtube.com/watch?v=WEM9FCIPlxQ";
        } else if (nombre.equals("Crunches")) {
            videoUrl = "https://www.youtube.com/watch?v=NGRKFMKhF8s";
        } else if (nombre.equals("Curl con Barra") || nombre.equals("Barbell curl")) {
            videoUrl = "https://www.youtube.com/watch?v=LY1V6UbRHFM";
        } else if (nombre.equals("Curl Femoral") || nombre.equals("Hamstring Curl")) {
            videoUrl = "https://www.youtube.com/watch?v=1Tq3QdYUuHs";
        } else if (nombre.equals("Curl Martillo") || nombre.equals("Hammer curl")) {
            videoUrl = "https://www.youtube.com/watch?v=zC3nLlEvin4";
        } else if (nombre.equals("Elevacion Gemelo") || nombre.equals("Calf Raises")) {
            videoUrl = "https://www.youtube.com/watch?v=3UWi44yN-wM";
        } else if (nombre.equals("Extension Pierna") || nombre.equals("Leg Extension")) {
            videoUrl = "https://www.youtube.com/watch?v=YyvSfVjQeL0";
        } else if (nombre.equals("Extension Triceps") || nombre.equals("Triceps Extension")) {
            videoUrl = "https://www.youtube.com/watch?v=2-LAMcpzODU";
        } else if (nombre.equals("Hip Trust")) {
            videoUrl = "https://www.youtube.com/watch?v=SEdqd1n0cvg";
        } else if (nombre.equals("Jalon al Pecho") || nombre.equals("Lat Pulldown")) {
            videoUrl = "https://www.youtube.com/watch?v=X5n55mMqSUs";
        } else if (nombre.equals("Laterales") || nombre.equals("Lat Raises")) {
            videoUrl = "https://www.youtube.com/watch?v=3VcKaXpzqRo";
        } else if (nombre.equals("Plancha") || nombre.equals("Planks")) {
            videoUrl = "https://www.youtube.com/watch?v=pSHjTRCQxIw";
        } else if (nombre.equals("Prensa") || nombre.equals("Leg Press")) {
            videoUrl = "https://www.youtube.com/watch?v=IZxyjW7MPJQ";
        } else if (nombre.equals("Press Banca") || nombre.equals("Bench Press")) {
            videoUrl = "https://www.youtube.com/watch?v=rT7DgCr-3pg";
        } else if (nombre.equals("Press Frances") || nombre.equals("French Press")) {
            videoUrl = "https://www.youtube.com/watch?v=d_KZxkY_0cM";
        } else if (nombre.equals("Press Militar") || nombre.equals("Military Press")) {
            videoUrl = "https://www.youtube.com/watch?v=qEwKCR5JCog";
        } else if (nombre.equals("Sentadilla") || nombre.equals("Squat")) {
            videoUrl = "https://www.youtube.com/watch?v=nFAscG0XUNY";
        } else if (nombre.equals("Dorsal en Polea") || nombre.equals("Lat Pushdown")) {
            videoUrl = "https://www.youtube.com/watch?v=AjCCGN2tU3Q";
        } else if (nombre.equals("Remo con Barra") || nombre.equals("Barbell Row")) {
            videoUrl = "https://www.youtube.com/watch?v=9efgcAjQe7E";
        } else if (nombre.equals("Remo Gironda") || nombre.equals("Seated Row")) {
            videoUrl = "https://www.youtube.com/watch?v=GZbfZ033f74";
        }

        // setear y cargar el video
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(videoUrl);


    }

}
