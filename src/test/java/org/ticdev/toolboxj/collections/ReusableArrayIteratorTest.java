package org.ticdev.toolboxj.collections;

import java.util.Iterator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for {@link ReusableArrayIterator}.
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 */
public class ReusableArrayIteratorTest {

    /**
     * Global test array
     */
    static String[] testArray=new String[23];

    /**
     * Initialize test array
     */
    @Before
    public void before() {
        for (int i = 0; i < testArray.length; i++) {
            testArray[i] = i + "";
        }
    }

    /**
     * Test construction via {@link ReusableArrayIterator#newInstance()}
     */
    @Test
    public void testConstruction_NewInstance() {

        Iterator<String> it = ReusableArrayIterator.newInstance();
        Assert.assertFalse(it.hasNext());

    }

    /**
     * Test construction using the
     * 
     * <pre>
     * of
     * </pre>
     * 
     * methods.
     */
    @Test
    public void testConstruction_Of() {

        Assert.assertTrue(
            ReusableArrayIterator.of(new String[10]).hasNext());

        Assert.assertFalse(
            ReusableArrayIterator.of(new String[10], 5, 0).hasNext());

        Assert.assertTrue(
            ReusableArrayIterator.of(new String[10], 4, 5).hasNext());

        Assert.assertTrue(
            ReusableArrayIterator.of(new String[10], 0, 10).hasNext());

        try {
            Assert.assertFalse(
                ReusableArrayIterator.of(new String[10], -1, 2).hasNext());
            Assert.fail();
        } catch (Exception ex) {
            Assert.assertNotNull(ex);
        }

        try {
            Assert.assertFalse(
                ReusableArrayIterator.of(new String[10], 0, 11).hasNext());
            Assert.fail();
        } catch (Exception ex) {
            Assert.assertNotNull(ex);
        }

    }

    /**
     * Test construction using newInstance and reset methods.
     */
    @Test
    public void testConstruction_newInstance_Reset() {

        Assert.assertTrue(ReusableArrayIterator.newInstance()
            .reset(new String[10]).hasNext());

        Assert.assertFalse(ReusableArrayIterator.newInstance()
            .reset(new String[10], 5, 0).hasNext());

        Assert.assertTrue(
            ReusableArrayIterator.newInstance().reset(new String[10], 4, 5)
                .hasNext());

        Assert.assertTrue(
            ReusableArrayIterator.newInstance()
                .reset(new String[10], 0, 10).hasNext());

        try {
            Assert.assertFalse(
                ReusableArrayIterator.newInstance()
                    .reset(new String[10], -1, 2).hasNext());
            Assert.fail();
        } catch (Exception ex) {
            Assert.assertNotNull(ex);
        }

        try {
            Assert.assertFalse(
                ReusableArrayIterator.newInstance()
                    .reset(new String[10], 0, 11).hasNext());
            Assert.fail();
        } catch (Exception ex) {
            Assert.assertNotNull(ex);
        }

    }

    /**
     * Test iterations
     */
    @Test
    public void testIteration() {

        int offset;

        int len;

        for (offset = 0; offset < testArray.length; offset++) {
            for (len = 0; len <= testArray.length - offset; len++) {
                Iterator<String> it =
                    ReusableArrayIterator.of(testArray, offset, len);
                if (len == 0) {
                    Assert.assertFalse(it.hasNext());
                } else {
                    for (int i = offset; i < offset + len; i++) {
                        Assert.assertEquals(testArray[i], it.next());
                    }
                }
            }
        }

        ReusableArrayIterator<String> it =
            ReusableArrayIterator.newInstance();

        for (offset = 0; offset < testArray.length; offset++) {
            for (len = 0; len <= testArray.length - offset; len++) {
                it.reset(testArray, offset, len);
                if (len == 0) {
                    Assert.assertFalse(it.hasNext());
                } else {
                    for (int i = offset; i < offset + len; i++) {
                        Assert.assertEquals(testArray[i], it.next());
                    }
                }
            }
        }

    }

}
