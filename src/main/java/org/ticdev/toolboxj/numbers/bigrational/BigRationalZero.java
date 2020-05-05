package org.ticdev.toolboxj.numbers.bigrational;

import org.ticdev.toolboxj.tuples.Pair;
import org.ticdev.toolboxj.tuples.PairView;
import org.ticdev.toolboxj.tuples.TupleSupport;

import java.math.BigInteger;

/**
 * Zero concrete BigRational implementation as singleton.
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public class BigRationalZero
        implements BigRational {

    /**
     * The singleton instance.
     */
    private static final BigRationalZero INSTANCE = new BigRationalZero();

    /**
     * Default hash code.
     */
    private static final int HASH_CODE_ =
            TupleSupport.hashCode(INSTANCE.item1(), INSTANCE.item2());

    /**
     * Private singleton constructor.
     */
    private BigRationalZero() {
    }

    /**
     * Returns the singleton instance.
     *
     * @return the singleton instance.
     */
    public static BigRationalZero getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean isNaN() {
        return false;
    }

    @Override
    public boolean isZero() {
        return true;
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
            BigRational bigRational) {
        return bigRational;
    }

    @Override
    public BigRational subtract(
            BigRational bigRational) {
        return bigRational.negate();
    }

    @Override
    public BigRational multiply(
            BigRational bigRational) {
        return bigRational.isNaN() ? bigRational : this;
    }

    @Override
    public BigRational divide(
            BigRational bigRational) {
        return (bigRational.isNaN() || bigRational.isZero()) ?
                BigRational.NAN : this;
    }

    @Override
    public BigRational negate() {
        return this;
    }

    @Override
    public BigRational reciprocal() {
        return BigRational.POSITIVE_INFINITY;
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
        return BigInteger.ZERO;
    }

    @Override
    public BigInteger denominator() {
        return BigInteger.ONE;
    }

    @Override
    public int signum() {
        return 0;
    }

    @Override
    public Type type() {
        return Type.ZERO;
    }

    @Override
    public String toString() {
        return "0";
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || (obj instanceof PairView<?, ?> && TupleSupport
                .pairEquals(this, (PairView<?, ?>) obj));
    }

    @Override
    public int compareTo(BigRational o) {
        return this == o ? 0 :
                (o == BigRational.NAN ? -1 : (o.signum() < 0 ? 1 : -1));
    }

    @Override
    public int hashCode() {
        return HASH_CODE_;
    }

}

