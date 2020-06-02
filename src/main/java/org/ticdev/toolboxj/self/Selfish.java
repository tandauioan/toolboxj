package org.ticdev.toolboxj.self;

import org.ticdev.toolboxj.functions.UnaryConsumer;
import org.ticdev.toolboxj.functions.UnaryFunction;

import java.util.Objects;

/**
 * A self-based object wrapper. It inherits the hash code from its
 * wrapped object and delegates to the wrapped object for equality checks.
 *
 * @param <T> the type of the wrapped object.
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public final class Selfish<T> implements Self<Selfish<T>> {

  /**
   * The wrapped value.
   */
  private final T value;

  /**
   * Class constructor.
   *
   * @param wrappedValue the wrapped value.
   */
  public Selfish(final T wrappedValue) {
    this.value = wrappedValue;
  }

  @Override
  public Selfish<T> self() {
    return this;
  }

  /**
   * Returns the wrapped object.
   *
   * @return the wrapped object.
   */
  public T get() {
    return value;
  }

  /**
   * Creates a new instance of a Selfish.
   *
   * @param wrappedValue the wrappeed value.
   * @param <TNEW>       the type of the wrapped value.
   * @return the new Selfish wrapping the given value.
   */
  public static <TNEW> Selfish<TNEW> of(final TNEW wrappedValue) {
    return new Selfish<>(wrappedValue);
  }

  /**
   * Applies the given mapper to the wrapped value and return a
   * Selfish wrapping the result.
   *
   * @param mapper  the mapper.
   * @param <NEW_T> the type of the new wrapped value.
   * @return a new Selfish instance wrapping the resulting value.
   */
  public <NEW_T> Selfish<NEW_T> selfishMap(
      final UnaryFunction<? super T, ? extends NEW_T> mapper
  ) {
    return Selfish.of(mapper.apply(value));
  }

  /**
   * Passes the wrapped value to a consumer, then returns this instance.
   *
   * @param consumer the consumer.
   * @return this instance.
   */
  public Selfish<T> selfishConsume(
      final UnaryConsumer<? super T> consumer) {
    consumer.accept(value);
    return this;
  }

  /**
   * Cached hash code.
   */
  private transient int cachedHashCode;

  @Override
  public boolean equals(final Object o) {
    return this == o
        || o instanceof Selfish
        && Objects.equals(value, ((Selfish<?>) o).value);
  }

  @Override
  public int hashCode() {
    if (cachedHashCode == 0) {
      cachedHashCode = Objects.hash(value);
    }
    return cachedHashCode;
  }

}
