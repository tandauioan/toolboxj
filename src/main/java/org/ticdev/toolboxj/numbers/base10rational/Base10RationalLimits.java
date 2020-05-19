package org.ticdev.toolboxj.numbers.base10rational;

/**
 * Supporting methods and constants for base 10 rationals
 * (long numerator, int base 10 exponent).
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public final class Base10RationalLimits {

  /**
   * The maximum value for the numerator - ensures that the negative
   * of every rational is not overflowing.
   */
  public static final long MAX_NUMERATOR =
      Long.MAX_VALUE + Long.MIN_VALUE < 0
          ? Long.MAX_VALUE
          : Long.MIN_VALUE;

  /**
   * The minimum value for the numerator, equal to
   * -{@link Base10RationalLimits#MAX_NUMERATOR}.
   */
  public static final long MIN_NUMERATOR = -MAX_NUMERATOR;

  /**
   * Maximum base 10 exponent.
   */
  public static final int MAX_BASE10_EXPONENT;

  /**
   * Maximum denominator value.
   */
  public static final long MAX_DENOMINATOR;

  /**
   * mapping between the base 10 exponent and the long denominator
   * (10^exp).
   */
  private static final long[] DENOMINATORS;

  /**
   * Like {@link Base10RationalLimits#DENOMINATORS} but using
   * {@link Long} objects rather than long primitives.
   */
  private static final Long[] DENOMINATORS_LONG_OBJECTS;

  /**
   * Value 10.
   */
  private static final long TEN = 10L;

  /* initializations */
  static {
    long l = MAX_NUMERATOR;
    long maxDenominator = 1;
    int count = 0;
    while ((l = l / TEN) != 0) {
      count++;
      maxDenominator = TEN * maxDenominator;
    }
    MAX_DENOMINATOR = maxDenominator;
    MAX_BASE10_EXPONENT = count;
    count++;
    DENOMINATORS = new long[count];
    DENOMINATORS_LONG_OBJECTS = new Long[count];
    maxDenominator = 1;
    for (int i = 0; i < count; i++) {
      DENOMINATORS[i] = maxDenominator;
      DENOMINATORS_LONG_OBJECTS[i] = Long.valueOf(maxDenominator);
      maxDenominator = maxDenominator * TEN;
    }
  }

  /**
   * Private constructor.
   */
  private Base10RationalLimits() {
  }

  /**
   * Returns the long denominator for a given exponent. If the
   * exponent is outside accepted range
   * (0-{@link Base10RationalLimits#MAX_BASE10_EXPONENT}) an
   * {@link IndexOutOfBoundsException} will be thrown.
   *
   * @param base10Exponent the base 10 exponent
   * @return the denominator as long
   * @throws IndexOutOfBoundsException if the base 10 exponent is
   *                                   out of the representation bounds.
   */
  public static long denominator(final int base10Exponent)
      throws IndexOutOfBoundsException {
    return DENOMINATORS[base10Exponent];
  }

  /**
   * Like {@link Base10RationalLimits#denominator(int)} but returns
   * a cached {@link Long} denominator.
   *
   * @param base10Exponent the base 10 exponent
   * @return the denominator as {@link Long}
   */
  public static Long denominatorLong(final int base10Exponent) {
    return DENOMINATORS_LONG_OBJECTS[base10Exponent];
  }

  /**
   * Checks that the numerator is within valid ranges.
   * ({@link Base10RationalLimits#MIN_NUMERATOR} -
   * {@link Base10RationalLimits#MAX_NUMERATOR})
   *
   * @param numerator the numerator to check
   * @throws IllegalArgumentException if the numerator is outside the
   *                                  valid range.
   */
  public static void assertValidNumerator(final long numerator)
      throws
      IllegalArgumentException {
    if (numerator < MIN_NUMERATOR || numerator > MAX_NUMERATOR) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Checks that the base 10 exponent is within valid ranges.
   * (0 -
   * {@link Base10RationalLimits#MAX_BASE10_EXPONENT})
   *
   * @param base10Exponent the base 10 exponent to check
   * @throws IllegalArgumentException if the base 10 exponent is
   *                                  outside the valid range
   */
  public static void assertValidBase10Exponent(final int base10Exponent)
      throws IllegalArgumentException {
    if (base10Exponent < 0 || base10Exponent > MAX_BASE10_EXPONENT) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Checks that the given numerator and base 10 exponent denominator
   * are within valid ranges (similiar to calling
   * {@link Base10RationalLimits#assertValidNumerator(long)} and
   * {@link Base10RationalLimits#assertValidBase10Exponent(int)}).
   *
   * @param numerator      the numerator to check
   * @param base10Exponent the base 10 exponent to check
   * @throws IllegalArgumentException if either the numerator or the
   *                                  base 10 exponent are outside
   *                                  their respective valid ranges.
   */
  public static void assertValidLimits(
      final long numerator, final int base10Exponent)
      throws IllegalArgumentException {
    if (numerator < MIN_NUMERATOR || numerator > MAX_NUMERATOR
        || base10Exponent < 0 || base10Exponent > MAX_BASE10_EXPONENT) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Returns the absolute value of a long numerator.
   *
   * @param numerator the numerator
   * @return the absolutve value of the numerator
   */
  public static long abs(final long numerator) {
    return numerator < 0 ? -numerator : numerator;
  }

}
