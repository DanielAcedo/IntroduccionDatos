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
        switch(v.getId()){
            case R.id.btn_Ejercicio1:
                Intent intento = new Intent(this, Ejercicio1Activity.class);
                startActivity(intento);
        }
    }
}
