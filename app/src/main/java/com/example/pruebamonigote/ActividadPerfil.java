package com.example.pruebamonigote;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class ActividadPerfil extends AppCompatActivity {
    private static boolean preferenciasCargadas = false;
    private Context c = this;
    private Activity a = this;
    String user;
    private TextView tvuser;
    private EditText editPass;
    private Spinner spinnerGenero;
    private EditText editEdad;
    private EditText editPeso;
    private EditText editAltura;
    private ImageView fotoperfil;
    private Bitmap bitmapRedimensionado;
    private Bitmap bitmapOriginal;

    private static final int PICK_IMAGE_REQUEST = 1;
    private int anchoDestino;
    private int altoDestino;
    public static String passActual;
    private static final int REQUEST_CAMERA_PERMISSION = 3;

    // Sacar una fotografía usando una aplicación de fotografía y posteriormente
    // escalarlas al tamaño que se van a mostrar, pero manteniendo su aspecto
    private ActivityResultLauncher<Intent> takePictureLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData()!= null ) {
                    Bundle bundle = result.getData().getExtras();
                    Bitmap bitmapFoto = (Bitmap) bundle.get( "data" );

                    int anchoImagen = bitmapFoto.getWidth();
                    int altoImagen = bitmapFoto.getHeight();
                    float ratioImagen = ( float ) anchoImagen / ( float ) altoImagen;
                    float ratioDestino = ( float ) anchoDestino / ( float ) altoDestino;
                    int anchoFinal = anchoDestino;
                    int altoFinal = altoDestino;
                    if (ratioDestino > ratioImagen) {
                        anchoFinal = ( int ) (( float )altoDestino * ratioImagen);
                    } else {
                        altoFinal = ( int ) (( float )anchoDestino / ratioImagen);
                    }
                    bitmapRedimensionado = Bitmap. createScaledBitmap (bitmapFoto,anchoFinal,altoFinal, true );
                    fotoperfil.setImageBitmap(bitmapRedimensionado);

                } else {
                    Log. d ( "FOTOS" , "no se ha sacado la foto" );
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // gestion de las preferencias de idiomas
        if (!preferenciasCargadas){
            preferenciasCargadas=true;
            GestorIdiomas.cargarPreferencias(c,a);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_perfil);

        // pedir permisos para acceder a camara
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        }

        // recuperar el username que ha sido pasado a la actividad
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user = extras.getString("user");
        }

        // inicializar los elementos del layout
        tvuser = findViewById(R.id.tvuser);
        editPass = findViewById(R.id.editPass);
        spinnerGenero = findViewById(R.id.editGenero);
        editEdad = findViewById(R.id.editEdad);
        editPeso = findViewById(R.id.editPeso);
        editAltura = findViewById(R.id.editAltura);
        fotoperfil = findViewById(R.id.fotoperfil);

        // restricciones para los editexts
        editPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        editEdad.setInputType(InputType.TYPE_CLASS_NUMBER);
        editEdad.setFilters(new InputFilter[] { new InputFilter.LengthFilter(3) }); //limitar a 3 caracteres
        editPeso.setInputType(InputType.TYPE_CLASS_NUMBER );
        editPeso.setFilters(new InputFilter[] { new InputFilter.LengthFilter(3) }); //limitar a 3 caracteres
        editAltura.setInputType(InputType.TYPE_CLASS_NUMBER);
        editAltura.setFilters(new InputFilter[] { new InputFilter.LengthFilter(3) }); //limitar a 3 caracteres

        // seteo del spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.genero, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGenero.setAdapter(adapter);

        // llamada a trabajo asincrono en segundo plano para recuperar los datos (texto) del usuario y colocarlos en los editTexts
        String url = "http://ec2-54-93-62-124.eu-central-1.compute.amazonaws.com/ugarcia053/WEB/selectuser.php?user="+user;
        TaskGetDatosUsuario task = new TaskGetDatosUsuario(url, tvuser, editPass, spinnerGenero, editEdad, editPeso, editAltura);
        task.execute();

        // llamada a trabajo asincrono en segundo plano para recuperar la foto de perfil del usuario y colocarla en el imageview
        String url2 = "http://ec2-54-93-62-124.eu-central-1.compute.amazonaws.com/ugarcia053/WEB/selectuser.php?user="+user;
        TaskGetFotoPerfil task2 = new TaskGetFotoPerfil(url2, fotoperfil, c);
        task2.execute();


        if (savedInstanceState != null) {
            String fotoen64 = savedInstanceState.getString("fotoen64");
            if (fotoen64!=null){
                byte[] decodedBytes = Base64.getDecoder().decode(fotoen64);
                bitmapRedimensionado = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                fotoperfil.setImageBitmap(bitmapRedimensionado);
            }
        }

        // objeto OrientationEventListener para detectar cambios de orientación de pantalla
        OrientationEventListener orientationEventListener = new OrientationEventListener(this) {
            @Override
            public void onOrientationChanged(int orientation) {
            }
        };
        orientationEventListener.enable();

    }

    public void guardarCambios(View v){

        // llamada a trabajo asincrono en segundo plano para actualizar la info del usuario

        editPass = findViewById(R.id.editPass);
        spinnerGenero = findViewById(R.id.editGenero);
        editEdad = findViewById(R.id.editEdad);
        editPeso = findViewById(R.id.editPeso);
        editAltura = findViewById(R.id.editAltura);
        fotoperfil = findViewById(R.id.fotoperfil);

        String pass = editPass.getText().toString();
        String genero = spinnerGenero.getSelectedItem().toString();
        String edad = editEdad.getText().toString();
        String peso = editPeso.getText().toString();
        String altura = editAltura.getText().toString();
        String passEncriptada;


        // obtener bitmap del imageview actual --> comprimirla --> convertirlo en base64
        String fotoen64="default";
        if (fotoperfil.getDrawable()!=null) {
            Bitmap bitmap = ((BitmapDrawable) fotoperfil.getDrawable()).getBitmap();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            fotoen64 = new String(Base64.getEncoder().encode(byteArray));
        }

        // encriptar y actualizar la contraseña solo si la ha cambiado
        if (pass.equals("")){
            passEncriptada = passActual;
        }else{
            passEncriptada = EncriptadorContraseñas.encrypt(pass);
        }

        String url = "http://ec2-54-93-62-124.eu-central-1.compute.amazonaws.com/ugarcia053/WEB/updateuser.php";
        String payload = "user="+user+"&pass="+passEncriptada+"&genero="+genero+"&edad="+edad+"&peso="+peso+"&altura="+altura+"&fotoperfil="+fotoen64;
        TaskUpdateUsuario task = new TaskUpdateUsuario(url, payload, c);
        task.execute();

    }

    public void setFotopPerfil(View v){
        // dialogo para setear la foto de perfil con dos opciones --> (1)camara // (2)galeria
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.str135))
                .setIcon(R.drawable.logo)
                .setItems(new CharSequence[]{getString(R.string.str136), getString(R.string.str137)}, (dialog, which) -> {
                    switch (which) {

                        case 0:
                            fotoperfil = findViewById(R.id.fotoperfil);
                            anchoDestino = fotoperfil.getWidth();
                            altoDestino = fotoperfil.getHeight();
                            Intent elIntentFoto= new Intent(MediaStore. ACTION_IMAGE_CAPTURE );
                            takePictureLauncher.launch(elIntentFoto);
                            break;

                        case 1:
                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                            // agregar el siguiente extra para establecer la orientación de la galería a la orientación actual de la pantalla
                            int orientation = getResources().getConfiguration().orientation;
                            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                                intent.putExtra("orientation", ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                            } else {
                                intent.putExtra("orientation", ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                            }
                            startActivityForResult(intent, PICK_IMAGE_REQUEST);
                            break;
                    }
                })
                .show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // si la imagen viene de la galeria, primero reducir la calidad y despues colocarla en el imageview cuando el proceso termine
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            InputStream inputStream = null;
            try {
                inputStream = getContentResolver().openInputStream(uri);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            bitmapOriginal  = BitmapFactory.decodeStream(inputStream);
            fotoperfil.setImageBitmap(bitmapRedimensionado);

            // Reducir la calidad de la imagen al 50%
            int calidad = 50; // porcentaje de calidad de la imagen (0-100)
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmapOriginal.compress(Bitmap.CompressFormat.JPEG, calidad, byteArrayOutputStream);

            // Convertir el ByteArrayOutputStream en un array de bytes
            byte[] byteArray = byteArrayOutputStream.toByteArray();

            // Decodificar el array de bytes en un objeto Bitmap
            bitmapRedimensionado = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

            // Mostrar el Bitmap redimensionado en una ImageView
            fotoperfil.setImageBitmap(bitmapRedimensionado);

        }

        // una vez colocada la imagen restablecer la orientación de la pantalla a su valor predeterminado
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

    }


    // restablecer la orientación de la pantalla a su valor predeterminado después de cualquier cambio de orientación de pantalla
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }

    protected void onSaveInstanceState(Bundle savedInstanceState) {
        // guardar el idioma seleccionado a ya que a la hora de rotar sino se pondria
        // por defecto el idioma predetermionado y no el elegido por el usuario
        super.onSaveInstanceState(savedInstanceState);
        preferenciasCargadas=false;
        if (bitmapRedimensionado!=null){
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmapRedimensionado.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            String fotoen64 = Base64.getEncoder().encodeToString(byteArray);
            savedInstanceState.putString("fotoen64", fotoen64);
        }
    }

    @Override
    public void onBackPressed() {
        // Aquí puedes escribir el código que quieres que se ejecute cuando se presiona el botón de "Atrás"

        // Si quieres mantener el comportamiento predeterminado, llama al método super.onBackPressed()
        super.onBackPressed();
        ActividadPrincipal.actividadPrincipal.finish();
        Intent i = new Intent(this, ActividadPrincipal.class);
        i.putExtra("User", user);
        startActivity(i);
        finish();
    }

}