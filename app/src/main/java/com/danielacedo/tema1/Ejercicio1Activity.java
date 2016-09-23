package com.danielacedo.tema1;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

    private String urlApiRatio = "http://api.fixer.io/latest";
    private String codDivisa = "USD";
    private double ratioActual = 0.0;

    TextView txv_InfoDivisa, txv_Euros, txv_Dolares;
    EditText edt_Dolares, edt_Euros;
    Button btn_ActualizarRatio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.ej1_title);
        setContentView(R.layout.activity_ejercicio1);
        txv_InfoDivisa = (TextView)findViewById(R.id.txv_InfoDivisa);
        txv_Euros = (TextView)findViewById(R.id.txv_Euros);
        txv_Dolares = (TextView)findViewById(R.id.txv_Dolares);
        edt_Euros = (EditText)findViewById(R.id.edt_Euros);
        edt_Dolares = (EditText)findViewById(R.id.edt_Dolares);
        btn_ActualizarRatio = (Button)findViewById(R.id.btn_ActualizarRatio);

        //Llamar al servidor para coger los datos del ratio de divisas
        ActualizarDivisas();
    }

    public void onClick(View v)  {
        ActualizarDivisas();
    }

    private void ActualizarDivisas(){
        btn_ActualizarRatio.setText(R.string.btn_InfoDivisa_text_conectando);
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
        boolean error;

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
                conection.disconnect();
                msg=response.toString();

            } catch (MalformedURLException e) {

                error=true;
            } catch (IOException e){

                error=true;
            }


            return null;
        }

        @Override
        protected void onPostExecute(String url){
            if(msg != null && error != true){
                try{
                    JSONObject json = new JSONObject(msg);
                    JSONObject ratios = json.getJSONObject("rates");
                    ratioActual = ratios.getDouble(codDivisa);

                }catch (JSONException e){
                    Mostrar("Error al recibir el ratio de divisas del servidor");
                    error=true;
                }
            }

            if(error==true){
                txv_InfoDivisa.setText(R.string.txv_InfoDivisa_text_error);
            }else{

                txv_InfoDivisa.setText("El ratio de divisa actual es: \n"+String.valueOf(ratioActual)+" EUR/"+codDivisa);
            }

            btn_ActualizarRatio.setText(R.string.btn_ActualizarRatio_text);
        }
    }
}
