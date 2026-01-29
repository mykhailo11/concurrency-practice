package me.mt.threads.factory.api;

import me.mt.threads.api.SorterService;
import me.mt.threads.factory.SorterServiceFactoryImpl;

public interface SorterServiceFactory {

    static SorterServiceFactory getSorterServiceFactory() {
        return SorterServiceFactoryImpl.getInstance();
    }

    SorterService createSorterService();

}
