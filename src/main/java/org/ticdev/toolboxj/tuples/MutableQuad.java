package org.ticdev.toolboxj.tuples;

import org.ticdev.toolboxj.self.Self;

/**
 * Mutable quad interface.
 *
 * @param <T1> the type of the first element
 * @param <T2> the type of the second element
 * @param <T3> the type of the third element
 * @param <T4> the type of the fourth element
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public interface MutableQuad<T1, T2, T3, T4>
    extends
    QuadView<T1, T2, T3, T4>,
    Self<MutableQuad<T1, T2, T3, T4>> {

  /**
   * Sets the value of the first element.
   *
   * @param item1 the value
   * @return this instance
   */
  MutableQuad<T1, T2, T3, T4> item1(T1 item1);

  /**
   * Sets the value of the second element.
   *
   * @param item2 the value
   * @return this instance
   */
  MutableQuad<T1, T2, T3, T4> item2(T2 item2);

  /**
   * Sets the value of the third element.
   *
   * @param item3 the value
   * @return this instance
   */
  MutableQuad<T1, T2, T3, T4> item3(T3 item3);

  /**
   * Sets the value of the fourth element.
   *
   * @param item4 the value
   * @return this instance
   */
  MutableQuad<T1, T2, T3, T4> item4(T4 item4);

  /**
   * Copy the value from the given quad.
   *
   * @param source the quad to copy from
   * @return this instance
   */
  MutableQuad<T1, T2, T3, T4> copyFrom(Quad<T1, T2, T3, T4> source);

  /**
   * Creates a new instance of a mutable quad initialized with the
   * given values.
   *
   * @param item1 the first element.
   * @param item2 the second element.
   * @param item3 the third element.
   * @param item4 the fourth element.
   * @param <T1>  the type of the first element.
   * @param <T2>  the type of the second element.
   * @param <T3>  the type of the third element.
   * @param <T4>  the type of the fourth element.
   * @return the new instance of a mutable quad.
   */
  static <T1, T2, T3, T4> MutableQuad<T1, T2, T3, T4> of(
      final T1 item1,
      final T2 item2,
      final T3 item3,
      final T4 item4
  ) {
    return TupleSupport.mutableOf(item1, item2, item3, item4);
  }

}
