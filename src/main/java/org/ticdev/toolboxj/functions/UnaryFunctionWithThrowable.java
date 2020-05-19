package org.ticdev.toolboxj.functions;

import java.util.function.Function;

/**
 * A function receiving an argument and returning a result, possibly
 * throwing a {@link Throwable} in the process.
 *
 * @param <A1> the type of the argument.
 * @param <R>  the type of the result.
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
@FunctionalInterface
public interface UnaryFunctionWithThrowable<A1, R> {

  /**
   * Calls the function on the given argument.
   *
   * @param arg1 the argument.
   * @return the result.
   * @throws Throwable possible exception which can be overriden by
   *                   implementing classes to throw specific exceptions.
   */
  R apply(A1 arg1) throws Throwable;

  /**
   * Creates an identity instance of {@link UnaryFunctionWithThrowable}
   * which returns its argument.
   *
   * @param <R> the type of the argument and return value.
   * @return the new identity function.
   */
  static <R> UnaryFunction<R, R> identity() {
    return t -> t;
  }

  /**
   * Returns a new instance of {@link UnaryFunctionWithThrowable} as a
   * wrapper around the given function. A call to
   * {@link UnaryFunctionWithThrowable#apply(Object)}
   * is forwarded to the underlying {@link Function#apply(Object)}.
   *
   * @param function the underlying function.
   * @param <A>      the type of the function argument.
   * @param <R>      the type of the function result.
   * @return the new instance of {@link UnaryFunctionWithThrowable}
   *     as a wrapper.
   */
  static <A, R> UnaryFunctionWithThrowable<A, R> of(
      final Function<A, R> function) {
    return function::apply;
  }

  /**
   * Returns a new instance of {@link UnaryFunctionWithThrowable} as a wrapper
   * around the given unary function. A call to
   * {@link UnaryFunctionWithThrowable#apply(Object)}
   * is forwarded to the underlying {@link UnaryFunction#apply(Object)}.
   *
   * @param function the underlying unary function.
   * @param <A>      the type of the function argument.
   * @param <R>      the type of the function result.
   * @return the new instance of {@link UnaryFunctionWithThrowable}
   *     as a wrapper.
   */
  static <A, R> UnaryFunctionWithThrowable<A, R> of(
      final UnaryFunction<A, R> function) {
    return function::apply;
  }

}
