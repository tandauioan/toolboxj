package org.ticdev.toolboxj.numbers.bigrational;

import org.ticdev.toolboxj.numbers.Rational;
import org.ticdev.toolboxj.self.Self;
import org.ticdev.toolboxj.tuples.Pair;
import org.ticdev.toolboxj.tuples.PairView;

import java.math.BigInteger;

/**
 * Rational immutable generalization for a rational that uses
 * {@link BigInteger} for numerator and denominator.
 * <p>In most cases, a new rational instance is returned by every
 * method that returns a rational instance with the exception of specific
 * edge (+/- infinity) and sentinel/short-cut (zero, nan) cases.</p>
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public interface BigRational
    extends
    Rational<BigInteger, BigRational>,
    Self<BigRational> {

  @Override
  default BigRational self() {
    return this;
  }

  /**
   * BigRational types.
   */
  enum Type {
    /* regular rational */
    REGULAR,
    /* not a number */
    NAN,
    /* zero */
    ZERO,
    /* positive infinity */
    POSITIVE_INFINITY,
    /* negative infinity */
    NEGATIVE_INFINITY
  }

  /**
   * not-a-number instance
   */
  BigRational NAN = BigRationalNaN.getInstance();

  /**
   * zero instance
   */
  BigRational ZERO = BigRationalZero.getInstance();

  /**
   * positive infinity
   */
  BigRational POSITIVE_INFINITY =
      BigRationalPositiveInfinity.getInstance();

  /**
   * negative infinity
   */
  BigRational NEGATIVE_INFINITY =
      BigRationalNegativeInfinity.getInstance();

  /**
   * Returns the {@link Type} of the {@link BigRational}
   *
   * @return the {@link Type} of the {@link BigRational}
   */
  Type type();

  /**
   * Returns a rational with the given numerator and denominator.
   * <p>If either the numerator or denominator are null return
   * {@link BigRational#NAN}.</p>
   *
   * @param numerator   the numerator
   * @param denominator the denominator
   * @return the rational
   */
  static BigRational of(BigInteger numerator, BigInteger denominator) {
    if (numerator == null || denominator == null) {
      return BigRational.NAN;
    }
    return BigRationalRegular.of(numerator, denominator);
  }

  /**
   * Returns a rational from the given pair of {@link BigInteger}S.
   * <p>If the pair is null or any of its components are null return
   * {@link BigRational#NAN}.</p>
   *
   * @param pair the pair of big integers
   * @return a new rational
   */
  static BigRational of(Pair<BigInteger, BigInteger> pair) {
    return pair == null ? BigRational.NAN :
        BigRational.of(pair.item1(), pair.item2());
  }

  /**
   * Returns a rational with the given numerator and the denominator set to {@link BigInteger#ONE}.
   *
   * @param numerator the numerator.
   * @return the rational.
   */
  static BigRational of(BigInteger numerator) {
    return BigRationalRegular.of(numerator);
  }

  /**
   * Returns a rational with the numerator and denominator given as long values.
   *
   * @param numerator   the numerator
   * @param denominator the denominator
   * @return the rational.
   */
  static BigRational of(long numerator, long denominator) {
    return BigRational.of(BigInteger.valueOf(numerator),
        BigInteger.valueOf(denominator));
  }

  /**
   * Returns a rational with the numerator given as a long value and the denominator 1L.
   *
   * @param numerator the numerator
   * @return the rational.
   */
  static BigRational of(long numerator) {
    return BigRational.of(BigInteger.valueOf(numerator));
  }

  /**
   * Returns a mutable version of this rational.
   *
   * @return a mutable version of this rational.
   */
  default MutableBigRational newMutableCopy() {
    return new MutableBigRational(this);
  }

  /**
   * Copies this rational into the given mutable big rational
   *
   * @param destination the destination of the copy
   * @return this instance
   */
  default BigRational copyTo(MutableBigRational destination) {
    destination.set(numerator(), denominator());
    return this;
  }

  /**
   * See {@link MutableBigRational#MutableBigRational()}
   *
   * @return new mutable instance
   */
  static MutableBigRational mutableOfZero() {
    return new MutableBigRational();
  }

  /**
   * See {@link MutableBigRational#MutableBigRational(BigInteger, BigInteger)}
   *
   * @param numerator   numerator
   * @param denominator denominator
   * @return new mutable instance
   */
  static MutableBigRational mutableOf(
      BigInteger numerator, BigInteger denominator) {
    return new MutableBigRational(numerator, denominator);
  }

  /**
   * See {@link MutableBigRational#MutableBigRational(BigInteger)}
   *
   * @param numerator numerator
   * @return new mutable instance
   */
  static MutableBigRational mutableOf(BigInteger numerator) {
    return new MutableBigRational(numerator);
  }

  /**
   * See {@link MutableBigRational#MutableBigRational(PairView)}.
   *
   * @param pair the pair of big integers
   * @return new mutable instance
   */
  static MutableBigRational mutableOf(
      Pair<BigInteger, BigInteger> pair) {
    return new MutableBigRational(pair);
  }

  /**
   * See {@link MutableBigRational#MutableBigRational(long, long)}
   *
   * @param numerator   numerator
   * @param denominator denominator
   * @return new mutable instance
   */
  static MutableBigRational mutableOf(long numerator, long denominator) {
    return new MutableBigRational(numerator, denominator);
  }

  /**
   * See {@link MutableBigRational#MutableBigRational(long)}
   *
   * @param numerator numerator
   * @return new mutable instance
   */
  static MutableBigRational mutableOf(long numerator) {
    return new MutableBigRational(numerator);
  }

}
