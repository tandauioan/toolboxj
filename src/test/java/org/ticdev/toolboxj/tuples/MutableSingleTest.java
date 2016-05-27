package org.ticdev.toolboxj.tuples;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.ticdev.toolboxj.support.ObjectSupport;
import org.ticdev.toolboxj.tuplesnew.MutableSingle;
import org.ticdev.toolboxj.tuplesnew.Single;
import org.ticdev.toolboxj.tuplesnew.TupleSupport;

/**
 * Test {@link MutableSingle}.
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public class MutableSingleTest {

    /**
     * Test construction.
     */
    @Test
    public void testConstruction() {

        final String item1 = "testitem1";

        final List<MutableSingle<String>> inputTuples = new LinkedList<>();
        inputTuples.add(TupleSupport.mutableOf(item1));
        inputTuples.add(TupleSupport.mutableCopyOf(
                TupleSupport.mutableOf(item1)));
        inputTuples
                .add(TupleSupport.mutableCopyOf(TupleSupport.of(item1)));
        inputTuples
                .add(TupleSupport.<String>newMutableSingle().item1(item1));
        inputTuples.add(TupleSupport.mutableOf(null));
        inputTuples.add(TupleSupport.mutableCopyOf(
                TupleSupport.mutableOf(null)));
        inputTuples.add(TupleSupport.mutableCopyOf(TupleSupport.of(null)));
        inputTuples.add(
                TupleSupport.<String>newMutableSingle().item1(item1)
                                                       .item1(null));

        final List<String> expectedContent = new LinkedList<>();
        expectedContent.add(item1);
        expectedContent.add(item1);
        expectedContent.add(item1);
        expectedContent.add(item1);
        expectedContent.add(null);
        expectedContent.add(null);
        expectedContent.add(null);
        expectedContent.add(null);

        /* none null */
        inputTuples.stream().forEach(Assert::assertNotNull);

        /* correct content */
        final Iterator<String> expectedContentIterator =
                expectedContent.iterator();
        inputTuples.stream().map(Single::item1).forEach(ims -> Assert
                .assertEquals(expectedContentIterator.next(), ims));

    }

    /**
     * Test mutable single equality
     */
    @Test
    public void testEqualObjects() {
        final String item1 = "testitem1";
        final Object[] objects = {
                TupleSupport.of(item1),
                TupleSupport.copyOf(TupleSupport.mutableOf(item1)),
                TupleSupport.mutableOf(item1),
                TupleSupport.mutableCopyOf(TupleSupport.mutableOf(item1)),
                TupleSupport.mutableCopyOf(TupleSupport.of(item1))
        };
        Assert.assertTrue(ObjectSupport.allNonNull(objects));
        Assert.assertTrue(ObjectSupport.allEqualHashes(objects));
        Assert.assertTrue(ObjectSupport.allEqual(objects));

    }

}
