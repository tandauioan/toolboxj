package org.ticdev.toolboxj.functions;

import java.util.function.Function;

/**
 * A function receiving an argument and returning a result.
 *
 * @param <A1> the type of the argument
 * @param <R>  the type of the result
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public interface UnaryFunction<A1, R> {

  /**
   * Calls the function on the given argument.
   *
   * @param arg1 the argument
   * @return the result
   */
  R apply(A1 arg1);

  /**
   * Creates an identity instance of {@link UnaryFunction} which returns
   * its argument.
   *
   * @param <R> the type of the argument and return value.
   * @return the new identity function.
   */
  static <R> UnaryFunction<R, R> identity() {
    return t -> t;
  }

  /**
   * Returns a new instance of {@link UnaryFunction} as a wrapper around
   * the given function. A call to  {@link UnaryFunction#apply(Object)}
   * is forwarded to the underyling {@link Function#apply(Object)}.
   *
   * @param function the underlying function.
   * @param <A>      the type of the function argument.
   * @param <R>      the type of the function result.
   * @return the new instance of {@link UnaryFunction} as a wrapper.
   */
  static <A, R> UnaryFunction<A, R> of(final Function<A, R> function) {
    return function::apply;
  }

}
