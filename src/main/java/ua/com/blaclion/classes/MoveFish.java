package ua.com.blaclion.classes;

import org.apache.log4j.Logger;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by nick on 24.02.17.
 */
public class MoveFish implements Runnable {
    private Fish fish;
    private Ocean ocean;
    private Logger logger = Logger.getLogger(this.getClass());
    private boolean isRunning = false;

    public MoveFish(Fish fish, Ocean ocean) {
        this.fish = fish;
        this.ocean = ocean;
        isRunning = true;
    }

    @Override
    public void run() {
        /*Random random = new Random(System.currentTimeMillis());
        int seconds = random.nextInt(5);*/

        while(isRunning) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            fish.swim();
            ocean.repaint();
        }
    }

    public void kill(){
        isRunning = false;
    }

    public void wakeUp(){
        isRunning = true;
    }
}
