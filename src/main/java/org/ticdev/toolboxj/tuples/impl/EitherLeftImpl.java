package org.ticdev.toolboxj.tuples.impl;

import org.ticdev.toolboxj.functions.UnaryFunction;
import org.ticdev.toolboxj.tuples.Either;

import java.util.Objects;

/**
 * Concrete implementation of a left-side {@link Either}.
 *
 * @param <LEFT>  the type of the left side value.
 * @param <RIGHT> the type of the right side value.
 */
public final class EitherLeftImpl<LEFT, RIGHT> implements Either<LEFT, RIGHT> {

  /**
   * The value.
   */
  private final LEFT left;

  /**
   * Protected constructor.
   *
   * @param leftValue the value.
   */
  public EitherLeftImpl(final LEFT leftValue) {
    this.left = leftValue;
  }

  @Override
  public Either<LEFT, RIGHT> self() {
    return this;
  }

  @Override
  public <R> R fold(
      final UnaryFunction<LEFT, R> leftMapper,
      final UnaryFunction<RIGHT, R> rightMapper) {
    return leftMapper.apply(left);
  }

  /**
   * Cached hash code.
   */
  private volatile int hashCode;

  @Override
  public int hashCode() {
    if (hashCode == 0) {
      hashCode = Objects.hash(left);
    }
    return hashCode;
  }

  @Override
  public boolean equals(final Object o) {
    return this == o
        || o instanceof Either
        && ((Either<?, ?>) o).fold(
        otherLeft -> Objects.equals(left, otherLeft),
        otherRight -> false
    );
  }

  @Override
  public String toString() {
    return "EitherLeftImpl(" + left + ")";
  }

}
