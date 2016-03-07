package org.ticdev.toolboxj.self;

import org.junit.Test;
import org.junit.Assert;

/**
 * Test {@link SelfConsumer}
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 */
public class SelfConsumerTest {

    /**
     * Concrete implementation of a self consumer.
     * 
     * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
     *
     */
    static class SelfConsumerImpl
        implements
        SelfConsumer<SelfConsumerImpl> {
        @Override
        public SelfConsumerImpl self() {
            return this;
        }
    }

    /**
     * Test self consumer.
     */
    @Test
    public void test() {

        final SelfConsumerImpl sci = new SelfConsumerImpl();

        final SelfConsumerImpl[] sideEffect = { null };

        Assert.assertEquals(sci,
            sci.doConsume(sci1 -> sideEffect[0] = sci1));

        Assert.assertNotNull(sideEffect[0]);
        Assert.assertEquals(sci, sideEffect[0]);

    }

}
