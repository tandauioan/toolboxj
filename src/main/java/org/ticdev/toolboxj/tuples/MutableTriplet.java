package org.ticdev.toolboxj.tuples;

import org.ticdev.toolboxj.self.Self;

/**
 * Mutable triplet interface.
 *
 * @param <T1> the type of the first element
 * @param <T2> the type of the second element
 * @param <T3> the type of the third element
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public interface MutableTriplet<T1, T2, T3>
    extends
    TripletView<T1, T2, T3>,
    Self<MutableTriplet<T1, T2, T3>> {

  /**
   * Sets the value of the first element.
   *
   * @param item1 the value.
   * @return this instance.
   */
  MutableTriplet<T1, T2, T3> item1(T1 item1);

  /**
   * Sets the value of the second element.
   *
   * @param item2 the value.
   * @return this instance.
   */
  MutableTriplet<T1, T2, T3> item2(T2 item2);

  /**
   * Sets the value of the third element.
   *
   * @param item3 the value.
   * @return this instance.
   */
  MutableTriplet<T1, T2, T3> item3(T3 item3);

  /**
   * Copy the value from the given triplet.
   *
   * @param source the triplet to copy from
   * @return this instance
   */
  MutableTriplet<T1, T2, T3> copyFrom(Triplet<T1, T2, T3> source);

  /**
   * Creates a new instance of a mutable triplet initialized with the
   * given values.
   *
   * @param item1 the first element.
   * @param item2 the second element.
   * @param item3 the third element.
   * @param <T1>  the type of the first element.
   * @param <T2>  the type of the second element.
   * @param <T3>  the type of the third element.
   * @return a new instance of a mutable triplet.
   */
  static <T1, T2, T3> MutableTriplet<T1, T2, T3> of(
      final T1 item1,
      final T2 item2,
      final T3 item3
  ) {
    return TupleSupport.mutableOf(item1, item2, item3);
  }

}
