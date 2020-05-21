package org.ticdev.toolboxj.tuples.impl;

import org.junit.Assert;
import org.junit.Test;
import org.ticdev.toolboxj.tuples.Single;
import org.ticdev.toolboxj.tuples.TupleSupport;

/**
 * Test class for {@link org.ticdev.toolboxj.tuples.impl.SingleImpl}.
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public class SingleImplTest {

  private final Long singleValue = 123L;
  private final Single<?> singleFirst = TupleSupport.of(singleValue);
  private final Single<?> singleLikeFirst = TupleSupport.of(singleValue);
  private final Long differentValue = 321L;
  private final Single<?> singleDifferent =
      TupleSupport.of(differentValue);

  @Test
  public void testSelf() {
    Assert.assertSame(singleFirst, singleFirst.self());
    Assert.assertNotSame(singleFirst, singleLikeFirst);
  }

  @Test
  public void testEquals() {
    Assert.assertEquals(singleFirst, singleFirst);
    Assert.assertEquals(singleFirst, singleLikeFirst);
    Assert.assertNotEquals(singleFirst, singleDifferent);
    Assert.assertNotEquals(singleFirst, singleValue);
  }
}