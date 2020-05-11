package org.ticdev.toolboxj.tuples;

import org.ticdev.toolboxj.self.Self;

/**
 * Quad tuple interface.
 *
 * @param <T1> the type of the first element
 * @param <T2> the type of the second element
 * @param <T3> the type of the third element
 * @param <T4> the type of the fourth element
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public interface Quad<T1, T2, T3, T4>
    extends
    QuadView<T1, T2, T3, T4>,
    Self<Quad<T1, T2, T3, T4>> {

  /**
   * Creates a new instance of a quad initialized with the given values.
   *
   * @param item1 the first element.
   * @param item2 the second element.
   * @param item3 the third element.
   * @param item4 the fourth element.
   * @param <T1>  the type of the first element.
   * @param <T2>  the type of the second element.
   * @param <T3>  the type of the third element.
   * @param <T4>  the type of the fourth element.
   * @return a new instance of a quad.
   */
  static <T1, T2, T3, T4> Quad<T1, T2, T3, T4> of(
      final T1 item1,
      final T2 item2,
      final T3 item3,
      final T4 item4
  ) {
    return TupleSupport.of(item1, item2, item3, item4);
  }

}
