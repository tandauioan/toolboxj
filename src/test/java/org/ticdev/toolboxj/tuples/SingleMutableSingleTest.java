package org.ticdev.toolboxj.tuples;

import org.junit.Assert;
import org.junit.Test;

public class SingleTest {

    /**
     * Verifies that all the given objects have the same hash
     * 
     * @param objects
     *            the objects
     */
    private void allEqualHashes(Object... objects) {
        for (int i = 0; i < objects.length - 1; i++) {
            Assert.assertEquals(objects[i].hashCode(),
                objects[i + 1].hashCode());
        }
    }

    /**
     * Verifies that all the given objects are equal
     * 
     * @param objects
     *            the objects
     */
    private void allEquals(Object... objects) {
        for (int i = 0; i < objects.length - 1; i++) {
            Assert.assertEquals(objects[i], objects[i + 1]);
        }
    }

    /**
     * Verifies that these are all different objects
     * 
     * @param objects
     *            the objects
     */
    private void noneEquals(Object... objects) {
        for (int i = 0; i < objects.length - 1; i++) {
            for (int j = i + 1; j < objects.length; j++) {
                Assert.assertNotEquals(objects[i], objects[j]);
            }
        }
    }

    /**
     * Run various tests on Single and MutableSingle
     */
    @Test
    public void test_Single_MutableSingle_integration() {

        String item1 = "first element";

        Object[] equalObjects = {
            Tuples.of(item1),
            Tuples.copyOf(Tuples.of(item1)),
            Tuples.mutableOf(item1),
            Tuples.mutableCopyOf(Tuples.of(item1)),
            Tuples.newMutableSingle().copyFrom(Tuples.mutableOf(item1)),
            Tuples.newMutableSingle().item1(item1)
        };

        allEqualHashes(equalObjects);

        allEquals(equalObjects);

        Object[] nonEqualObjects = {

            Tuples.of(null),
            Tuples.of("1"),
            Tuples.copyOf(Tuples.of("2")),
            Tuples.mutableOf("3"),
            Tuples.mutableCopyOf(Tuples.of("4")),
            Tuples.newMutableSingle().item1("5"),
            Tuples.newMutableSingle()
                .copyFrom(Tuples.mutableOf("6"))

        };

        noneEquals(nonEqualObjects);

    }

    /**
     * Run tests on Single
     */
    public void test_Single() {

        String item1 = "first item";

        String item1bad = "bad first item";

        Tuples.of(item1)
            /* identity */
            .doConsume(t -> Assert.assertEquals(t, t))
            /* copy */
            .doConsume(t -> Assert.assertEquals(t, Tuples.copyOf(t)))
            /* content */
            .doConsume(t -> Assert.assertEquals(item1, t.item1()))
            .doConsume(t -> Assert.assertNotEquals(item1bad, t.item1()))
            /* new single */
            .doConsume(t -> Assert.assertEquals(t, Tuples.of(item1)))
            .doConsume(t -> Assert.assertNotEquals(t, Tuples.of(item1bad)))
            /* new mutable single */
            .doConsume(
                t -> Assert.assertEquals(t, Tuples.mutableOf(item1)))
            .doConsume(
                t -> Assert.assertNotEquals(t, Tuples.mutableOf(item1bad)))
            /* mutable copy */
            .doConsume(
                t -> Assert.assertEquals(t, Tuples.mutableCopyOf(t)))
            /* ifThenElse */
            .ifThenElse(t -> true, Assert::assertNotNull,
                t -> Assert.fail(t.item1()))
            .ifThenElse(t -> false, t -> Assert.fail(),
                Assert::assertNotNull)
            /* ifDo */
            .ifDo(t -> true, Assert::assertNotNull)
            .ifDo(t -> false, t -> Assert.fail())
            /* filter */
            .doConsume(
                t -> Assert.assertEquals(t, t.filter(t1 -> true).get()))
            .doConsume(t -> Assert
                .assertFalse(t.filter(t1 -> false).isPresent()))
            /* map */
            .doConsume(t -> Assert.assertEquals(item1.length(),
                t.map(t1 -> t1.item1().length()).get().intValue()));

    }

}
