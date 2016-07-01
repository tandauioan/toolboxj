package org.ticdev.toolboxj.numbers.bigrational;

import org.ticdev.toolboxj.numbers.Rational;
import org.ticdev.toolboxj.tuplesnew.Pair;
import org.ticdev.toolboxj.tuplesnew.TupleSupport;

import java.math.BigInteger;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
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
public class MutableBigRational
        implements Rational<BigInteger, MutableBigRational> {

    /**
     * the numerator
     */
    private BigInteger numerator;

    /**
     * the denominator
     */
    private BigInteger denominator;

    /**
     * The type
     */
    private BigRational.Type type;

    /**
     * Supplier of new instances of zero mutable rationals
     */
    private static final Supplier<MutableBigRational> zeroSupplier =
            MutableBigRational::new;

    /**
     * Supplier of new instances of one mutable rationals
     */
    private static final Supplier<MutableBigRational> oneSupplier =
            () -> new MutableBigRational(BigInteger.ONE);


    /**
     * Default constructor. Sets the rational to zero.
     */
    public MutableBigRational() {
        set_(BigInteger.ZERO, BigInteger.ONE).normalize_();
    }

    /**
     * Class constructor. Sets the rational to numerator/1.
     *
     * @param numerator the numerator
     */
    public MutableBigRational(BigInteger numerator) {
        set_(numerator, BigInteger.ONE).normalize_();
    }

    /**
     * Class constructor. Sets the rational to numerator/denominator.
     *
     * @param numerator   the numerator
     * @param denominator the denominator
     */
    public MutableBigRational(
            BigInteger numerator, BigInteger denominator) {
        set_(numerator, denominator).normalize_();
    }

    /**
     * Class constructor. Sets the rational to numerator/denominator
     * after converting the long values to BigInteger.
     *
     * @param numerator   the numerator
     * @param denominator the denominator
     */
    public MutableBigRational(long numerator, long denominator) {
        set_(BigInteger.valueOf(numerator),
             BigInteger.valueOf(denominator)).normalize_();
    }

    /**
     * Class constructor. Sets the rational to numerator/1
     * after converting the long value to BigInteger.
     *
     * @param numerator the numerator
     */
    public MutableBigRational(long numerator) {
        set_(BigInteger.valueOf(numerator), BigInteger.ONE).normalize_();
    }

    /**
     * Class constructor. Creates a new instance from a pair of BigRational numbers.
     *
     * @param pair the pair
     */
    public MutableBigRational(Pair<BigInteger, BigInteger> pair) {
        set_(pair).normalize_();
    }

    /**
     * Internal set without normalization.
     *
     * @param numerator   the numerator
     * @param denominator the denominator
     * @return this instance
     */
    private MutableBigRational set_(
            BigInteger numerator, BigInteger denominator) {
        if (numerator == null || denominator == null) {
            this.numerator = BigInteger.ZERO;
            this.denominator = BigInteger.ZERO;
        }
        this.numerator = numerator;
        this.denominator = denominator;
        return this;
    }

    /**
     * Sets the numerator and denominator to the given values
     *
     * @param numerator   the numerator
     * @param denominator the denominator.
     * @return this
     */
    public MutableBigRational set(
            BigInteger numerator, BigInteger denominator) {
        return set_(numerator, denominator).normalize_();
    }

    /**
     * Internal set without normalization.
     *
     * @param pair the pair of BigInteger values
     * @return this instance
     */
    private MutableBigRational set_(Pair<BigInteger, BigInteger> pair) {
        return pair == null ? set_(null, null) :
                set_(pair.item1(), pair.item2());
    }

    /**
     * Sets the numerator and denominator to the given values
     *
     * @param pair the pair containing the numerator and denominator,
     *             in this order.
     * @return this instance
     */
    public MutableBigRational set(Pair<BigInteger, BigInteger> pair) {
        return set_(pair).normalize_();
    }

    /**
     * Internal non-normalized set to not-a-number.
     *
     * @return this instance
     */
    private MutableBigRational set_nan_() {
        this.numerator = BigInteger.ZERO;
        this.denominator = BigInteger.ZERO;
        return this;
    }

    /**
     * Internal non-normalized set for numerator.
     *
     * @param numerator the numerator
     * @return this instance
     */
    private MutableBigRational set_(BigInteger numerator) {
        if (numerator == null) {
            return set_nan_();
        }
        this.numerator = numerator;
        return this;
    }

    /**
     * Sets the numerator.
     *
     * @param numerator the numerator
     * @return this instance
     */
    public MutableBigRational set(BigInteger numerator) {
        return set_(numerator).normalize_();
    }

    /**
     * Sets the numerator
     *
     * @param numerator the numerator
     * @return this instance
     */
    public MutableBigRational set(long numerator) {
        return set(BigInteger.valueOf(numerator));
    }

    /**
     * Sets the numerator
     *
     * @param numerator the numerator
     * @return this instance
     */
    public MutableBigRational numerator(BigInteger numerator) {
        return set_(numerator).normalize_();
    }

    /**
     * Sets the numerator.
     *
     * @param numerator the numerator
     * @return this instance
     */
    public MutableBigRational numerator(long numerator) {
        return set(numerator);
    }

    /**
     * Internal set denominator, not normalized.
     *
     * @param denominator the denominator
     * @return this instance
     */
    private MutableBigRational set_denominator_(BigInteger denominator) {
        if (denominator == null) {
            return set_nan_();
        }
        this.denominator = denominator;
        return this;
    }

    /**
     * Sets the denominator.
     *
     * @param denominator the denominator
     * @return this instance
     */
    public MutableBigRational denominator(BigInteger denominator) {
        return set_denominator_(denominator).normalize_();
    }

    /**
     * Sets the denominator
     *
     * @param denominator the denominator
     * @return this instance
     */
    public MutableBigRational denominator(long denominator) {
        return denominator(BigInteger.valueOf(denominator));
    }

    /**
     * Internal normalization process.
     *
     * @return this instance
     */
    private MutableBigRational normalize_() {
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
            MutableBigRational mutableBigRational) {
        return add_not_normalized_(mutableBigRational).normalize_();
    }

    @Override
    public MutableBigRational subtract(
            MutableBigRational mutableBigRational) {
        return subtract_not_normalized_(mutableBigRational)
                .normalize_();
    }

    @Override
    public MutableBigRational multiply(
            MutableBigRational mutableBigRational) {
        return multiply_not_normalized_(mutableBigRational)
                .normalize_();
    }

    @Override
    public MutableBigRational divide(
            MutableBigRational mutableBigRational) {
        return divide_not_normalized_(mutableBigRational).normalize_();
    }


    @Override
    public MutableBigRational negate() {
        numerator = numerator.negate();
        return normalize_();
    }

    @Override
    public MutableBigRational reciprocal() {
        return set_(denominator, numerator).normalize_();
    }

    @Override
    public MutableBigRational abs() {
        return numerator.signum() < 0 ?
                set_(numerator.negate()).normalize_() : this;
    }

    @Override
    public MutableBigRational reduce() {
        BigInteger gcd = numerator.gcd(denominator);
        if (gcd.equals(BigInteger.ZERO) || gcd.equals(BigInteger.ONE)) {
            return this;
        }
        return set_(numerator.divide(gcd), denominator.divide(gcd))
                .normalize_();
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
    public int compareTo(MutableBigRational o) {
        switch (type) {
            case NAN:
                return o.type == BigRational.Type.NEGATIVE_INFINITY ? 0 :
                        1;
            case ZERO:
                if (o.isNaN()) {
                    return -1;
                }
                if (o.isZero()) {
                    return 0;
                }
                return -o.numerator.signum();
            case NEGATIVE_INFINITY:
                return o.type == BigRational.Type.NEGATIVE_INFINITY ? 0 :
                        -1;
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
                if (o.type == BigRational.Type.POSITIVE_INFINITY ||
                    o.type == BigRational.Type.NAN) {
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
    public boolean equals(Object obj) {
        return (obj instanceof Pair<?, ?>) &&
               TupleSupport.pairEquals(this, (Pair<?, ?>) obj);
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
     * Adds a rational to this rational without normalization
     *
     * @param rational the rational to add
     * @return this instance
     */
    private MutableBigRational add_not_normalized_(
            Rational<BigInteger, ?> rational) {
        return set_(numerator.multiply(rational.denominator())
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
            Rational<BigInteger, ?> rational) {
        return add_not_normalized_(rational).normalize_();
    }

    /**
     * Adds a sequence of rationals to this rational.
     *
     * @param rationals the rationals to add
     * @return this instance
     */
    @SafeVarargs
    public final MutableBigRational addRationals(
            Rational<BigInteger, ?>... rationals) {
        for (Rational<BigInteger, ?> rational : rationals) {
            add_not_normalized_(rational);
        }
        return normalize_();
    }

    /**
     * Adds a collection of rationals to this rational.
     *
     * @param rationals the rationals to add
     * @return this instance
     */
    public final MutableBigRational addRationals(
            Collection<? extends Rational<BigInteger, ?>> rationals) {
        rationals.forEach(this::add_not_normalized_);
        return normalize_();
    }

    /**
     * Subtracts a rational from this rational without normalization.
     *
     * @param rational the rational to subtract
     * @return this instance
     */
    private MutableBigRational subtract_not_normalized_(
            Rational<BigInteger, ?> rational) {
        return set_(numerator.multiply(rational.denominator())
                             .subtract(denominator
                                               .multiply(
                                                       rational.numerator())),
                    denominator.multiply(rational.denominator()));
    }

    /**
     * Subtracts the given rational from this rational
     *
     * @param rational the rational to subtract
     * @return this instance
     */
    public MutableBigRational subtractRational(
            Rational<BigInteger, ?> rational) {
        return subtract_not_normalized_(rational).normalize_();
    }

    /**
     * Subtracts the given sequence of rationals from this rational
     *
     * @param rationals the rationals to subtract
     * @return this instance
     */
    @SafeVarargs
    public final MutableBigRational subtractRationals(
            Rational<BigInteger, ?>... rationals) {
        for (Rational<BigInteger, ?> rational : rationals) {
            subtract_not_normalized_(rational);
        }
        return normalize_();
    }

    /**
     * Subtracts the given rationals from this rational
     *
     * @param rationals the rationals to subtract
     * @return this instance
     */
    public final MutableBigRational subtractRationals(
            Collection<? extends Rational<BigInteger, ?>> rationals) {
        rationals.forEach(this::subtract_not_normalized_);
        return normalize_();
    }

    /**
     * Multiplies this rational by the given rational without normalization
     *
     * @param rational the rational to multiply by.
     * @return this instance
     */
    private MutableBigRational multiply_not_normalized_(
            Rational<BigInteger, ?> rational) {
        return set_(numerator.multiply(rational.numerator()),
                    denominator.multiply(rational.denominator()));
    }

    /**
     * Multiplies this rational by the given rational.
     *
     * @param rational the rational to multiply by
     * @return this instance
     */
    public MutableBigRational multiplyRational(
            Rational<BigInteger, ?> rational) {
        return multiply_not_normalized_(rational).normalize_();
    }

    /**
     * Multiplies this rational by the given sequence of rationals.
     *
     * @param rationals the rationals to multiply by
     * @return this instance
     */
    @SafeVarargs
    public final MutableBigRational multiplyRationals(
            Rational<BigInteger, ?>... rationals) {
        for (Rational<BigInteger, ?> rational : rationals) {
            multiply_not_normalized_(rational);
        }
        return normalize_();
    }

    /**
     * Multiplies this rational by the given rationals
     *
     * @param rationals the rationals to multiply by
     * @return this instance
     */
    public final MutableBigRational multiplyRationals(
            Collection<? extends Rational<BigInteger, ?>> rationals) {
        rationals.forEach(this::multiply_not_normalized_);
        return normalize_();
    }

    /**
     * Divides this rational by the given rational without normalization
     *
     * @param rational the rational to divide by
     * @return this instance
     */
    private MutableBigRational divide_not_normalized_(
            Rational<BigInteger, ?> rational) {
        return set_(numerator.multiply(rational.denominator()),
                    denominator.multiply(rational.numerator()));
    }

    /**
     * Divides this rational by the given rational
     *
     * @param rational the rational to divide by
     * @return this instance
     */
    private MutableBigRational divideRational(
            Rational<BigInteger, ?> rational) {
        return divide_not_normalized_(rational).normalize_();
    }

    /**
     * Divides this rational by the given sequence of rationals.
     *
     * @param rationals the rationals to divide by
     * @return this instance
     */
    @SafeVarargs
    public final MutableBigRational divideRationals(
            Rational<BigInteger, ?>... rationals) {
        for (Rational<BigInteger, ?> rational : rationals) {
            divide_not_normalized_(rational);
        }
        return normalize_();
    }

    /**
     * Divides this rational by the given rationals.
     *
     * @param rationals the rationals to divide by
     * @return this instance
     */
    public final MutableBigRational divideRationals(
            Collection<? extends Rational<BigInteger, ?>> rationals) {
        rationals.forEach(this::divide_not_normalized_);
        return normalize_();
    }

    /**
     * Adds all the rationals in the given stream to this rational.
     *
     * @param stream the stream of rationals
     * @return this instance
     */
    public final MutableBigRational addRationals(
            Stream<? extends Rational<BigInteger, ?>> stream) {
        return add(stream.collect(zeroSupplier,
                                  MutableBigRational::add_not_normalized_,
                                  MutableBigRational::add_not_normalized_));
    }


    /**
     * Subtracts all the rationals in the given stream from this rational.
     *
     * @param stream the stream of rationals
     * @return this instance
     */
    public final MutableBigRational subtractRationals(
            Stream<? extends Rational<BigInteger, ?>> stream) {
        return subtract(stream.collect(zeroSupplier,
                                       MutableBigRational::add_not_normalized_,
                                       MutableBigRational::add_not_normalized_));
    }

    /**
     * Multiplies this rational with all the rationals in the given stream.
     *
     * @param stream the stream of rationals
     * @return this instance
     */
    public final MutableBigRational multiplyRationals(
            Stream<? extends Rational<BigInteger, ?>> stream) {
        return multiply(stream.collect(oneSupplier,
                                       MutableBigRational::multiply_not_normalized_,
                                       MutableBigRational::multiply_not_normalized_));
    }

    /**
     * Divides this rational by all the rationals in the given stream.
     *
     * @param stream the stream of rationals
     * @return this instance
     */
    public final MutableBigRational divideRationals(
            Stream<? extends Rational<BigInteger, ?>> stream) {
        return divide(stream.collect(oneSupplier,
                                     MutableBigRational::multiply_not_normalized_,
                                     MutableBigRational::multiply_not_normalized_));
    }


}
