package com.example.projetandroid;

import android.widget.ProgressBar;

import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadTest {
    private ProgressBar pb_main_progressionTH;
    private AtomicBoolean isRunning = new AtomicBoolean(false);
    private java.lang.Thread monthread;
    public ThreadTest(ProgressBar p) {
        pb_main_progressionTH = p;
    }
    public void Go() {
        pb_main_progressionTH.setProgress(0);
        monthread = new java.lang.Thread(new Runnable() {
            @ Override
            public void run() {
                for(int i=0;i<20 && isRunning.get();i++){
                    pb_main_progressionTH.incrementProgressBy(5);
                    try {
                        java.lang.Thread.sleep(1000);
                    }catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        isRunning.set(true);
        monthread.start();
    }

    public void Stop(){
        isRunning.set(false);
        monthread.interrupt();
        pb_main_progressionTH.setProgress(0);
    }
}
