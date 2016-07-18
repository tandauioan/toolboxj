package org.ticdev.toolboxj.collections;

import org.ticdev.toolboxj.algorithms.sort.SortSupport;
import org.ticdev.toolboxj.numbers.BigCounter;
import org.ticdev.toolboxj.primitives.IntWrapper;
import org.ticdev.toolboxj.primitives.LongWrapper;
import org.ticdev.toolboxj.support.ExecutionSupport;

import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Various manual tests.
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public class SortSupportManual {

    /**
     * Generates a test string array.
     *
     * @param arraySize               the size of the array
     * @param keyCount                the number of distinct keys
     * @param keyMultiplicationFactor a multiplication factor for each key to make it longer
     * @return the test array
     */
    public static String[] generate_test_array(
            int arraySize, int keyCount, int keyMultiplicationFactor) {
        final String[] array = new String[arraySize];
        final String[] arraysort = new String[arraySize];
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < arraySize; i++) {
            String key = "" + arraySize % keyCount;
            stringBuilder.append(key);
            for (int mul = 0; mul < keyMultiplicationFactor - 1; mul++) {
                stringBuilder.append(key);
            }
            array[i] = stringBuilder.substring(0);
            stringBuilder.delete(0, stringBuilder.length());
        }
        return array;
    }

    /**
     * Sorts an array in place using {@link Arrays#sort(Object[])}
     *
     * @param array the array
     * @param times the number of times to repeat the sort - contains
     *              the time for each run upon return
     * @return the sorted array from the last repeat
     * @throws Exception if an exception occurs
     */
    public static String[] array_sort_in_place(
            String[] array, long[] times)
            throws
            Exception {
        final String[] sortarray = new String[array.length];
        final Runnable reset = () -> {
            for (int i = 0; i < array.length; i++) {
                sortarray[i] = array[i];
            }
        };
        final Callable<Void> execution = () -> {
            Arrays.sort(sortarray);
            return null;
        };
        ExecutionSupport.timeMultiple(reset, execution, times);
        return sortarray;
    }

    /**
     * Sorts array in place using {@link SortSupport#countSort(Object[])}
     *
     * @param array the array to sort
     * @param times the number of times to repeat the sort - contains the
     *              time for each run upon return
     * @return the sorted array from the last repeat
     * @throws Exception if an exception occurs
     */
    public static String[] count_sort_in_place(
            String[] array, long[] times)
            throws
            Exception {
        final String[] sortarray = new String[array.length];
        final Runnable reset = () -> {
            for (int i = 0; i < array.length; i++) {
                sortarray[i] = array[i];
            }
        };
        final Callable<Void> execution = () -> {
            SortSupport.countSort(sortarray);
            return null;
        };
        ExecutionSupport.timeMultiple(reset, execution, times);
        return sortarray;
    }

    /**
     * Sorts array in place using count sort and a parallel stream
     * over the array to sort.
     *
     * @param array the array to sort
     * @param times the number of times to repeat the sort - contains
     *              the time for each run upon return
     * @return the sorted array from the last repeat
     * @throws Exception if an exception occurs
     */
    public static String[] parcount_sort_in_place(
            String[] array, long[] times)
            throws
            Exception {
        final String[] sortarray = new String[array.length];
        final Runnable reset = () -> {
            for (int i = 0; i < array.length; i++) {
                sortarray[i] = array[i];
            }
        };
        final Callable<Void> execution = () -> {
            try (Stream<String> input = StreamSupport.stream(
                    Spliterators.spliterator(
                            sortarray,
                            Spliterator.CONCURRENT),
                    true);
                 Stream<String> sortedStream = BigCounterSet
                         .sortedStream(input)) {
                IntWrapper iw = IntWrapper.of(0);
                sortedStream.forEach(s -> {
                    int i = iw.intValue();
                    sortarray[i] = s;
                    iw.set(i + 1);
                });
            }
            return null;
        };
        ExecutionSupport.timeMultiple(reset, execution, times);
        return sortarray;
    }

    /**
     * Testing average over array sort, count sort, parallel count sort
     *
     * @throws Exception if an exception occurs
     */
    public static void test_average_comparison()
            throws
            Exception {
        String[] array = generate_test_array(1000000, 256, 1);
        long times[] = new long[1000];
        String[] arrres = array_sort_in_place(array, times);
        System.out
                .println("Array sort: " + ExecutionSupport.average(times));
        String[] countres = count_sort_in_place(array, times);
        System.out
                .println("Count sort: " + ExecutionSupport.average(times));
        String[] parcountres = parcount_sort_in_place(array, times);
        System.out
                .println("Par   sort: " + ExecutionSupport.average(times));
        System.out.println("Running comparison on results...");
        for (int i = 0; i < array.length; i++) {
            if (!arrres[i].equals(countres[i]) ||
                !arrres[i].equals(parcountres[i])) {
                System.out.println("Arrays not the same!");
                break;
            }
        }
        System.out.println("Done.");
    }


}
