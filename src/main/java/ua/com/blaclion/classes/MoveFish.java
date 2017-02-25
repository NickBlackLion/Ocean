package ua.com.blaclion.classes;

import org.apache.log4j.Logger;

import javax.swing.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by nick on 24.02.17.
 */
public class MoveFish implements Runnable {
    private Fish fish;
    private JFrame frame;
    private Ocean ocean;
    private Logger logger = Logger.getLogger(this.getClass());
    private boolean isRunning = false;

    public MoveFish(Fish fish, Ocean ocean) {
        this.fish = fish;
        this.frame = null;
        this.ocean = ocean;
        isRunning = true;
    }

    @Override
    public void run() {
        while(isRunning) {
            fish.swim(8, 0);
            ocean.repaint();
            logger.info("sleep");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void kill(){
        isRunning = false;
    }

    public void wakeUp(){
        isRunning = true;
    }
}
