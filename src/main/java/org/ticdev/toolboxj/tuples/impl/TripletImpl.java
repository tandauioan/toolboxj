package org.ticdev.toolboxj.tuples.impl;

import org.ticdev.toolboxj.tuples.Triplet;
import org.ticdev.toolboxj.tuples.TripletView;
import org.ticdev.toolboxj.tuples.TupleSupport;

/**
 * Concrete implementation of {@link Triplet}.
 *
 * @param <T1> the type of the first element
 * @param <T2> the type of the second element
 * @param <T3> the type of the third element
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public final class TripletImpl<T1, T2, T3>
    implements Triplet<T1, T2, T3> {

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
   * The third element.
   */
  private final T3 item3;

  /**
   * Cached hash code.
   */
  private int cachedHashCode;

  /**
   * Class constructor.
   *
   * @param item1Value the first element
   * @param item2Value the second element
   * @param item3Value the third element
   */
  public TripletImpl(
      final T1 item1Value,
      final T2 item2Value,
      final T3 item3Value) {
    this.item1 = item1Value;
    this.item2 = item2Value;
    this.item3 = item3Value;
  }

  /**
   * Copy constructor.
   *
   * @param source the triplet from which to copy the value
   */
  public TripletImpl(final TripletView<T1, T2, T3> source) {
    this(source.item1(), source.item2(), source.item3());
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
  public T3 item3() {
    return item3;
  }

  @Override
  public int hashCode() {
    if (cachedHashCode == 0) {
      cachedHashCode =
          TupleSupport.hashCode(item1, item2, item3);
    }
    return cachedHashCode;
  }

  @Override
  public boolean equals(final Object obj) {
    return this == obj
        || obj instanceof TripletView<?, ?, ?>
        && TupleSupport.tripletEquals(
        this,
        (TripletView<?, ?, ?>) obj);
  }

  @Override
  public Triplet<T1, T2, T3> self() {
    return this;
  }
}
