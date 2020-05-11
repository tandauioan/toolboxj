package org.ticdev.toolboxj.self;

import org.ticdev.toolboxj.functions.UnaryConsumer;

import java.util.function.Predicate;

/**
 * A conditional consumer on a self interface.
 *
 * @param <T> the self-referencing implementation
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 * @deprecated {@link Self} generalizations have been deprecated in favor
 *     of default methods too keep the {@link Self} interface self-contained.
 */
public interface SelfConditionalConsumer<T extends SelfConditionalConsumer<T>>
    extends
    Self<T> {

  /**
   * If the predicate applied to this instance returns true then thenDo
   * consumes this instance, else elseDo consumes this instance.
   *
   * @param predicate the predicate
   * @param thenDo    consumer if the predicate is true
   * @param elseDo    consumer if the predicate is false
   * @return this instance
   */
  default T ifThenElse(
      Predicate<? super T> predicate,
      UnaryConsumer<? super T> thenDo,
      UnaryConsumer<? super T> elseDo) {
    T self = self();
    if (predicate.test(self)) {
      thenDo.accept(self);
    } else {
      elseDo.accept(self);
    }
    return self;
  }

  /**
   * thenDo consumes this instance if the predicate applied to this instance
   * returns true.
   *
   * @param predicate the predicate
   * @param thenDo    consumer if the predicate is true
   * @return this instance
   */
  default T ifDo(
      Predicate<? super T> predicate,
      UnaryConsumer<? super T> thenDo) {

    T self = self();
    if (predicate.test(self)) {
      thenDo.accept(self);
    }

    return self;

  }

}
