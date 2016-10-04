package com.danielacedo.tema1;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Ejercicio4Activity extends AppCompatActivity {

    private TextView txv_CoffeeCounter, txv_TimeCounter;
    private Button btn_AddTime, btn_SubstractTime, btn_StartCounter;
    private CoffeeTimer coffeeTimer;
    private CoffeeCounter coffeeCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio4);
        setTitle(R.string.ej4_title);

        txv_CoffeeCounter = (TextView)findViewById(R.id.txv_CoffeeCounter);
        txv_TimeCounter = (TextView)findViewById(R.id.txv_TimeCounter);
        btn_AddTime = (Button)findViewById(R.id.btn_AddTime);
        btn_SubstractTime = (Button)findViewById(R.id.btn_SubtractTime);
        btn_StartCounter = (Button)findViewById(R.id.btn_StartCounter);
        coffeeCounter = new CoffeeCounter();

        btn_AddTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coffeeCounter.addTime();
            }
        });

        btn_SubstractTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coffeeCounter.substractTime();
            }
        });

        btn_StartCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
            }
        });

    }

    public void startTimer(){
        coffeeTimer = new CoffeeTimer(coffeeCounter.toMillis(), 1000);
        coffeeTimer.start();
    }

    public void refreshTimerDisplay(){
        txv_TimeCounter.setText(coffeeCounter.formatTime());
    }

    public void refreshCoffeeCount(){
        txv_CoffeeCounter.setText(coffeeCounter.getCoffees());
    }

    public void timerFinished(){
        txv_TimeCounter.setText(R.string.txv_TimeCounter_finished);
        coffeeCounter.addCoffee();
    }

    public class CoffeeTimer extends CountDownTimer {

        public CoffeeTimer(long millisToFuture, long tickInterval){
            super(millisToFuture, tickInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            coffeeCounter.substractTime();
            refreshTimerDisplay();
        }

        @Override
        public void onFinish() {
            timerFinished();
        }
    }
}
