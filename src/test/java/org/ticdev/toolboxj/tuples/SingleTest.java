package org.ticdev.toolboxj.tuples;


import org.junit.Assert;
import org.junit.Test;
import org.ticdev.toolboxj.support.ObjectSupport;
import org.ticdev.toolboxj.tuplesnew.Single;
import org.ticdev.toolboxj.tuplesnew.TupleSupport;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Test a {@link Single}
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public class SingleTest {

    /**
     * Test single construction
     */
    @Test
    public void testConstruction() {

        final String item1 = "testitem1";

        final List<Single<String>> inputTuples = new LinkedList<>();
        inputTuples.add(TupleSupport.of(item1));
        inputTuples.add(TupleSupport.copyOf(TupleSupport.of(item1)));
        inputTuples.add(TupleSupport.of(null));
        inputTuples.add(TupleSupport.copyOf(TupleSupport.of(null)));

        final List<String> expectedContent = new LinkedList<>();
        expectedContent.add(item1);
        expectedContent.add(item1);
        expectedContent.add(null);
        expectedContent.add(null);

        /* none null */
        inputTuples.stream().forEach(Assert::assertNotNull);

        /* correct content */
        final Iterator<String> expectedContentIterator =
                expectedContent.iterator();
        inputTuples.stream().map(Single::item1).forEach(is -> Assert
                .assertEquals(expectedContentIterator.next(), is));

    }

    /**
     * Test single equality
     */
    @Test
    public void testEqualObjects() {

        final String item1 = "testitem1";

        final Object[] objects = {

                TupleSupport.of(item1),

                TupleSupport.copyOf(TupleSupport.of(item1))

        };

        Assert.assertTrue(ObjectSupport.allNonNull(objects));

        Assert.assertTrue(ObjectSupport.allEqualHashes(objects));

        Assert.assertTrue(ObjectSupport.allEqual(objects));

    }

}
