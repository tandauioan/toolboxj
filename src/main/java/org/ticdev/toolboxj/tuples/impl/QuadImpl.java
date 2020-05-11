package org.ticdev.toolboxj.tuples.impl;

import org.ticdev.toolboxj.tuples.Quad;
import org.ticdev.toolboxj.tuples.QuadView;
import org.ticdev.toolboxj.tuples.TupleSupport;

/**
 * Concrete implementation of {@link Quad}.
 *
 * @param <T1> the type of the first element
 * @param <T2> the type of the second element
 * @param <T3> the type of the third element
 * @param <T4> the type of the fourth element
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public final class QuadImpl<T1, T2, T3, T4>
    implements Quad<T1, T2, T3, T4> {

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
   * The fourth element.
   */
  private final T4 item4;

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
   * @param item4Value the fourth element
   */
  public QuadImpl(
      final T1 item1Value,
      final T2 item2Value,
      final T3 item3Value,
      final T4 item4Value) {
    this.item1 = item1Value;
    this.item2 = item2Value;
    this.item3 = item3Value;
    this.item4 = item4Value;
  }

  /**
   * Copy constructor.
   *
   * @param source the quad from which to copy the value
   */
  public QuadImpl(final QuadView<T1, T2, T3, T4> source) {
    this(source.item1(), source.item2(), source.item3(),
        source.item4());
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
  public T4 item4() {
    return item4;
  }

  @Override
  public int hashCode() {
    if (cachedHashCode == 0) {
      cachedHashCode =
          TupleSupport.hashCode(item1, item2, item3, item4);
    }
    return cachedHashCode;
  }

  @Override
  public boolean equals(final Object obj) {
    return this == obj
        || obj instanceof QuadView<?, ?, ?, ?>
        && TupleSupport.quadEquals(
        this, (QuadView<?, ?, ?, ?>) obj);
  }

  @Override
  public Quad<T1, T2, T3, T4> self() {
    return this;
  }
}
