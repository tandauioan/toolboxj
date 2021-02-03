package org.ticdev.toolboxj.functions;

/**
 * A function receiving an argument and returning a result,
 * possibly a checked exception.
 *
 * @param <A1> the type of the argument.
 * @param <R>  the type of the result.
 * @param <E>  the type of the checked exception.
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public interface CheckedUnaryFunction<A1, R, E extends Throwable> {

  /**
   * Calls the function on the given argument.
   *
   * @param arg1 the argument.
   * @return the result.
   * @throws E if an exception occurs.
   */
  R apply(A1 arg1) throws E;

}
