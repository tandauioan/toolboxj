package org.ticdev.toolboxj.numbers;

import org.ticdev.toolboxj.primitives.LongWrapper;

import java.math.BigInteger;

/**
 * This class represents a counter that can be increased and decreased.
 * <p>The counter is always a positive value. It optimizes operations by
 * using a long primitive and tracking overflow using a {@link BigInteger}.
 * </p>
 * <p>Decrementing from zero or subtracting more than the current counter
 * will always reset to 0.</p>
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public final class BigCounter {

    /**
     * {@link Long#MAX_VALUE} as {@link BigInteger}.
     */
    public static final BigInteger LONG_MAX_VALUE =
            BigInteger.valueOf(Long.MAX_VALUE);

    /**
     * Default class constructor.
     * <p/>
     * <p>Creates a new counter set to 0.</p>
     */
    public BigCounter() {
        reset();
    }

    /**
     * Class constructor.
     * <p/>
     * <p>Creates a new account initialized with the given initial
     * amount.</p>
     *
     * @param initialAmount the initial amount as a positive long.
     * @throws IllegalArgumentException if the initial amount is
     *                                  strictly negative.
     */
    public BigCounter(long initialAmount)
            throws
            IllegalArgumentException {
        assert_positive_argument_(initialAmount);
        counter = initialAmount;
    }

    /**
     * Copy constructor.
     * <p>Creates a new instance initialized with the values from
     * another counter.</p>
     *
     * @param other the other counter.
     */
    public BigCounter(BigCounter other) {
        maxLongCounter = other.maxLongCounter;
        counter = other.counter;
    }

    /**
     * Initial overflow value
     */
    private BigInteger maxLongCounter = BigInteger.ZERO;

    /**
     * Long counter.
     * always >= 0
     */
    private long counter = 0;

    /**
     * Resets the counter to 0.
     */
    public BigCounter reset() {
        maxLongCounter = BigInteger.ZERO;
        counter = 0;
        return this;
    }

    /**
     * Increments the counter by 1.
     */
    public BigCounter increment() {
        if (counter == Long.MAX_VALUE) {
            maxLongCounter = maxLongCounter.add(BigInteger.ONE);
            counter = 1;
        } else {
            counter++;
        }
        return this;
    }

    /**
     * Decrements the counter by 1. If the counter is 0 it will stay 0.
     */
    public BigCounter decrement() {
        if (counter > 0) {
            counter--;
        } else {
            if (maxLongCounter.signum() != 0) {
                maxLongCounter = maxLongCounter.subtract(BigInteger.ONE);
                counter = Long.MAX_VALUE - 1;
            }
        }
        return this;
    }

    /**
     * Returns true if the counter fits in a long primitive,
     * and false otherwise.
     *
     * @return true if the counter fits in a long primitive,
     * and false otherwise.
     */
    public boolean fitsLong() {
        return maxLongCounter.signum() == 0;
    }

    /**
     * Returns true if the counter is 0, and false otherwise.
     *
     * @return true if the counter is 0, and false otherwise.
     */
    public boolean isZero() {
        return maxLongCounter == null && counter == 0;
    }

    /**
     * Returns true if the counter fits in a long primitive and false otherwise.
     * <p>As a side-effect it sets a counter values that is guaranteed
     * to be equal or smaller than the actual value of the counter
     * following the rules below:</p>
     * <ul>
     * <li>if the returned value is true the long wrapper contains
     * the actual value of the counter</li>
     * <li>if the returned value is false the long wrapper contains
     * {@link Long#MAX_VALUE}.</li>
     * </ul>
     * <p>If the side-effect long wrapper is null, it is ignored.</p>
     *
     * @param longWrapper side-effect long wrapper
     * @return true if the counter fits in a long primitive and false
     * otherwise.
     */
    public boolean getMaxLong(LongWrapper longWrapper) {
        if (fitsLong()) {
            if (longWrapper != null) {
                longWrapper.set(counter);
            }
            return true;
        } else {
            if (longWrapper != null) {
                longWrapper.set(Long.MAX_VALUE);
            }
            return false;
        }
    }

    /**
     * Subtracts as many values as possible to fit in a long, while the
     * counter stays above 0, and returns the value.
     * <p>This method returns 0 when the counter reached 0.</p>
     * <p>This method is usable to deplete the counter by calling it
     * repeatedly, getting partial counts as long.</p>
     *
     * @return number of available values before reaching zero that fit in a long.
     */
    public long subtractMax() {
        if (maxLongCounter.signum() != 0) {
            maxLongCounter.subtract(BigInteger.ONE);
            return Long.MAX_VALUE;
        }
        long res = counter;
        counter -= counter;
        return res;
    }

    /**
     * Returns the actual value of the counter.
     *
     * @return the actual value of the counter.
     */
    public BigInteger getCounter() {
        return maxLongCounter.multiply(LONG_MAX_VALUE)
                             .add(BigInteger.valueOf(counter));
    }

    /**
     * Ensure the long argument is always positive
     *
     * @param argument the argument to check
     * @throws IllegalArgumentException if the argument is strictly negative.
     */
    private void assert_positive_argument_(long argument)
            throws
            IllegalArgumentException {
        if (argument < 0) {
            throw new IllegalArgumentException(
                    "The argument is expected to be >=0. Actual: " +
                    argument);
        }
    }

    /**
     * Adds the given positive amount to the counter.
     *
     * @param amount the amount to add
     * @throws IllegalArgumentException if the amount is strictly negative.
     */
    public BigCounter add(long amount)
            throws
            IllegalArgumentException {
        assert_positive_argument_(amount);
        if (Long.MAX_VALUE - amount >= counter) {
            counter += amount;
        } else {
            maxLongCounter = maxLongCounter.add(BigInteger.ONE);
            counter -= (Long.MAX_VALUE - amount);
        }
        return this;
    }

    /**
     * Subtracts the given positive amount from the counter.
     * If the counter would fall below zero it will be reset to 0.
     *
     * @param amount the amount to subtract
     * @throws IllegalArgumentException if the amount is strictly negative.
     */
    public BigCounter subtract(long amount)
            throws
            IllegalArgumentException {
        assert_positive_argument_(amount);
        if (amount <= counter) {
            counter -= amount;
            return this;
        }
        if (maxLongCounter == null) {
            counter = 0;
            return this;
        }
        counter = Long.MAX_VALUE - (amount - counter);
        maxLongCounter = maxLongCounter.subtract(BigInteger.ONE);
        return this;
    }

    /**
     * Adds the value of another counter to this one.
     *
     * @param other the other counter.
     */
    public BigCounter add(BigCounter other) {
        maxLongCounter = maxLongCounter.add(other.maxLongCounter);
        return add(other.counter);
    }

    /**
     * Subtracts the value of another counter from this one.
     *
     * @param other the other counter.
     */
    public BigCounter subtract(BigCounter other) {
        if (maxLongCounter.compareTo(other.maxLongCounter) < 0) {
            return reset();
        } else {
            maxLongCounter = maxLongCounter.subtract(other.maxLongCounter);
            return subtract(other.counter);
        }
    }

}
