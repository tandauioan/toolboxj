package org.ticdev.toolboxj.functions;

/**
 * A method that is called with four arguments.
 *
 * @param <A1> the type of the first argument.
 * @param <A2> the type of the second argument.
 * @param <A3> the type of the third argument.
 * @param <A4> the type of the fourth argument.
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public interface QuadConsumer<A1, A2, A3, A4> {

  /**
   * Calls the method with the given arguments.
   *
   * @param arg1 the first argument.
   * @param arg2 the second argument.
   * @param arg3 the third argument.
   * @param arg4 the fourth argument.
   */
  void accept(A1 arg1, A2 arg2, A3 arg3, A4 arg4);

}
