package com.danielacedo.tema1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Ejercicio1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.ej1_title);
        setContentView(R.layout.activity_ejercicio1);
    }

    public void onClick(View v) {
        try {
            URL url = new URL("http://api.fixer.io/latest");
            HttpURLConnection conection = (HttpURLConnection) url.openConnection();
            conection.setRequestMethod("GET");
            conection.setDoOutput(true);

            DataOutputStream out = new DataOutputStream(conection.getOutputStream());
            out.writeBytes("0");
            out.flush();
            out.close();

            int responseCode = conection.getResponseCode();
            BufferedReader br = (BufferedReader) conection.getInputStream();
        } catch (MalformedURLException e) {

        } catch (IOException e){

        }
        finally{

        }

    }
}
