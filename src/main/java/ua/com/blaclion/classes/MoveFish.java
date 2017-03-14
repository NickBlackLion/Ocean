package ua.com.blaclion.classes;

import org.apache.log4j.Logger;
import ua.com.blaclion.abstract_classes.Fish;

import java.util.concurrent.TimeUnit;

/**
 * Class that moves fish in its own thread
 */
public class MoveFish implements Runnable {
    private Fish fish;
    private Ocean ocean;
    private Logger logger = Logger.getLogger(this.getClass());
    private boolean isRunning = false;
    private int timeOut;
    private int circle;

    public MoveFish(Fish fish, Ocean ocean) {
        this.fish = fish;
        this.ocean = ocean;
        isRunning = true;
        timeOut = 3000;
        circle = 0;
    }

    @Override
    public void run() {
        while(isRunning) {
            fish.swim();
            logger.info(fish.getExemplar() + " " + Thread.currentThread());
            ocean.repaint();
            holdNextStep();
            if (timeOut == 0) {
                circle++;
            }
            if (circle == 2) {
                setTimeOutToNorm();
                circle = 0;
            }
        }
    }

    public void kill(){
        isRunning = false;
    }

    public void wakeUp(){
        isRunning = true;
    }

    public Fish getFish() {
        return fish;
    }

    /**
     * Method that makes fish sleep for a while
     */
    public void holdNextStep() {
        try {
            TimeUnit.MILLISECONDS.sleep(timeOut);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setTimeOutToZero() {
        timeOut = 0;
    }

    private void setTimeOutToNorm() {
        timeOut = 3000;
    }
}
