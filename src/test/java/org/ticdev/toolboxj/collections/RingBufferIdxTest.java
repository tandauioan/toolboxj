package org.ticdev.toolboxj.collections;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;
import org.junit.Assert;
import org.junit.Test;
import org.ticdev.toolboxj.allocation.AllocatorWithParameter;
import org.ticdev.toolboxj.primitives.IntWrapper;
import org.ticdev.toolboxj.tuples.Pair;
import org.ticdev.toolboxj.tuples.Tuples;

/**
 * Test class for {@link RingBufferIdx}.
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public class RingBufferIdxTest {

    /**
     * Unique string value supplier, with any call to {@link Supplier#get()},
     * starting from 0.
     */
    static final Supplier<String> VALUE_SUPPLIER = new Supplier<String>() {

        long currentValue = 0;

        @Override
        public String get() {
            String result = currentValue + "";
            currentValue++;
            return result;
        }
    };

    /**
     * Test constructor with the minimum acceptable capacity
     */
    @Test
    public void test_Constructor_Boundary_Min() {
        try {
            Assert.assertTrue(
                    new RingBufferIdx(
                            RingBufferIdx.MIN_CAPACITY,
                            d -> {
                            },
                            (to, from) -> {
                            }).isEmpty());
        } catch (IllegalArgumentException | NullPointerException ex) {
            Assert.fail(ex.toString());
        }
    }

    /**
     * Test constructor with the maximum acceptable capacity
     */
    @Test
    public void test_Constructor_Boundary_Max() {
        try {
            Assert.assertTrue(
                    new RingBufferIdx(
                            RingBufferIdx.MAX_CAPACITY,
                            d -> {
                            },
                            (to, from) -> {
                            }).isEmpty());
        } catch (IllegalArgumentException | NullPointerException ex) {
            Assert.fail(ex.toString());
        }
    }

    /**
     * Test constructor fail with capacity one less than min
     */
    @Test
    public void test_Constructor_Fail_BoundayUnderflow() {
        if (RingBufferIdx.MIN_CAPACITY > Integer.MIN_VALUE) {

            try {
                Assert.assertTrue(Objects.isNull(new RingBufferIdx(
                        RingBufferIdx.MIN_CAPACITY - 1,
                        d -> {
                        },
                        (to, from) -> {
                        }
                )));
            } catch (IllegalArgumentException ex) {
                Assert.assertNotNull(ex);
            } catch (NullPointerException ex) {
                Assert.fail(ex.toString());
            }

        }
    }

    /**
     * Test constructor fail with capacity one more than max
     */
    @Test
    public void test_Constructor_Fail_BoundaryOverflow() {
        if (RingBufferIdx.MAX_CAPACITY < Integer.MAX_VALUE) {

            try {

                Assert.assertTrue(Objects.isNull(new RingBufferIdx(
                        RingBufferIdx.MAX_CAPACITY + 1,
                        d -> {
                        },
                        (to, from) -> {
                        }
                )));

            } catch (IllegalArgumentException ex) {
                Assert.assertNotNull(ex);
            } catch (NullPointerException ex) {
                Assert.fail(ex.toString());
            }

        }
    }

    /**
     * Test construction failure with null operators
     */
    @Test
    public void test_Constructor_Fail_NullOperations() {

        try {
            Assert.assertTrue(
                    Objects.isNull(
                            new RingBufferIdx(
                                    RingBufferIdx.MIN_CAPACITY,
                                    d -> {
                                    },
                                    null)));
        } catch (NullPointerException ex) {
            Assert.assertNotNull(ex);
        } catch (IllegalArgumentException ex) {
            Assert.fail(ex.toString());
        }

        try {
            Assert.assertTrue(
                    Objects.isNull(
                            new RingBufferIdx(
                                    RingBufferIdx.MIN_CAPACITY,
                                    null,
                                    (to, from) -> {
                                    })));
        } catch (NullPointerException ex) {
            Assert.assertNotNull(ex);
        } catch (IllegalArgumentException ex) {
            Assert.fail(ex.toString());
        }

        try {
            Assert.assertTrue(
                    Objects.isNull(
                            new RingBufferIdx(
                                    RingBufferIdx.MIN_CAPACITY,
                                    null,
                                    null)));
        } catch (NullPointerException ex) {
            Assert.assertNotNull(ex);
        } catch (IllegalArgumentException ex) {
            Assert.fail(ex.toString());
        }

    }

    /**
     * Test capacity
     */
    @Test
    public void test_Capacity() {

        final AllocatorWithParameter<RingBufferIdx, Integer> allocator
                = (capacity) -> new RingBufferIdx(
                        capacity,
                        d -> {
                        },
                        (to, from) -> {
                        });

        int rangeMin = Math.max(1, RingBufferIdx.MIN_CAPACITY);
        int rangeMax = Math.min(
                RingBufferIdx.MIN_CAPACITY + 10,
                RingBufferIdx.MAX_CAPACITY);

        for (int capacity = rangeMin; capacity < rangeMax; capacity++) {
            RingBufferIdx rbi = allocator.allocate(capacity);
            Assert.assertTrue(rbi.isEmpty());
            Assert.assertEquals(capacity, rbi.capacity());
        }

    }

    /**
     * Test {@link RingBufferIdx#acquireAppend()}
     */
    @Test
    public void test_acquireAppend() {

        final LinkedList<Integer> deletions = new LinkedList<>();

        final LinkedList<Pair<Integer, Integer>> copies = new LinkedList<>();

        final AllocatorWithParameter<RingBufferIdx, Integer> allocator
                = (capacity)
                -> new RingBufferIdx(
                        capacity,
                        d -> deletions.add(d),
                        (to, from) -> copies.add(
                                Tuples.of(to, from)));

        int rangeMin = Math.max(1, RingBufferIdx.MIN_CAPACITY);
        int rangeMax = Math.min(RingBufferIdx.MIN_CAPACITY + 10,
                RingBufferIdx.MAX_CAPACITY);

        for (int capacity = rangeMin; capacity < rangeMax; capacity++) {
            RingBufferIdx rbi = allocator.allocate(capacity);
            Assert.assertTrue(rbi.isEmpty());
            /* fill it */
            final LinkedList<Integer> acquiredList = new LinkedList<>();
            for (int i = 0; i < capacity; i++) {
                acquiredList.add(rbi.acquireAppend());
            }
            Assert.assertEquals(capacity, rbi.size());
            Assert.assertTrue(rbi.isFull());
            Assert.assertTrue(deletions.isEmpty());
            /* add the same number of elements and check that 
             * deletions match all acquired positions */
            for (int i = 0; i < capacity; i++) {
                rbi.acquireAppend();
            }
            acquiredList.forEach(expected -> {
                Assert.assertFalse(deletions.isEmpty());
                Assert.assertEquals(expected, deletions.removeFirst());
            });
            Assert.assertTrue(deletions.isEmpty());
        }

    }

    /**
     * Test {@link RingBufferIdx#acquireIndex(int)} with capacity 1.
     */
    @Test
    public void test_acquireIndex_OneElement() {

        final LinkedList<String> deletions = new LinkedList<>();

        String[] elements = new String[RingBufferIdx.
                allocationSizeForCapacity(1)];
        RingBufferIdx rbi = new RingBufferIdx(1, d -> {
            deletions.add(elements[d]);
            elements[d] = null;
        },
                (to, from) -> elements[to] = elements[from]
        );

        Assert.assertTrue(rbi.isEmpty());

        final String ZERO = "Zero";

        final String ONE = "One";

        final String TWO = "Two";

        elements[rbi.acquireIndex(0)] = ZERO;
        Assert.assertTrue(rbi.isFull());
        Assert.assertEquals(ZERO, elements[rbi.forwardIterator().next()]);

        elements[rbi.acquireIndex(0)] = ONE;
        Assert.assertTrue(rbi.isFull());
        Assert.assertEquals(ONE, elements[rbi.forwardIterator().next()]);
        Assert.assertEquals(1, deletions.size());
        Assert.assertEquals(ZERO, deletions.removeFirst());

        elements[rbi.acquireIndex(1)] = "Two";
        Assert.assertTrue(rbi.isFull());
        Assert.assertEquals(TWO, elements[rbi.forwardIterator().next()]);
        Assert.assertEquals(1, deletions.size());
        Assert.assertEquals(ONE, deletions.removeFirst());

    }

    /**
     * Test {@link RingBufferIdx#acquireIndex(int)}
     */
    @Test
    public void test_acquireIndex() {

        int capacity = 13;

        final String[] elements = new String[RingBufferIdx.
                allocationSizeForCapacity(capacity)];

        RingBufferIdx rbi = new RingBufferIdx(capacity, d -> elements[d] = null,
                (to, from) -> elements[to] = elements[from]);

        final String[] expected = new String[capacity];

        for (int i = 0; i < expected.length; i++) {
            expected[i] = VALUE_SUPPLIER.get();
        }

        /* fill rbi by inserting at 0 with expected - ends up with reversed content */
        Assert.assertTrue(rbi.isEmpty());
        for (String s : expected) {
            Assert.assertFalse(rbi.isFull());
            elements[rbi.acquireIndex(0)] = s;
        }
        Assert.assertTrue(rbi.isFull());

        IntIterator rbIterator = rbi.forwardIterator();
        for (String s : ArraysSupport.reverseCopy(expected)) {
            Assert.assertTrue(rbIterator.hasNext());
            String actual = elements[rbIterator.next()];
            Assert.assertEquals(s, actual);
        }
        Assert.assertFalse(rbIterator.hasNext());

        /* inserting at with index==size appends */
        for (int i = 0; i < expected.length; i++) {
            expected[i] = VALUE_SUPPLIER.get();
            elements[rbi.acquireIndex(rbi.size())] = expected[i];
        }
        rbIterator = rbi.forwardIterator();
        for (String s : expected) {
            Assert.assertTrue(rbIterator.hasNext());
            String actual = elements[rbIterator.next()];
            Assert.assertEquals(s, actual);
        }
        Assert.assertFalse(rbIterator.hasNext());

        /* inserting at any position in full buffer will remove head 
        so the position will be shifted by one toward the head */
        for (int i = 0; i < capacity; i++) {
            int expectedPos = i - 1;
            if (expectedPos < 0) {
                expectedPos = 0;
            }
            String expectedValue = VALUE_SUPPLIER.get();
            elements[rbi.acquireIndex(i)] = expectedValue;
            Assert.assertEquals(expectedValue, elements[rbi.
                    mapIndex(expectedPos)]);
        }

        /* inserting at any position in the non-full buffer should
        preserve the position
         */
        rbi = new RingBufferIdx(capacity, d -> elements[d] = null,
                (to, from) -> elements[to] = elements[from]);
        elements[rbi.acquireAppend()] = "HEAD";
        elements[rbi.acquireAppend()] = "TAIL";
        /* 0 and 1 have values */
 /* pos moves modulo 5 to retry indexes */
        int pos = 0;
        for (int i = 1; i < capacity - 2; i++) {
            pos = pos % 5;
            Assert.assertFalse(rbi.isFull());
            String expectedValue = VALUE_SUPPLIER.get();
            elements[rbi.acquireIndex(pos)] = expectedValue;
            Assert.assertEquals(expectedValue, elements[rbi.mapIndex(pos)]);
        }

    }

    /**
     * Test correct mapping of external indexes
     */
    @Test
    public void test_mapIndex() {
        /* out of bounds for an empty ring buffer - capacity 1 */
        RingBufferIdx rbi = new RingBufferIdx(1, d -> {
        }, (to, from) -> {
        });
        int[] testIndexes = {0, rbi.size(), rbi.capacity()};
        for (int i : testIndexes) {
            try {
                testIndexes[i] = rbi.mapIndex(testIndexes[i]);
            } catch (IndexOutOfBoundsException ex) {
                Assert.assertNotNull(ex);
            }
        }
        rbi.acquireAppend();
        Assert.assertEquals(1, rbi.size());
        try {
            Assert.assertEquals(rbi.head(), rbi.mapIndex(0));
        } catch (IndexOutOfBoundsException ex) {
            Assert.fail(ex.toString());
        }
        try {
            Assert.assertEquals(rbi.head(), rbi.mapIndex(rbi.size()));
            Assert.fail();
        } catch (IndexOutOfBoundsException ex) {
            Assert.assertNotNull(ex.toString());
        }

        int capacity = Math.min(RingBufferIdx.MAX_CAPACITY, 25);
        final RingBufferIdx rbi1 = new RingBufferIdx(capacity, d -> {
        }, (to, from) -> {
        });
        for (int i = 0; i < 31; i++) {
            rbi1.acquireAppend();
            IntWrapper currentPosition = IntWrapper.of(0);
            rbi1.forwardIterator().forEachRemaining(pos -> {
                int cp = currentPosition.intValue();
                Assert.assertEquals(pos, rbi1.mapIndex(cp));
                currentPosition.set(cp + 1);
            });
        }

    }

    /**
     * Test {@link RingBufferIdx#acquirePrepend()}
     */
    @Test
    public void test_acquirePrepend() {

        final LinkedList<Integer> deletions = new LinkedList<>();

        final LinkedList<Pair<Integer, Integer>> copies = new LinkedList<>();

        final AllocatorWithParameter<RingBufferIdx, Integer> allocator
                = (capacity)
                -> new RingBufferIdx(
                        capacity,
                        d -> deletions.add(d),
                        (to, from) -> copies.add(
                                Tuples.of(to, from)));

        int rangeMin = Math.max(1, RingBufferIdx.MIN_CAPACITY);
        int rangeMax = Math.min(RingBufferIdx.MIN_CAPACITY + 10,
                RingBufferIdx.MAX_CAPACITY);

        for (int capacity = rangeMin; capacity < rangeMax; capacity++) {
            RingBufferIdx rbi = allocator.allocate(capacity);
            Assert.assertTrue(rbi.isEmpty());
            /* fill it */
            final LinkedList<Integer> acquiredList = new LinkedList<>();
            for (int i = 0; i < capacity; i++) {
                acquiredList.add(rbi.acquirePrepend());
            }
            Assert.assertEquals(capacity, rbi.size());
            Assert.assertTrue(rbi.isFull());
            Assert.assertTrue(deletions.isEmpty());
            /* add the same number of elements and check that deletions match 
             * all acquired positions */
            for (int i = 0; i < capacity; i++) {
                rbi.acquirePrepend();
            }
            acquiredList.forEach(expected -> {
                Assert.assertFalse(deletions.isEmpty());
                Assert.assertEquals(expected, deletions.removeFirst());
            });
            Assert.assertTrue(deletions.isEmpty());
        }

    }

    /**
     * Ensure allocation size of instance matches the static allocation size
     * method
     */
    @Test
    public void test_validate_allocationSize() {
        int rangeMin = Math.max(1, RingBufferIdx.MIN_CAPACITY);
        int rangeMax = Math.min(RingBufferIdx.MIN_CAPACITY + 10,
                RingBufferIdx.MAX_CAPACITY);
        for (int capacity = rangeMin; capacity < rangeMax; capacity++) {
            Assert.assertEquals(new RingBufferIdx(capacity, d -> {
            }, (to, from) -> {
            }).allocationSize(), RingBufferIdx.allocationSizeForCapacity(
                    capacity));
        }
        rangeMin = Math.max(RingBufferIdx.MIN_CAPACITY,
                RingBufferIdx.MAX_CAPACITY - 10);
        rangeMax = RingBufferIdx.MAX_CAPACITY;
        for (int capacity = rangeMin; capacity < rangeMax; capacity++) {
            Assert.assertEquals(new RingBufferIdx(capacity, d -> {
            }, (to, from) -> {
            }).allocationSize(), RingBufferIdx.allocationSizeForCapacity(
                    capacity));
        }
    }

    /**
     * Test forward iterator
     */
    @Test
    public void test_forwardIterator() {
        int capacity = Math.min(RingBufferIdx.MAX_CAPACITY, 25);
        final LinkedList<Integer> acquiredList = new LinkedList<>();
        RingBufferIdx rbi = new RingBufferIdx(capacity, d -> {
        }, (to, from) -> {
        });

        /* simple iteration */
        while (!rbi.isFull()) {
            acquiredList.add(rbi.acquireAppend());
        }
        IntIterator rbIterator = rbi.forwardIterator();
        while (rbIterator.hasNext()) {
            Assert.assertTrue(!acquiredList.isEmpty());
            Assert.assertEquals(rbIterator.next(), acquiredList.removeFirst().
                    intValue());
        }
        Assert.assertTrue(acquiredList.isEmpty());

        /* iterate and delete each element */
        Set<Integer> deletePositions = new HashSet<>();
        rbi = new RingBufferIdx(capacity, deletePositions::add, (to, from) -> {
        });
        while (!rbi.isFull()) {
            rbi.acquireAppend();
        }
        rbIterator = rbi.forwardIterator();
        final int head = rbi.head();
        while (rbIterator.hasNext()) {
            /* always returning the same position as it gets deleted and compacted */
            Assert.assertEquals(head, rbIterator.next());
            rbIterator.remove();
        }
        Assert.assertTrue(rbi.isEmpty());
        /* all positions have delete call on them either because 
         * they're the position that needs to be deleted, always the head, 
         * or because of the compacting operation */
        Assert.assertEquals(capacity, deletePositions.size());

        /* iterate and remove every even position */
        rbi = new RingBufferIdx(capacity, d -> {
        }, (to, from) -> {
        });
        while (!rbi.isFull()) {
            rbi.acquireAppend();
        }
        rbIterator = rbi.forwardIterator();
        int deleted = 0;
        boolean deleteCondition = false;
        while (rbIterator.hasNext()) {
            rbIterator.next();
            if (deleteCondition) {
                deleted++;
                rbIterator.remove();
                deleteCondition = false;
            } else {
                deleteCondition = true;
            }
        }
        Assert.assertEquals(deleted, rbi.capacity() - rbi.size());

        /* check iterator vs. forEachRemaining */
        rbi = new RingBufferIdx(capacity, d -> {
        }, (to, from) -> {
        });
        while (!rbi.isFull()) {
            rbi.acquireAppend();
        }
        final IntIterator rbIterator1 = rbi.forwardIterator();
        rbi.forwardIterator().forEachRemaining(e -> {
            Assert.assertTrue(rbIterator1.hasNext());
            Assert.assertEquals(e, rbIterator1.next());
        });

    }

    /**
     * Test backward iterator
     */
    @Test
    public void test_backwardIterator() {
        int capacity = Math.min(RingBufferIdx.MAX_CAPACITY, 25);
        final LinkedList<Integer> acquiredList = new LinkedList<>();
        RingBufferIdx rbi = new RingBufferIdx(capacity, d -> {
        }, (to, from) -> {
        });

        /* simple iterator */
        while (!rbi.isFull()) {
            acquiredList.add(rbi.acquireAppend());
        }
        IntIterator rbIterator = rbi.backwardIterator();
        while (rbIterator.hasNext()) {
            Assert.assertTrue(!acquiredList.isEmpty());
            Assert.assertEquals(rbIterator.next(), acquiredList.removeLast().
                    intValue());
        }
        Assert.assertTrue(acquiredList.isEmpty());

        /* iterate and delete each element */
        Set<Integer> deletePositions = new HashSet<>();
        rbi = new RingBufferIdx(capacity, deletePositions::add, (to, from) -> {
        });
        while (!rbi.isFull()) {
            rbi.acquireAppend();
        }
        rbIterator = rbi.backwardIterator();
        final int tail = rbi.tail();
        while (rbIterator.hasNext()) {
            /* always returning the same position as it gets deleted and compacted */
            Assert.assertEquals(tail, rbIterator.next());
            rbIterator.remove();
        }
        Assert.assertTrue(rbi.isEmpty());
        /* all positions have delete call on them either because 
         * they're the position that needs to be deleted, always the head, 
         * or because of the compacting operation */
        Assert.assertEquals(capacity, deletePositions.size());

        /* iterate and remove every even position */
        rbi = new RingBufferIdx(capacity, d -> {
        }, (to, from) -> {
        });
        while (!rbi.isFull()) {
            rbi.acquireAppend();
        }
        rbIterator = rbi.backwardIterator();
        int deleted = 0;
        boolean deleteCondition = false;
        while (rbIterator.hasNext()) {
            rbIterator.next();
            if (deleteCondition) {
                deleted++;
                rbIterator.remove();
                deleteCondition = false;
            } else {
                deleteCondition = true;
            }
        }
        Assert.assertEquals(deleted, rbi.capacity() - rbi.size());

        /* check iterator vs. forEachRemaining */
        rbi = new RingBufferIdx(capacity, d -> {
        }, (to, from) -> {
        });
        while (!rbi.isFull()) {
            rbi.acquireAppend();
        }
        final IntIterator rbIterator1 = rbi.backwardIterator();
        rbi.backwardIterator().forEachRemaining(e -> {
            Assert.assertTrue(rbIterator1.hasNext());
            Assert.assertEquals(e, rbIterator1.next());
        });

    }

    /**
     * Test {@link RingBufferIdx#removeAll()}
     */
    @Test
    public void test_removeAll_removeAllNoReporting() {

        final int capacity = 123;

        String[] elements = new String[RingBufferIdx.allocationSizeForCapacity(
                capacity)];

        RingBufferIdx rbi = new RingBufferIdx(capacity, d -> elements[d] = null,
                (to, from) -> elements[to] = elements[from]);

        for (int i = 0; i < capacity + 17; i++) {
            elements[rbi.acquireAppend()] = VALUE_SUPPLIER.get();
        }
        Assert.assertTrue(rbi.isFull());
        rbi.removeAll();
        Assert.assertTrue(rbi.isEmpty());

        /* all elements should've been set to null */
        for (String s : elements) {
            Assert.assertNull(s);
        }

        for (int i = 0; i < capacity + 17; i++) {
            elements[rbi.acquireAppend()] = VALUE_SUPPLIER.get();
        }
        Assert.assertTrue(rbi.isFull());
        rbi.removeAllNoReporting();
        Assert.assertTrue(rbi.isEmpty());
        /* all elements except for the extra allocated should be non-null, i.e. 1 null */
        int nullCount = 0;
        for (String s : elements) {
            if (s == null) {
                nullCount++;
            }
        }
        Assert.assertEquals(1, nullCount);
    }

    /**
     * Test {@link RingBufferIdx#removeHead()}
     */
    @Test
    public void test_removeHead() {
        final int capacity = 123;
        String[] elements = new String[RingBufferIdx.allocationSizeForCapacity(
                capacity)];
        RingBufferIdx rbi = new RingBufferIdx(capacity, d -> elements[d] = null,
                (to, from) -> elements[to] = elements[from]);

        LinkedList<String> doubleCheck = new LinkedList<>();

        while (!rbi.isFull()) {
            String value = VALUE_SUPPLIER.get();
            elements[rbi.acquireAppend()] = value;
            doubleCheck.add(value);
        }

        while (!doubleCheck.isEmpty()) {
            String expectedValue = doubleCheck.removeFirst();
            String actualValue = elements[rbi.removeHead()];
            Assert.assertEquals(expectedValue, actualValue);
            final IntIterator rbIterator = rbi.forwardIterator();
            doubleCheck.forEach(e -> {
                Assert.assertTrue(rbIterator.hasNext());
                Assert.assertEquals(e, elements[rbIterator.next()]);
            });
        }
        Assert.assertTrue(rbi.isEmpty());

        /* sweep and catch up */
        while (!rbi.isFull()) {
            elements[rbi.acquireAppend()] = VALUE_SUPPLIER.get();
        }

        int count = 0;
        while (!rbi.isEmpty()) {
            elements[rbi.removeHead()] = null;
            if (!rbi.isEmpty()) {
                elements[rbi.acquireAppend()] = VALUE_SUPPLIER.get();
                elements[rbi.removeHead()] = null;
            }
            count++;
        }
        Assert.assertEquals(capacity, count);

    }

    @Test
    public void test_removeTail() {
        final int capacity = 123;
        String[] elements = new String[RingBufferIdx.allocationSizeForCapacity(
                capacity)];
        RingBufferIdx rbi = new RingBufferIdx(capacity, d -> elements[d] = null,
                (to, from) -> elements[to] = elements[from]);

        LinkedList<String> doubleCheck = new LinkedList<>();

        while (!rbi.isFull()) {
            String value = VALUE_SUPPLIER.get();
            elements[rbi.acquireAppend()] = value;
            doubleCheck.add(value);
        }

        while (!doubleCheck.isEmpty()) {
            String expectedValue = doubleCheck.removeLast();
            String actualValue = elements[rbi.removeTail()];
            Assert.assertEquals(expectedValue, actualValue);
            final IntIterator rbIterator = rbi.forwardIterator();
            doubleCheck.forEach(e -> {
                Assert.assertTrue(rbIterator.hasNext());
                Assert.assertEquals(e, elements[rbIterator.next()]);
            });
        }

        Assert.assertTrue(rbi.isEmpty());

        /* sweep and catch up */
        while (!rbi.isFull()) {
            elements[rbi.acquireAppend()] = VALUE_SUPPLIER.get();
        }

        int count = 0;
        while (!rbi.isEmpty()) {
            elements[rbi.removeTail()] = null;
            if (!rbi.isEmpty()) {
                elements[rbi.acquirePrepend()] = VALUE_SUPPLIER.get();
                elements[rbi.removeTail()] = null;
            }
            count++;
        }
        Assert.assertEquals(capacity, count);
    }

    /**
     * Test {@link RingBufferIdx#removeFirst(java.util.function.IntPredicate)}
     */
    @Test
    public void test_removeFirst_predicate() {

        LinkedList<Integer> deletionPos = new LinkedList<>();

        /* test capacity one */
        RingBufferIdx rbi = new RingBufferIdx(1, d -> deletionPos.add(d),
                (to, from) -> {
                });
        Assert.assertTrue(rbi.isEmpty());
        Assert.assertFalse(rbi.removeFirst(i -> i == 0));

        rbi.acquireAppend();
        int head = rbi.head();
        Assert.assertTrue(rbi.isFull());
        Assert.assertTrue(rbi.removeFirst(i -> i == head));
        Assert.assertTrue(rbi.isEmpty());
        Assert.assertEquals(head, deletionPos.removeFirst().intValue());
        Assert.assertTrue(deletionPos.isEmpty());

        /* test capacity more than one*/
        for (int capacity = 1; capacity < 50; capacity++) {
            String[] elements = new String[RingBufferIdx.
                    allocationSizeForCapacity(
                            capacity)];
            rbi = new RingBufferIdx(capacity, d -> elements[d] = null,
                    (to, from) -> elements[to] = elements[from]);
            LinkedList<String> elementsExpected = new LinkedList<>();
            String notFoundValue = VALUE_SUPPLIER.get();
            String repeatingValue = VALUE_SUPPLIER.get();
            boolean useRepeatingValue = false;
            while (!rbi.isFull()) {
                String value;
                if (useRepeatingValue) {
                    value = repeatingValue;
                } else {
                    value = VALUE_SUPPLIER.get();
                }
                useRepeatingValue = !useRepeatingValue;
                elements[rbi.acquireAppend()] = value;
                elementsExpected.add(value);
            }
            /* remove repeating first */
            while (rbi.removeFirst(i -> elements[i].equals(repeatingValue))) {
                Assert.assertTrue(elementsExpected.removeFirstOccurrence(
                        repeatingValue));
                IntIterator rbIterator = rbi.forwardIterator();
                for (String s : elementsExpected) {
                    Assert.assertTrue(rbIterator.hasNext());
                    Assert.assertEquals(s, elements[rbIterator.next()]);
                }
            }

            /* try removing something that doesn't exist */
            Assert.assertFalse(rbi.removeFirst(i -> elements[i].
                    equals(notFoundValue)));

            /* remove the rest */
            int headTailBetween = 0;

            while (!rbi.isEmpty()) {
                switch (headTailBetween) {
                    case 0:
                        final String sRemoveHead = elementsExpected.peekFirst();
                        elementsExpected.removeFirstOccurrence(sRemoveHead);
                        Assert.assertTrue(rbi.removeFirst(i -> elements[i].
                                equals(
                                        sRemoveHead)));
                        IntIterator rbIteratorHead = rbi.forwardIterator();
                        for (String s : elementsExpected) {
                            Assert.assertTrue(rbIteratorHead.hasNext());
                            Assert.assertEquals(s, elements[rbIteratorHead.
                                    next()]);
                        }
                        headTailBetween++;
                        break;
                    case 1:
                        final String sRemoveTail = elementsExpected.peekLast();
                        elementsExpected.removeFirstOccurrence(sRemoveTail);
                        Assert.assertTrue(rbi.removeFirst(i -> elements[i].
                                equals(
                                        sRemoveTail)));
                        IntIterator rbIteratorTail = rbi.forwardIterator();
                        for (String s : elementsExpected) {
                            Assert.assertTrue(rbIteratorTail.hasNext());
                            Assert.assertEquals(s, elements[rbIteratorTail.
                                    next()]);
                        }
                        headTailBetween++;
                        headTailBetween++;
                        break;
                    default:
                        final String sRemoveBetween = elementsExpected.get(
                                elementsExpected.size() / 2);
                        elementsExpected.removeFirstOccurrence(sRemoveBetween);
                        Assert.assertTrue(rbi.removeFirst(i -> elements[i].
                                equals(
                                        sRemoveBetween)));
                        IntIterator rbIteratorBetween = rbi.forwardIterator();
                        for (String s : elementsExpected) {
                            Assert.assertTrue(rbIteratorBetween.hasNext());
                            Assert.assertEquals(s,
                                    elements[rbIteratorBetween.next()]);
                        }
                        headTailBetween = 0;
                }
            }
            Assert.assertTrue(elementsExpected.isEmpty());
        }
    }

    /**
     * Test {@link RingBufferIdx#removeLast(java.util.function.IntPredicate)}
     */
    @Test
    public void test_removeLast_predicate() {
        LinkedList<Integer> deletionPos = new LinkedList<>();

        /* test capacity one */
        RingBufferIdx rbi = new RingBufferIdx(1, d -> deletionPos.add(d),
                (to, from) -> {
                });
        Assert.assertTrue(rbi.isEmpty());
        Assert.assertFalse(rbi.removeFirst(i -> i == 0));

        rbi.acquireAppend();
        int tail = rbi.head();
        Assert.assertTrue(rbi.isFull());
        Assert.assertTrue(rbi.removeLast(i -> i == tail));
        Assert.assertTrue(rbi.isEmpty());
        Assert.assertEquals(tail, deletionPos.removeLast().intValue());
        Assert.assertTrue(deletionPos.isEmpty());

        /* test capacity more than one*/
        for (int capacity = 1; capacity < 50; capacity++) {
            String[] elements = new String[RingBufferIdx.
                    allocationSizeForCapacity(
                            capacity)];
            rbi = new RingBufferIdx(capacity, d -> elements[d] = null,
                    (to, from) -> elements[to] = elements[from]);
            LinkedList<String> elementsExpected = new LinkedList<>();
            String notFoundValue = VALUE_SUPPLIER.get();
            String repeatingValue = VALUE_SUPPLIER.get();
            boolean useRepeatingValue = false;
            while (!rbi.isFull()) {
                String value;
                if (useRepeatingValue) {
                    value = repeatingValue;
                } else {
                    value = VALUE_SUPPLIER.get();
                }
                useRepeatingValue = !useRepeatingValue;
                elements[rbi.acquireAppend()] = value;
                elementsExpected.add(value);
            }
            /* remove repeating first */
            while (rbi.removeLast(i -> elements[i].equals(repeatingValue))) {
                Assert.assertTrue(elementsExpected.removeLastOccurrence(
                        repeatingValue));
                IntIterator rbIterator = rbi.forwardIterator();
                for (String s : elementsExpected) {
                    Assert.assertTrue(rbIterator.hasNext());
                    Assert.assertEquals(s, elements[rbIterator.next()]);
                }
            }

            /* try removing something that doesn't exist */
            Assert.assertFalse(rbi.removeFirst(i -> elements[i].
                    equals(notFoundValue)));

            /* remove the rest */
            int headTailBetween = 0;

            while (!rbi.isEmpty()) {

                switch (headTailBetween) {
                    case 0:
                        final String sRemoveHead = elementsExpected.peekFirst();
                        elementsExpected.removeLastOccurrence(sRemoveHead);
                        Assert.assertTrue(rbi.removeLast(i -> elements[i].
                                equals(
                                        sRemoveHead)));
                        IntIterator rbIteratorHead = rbi.forwardIterator();
                        for (String s : elementsExpected) {
                            Assert.assertTrue(rbIteratorHead.hasNext());
                            Assert.assertEquals(s, elements[rbIteratorHead.
                                    next()]);
                        }
                        headTailBetween++;
                        break;
                    case 1:
                        final String sRemoveTail = elementsExpected.peekLast();
                        elementsExpected.removeLastOccurrence(sRemoveTail);
                        Assert.assertTrue(rbi.removeLast(i -> elements[i].
                                equals(
                                        sRemoveTail)));
                        IntIterator rbIteratorTail = rbi.forwardIterator();
                        for (String s : elementsExpected) {
                            Assert.assertTrue(rbIteratorTail.hasNext());
                            Assert.assertEquals(s, elements[rbIteratorTail.
                                    next()]);
                        }
                        headTailBetween++;
                        headTailBetween++;
                        break;
                    default:
                        final String sRemoveBetween = elementsExpected.get(
                                elementsExpected.size() / 2);
                        elementsExpected.removeLastOccurrence(sRemoveBetween);
                        Assert.assertTrue(rbi.removeLast(i -> elements[i].
                                equals(
                                        sRemoveBetween)));
                        IntIterator rbIteratorBetween = rbi.forwardIterator();
                        for (String s : elementsExpected) {
                            Assert.assertTrue(rbIteratorBetween.hasNext());
                            Assert.assertEquals(s,
                                    elements[rbIteratorBetween.next()]);
                        }
                        headTailBetween = 0;
                }
            }
            Assert.assertTrue(elementsExpected.isEmpty());
        }
    }

    /**
     * Test {@link RingBufferIdx#removeAll(java.util.function.IntPredicate)}
     */
    @Test
    public void test_removeAll_predicate() {
        for(int capacity=1;capacity<50;capacity++) {
            String[] elements = new String[RingBufferIdx.allocationSizeForCapacity(capacity)];
            RingBufferIdx rbi = new RingBufferIdx(capacity,
                    d -> elements[d] = null,
                    (to, from) -> elements[to] = elements[from]);
            LinkedList<String> elementsExpected = new LinkedList<>();
            /* fill with the same value */
            final String valueSame = VALUE_SUPPLIER.get();
            while(!rbi.isFull()) {
                elements[rbi.acquireAppend()]=valueSame;
                elementsExpected.add(valueSame);
            }
            rbi.removeAll(i -> elements[i].equals(valueSame));
            while(elementsExpected.remove(valueSame));
            Assert.assertTrue(rbi.isEmpty());
            Assert.assertTrue(elementsExpected.isEmpty());
            /* fill with unique values */
            while(!rbi.isFull()) {
                final String valueUnique = VALUE_SUPPLIER.get();
                elements[rbi.acquireAppend()]=valueUnique;
                elementsExpected.add(valueUnique);
            }
            while(!elementsExpected.isEmpty()) {
                final String valueToRemove = elementsExpected.get(elementsExpected.size()/2);
                rbi.removeAll(i -> elements[i].equals(valueToRemove));
                while(elementsExpected.remove(valueToRemove));
                Assert.assertEquals(elementsExpected.size(), rbi.size());
                IntIterator rbIterator = rbi.forwardIterator();
                for(String s : elementsExpected) {
                    Assert.assertEquals(s, elements[rbIterator.next()]);
                }
            }
            /* unique and non-unique values */
            final String uniqueValue = VALUE_SUPPLIER.get();
            boolean uniqueOn = false;
            while(!rbi.isFull()) {
                String value;
                if(uniqueOn) {
                    value = uniqueValue;
                } else {
                    value = VALUE_SUPPLIER.get();
                }
                uniqueOn = !uniqueOn;
                elements[rbi.acquireAppend()]=value;
                elementsExpected.add(value);
            }
            while(!elementsExpected.isEmpty()) {
                final String valueToRemove = elementsExpected.get(elementsExpected.size()/2);
                rbi.removeAll(i -> elements[i].equals(valueToRemove));
                while(elementsExpected.remove(valueToRemove));
                Assert.assertEquals(elementsExpected.size(), rbi.size());
                IntIterator rbIterator = rbi.forwardIterator();
                for(String s : elementsExpected) {
                    Assert.assertEquals(s, elements[rbIterator.next()]);
                }
            }
        }
    }

    /**
     * Test {@link RingBufferIdx#remove(int)}
     */
    @Test
    public void test_remove_at_index() {
        for(int capacity=1;capacity<50;capacity++) {
            RingBufferIdx rbi = new RingBufferIdx(capacity, d->{}, (to, from)->{});
            while(!rbi.isFull()) {
                rbi.acquireAppend();
            }
            int delpos = 0;
            while(!rbi.isEmpty()) {
                int lastSize=rbi.size();
                switch(delpos) {
                    case 0: rbi.remove(0);
                    delpos++;
                    break;
                    case 1: rbi.remove(rbi.size()-1);
                    delpos++;
                    break;
                    default:
                        rbi.remove(rbi.size()/2);
                        delpos++;
                }
                if(delpos==3) {
                    delpos=0;
                }
                Assert.assertEquals(lastSize-1, rbi.size());
                Assert.assertEquals(rbi.size(), rbi.compute_size_());
            }
        }
    }

}
