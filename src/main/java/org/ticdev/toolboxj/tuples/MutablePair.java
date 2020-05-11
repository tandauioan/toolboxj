package org.ticdev.toolboxj.tuples;

import org.ticdev.toolboxj.self.Self;

/**
 * Mutable pair interface.
 *
 * @param <T1> the type of the first element
 * @param <T2> the type of the second element
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public interface MutablePair<T1, T2>
    extends
    PairView<T1, T2>,
    Self<MutablePair<T1, T2>> {

  /**
   * Sets the value of the first element.
   *
   * @param item1 the value
   * @return this instance
   */
  MutablePair<T1, T2> item1(T1 item1);

  /**
   * Sets the value of the second element.
   *
   * @param item2 the value
   * @return this instance
   */
  MutablePair<T1, T2> item2(T2 item2);

  /**
   * Copy the value from the given pair.
   *
   * @param source the pair to copy from
   * @return this instance
   */
  MutablePair<T1, T2> copyFrom(Pair<T1, T2> source);

  /**
   * Creates a new mutable pair instance initialized with the given
   * values.
   *
   * @param item1 the first element.
   * @param item2 the second element.
   * @param <T1>  the type of the first element.
   * @param <T2>  the type of the second element.
   * @return a new mutable pair instance.
   */
  static <T1, T2> MutablePair<T1, T2> of(
      final T1 item1,
      final T2 item2
  ) {
    return TupleSupport.mutableOf(item1, item2);
  }

}
