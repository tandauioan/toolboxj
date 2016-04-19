package org.ticdev.toolboxj.collections;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Set;
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

}
