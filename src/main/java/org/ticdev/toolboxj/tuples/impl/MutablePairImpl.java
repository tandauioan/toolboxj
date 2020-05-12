package org.ticdev.toolboxj.tuples.impl;

import org.ticdev.toolboxj.tuples.MutablePair;
import org.ticdev.toolboxj.tuples.Pair;
import org.ticdev.toolboxj.tuples.PairView;
import org.ticdev.toolboxj.tuples.TupleSupport;

/**
 * Concrete implementation of {@link MutablePair}.
 *
 * @param <T1> the type of the first element
 * @param <T2> the type of the second element
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public final class MutablePairImpl<T1, T2>
    implements MutablePair<T1, T2> {

  /**
   * Default serial version.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The first element.
   */
  private T1 item1;

  /**
   * The second element.
   */
  private T2 item2;

  /**
   * Class constructor.
   *
   * @param item1Value the first element
   * @param item2Value the second element
   */
  public MutablePairImpl(final T1 item1Value, final T2 item2Value) {
    this.item1 = item1Value;
    this.item2 = item2Value;
  }

  /**
   * Copy constructor.
   *
   * @param source the source to copy from
   */
  public MutablePairImpl(final PairView<T1, T2> source) {
    this(source.item1(), source.item2());
  }

  @Override
  public MutablePair<T1, T2> item1(final T1 item1Value) {
    this.item1 = item1Value;
    return this;
  }

  @Override
  public MutablePair<T1, T2> item2(final T2 item2Value) {
    this.item2 = item2Value;
    return this;
  }

  @Override
  public T1 item1() {
    return item1;
  }

  @Override
  public T2 item2() {
    return item2;
  }

  @Override
  public MutablePair<T1, T2> copyFrom(
      final Pair<T1, T2> source) {
    this.item1 = source.item1();
    this.item2 = source.item2();
    return this;
  }

  @Override
  public int hashCode() {
    return TupleSupport.hashCode(item1, item2);
  }

  @Override
  public boolean equals(final Object obj) {
    return this == obj
        || obj instanceof PairView<?, ?>
        && TupleSupport.pairEquals(this, (PairView<?, ?>) obj);
  }

  @Override
  public MutablePair<T1, T2> self() {
    return this;
  }
}
