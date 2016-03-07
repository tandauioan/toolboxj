package org.ticdev.toolboxj.tuples;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.ticdev.toolboxj.tools.ObjectTools;

/**
 * Test {@link MutableSingle}.
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 */
public class MutableSingleTest {

    @Test
    public void testConstruction() {

        final String item1 = "testitem1";

        final List<MutableSingle<String>> inputTuples = new LinkedList<>();
        inputTuples.add(Tuples.mutableOf(item1));
        inputTuples.add(Tuples.mutableCopyOf(Tuples.mutableOf(item1)));
        inputTuples.add(Tuples.mutableCopyOf(Tuples.of(item1)));
        inputTuples.add(Tuples.<String> newMutableSingle().item1(item1));
        inputTuples.add(Tuples.mutableOf(null));
        inputTuples.add(Tuples.mutableCopyOf(Tuples.mutableOf(null)));
        inputTuples.add(Tuples.mutableCopyOf(Tuples.of(null)));
        inputTuples.add(
            Tuples.<String> newMutableSingle().item1(item1).item1(null));

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
        inputTuples.stream().forEach(ms -> Assert.assertNotNull(ms));

        /* correct content */
        final Iterator<String> expectedContentIterator =
            expectedContent.iterator();
        inputTuples.stream().map(ms -> ms.item1()).forEach(ims -> Assert
            .assertEquals(expectedContentIterator.next(), ims));

    }

    /**
     * Test mutable single equality
     */
    @Test
    public void testEqualObjects() {
        final String item1 = "testitem1";
        final Object[] objects = {
            Tuples.of(item1),
            Tuples.copyOf(Tuples.mutableOf(item1)),
            Tuples.mutableOf(item1),
            Tuples.mutableCopyOf(Tuples.mutableOf(item1)),
            Tuples.mutableCopyOf(Tuples.of(item1))
        };
        Assert.assertTrue(ObjectTools.allNonNull(objects));
        Assert.assertTrue(ObjectTools.allEqualHashes(objects));
        Assert.assertTrue(ObjectTools.allEqual(objects));

    }

}
