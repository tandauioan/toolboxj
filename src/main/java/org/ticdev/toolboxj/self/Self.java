package org.ticdev.toolboxj.self;

import org.ticdev.toolboxj.functions.UnaryConsumer;
import org.ticdev.toolboxj.functions.UnaryFunction;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * A self-referencing extendable interface.
 *
 * @param <T> the self-referenced implementation
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public interface Self<T extends Self<T>> {

  /**
   * Returns the self-referencing implementation or extension of this
   * interface.
   *
   * @return the self-referencing implementation or extension of this
   *     interface.
   */
  T self();

  /**
   * Applies the given mapper to this Self.
   *
   * @param mapper the mapper.
   * @param <R>    the type of the mapping.
   * @return the result of the mapping.
   */
  default <R> R selfMap(
      final UnaryFunction<? super T, ? extends R> mapper) {
    return mapper.apply(self());
  }

  /**
   * Filters this Self.
   *
   * @param predicate the predicate to test the Self.
   * @return a defined optional containing this instance if the predicate's
   *     test method returns true, and empty otherwise.
   */
  default Optional<T> selfFilter(final Predicate<? super T> predicate) {
    final T self = self();
    return predicate.test(self)
        ? Optional.of(self)
        : Optional.empty();
  }

  /**
   * Passes this instance to the given consumer and returns this instance
   * upon return.
   *
   * @param consumer the consumer.
   * @return this instance.
   */
  default T selfConsume(final UnaryConsumer<? super T> consumer) {
    T self = self();
    consumer.accept(self);
    return self;
  }

  /**
   * Filters this instance using the given predicate, and
   * calls either onTrue, if the predicate returns true, or onFalse
   * if the predicate returns false.
   *
   * @param predicate the predicate.
   * @param onTrue    the mapping to apply if the predicate returns true.
   * @param onFalse   the mapping to apply if the predicate returns false.
   * @param <R>       the type of the result.
   * @return either the result of applying onTrue to this instance if
   *     the predicate applied to this instance returns true, or the result
   *     of applying onFalse to this instance.
   */
  default <R> R selfFilteredMap(
      final Predicate<? super T> predicate,
      final UnaryFunction<? super T, ? extends R> onTrue,
      final UnaryFunction<? super T, ? extends R> onFalse
  ) {
    T self = self();
    return predicate.test(self)
        ? onTrue.apply(self)
        : onFalse.apply(self);
  }

}
