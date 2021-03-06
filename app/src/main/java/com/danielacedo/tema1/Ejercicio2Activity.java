package com.danielacedo.tema1;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Locale;

public class Ejercicio2Activity extends AppCompatActivity {
    private static final double CONVERSION_CM_PULGADAS = 0.393701;

    private Button btn_ConvertirCm, btn_CambiarModo;
    private EditText edt_Cm, edt_Inches;
    private TextView txv_Cm, txv_Inches;
    private boolean modoCentimentros = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.ej2_title);
        setContentView(R.layout.activity_ejercicio2);

        //Initialize the Views
        btn_ConvertirCm = (Button)findViewById(R.id.btn_ConvertirCm);
        btn_CambiarModo = (Button)findViewById(R.id.btn_CambiarModo);
        edt_Cm = (EditText)findViewById(R.id.edt_Cm);
        edt_Inches = (EditText)findViewById(R.id.edt_Inches);
        txv_Cm = (TextView)findViewById(R.id.txv_Cm);
        txv_Inches = (TextView)findViewById(R.id.txv_Inches);

        //Implementing the onClick listener using anonymous class
        btn_ConvertirCm.setOnClickListener(new View.OnClickListener() {
            /**
             * Anonymous class for the onClickListener implementation. This button converts the value written in the field depending on which mode is currently selected.
             * The mode is switched between centimeter to inches and inches to centimeter after each click.
             * @author Daniel Acedo Calderón
             * @param v The view that is clicked
             */
            @Override
            public void onClick(View v) {
                clickConvertir();
            }
        });

        btn_CambiarModo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                changeMode();
            }
        });
    }

    private void clickConvertir(){
        if(modoCentimentros){
            try{
                double cm = Double.parseDouble(edt_Cm.getText().toString());
                edt_Inches.setText(String.format(Locale.US, "%.2f", cmToInches(cm)));
            }catch(NumberFormatException e){

            }
        }else{
            try{
                double inches = Double.parseDouble(edt_Cm.getText().toString());
                edt_Inches.setText(String.format(Locale.US, "%.2f", inchesToCm(inches)));
            }catch(NumberFormatException e){

            }
        }
    }

    private void changeMode(){
        if(modoCentimentros){
                //Switch titles and visual style
                edt_Cm.setBackground(getResources().getDrawable(R.drawable.cm_edt_border_inch));
                txv_Cm.setText(getResources().getString(R.string.txv_Cm_text_inches));
                edt_Inches.setBackground(getResources().getDrawable(R.drawable.cm_edt_border_cm));
                txv_Inches.setText(getResources().getString(R.string.txv_Cm_text_cm));

                //Switch numeric values
                String cm = edt_Cm.getText().toString();
                edt_Cm.setText(edt_Inches.getText());
                edt_Inches.setText(cm);

                modoCentimentros = false;

        }else{
                //Switch titles and visual style
                edt_Cm.setBackground(getResources().getDrawable(R.drawable.cm_edt_border_cm));
                txv_Cm.setText(getResources().getString(R.string.txv_Cm_text_cm));
                edt_Inches.setBackground(getResources().getDrawable(R.drawable.cm_edt_border_inch));
                txv_Inches.setText(getResources().getString(R.string.txv_Cm_text_inches));

                //Switch numeric values
                String cm = edt_Inches.getText().toString();
                edt_Inches.setText(edt_Cm.getText());
                edt_Cm.setText(cm);
                modoCentimentros = true;
        }
    }

    double cmToInches(double cm){
        return cm * CONVERSION_CM_PULGADAS;
    }
    double inchesToCm(double inches){return inches / CONVERSION_CM_PULGADAS;}
}
