package org.ticdev.toolboxj.numbers.bigrational;

import org.ticdev.toolboxj.tuples.PairView;
import org.ticdev.toolboxj.tuples.TupleSupport;

import java.math.BigInteger;

/**
 * Regular concrete BigRational implementation.
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public final class BigRationalRegular
    implements BigRational {

  /**
   * Numerator.
   */
  private final BigInteger numerator;

  /**
   * Denominator.
   */
  private final BigInteger denominator;

  /**
   * Signum.
   */
  private final int signum;

  /**
   * Cached hash code.
   */
  private int cachedHashCode;

  /**
   * Private constructor. Due to special instances, BigRational
   * instantiation happens via factory methods.
   *
   * <p>
   * This constructor is reached only for guaranteed regular,
   * and non-zero rationals.
   * </p>
   *
   * @param numeratorValue   the numerator.
   * @param denominatorValue the denominator.
   */
  private BigRationalRegular(
      final BigInteger numeratorValue,
      final BigInteger denominatorValue) {
    if (denominatorValue.signum() < 0) {
      this.numerator = numeratorValue.negate();
      this.denominator = denominatorValue.negate();
    } else {
      this.numerator = numeratorValue;
      this.denominator = denominatorValue;
    }
    this.signum = numeratorValue.signum() > 0 ? 1 : -1;
  }

  /**
   * Factory method for creating rational values.
   * <table border="1">
   * <caption>Rational values returned based on numerator and
   * denominator values.</caption>
   * <tr>
   * <th>numerator</th>
   * <th>denominator</th>
   * <th>result</th>
   * </tr>
   * <tr>
   * <td>0</td>
   * <td>0</td>
   * <td>{@link BigRational#NAN}</td>
   * </tr>
   * <tr>
   * <td>0</td>
   * <td>!=0</td>
   * <td>{@link BigRational#ZERO}</td>
   * </tr>
   * <tr>
   * <td>&lt;0</td>
   * <td>0</td>
   * <td>{@link BigRational#NEGATIVE_INFINITY}</td>
   * </tr>
   * <tr>
   * <td>&gt;0</td>
   * <td>0</td>
   * <td>{@link BigRational#POSITIVE_INFINITY}</td>
   * </tr>
   * <tr>
   * <td>!=0</td>
   * <td>!=0</td>
   * <td>new BigRational(numerator, denominator)</td>
   * </tr>
   * </table>
   * <p>If either the numerator, denominator, or both are null,
   * they are set to {@link BigInteger#ZERO}.</p>
   *
   * @param numeratorValue   the numerator
   * @param denominatorValue the denominator
   * @return the rational instance
   */
  public static BigRational of(
      final BigInteger numeratorValue, final BigInteger denominatorValue) {
    final BigInteger fixedNumeratorValue = numeratorValue == null
        ? BigInteger.ZERO
        : numeratorValue;
    final BigInteger fixedDenominatorValue = denominatorValue == null
        ? BigInteger.ZERO
        : denominatorValue;
    if (fixedNumeratorValue.equals(BigInteger.ZERO)) {
      if (fixedDenominatorValue.equals(BigInteger.ZERO)) {
        return BigRational.NAN;
      } else {
        return BigRational.ZERO;
      }
    } else if (fixedDenominatorValue.equals(BigInteger.ZERO)) {
      if (fixedNumeratorValue.signum() < 0) {
        return BigRational.NEGATIVE_INFINITY;
      } else {
        return BigRational.POSITIVE_INFINITY;
      }
    }
    return new BigRationalRegular(
        fixedNumeratorValue,
        fixedDenominatorValue
    );
  }

  /**
   * Factory method for creating rational values with a given numerator
   * and a denominator of {@link BigInteger#ONE}.
   *
   * @param numerator the numerator.
   * @return the rational instance.
   */
  public static BigRational of(final BigInteger numerator) {
    return of(numerator, BigInteger.ONE);
  }

  @Override
  public Type type() {
    return Type.REGULAR;
  }

  @Override
  public boolean isNaN() {
    return false;
  }

  @Override
  public boolean isZero() {
    return false;
  }

  @Override
  public boolean isPositiveInfinity() {
    return false;
  }

  @Override
  public boolean isNegativeInfinity() {
    return false;
  }

  @Override
  public BigRational add(
      final BigRational bigRational) {
    if (bigRational.type() != Type.REGULAR) {
      return bigRational.add(this);
    }
    BigInteger rn = bigRational.numerator();
    BigInteger rd = bigRational.denominator();
    return BigRational
        .of(numerator.multiply(rd).add(rn.multiply(denominator)),
            denominator.multiply(rd));
  }

  @Override
  public BigRational subtract(
      final BigRational bigRational) {
    if (bigRational.type() != Type.REGULAR) {
      return bigRational.negate().add(this);
    }
    BigInteger rn = bigRational.numerator();
    BigInteger rd = bigRational.denominator();
    return BigRational
        .of(numerator.multiply(rd)
                .subtract(rn.multiply(denominator)),
            denominator.multiply(rd));
  }

  @Override
  public BigRational multiply(
      final BigRational bigRational) {
    if (bigRational.type() != Type.REGULAR) {
      return bigRational.multiply(this);
    }
    return BigRational.of(numerator.multiply(bigRational.numerator()),
        denominator.multiply(
            bigRational.denominator()));
  }

  @Override
  public BigRational divide(
      final BigRational bigRational) {
    if (bigRational.type() != Type.REGULAR) {
      return bigRational.reciprocal().multiply(this);
    }
    return BigRational
        .of(numerator.multiply(bigRational.denominator()),
            denominator.multiply(bigRational.numerator()));
  }

  @Override
  public BigRational negate() {
    return BigRational.of(numerator.negate(), denominator);
  }

  @Override
  public BigRational reciprocal() {
    return BigRational.of(denominator, numerator);
  }

  @Override
  public BigRational abs() {
    return signum < 0 ? negate() : this;
  }

  @Override
  public BigRational reduce() {
    BigInteger gcd = numerator.gcd(denominator);
    return gcd.equals(BigInteger.ONE) ? this : BigRational
        .of(numerator.divide(gcd), denominator.divide(gcd));
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
    return signum;
  }

  @Override
  public String toString() {
    return numerator + "/" + denominator;
  }

  @Override
  public boolean equals(final Object obj) {
    return this == obj
        || (obj instanceof PairView)
        && TupleSupport.pairEquals(this, (PairView<?, ?>) obj);
  }

  @Override
  public int compareTo(final BigRational o) {
    if (o.type() == Type.REGULAR) {
      return numerator.multiply(o.denominator()).compareTo(
          denominator.multiply(o.numerator()));
    } else {
      return -o.compareTo(this);
    }
  }

  @Override
  public int hashCode() {
    if (cachedHashCode == 0) {
      cachedHashCode = TupleSupport.hashCode(item1(), item2());
    }
    return cachedHashCode;
  }

}
