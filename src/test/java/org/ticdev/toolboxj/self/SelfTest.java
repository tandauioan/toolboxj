package org.ticdev.toolboxj.self;

import org.junit.Assert;
import org.junit.Test;
import org.ticdev.toolboxj.functions.UnaryFunction;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Test for {@link Self}.
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public class SelfTest {

  /**
   * Concrete self implementation test class.
   *
   * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
   */
  static final class SelfImpl
      implements
      Self<SelfImpl> {

    @Override
    public SelfImpl self() {
      return this;
    }
  }

  /**
   * Test self instance.
   */
  static SelfImpl instance1 = new SelfImpl();

  /**
   * Test self instance.
   */
  static SelfImpl instance2 = new SelfImpl();

  /**
   * Ensures that {@link Self#self()} returns the instance.
   */
  @Test
  public void testSelf() {
    Assert.assertSame(instance1, instance1.self());
  }

  @Test
  public void testSelfConsume() {
    final AtomicReference<Object> sideEffectReference =
        new AtomicReference<>(null);
    Assert.assertSame(
        instance1,
        instance1.selfConsume(sideEffectReference::set)
    );
    Assert.assertSame(
        instance1,
        sideEffectReference.get()
    );
  }

  @Test
  public void testSelfMap() {
    Assert.assertSame(
        instance1,
        instance1.selfMap(UnaryFunction.identity())
    );
    Assert.assertSame(
        instance2,
        instance1.selfMap(s -> instance2)
    );
  }

  @Test
  public void testSelfFilteredMap() {
    Assert.assertSame(
        instance1,
        instance1.selfFilteredMap(
            instance -> instance1 == instance,
            UnaryFunction.identity(),
            instance -> instance2
        )
    );
    Assert.assertSame(
        instance2,
        instance1.selfFilteredMap(
            instance -> instance1 != instance,
            UnaryFunction.identity(),
            instance -> instance2
        )
    );
  }

  @Test
  public void testSelfFilter() {
    Assert.assertTrue(
        instance1.selfFilter(instance -> instance == instance1).isPresent()
    );
    Assert.assertFalse(
        instance2.selfFilter(instance -> instance == instance1).isPresent()
    );
  }

}
