package ua.com.blaclion.classes;

import org.apache.log4j.Logger;
import ua.com.blaclion.abstract_classes.Fish;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * Class that moves fish in its own thread
 */
public class MoveFish {
    private Fish fish;
    private Ocean ocean;
    private Logger logger = Logger.getLogger(this.getClass());
    private int timeOut;
    private Timer timer;
    private MoveFishTask moveFishTask;

    public MoveFish(Fish fish, Ocean ocean) {
        this.fish = fish;
        this.ocean = ocean;
        timeOut = 2000;
        timer = new Timer();
        moveFishTask = new MoveFishTask();
    }

    public void run() {
        setTimer(0);
    }

    public void stop() {
        timer.cancel();
    }

    public void restart() {
        timer.cancel();

        swimFish();

        setTimer(2000);
    }

    public Fish getFish() {
        return fish;
    }

    private class MoveFishTask extends TimerTask {
        @Override
        public void run() {
            swimFish();
        }
    }

    private void setTimer(int delay) {
        timer = new Timer();
        moveFishTask = new MoveFishTask();
        timer.scheduleAtFixedRate(moveFishTask, delay, timeOut);
    }

    private void swimFish() {
        fish.swim();
        ocean.repaint();
    }
}
