package com.cromero.pucmunch.control;

public class TimeCountDown implements Runnable{

    public int seconds;

    public TimeCountDown(int seconds) {
        this.seconds = seconds;
    }


    @Override
    public void run() {
        while(seconds > 0){
            try {
                seconds--;
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
