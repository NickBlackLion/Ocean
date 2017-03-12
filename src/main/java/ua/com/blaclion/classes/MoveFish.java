package ua.com.blaclion.classes;

import org.apache.log4j.Logger;
import ua.com.blaclion.abstract_classes.Fish;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Class that moves fish in its own thread
 */
public class MoveFish implements Runnable {
    private SafetyList<Fish> fishes;
    private Ocean ocean;
    private Logger logger = Logger.getLogger(this.getClass());
    private boolean isRunning = false;
    private Lock lock;
    private int timeOut;

    public MoveFish(Ocean ocean) {
        this.ocean = ocean;
        isRunning = true;
        fishes = new SafetyList<>();
        timeOut = 2000;
        lock = new ReentrantLock();
    }

    @Override
    public void run() {
        while(isRunning) {
            for (int i = 0; i < fishes.size(); i++) {
                Fish fish = fishes.get(i);
                fish.swim();
                if (fish.isKill()) {
                    fish.getMoveFish().removeFromMoveList(fish);
                }
            }
            ocean.repaint();
            holdNextStep();
            setTimeOutToNormal();
        }
    }

    public void kill() {
        isRunning = false;
    }

    public void wakeUp() {
        isRunning = true;
    }

    public void addToMoveList(Fish fish) {
        lock.lock();
        try {
            fishes.add(fish);
        }
        finally {
            lock.unlock();
        }
    }

    public void removeFromMoveList(Fish fish) {
        lock.lock();
        try {
            fishes.remove(fish);
        }
        finally {
            lock.unlock();
        }
    }

    /**
     * Method that makes fish sleep for a while
     */
    protected void holdNextStep() {
        try {
            TimeUnit.MILLISECONDS.sleep(timeOut);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setTimeOutToZero() {
        timeOut = 0;
    }

    private void setTimeOutToNormal() {
        timeOut = 2000;
    }
}
