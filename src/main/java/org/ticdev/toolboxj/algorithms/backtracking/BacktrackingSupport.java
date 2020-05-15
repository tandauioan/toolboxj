package org.ticdev.toolboxj.algorithms.backtracking;

/**
 * Support methods for the backtracking class offering implementations for
 * different backtracking problems.
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public final class BacktrackingSupport {

  /**
   * Default private constructor.
   */
  private BacktrackingSupport() {
  }

  /**
   * Condition for a permutations search.
   */
  private static final BacktrackingCondition PERMUTATIONS_CONDITION =
      (arr, lastIndex) -> {
        /* if duplicate position in partial result then return false */
        for (int i = 0; i < lastIndex; i++) {
          if (arr[i] == arr[lastIndex]) {
            return false;
          }
        }

        return true;

      };

  /**
   * Condition for the queens problem test.
   */
  private static final BacktrackingCondition QUEENS_CONDITION =
      (arr, lastIndex) -> {
        /*
         * if queens on same line or matching diagonal in the partial result
         * then false
         */
        for (int i = 0; i < lastIndex; i++) {
          int val1 = lastIndex - i;
          int val2 = arr[lastIndex] - arr[i];
          val2 = val2 < 0 ? -val2 : val2;
          if (val1 == val2 || arr[lastIndex] == arr[i]) {
            return false;
          }
        }
        return true;
      };

  /**
   * Returns a {@link Backtracking} instance that solves the permutations
   * problem.
   *
   * @param count the number of elements to permute
   * @return the backtracking solution
   * @throws IllegalArgumentException if the count is invalid
   */
  public static Backtracking permutations(final int count)
      throws IllegalArgumentException {
    return new Backtracking(count, count, PERMUTATIONS_CONDITION);
  }

  /**
   * Returns a {@link Backtracking} instance that solves the n-queens problem.
   *
   * @param count the number of queens
   * @return the backtracking solution
   * @throws IllegalArgumentException if the count is invalid
   */
  public static Backtracking queens(final int count)
      throws IllegalArgumentException {
    return new Backtracking(count, count, QUEENS_CONDITION);
  }

}
