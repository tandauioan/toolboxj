package org.ticdev.toolboxj.numbers.bigrational;

import org.ticdev.toolboxj.tuplesnew.TupleSupport;

import java.math.BigInteger;

/**
 * Negative-Infinity concrete BigRational implementation as singleton.
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public class BigRationalNegativeInfinity
        implements BigRational {

    /**
     * The value of -{@link BigInteger#ONE}.
     */
    private static final BigInteger BIGINTEGER_MINUSONE =
            BigInteger.ONE.negate();

    /**
     * The singleton instance
     */
    private static final BigRationalNegativeInfinity INSTANCE =
            new BigRationalNegativeInfinity();

    /**
     * Default hash code
     */
    private static final int HASH_CODE_ =
            TupleSupport.hashCode(INSTANCE.item1(), INSTANCE.item2());

    /**
     * Private singleton constructor.
     */
    private BigRationalNegativeInfinity() {
    }

    /**
     * Returns the singleton instance.
     *
     * @return the singleton instance.
     */
    public static BigRationalNegativeInfinity getInstance() {
        return INSTANCE;
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
        return true;
    }

    @Override
    public BigRational add(
            BigRational bigRational) {
        return (bigRational.isNaN() || bigRational.isPositiveInfinity()) ?
                BigRational.NAN : this;
    }

    @Override
    public BigRational subtract(
            BigRational bigRational) {
        return (bigRational.isNaN() || bigRational.isNegativeInfinity()) ?
                BigRational.NAN : this;
    }

    @Override
    public BigRational multiply(
            BigRational bigRational) {
        return (bigRational.isNaN() || bigRational.isZero()) ?
                bigRational :
                (bigRational.signum() < 0 ?
                        BigRational.POSITIVE_INFINITY : this);
    }

    @Override
    public BigRational divide(
            BigRational bigRational) {
        return (bigRational.isNaN() || bigRational.isNegativeInfinity() ||
                bigRational.isPositiveInfinity()) ? BigRational.NAN :
                (bigRational.isZero() || bigRational.signum() > 0 ? this :
                        BigRational.POSITIVE_INFINITY);
    }

    @Override
    public BigRational negate() {
        return BigRational.POSITIVE_INFINITY;
    }

    @Override
    public BigRational reciprocal() {
        return BigRational.ZERO;
    }

    @Override
    public BigRational abs() {
        return BigRational.POSITIVE_INFINITY;
    }

    @Override
    public BigRational reduce() {
        return this;
    }

    @Override
    public BigInteger numerator() {
        return BIGINTEGER_MINUSONE;
    }

    @Override
    public BigInteger denominator() {
        return BigInteger.ZERO;
    }

    @Override
    public int signum() {
        return -1;
    }

    @Override
    public Type type() {
        return Type.NEGATIVE_INFINITY;
    }

    @Override
    public String toString() {
        return "-Infinity";
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj;
    }

    @Override
    public int compareTo(BigRational o) {
        return this == o ? 0 : -1;
    }

    @Override
    public int hashCode() {
        return HASH_CODE_;
    }
}
