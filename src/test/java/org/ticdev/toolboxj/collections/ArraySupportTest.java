package org.ticdev.toolboxj.collections;

import java.util.Arrays;
import java.util.function.Supplier;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link ArraySupport}
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public class ArraySupportTest {

    /**
     * Supplier of unique strings
     */
    public static final Supplier<String> VALUES_SUPPLIER = new Supplier<String>() {

        /*
         * initial value
         */
        long current = Long.MIN_VALUE;

        @Override
        public String get() {
            String result = current + "";
            current++;
            return result;

        }
    };

    /**
     * Test for {@link ArraySupport#reverseCopy(T[])}
     */
    @Test
    public void test_reverseCopy() {
        int MAX_ITERATIONS = 23;
        for (int capacity = 0; capacity < MAX_ITERATIONS; capacity++) {

            final String[] toReverse = new String[capacity];
            Assert.assertEquals(capacity, toReverse.length);

            for (int i = 0; i < toReverse.length; i++) {
                toReverse[i] = VALUES_SUPPLIER.get();
            }

            final String[] actual = ArraySupport.reverseCopy(toReverse);

            Assert.assertEquals(toReverse.length, actual.length);
            int index = actual.length - 1;
            for (String expectedString : toReverse) {
                Assert.assertEquals(expectedString, actual[index--]);
            }

        }
    }

    /**
     * Test {@link ArraySupport#reverse(T[])}
     */
    @Test
    public void test_reverse() {
        int MAX_ITERATIONS = 23;
        for (int capacity = 0; capacity < MAX_ITERATIONS; capacity++) {

            final String[] original = new String[capacity];
            Assert.assertEquals(capacity, original.length);

            for (int i = 0; i < original.length; i++) {
                original[i] = VALUES_SUPPLIER.get();
            }

            final String[] actual = Arrays.copyOf(original, original.length);
            Assert.assertEquals(capacity, actual.length);

            int index = 0;
            for (String originalString : original) {
                Assert.assertEquals(originalString, actual[index++]);
            }

            ArraySupport.reverse(actual);

            index = actual.length - 1;
            for (String expectedString : original) {
                Assert.assertEquals(expectedString, actual[index--]);
            }

        }
    }

    /**
     * Test {@link ArraySupport#indexedGetterSetterOf(T[], int, int)}
     */
    @Test
    public void test_indexedGetterSetterOf_fail_on_null() {

        try {
            Assert.assertNull(ArraySupport.indexedGetterSetterOf(null, 0, 1));
        } catch (NullPointerException ex) {
            Assert.assertNotNull(ex);
        } catch (IllegalArgumentException ex) {
            Assert.fail(ex.toString());
        }

        try {
            Assert.assertNull(ArraySupport.indexedGetterSetterOf(null));
        } catch (NullPointerException ex) {
            Assert.assertNotNull(ex);
        } catch (IllegalArgumentException ex) {
            Assert.fail(ex.toString());
        }

    }

    /**
     * Test {@link ArraySupport#indexedGetterSetterOf(T[], int, int)} with
     * illegal indexes.
     */
    @Test
    public void test_indexedGetterSetterOf_fail_on_bad_indexes() {

        try {
            Assert.assertNull(ArraySupport.
                    indexedGetterSetterOf(new String[10], -1, 1));
        } catch (NullPointerException ex) {
            Assert.fail(ex.toString());
        } catch (IllegalArgumentException ex) {
            Assert.assertNotNull(ex);
        }

        try {
            Assert.assertNull(ArraySupport.
                    indexedGetterSetterOf(new String[10], 10, 1));
        } catch (NullPointerException ex) {
            Assert.fail(ex.toString());
        } catch (IllegalArgumentException ex) {
            Assert.assertNotNull(ex);
        }

        try {
            Assert.assertNull(ArraySupport.indexedGetterSetterOf(new String[0],
                    0, 0));
        } catch (NullPointerException ex) {
            Assert.fail(ex.toString());
        } catch (IllegalArgumentException ex) {
            Assert.assertNotNull(ex);
        }

        try {
            Assert.assertNull(ArraySupport.
                    indexedGetterSetterOf(new String[10],
                            0, -1));
        } catch (NullPointerException ex) {
            Assert.fail(ex.toString());
        } catch (IllegalArgumentException ex) {
            Assert.assertNotNull(ex);
        }

        try {
            Assert.assertNull(ArraySupport.
                    indexedGetterSetterOf(new String[10],
                            2, 1));
        } catch (NullPointerException ex) {
            Assert.fail(ex.toString());
        } catch (IllegalArgumentException ex) {
            Assert.assertNotNull(ex);
        }

        try {
            Assert.assertNull(ArraySupport.
                    indexedGetterSetterOf(new String[10],
                            0, 10));
        } catch (NullPointerException ex) {
            Assert.fail(ex.toString());
        } catch (IllegalArgumentException ex) {
            Assert.assertNotNull(ex);
        }

        try {
            Assert.assertNull(ArraySupport.
                    indexedGetterSetterOf(new String[0]));
        } catch (NullPointerException ex) {
            Assert.fail(ex.toString());
        } catch (IllegalArgumentException ex) {
            Assert.assertNotNull(ex);
        }

    }

    /**
     * Testing {@link ArraySupport#indexedGetterSetterOf(T[])}
     */
    @Test
    public void test_indexedGetterSetterOf() {

        int MAX_ITERATIONS = 23;

        for (int capacity = 1; capacity < MAX_ITERATIONS; capacity++) {
            String[] original = new String[capacity];
            Assert.assertEquals(capacity, original.length);
            for (int i = 0; i < capacity; i++) {
                original[i] = VALUES_SUPPLIER.get();
            }

            IntIndexedGetterSetter<String> gs = ArraySupport.
                    indexedGetterSetterOf(original);
            Assert.assertEquals(capacity, gs.size());

            for (int i = 0; i < original.length; i++) {
                Assert.assertEquals(original[i], gs.get(i));
            }

            /*
             * reverse
             */
            String[] reverseCopy = ArraySupport.reverseCopy(original);
            for (int i = 0; i < reverseCopy.length; i++) {
                gs.set(i, reverseCopy[i]);
            }

            for (int i = 0; i < reverseCopy.length; i++) {
                Assert.assertEquals(reverseCopy[i], gs.get(i));
            }

        }

    }

    /**
     * Test {@link ArraySupport#indexedGetterSetterOf(T[], int, int)}
     */
    @Test
    public void test_indexedGetterSetterOf_ranged() {
        int MAX_ITERATIONS = 23;
        for (int capacity = 1; capacity < MAX_ITERATIONS; capacity++) {
            for (int left = 0; left < capacity; left++) {
                for (int right = left; right < capacity; right++) {
                    String[] original = new String[capacity];
                    for (int i = 0; i < original.length; i++) {
                        original[i] = VALUES_SUPPLIER.get();
                    }
                    IntIndexedGetterSetter<String> gs = ArraySupport.
                            indexedGetterSetterOf(original, left, right);
                    for (int i = left; i < right; i++) {
                        Assert.assertEquals(original[i], gs.get(i - left));
                    }
                    /*
                     * TODO make changes via gs and make sure they're reflected
                     * in array
                     */
                    for (int i = left; i < right; i++) {
                        String value = VALUES_SUPPLIER.get();
                        gs.set(i - left, value);
                        Assert.assertEquals(value, original[i]);
                    }
                }
            }
        }
    }

}
