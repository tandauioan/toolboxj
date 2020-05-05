package org.ticdev.toolboxj.numbers.bigrational;

import org.ticdev.toolboxj.tuples.Pair;
import org.ticdev.toolboxj.tuples.PairView;
import org.ticdev.toolboxj.tuples.TupleSupport;

import java.math.BigInteger;

/**
 * Positive-Infinity concrete BigRational implementation as singleton.
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public class BigRationalPositiveInfinity
        implements BigRational {

    /**
     * The singleton instance.
     */
    private static final BigRationalPositiveInfinity INSTANCE =
            new BigRationalPositiveInfinity();

    /**
     * Default hash code.
     */
    private static final int HASH_CODE_ =
            TupleSupport.hashCode(INSTANCE.item1(), INSTANCE.item2());

    /**
     * Private singleton constructor.
     */
    private BigRationalPositiveInfinity() {
    }

    /**
     * Returns the singleton instance.
     *
     * @return the singleton instance.
     */
    public static BigRationalPositiveInfinity getInstance() {
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
        return true;
    }

    @Override
    public boolean isNegativeInfinity() {
        return false;
    }

    @Override
    public BigRational add(
            BigRational bigRational) {
        return (bigRational.isNaN() || bigRational.isNegativeInfinity()) ?
                BigRational.NAN : this;
    }

    @Override
    public BigRational subtract(
            BigRational bigRational) {
        return (bigRational.isNaN() || bigRational.isPositiveInfinity()) ?
                BigRational.NAN : this;
    }

    @Override
    public BigRational multiply(
            BigRational bigRational) {
        return (bigRational.isNaN() || bigRational.isZero()) ?
                bigRational : (bigRational.signum() < 0 ?
                BigRational.NEGATIVE_INFINITY : this);
    }

    @Override
    public BigRational divide(
            BigRational bigRational) {
        return (bigRational.isNaN() || bigRational.isNegativeInfinity() ||
                bigRational.isPositiveInfinity()) ? BigRational.NAN :
                (bigRational.isZero() || bigRational.signum() > 0) ? this :
                        BigRational.NEGATIVE_INFINITY;
    }

    @Override
    public BigRational negate() {
        return BigRational.NEGATIVE_INFINITY;
    }

    @Override
    public BigRational reciprocal() {
        return BigRational.ZERO;
    }

    @Override
    public BigRational abs() {
        return this;
    }

    @Override
    public BigRational reduce() {
        return this;
    }

    @Override
    public BigInteger numerator() {
        return BigInteger.ONE;
    }

    @Override
    public BigInteger denominator() {
        return BigInteger.ZERO;
    }

    @Override
    public int signum() {
        return 1;
    }

    @Override
    public Type type() {
        return Type.POSITIVE_INFINITY;
    }

    @Override
    public String toString() {
        return "Infinity";
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || (obj instanceof PairView && TupleSupport
                .pairEquals(this, (PairView<?, ?>) obj));
    }

    @Override
    public int compareTo(BigRational o) {
        return this == o ? 0 : (o == BigRational.NAN ? -1 : 1);
    }

    @Override
    public int hashCode() {
        return HASH_CODE_;
    }

}
