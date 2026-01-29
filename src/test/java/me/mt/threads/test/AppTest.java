package me.mt.threads.test;

import me.mt.threads.App;
import me.mt.threads.api.AsyncService;
import me.mt.threads.api.SorterService;
import me.mt.threads.factory.api.AsyncServiceFactory;
import me.mt.threads.factory.api.SorterServiceFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.awaitility.Awaitility.*;
import static org.hamcrest.Matchers.*;

public class AppTest {

    private static App app;
    private static AsyncService asyncService;
    private static SorterService sorterService;

    @BeforeAll
    static void setUp() {
        AsyncServiceFactory asyncServiceFactory = AsyncServiceFactory.getAsyncServiceFactory();
        SorterServiceFactory sorterServiceFactory = SorterServiceFactory.getSorterServiceFactory();
        asyncService = asyncServiceFactory.createAsyncService();
        sorterService = sorterServiceFactory.createSorterService();
        app = new App();
    }

    // the same for volatile variable as increment is not an atomic operation
    @Disabled("Unpredicted behaviour")
    @Test
    void checkSimultaneousThreadsDoesNotUpdateCorrectly() {
        // Executing async task
        app.incrementTwoTimesUnsafe(2);

        // It is not set correctly
        await()
                .atMost(2, TimeUnit.SECONDS)
                .until(app::getValue, not(22));
    }

    @Test
    void checkSimultaneousThreadsUpdateAtomicValue() {
        // Executing async task
        app.incrementTwoTimes(2);

        // It is set correctly
        await()
                .atMost(2, TimeUnit.SECONDS)
                .until(app::getAtomicValue, equalTo(22));
    }

    @ParameterizedTest
    @ValueSource(longs = { 0, 2, 4 })
    void checkTaskIsExecutedAfterTheDelay(long delay) {
        AtomicBoolean checked = new AtomicBoolean(false);
        Runnable runnable = () -> checked.set(true);
        asyncService.executeTaskAfterDelay(runnable, delay);
        await()
                .atMost(5, TimeUnit.SECONDS)
                .until(checked::get, is(true));
    }

    @ParameterizedTest
    @ValueSource(longs = { 4, 5, 8 })
    void checkTaskIsNotExecutedBeforeTheDelay(long delay) {
        AtomicBoolean checked = new AtomicBoolean(false);
        Runnable runnable = () -> checked.set(true);
        asyncService.executeTaskAfterDelay(runnable, delay);
        await()
                .atMost(3, TimeUnit.SECONDS)
                .until(checked::get, not(true));
    }

    @Test
    void checkSortsArray() {
        int[] array = {
                42, 7, 19, 3, 88, 25, 14,
                60, 1, 33, 72, 9, 55, 21,
                6, 47, 30, 11, 90, 18
        };
        int[] sorted = {
                1, 3, 6, 7, 9, 11, 14, 18,
                19, 21, 25, 30, 33, 42, 47,
                55, 60, 72, 88, 90
        };
        sorterService.quickSort(array);
        Assertions.assertArrayEquals(sorted, array, "Arrays are not equal");
    }

}
