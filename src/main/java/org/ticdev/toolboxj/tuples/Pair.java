package org.ticdev.toolboxj.tuples;

import org.ticdev.toolboxj.self.Self;

/**
 * Pair tuple interface.
 *
 * @param <T1> the type of the first element
 * @param <T2> the type of the second element
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public interface Pair<T1, T2>
    extends
    PairView<T1, T2>,
    Self<Pair<T1, T2>> {

  /**
   * Creates and returns a new instance of a pair initialized with the
   * given values.
   *
   * @param item1 the first element.
   * @param item2 the second element.
   * @param <T1>  the type of the first element.
   * @param <T2>  the type of the second element.
   * @return a new instance of pair.
   */
  static <T1, T2> Pair<T1, T2> of(
      final T1 item1,
      final T2 item2
  ) {
    return TupleSupport.of(item1, item2);
  }

}
