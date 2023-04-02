package com.example.pruebamonigote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class Toolbar extends AppCompatActivity{
    DrawerLayout elmenudesplegable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);
        Toolbar activity = this;

        setSupportActionBar(findViewById(R.id.labarra));
        getSupportActionBar().setHomeAsUpIndicator(android.R.drawable.ic_dialog_info);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        elmenudesplegable = findViewById(R.id.drawer_layout);
        NavigationView elnavigation = findViewById(R.id.elnavigationview);
        elnavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
               case R.id.microfono:
                   Intent intent = new Intent(activity, ActividadListaRutinasPred.class);
                   startActivity(intent);
                   break;
               case R.id.localizacion:
                   break;
           }
           elmenudesplegable.closeDrawers();
           return false;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                elmenudesplegable.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (elmenudesplegable.isDrawerOpen(GravityCompat.START)) {
            elmenudesplegable.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.opcionesdrawer,menu);
        return true;
    }
}