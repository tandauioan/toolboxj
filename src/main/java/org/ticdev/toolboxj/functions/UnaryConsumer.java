package org.ticdev.toolboxj.functions;

/**
 * A method that is called with one argument.
 *
 * @param <A1> the type of the argument
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public interface UnaryConsumer<A1> {

  /**
   * Calls the method on the given argument.
   *
   * @param arg1 the argument
   */
  void accept(A1 arg1);

}
