package org.ticdev.toolboxj.numbers.bigrational;

import org.junit.Assert;
import org.junit.Test;
import org.ticdev.toolboxj.collections.ReusableArrayIterator;
import org.ticdev.toolboxj.tuples.TupleSupport;

import java.math.BigInteger;

/**
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public class BigRationalTest {

    /**
     * Test construction of nan rationals
     */
    @Test
    public void test_construction_nan() {

        BigRational[] nans = new BigRational[]{
                BigRationalNaN.getInstance(),
                BigRational.NAN,
                BigRational.of(BigInteger.ZERO, BigInteger.ZERO),
                BigRational.of(null, null),
                BigRational.of(TupleSupport.of((BigInteger) null,
                                               (BigInteger) null))
        };

        for (BigRational nan : nans) {
            for (BigRational nan1 : nans) {
                Assert.assertTrue(nan == nan1);
                Assert.assertEquals(nan, nan1);
                Assert.assertEquals(0, nan.compareTo(nan1));
                Assert.assertEquals(nan.hashCode(), nan1.hashCode());
            }
            Assert.assertEquals(BigRational.Type.NAN, nan.type());
            Assert.assertTrue(nan.isNaN());
            Assert.assertFalse(nan.isNegativeInfinity());
            Assert.assertFalse(nan.isPositiveInfinity());
            Assert.assertFalse(nan.isZero());
        }
        Assert.assertEquals(
                TupleSupport.of(BigInteger.ZERO, BigInteger.ZERO),
                BigRational.NAN);
        Assert.assertEquals(BigRational.NAN,
                            TupleSupport
                                    .of(BigInteger.ZERO, BigInteger.ZERO));
    }

    /**
     * Test construction of zero rationals
     */
    @Test
    public void test_construction_zero() {
        BigRational[] zeros = new BigRational[]{
                BigRationalZero.getInstance(),
                BigRational.ZERO,
                BigRational.of(BigInteger.ZERO, BigInteger.TEN.negate()),
                BigRational.of(BigInteger.ZERO, BigInteger.TEN),
                BigRational.of(BigInteger.ZERO),
                BigRational.of(0)
        };
        for (BigRational zero : zeros) {
            for (BigRational zero1 : zeros) {
                Assert.assertTrue(zero == zero1);
                Assert.assertEquals(zero, zero1);
                Assert.assertEquals(0, zero.compareTo(zero1));
                Assert.assertEquals(zero.hashCode(), zero1.hashCode());
            }
            Assert.assertEquals(BigRational.Type.ZERO, zero.type());
            Assert.assertFalse(zero.isNaN());
            Assert.assertFalse(zero.isNegativeInfinity());
            Assert.assertFalse(zero.isPositiveInfinity());
            Assert.assertTrue(zero.isZero());
        }
        Assert.assertEquals(
                TupleSupport.of(BigInteger.ZERO, BigInteger.ONE),
                BigRational.ZERO);
        Assert.assertEquals(BigRational.ZERO,
                            TupleSupport
                                    .of(BigInteger.ZERO, BigInteger.ONE));
    }

    /**
     * Test construction of negative infinity rationals
     */
    @Test
    public void test_construction_negative_infinity() {
        BigRational[] nifs = new BigRational[]{
                BigRationalNegativeInfinity.getInstance(),
                BigRational.NEGATIVE_INFINITY,
                BigRational.of(BigInteger.TEN.negate(), BigInteger.ZERO)
        };
        for (BigRational nif : nifs) {
            for (BigRational nif1 : nifs) {
                Assert.assertTrue(nif == nif1);
                Assert.assertEquals(nif, nif1);
                Assert.assertEquals(0, nif.compareTo(nif1));
                Assert.assertEquals(nif.hashCode(), nif1.hashCode());
            }
            Assert.assertEquals(BigRational.Type.NEGATIVE_INFINITY,
                                nif.type());
            Assert.assertFalse(nif.isNaN());
            Assert.assertTrue(nif.isNegativeInfinity());
            Assert.assertFalse(nif.isPositiveInfinity());
            Assert.assertFalse(nif.isZero());
        }
        Assert.assertEquals(
                TupleSupport.of(BigInteger.ONE.negate(), BigInteger.ZERO),
                BigRational.NEGATIVE_INFINITY);
        Assert.assertEquals(BigRational.NEGATIVE_INFINITY,
                            TupleSupport
                                    .of(BigInteger.ONE.negate(),
                                        BigInteger.ZERO));
    }

    /**
     * Test construction of positive infinity rationals
     */
    @Test
    public void test_construction_positive_infinity() {
        BigRational[] pifs = new BigRational[]{
                BigRationalPositiveInfinity.getInstance(),
                BigRational.POSITIVE_INFINITY,
                BigRational.of(BigInteger.TEN, BigInteger.ZERO)
        };
        for (BigRational pif : pifs) {
            for (BigRational pif1 : pifs) {
                Assert.assertTrue(pif == pif1);
                Assert.assertEquals(pif, pif1);
                Assert.assertEquals(0, pif.compareTo(pif1));
                Assert.assertEquals(pif.hashCode(), pif1.hashCode());
            }
            Assert.assertEquals(BigRational.Type.POSITIVE_INFINITY,
                                pif.type());
            Assert.assertFalse(pif.isNaN());
            Assert.assertFalse(pif.isNegativeInfinity());
            Assert.assertTrue(pif.isPositiveInfinity());
            Assert.assertFalse(pif.isZero());
        }
        Assert.assertEquals(
                TupleSupport.of(BigInteger.ONE, BigInteger.ZERO),
                BigRational.POSITIVE_INFINITY);
        Assert.assertEquals(BigRational.POSITIVE_INFINITY,
                            TupleSupport
                                    .of(BigInteger.ONE, BigInteger.ZERO));
    }

    /**
     * Test construction of regular rationals
     */
    @Test
    public void test_construction_regular() {
        BigRational[] regs = new BigRational[]{
                BigRational.of(BigInteger.TEN, BigInteger.ONE),
                BigRational.of(10, 1),
                BigRational.of(BigInteger.TEN),
                BigRational.of(10),
                BigRational.of(-10, -1),
                BigRational.of(BigInteger.TEN.negate(),
                               BigInteger.ONE.negate())
        };
        for (BigRational reg : regs) {
            for (BigRational reg1 : regs) {
                Assert.assertEquals(reg, reg1);
                Assert.assertEquals(0, reg.compareTo(reg1));
                Assert.assertEquals(reg.hashCode(), reg1.hashCode());
            }
            Assert.assertEquals(BigRational.Type.REGULAR, reg.type());
            Assert.assertFalse(reg.isNaN());
            Assert.assertFalse(reg.isNegativeInfinity());
            Assert.assertFalse(reg.isPositiveInfinity());
            Assert.assertFalse(reg.isZero());

        }
        regs = new BigRational[]{
                BigRational.of(BigInteger.TEN.negate(), BigInteger.ONE),
                BigRational.of(-10, 1),
                BigRational.of(BigInteger.TEN.negate()),
                BigRational.of(-10)
        };
        for (BigRational reg : regs) {
            for (BigRational reg1 : regs) {
                Assert.assertEquals(reg, reg1);
                Assert.assertEquals(0, reg.compareTo(reg1));
                Assert.assertEquals(reg.hashCode(), reg1.hashCode());
            }
            Assert.assertEquals(BigRational.Type.REGULAR, reg.type());
            Assert.assertFalse(reg.isNaN());
            Assert.assertFalse(reg.isNegativeInfinity());
            Assert.assertFalse(reg.isPositiveInfinity());
            Assert.assertFalse(reg.isZero());
        }
        regs = new BigRational[]{
                BigRational.of(BigInteger.TEN.negate(),
                               BigInteger.valueOf(3)),
                BigRational.of(-10, 3),
                };
        for (BigRational reg : regs) {
            for (BigRational reg1 : regs) {
                Assert.assertEquals(reg, reg1);
                Assert.assertEquals(0, reg.compareTo(reg1));
                Assert.assertEquals(reg.hashCode(), reg1.hashCode());
            }
            Assert.assertEquals(BigRational.Type.REGULAR, reg.type());
            Assert.assertFalse(reg.isNaN());
            Assert.assertFalse(reg.isNegativeInfinity());
            Assert.assertFalse(reg.isPositiveInfinity());
            Assert.assertFalse(reg.isZero());
        }
    }

    /**
     * Test negate
     */
    @Test
    public void test_negate() {
        ReusableArrayIterator<BigRational> inputIterator =
                ReusableArrayIterator.of(new BigRational[]{
                        BigRational.NAN,
                        BigRational.NEGATIVE_INFINITY,
                        BigRational.POSITIVE_INFINITY,
                        BigRational.ZERO,
                        BigRational.of(-1, 5),
                        BigRational.of(1, 5)
                });
        ReusableArrayIterator<BigRational> expectedIterator =
                ReusableArrayIterator.of(new BigRational[]{
                        BigRational.NAN,
                        BigRational.POSITIVE_INFINITY,
                        BigRational.NEGATIVE_INFINITY,
                        BigRational.ZERO,
                        BigRational.of(1, 5),
                        BigRational.of(-1, 5)
                });
        while (inputIterator.hasNext()) {
            Assert.assertEquals(expectedIterator.next(),
                                inputIterator.next().negate());
        }
    }

    /**
     * Test reciprocal
     */
    @Test
    public void test_reciprocal() {
        ReusableArrayIterator<BigRational> inputIterator =
                ReusableArrayIterator.of(new BigRational[]{
                        BigRational.NAN,
                        BigRational.NEGATIVE_INFINITY,
                        BigRational.POSITIVE_INFINITY,
                        BigRational.ZERO,
                        BigRational.of(1, 5),
                        BigRational.of(5, 1),
                        BigRational.of(-1, 5),
                        BigRational.of(-5, 1)
                });
        ReusableArrayIterator<BigRational> expectedIterator =
                ReusableArrayIterator.of(new BigRational[]{
                        BigRational.NAN,
                        BigRational.ZERO,
                        BigRational.ZERO,
                        BigRational.POSITIVE_INFINITY,
                        BigRational.of(5, 1),
                        BigRational.of(1, 5),
                        BigRational.of(-5, 1),
                        BigRational.of(-1, 5)
                });
        while (inputIterator.hasNext()) {
            Assert.assertEquals(expectedIterator.next(),
                                inputIterator.next().reciprocal());
        }
    }

    /**
     * Test abs
     */
    @Test
    public void test_abs() {
        ReusableArrayIterator<BigRational> inputIterator =
                ReusableArrayIterator.of(new BigRational[]{
                        BigRational.NAN,
                        BigRational.NEGATIVE_INFINITY,
                        BigRational.POSITIVE_INFINITY,
                        BigRational.ZERO,
                        BigRational.of(1, 5),
                        BigRational.of(5, 1),
                        BigRational.of(-1, 5),
                        BigRational.of(-5, 1)
                });
        ReusableArrayIterator<BigRational> expectedIterator =
                ReusableArrayIterator.of(new BigRational[]{
                        BigRational.NAN,
                        BigRational.POSITIVE_INFINITY,
                        BigRational.POSITIVE_INFINITY,
                        BigRational.ZERO,
                        BigRational.of(1, 5),
                        BigRational.of(5, 1),
                        BigRational.of(1, 5),
                        BigRational.of(5, 1)
                });
        while (inputIterator.hasNext()) {
            Assert.assertEquals(expectedIterator.next(),
                                inputIterator.next().abs());
        }
    }

    /**
     * Test reduce
     */
    @Test
    public void test_reduce() {
        ReusableArrayIterator<BigRational> inputIterator =
                ReusableArrayIterator.of(new BigRational[]{
                        BigRational.NAN,
                        BigRational.NEGATIVE_INFINITY,
                        BigRational.POSITIVE_INFINITY,
                        BigRational.ZERO,
                        BigRational.of(1, 5),
                        BigRational.of(2, 10),
                        BigRational.of(-1, 5),
                        BigRational.of(-2, 10)
                });
        ReusableArrayIterator<BigRational> expectedIterator =
                ReusableArrayIterator.of(new BigRational[]{
                        BigRational.NAN,
                        BigRational.NEGATIVE_INFINITY,
                        BigRational.POSITIVE_INFINITY,
                        BigRational.ZERO,
                        BigRational.of(1, 5),
                        BigRational.of(1, 5),
                        BigRational.of(-1, 5),
                        BigRational.of(-1, 5)
                });
        while (inputIterator.hasNext()) {
            Assert.assertEquals(expectedIterator.next(),
                                inputIterator.next().reduce());
        }
    }

    /**
     * Returns true if and only if the value is zero, false otherwise.
     *
     * @param value the value to test
     * @return true if and only if the value is zero, false otherwise.
     */
    private boolean zero(int value) {
        return value == 0;
    }

    /**
     * Returns true if and only if the value is strictly positive, false otherwise
     *
     * @param value the value to test
     * @return true if and only if the value is strictly positive, false otherwise
     */
    private boolean positive(int value) {
        return value > 0;
    }

    /**
     * Returns true if and only if the value is strictly negative, false otherwise
     *
     * @param value the value to test
     * @return true if and only if the value is strictly negative, false otherwise
     */
    private boolean negative(int value) {
        return value < 0;
    }

    /**
     * Test compareTo
     */
    @Test
    public void test_compareTo() {
        BigRational positiveNumber = BigRational.of(1, 10);
        BigRational positiveNumberMultiple = BigRational.of(2, 20);
        BigRational negativeNumber = BigRational.of(-1, 10);
        BigRational negativeNumberMultiple = BigRational.of(-2, 20);

        /* positive / negative */
        BigRational[][] testArray = {
                {BigRational.NAN, BigRational.ZERO},
                {BigRational.NAN, BigRational.NEGATIVE_INFINITY},
                {BigRational.NAN, BigRational.POSITIVE_INFINITY},
                {BigRational.NAN, positiveNumber},
                {BigRational.NAN, negativeNumber},
                {BigRational.ZERO, BigRational.NEGATIVE_INFINITY},
                {BigRational.ZERO, negativeNumber},
                {
                        BigRational.POSITIVE_INFINITY,
                        BigRational.NEGATIVE_INFINITY
                },
                {BigRational.POSITIVE_INFINITY, BigRational.ZERO},
                {BigRational.POSITIVE_INFINITY, negativeNumber},
                {BigRational.POSITIVE_INFINITY, positiveNumber},
                {negativeNumber, BigRational.NEGATIVE_INFINITY},
                {positiveNumber, BigRational.NEGATIVE_INFINITY},
                {positiveNumber, negativeNumber},
                {positiveNumber, BigRational.ZERO}
        };
        for (BigRational[] pair : testArray) {
            Assert.assertTrue(positive(pair[0].compareTo(pair[1])));
            Assert.assertTrue(negative(pair[1].compareTo(pair[0])));
        }
        /* zero */
        testArray = new BigRational[][]{
                {BigRational.NAN, BigRational.NAN},
                {BigRational.ZERO, BigRational.ZERO},
                {
                        BigRational.NEGATIVE_INFINITY,
                        BigRational.NEGATIVE_INFINITY
                },
                {
                        BigRational.POSITIVE_INFINITY,
                        BigRational.POSITIVE_INFINITY
                },
                {positiveNumber, positiveNumberMultiple},
                {negativeNumber, negativeNumberMultiple}
        };
        for (BigRational[] pair : testArray) {
            Assert.assertTrue(zero(pair[0].compareTo(pair[1])));
        }

    }

    /**
     * Testing rationals that are equal by value but not by structure, i.e.
     * o1.compareTo(o2)==0 and !o1.equals(o2)
     */
    @Test
    public void test_equal_by_compareTo_but_not_equals() {
        BigRational positiveNumber = BigRational.of(1, 10);
        BigRational positiveNumberMultiple = BigRational.of(2, 20);
        BigRational negativeNumber = BigRational.of(-1, 10);
        BigRational negativeNumberMultiple = BigRational.of(-2, 20);
        BigRational[][] testArray = {
                {positiveNumber, positiveNumberMultiple},
                {negativeNumber, negativeNumberMultiple}
        };
        for (BigRational[] pair : testArray) {
            Assert.assertNotEquals(pair[0], pair[1]);
            Assert.assertEquals(0, pair[0].compareTo(pair[1]));
            Assert.assertEquals(pair[0].reduce(), pair[1].reduce());
        }
    }

    /**
     * Test addition
     */
    @Test
    public void test_add() {
        BigRational regularPositive = BigRational.of(1, 5);
        BigRational regularPositiveHalf = BigRational.of(1, 10);
        BigRational regularPositiveDouble = BigRational.of(2, 5);
        BigRational regularNegative = BigRational.of(-1, 5);
        BigRational regularNegativeHalf = BigRational.of(-1, 10);
        BigRational regularNegativeDouble = BigRational.of(-2, 5);

        BigRational[][] testArray = {
                {BigRational.NAN, BigRational.NAN, BigRational.NAN},
                {BigRational.NAN, BigRational.ZERO, BigRational.NAN},
                {
                        BigRational.NAN, BigRational.NEGATIVE_INFINITY,
                        BigRational.NAN
                },
                {
                        BigRational.NAN, BigRational.POSITIVE_INFINITY,
                        BigRational.NAN
                },
                {BigRational.ZERO, BigRational.ZERO, BigRational.ZERO},
                {
                        BigRational.ZERO, BigRational.NEGATIVE_INFINITY,
                        BigRational.NEGATIVE_INFINITY
                },
                {
                        BigRational.ZERO, BigRational.POSITIVE_INFINITY,
                        BigRational.POSITIVE_INFINITY
                },
                {
                        BigRational.NEGATIVE_INFINITY,
                        BigRational.NEGATIVE_INFINITY,
                        BigRational.NEGATIVE_INFINITY
                },
                {
                        BigRational.NEGATIVE_INFINITY,
                        BigRational.POSITIVE_INFINITY, BigRational.NAN
                },
                {BigRational.NAN, regularPositive, BigRational.NAN},
                {BigRational.ZERO, regularPositive, regularPositive},
                {
                        BigRational.NEGATIVE_INFINITY, regularPositive,
                        BigRational.NEGATIVE_INFINITY
                },
                {
                        BigRational.POSITIVE_INFINITY, regularPositive,
                        BigRational.POSITIVE_INFINITY
                },
                {BigRational.NAN, regularNegative, BigRational.NAN},
                {BigRational.ZERO, regularNegative, regularNegative},
                {
                        BigRational.NEGATIVE_INFINITY, regularNegative,
                        BigRational.NEGATIVE_INFINITY
                },
                {
                        BigRational.POSITIVE_INFINITY, regularNegative,
                        BigRational.POSITIVE_INFINITY
                },
                {regularPositive, regularNegative, BigRational.ZERO},
                {regularPositive, regularPositive, regularPositiveDouble},
                {
                        regularPositiveHalf, regularPositiveHalf,
                        regularPositive
                },
                {regularNegative, regularNegative, regularNegativeDouble},
                {regularNegativeHalf, regularNegativeHalf, regularNegative}
        };
        for (BigRational[] triplet : testArray) {
            Assert.assertEquals(triplet[2],
                                triplet[0].add(triplet[1]).reduce());
            Assert.assertEquals(0, triplet[2]
                    .compareTo(triplet[0].add(triplet[1])));
            Assert.assertEquals(triplet[2],
                                triplet[1].add(triplet[0]).reduce());
            Assert.assertEquals(0, triplet[2]
                    .compareTo(triplet[1].add(triplet[0])));
        }
    }

    /**
     * Test subtraction
     */
    @Test
    public void test_subtract() {
        BigRational regularPositive = BigRational.of(1, 5);
        BigRational regularPositiveHalf = BigRational.of(1, 10);
        BigRational regularPositiveDouble = BigRational.of(2, 5);
        BigRational regularNegative = BigRational.of(-1, 5);
        BigRational regularNegativeHalf = BigRational.of(-1, 10);
        BigRational regularNegativeDouble = BigRational.of(-2, 5);
        BigRational[][] testArray = {

                {
                        BigRational.NAN, BigRational.NAN,
                        BigRational.NAN
                },

                {BigRational.NAN, BigRational.ZERO, BigRational.NAN},
                {
                        BigRational.NAN, BigRational.NEGATIVE_INFINITY,
                        BigRational.NAN
                },
                {
                        BigRational.NAN, BigRational.POSITIVE_INFINITY,
                        BigRational.NAN
                },
                {BigRational.NAN, regularNegative, BigRational.NAN},
                {BigRational.NAN, regularPositive, BigRational.NAN},
                {BigRational.ZERO, BigRational.NAN, BigRational.NAN},
                {BigRational.ZERO, BigRational.ZERO, BigRational.ZERO},
                {
                        BigRational.ZERO, BigRational.NEGATIVE_INFINITY,
                        BigRational.POSITIVE_INFINITY
                },
                {
                        BigRational.ZERO, BigRational.POSITIVE_INFINITY,
                        BigRational.NEGATIVE_INFINITY
                },
                {BigRational.ZERO, regularNegative, regularPositive},
                {BigRational.ZERO, regularPositive, regularNegative},
                {
                        BigRational.NEGATIVE_INFINITY, BigRational.NAN,
                        BigRational.NAN
                },
                {
                        BigRational.NEGATIVE_INFINITY, BigRational.ZERO,
                        BigRational.NEGATIVE_INFINITY
                },
                {
                        BigRational.NEGATIVE_INFINITY,
                        BigRational.NEGATIVE_INFINITY, BigRational.NAN
                },
                {
                        BigRational.NEGATIVE_INFINITY,
                        BigRational.POSITIVE_INFINITY,
                        BigRational.NEGATIVE_INFINITY
                },
                {
                        BigRational.NEGATIVE_INFINITY, regularNegative,
                        BigRational.NEGATIVE_INFINITY
                },
                {
                        BigRational.NEGATIVE_INFINITY, regularPositive,
                        BigRational.NEGATIVE_INFINITY
                },
                {
                        BigRational.POSITIVE_INFINITY, BigRational.NAN,
                        BigRational.NAN
                },
                {
                        BigRational.POSITIVE_INFINITY, BigRational.ZERO,
                        BigRational.POSITIVE_INFINITY
                },
                {
                        BigRational.POSITIVE_INFINITY,
                        BigRational.NEGATIVE_INFINITY,
                        BigRational.POSITIVE_INFINITY
                },
                {
                        BigRational.POSITIVE_INFINITY,
                        BigRational.POSITIVE_INFINITY, BigRational.NAN
                },
                {
                        BigRational.POSITIVE_INFINITY, regularNegative,
                        BigRational.POSITIVE_INFINITY
                },
                {
                        BigRational.POSITIVE_INFINITY, regularPositive,
                        BigRational.POSITIVE_INFINITY
                },
                {regularNegative, BigRational.NAN, BigRational.NAN},
                {regularNegative, BigRational.ZERO, regularNegative},
                {
                        regularNegative, BigRational.NEGATIVE_INFINITY,
                        BigRational.POSITIVE_INFINITY
                },
                {
                        regularNegative, BigRational.POSITIVE_INFINITY,
                        BigRational.NEGATIVE_INFINITY
                },
                {regularNegative, regularNegative, BigRational.ZERO},
                {regularNegative, regularPositive, regularNegativeDouble},
                {
                        regularNegativeHalf, regularPositiveHalf,
                        regularNegative
                },
                {regularPositive, BigRational.NAN, BigRational.NAN},
                {regularPositive, BigRational.ZERO, regularPositive},
                {
                        regularPositive, BigRational.NEGATIVE_INFINITY,
                        BigRational.POSITIVE_INFINITY
                },
                {
                        regularPositive, BigRational.POSITIVE_INFINITY,
                        BigRational.NEGATIVE_INFINITY
                },
                {regularPositive, regularPositive, BigRational.ZERO},
                {regularPositive, regularNegative, regularPositiveDouble},
                {regularPositiveHalf, regularNegativeHalf, regularPositive}
        };
        for (BigRational[] triplet : testArray) {
            Assert.assertEquals(triplet[2],
                                triplet[0].subtract(triplet[1]).reduce());
            Assert.assertEquals(0, triplet[2]
                    .compareTo(triplet[0].subtract(triplet[1])));
        }
    }

    /**
     * Test multiplication
     */
    @Test
    public void test_multiply() {
        BigRational regularPositive = BigRational.of(1, 5);
        BigRational regularPositive2 = BigRational.of(1, 25);
        BigRational regularNegative = BigRational.of(-1, 5);
        BigRational regularNegative2 = BigRational.of(-1, 25);
        BigRational[][] testArray = {
                {BigRational.NAN, BigRational.NAN, BigRational.NAN},
                {BigRational.NAN, BigRational.ZERO, BigRational.NAN},
                {
                        BigRational.NAN, BigRational.NEGATIVE_INFINITY,
                        BigRational.NAN
                },
                {
                        BigRational.NAN, BigRational.POSITIVE_INFINITY,
                        BigRational.NAN
                },
                {BigRational.NAN, regularNegative, BigRational.NAN},
                {BigRational.NAN, regularPositive, BigRational.NAN},
                {BigRational.ZERO, BigRational.NAN, BigRational.NAN},
                {BigRational.ZERO, BigRational.ZERO, BigRational.ZERO},
                {
                        BigRational.ZERO, BigRational.NEGATIVE_INFINITY,
                        BigRational.ZERO
                },
                {
                        BigRational.ZERO, BigRational.POSITIVE_INFINITY,
                        BigRational.ZERO
                },
                {BigRational.ZERO, regularNegative, BigRational.ZERO},
                {BigRational.ZERO, regularPositive, BigRational.ZERO},
                {
                        BigRational.NEGATIVE_INFINITY, BigRational.NAN,
                        BigRational.NAN
                },
                {
                        BigRational.NEGATIVE_INFINITY, BigRational.ZERO,
                        BigRational.ZERO
                },
                {
                        BigRational.NEGATIVE_INFINITY,
                        BigRational.NEGATIVE_INFINITY,
                        BigRational.POSITIVE_INFINITY
                },
                {
                        BigRational.NEGATIVE_INFINITY,
                        BigRational.POSITIVE_INFINITY,
                        BigRational.NEGATIVE_INFINITY
                },
                {
                        BigRational.NEGATIVE_INFINITY, regularNegative,
                        BigRational.POSITIVE_INFINITY
                },
                {
                        BigRational.NEGATIVE_INFINITY, regularPositive,
                        BigRational.NEGATIVE_INFINITY
                },
                {
                        BigRational.POSITIVE_INFINITY, BigRational.NAN,
                        BigRational.NAN
                },
                {
                        BigRational.POSITIVE_INFINITY, BigRational.ZERO,
                        BigRational.ZERO
                },
                {
                        BigRational.POSITIVE_INFINITY,
                        BigRational.NEGATIVE_INFINITY,
                        BigRational.NEGATIVE_INFINITY
                },
                {
                        BigRational.POSITIVE_INFINITY,
                        BigRational.POSITIVE_INFINITY,
                        BigRational.POSITIVE_INFINITY
                },
                {
                        BigRational.POSITIVE_INFINITY, regularNegative,
                        BigRational.NEGATIVE_INFINITY
                },
                {
                        BigRational.POSITIVE_INFINITY, regularPositive,
                        BigRational.POSITIVE_INFINITY
                },
                {regularNegative, BigRational.NAN, BigRational.NAN},
                {regularNegative, BigRational.ZERO, BigRational.ZERO},
                {
                        regularNegative, BigRational.NEGATIVE_INFINITY,
                        BigRational.POSITIVE_INFINITY
                },
                {
                        regularNegative, BigRational.POSITIVE_INFINITY,
                        BigRational.NEGATIVE_INFINITY
                },
                {regularNegative, regularNegative, regularPositive2},
                {regularNegative, regularPositive, regularNegative2},
                {regularPositive, BigRational.NAN, BigRational.NAN},
                {regularPositive, BigRational.ZERO, BigRational.ZERO},
                {
                        regularPositive, BigRational.NEGATIVE_INFINITY,
                        BigRational.NEGATIVE_INFINITY
                },
                {
                        regularPositive, BigRational.POSITIVE_INFINITY,
                        BigRational.POSITIVE_INFINITY
                },
                {regularPositive, regularNegative, regularNegative2},
                {regularPositive, regularPositive, regularPositive2}

        };
        for (BigRational[] triplet : testArray) {
            Assert.assertEquals(triplet[2],
                                triplet[0].multiply(triplet[1]).reduce());
            Assert.assertEquals(0, triplet[2]
                    .compareTo(triplet[0].multiply(triplet[1])));
        }
    }

    /**
     * Test division
     */
    @Test
    public void test_divide() {
        BigRational regularPositive = BigRational.of(1, 25);
        BigRational regularNegative = BigRational.of(-1, 25);
        BigRational regularPositiveOne = BigRational.of(1);
        BigRational regularNegativeOne = BigRational.of(-1);
        BigRational[][] testArray = {
                {BigRational.NAN, BigRational.NAN, BigRational.NAN},
                {BigRational.NAN, BigRational.ZERO, BigRational.NAN},
                {
                        BigRational.NAN, BigRational.NEGATIVE_INFINITY,
                        BigRational.NAN
                },
                {
                        BigRational.NAN, BigRational.POSITIVE_INFINITY,
                        BigRational.NAN
                },
                {BigRational.NAN, regularNegative, BigRational.NAN},
                {BigRational.NAN, regularPositive, BigRational.NAN},
                {BigRational.ZERO, BigRational.NAN, BigRational.NAN},
                {BigRational.ZERO, BigRational.ZERO, BigRational.NAN},
                {
                        BigRational.ZERO, BigRational.NEGATIVE_INFINITY,
                        BigRational.ZERO
                },
                {
                        BigRational.ZERO, BigRational.POSITIVE_INFINITY,
                        BigRational.ZERO
                },
                {BigRational.ZERO, regularNegative, BigRational.ZERO},
                {BigRational.ZERO, regularPositive, BigRational.ZERO},
                {
                        BigRational.NEGATIVE_INFINITY, BigRational.NAN,
                        BigRational.NAN
                },
                {
                        BigRational.NEGATIVE_INFINITY, BigRational.ZERO,
                        BigRational.NEGATIVE_INFINITY
                },
                {
                        BigRational.NEGATIVE_INFINITY,
                        BigRational.NEGATIVE_INFINITY, BigRational.NAN
                },
                {
                        BigRational.NEGATIVE_INFINITY,
                        BigRational.POSITIVE_INFINITY, BigRational.NAN
                },
                {
                        BigRational.NEGATIVE_INFINITY, regularNegative,
                        BigRational.POSITIVE_INFINITY
                },
                {
                        BigRational.NEGATIVE_INFINITY, regularPositive,
                        BigRational.NEGATIVE_INFINITY
                },
                {
                        BigRational.POSITIVE_INFINITY, BigRational.NAN,
                        BigRational.NAN
                },
                {
                        BigRational.POSITIVE_INFINITY, BigRational.ZERO,
                        BigRational.POSITIVE_INFINITY
                },
                {
                        BigRational.POSITIVE_INFINITY,
                        BigRational.NEGATIVE_INFINITY, BigRational.NAN
                },
                {
                        BigRational.POSITIVE_INFINITY,
                        BigRational.POSITIVE_INFINITY, BigRational.NAN
                },
                {
                        BigRational.POSITIVE_INFINITY, regularNegative,
                        BigRational.NEGATIVE_INFINITY
                },
                {
                        BigRational.POSITIVE_INFINITY, regularPositive,
                        BigRational.POSITIVE_INFINITY
                },
                {regularNegative, BigRational.NAN, BigRational.NAN},
                {
                        regularNegative, BigRational.ZERO,
                        BigRational.NEGATIVE_INFINITY
                },
                {
                        regularNegative, BigRational.NEGATIVE_INFINITY,
                        BigRational.ZERO
                },
                {
                        regularNegative, BigRational.POSITIVE_INFINITY,
                        BigRational.ZERO
                },
                {regularNegative, regularNegative, regularPositiveOne},
                {regularNegative, regularPositive, regularNegativeOne},
                {regularPositive, BigRational.NAN, BigRational.NAN},
                {
                        regularPositive, BigRational.ZERO,
                        BigRational.POSITIVE_INFINITY
                },
                {
                        regularPositive, BigRational.NEGATIVE_INFINITY,
                        BigRational.ZERO
                },
                {
                        regularPositive, BigRational.POSITIVE_INFINITY,
                        BigRational.ZERO
                },
                {regularPositive, regularNegative, regularNegativeOne},
                {regularPositive, regularPositive, regularPositiveOne}
        };
        for (BigRational[] triplet : testArray) {
            Assert.assertEquals(triplet[2],
                                triplet[0].divide(triplet[1]).reduce());
            Assert.assertEquals(0, triplet[2]
                    .compareTo(triplet[0].divide(triplet[1])));
        }
    }

    /**
     * Test {@link BigRational#newMutableCopy()}
     */
    @Test
    public void testNewMutableCopy() {
        Assert.assertEquals(BigRational.NAN,
                            BigRational.NAN.newMutableCopy());
        Assert.assertEquals(BigRational.ZERO,
                            BigRational.ZERO.newMutableCopy());
        Assert.assertEquals(BigRational.NEGATIVE_INFINITY,
                            BigRational.NEGATIVE_INFINITY
                                    .newMutableCopy());
        Assert.assertEquals(BigRational.POSITIVE_INFINITY,
                            BigRational.POSITIVE_INFINITY
                                    .newMutableCopy());
        Assert.assertTrue(BigRational.NAN.newMutableCopy().isNaN());
        Assert.assertTrue(BigRational.ZERO.newMutableCopy().isZero());
        Assert.assertTrue(BigRational.NEGATIVE_INFINITY.newMutableCopy()
                                                       .isNegativeInfinity());
        Assert.assertTrue(BigRational.POSITIVE_INFINITY.newMutableCopy()
                                                       .isPositiveInfinity());
        Assert.assertEquals(BigRational.of(1, 5),
                            BigRational.of(1, 5).newMutableCopy());
        Assert.assertEquals(BigRational.of(1, 5),
                            new MutableBigRational(1, 5));
        Assert.assertEquals(BigRational.of(-1, 5),
                            BigRational.of(-1, 5).newMutableCopy());
        Assert.assertEquals(BigRational.of(-1, 5),
                            new MutableBigRational(-1, 5));

    }

    /**
     * Test {@link BigRational#copyTo(MutableBigRational)}
     */
    @Test
    public void testCopyTo() {
        MutableBigRational mbr = new MutableBigRational();
        BigRational.NAN.copyTo(mbr);
        Assert.assertTrue(mbr.isNaN());
        BigRational.ZERO.copyTo(mbr);
        Assert.assertTrue(mbr.isZero());
        BigRational.NEGATIVE_INFINITY.copyTo(mbr);
        Assert.assertTrue(mbr.isNegativeInfinity());
        BigRational.POSITIVE_INFINITY.copyTo(mbr);
        Assert.assertTrue(mbr.isPositiveInfinity());
        BigRational.of(1, 5).copyTo(mbr);
        Assert.assertEquals(BigRational.of(1, 5), mbr);
        BigRational.of(-1, 5).copyTo(mbr);
        Assert.assertEquals(BigRational.of(-1, 5), mbr);
        BigRational.of(1, -5).copyTo(mbr);
        Assert.assertEquals(BigRational.of(-1, 5), mbr);

    }

    /**
     * Test {@link BigRational#mutableOfZero()}
     */
    @Test
    public void testMutableOfZero() {
        Assert.assertEquals(BigRational.ZERO, BigRational.mutableOfZero());
        Assert.assertEquals(BigRational.ZERO.newMutableCopy(),
                            BigRational.mutableOfZero());
    }

    @Test
    public void testMutableOf() {
        long[][] input = {
                {0, 0},
                {0, 1},
                {-1, 0},
                {1, 0},
                {-1, 5},
                {1, 5}
        };
        for (long[] longs : input) {
            BigRational br1 = BigRational.of(longs[0], longs[1]);
            MutableBigRational br2 =
                    BigRational.mutableOf(longs[0], longs[1]);
            MutableBigRational br3 = BigRational
                    .mutableOf(BigInteger.valueOf(longs[0]),
                               BigInteger.valueOf(longs[1]));
            MutableBigRational br4 = BigRational.mutableOf(
                    TupleSupport.of(BigInteger.valueOf(longs[0]),
                                    BigInteger.valueOf(longs[1])));
            MutableBigRational br5 =
                    new MutableBigRational(longs[0], longs[1]);
            Assert.assertEquals(br1, br2);
            Assert.assertEquals(br1, br3);
            Assert.assertEquals(br1, br4);
            Assert.assertEquals(br1, br5);
        }
        Assert.assertEquals(BigRational.of(5),
                            BigRational.mutableOf(5));
    }

}
