package me.mt.threads.api;

public interface AsyncService {

    void executeTask(Runnable runnable);
    void executeTasks(Runnable... runnableEntries);
    void executeTaskAfterDelay(Runnable runnable, long delay);
    void executeTaskAtRate(Runnable runnable, long rate);
    void executeTaskAtDelay(Runnable runnable, long delay);

}
