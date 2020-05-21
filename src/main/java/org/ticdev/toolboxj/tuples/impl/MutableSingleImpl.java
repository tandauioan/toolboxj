package org.ticdev.toolboxj.tuples.impl;

import org.ticdev.toolboxj.tuples.MutableSingle;
import org.ticdev.toolboxj.tuples.SingleView;
import org.ticdev.toolboxj.tuples.TupleSupport;

/**
 * Concrete mutable single implementation.
 *
 * @param <T1> the type of the first element
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public final class MutableSingleImpl<T1>
    implements MutableSingle<T1> {

  /**
   * Default serial version.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The first item.
   */
  private T1 item1;

  /**
   * Class constructor.
   *
   * @param item1Value the first element
   */
  public MutableSingleImpl(final T1 item1Value) {
    this.item1 = item1Value;
  }

  /**
   * Copy constructor.
   *
   * @param source the single from which to copy the value
   */
  public MutableSingleImpl(final SingleView<T1> source) {
    this(source.item1());
  }

  @Override
  public MutableSingle<T1> item1(final T1 item1Value) {
    this.item1 = item1Value;
    return this;
  }

  @Override
  public MutableSingle<T1> copyFrom(
      final SingleView<T1> source) {
    return this.item1(source.item1());
  }

  @Override
  public T1 item1() {
    return item1;
  }

  @Override
  public int hashCode() {
    return TupleSupport.hashCode(item1);
  }

  @Override
  public boolean equals(final Object obj) {
    return this == obj
        || obj instanceof SingleView<?>
        && TupleSupport.singleEquals(this, (SingleView<?>) obj);
  }

  @Override
  public MutableSingle<T1> self() {
    return this;
  }
}
