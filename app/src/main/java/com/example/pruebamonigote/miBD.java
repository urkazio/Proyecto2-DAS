package com.example.pruebamonigote;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class miBD extends SQLiteOpenHelper {
    public miBD(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {  //se ejecuta cuando hay que crear la BD porque no existe.

        //crear base de datos de usuarios (fitproBD)
        sqLiteDatabase.execSQL("CREATE TABLE Usuarios ('Usuario' VARCHAR(255) PRIMARY KEY NOT NULL, 'Contraseña' VARCHAR(255) NOT NULL," +
                "'Genero' VARCHAR(255), 'Edad' NUMBER, 'Peso' INTEGER, 'Altura' INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) { //: se ejecuta cuando la versión de la BD que queremos usar y la que existe no coinciden

    }

}
