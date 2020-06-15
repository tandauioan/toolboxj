package org.ticdev.toolboxj.functions;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Test class for {@link UnaryConsumerLong}.
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public class UnaryConsumerLongTest {

  @Test
  public void testRouting() {
    final AtomicReference<String> sideEffect =
        new AtomicReference<>(null);
    final String object = "object";
    final String primitive = "primitive";
    final String routed = "routed";
    UnaryConsumerLong ucl = l -> sideEffect.set(routed);
    final long value = 123L;
    ucl.accept(value);
    Assert.assertSame(routed, sideEffect.get());
    UnaryConsumerLong uclImpl = new UnaryConsumerLong() {

      @Override
      public void acceptLong(long arg1) {
        sideEffect.set(primitive);
      }

      @Override
      public void accept(Long arg1) {
        sideEffect.set(object);
      }
    };
    uclImpl.acceptLong(value);
    Assert.assertSame(primitive, sideEffect.get());
    uclImpl.accept(value);
    Assert.assertSame(object, sideEffect.get());
  }

}