package org.ticdev.toolboxj.functions;

import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Utility methods for functions.
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public interface FunctionSupport {

  /**
   * Executes the given {@link Callable} and returns the result.
   * If the callable throws an exception the <code>onException</code>
   * method will be used to convert the exception
   * into a result of the expected type.
   *
   * @param callable    the callable to execute.
   * @param onException the exception mapper.
   * @param <T>         the type of the result.
   * @return the result from the callable if no exception, or the result
   *     from the exception mapper function.
   */
  static <T> T remapOnThrowable(
      Callable<T> callable,
      Function<? super Throwable, ? extends T> onException) {
    try {
      return callable.call();
    } catch (Throwable throwable) {
      return onException.apply(throwable);
    }
  }

  /**
   * Converts a throwable to a {@link RuntimeException}.
   *
   * <p>
   * If the throwable is a runtime exception already,
   * it will be case to {@link RuntimeException}
   * and returned. Otherwise, the exception will be wrapped in
   * a new instance of a runtime exception.
   * </p>
   *
   * @param throwable the throwable.
   * @return a runtime exception.
   */
  static RuntimeException toRte(Throwable throwable) {
    if (throwable instanceof RuntimeException) {
      return (RuntimeException) throwable;
    }
    return new RuntimeException(throwable);
  }

  /**
   * Converts a throwable to a new instance of {@link RuntimeException}.
   *
   * <p>
   * The difference between this method and {@link #toRte(Throwable)}
   * is that this method will wrap the throwable in a new instance of
   * {@link RuntimeException} even if the {@link Throwable} is already
   * a runtime exception.
   * </p>
   *
   * @param throwable the throwable.
   * @return a new instance of a runtime exception.
   */
  static RuntimeException toNewRte(Throwable throwable) {
    return new RuntimeException(throwable);
  }

  /**
   * A functional substitution with expected result and exception type
   * which will always throw a {@link RuntimeException} using
   * {@link #toRte(Throwable)}.
   *
   * @param throwable the throwable to wrap or re-throw as a runtime exception.
   * @param <R>       the type of result expected from this method.
   * @return this method never returns, it always throws a runtime exception.
   */
  static <R> R alwaysRte(Throwable throwable) {
    throw toRte(throwable);
  }

  /**
   * Like {@link #alwaysRte(Throwable)} except that it is using
   * {@link #toNewRte(Throwable)} as the thrown exception.
   *
   * @param throwable the throwable to wrap in the runtime exception.
   * @param <R>       the type of result expected from this method.
   * @return this method never returns, it always throws a runtime exception.
   */
  static <R> R alwaysNewRte(Throwable throwable) {
    throw toNewRte(throwable);
  }

  /**
   * Like {@link #alwaysRte(Throwable)} except that this method
   * mimics a consumer.
   *
   * @param throwable the throwable to wrap in the runtime exception.
   */
  static void doAlwaysRte(Throwable throwable) {
    throw toRte(throwable);
  }

  /**
   * Like {@link #alwaysNewRte(Throwable)} except that this
   * methods mimics a consumer.
   *
   * @param throwable the throwable to wrap in the runtime exception.
   */
  static void doAlwaysNewRte(Throwable throwable) {
    throw toNewRte(throwable);
  }

  /**
   * Calls the given {@link Callable} and returns its result. Exceptions are
   * converted to {@link RuntimeException}
   * using {@link #alwaysRte(Throwable)}.
   *
   * @param callable the callable.
   * @param <T>      the type of the result returned by the callable.
   * @return the result of calling the callable.
   * @throws RuntimeException if the callable threw an exception.
   */
  static <T> T withRte(Callable<T> callable) throws RuntimeException {
    return remapOnThrowable(callable, FunctionSupport::alwaysRte);
  }

  /**
   * Calls the given {@link Callable} and returns its result. Exceptions are
   * converted to {@link RuntimeException}
   * using {@link #alwaysNewRte(Throwable)}.
   *
   * @param callable the callable.
   * @param <T>      the type of the result returned by the callable.
   * @return the result of calling the callable.
   * @throws RuntimeException if the callable threw an exception.
   */
  static <T> T withNewRte(Callable<T> callable) throws RuntimeException {
    return remapOnThrowable(callable, FunctionSupport::alwaysNewRte);
  }

  /**
   * Converts the {@link Callable} to a {@link Supplier}. The callable's
   * exception is converted to {@link RuntimeException}
   * using {@link #withRte}.
   *
   * @param callable the callable.
   * @param <T>      the type of result returned by the callable.
   * @return the result of calling the callable.
   */
  static <T> Supplier<T> toRteSupplier(Callable<T> callable) {
    return () -> withRte(callable);
  }

  /**
   * Converts the {@link Callable} to a {@link Supplier}.
   * The callable's exception is converted to {@link RuntimeException}
   * using {@link #withNewRte(Callable)}.
   *
   * @param callable the callable.
   * @param <T>      the type of result returned by the callable.
   * @return the result of calling the callable.
   */
  static <T> Supplier<T> toNewRteSupplier(Callable<T> callable) {
    return () -> withNewRte(callable);
  }

  /**
   * Like {@link #withRte(Callable)}, but it does not return a value.
   *
   * @param callable the callable.
   * @throws RuntimeException if an exception occurred.
   */
  static void doWithRte(Callable<?> callable) throws RuntimeException {
    withRte(callable);
  }

  /**
   * Like {@link #withNewRte(Callable)}, but it does not return a value.
   *
   * @param callable the callable.
   * @throws RuntimeException if an exception occurred.
   */
  static void doWithNewRte(Callable<?> callable) throws RuntimeException {
    withNewRte(callable);
  }

  /**
   * Converts the {@link Callable} to a {@link Runnable}. The callable's
   * exceptions is converted to {@link RuntimeException}
   * using {@link #withRte(Callable)}.
   *
   * @param callable the callable.
   * @return the runnable wrapping the callable.
   */
  static Runnable toRteRunnable(Callable<?> callable) {
    return () -> doWithRte(callable);
  }

  /**
   * Converts the {@link Callable} to a {@link Runnable}. The callable's
   * exception is converted to {@link RuntimeException}
   * using {@link #withNewRte(Callable)}.
   *
   * @param callable the callable.
   * @return the runnable wrapping the callable.
   */
  static Runnable toNewRteRunnable(Callable<?> callable) {
    return () -> doWithNewRte(callable);
  }

}
