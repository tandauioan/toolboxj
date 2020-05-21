package org.ticdev.toolboxj.tuples;

import org.junit.Assert;
import org.junit.Test;
import org.ticdev.toolboxj.support.ObjectSupport;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

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
    inputTuples.forEach(Assert::assertNotNull);

    /* correct content */
    final Iterator<String> expectedContentIterator =
        expectedContent.iterator();
    inputTuples.stream().map(SingleView::item1).forEach(ims -> Assert
        .assertEquals(expectedContentIterator.next(), ims));

  }

  /**
   * Test mutable single equality
   */
  @Test
  public void testEqualObjects() {
    final String item1 = "testitem1";
    final String different = "testitem2";
    final MutableSingle<String> oFirst = TupleSupport.mutableOf(item1);
    final MutableSingle<String> oDifferent =
        TupleSupport.mutableOf(different);
    final Object[] objects = {
        oFirst,
        TupleSupport.copyOf(TupleSupport.mutableOf(item1)),
        TupleSupport.mutableOf(item1),
        TupleSupport.mutableCopyOf(TupleSupport.mutableOf(item1)),
        TupleSupport.mutableCopyOf(TupleSupport.of(item1)),
        TupleSupport.<String>newMutableSingle().copyFrom(oFirst)
    };
    for(Object objectFirst : objects) {
      Assert.assertNotNull(objectFirst);
      Assert.assertNotEquals(objectFirst, oDifferent);
      Assert.assertNotEquals(objectFirst, item1);
      for(Object objectSecond : objects) {
        Assert.assertEquals(objectFirst, objectSecond);
        Assert.assertEquals(
            objectFirst.hashCode(),
            objectSecond.hashCode());
      }
    }
  }

  @Test
  public void of() {
    String inputItem = "test item";
    String mutatedInputItem = "mutated test item";
    MutableSingle<String> mutable = MutableSingle.of(inputItem);
    Assert.assertEquals(inputItem, mutable.item1());
    Assert.assertEquals(
        mutatedInputItem,
        mutable.item1(mutatedInputItem).item1()
    );
  }

  @Test
  public void self() {
    String inputItem = "test item";
    MutableSingle<?> mutableSingle =
        TupleSupport.mutableOf(inputItem);
    Assert.assertSame(
        mutableSingle,
        mutableSingle.self()
    );
  }
}
