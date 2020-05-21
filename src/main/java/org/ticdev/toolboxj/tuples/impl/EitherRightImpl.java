package org.ticdev.toolboxj.tuples.impl;

import org.ticdev.toolboxj.functions.UnaryFunction;
import org.ticdev.toolboxj.tuples.Either;

import java.util.Objects;

/**
 * Concrete implementation of a right-side {@link Either}.
 *
 * @param <LEFT>  the type of the left side value.
 * @param <RIGHT> the type of the right side value.
 */
public final class EitherRightImpl<LEFT, RIGHT> implements Either<LEFT, RIGHT> {

  /**
   * The value.
   */
  private final RIGHT right;

  /**
   * Protected constructor.
   *
   * @param rightValue the value.
   */
  public EitherRightImpl(final RIGHT rightValue) {
    this.right = rightValue;
  }

  /**
   * Cached hash code.
   */
  private transient int hashCode;

  @Override
  public Either<LEFT, RIGHT> self() {
    return this;
  }

  @Override
  public <R> R fold(
      final UnaryFunction<LEFT, R> leftMapper,
      final UnaryFunction<RIGHT, R> rightMapper) {
    return rightMapper.apply(right);
  }

  @Override
  public int hashCode() {
    if (hashCode == 0) {
      hashCode = Objects.hash(right);
    }
    return hashCode;
  }

  @Override
  public boolean equals(final Object o) {
    return this == o
        || o instanceof Either
        && ((Either<?, ?>) o).fold(
        otherLeft -> false,
        otherRight -> Objects.equals(right, otherRight)
    );
  }

  @Override
  public String toString() {
    return "EitherRightImpl(" + right + ")";
  }
}
