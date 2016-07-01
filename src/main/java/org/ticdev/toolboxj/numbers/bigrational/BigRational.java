package org.ticdev.toolboxj.numbers.bigrational;

import org.ticdev.toolboxj.functions.NullaryFunction;
import org.ticdev.toolboxj.numbers.Rational;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.*;

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
        extends Rational<BigInteger, BigRational> {

    /**
     * BigRational types.
     */
    public enum Type {
        /* regular rational */
        REGULAR,
        /* not a number */
        NAN,
        /* zero */
        ZERO,
        /* positive infinity */
        POSITIVE_INFINITY,
        /* negative infinity */
        NEGATIVE_INFINITY;
    }

    /**
     * not-a-number instance
     */
    public static final BigRational NAN = BigRationalNaN.getInstance();

    /**
     * zero instance
     */
    public static final BigRational ZERO = BigRationalZero.getInstance();

    /**
     * positive infinity
     */
    public static final BigRational POSITIVE_INFINITY =
            BigRationalPositiveInfinity.getInstance();

    /**
     * negative infinity
     */
    public static final BigRational NEGATIVE_INFINITY =
            BigRationalNegativeInfinity.getInstance();

    /**
     * Returns the {@link Type} of the {@link BigRational}
     *
     * @return the {@link Type} of the {@link BigRational}
     */
    public Type type();

    /**
     * Returns a rational with the given numerator and denominator.
     *
     * @param numerator   the numerator
     * @param denominator the denominator
     * @return the rational
     */
    static BigRational of(BigInteger numerator, BigInteger denominator) {
        return BigRationalRegular.of(numerator, denominator);
    }

    /**
     * Returns a rational with the given numerator and the denominator set to {@link BigInteger#ONE}.
     *
     * @param numerator the nuerator.
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

}
