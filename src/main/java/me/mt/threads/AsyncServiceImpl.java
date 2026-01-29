package me.mt.threads;

import me.mt.threads.api.AsyncService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AsyncServiceImpl implements AsyncService {

    private final ExecutorService singleThreadExecutorService;
    private final ExecutorService multithreadExecutorService;
    private final ScheduledExecutorService scheduledExecutorService;

    public AsyncServiceImpl() {
        this.singleThreadExecutorService = Executors.newSingleThreadExecutor();
        this.multithreadExecutorService = Executors.newFixedThreadPool(5);
        this.scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    public void executeTask(Runnable runnable) {
        singleThreadExecutorService.submit(runnable);
    }

    @Override
    public void executeTasks(Runnable... runnableEntries) {
        for (Runnable runnable : runnableEntries) {
            multithreadExecutorService.submit(runnable);
        }
    }

    /**
     * Executes task after the specified delay in seconds
     * @param runnable task
     * @param delay time in seconds
     */
    @Override
    public void executeTaskAfterDelay(Runnable runnable, long delay) {
        scheduledExecutorService.schedule(runnable, delay, TimeUnit.SECONDS);
    }

    /**
     * Executes task at the specified rate
     * @param runnable task
     * @param rate time between each run
     */
    @Override
    public void executeTaskAtRate(Runnable runnable, long rate) {
        scheduledExecutorService.scheduleAtFixedRate(runnable, 0, rate, TimeUnit.SECONDS);
    }

    /**
     * Executes task at the specified delay after each run
     * @param runnable task
     * @param delay time after each run
     */
    @Override
    public void executeTaskAtDelay(Runnable runnable, long delay) {
        scheduledExecutorService.scheduleWithFixedDelay(runnable, 0, delay, TimeUnit.SECONDS);
    }

}
