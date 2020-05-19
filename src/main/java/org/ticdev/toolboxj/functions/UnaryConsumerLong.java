package org.ticdev.toolboxj.functions;

/**
 * {@link UnaryConsumer} generalization for long primitives.
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public interface UnaryConsumerLong extends UnaryConsumer<Long> {

  /**
   * Calls the method with the given long argument.
   *
   * @param arg1 the argument
   */
  void acceptLong(long arg1);

  @Override
  default void accept(Long arg1) {
    acceptLong(arg1);
  }

}
