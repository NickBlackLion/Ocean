package ua.com.blaclion.classes;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Class-container that implement thread safety for few methods
 * @param <T>
 */
public class SafetyList<T> extends ArrayList<T> {
    private Lock lock;
    private Logger logger = Logger.getLogger(this.getClass());

    public SafetyList() {
        lock = new ReentrantLock();
    }

    @Override
    public boolean add(T t) {
        lock.lock();
        try {
            return super.add(t);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public T get(int index) {
        lock.lock();
        try {
            return super.get(index);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean remove(Object o) {
        lock.lock();
        try {
            return super.remove(o);
        } finally {
            lock.unlock();
        }
    }
}
