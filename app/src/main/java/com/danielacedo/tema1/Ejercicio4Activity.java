package com.danielacedo.tema1;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class Ejercicio4Activity extends AppCompatActivity {

    private final int MILLIS_OFSET = 100; //Offset applied to the timer to be accurate
    private final int COFFEE_LIMIT = 10; //Number of coffees that will trigger the warning

    private TextView txv_CoffeeCounter, txv_TimeCounter;
    private Button btn_AddTime, btn_SubstractTime, btn_StartCounter, btn_Pause, btn_Restart;
    private CoffeeTimer coffeeTimer;
    private CoffeeCounter coffeeCounter;
    private MediaPlayer mp;

    protected boolean isPaused;
    protected boolean isRunning;
    protected boolean limitReached;

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
        btn_Restart = (Button)findViewById(R.id.btn_Restart);
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

        btn_Restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restart_Coffees();
            }
        });
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
        checkCoffeesLimit();
        playFinishSound();
    }

    public void playFinishSound(){
        if(mp == null){
            mp = MediaPlayer.create(Ejercicio4Activity.this, R.raw.imgburn);
        }else if(mp.isPlaying()){
            try{
                mp.reset();
                AssetFileDescriptor afd = Ejercicio4Activity.this.getResources().openRawResourceFd(R.raw.imgburn);
                mp.setDataSource(afd.getFileDescriptor());
                mp.prepare();
            }catch(IOException e){
                Toast.makeText(Ejercicio4Activity.this, Ejercicio4Activity.this.getResources().getString(R.string.err_Play_FinishTimer), Toast.LENGTH_SHORT).show();
            }

        }

        mp.start();
        //mp.release();
    }

    public void checkCoffeesLimit(){
        if(coffeeCounter.getCoffees() >= COFFEE_LIMIT) {
            AlertDialog.Builder popup = new AlertDialog.Builder(this);
            popup.setTitle(Ejercicio4Activity.this.getResources().getString(R.string.warning_CoffeeLimit_Title));
            popup.setMessage(Ejercicio4Activity.this.getResources().getString(R.string.warning_CoffeeLimit_Body));
            popup.setPositiveButton("Ok", null);
            popup.show();
            limitReached = true;
            btn_Restart.setVisibility(View.VISIBLE);
            btn_StartCounter.setEnabled(false);
            btn_Pause.setEnabled(false);

        }
    }

    public void restart_Coffees(){
        if(limitReached){
            coffeeCounter.resetCoffees();
            limitReached = false;
            refreshCoffeeCount();
            btn_Restart.setVisibility(View.GONE);
            btn_StartCounter.setEnabled(true);
            btn_Pause.setEnabled(true);
        }
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
