package org.ticdev.toolboxj.functions;

/**
 * A method that is called with two arguments.
 *
 * @param <A1> the type of the first argument
 * @param <A2> the type of the second argument
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public interface BinaryConsumer<A1, A2> {

  /**
   * Calls the method with the given arguments.
   *
   * @param arg1 the first argument
   * @param arg2 the second argument
   */
  void accept(A1 arg1, A2 arg2);

}
