package org.ticdev.toolboxj.numbers;

import org.ticdev.toolboxj.tuplesnew.Pair;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Rational number interface.
 * <p>
 * For methods which return a rational instance, whether or not the result is a new instance
 * or an existing instance (including this) is implementation defined.
 *</p>
 * <p>
 * The {@link Object#equals(Object)} and {@link Object#hashCode()} methods must be
 * implemented to match the {@link Pair} class.
 * A direct result of this is that {@link Object#equals} and {@link Rational#compareTo(Object)}
 * may not return consistent values for equality, but consistent with the {@link BigDecimal}
 * behavior. For example, given 1/3 and 2/6, an implementation may return false for equality,
 * but it must return 0 for compareTo. Implementations are not required to reduce fractions,
 * unless demanded so via {@link Rational#reduce()}.
 * </p>
 *
 * @param <ARGS> type of numerator and denominator.
 * @param <R>    concrete rational type
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public interface Rational<ARGS, R extends Rational<ARGS, R>>
        extends Pair<ARGS, ARGS>,
        Comparable<R> {

    /**
     * Returns true if this instance does not represent a valid rational number. For example, if both the numerator and denominator are 0, the division results in non-a-number.
     *
     * @return true if this instance does not represent a valid rational number.
     */
    boolean isNaN();

    /**
     * Returns true if this instance represents a value of 0. For example if the numerator is 0 and the denominator is a non-zero value.
     *
     * @return true if this instance represents a value of 0.
     */
    boolean isZero();

    /**
     * Returns true if this instance represents positive infinity. For example, if the numerator is a positive number and the denominator is 0.
     *
     * @return true if this instance represents positive infinity.
     */
    boolean isPositiveInfinity();

    /**
     * Returns true if this instance represents negative infinity. For example, if the numerator is a negative number and the denominator is 0.
     *
     * @return true if this instance represents negative infinity.
     */
    boolean isNegativeInfinity();

    /**
     * Returns the sum of this rational and the given rational.
     *
     * @param r the rational to add to this rational.
     * @return the result of the addition.
     */
    R add(R r);

    /**
     * Returns the subtraction of the given rational from this rational.
     *
     * @param r the rational to subtract from this rational.
     * @return the result of the subtraction.
     */
    R subtract(R r);

    /**
     * Returns the result of multiplying this rational by the given rational.
     *
     * @param r the rational by which to multiply this rational.
     * @return the result of the multiplication.
     */
    R multiply(R r);

    /**
     * Returns the result of dividing this rational by the given rational.
     *
     * @param r the rational by which to divide this rational.
     * @return the result of the division.
     */
    R divide(R r);

    /**
     * Returns a rational whose value is -this.
     *
     * @return -this.
     */
    R negate();

    /**
     * Returns a rational whose value is 1/this.
     *
     * @return 1/this.
     */
    R reciprocal();

    /**
     * Returns the absolute (positive) value of this rational.
     *
     * @return the absolute value of this rational.
     */
    R abs();

    /**
     * Returns a rational whose value is the simplified fraction of this.
     *
     * @return a rational whose value is the simplified fraction of this.
     */
    R reduce();

    /**
     * Returns the numerator.
     *
     * @return the numerator.
     */
    ARGS numerator();

    /**
     * Returns the denominator.
     *
     * @return the denominator.
     */
    ARGS denominator();

    /**
     * Returns the sign of this.
     *
     * @return the sign of this.
     */
    int signum();

    @Override
    default ARGS item1() {
        return numerator();
    }

    @Override
    default ARGS item2() {
        return denominator();
    }

}
