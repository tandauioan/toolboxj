package org.ticdev.toolboxj.functions;

/**
 * {@link UnaryConsumer} generalization for int primitives.
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public interface UnaryConsumerInt extends UnaryConsumer<Integer> {

  /**
   * Calls the method with the given int argument.
   *
   * @param arg1 the argument
   */
  void acceptInt(int arg1);

  @Override
  default void accept(Integer arg1) {
    acceptInt(arg1);
  }

}
