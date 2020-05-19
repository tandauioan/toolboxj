package org.ticdev.toolboxj.functions;

/**
 * A function that declares no arguments but returns a result.
 *
 * @param <R> the type of the result
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public interface NullaryFunction<R> {

  /**
   * Calls the function and returns the result.
   *
   * @return the result
   */
  R apply();

}
