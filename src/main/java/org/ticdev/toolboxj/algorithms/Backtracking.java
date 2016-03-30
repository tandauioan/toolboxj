package org.ticdev.toolboxj.algorithms;

import java.util.Arrays;

/**
 * 
 * Generic implementation of backtracking.
 * 
 * <p>
 * A partial result is a function that maps a position in the set {0..
 * lastIndex} with values in {p[0]...p[lastIndex]}, where lastIndex &lt; count,
 * and p[i] &lt; top limit.
 * </p>
 * 
 * <p>
 * The entries in the partial result are checked against a
 * {@link BacktrackingCondition} that receives the partial result array and the
 * last index, and returns true if the partial solution satisfies a correctness
 * condition. A complete result is found when the array is filled with values
 * that satisfy the condition.
 * </p>
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 */
public class Backtracking {

    /**
     * Array used to construct the result.
     */
    private final int[] resultArray;

    /**
     * The exclusive upper bound for the values of an element
     */
    private final int topLimit;

    /**
     * The backtracking condition handler.
     */
    private final BacktrackingCondition conditionHandler;

    /**
     * The current position in the result array
     */
    private int cursor;

    /**
     * The minimum value of the number of elements
     */
    public static final int COUNT_MIN = 1;

    /**
     * The maximum value of the number of elements
     */
    public static final int COUNT_MAX = Integer.MAX_VALUE - 1;

    /**
     * The min value for the exclusive upper bound of the value of an element
     */
    public static final int TOP_LIMIT_MIN = 1;

    /**
     * The max value for the exclusive upper bound of the value of an element
     */
    public static final int TOP_LIMIT_MAX = Integer.MAX_VALUE;

    /**
     * Class constructor.
     * 
     * @param count
     *            the number of elements in the backtracking structure
     * @param topLimit
     *            the maximum value of any of the elements
     * @param conditionHandler
     *            the non-null backtracking condition handler
     * @throws IllegalArgumentException
     *             if the arguments are invalid.
     */
    public Backtracking(
        int count,
        int topLimit,
        BacktrackingCondition conditionHandler)
            throws IllegalArgumentException {
        assert_valid_count_(count);
        assert_valid_topLimit_(topLimit);
        assert_non_null_handler_(conditionHandler);
        resultArray = new int[count];
        this.topLimit = topLimit;
        this.conditionHandler = conditionHandler;
        reset_();
    }

    /**
     * Verifies that the backtracking condition handler is non-null.
     * 
     * @param conditionHandler
     *            the condition handler
     * @throws IllegalArgumentException
     *             if the handler is null
     */
    private void
        assert_non_null_handler_(BacktrackingCondition conditionHandler)
            throws IllegalArgumentException {
        if (conditionHandler == null) {
            throw new IllegalArgumentException(
                "The backtracking condition handler cannot be null.");
        }
    }

    /**
     * Asserts that the count is within range.
     * 
     * @param count
     *            the count
     * @throws IllegalArgumentException
     *             if the count is outside the range.
     */
    private void assert_valid_count_(int count)
        throws IllegalArgumentException {
        if (count < COUNT_MIN || count > COUNT_MAX) {
            throw new IllegalArgumentException(String.format(
                "Count should be between %d and %d. Actual value: %d",
                COUNT_MIN, COUNT_MAX, count));
        }
    }

    /**
     * Asserts that the max value is within range.
     * 
     * @param maxValue
     *            the max value of an element
     * @throws IllegalArgumentException
     *             if the max value is outside the range.
     */
    private void assert_valid_topLimit_(int maxValue)
        throws IllegalArgumentException {
        if (maxValue < TOP_LIMIT_MIN || maxValue > TOP_LIMIT_MAX) {
            throw new IllegalArgumentException(String.format(
                "MaxValue should be between %d and %d. Actual value: %d",
                TOP_LIMIT_MIN, TOP_LIMIT_MAX, maxValue));
        }
    }

    /**
     * Resets the algorithm so it starts from the beginning.
     */
    private void reset_() {
        cursor = 0;
        resultArray[0] = -1;
    }

    /**
     * Resets the algorithm so it starts from the beginning.
     */
    public void reset() {
        reset_();
    }

    /**
     * Resets the internal array to the given partial or complete solution to
     * use as a starting point for the search. The current state is ignored. It
     * is up to the user to provide a correct partial solution.
     * 
     * <p>
     * If the provided configuration is incorrect for the backtracking
     * condition, then the behavior of the algorithm is undefined.
     * </p>
     * 
     * @param partial
     *            the partial of complete solution to start from.
     * @param off
     *            the offset in the array
     * @param len
     *            the number of elements in the (partial) solution.
     */
    public void resetTo(int[] partial, int off, int len) {
        if (off < 0 || len < 0 || partial.length - len < 0
            || partial.length - len < off || len > resultArray.length) {
            throw new IndexOutOfBoundsException();
        }
        cursor =
            len > resultArray.length ? resultArray.length - 1 : len - 1;
        for (int i = 0; i < len; i++) {
            int voff = partial[off++];
            if (voff < 0 || voff >= topLimit) {
                throw new RuntimeException("Bad value: " + voff);
            }
            resultArray[i] = voff;
        }
    }

    /**
     * Like {@link #resetTo(int[], int, int)} using 0 as offset and the array
     * length as len.
     * 
     * @param partial
     *            the partial solution array
     */
    public void resetTo(int[] partial) {
        resetTo(partial, 0, partial.length);
    }

    /**
     * Finds the next solution. This method returns the underlying array to
     * avoid a copy overhead. However, the array could be changed outside of the
     * class, breaking the encapsulation. Only use if you can enforce the scope.
     * 
     * @return the next solution
     * @throws InterruptedException
     *             if the searching thread was interrupted.
     */
    public int[] findNextNoCopy()
        throws InterruptedException {
        while (cursor > -1) {
            if (Thread.interrupted()) {
                throw new InterruptedException();
            }
            resultArray[cursor]++;
            if (resultArray[cursor] == topLimit) {
                cursor--;
            } else {
                if (conditionHandler.test(resultArray, cursor)) {
                    if (cursor == resultArray.length - 1) {
                        return resultArray;
                    } else {
                        cursor++;
                        resultArray[cursor] = -1;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Finds the next solution and returns a copy of the solution array. This
     * method has an array copy overhead, but maintains encapsulation.
     * 
     * @return the next solution
     * @throws InterruptedException
     *             if the searching thread was interrupted.
     */
    public int[] findNext()
        throws InterruptedException {
        while (cursor > -1) {
            if (Thread.interrupted()) {
                throw new InterruptedException();
            }
            resultArray[cursor]++;
            if (resultArray[cursor] == topLimit) {
                cursor--;
            } else {
                if (conditionHandler.test(resultArray, cursor)) {
                    if (cursor == resultArray.length - 1) {
                        return Arrays.copyOf(resultArray,
                            resultArray.length);
                    } else {
                        cursor++;
                        resultArray[cursor] = -1;
                    }
                }
            }
        }
        return null;
    }

}
