package com.danielacedo.tema1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Ejercicio2Activity extends AppCompatActivity {
    private static final double CONVERSION_CM_PULGADAS = 0.393701;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.ej2_title);
        setContentView(R.layout.activity_ejercicio2);
    }

    double centimetrosAPulgadas(double cm){
        return cm * CONVERSION_CM_PULGADAS;
    }
}
