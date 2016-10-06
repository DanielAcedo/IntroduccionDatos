package com.danielacedo.tema1;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Ejercicio4Activity extends AppCompatActivity {

    private TextView txv_CoffeeCounter, txv_TimeCounter;
    private Button btn_AddTime, btn_SubstractTime, btn_StartCounter, btn_Pause;
    private CoffeeTimer coffeeTimer;
    private CoffeeCounter coffeeCounter;

    protected boolean isPaused;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio4);
        setTitle(R.string.ej4_title);

        isPaused = false;

        txv_CoffeeCounter = (TextView)findViewById(R.id.txv_CoffeeCounter);
        txv_TimeCounter = (TextView)findViewById(R.id.txv_TimeCounter);
        btn_AddTime = (Button)findViewById(R.id.btn_AddTime);
        btn_SubstractTime = (Button)findViewById(R.id.btn_SubtractTime);
        btn_StartCounter = (Button)findViewById(R.id.btn_StartCounter);
        btn_Pause = (Button)findViewById(R.id.btn_Pause);
        coffeeCounter = new CoffeeCounter();

        btn_AddTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coffeeCounter.addTime();
                refreshTimerDisplay();
            }
        });

        btn_SubstractTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coffeeCounter.substractTime();
                refreshTimerDisplay();
            }
        });

        btn_StartCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
            }
        });

        btn_Pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer();
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
        txv_CoffeeCounter.setText(String.valueOf(coffeeCounter.getCoffees()));
    }

    public void pauseTimer(){
        if(isPaused){
            coffeeTimer = new CoffeeTimer(coffeeCounter.toMillis(), 1000);
            coffeeTimer.start();
            isPaused = false;
            btn_Pause.setText(getResources().getString(R.string.btn_Pause));
        }else if(coffeeTimer!=null){
            coffeeTimer.cancel();
            coffeeTimer = null;
            isPaused = true;
            btn_Pause.setText(getResources().getString(R.string.btn_Pause_Restart));
        }

    }

    public void timerFinished(){
        txv_TimeCounter.setText(Ejercicio4Activity.this.getResources().getString(R.string.txv_TimeCounter_finished));
        coffeeCounter.addCoffee();
        refreshCoffeeCount();
    }

    public class CoffeeTimer extends CountDownTimer {

        public CoffeeTimer(long millisToFuture, long tickInterval){
            super(millisToFuture, tickInterval);
            isPaused = false;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            coffeeCounter.substractTime();
            refreshTimerDisplay();
        }

        @Override
        public void onFinish() {
            timerFinished();
            coffeeCounter.resetTime();

        }
    }
}
