package org.ticdev.toolboxj.algorithms;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.ticdev.toolboxj.algorithms.backtracking.Backtracking;
import org.ticdev.toolboxj.algorithms.backtracking.BacktrackingSupport;

/**
 * Tests for {@link Backtracking} and associated classes.
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 */
public class BacktrackingTest {

    /**
     * Test permutations
     */
    @Test
    public void test_permutations() {

        Integer[][] expectedArrays = {

            { 0, 1, 2 },

            { 0, 2, 1 },

            { 1, 0, 2 },

            { 1, 2, 0 },

            { 2, 0, 1 },

            { 2, 1, 0 }

        };

        Set<List<Integer>> solutions = new HashSet<>();

        for (Integer[] ai : expectedArrays) {
            solutions.add(Arrays.asList(ai));
        }

        try {
            Backtracking permutations =
                BacktrackingSupport.permutations(3);
            int arr[];
            while ((arr = permutations.findNext()) != null) {
                Integer[] iarr = new Integer[arr.length];
                for (int i = 0; i < arr.length; i++) {
                    iarr[i] = arr[i];
                }
                List<Integer> actual = Arrays.asList(iarr);
                Assert.assertTrue(solutions.remove(actual));
            }
            Assert.assertTrue(solutions.isEmpty());
        } catch (IllegalArgumentException | InterruptedException ex) {
            Assert.fail(ex.toString());
        }

    }

    /**
     * Test queens problem and reset to partial solution
     */
    @Test
    public void test_queens() {

        Backtracking queens;

        /*
         * 0 should fail the constructor
         */
        try {
            queens = BacktrackingSupport.queens(0);
            Assert.fail(queens.toString());
        } catch (Exception ex) {
            Assert.assertNotNull(ex);
        }

        /*
         * 1 should only have 1 solution = {0}
         */
        try {
            queens = BacktrackingSupport.queens(1);
            int[] arr;
            arr = queens.findNext();
            Assert.assertNotNull(arr);
            Assert.assertEquals(1, arr.length);
            Assert.assertEquals(0, arr[0]);
            Assert.assertNull(queens.findNext());
        } catch (IllegalArgumentException | InterruptedException ex) {
            Assert.fail(ex.toString());
        }

        /*
         * 2 should have no solutions
         */
        try {
            queens = BacktrackingSupport.queens(2);
            Assert.assertNull(queens.findNext());
        } catch (IllegalArgumentException | InterruptedException ex) {
            Assert.fail(ex.toString());
        }

        /*
         * 3 should have no solutions
         */
        try {
            queens = BacktrackingSupport.queens(3);
            Assert.assertNull(queens.findNext());
        } catch (IllegalArgumentException | InterruptedException ex) {
            Assert.fail(ex.toString());
        }

        /*
         * 4 should have 2 solutions
         */

        int[] solution1 = { 1, 3, 0, 2 };

        int[] solution2 = { 2, 0, 3, 1 };

        try {
            queens = BacktrackingSupport.queens(4);
            int[] arr;
            arr = queens.findNext();
            Assert.assertNotNull(arr);
            Assert.assertArrayEquals(solution1, arr);
            arr = queens.findNext();
            Assert.assertNotNull(arr);
            Assert.assertArrayEquals(solution2, arr);
            Assert.assertNull(queens.findNext());
            /* reset */
            queens.reset();
            arr = queens.findNext();
            Assert.assertNotNull(arr);
            Assert.assertArrayEquals(solution1, arr);
            arr = queens.findNext();
            Assert.assertNotNull(arr);
            Assert.assertArrayEquals(solution2, arr);
            Assert.assertNull(queens.findNext());
            /* reset to first solution and get the second */
            queens.resetTo(solution1);
            arr = queens.findNext();
            Assert.assertNotNull(arr);
            Assert.assertArrayEquals(solution2, arr);
            Assert.assertNull(queens.findNext());
            /* reset to partial before first and get both */
            queens.resetTo(solution1, 0, 1);
            arr = queens.findNext();
            Assert.assertNotNull(arr);
            Assert.assertArrayEquals(solution2, arr);
            Assert.assertNull(queens.findNext());
            /* reset to before the solutions and capture both */
            queens.resetTo(new int[] { 0 });
            arr = queens.findNext();
            Assert.assertNotNull(arr);
            Assert.assertArrayEquals(solution1, arr);
            arr = queens.findNext();
            Assert.assertNotNull(arr);
            Assert.assertArrayEquals(solution2, arr);
            Assert.assertNull(queens.findNext());
            /* fail on large value */
            try {
                queens.resetTo(new int[] { 4 });
                Assert.fail();
            } catch (Exception ex) {
                Assert.assertNotNull(ex);
            }
        } catch (IllegalArgumentException | InterruptedException ex) {
            Assert.fail(ex.toString());
        }

    }

    /**
     * Fail constructor
     */
    @Test
    public void testFailConstructor() {

        /* success first */
        try {
            Backtracking b = new Backtracking(Backtracking.COUNT_MIN,
                Backtracking.TOP_LIMIT_MIN, (arr, i) -> true);
            Assert.assertNotNull(b.findNext());
        } catch (IllegalArgumentException | InterruptedException ex) {
            Assert.fail(ex.toString());
        }

        /* failures */

        try {
            Assert.fail(new Backtracking(Backtracking.COUNT_MIN,
                                         Backtracking.TOP_LIMIT_MIN, null).
                toString());
        } catch (Exception ex) {
            Assert.assertNotNull(ex);
        }

        try {
            Assert.fail(new Backtracking(Backtracking.COUNT_MIN - 1,
                                         Backtracking.TOP_LIMIT_MIN, (arr,
                                                                      index) ->
                                         true).toString());
        } catch (Exception ex) {
            Assert.assertNotNull(ex);
        }

        try {
            Assert.fail(new Backtracking(Backtracking.COUNT_MAX + 1,
                                         Backtracking.TOP_LIMIT_MIN, (arr,
                                                                      index) ->
                                         true).toString());
        } catch (Exception ex) {
            Assert.assertNotNull(ex);
        }

        try {
            Assert.fail(new Backtracking(Backtracking.COUNT_MIN - 1, 2,
                                         (arr, index) -> true).toString());
        } catch (Exception ex) {
            Assert.assertNotNull(ex);
        }

    }

}
