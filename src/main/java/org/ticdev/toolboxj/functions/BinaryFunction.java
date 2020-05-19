package org.ticdev.toolboxj.functions;

/**
 * A function receiving two arguments and returning a result.
 *
 * @param <A1> the type of the first argument
 * @param <A2> the type of the second argument
 * @param <R>  the type of the result
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public interface BinaryFunction<A1, A2, R> {

  /**
   * Calls the function with the given arguments.
   *
   * @param arg1 the first argument
   * @param arg2 the second argument
   * @return the result
   */
  R apply(A1 arg1, A2 arg2);

}
