package org.ticdev.toolboxj.support;

import org.junit.Assert;
import org.junit.Test;
import org.ticdev.toolboxj.primitives.IntWrapper;
import org.ticdev.toolboxj.primitives.LongWrapper;
import org.ticdev.toolboxj.tuples.Pair;
import org.ticdev.toolboxj.tuples.TupleSupport;

import java.util.concurrent.Callable;

/**
 * Test class for {@link ExecutionSupport}.
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public class ExecutionSupportTest {

    /**
     * Test {@link ExecutionSupport#time(Callable, LongWrapper)}.
     */
    @Test
    public void testTime() {

        LongWrapper lw = LongWrapper.of(-1);

        /* timed, no exception */
        try {
            Long result = ExecutionSupport.time(() -> 1L, lw);
            Assert.assertEquals(1L, result.longValue());
            Assert.assertTrue(lw.longValue() >= 0);
        } catch (Exception ex) {
            Assert.fail();
        }

        /* timed, exception */
        lw.set(-1);
        try {
            Long result = ExecutionSupport.time(() -> {
                throw new Exception();
            }, lw);
            Assert.fail("Timed execution returned result " + result);
        } catch (Exception ex) {
            Assert.assertNotNull(ex);
            Assert.assertTrue(lw.longValue() >= 0);
        }

    }

    /**
     * Test {@link ExecutionSupport#timeMultiple(Callable, long[])}.
     */
    @Test
    public void testTimeMultiple() {

        /* timed, no exception */
        long[] times = new long[100];
        for (int i = 0; i < times.length; i++) {
            times[i] = -1;
        }
        IntWrapper runs = IntWrapper.of(0);
        try {
            Long result = ExecutionSupport.timeMultiple(() -> {
                runs.set(runs.intValue() + 1);
                return 1L;
            }, times);
            Assert.assertEquals(1L, result.longValue());
            Assert.assertEquals(times.length, runs.intValue());
            for (long time : times) {
                Assert.assertTrue(time >= 0);
            }
        } catch (Exception ex) {
            Assert.fail(ex.toString());
        }

        /* timed, exception */
        for (int i = 0; i < times.length; i++) {
            times[i] = -1;
        }
        runs.set(0);
        final Exception[] exceptions = new Exception[times.length];
        for (int i = 0; i < exceptions.length; i++) {
            exceptions[i] = new Exception();
        }
        try {
            Long result = ExecutionSupport.timeMultiple(() -> {
                Exception ex = exceptions[runs.intValue()];
                runs.set(runs.intValue() + 1);
                throw ex;
            }, times);
            Assert.fail("Timed execution returned result " + result);
        } catch (Exception ex) {
            Assert.assertTrue(ex == exceptions[exceptions.length - 1]);
            Assert.assertEquals(times.length, runs.intValue());
            for (long time : times) {
                Assert.assertTrue(time >= 0);
            }
        }

    }

    /**
     * Test {@link ExecutionSupport#timeMultiple(Runnable, Callable, long[])}.
     */
    @Test
    public void testTimeMultiple_Reset() {

        final IntWrapper resetCount = IntWrapper.of(0);
        final IntWrapper runsCount = IntWrapper.of(0);
        final Runnable reset =
                () -> resetCount.set(resetCount.intValue() + 1);
        final long[] times = new long[100];

        /* timed, no exception */
        for (int i = 0; i < times.length; i++) {
            times[i] = -1;
        }
        try {
            Long result = ExecutionSupport.timeMultiple(reset, () -> {
                runsCount.set(runsCount.intValue() + 1);
                return 1L;
            }, times);
            Assert.assertEquals(1L, result.longValue());
            Assert.assertEquals(times.length, runsCount.intValue());
            Assert.assertEquals(times.length, resetCount.intValue());
            for (long time : times) {
                Assert.assertTrue(time >= 0);
            }
        } catch (Exception ex) {
            Assert.fail(ex.toString());
        }

        /* timed, exception */
        for (int i = 0; i < times.length; i++) {
            times[i] = -1;
        }
        resetCount.set(0);
        runsCount.set(0);
        final Exception[] exceptions = new Exception[times.length];
        for (int i = 0; i < exceptions.length; i++) {
            exceptions[i] = new Exception();
        }
        try {
            Long result = ExecutionSupport.timeMultiple(reset, () -> {
                Exception ex = exceptions[runsCount.intValue()];
                runsCount.set(runsCount.intValue() + 1);
                throw ex;
            }, times);
        } catch (Exception ex) {
            Assert.assertTrue(ex == exceptions[exceptions.length - 1]);
            Assert.assertEquals(times.length, runsCount.intValue());
            Assert.assertEquals(times.length, resetCount.intValue());
            for (long time : times) {
                Assert.assertTrue(time >= 0);
            }
        }
    }

    /**
     * Test {@link ExecutionSupport#average(long[])}
     */
    @Test
    public void testAverage() {
        long average;
        try {
            average = ExecutionSupport.average(null);
            Assert.fail(
                    "Expected exception, instead averaged to" + average);
        } catch (NullPointerException ex) {
            Assert.assertNotNull(ex);
        }

        average = ExecutionSupport.average(new long[]{});
        Assert.assertEquals(0, average);

        average = ExecutionSupport.average(new long[]{
                Long.MAX_VALUE, Long.MAX_VALUE, Long.MAX_VALUE
        });
        Assert.assertEquals(Long.MAX_VALUE, average);

        Pair<Long, long[]> testPair =
                TupleSupport.of(2L, new long[]{1, 2, 3});
        Assert.assertEquals(testPair.item1().longValue(),
                            ExecutionSupport.average(testPair.item2()));

    }
}
