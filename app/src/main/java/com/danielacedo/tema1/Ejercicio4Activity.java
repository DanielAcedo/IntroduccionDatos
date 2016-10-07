package com.danielacedo.tema1;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Ejercicio4Activity extends AppCompatActivity {

    private final int MILLIS_OFSET = 100; //Offset applied to the timer to be accurate

    private TextView txv_CoffeeCounter, txv_TimeCounter;
    private Button btn_AddTime, btn_SubstractTime, btn_StartCounter, btn_Pause;
    private CoffeeTimer coffeeTimer;
    private CoffeeCounter coffeeCounter;

    protected boolean isPaused;
    protected boolean isRunning;

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
                handleStartButton();
            }
        });

        btn_Pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer();
            }
        });
        btn_Pause.setEnabled(false);
    }

    public void startTimer(){
        coffeeTimer = new CoffeeTimer(coffeeCounter.toMillis()+MILLIS_OFSET, 1000);
        coffeeTimer.start();
        btn_Pause.setEnabled(true);
        btn_Pause.setText(getResources().getString(R.string.btn_Pause));
        btn_StartCounter.setText(getResources().getString(R.string.btn_StartCounter_Cancel));
        isRunning = true;
    }

    public void handleStartButton(){
        if(isRunning){
            if(coffeeTimer!=null){
                coffeeTimer.cancel();
            }

            coffeeTimer = null;
            isPaused = false;
            isRunning = false;
            coffeeCounter.resetTime();
            btn_Pause.setText(getResources().getString(R.string.btn_Pause));
            btn_StartCounter.setText(getResources().getString(R.string.btn_StartCounter));
            btn_Pause.setEnabled(false);
            btn_AddTime.setEnabled(true);
            btn_SubstractTime.setEnabled(true);
            refreshTimerDisplay();
        }else{
            if(coffeeCounter.getMinutesCountDown() == 0 && coffeeCounter.getSecondsCountDown() == 0){
                Toast.makeText(Ejercicio4Activity.this, getResources().getString(R.string.err_Timer_Start_Zero), Toast.LENGTH_SHORT).show();
            }else{
                startTimer();
                btn_AddTime.setEnabled(false);
                btn_SubstractTime.setEnabled(false);
            }

        }
    }

    public void refreshTimerDisplay(){
        txv_TimeCounter.setText(coffeeCounter.formatTime());
    }

    public void refreshCoffeeCount(){
        txv_CoffeeCounter.setText(String.valueOf(coffeeCounter.getCoffees()));
    }

    public void pauseTimer(){
        if(isPaused){
            coffeeTimer = new CoffeeTimer(coffeeCounter.toMillis()+MILLIS_OFSET, 1000);
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
        btn_Pause.setEnabled(false);
        btn_AddTime.setEnabled(true);
        btn_SubstractTime.setEnabled(true);
        btn_Pause.setText(getResources().getString(R.string.btn_Pause));
        btn_StartCounter.setText(getResources().getString(R.string.btn_StartCounter));
        coffeeCounter.resetTime();
        isRunning = false;
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
        }
    }
}
