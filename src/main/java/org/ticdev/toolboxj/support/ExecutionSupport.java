package org.ticdev.toolboxj.support;

import org.ticdev.toolboxj.primitives.LongWrapper;

import java.math.BigInteger;
import java.util.concurrent.Callable;

/**
 * Utility methods for tracking execution of code.
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public class ExecutionSupport {

    /**
     * Time the execution of a callable.
     *
     * @param callable   the callable to execute and time
     * @param durationNS a long wrapper that will contain the duration
     *                   when the method returns if it's non-null.
     * @param <R>        the type of result returned by the method
     * @return the result of the callable's execution
     * @throws Exception if an exception occurred (the exception will
     *                   be reflected in the side-effect duration, as well.)
     */
    public static <R> R time(
            Callable<R> callable, LongWrapper durationNS)
            throws
            Exception {
        long duration = System.nanoTime();
        try {
            R result = callable.call();
            duration = System.nanoTime() - duration;
            if (durationNS != null) {
                durationNS.set(duration);
            }
            return result;
        } catch (Exception ex) {
            duration = System.nanoTime() - duration;
            if (durationNS != null) {
                durationNS.set(duration);
            }
            throw ex;
        }
    }

    /**
     * Times the execution of a callable multiple times.
     *
     * @param callable the callable
     * @param times    an array whose length is the number of times the execution
     *                 should be timed and each index of the array points to
     *                 the time of each execution
     * @param <R>      the type of the result
     * @return the result returned by the last run of the callable
     * @throws Exception if an exception occurred - if an exception occurred
     *                   on more than one runs, the last exception is thrown
     *                   (each exception will be reflected in the
     *                   side-effect duration, as well)
     */
    public static <R> R timeMultiple(Callable<R> callable, long[] times)
            throws
            Exception {
        Exception exception = null;
        R result = null;
        for (int i = 0; i < times.length; i++) {
            LongWrapper lw = new LongWrapper();
            try {
                result = time(callable, lw);
                times[i] = lw.longValue();
            } catch (Exception ex) {
                times[i] = lw.longValue();
                exception = ex;
            }
        }
        if (exception != null) {
            throw exception;
        }
        return result;
    }

    /**
     * Times the execution of a callable multiple times, running the reset
     * code before each timed run.
     *
     * @param reset    the method to call each time before a timed run
     * @param callable the callable
     * @param times    an array whose length is the number of times the
     *                 execution should be timed and each index of the array
     *                 points to the time of each execution.
     * @param <R>      the type of the result
     * @return the result returned by the last run of the callable
     * @throws Exception if an exception occurred - if an exception occurred
     *                   on more than one runs, the last exception is thrown
     *                   (each exception will be reflected in the
     *                   side-effect duration, as well)
     */
    public static <R> R timeMultiple(
            Runnable reset, Callable<R> callable, long[] times)
            throws
            Exception {
        Exception exception = null;
        R result = null;
        for (int i = 0; i < times.length; i++) {
            LongWrapper lw = new LongWrapper();
            try {
                reset.run();
                result = time(callable, lw);
                times[i] = lw.longValue();
            } catch (Exception ex) {
                times[i] = lw.longValue();
                exception = ex;
            }
        }
        if (exception != null) {
            throw exception;
        }
        return result;
    }

    /**
     * Computes the average of the given array of longs. This method uses
     * {@link BigInteger} internally, for the partial computations to
     * avoid overflow and precision loss.
     *
     * @param values the values to average
     * @return the average
     */
    public static long average(long[] values) {
        if (values.length == 0) {
            return 0;
        }
        BigInteger bi = BigInteger.ZERO;
        for (long value : values) {
            bi = bi.add(BigInteger.valueOf(value));
        }
        return bi.divide(BigInteger.valueOf(values.length)).longValue();
    }

}
