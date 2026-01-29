package me.mt.threads.factory;

import me.mt.threads.AsyncServiceImpl;
import me.mt.threads.api.AsyncService;
import me.mt.threads.factory.api.AsyncServiceFactory;

public class AsyncServiceFactoryImpl implements AsyncServiceFactory {

    /*
     * This is a utility interface so it can be wrapped in a singleton
     * if is not explicitly required to have multiple instances.
     */

    private static AsyncServiceFactoryImpl instance;

    public static AsyncServiceFactoryImpl getInstance() {
        if (instance == null) instance = new AsyncServiceFactoryImpl();
        return instance;
    }

    private AsyncServiceFactoryImpl() {}

    @Override
    public AsyncService createAsyncService() {
        return new AsyncServiceImpl();
    }

}
