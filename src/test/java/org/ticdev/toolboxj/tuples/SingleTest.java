package org.ticdev.toolboxj.tuples;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.ticdev.toolboxj.support.ObjectSupport;

/**
 * Test a {@link Single}
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 */
public class SingleTest {

    /**
     * Test single construction
     */
    @Test
    public void testConstruction() {

        final String item1 = "testitem1";

        final List<Single<String>> inputTuples = new LinkedList<>();
        inputTuples.add(Tuples.of(item1));
        inputTuples.add(Tuples.copyOf(Tuples.of(item1)));
        inputTuples.add(Tuples.of(null));
        inputTuples.add(Tuples.copyOf(Tuples.of(null)));

        final List<String> expectedContent = new LinkedList<>();
        expectedContent.add(item1);
        expectedContent.add(item1);
        expectedContent.add(null);
        expectedContent.add(null);

        /* none null */
        inputTuples.stream().forEach(s -> Assert.assertNotNull(s));

        /* correct content */
        final Iterator<String> expectedContentIterator =
            expectedContent.iterator();
        inputTuples.stream().map(s -> s.item1()).forEach(is -> Assert
            .assertEquals(expectedContentIterator.next(), is));

    }

    /**
     * Test single equality
     */
    @Test
    public void testEqualObjects() {

        final String item1 = "testitem1";

        final Object[] objects = {

            Tuples.of(item1),

            Tuples.copyOf(Tuples.of(item1))

        };

        Assert.assertTrue(ObjectSupport.allNonNull(objects));

        Assert.assertTrue(ObjectSupport.allEqualHashes(objects));

        Assert.assertTrue(ObjectSupport.allEqual(objects));

    }

}
