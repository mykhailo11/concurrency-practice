package me.mt.threads;

import me.mt.threads.factory.api.AsyncServiceFactory;

import java.util.concurrent.atomic.AtomicInteger;

public class App {

    private int value;
    private final AtomicInteger atomicValue = new AtomicInteger(0);

    // custom thread
    private final class CustomThread extends Thread {

        @Override
        public void run() {
            for (int count = 0; count < 10; value++, count++);
        }

    }

    // runnable that can be used as a thread argument
    private final class CustomRunnable implements Runnable {

        @Override
        public void run() {
            for (int count = 0; count < 10; value++, count++);
        }

    }

    // Unpredictable
    public void incrementTwoTimesUnsafe(int value) {
        this.value = value;
        Thread threadOne = new CustomThread();
        Thread threadTwo = new Thread(new CustomRunnable());
        threadOne.start();
        threadTwo.start();
    }

    public void incrementTwoTimes(int value) {
        atomicValue.set(value);
        Thread threadOne = new Thread(() -> { for (int count = 0; count < 10; count++, atomicValue.getAndIncrement()); });
        Thread threadTwo = new Thread(() -> { for (int count = 0; count < 10; count++, atomicValue.getAndIncrement()); });
        threadOne.start();
        threadTwo.start();
    }

    public int getValue() {
        return value;
    }

    public int getAtomicValue() {
        return atomicValue.get();
    }



}
