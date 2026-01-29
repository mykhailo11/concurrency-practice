package me.mt.threads;

import me.mt.threads.api.SorterService;

import java.util.concurrent.*;

public class SorterServiceImpl implements SorterService {

    // it is better to implement another algorithm for array with a small length
    private static class QuickSortTask extends RecursiveAction {

        private final int[] array;
        private final int start;
        private final int end;

        public QuickSortTask(int[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            if (start < end) {
                int pivotIndex = partition(array, start, end);
                invokeAll(
                        new QuickSortTask(array, start, pivotIndex - 1),
                        new QuickSortTask(array, pivotIndex + 1, end)
                );
            }
        }

        private int partition(int[] array, int start, int end) {
            // default pivot is end of the array
            int pivot = array[end];
            int checked = start;
            for (int index = start; index <= end; index++)
                if (array[index] <= pivot) swap(array, index, checked++);
            return checked - 1;
        }

    }

    private static void swap(int[] array, int first, int second) {
        int temp = array[first];
        array[first] = array[second];
        array[second] = temp;
    }

    @Override
    public void quickSort(int[] array) {
        try (ForkJoinPool pool = ForkJoinPool.commonPool()) {
            pool.invoke(new QuickSortTask(array, 0, array.length - 1));
        }
    }

    @Override
    public void mergeSort(int[] array) {

    }

}
