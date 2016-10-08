package com.danielacedo.tema1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.menu_title);
        setContentView(R.layout.activity_menu);
    }

    public void onClick (View v){
        Intent intento;
        switch(v.getId()){
            case R.id.btn_Ejercicio1:
                intento = new Intent(this, Ejercicio1Activity.class);
                startActivity(intento);
                break;
            case R.id.btn_Ejercicio2:
                intento = new Intent(this, Ejercicio2Activity.class);
                startActivity(intento);
                break;
            case R.id.btn_Ejercicio3:
                intento = new Intent(this, Ejercicio3Activity.class);
                startActivity(intento);
                break;
            case R.id.btn_Ejercicio4:
                intento = new Intent(this, Ejercicio4Activity.class);
                startActivity(intento);
                break;
        }
    }
}
