package org.ticdev.toolboxj.tuples.impl;

import org.junit.Assert;
import org.junit.Test;
import org.ticdev.toolboxj.tuples.Either;

/**
 * Test class for {@link EitherRightImpl}.
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public class EitherRightImplTest {

  private final Long eitherValue = 12L;
  private final Either<?, ?> eitherFirst = Either.right(eitherValue);
  private final Either<?, ?> eitherLikeFirst =
      Either.left(eitherValue).swap();

  @Test
  public void testSelf() {
    Assert.assertSame(eitherFirst, eitherFirst.self());
  }

  @Test
  public void testToString() {
    Assert.assertEquals(
        eitherFirst.toString(),
        eitherLikeFirst.toString());
  }

  @Test
  public void testHashCode() {
    Assert.assertEquals(
        eitherFirst.hashCode(),
        eitherFirst.hashCode()
    );
    Assert.assertEquals(
        eitherFirst.hashCode(),
        eitherLikeFirst.hashCode());
  }

}