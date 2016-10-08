package com.danielacedo.tema1;

import android.os.CountDownTimer;

/**
 * Created by Daniel on 4/10/16.
 */

/**
 * Intermediate class between the timer and the view. It stores the current time and number of coffee breaks.
 * @author Daniel Acedo Calderón
 */
public class CoffeeCounter{

    //The time your coffee break lasts
    private int minutesCountDown;
    private int secondsCountDown;

    //Number of coffees had
    private int coffees;

    public CoffeeCounter(){
        this.minutesCountDown = 0;
        this.secondsCountDown = 0;
        this.coffees = 0;
    }

    /**
     * Substracts one second out of the total time
     * @author Daniel Acedo Calderón
     */
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

    /**
     * Adds one seconds to the total time
     * @author Daniel Acedo Calderón
     */
    public void addTime(){
        if(secondsCountDown==59){
            minutesCountDown++;
            secondsCountDown=0;

            return;
        }

        secondsCountDown++;
    }

    /**
     * Adds one coffee break to the counter
     * @author Daniel Acedo Calderón
     */
    public void addCoffee(){
        coffees++;
    }

    /**
     * Returns the number of minutes
     * @return Current minutes
     * @author Daniel Acedo Calderón
     */
    public int getMinutesCountDown() {
        return minutesCountDown;
    }

    /**
     * Returns the number of seconds
     * @return current seconds
     * @author Daniel Acedo Calderón
     */
    public int getSecondsCountDown() {
        return secondsCountDown;
    }

    /**
     * Returns the number of coffee breaks
     * @return Coffee breaks
     * @author Daniel Acedo Calderón
     */
    public int getCoffees() {
        return coffees;
    }

    /**
     * Returns the time as a string suitable for the view to display (mm:ss)
     * @return Formatted time mm:ss
     * @author Daniel Acedo Calderón
     */
    public String formatTime(){
        return String.format("%02d",minutesCountDown)+":"+String.format("%02d", secondsCountDown);
    }

    /**
     * Resets the time to zero
     * @author Daniel Acedo Calderón
     */
    public void resetTime(){
        secondsCountDown = 0;
        minutesCountDown = 0;
    }

    /**
     * Resets the coffees to zero
     * @author Daniel Acedo Calderón
     */
    public void resetCoffees(){
        coffees = 0;
    }

    /**
     * Converts the total time to milliseconds
     * @return The time in milliseconds
     * @author Daniel Acedo Calderón
     */
    public long toMillis(){
        long secondsInMillis = secondsCountDown * 1000;
        long minutesInMillis = minutesCountDown * 60 * 1000;

        return secondsInMillis+minutesInMillis;
    }

    /**
     * Converts the time in milliseconds to seconds and minutes
     * @param millis Milliseconds to convert
     */
    public void storeTimeInMillis(long millis){
        minutesCountDown = (int)(millis/1000)/60;
        secondsCountDown = (int)(millis/1000)%60;

    }



}
