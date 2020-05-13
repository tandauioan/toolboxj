package org.ticdev.toolboxj.numbers.bigrational;

import org.ticdev.toolboxj.numbers.Rational;
import org.ticdev.toolboxj.self.Self;
import org.ticdev.toolboxj.tuples.Pair;
import org.ticdev.toolboxj.tuples.PairView;
import org.ticdev.toolboxj.tuples.TupleSupport;

import java.math.BigInteger;
import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * MutableBigRational implementation.
 * <p>The numerator and denominator are allowed to change within the
 * same instance. The methods that perform changes on the rational do so
 * in place, and return this.</p>
 * <p>Constructors and setters that take as arguments either objects or
 * object containers as arguments set the value to not-a-number if the
 * objects, respectively object containers, are null.</p>
 * <p>Setting values is a two step process:</p>
 * <OL>
 * <LI>set numerator and denominator</LI>
 * <LI>normalize numerator and denominator (extract specific type
 * and maintain internal consistency)</LI>
 * </OL>
 * <p>Exposed methods always return normalized values. Multiple argument
 * operations may perform normalization only on the end-result to
 * improve speed.</p>
 * <p>A number of operations are implemented using stream arguments to
 * allow the calling side to control stream set up and parallelism rather
 * than offering a default, collection-based operation. Stream based
 * operations may provide a significant performance increase compared
 * to the sequential operations when parallel streams are used. However,
 * the performance increase depends on the size and number of rationals
 * being considered.</p>
 * <p>While similar to {@link BigRational} this class does not implement
 * that interface.</p>
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public final class MutableBigRational
    implements
    Rational<BigInteger, MutableBigRational>,
    Self<MutableBigRational> {

  /**
   * The numerator.
   */
  private BigInteger numerator;

  /**
   * The denominator.
   */
  private BigInteger denominator;

  /**
   * The type.
   */
  private BigRational.Type type;

  /**
   * Supplier of new instances of zero mutable rationals.
   */
  private static final Supplier<MutableBigRational> ZERO_SUPPLIER =
      MutableBigRational::new;

  /**
   * Supplier of new instances of one mutable rationals.
   */
  private static final Supplier<MutableBigRational> ONE_SUPPLIER =
      () -> new MutableBigRational(BigInteger.ONE);


  /**
   * Default constructor. Sets the rational to zero.
   */
  public MutableBigRational() {
    internalSet(BigInteger.ZERO, BigInteger.ONE).internalNormalize();
  }

  /**
   * Class constructor. Sets the rational to numerator/1.
   *
   * @param numeratorValue the numerator
   */
  public MutableBigRational(final BigInteger numeratorValue) {
    internalSet(numeratorValue, BigInteger.ONE).internalNormalize();
  }

  /**
   * Class constructor. Sets the rational to numerator/denominator.
   *
   * @param numeratorValue   the numerator
   * @param denominatorValue the denominator
   */
  public MutableBigRational(
      final BigInteger numeratorValue, final BigInteger denominatorValue) {
    internalSet(numeratorValue, denominatorValue).internalNormalize();
  }

  /**
   * Class constructor. Sets the rational to numerator/denominator
   * after converting the long values to BigInteger.
   *
   * @param numeratorValue   the numerator
   * @param denominatorValue the denominator
   */
  public MutableBigRational(
      final long numeratorValue,
      final long denominatorValue) {
    internalSet(BigInteger.valueOf(numeratorValue),
        BigInteger.valueOf(denominatorValue)).internalNormalize();
  }

  /**
   * Class constructor. Sets the rational to numerator/1
   * after converting the long value to BigInteger.
   *
   * @param numeratorValue the numerator
   */
  public MutableBigRational(final long numeratorValue) {
    internalSet(BigInteger.valueOf(numeratorValue), BigInteger.ONE)
        .internalNormalize();
  }

  /**
   * Class constructor. Creates a new instance from a pair of BigRational
   * numbers.
   *
   * @param pair the pair
   */
  public MutableBigRational(final PairView<BigInteger, BigInteger> pair) {
    internalSet(pair).internalNormalize();
  }

  /**
   * Internal set without normalization.
   *
   * @param numeratorValue   the numerator
   * @param denominatorValue the denominator
   * @return this instance
   */
  private MutableBigRational internalSet(
      final BigInteger numeratorValue,
      final BigInteger denominatorValue) {
    if (numeratorValue == null || denominatorValue == null) {
      this.numerator = BigInteger.ZERO;
      this.denominator = BigInteger.ZERO;
    }
    this.numerator = numeratorValue;
    this.denominator = denominatorValue;
    return this;
  }

  /**
   * Sets the numerator and denominator to the given values.
   *
   * @param numeratorValue   the numerator
   * @param denominatorValue the denominator.
   * @return this
   */
  public MutableBigRational set(
      final BigInteger numeratorValue,
      final BigInteger denominatorValue) {
    return internalSet(numeratorValue, denominatorValue).internalNormalize();
  }

  /**
   * Internal set without normalization.
   *
   * @param pair the pair of BigInteger values
   * @return this instance
   */
  private MutableBigRational internalSet(
      final PairView<BigInteger, BigInteger> pair) {
    return pair == null
        ? internalSet(null, null)
        : internalSet(pair.item1(), pair.item2());
  }

  /**
   * Sets the numerator and denominator to the given values.
   *
   * @param pair the pair containing the numerator and denominator,
   *             in this order.
   * @return this instance
   */
  public MutableBigRational set(final Pair<BigInteger, BigInteger> pair) {
    return internalSet(pair).internalNormalize();
  }

  /**
   * Internal non-normalized set to not-a-number.
   *
   * @return this instance
   */
  private MutableBigRational internalSetNan() {
    this.numerator = BigInteger.ZERO;
    this.denominator = BigInteger.ZERO;
    return this;
  }

  /**
   * Internal non-normalized set for numerator.
   *
   * @param numeratorValue the numerator
   * @return this instance
   */
  private MutableBigRational internalSet(final BigInteger numeratorValue) {
    if (numeratorValue == null) {
      return internalSetNan();
    }
    this.numerator = numeratorValue;
    return this;
  }

  /**
   * Sets the numerator.
   *
   * @param numeratorValue the numerator
   * @return this instance
   */
  public MutableBigRational set(final BigInteger numeratorValue) {
    return internalSet(numeratorValue).internalNormalize();
  }

  /**
   * Sets the numerator.
   *
   * @param numeratorValue the numerator
   * @return this instance
   */
  public MutableBigRational set(final long numeratorValue) {
    return set(BigInteger.valueOf(numeratorValue));
  }

  /**
   * Sets the numerator.
   *
   * @param numeratorValue the numerator
   * @return this instance
   */
  public MutableBigRational numerator(final BigInteger numeratorValue) {
    return internalSet(numeratorValue).internalNormalize();
  }

  /**
   * Sets the numerator.
   *
   * @param numeratorValue the numerator
   * @return this instance
   */
  public MutableBigRational numerator(final long numeratorValue) {
    return set(numeratorValue);
  }

  /**
   * Internal set denominator, not normalized.
   *
   * @param denominatorValue the denominator
   * @return this instance
   */
  private MutableBigRational internalSetDenominator(
      final BigInteger denominatorValue) {
    if (denominatorValue == null) {
      return internalSetNan();
    }
    this.denominator = denominatorValue;
    return this;
  }

  /**
   * Sets the denominator.
   *
   * @param denominatorValue the denominator
   * @return this instance
   */
  public MutableBigRational denominator(
      final BigInteger denominatorValue) {
    return internalSetDenominator(denominatorValue).internalNormalize();
  }

  /**
   * Sets the denominator.
   *
   * @param denominatorValue the denominator
   * @return this instance
   */
  public MutableBigRational denominator(final long denominatorValue) {
    return denominator(BigInteger.valueOf(denominatorValue));
  }

  /**
   * Internal normalization process.
   *
   * @return this instance
   */
  private MutableBigRational internalNormalize() {
    if (numerator.equals(BigInteger.ZERO)) {
      if (denominator.equals(BigInteger.ZERO)) {
        type = BigRational.Type.NAN;
      } else {
        type = BigRational.Type.ZERO;
        denominator = BigInteger.ONE;
      }
    } else {
      if (denominator.equals(BigInteger.ZERO)) {
        if (numerator.signum() < 0) {
          type = BigRational.Type.NEGATIVE_INFINITY;
          numerator =
              BigRationalNegativeInfinity.BIGINTEGER_MINUSONE;
        } else {
          type = BigRational.Type.POSITIVE_INFINITY;
          numerator = BigInteger.ONE;
        }
      } else {
        type = BigRational.Type.REGULAR;
        if (denominator.signum() < 0) {
          numerator = numerator.negate();
          denominator = denominator.negate();
        }
      }
    }
    return this;
  }

  @Override
  public boolean isNaN() {
    return type == BigRational.Type.NAN;
  }

  @Override
  public boolean isZero() {
    return type == BigRational.Type.ZERO;
  }

  @Override
  public boolean isPositiveInfinity() {
    return type == BigRational.Type.POSITIVE_INFINITY;
  }

  @Override
  public boolean isNegativeInfinity() {
    return type == BigRational.Type.NEGATIVE_INFINITY;
  }

  @Override
  public MutableBigRational add(
      final MutableBigRational mutableBigRational) {
    return addNotNormalized(mutableBigRational).internalNormalize();
  }

  @Override
  public MutableBigRational subtract(
      final MutableBigRational mutableBigRational) {
    return subtractNotNormalized(mutableBigRational)
        .internalNormalize();
  }

  @Override
  public MutableBigRational multiply(
      final MutableBigRational mutableBigRational) {
    return multiplyNotNormalized(mutableBigRational)
        .internalNormalize();
  }

  @Override
  public MutableBigRational divide(
      final MutableBigRational mutableBigRational) {
    return divideNotNormalized(mutableBigRational).internalNormalize();
  }


  @Override
  public MutableBigRational negate() {
    numerator = numerator.negate();
    return internalNormalize();
  }

  @Override
  public MutableBigRational reciprocal() {
    return internalSet(denominator, numerator).internalNormalize();
  }

  @Override
  public MutableBigRational abs() {
    return numerator.signum() < 0
        ? internalSet(numerator.negate()).internalNormalize()
        : this;
  }

  @Override
  public MutableBigRational reduce() {
    BigInteger gcd = numerator.gcd(denominator);
    if (gcd.equals(BigInteger.ZERO) || gcd.equals(BigInteger.ONE)) {
      return this;
    }
    return internalSet(numerator.divide(gcd), denominator.divide(gcd))
        .internalNormalize();
  }

  @Override
  public BigInteger numerator() {
    return numerator;
  }

  @Override
  public BigInteger denominator() {
    return denominator;
  }

  @Override
  public int signum() {
    return numerator.signum();
  }

  @Override
  public int compareTo(final MutableBigRational o) {
    switch (type) {
      case NAN:
        return o.type == BigRational.Type.NEGATIVE_INFINITY
            ? 0
            : 1;
      case ZERO:
        if (o.isNaN()) {
          return -1;
        }
        if (o.isZero()) {
          return 0;
        }
        return -o.numerator.signum();
      case NEGATIVE_INFINITY:
        return o.type == BigRational.Type.NEGATIVE_INFINITY
            ? 0
            : -1;
      case POSITIVE_INFINITY:
        if (o.type == BigRational.Type.POSITIVE_INFINITY) {
          return 0;
        }
        if (o.type == BigRational.Type.NAN) {
          return -1;
        }
        return 1;
      default: /* regular */
        if (o.type == BigRational.Type.NEGATIVE_INFINITY) {
          return 1;
        }
        if (o.type == BigRational.Type.POSITIVE_INFINITY
            || o.type == BigRational.Type.NAN) {
          return -1;
        }
        return numerator.multiply(o.denominator).compareTo(
            denominator.multiply(o.numerator()));
    }
  }

  @Override
  public String toString() {
    return numerator + "/" + denominator;
  }

  @Override
  public int hashCode() {
    return TupleSupport.hashCode(numerator, denominator);
  }

  @Override
  public boolean equals(final Object obj) {
    return this == obj
        || (obj instanceof PairView<?, ?>)
        && TupleSupport.pairEquals(this, (PairView<?, ?>) obj);
  }

  /**
   * Returns the type of BigRational to which this rational is equivalent.
   *
   * @return the type of BigRational to which this rational is equivalent.
   */
  public BigRational.Type type() {
    return type;
  }

  /**
   * Adds a rational to this rational without normalization.
   *
   * @param rational the rational to add
   * @return this instance
   */
  private MutableBigRational addNotNormalized(
      final Rational<BigInteger, ?> rational) {
    return internalSet(numerator.multiply(rational.denominator())
            .add(denominator.multiply(
                rational.numerator())),
        denominator.multiply(rational.denominator()));
  }

  /**
   * Adds the given rational to this rational.
   *
   * @param rational the rational to add
   * @return this instance
   */
  public MutableBigRational addRational(
      final Rational<BigInteger, ?> rational) {
    return addNotNormalized(rational).internalNormalize();
  }

  /**
   * Adds a sequence of rationals to this rational.
   *
   * @param rationals the rationals to add
   * @return this instance
   */
  @SafeVarargs
  public final MutableBigRational addRationals(
      final Rational<BigInteger, ?>... rationals) {
    for (Rational<BigInteger, ?> rational : rationals) {
      addNotNormalized(rational);
    }
    return internalNormalize();
  }

  /**
   * Adds a collection of rationals to this rational.
   *
   * @param rationals the rationals to add
   * @return this instance
   */
  public MutableBigRational addRationals(
      final Collection<? extends Rational<BigInteger, ?>> rationals) {
    rationals.forEach(this::addNotNormalized);
    return internalNormalize();
  }

  /**
   * Subtracts a rational from this rational without normalization.
   *
   * @param rational the rational to subtract
   * @return this instance
   */
  private MutableBigRational subtractNotNormalized(
      final Rational<BigInteger, ?> rational) {
    return internalSet(numerator.multiply(rational.denominator())
            .subtract(denominator
                .multiply(
                    rational.numerator())),
        denominator.multiply(rational.denominator()));
  }

  /**
   * Subtracts the given rational from this rational.
   *
   * @param rational the rational to subtract
   * @return this instance
   */
  public MutableBigRational subtractRational(
      final Rational<BigInteger, ?> rational) {
    return subtractNotNormalized(rational).internalNormalize();
  }

  /**
   * Subtracts the given sequence of rationals from this rational.
   *
   * @param rationals the rationals to subtract
   * @return this instance
   */
  @SafeVarargs
  public final MutableBigRational subtractRationals(
      final Rational<BigInteger, ?>... rationals) {
    for (Rational<BigInteger, ?> rational : rationals) {
      subtractNotNormalized(rational);
    }
    return internalNormalize();
  }

  /**
   * Subtracts the given rationals from this rational.
   *
   * @param rationals the rationals to subtract
   * @return this instance
   */
  public MutableBigRational subtractRationals(
      final Collection<? extends Rational<BigInteger, ?>> rationals) {
    rationals.forEach(this::subtractNotNormalized);
    return internalNormalize();
  }

  /**
   * Multiplies this rational by the given rational without normalization.
   *
   * @param rational the rational to multiply by.
   * @return this instance
   */
  private MutableBigRational multiplyNotNormalized(
      final Rational<BigInteger, ?> rational) {
    return internalSet(numerator.multiply(rational.numerator()),
        denominator.multiply(rational.denominator()));
  }

  /**
   * Multiplies this rational by the given rational.
   *
   * @param rational the rational to multiply by
   * @return this instance
   */
  public MutableBigRational multiplyRational(
      final Rational<BigInteger, ?> rational) {
    return multiplyNotNormalized(rational).internalNormalize();
  }

  /**
   * Multiplies this rational by the given sequence of rationals.
   *
   * @param rationals the rationals to multiply by
   * @return this instance
   */
  @SafeVarargs
  public final MutableBigRational multiplyRationals(
      final Rational<BigInteger, ?>... rationals) {
    for (Rational<BigInteger, ?> rational : rationals) {
      multiplyNotNormalized(rational);
    }
    return internalNormalize();
  }

  /**
   * Multiplies this rational by the given rationals.
   *
   * @param rationals the rationals to multiply by
   * @return this instance
   */
  public MutableBigRational multiplyRationals(
      final Collection<? extends Rational<BigInteger, ?>> rationals) {
    rationals.forEach(this::multiplyNotNormalized);
    return internalNormalize();
  }

  /**
   * Divides this rational by the given rational without normalization.
   *
   * @param rational the rational to divide by
   * @return this instance
   */
  private MutableBigRational divideNotNormalized(
      final Rational<BigInteger, ?> rational) {
    return internalSet(numerator.multiply(rational.denominator()),
        denominator.multiply(rational.numerator()));
  }

  /**
   * Divides this rational by the given rational.
   *
   * @param rational the rational to divide by
   * @return this instance
   */
  private MutableBigRational divideRational(
      final Rational<BigInteger, ?> rational) {
    return divideNotNormalized(rational).internalNormalize();
  }

  /**
   * Divides this rational by the given sequence of rationals.
   *
   * @param rationals the rationals to divide by
   * @return this instance
   */
  @SafeVarargs
  public final MutableBigRational divideRationals(
      final Rational<BigInteger, ?>... rationals) {
    for (Rational<BigInteger, ?> rational : rationals) {
      divideNotNormalized(rational);
    }
    return internalNormalize();
  }

  /**
   * Divides this rational by the given rationals.
   *
   * @param rationals the rationals to divide by
   * @return this instance
   */
  public MutableBigRational divideRationals(
      final Collection<? extends Rational<BigInteger, ?>> rationals) {
    rationals.forEach(this::divideNotNormalized);
    return internalNormalize();
  }

  /**
   * Adds all the rationals in the given stream to this rational.
   *
   * @param stream the stream of rationals
   * @return this instance
   */
  public MutableBigRational addRationals(
      final Stream<? extends Rational<BigInteger, ?>> stream) {
    return add(stream.collect(ZERO_SUPPLIER,
        MutableBigRational::addNotNormalized,
        MutableBigRational::addNotNormalized));
  }


  /**
   * Subtracts all the rationals in the given stream from this rational.
   *
   * @param stream the stream of rationals
   * @return this instance
   */
  public MutableBigRational subtractRationals(
      final Stream<? extends Rational<BigInteger, ?>> stream) {
    return subtract(stream.collect(ZERO_SUPPLIER,
        MutableBigRational::addNotNormalized,
        MutableBigRational::addNotNormalized));
  }

  /**
   * Multiplies this rational with all the rationals in the given stream.
   *
   * @param stream the stream of rationals
   * @return this instance
   */
  public MutableBigRational multiplyRationals(
      final Stream<? extends Rational<BigInteger, ?>> stream) {
    return multiply(stream.collect(ONE_SUPPLIER,
        MutableBigRational::multiplyNotNormalized,
        MutableBigRational::multiplyNotNormalized));
  }

  /**
   * Divides this rational by all the rationals in the given stream.
   *
   * @param stream the stream of rationals
   * @return this instance
   */
  public MutableBigRational divideRationals(
      final Stream<? extends Rational<BigInteger, ?>> stream) {
    return divide(stream.collect(ONE_SUPPLIER,
        MutableBigRational::multiplyNotNormalized,
        MutableBigRational::multiplyNotNormalized));
  }

  @Override
  public MutableBigRational self() {
    return this;
  }
}
