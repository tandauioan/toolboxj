package org.ticdev.toolboxj.algorithms;

/**
 * Condition used with the {@link Backtracking} class to validate a
 * partial/complete solution.
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 */
public interface BacktrackingCondition {

    /**
     * Given a partial solution array, this method returns true if the
     * backtracking condition holds and false otherwise.
     * 
     * @param array
     *            the array containing the (partial) solution
     * @param lastIndex
     *            the last index in the array (less than the length of the array
     *            for a partial solution test).
     * @return true if the condition holds and false otherwise.
     */
    boolean test(int[] array, int lastIndex);

}
