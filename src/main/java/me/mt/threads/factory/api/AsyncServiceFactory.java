package me.mt.threads.factory.api;

import me.mt.threads.api.AsyncService;
import me.mt.threads.factory.AsyncServiceFactoryImpl;
import me.mt.threads.factory.BlockingAsyncServiceFactoryImpl;

public interface AsyncServiceFactory {

    static AsyncServiceFactory getAsyncServiceFactory() {
        return AsyncServiceFactoryImpl.getInstance();
    }

    static AsyncServiceFactory getBlockingAsyncServiceFactory() {
        return BlockingAsyncServiceFactoryImpl.getInstance();
    }

    AsyncService createAsyncService();

}
