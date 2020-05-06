package org.ticdev.toolboxj.tuples;

import org.junit.Assert;
import org.junit.Test;
import org.ticdev.toolboxj.functions.UnaryFunction;
import org.ticdev.toolboxj.functions.UnaryFunctionWithThrowable;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * Test class for {@link Either}.
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public class EitherTest {

  private static final String leftValueString = "left value";
  private static final String rightValueString = "right value";
  private static final Integer leftValueInt = 11;
  private static final Integer rightValueInt = 12;

  final Either<String, String> eLeft = Either.left(leftValueString);
  final Either<String, String> eRight = Either.right(rightValueString);


  @Test
  public void testConstruction() {
    final Either<String, Integer> left = Either.left(leftValueString);
    Assert.assertTrue(left.isLeft());
    Assert.assertFalse(left.isRight());
    Assert.assertEquals(leftValueString, left.getLeft());
    Assert.assertThrows(
        IllegalStateException.class,
        left::getRight
    );
    final Either<String, Integer> right = Either.right(rightValueInt);
    Assert.assertTrue(right.isRight());
    Assert.assertFalse(right.isLeft());
    Assert.assertEquals(rightValueInt, right.getRight());
    Assert.assertThrows(
        IllegalStateException.class,
        right::getLeft
    );
  }

  @Test
  public void testEquals() {
    final Either<Integer, Integer> right = Either.right(rightValueInt);
    final Either<Integer, Integer> left = Either.left(leftValueInt);
    final Either<Integer, Integer> unequal = Either.left(rightValueInt);
    final Either<Integer, Integer> enull = Either.right(null);
    Assert.assertEquals(right, right);
    Assert.assertEquals(left, left);
    Assert.assertEquals(unequal, unequal);
    Assert.assertEquals(enull, enull);
    Assert.assertEquals(right, Either.<Integer, Integer>right(right.getRight()));
    Assert.assertEquals(left, Either.<Integer, Integer>left(left.getLeft()));
    Assert.assertEquals(unequal, Either.<Integer, Integer>left(unequal.getLeft()));
    final List<Either<?, ?>> testValues = Arrays.asList(right, left, unequal, null);
    final List<Pair<?, ?>> unequalRefs =
        testValues
            .stream()
            .flatMap(e1 -> testValues.stream().map(e2 -> TupleSupport.of(e1, e2)))
            .filter(p -> p.item1() != p.item2())
            .collect(Collectors.toList());
    Assert.assertFalse(unequalRefs.isEmpty());
    Assert.assertTrue(
        unequalRefs.stream()
            .noneMatch(p -> Objects.equals(p.item1(), p.item2()))
    );

  }

  @Test
  public void fold() {
    Assert.assertTrue(eLeft.fold(l -> true, r -> false));
    Assert.assertTrue(eRight.fold(l -> false, r -> true));
  }

  @Test
  public void spread() {
    Assert.assertTrue(eLeft.spread(e -> true, e -> false));
    Assert.assertTrue(eRight.fold(e -> false, e -> true));
  }

  @Test
  public void consume() {
    final AtomicBoolean sideEffect = new AtomicBoolean(false);
    eLeft.consume(l -> sideEffect.set(true), r -> sideEffect.set(false));
    Assert.assertTrue(sideEffect.get());
    sideEffect.set(false);
    eRight.consume(l -> sideEffect.set(false), r -> sideEffect.set(true));
    Assert.assertTrue(sideEffect.get());
  }

  @Test
  public void isLeft() {
    Assert.assertTrue(eLeft.isLeft());
    Assert.assertFalse(eRight.isLeft());
  }

  @Test
  public void isRight() {
    Assert.assertFalse(eLeft.isRight());
    Assert.assertTrue(eRight.isRight());
  }

  @Test
  public void getLeft() {
    Assert.assertEquals(leftValueString, eLeft.getLeft());
    Assert.assertThrows(IllegalStateException.class, eRight::getLeft);
  }

  @Test
  public void getRight() {
    Assert.assertThrows(IllegalStateException.class, eLeft::getRight);
    Assert.assertEquals(rightValueString, eRight.getRight());
  }

  @Test
  public void optionalLeft() {
    Assert.assertEquals(leftValueString, eLeft.optionalLeft().orElse(null));
    Assert.assertFalse(eRight.optionalLeft().isPresent());
  }

  @Test
  public void optionalRight() {
    Assert.assertFalse(eLeft.optionalRight().isPresent());
    Assert.assertEquals(rightValueString, eRight.optionalRight().orElse(null));
  }

  @Test
  public void foldRight() {
    Assert.assertEquals(leftValueString, eLeft.foldRight(UnaryFunction.identity()));
    Assert.assertEquals(leftValueString, eRight.foldRight(s -> leftValueString));
  }

  @Test
  public void foldLeft() {
    Assert.assertEquals(rightValueString, eLeft.foldLeft(s -> rightValueString));
    Assert.assertEquals(rightValueString, eRight.foldLeft(UnaryFunction.identity()));
  }

  @Test
  public void flatMap() {
    Assert.assertEquals(
        leftValueString.length(),
        eLeft
            .flatMap(
                l -> Either.left(l.length()),
                r -> Either.right(r.length()))
            .optionalLeft().orElse(-1).intValue()
    );
    Assert.assertEquals(
        rightValueString.length(),
        eRight
            .flatMap(
                l -> Either.left(l.length()),
                r -> Either.right(r.length()))
            .optionalRight().orElse(-1).intValue()
    );
  }

  @Test
  public void map() {
    Assert.assertEquals(
        leftValueString.length(),
        eLeft.map(String::length, String::length)
            .optionalLeft().orElse(-1).intValue()
    );
    Assert.assertEquals(
        rightValueString.length(),
        eRight.map(String::length, String::length)
            .optionalRight().orElse(-1).intValue()
    );
  }

  @Test
  public void flatMapRight() {
    Assert.assertTrue(
        eLeft.flatMapRight(s -> Either.right(s.length())).isLeft());
    Assert.assertEquals(
        rightValueString.length(),
        eRight.flatMapRight(s -> Either.right(s.length()))
            .optionalRight().orElse(-1).intValue()
    );
  }

  @Test
  public void mapRight() {
    Assert.assertTrue(eLeft.mapRight(String::length).isLeft());
    Assert.assertEquals(
        rightValueString.length(),
        eRight.mapRight(String::length)
            .optionalRight().orElse(-1).intValue()
    );
  }

  @Test
  public void flatMapLeft() {
    Assert.assertEquals(
        leftValueString.length(),
        eLeft.flatMapLeft(s -> Either.left(s.length()))
            .optionalLeft().orElse(-1).intValue()
    );
    Assert.assertTrue(
        eRight.flatMapLeft(s -> Either.left(s.length())).isRight());
  }

  @Test
  public void mapLeft() {
    Assert.assertEquals(
        leftValueString.length(),
        eLeft.mapLeft(String::length)
            .optionalLeft().orElse(-1).intValue()
    );
    Assert.assertTrue(eRight.mapLeft(String::length).isRight());
  }

  interface ArithEx<A, R> extends UnaryFunctionWithThrowable<A, R> {
    R apply(A arg) throws ArithmeticException;
  }

  @Test
  public void mapLeftThrowing() {
    Either<Integer, String> eResult = eLeft.mapLeftThrowing(
        String::length,
        (f, a) -> {
          try {
            return Either.left(f.apply(a));
          } catch (Throwable th) {
            return Either.right(th.toString());
          }
        }
    );
    Assert.assertEquals(
        leftValueString.length(),
        eResult.optionalLeft().orElse(-1).intValue());
    Either<Integer, String> eResultRight = eRight.mapLeftThrowing(
        String::length,
        (f, a) -> {
          try {
            return Either.left(f.apply(a));
          } catch (Throwable th) {
            return Either.right(th.toString());
          }
        }
    );
    Assert.assertTrue(eResultRight.isRight());
    final int zero = 0;
    Either<Integer, ArithmeticException> exRight =
        eLeft.mapRight(ArithmeticException::new)
            .mapLeftThrowing(
                (ArithEx<String, Integer>) s -> s.length() / zero,
                (f, a) -> {
                  try {
                    return Either.left(f.apply(a));
                  } catch (ArithmeticException ex) {
                    return Either.right(ex);
                  }
                }
            );
    Assert.assertTrue(exRight.isRight());
  }

  @Test
  public void mapRightThrowing() {
    Either<String, Integer> eResult = eRight.mapRightThrowing(
        String::length,
        (f, a) -> {
          try {
            return Either.right(f.apply(a));
          } catch (Throwable th) {
            return Either.left(th.toString());
          }
        }
    );
    Assert.assertEquals(
        rightValueString.length(),
        eResult.optionalRight().orElse(-1).intValue()
    );
    Either<String, Integer> eResultLeft = eLeft.mapRightThrowing(
        String::length,
        (f, a) -> {
          try {
            return Either.right(f.apply(a));
          } catch (Throwable th) {
            return Either.left(th.toString());
          }
        }
    );
    Assert.assertTrue(eResultLeft.isLeft());
    final int zero = 0;
    Either<ArithmeticException, Integer> exLeft =
        eRight.mapLeft(ArithmeticException::new)
        .mapRightThrowing(
            (ArithEx<String, Integer>) s -> s.length() / zero,
            (f, a) -> {
              try {
                return Either.right(f.apply(a));
              } catch(ArithmeticException ex) {
                return Either.left(ex);
              }
            }
        );
    Assert.assertTrue(exLeft.isLeft());
  }

}