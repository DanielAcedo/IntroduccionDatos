package com.danielacedo.tema1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class Ejercicio3Activity extends AppCompatActivity {

    private TextView txv_LinkEntry;
    private EditText edt_Link;
    private Button btn_OkWeb;
    private WebView wv_web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio3);
        setTitle(R.string.ej3_title);

        txv_LinkEntry = (TextView)findViewById(R.id.txv_LinkEntry);
        edt_Link = (EditText)findViewById(R.id.edt_Link);
        btn_OkWeb = (Button)findViewById(R.id.btn_OkWeb);
        wv_web = (WebView)findViewById(R.id.wv_web);

        btn_OkWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String link = edt_Link.getText().toString();
                if(!Pattern.matches("[http://].*", link)){
                    link = "http://"+link;
                }
                wv_web.loadUrl(link);

            }
        });

    }
    
}
