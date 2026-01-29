package me.mt.threads.factory;

import me.mt.threads.api.AsyncService;
import me.mt.threads.factory.api.AsyncServiceFactory;

public class BlockingAsyncServiceFactoryImpl implements AsyncServiceFactory {

    /*
     * This is a utility interface so it can be wrapped in a singleton
     * if is not explicitly required to have multiple instances.
     */

    private static BlockingAsyncServiceFactoryImpl instance;

    public static BlockingAsyncServiceFactoryImpl getInstance() {
        if (instance == null) instance = new BlockingAsyncServiceFactoryImpl();
        return instance;
    }

    private BlockingAsyncServiceFactoryImpl() {}

    @Override
    public AsyncService createAsyncService() {
        // inline interface implementation
        return new AsyncService() {

            @Override
            public void executeTask(Runnable runnable) {
                runnable.run();
            }

            @Override
            public void executeTasks(Runnable... runnableEntries) {
                for (Runnable runnable : runnableEntries) {
                    runnable.run();
                }
            }

            @Override
            public void executeTaskAfterDelay(Runnable runnable, long delay) {
                try {
                    Thread.sleep(delay * 1000L);
                    runnable.run();
                } catch (InterruptedException exception) {
                    System.out.println("Thread execution was interrupted");
                }
            }

            // executes ones
            @Override
            public void executeTaskAtRate(Runnable runnable, long rate) {
                executeTask(runnable);
            }

            // executes ones
            @Override
            public void executeTaskAtDelay(Runnable runnable, long delay) {
                executeTaskAfterDelay(runnable, delay);
            }

        };
    }

}
