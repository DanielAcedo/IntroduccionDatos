package com.danielacedo.tema1;

import android.os.CountDownTimer;

/**
 * Created by usuario on 4/10/16.
 */

public class CoffeeTimer extends CountDownTimer {

    public CoffeeTimer(long millisToFuture, long tickInterval){
        super(millisToFuture, tickInterval);
    }

    @Override
    public void onTick(long millisUntilFinished) {

    }

    @Override
    public void onFinish() {

    }
}
