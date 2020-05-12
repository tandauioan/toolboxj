package org.ticdev.toolboxj.tuples;

import org.ticdev.toolboxj.self.Self;

/**
 * Triplet tuple interface.
 *
 * @param <T1> the type of the first element
 * @param <T2> the type of the second element
 * @param <T3> the type of the third element
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public interface Triplet<T1, T2, T3>
    extends
    TripletView<T1, T2, T3>,
    Self<Triplet<T1, T2, T3>> {

  /**
   * Creates a new triplet with the given elements.
   *
   * @param item1 the first element.
   * @param item2 the second element.
   * @param item3 the third element.
   * @param <T1>  the type of the first element.
   * @param <T2>  the type of the second element.
   * @param <T3>  the type of the third element.
   * @return a new instance of triplet.
   */
  static <T1, T2, T3> Triplet<T1, T2, T3> of(
      final T1 item1,
      final T2 item2,
      final T3 item3
  ) {
    return TupleSupport.of(item1, item2, item3);
  }

}
