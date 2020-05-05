package org.ticdev.toolboxj.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ticdev.toolboxj.algorithms.sort.SortSupport;

/**
 *
 * Test class for {@link SortSupport}
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public class SortSupportTest {

    /**
     * String comparator for sorting - ascending order
     */
    private final static Comparator<String> VALUES_COMPARATOR =
        Comparator.naturalOrder();

    /**
     * Unique strings supplier
     */
    private final static Supplier<String> VALUES_SUPPLIER = new Supplier<String>() {

        long currentValue = Long.MIN_VALUE;

        @Override
        public String get() {
            String result = currentValue + "";
            currentValue++;
            return result;
        }
    };

    /**
     * Generates an array of the given length filled with the same value
     *
     * @param length the length of the array
     * @return the array
     */
    private static String[] newArraySameValues(int length) {
        String[] result = new String[length];
        String value = VALUES_SUPPLIER.get();
        for (int i = 0; i < length; i++) {
            result[i] = value;
        }
        return result;
    }

    /**
     * Returns a new array of the given length that contains unique strings
     *
     * @param length the length of the array
     * @return the new array
     */
    private static String[] newArrayUniqueValues(int length) {
        String[] result = new String[length];
        for (int i = 0; i < result.length; i++) {
            result[i] = VALUES_SUPPLIER.get();
        }
        return result;
    }

    /**
     * Returns a new array of the given length filled with different strings and
     * a pseudo-random effort to introduce duplicates.
     *
     * @param length the length of the new array
     * @return the new array
     */
    private static String[] newArrayWithDuplicates(int length) {
        String[] result = new String[length];
        ArrayList<String> reuseSet = new ArrayList<>(length);
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < result.length; i++) {
            String value;
            if (reuseSet.size() > 0) {
                int choice = random.nextInt(reuseSet.size());
                value = (choice % 2 == 0) ? VALUES_SUPPLIER.get() : reuseSet.
                        remove(choice);
            } else {
                value = VALUES_SUPPLIER.get();
            }
            result[i] = value;
            reuseSet.add(value);
        }
        return result;
    }

    /**
     * Uses a pseudo random number generator to shuffle the content of the
     * array, in place.
     *
     * @param array the array to shuffle
     * @return the array, shuffled
     */
    private static String[] shuffle(String[] array) {
        Random random = new Random(System.currentTimeMillis());
        int sz = array.length;
        while (sz-- >= 0) {
            int to = random.nextInt(array.length);
            int from = random.nextInt(array.length);
            if (to != from) {
                String tmp = array[to];
                array[to] = array[from];
                array[from] = tmp;
            }
        }
        return array;
    }

    private final static List<String[]> ORIGINAL_SAME_VALUE = new LinkedList<>();

    private final static List<String[]> ORIGINAL_DUPLICATES = new LinkedList<>();

    private final static List<String[]> ORIGINAL_UNIQUES = new LinkedList<>();

    private final static int MAX_ITERATIONS_CAPACITY = 50;

    /**
     * Initialize original test arrays
     */
    @Before
    public void before() {

        for (int capacity = 1; capacity <= MAX_ITERATIONS_CAPACITY; capacity++) {
            ORIGINAL_SAME_VALUE.add(newArraySameValues(capacity));
            ORIGINAL_DUPLICATES.add(shuffle(newArrayWithDuplicates(capacity)));
            ORIGINAL_UNIQUES.add(shuffle(newArrayUniqueValues(capacity)));
        }
    }

    /**
     * Test
     * {@link SortSupport#heapSort(org.ticdev.toolboxj.collections.IntIndexedGetterSetter, java.util.Comparator)}
     */
    @Test
    public void test_heapSort_intIndexed() {

        /*
         * array has same value
         */
        for (String[] original : ORIGINAL_SAME_VALUE) {
            final String[] expected = Arrays.copyOf(original, original.length);
            final String[] actual = Arrays.copyOf(original, original.length);
            Arrays.sort(expected);
            SortSupport.heapSort(ArraySupport.indexedGetterSetterOf(actual),
                    VALUES_COMPARATOR);
            Assert.assertArrayEquals(expected, actual);
        }

        /*
         * array has some duplicates
         */
        for (String[] original : ORIGINAL_DUPLICATES) {
            final String[] expected = Arrays.copyOf(original, original.length);
            final String[] actual = Arrays.copyOf(original, original.length);
            Arrays.sort(expected);
            SortSupport.heapSort(ArraySupport.indexedGetterSetterOf(actual),
                    VALUES_COMPARATOR);
            Assert.assertArrayEquals(expected, actual);
        }

        /*
         * array has unique values
         */
        for (String[] original : ORIGINAL_UNIQUES) {
            final String[] expected = Arrays.copyOf(original, original.length);
            final String[] actual = Arrays.copyOf(original, original.length);
            Arrays.sort(expected);
            SortSupport.heapSort(ArraySupport.indexedGetterSetterOf(actual),
                    VALUES_COMPARATOR);
            Assert.assertArrayEquals(expected, actual);
        }
    }

    /**
     * Test
     * {@link SortSupport#heapSort(org.ticdev.toolboxj.collections.LongIndexedGetterSetter, java.util.Comparator)}
     */
    @Test
    public void test_heapSort_longIndexed() {

        /*
         * array has same value
         */
        for (String[] original : ORIGINAL_SAME_VALUE) {
            final String[] expected = Arrays.copyOf(original, original.length);
            final String[] actual = Arrays.copyOf(original, original.length);
            Arrays.sort(expected);
            SortSupport.heapSort(LongIndexedGetterSetter.wrap(ArraySupport.
                    indexedGetterSetterOf(actual)),
                    VALUES_COMPARATOR);
            Assert.assertArrayEquals(expected, actual);
        }

        /*
         * array has some duplicates
         */
        for (String[] original : ORIGINAL_DUPLICATES) {
            final String[] expected = Arrays.copyOf(original, original.length);
            final String[] actual = Arrays.copyOf(original, original.length);
            Arrays.sort(expected);
            SortSupport.heapSort(LongIndexedGetterSetter.wrap(ArraySupport.
                    indexedGetterSetterOf(actual)),
                    VALUES_COMPARATOR);
            Assert.assertArrayEquals(expected, actual);
        }

        /*
         * array has unique values
         */
        for (String[] original : ORIGINAL_UNIQUES) {
            final String[] expected = Arrays.copyOf(original, original.length);
            final String[] actual = Arrays.copyOf(original, original.length);
            Arrays.sort(expected);
            SortSupport.heapSort(LongIndexedGetterSetter.wrap(ArraySupport.
                    indexedGetterSetterOf(actual)),
                    VALUES_COMPARATOR);
            Assert.assertArrayEquals(expected, actual);
        }
    }

    /**
     * Test {@link SortSupport##heapSort(java.util.List, java.util.Comparator)}
     */
    @Test
    public void test_heapSort_list() {

        /*
         * array has same value
         */
        ORIGINAL_SAME_VALUE
                .forEach((original) -> {
                    final String[] expected = Arrays.copyOf(original,
                            original.length);
                    String[] actual = Arrays.copyOf(original, original.length);
                    Arrays.sort(expected);
                    List<String> list = Arrays.asList(actual);
                    SortSupport.heapSort(list, VALUES_COMPARATOR);
                    actual = list.toArray(actual);
                    Assert.assertArrayEquals(expected, actual);
                });

        /*
         * array has some duplicates
         */
        ORIGINAL_DUPLICATES
                .forEach((original) -> {
                    final String[] expected = Arrays.copyOf(original,
                            original.length);
                    String[] actual = Arrays.copyOf(original, original.length);
                    Arrays.sort(expected);
                    List<String> list = Arrays.asList(actual);
                    SortSupport.heapSort(list, VALUES_COMPARATOR);
                    actual = list.toArray(actual);
                    Assert.assertArrayEquals(expected, actual);
                });

        /*
         * array has unique values
         */
        ORIGINAL_UNIQUES
                .forEach((original) -> {
                    final String[] expected = Arrays.copyOf(original,
                            original.length);
                    String[] actual = Arrays.copyOf(original, original.length);
                    Arrays.sort(expected);
                    List<String> list = Arrays.asList(actual);
                    SortSupport.heapSort(list, VALUES_COMPARATOR);
                    actual = list.toArray(actual);
                    Assert.assertArrayEquals(expected, actual);
                });
    }

}
