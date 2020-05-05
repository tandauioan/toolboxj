package org.ticdev.toolboxj.functions;

/**
 * A function receining four arguments and returning a result.
 *
 * @param <A1> the type of the first argument.
 * @param <A2> the type of the second argument.
 * @param <A3> the type of the third argument.
 * @param <A4> the type of the fourth argument.
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public interface QuadFunction<A1, A2, A3, A4, R> {

  /**
   * Calls the function with the given arguments.
   *
   * @param arg1 the first argument.
   * @param arg2 the second argument.
   * @param arg3 the third argument.
   * @param arg4 the fourth argument.
   * @return the result.
   */
  R apply(A1 arg1, A2 arg2, A3 arg3, A4 arg4);

}
