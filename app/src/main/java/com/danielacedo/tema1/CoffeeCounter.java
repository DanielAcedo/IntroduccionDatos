package com.danielacedo.tema1;

import android.os.CountDownTimer;

/**
 * Created by usuario on 4/10/16.
 */

public class CoffeeCounter{

    //Variables for a yet to implement pause mechanic
    private int minutesPaused;
    private int secondsPaused;

    //The time your coffee break lasts
    private int minutesCountDown;
    private int secondsCountDown;

    //Number of coffees had
    private int coffees;

    public CoffeeCounter(){
        this.minutesCountDown = 0;
        this.secondsCountDown = 0;
        this.minutesPaused = 0;
        this.secondsPaused = 0;
        this.coffees = 0;
    }

    public void substractTime(){
        if(secondsCountDown==0 && minutesCountDown!=0){
            minutesCountDown--;
            secondsCountDown = 59;

            return;
        }

        if(secondsCountDown!=0){
            secondsCountDown--;
            return;
        }
    }

    public void addTime(){
        if(secondsCountDown==59){
            minutesCountDown++;
            secondsCountDown=0;

            return;
        }

        secondsCountDown++;
    }

    public void addCoffee(){
        coffees++;
    }

    public int getMinutesCountDown() {
        return minutesCountDown;
    }

    public int getSecondsCountDown() {
        return secondsCountDown;
    }

    public int getCoffees() {
        return coffees;
    }

    public String formatTime(){
        return String.format("%02d",minutesCountDown)+":"+String.format("%02d", secondsCountDown);
    }

    public void resetTime(){
        secondsCountDown = 0;
        minutesCountDown = 0;
    }

    public long toMillis(){
        long secondsInMillis = secondsCountDown * 1000;
        long minutesInMillis = minutesCountDown * 60 * 1000;

        return secondsInMillis+minutesInMillis;
    }

    public void storeTimeInMillis(long millis){
        minutesCountDown = (int)(millis/1000)/60;
        secondsCountDown = (int)(millis/1000)%60;

    }

    public void dumpToPause(){
        minutesPaused = minutesCountDown;
        secondsPaused = secondsCountDown;
    }


}
