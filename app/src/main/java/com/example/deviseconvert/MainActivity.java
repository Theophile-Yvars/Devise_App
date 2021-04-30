package com.example.deviseconvert;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

/**
 * Il n'y a rien dans l'activité main.
 * Tout est géré avec des framents, et piloté par une navigation.
 * Voir le .xml de cette activité
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}