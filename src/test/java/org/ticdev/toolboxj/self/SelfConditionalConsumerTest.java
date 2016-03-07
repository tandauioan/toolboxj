package org.ticdev.toolboxj.self;

import org.junit.Test;

import org.junit.Assert;

/**
 * Test class for {@link SelfConditionalConsumer}.
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 */
public class SelfConditionalConsumerTest {

    /**
     * Concrete implementation of a self conditional consumer
     * 
     * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
     *
     */
    static class SelfConditionalConsumerImpl
        implements
        SelfConditionalConsumer<SelfConditionalConsumerImpl> {

        @Override
        public SelfConditionalConsumerImpl self() {
            return this;
        }

    }

    /**
     * Test ifThenElse
     */
    @Test
    public void test_ifThenElse() {

        SelfConditionalConsumerImpl instance =
            new SelfConditionalConsumerImpl();

        final SelfConditionalConsumerImpl[] sideEffect = { null };

        /* predicate side-effect */

        Assert.assertEquals(instance, instance.ifThenElse(scci -> {
            sideEffect[0] = scci;
            return false;
        } , t -> {
        } , t -> {
        }));

        Assert.assertNotNull(sideEffect[0]);
        Assert.assertEquals(instance, sideEffect[0]);

        /* predicate true side effect */
        sideEffect[0] = null;
        Assert.assertEquals(instance, instance.ifThenElse(t -> true,
            t -> sideEffect[0] = t, t -> Assert.fail()));
        Assert.assertNotNull(sideEffect[0]);
        Assert.assertEquals(instance, sideEffect[0]);

        /* predicate false side effect */
        sideEffect[0] = null;
        Assert.assertEquals(instance, instance.ifThenElse(t -> false,
            t -> Assert.fail(), t -> sideEffect[0] = t));
        Assert.assertNotNull(sideEffect[0]);
        Assert.assertEquals(instance, sideEffect[0]);

        Assert.assertNotEquals(instance, null);
    }

    /**
     * Test ifDo
     */
    @Test
    public void test_ifDo() {

        SelfConditionalConsumerImpl instance =
            new SelfConditionalConsumerImpl();

        final SelfConditionalConsumerImpl[] sideEffect = { null };

        /* predicate side-effect */
        Assert.assertEquals(instance, instance.ifDo(t -> {
            sideEffect[0] = t;
            return false;
        } , t -> {
        }));

        Assert.assertNotNull(sideEffect[0]);
        Assert.assertEquals(instance, sideEffect[0]);

        /* predicate true side effect */
        sideEffect[0] = null;
        Assert.assertEquals(instance,
            instance.ifDo(t -> true, t -> sideEffect[0] = t));
        Assert.assertNotNull(sideEffect[0]);
        Assert.assertEquals(instance, sideEffect[0]);

        /* predicate false, no side effect */
        sideEffect[0] = null;
        Assert.assertEquals(instance,
            instance.ifDo(t -> false, t -> sideEffect[0] = t));
        Assert.assertNull(sideEffect[0]);

        Assert.assertNotEquals(instance, null);
    }

}
