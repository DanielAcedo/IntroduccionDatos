package com.danielacedo.tema1;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

public class Ejercicio1Activity extends AppCompatActivity {

    private String urlApiRatio = "http://api.fixer.io/latest";

    private Conversion conversion = null;

    private TextView txv_InfoDivisa, txv_Euros, txv_Dolares;
    private EditText edt_Dolares, edt_Euros;
    private RadioButton rbt_EurosADolares, rbt_DolaresAEuros;
    private Button btn_ActualizarRatio, btn_Convertir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.ej1_title);
        setContentView(R.layout.activity_ejercicio1);

        //Coger los Views
        txv_InfoDivisa = (TextView)findViewById(R.id.txv_InfoDivisa);
        txv_Euros = (TextView)findViewById(R.id.txv_Euros);
        txv_Dolares = (TextView)findViewById(R.id.txv_Dolares);
        edt_Euros = (EditText)findViewById(R.id.edt_Euros);
        edt_Dolares = (EditText)findViewById(R.id.edt_Dolares);
        btn_ActualizarRatio = (Button)findViewById(R.id.btn_ActualizarRatio);
        btn_Convertir = (Button)findViewById(R.id.btn_Convertir);
        rbt_DolaresAEuros = (RadioButton)findViewById(R.id.rbt_DolaresAEuros);
        rbt_EurosADolares = (RadioButton)findViewById(R.id.rbt_EurosADolares);


        //Inicializar valores

        conversion = new Conversion();
        edt_Dolares.setText("0.00");
        edt_Euros.setText("0.00");
        rbt_EurosADolares.setChecked(true);
        //Llamar al servidor para coger los datos del ratio de divisas
        actualizarDivisas();
    }

    public void onClick(View v)  {
        switch (v.getId()){
            case R.id.btn_ActualizarRatio:
                actualizarDivisas();
                break;
            case R.id.btn_Convertir:
                if(rbt_DolaresAEuros.isChecked()){
                    parsearDolares();
                }else if(rbt_EurosADolares.isChecked()){
                    parsearEuros();
                }
        }

    }

    private void parsearEuros(){
        try{
            double euros = Double.parseDouble(edt_Euros.getText().toString());
            edt_Euros.setText(String.format(Locale.US,"%.2f", euros)); //Añadimos los decimales si lo introducido es entero
            edt_Dolares.setText(String.format(Locale.US, "%.2f", conversion.ConvertirADivisa(euros)));
        }catch(NumberFormatException e){
            mostrar(getResources().getString(R.string.parseEurosError));
        }
    }

    private void parsearDolares(){
        try{
            double dolares = Double.parseDouble(edt_Dolares.getText().toString());
            edt_Dolares.setText(String.format(Locale.US,"%.2f", dolares)); //Añadimos los decimales si lo introducido es entero
            edt_Euros.setText(String.format(Locale.US, "%.2f", conversion.ConvertirAEuros(dolares))); //Se convierte con 2 decimales
        }catch(NumberFormatException e){
            mostrar(getResources().getString(R.string.parseDolaresError));
        }
    }

    private void actualizarDivisas(){
        btn_ActualizarRatio.setText(R.string.btn_InfoDivisa_text_conectando);
        new GetHttpResponse().execute(urlApiRatio);
    }


    public void mostrar(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    //Clase que ejecuta el método asíncrono para hacer la petición HTTP
    public class GetHttpResponse extends AsyncTask<String, String, String> {

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
            //Una vez recibida la respuesta GET con los datos, procesamos el JSON
            if(msg != null && error != true){
                try{
                    JSONObject json = new JSONObject(msg);
                    JSONObject ratios = json.getJSONObject("rates");
                    conversion.setRatio(ratios.getDouble(conversion.getCodPais()));

                }catch (JSONException e){
                    error=true;
                }
            }

            //Si ha habido un error en la conexion o al procesar los datos se fija un ratio fijo.
            if(error==true){
                conversion.setRatio(conversion.RATIO_DEFECTO);
                txv_InfoDivisa.setText(getResources().getString(R.string.txv_InfoDivisa_text_error) + "\n" + getResources().getString(R.string.txv_InfoDivisa_text_correcto)+String.valueOf(conversion.getRatio())+" EUR/"+conversion.getCodPais());
            }else{
                //Si no, se actualiza la divisa
                txv_InfoDivisa.setText(getResources().getString(R.string.txv_InfoDivisa_text_correcto)+String.valueOf(conversion.getRatio())+" EUR/"+conversion.getCodPais());
            }

            btn_ActualizarRatio.setText(R.string.btn_ActualizarRatio_text);
        }
    }
}
