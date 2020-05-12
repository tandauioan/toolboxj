package org.ticdev.toolboxj.tuples.impl;


import org.ticdev.toolboxj.tuples.MutableTriplet;
import org.ticdev.toolboxj.tuples.Triplet;
import org.ticdev.toolboxj.tuples.TripletView;
import org.ticdev.toolboxj.tuples.TupleSupport;

/**
 * Concrete implementation of {@link MutableTriplet}.
 *
 * @param <T1> the type of the first element
 * @param <T2> the type of the second element
 * @param <T3> the type of the third element
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public final class MutableTripletImpl<T1, T2, T3>
    implements MutableTriplet<T1, T2, T3> {

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
   * The third element.
   */
  private T3 item3;

  /**
   * Class constructor.
   *
   * @param item1Value the first element
   * @param item2Value the second element
   * @param item3Value the third element
   */
  public MutableTripletImpl(
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
   * @param source the triplet from which to copy the values
   */
  public MutableTripletImpl(final TripletView<T1, T2, T3> source) {
    this(source.item1(), source.item2(), source.item3());
  }

  @Override
  public MutableTriplet<T1, T2, T3> item1(final T1 item1Value) {
    this.item1 = item1Value;
    return this;
  }

  @Override
  public MutableTriplet<T1, T2, T3> item2(final T2 item2Value) {
    this.item2 = item2Value;
    return this;
  }

  @Override
  public MutableTriplet<T1, T2, T3> item3(final T3 item3Value) {
    this.item3 = item3Value;
    return this;
  }

  @Override
  public MutableTriplet<T1, T2, T3> copyFrom(
      final Triplet<T1, T2, T3> source) {
    this.item1 = source.item1();
    this.item2 = source.item2();
    this.item3 = source.item3();
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
  public T3 item3() {
    return item3;
  }

  @Override
  public int hashCode() {
    return TupleSupport.hashCode(item1, item2, item3);
  }

  @Override
  public boolean equals(final Object obj) {
    return this == obj
        || obj instanceof TripletView<?, ?, ?>
        && TupleSupport.tripletEquals(
        this, (TripletView<?, ?, ?>) obj);
  }

  @Override
  public MutableTriplet<T1, T2, T3> self() {
    return this;
  }
}
