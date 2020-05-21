package org.ticdev.toolboxj.tuples.impl;

import org.junit.Assert;
import org.junit.Test;
import org.ticdev.toolboxj.tuples.Either;

/**
 * Test class for {@link EitherLeftImpl}.
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public class EitherLeftImplTest {

  private final Long eitherValue = 12L;
  private final Either<?, ?> eitherFirst = Either.left(eitherValue);
  private final Either<?, ?> eitherLikeFirst =
      Either.right(eitherValue).swap();

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