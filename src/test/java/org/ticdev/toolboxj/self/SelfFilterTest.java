package org.ticdev.toolboxj.self;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test {@link SelfFilter}.
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 */
public class SelfFilterTest {

    /**
     * Concrete implementation for the test.
     * 
     * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
     *
     */
    static class SelfFilterImpl
        implements
        SelfFilter<SelfFilterImpl> {

        @Override
        public SelfFilterImpl self() {
            return this;
        }
    }

    /**
     * Test to ensure that the filter method works properly
     */
    @Test
    public void test() {

        final SelfFilterImpl sfi = new SelfFilterImpl();

        /* ensure filter triggers */
        final boolean[] predicateTriggered = { false };

        sfi.filter(sfi1 -> predicateTriggered[0] = true);

        Assert.assertTrue(predicateTriggered[0]);

        /* not present if filter fails */
        Assert.assertFalse(
            sfi.filter(sfi1 -> false).isPresent());

        /* present if filter succeedes and values equals the instance */

        Assert.assertTrue(sfi.filter(sfi1 -> true).isPresent());
        Assert.assertEquals(sfi, sfi.filter(sfi1 -> true).get());

    }

}
