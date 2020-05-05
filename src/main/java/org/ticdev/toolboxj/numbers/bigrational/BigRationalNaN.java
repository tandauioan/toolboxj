package org.ticdev.toolboxj.numbers.bigrational;

import org.ticdev.toolboxj.tuples.Pair;
import org.ticdev.toolboxj.tuples.PairView;
import org.ticdev.toolboxj.tuples.TupleSupport;

import java.math.BigInteger;

/**
 * Not-A-Number concrete BigRational implementation as singleton.
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public class BigRationalNaN
        implements BigRational {

    /**
     * The singleton instance
     */
    private static final BigRationalNaN INSTANCE = new BigRationalNaN();

    /**
     * Default hash code
     */
    private static final int HASH_CODE_ =
            TupleSupport.hashCode(INSTANCE.item1(), INSTANCE.item2());

    /**
     * Private singleton constructor.
     */
    private BigRationalNaN() {
    }

    /**
     * Returns the singleton instance.
     *
     * @return the singleton instance.
     */
    public static BigRationalNaN getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean isNaN() {
        return true;
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
            BigRational bigRational) {
        return this;
    }

    @Override
    public BigRational subtract(
            BigRational bigRational) {
        return this;
    }

    @Override
    public BigRational multiply(
            BigRational bigRational) {
        return this;
    }

    @Override
    public BigRational divide(
            BigRational bigRational) {
        return this;
    }

    @Override
    public BigRational negate() {
        return this;
    }

    @Override
    public BigRational reciprocal() {
        return this;
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
        return BigInteger.ZERO;
    }

    @Override
    public int signum() {
        return 0;
    }

    @Override
    public Type type() {
        return Type.NAN;
    }

    @Override
    public String toString() {
        return "NaN";
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || (obj instanceof PairView && TupleSupport
                .pairEquals(this, (PairView<?, ?>) obj));
    }

    @Override
    public int compareTo(BigRational o) {
        return this == o ? 0 : 1;
    }

    @Override
    public int hashCode() {
        return HASH_CODE_;
    }

}
