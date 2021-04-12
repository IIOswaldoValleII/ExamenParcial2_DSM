package com.example.examenparcial2_dsm;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        getSupportActionBar().hide();

        TimerTask incio = new TimerTask() {
            @Override
            public void run() {
                Intent splash = new Intent(SplashActivity.this, InicioSesion.class);
                startActivity(splash);
                finish();
            }
        };

        Timer tiempo = new Timer();
        tiempo.schedule(incio, 5000);
    }
}