package me.mt.threads.factory;

import me.mt.threads.SorterServiceImpl;
import me.mt.threads.api.SorterService;
import me.mt.threads.factory.api.SorterServiceFactory;

public class SorterServiceFactoryImpl implements SorterServiceFactory {

    private static SorterServiceFactoryImpl instance;

    public static SorterServiceFactoryImpl getInstance() {
        if (instance == null) instance = new SorterServiceFactoryImpl();
        return instance;
    }

    @Override
    public SorterService createSorterService() {
        return new SorterServiceImpl();
    }

}
