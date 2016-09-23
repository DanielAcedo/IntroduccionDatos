package com.danielacedo.tema1;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Ejercicio1Activity extends AppCompatActivity {

    private String urlApiRatio = "http://api.fkhkhjixer.io/latest";
    private String codDivisa = "USD";
    private double ratioActual = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.ej1_title);
        setContentView(R.layout.activity_ejercicio1);
    }

    public void onClick(View v)  {
       new getHttpResponse().execute(urlApiRatio);
    }

    public void Mostrar(){
        Toast.makeText(this, String.valueOf(ratioActual), Toast.LENGTH_SHORT).show();
    }

    public void Mostrar(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    //Clase que ejecuta el método asíncrono para hacer la petición HTTP
    public class getHttpResponse extends AsyncTask<String, String, String> {

        String msg=null;
        int responseCode;

        @Override
        protected String doInBackground(String... params) {
            int connectTimeOut = 15000;
            int readTimeOut = 10000;

            URL url = null;
            HttpURLConnection conection = null;
            try {
                url = new URL(params[0]);
                conection = (HttpURLConnection) url.openConnection();
                conection.setReadTimeout(readTimeOut);
                conection.setConnectTimeout(connectTimeOut);

                conection.connect();


                responseCode = conection.getResponseCode();

                StringBuilder response = new StringBuilder();
                BufferedReader br = new BufferedReader(new InputStreamReader(conection.getInputStream()));
                String line = "";

                while((line=br.readLine())!=null){
                    response.append(line);
                }

                br.close();
                msg=response.toString();

            } catch (MalformedURLException e) {
               Mostrar(e.getMessage());
            } catch (IOException e){
                Mostrar(e.getMessage());
            }
            finally{
                if (conection != null){
                    conection.disconnect();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String url){
            try{
                JSONObject json = new JSONObject(msg);
                JSONObject ratios = json.getJSONObject("rates");
                ratioActual = ratios.getDouble("USD");
                Mostrar();
            }catch (JSONException e){

            }

        }
    }
}
