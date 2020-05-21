package org.ticdev.toolboxj.tuples;

import org.ticdev.toolboxj.functions.UnaryConsumer;
import org.ticdev.toolboxj.functions.UnaryFunction;
import org.ticdev.toolboxj.self.Self;
import org.ticdev.toolboxj.tuples.impl.EitherLeftImpl;
import org.ticdev.toolboxj.tuples.impl.EitherRightImpl;

import java.util.Optional;

/**
 * A pair that can contain either the left, or the right value,
 * but not both or none.
 *
 * @param <LEFT>  the type of the left value.
 * @param <RIGHT> the type of the right value.
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public interface Either<LEFT, RIGHT>
    extends Self<Either<LEFT, RIGHT>> {

  /**
   * Uses the appropriate mapper to transform this instance into a
   * different value.
   *
   * @param leftMapper  the mapper applying if the left side is present.
   * @param rightMapper the mapper applying if the right side is present.
   * @param <R>         the type of the result.
   * @return the result of applying the appropriate mapper to the existing
   *     value.
   */
  <R> R fold(
      UnaryFunction<LEFT, R> leftMapper,
      UnaryFunction<RIGHT, R> rightMapper
  );

  /**
   * Reverses the position of the right and left choices and returns
   * the reverted either.
   *
   * <p>
   * If this is a right, the new instance will be a left. If this is
   * a left, the new instance will be a right.
   * </p>
   *
   * @return a new instance of an Either with the positions reversed.
   */
  default Either<RIGHT, LEFT> swap() {
    return fold(Either::right, Either::left);
  }

  /**
   * Similar to {@link #fold(UnaryFunction, UnaryFunction)} except that
   * it passes the entire either to the appropriate mapper.
   *
   * @param leftMapper  the mapper applying if the left side is present.
   * @param rightMapper the mapper applying if the right side is present.
   * @param <R>         the type of the result.
   * @return the result of applying the appropriate mapper to this either.
   */
  default <R> R spread(
      UnaryFunction<Either<LEFT, RIGHT>, R> leftMapper,
      UnaryFunction<Either<LEFT, RIGHT>, R> rightMapper
  ) {
    return fold(l -> leftMapper, r -> rightMapper).apply(this);
  }

  /**
   * Similar to {@link #fold} extends that it does not return a value.
   *
   * @param leftConsumer  the consumer applying if the left side is present.
   * @param rightConsumer the consumer applying if the right side is present.
   */
  default void consume(
      UnaryConsumer<LEFT> leftConsumer,
      UnaryConsumer<RIGHT> rightConsumer) {
    fold(
        left -> {
          leftConsumer.accept(left);
          return null;
        },
        right -> {
          rightConsumer.accept(right);
          return null;
        }
    );
  }

  /**
   * Returns true if the left side is present, and false otherwise.
   *
   * @return true if the left side is present, and false otherwise.
   */
  default boolean isLeft() {
    return fold(left -> true, right -> false);
  }

  /**
   * Returns true if the right side is present, and false otherwise.
   *
   * @return true if the right side is present, and false otherwise.
   */
  default boolean isRight() {
    return !isLeft();
  }

  /**
   * Attempts to return the left side, throwing an exception if the left
   * side is not present.
   *
   * @return the value of the left side.
   * @throws IllegalStateException if the left side is not present.
   */
  default LEFT getLeft() throws IllegalStateException {
    return fold(left -> left, right -> {
      throw new IllegalStateException("It is right.");
    });
  }

  /**
   * Attempts to return the right side, throwing an exception if the right
   * side is not present.
   *
   * @return the value of the right side.
   * @throws IllegalStateException if the right side is not present.
   */
  default RIGHT getRight() throws IllegalStateException {
    return fold(left -> {
          throw new IllegalStateException("It is left.");
        },
        right -> right
    );
  }

  /**
   * Like {@link #getLeft()}, but returns an {@link Optional} containing
   * the left side value if this is a left side either, and empty, otherwise.
   *
   * @return the optional left side value.
   */
  default Optional<LEFT> optionalLeft() {
    return fold(Optional::ofNullable, right -> Optional.empty());
  }

  /**
   * Like {@link #getRight()}, but returns an {@link Optional} containing
   * the right side value if this is a right side either, and empty, otherwise.
   *
   * @return the optional right side value.
   */
  default Optional<RIGHT> optionalRight() {
    return fold(left -> Optional.empty(), Optional::ofNullable);
  }

  /**
   * Folds the right side returning a left side type result,
   * by either returning the left side if this is a left side either,
   * or by applying the mapper to the right side to convert it to the
   * left typed value.
   *
   * @param rightMapper the right side mapper.
   * @return a left typed value resulting from folding on the right side.
   */
  default LEFT foldRight(UnaryFunction<RIGHT, LEFT> rightMapper) {
    return fold(left -> left, rightMapper);
  }

  /**
   * Folds the left side returning a right side type result,
   * by either returning the right side if this is a right side either,
   * or by applying the mapper to the left sife to convert it to the right
   * typed value.
   *
   * @param leftMapper the left side mapper.
   * @return a right typed value resulting from folding on the left side.
   */
  default RIGHT foldLeft(UnaryFunction<LEFT, RIGHT> leftMapper) {
    return fold(leftMapper, right -> right);
  }

  /**
   * Applies the appropriate mapping function based on the side of this
   * either.
   *
   * @param leftMapper  converts the left side into the new either type.
   * @param rightMapper converts the right side into the new either type.
   * @param <NEW_LEFT>  the new left side type for the either.
   * @param <NEW_RIGHT> the new right side type for the either.
   * @return an either resulting from applying the appropriate mapper.
   */
  default <NEW_LEFT, NEW_RIGHT> Either<NEW_LEFT, NEW_RIGHT> flatMap(
      UnaryFunction<LEFT, Either<NEW_LEFT, NEW_RIGHT>> leftMapper,
      UnaryFunction<RIGHT, Either<NEW_LEFT, NEW_RIGHT>> rightMapper
  ) {
    return fold(leftMapper, rightMapper);
  }

  /**
   * Like {@link #flatMap} but the mapping function returns the appropriate
   * side rather than an either.
   *
   * @param leftMapper  converts the left side into the new left side.
   * @param rightMapper converts the right side into the new right side.
   * @param <NEW_LEFT>  the new left side type for the either.
   * @param <NEW_RIGHT> the new right side type for the either.
   * @return an either resulting from applying the appropriate mapper.
   */
  default <NEW_LEFT, NEW_RIGHT> Either<NEW_LEFT, NEW_RIGHT> map(
      UnaryFunction<LEFT, NEW_LEFT> leftMapper,
      UnaryFunction<RIGHT, NEW_RIGHT> rightMapper
  ) {
    return fold(
        left -> left(leftMapper.apply(left)),
        right -> right(rightMapper.apply(right))
    );
  }

  /**
   * Like {@link #flatMap(UnaryFunction, UnaryFunction)} but only applies
   * a mapper to the right side, leaving the left side unchanged.
   *
   * @param mapper      the mapper to apply if this is a right side either.
   * @param <NEW_RIGHT> the new right side type for the either.
   * @return an either resulting from flat mapping right.
   */
  default <NEW_RIGHT> Either<LEFT, NEW_RIGHT> flatMapRight(
      UnaryFunction<RIGHT, Either<LEFT, NEW_RIGHT>> mapper) {
    return fold(Either::left, mapper);
  }

  /**
   * Like {@link #flatMapRight(UnaryFunction)} but only applies to the
   * right side value, if present, returning a result matching the new type
   * for the right side, instead of an either.
   *
   * @param mapper      the mapper to apply if this is a right side either.
   * @param <NEW_RIGHT> the new right side type for the either.
   * @return an either resulting from mapping right.
   */
  default <NEW_RIGHT> Either<LEFT, NEW_RIGHT> mapRight(
      UnaryFunction<RIGHT, NEW_RIGHT> mapper
  ) {
    return fold(Either::left, right -> right(mapper.apply(right)));
  }

  /**
   * Like {@link #flatMap(UnaryFunction, UnaryFunction)} but only applies
   * a mapper to the left side, leaving the right side unchanged.
   *
   * @param mapper     the mapper to apply if this is a left side either.
   * @param <NEW_LEFT> the new left side type for the either.
   * @return an either resulting from flat mapping left.
   */
  default <NEW_LEFT> Either<NEW_LEFT, RIGHT> flatMapLeft(
      UnaryFunction<LEFT, Either<NEW_LEFT, RIGHT>> mapper) {
    return fold(mapper, Either::right);
  }

  /**
   * Like {@link #flatMapLeft(UnaryFunction)} but only applies to the
   * left side value, if present, returning a result matching the new type
   * for the left side, instead of an either.
   *
   * @param mapper     the mapper to apply if this is a left side either.
   * @param <NEW_LEFT> the new left side type for either.
   * @return an either resulting from mapping left.
   */
  default <NEW_LEFT> Either<NEW_LEFT, RIGHT> mapLeft(
      UnaryFunction<LEFT, NEW_LEFT> mapper
  ) {
    return fold(left -> left(mapper.apply(left)), Either::right);
  }

  /**
   * Creates a new instance of a right-side either.
   *
   * @param value   the right side value.
   * @param <LEFT>  the type of the left side value.
   * @param <RIGHT> the type of the right side value.
   * @return the new right-side either.
   */
  static <LEFT, RIGHT> Either<LEFT, RIGHT> right(final RIGHT value) {
    return new EitherRightImpl<>(value);
  }

  /**
   * Creates a new instance of a left-side either.
   *
   * @param value   the left side value.
   * @param <LEFT>  the type of the left side value.
   * @param <RIGHT> the type of the right side value.
   * @return the new left-side either.
   */
  static <LEFT, RIGHT> Either<LEFT, RIGHT> left(final LEFT value) {
    return new EitherLeftImpl<>(value);
  }

}

