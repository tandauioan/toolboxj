package org.ticdev.toolboxj.numbers.bigrational;

import org.ticdev.toolboxj.tuplesnew.Pair;
import org.ticdev.toolboxj.tuplesnew.TupleSupport;

import java.math.BigInteger;

/**
 * Regular concrete BigRational implementation as singleton.
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public class BigRationalRegular
        implements BigRational {

    /**
     * numerator
     */
    private final BigInteger numerator;

    /**
     * denominator
     */
    private final BigInteger denominator;

    /**
     * signum
     */
    private final int signum;

    /**
     * cached hash code
     */
    private int cached_hash_code_;

    /**
     * Private constructor. Due to special instances, BigRational
     * instantiation happens via factory methods.
     * This constructor is reached only for guaranteed regular,
     * and non-zero rationals.
     *
     * @param numerator   the numerator.
     * @param denominator the denominator.
     */
    private BigRationalRegular(
            BigInteger numerator, BigInteger denominator) {
        if (denominator.signum() < 0) {
            numerator = numerator.negate();
            denominator = denominator.negate();
        }
        this.numerator = numerator;
        this.denominator = denominator;
        this.signum = numerator.signum() > 0 ? 1 : -1;
    }

    /**
     * Factory method for creating rational values.
     * <p/>
     * <p>The instance returned is explained in the table below:</p>
     * <p/>
     * <table border="1">
     * <th align=><b>numerator</b><td/><b>denominator</b><td/><b>result</b></th><tr/>
     * ==0<td/>==0<td/>NAN<tr/>
     * ==0<td/>!=0<td/>0<tr/>
     * &lt;0<td/>==0<td/>NEGATIVE_INFINITY<tr/>
     * &gt;0<td/>==0<td/>POSITIVE_INFINITY<tr/>
     * !=0<td/>!=0<td/>new BigRational(numerator, denominator)<tr/>
     * </table>
     * <p/>
     * <p>If either the numerator, denominator or both are null, they are
     * set to {@link BigInteger#ZERO}.</p>
     *
     * @param numerator   the numerator.
     * @param denominator the denominator.
     * @return the rational instance.
     */
    public static BigRational of(
            BigInteger numerator, BigInteger denominator) {
        if (numerator == null) {
            numerator = BigInteger.ZERO;
        }
        if (denominator == null) {
            denominator = BigInteger.ZERO;
        }
        if (numerator.equals(BigInteger.ZERO)) {
            if (denominator.equals(BigInteger.ZERO)) {
                return BigRational.NAN;
            } else {
                return BigRational.ZERO;
            }
        } else if (denominator.equals(BigInteger.ZERO)) {
            if (numerator.signum() < 0) {
                return BigRational.NEGATIVE_INFINITY;
            } else {
                return BigRational.POSITIVE_INFINITY;
            }
        }
        return new BigRationalRegular(numerator, denominator);
    }

    /**
     * Factory method for creating rational values with a given numerator
     * and a denominator of {@link BigInteger#ONE}.
     *
     * @param numerator the numerator.
     * @return the rational instance.
     */
    public static BigRational of(BigInteger numerator) {
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
            BigRational bigRational) {
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
            BigRational bigRational) {
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
            BigRational bigRational) {
        if (bigRational.type() != Type.REGULAR) {
            return bigRational.multiply(this);
        }
        return BigRational.of(numerator.multiply(bigRational.numerator()),
                              denominator.multiply(
                                      bigRational.denominator()));
    }

    @Override
    public BigRational divide(
            BigRational bigRational) {
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
    public boolean equals(Object obj) {
        return (obj instanceof Pair<?, ?>) &&
               TupleSupport.pairEquals(this, (Pair<?, ?>) obj);
    }

    @Override
    public int compareTo(BigRational o) {
        if (o.type() == Type.REGULAR) {
            return numerator.multiply(o.denominator()).compareTo(
                    denominator.multiply(o.numerator()));
        } else {
            return -o.compareTo(this);
        }
    }

    @Override
    public int hashCode() {
        if (cached_hash_code_ == 0) {
            cached_hash_code_ = TupleSupport.hashCode(item1(), item2());
        }
        return cached_hash_code_;
    }
}
