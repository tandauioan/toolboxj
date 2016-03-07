package org.ticdev.toolboxj.self;

import org.junit.Test;

import org.junit.Assert;

/**
 * Test {@link SelfMapper}
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 */
public class SelfMapperTest {

    /**
     * Concrete self mapper implementation.
     * 
     * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
     *
     */
    static class SelfMapperImpl
        implements
        SelfMapper<SelfMapperImpl> {
        @Override
        public SelfMapperImpl self() {
            return this;
        }
    }

    /**
     * test the mapping function
     */
    @Test
    public void test() {

        final SelfMapperImpl smi = new SelfMapperImpl();

        /* test null */
        Assert.assertFalse(smi.map(smi1 -> null).isPresent());

        /* test return hash code as map */
        Assert.assertEquals(smi.hashCode(),
            smi.map(smi1 -> smi1.hashCode()).get().intValue());

    }

}
