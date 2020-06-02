package org.ticdev.toolboxj.self;

import org.junit.Assert;
import org.junit.Test;
import org.ticdev.toolboxj.functions.UnaryFunction;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

/**
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public class SelfishTest {

  @Test
  public void testWrapper() {
    final long unboxed = 13L;
    final Long expected = 13L;
    Assert.assertSame(expected, Selfish.of(expected).get());
    Assert.assertEquals(expected, Selfish.of(unboxed).get());
    Assert.assertEquals(new Selfish<>(unboxed), Selfish.of(unboxed));
    final Selfish<Long> selfish = Selfish.of(expected);
    Assert.assertSame(selfish, selfish.self());
    final int firstHashCall = selfish.hashCode();
    final int secondHashCall = selfish.hashCode();
    Assert.assertEquals(firstHashCall, secondHashCall);
    Assert.assertEquals(Objects.hash(expected.hashCode()), firstHashCall);
    Assert.assertEquals(Selfish.of(expected), Selfish.of(expected));
    Assert.assertEquals(selfish, selfish);
    Assert.assertNotEquals(Selfish.of(expected), null);
    Assert.assertNotEquals(Selfish.of(expected), String.valueOf(unboxed));
    Assert.assertNotEquals(
        Selfish.of(expected),
        Selfish.of(String.valueOf(expected)));
  }

  @Test
  public void testSelfishConsume() {
    final Long longValue = 13L;
    final AtomicLong sideEffect = new AtomicLong(0L);
    final Selfish<Long> expectedSelfish = Selfish.of(longValue);
    Assert.assertEquals(
        expectedSelfish,
        expectedSelfish.selfishConsume(sideEffect::set)
    );
    Assert.assertEquals(sideEffect.get(), longValue.longValue());
  }

  @Test
  public void testSelfishMap() {
    final int initialValue = 13;
    final String expectedValue = String.valueOf(13);
    final UnaryFunction<Integer, String> mapper = String::valueOf;
    final Selfish<Integer> si = Selfish.of(initialValue);
    final Selfish<String> expectedSelfish = Selfish.of(expectedValue);
    Assert.assertEquals(expectedSelfish, si.selfishMap(mapper));
  }

}