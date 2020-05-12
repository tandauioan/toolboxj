package org.ticdev.toolboxj.tuples.impl;

import org.ticdev.toolboxj.tuples.Pair;
import org.ticdev.toolboxj.tuples.PairView;
import org.ticdev.toolboxj.tuples.TupleSupport;

/**
 * Concrete implementation of {@link Pair}.
 *
 * @param <T1> the type of the first element
 * @param <T2> the type of the second element
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public final class PairImpl<T1, T2>
    implements Pair<T1, T2> {

  /**
   * Default serial version.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The first element.
   */
  private final T1 item1;

  /**
   * The second element.
   */
  private final T2 item2;

  /**
   * Cached hash code.
   */
  private int cachedHashCode;

  /**
   * Class constructor.
   *
   * @param item1Value the first element.
   * @param item2Value the second element.
   */
  public PairImpl(
      final T1 item1Value,
      final T2 item2Value) {
    this.item1 = item1Value;
    this.item2 = item2Value;
  }

  /**
   * Copy constructor.
   *
   * @param source the pair from which to copy the value
   */
  public PairImpl(final PairView<T1, T2> source) {
    this(source.item1(), source.item2());
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
  public int hashCode() {
    if (cachedHashCode == 0) {
      cachedHashCode = TupleSupport.hashCode(item1, item2);
    }
    return cachedHashCode;
  }

  @Override
  public boolean equals(final Object obj) {
    return this == obj
        || obj instanceof PairView<?, ?>
        && TupleSupport.pairEquals(this, (PairView<?, ?>) obj);
  }

  @Override
  public Pair<T1, T2> self() {
    return this;
  }
}
