package org.ticdev.toolboxj.self;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test for {@link Self}.
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 */
public class SelfTest {

    /**
     * Concrete self implementation test class.
     * 
     * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
     *
     */
    static final class SelfImpl
        implements
        Self<SelfImpl> {

        @Override
        public SelfImpl self() {
            return this;
        }
    }

    /**
     * Ensures that {@link Self#self()} returns the instance.
     */
    @Test
    public void test() {

        SelfImpl instance = new SelfImpl();

        Assert.assertEquals(instance, instance.self());

    }

}
