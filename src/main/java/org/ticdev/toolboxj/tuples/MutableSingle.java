package org.ticdev.toolboxj.tuples;

import org.ticdev.toolboxj.self.Self;

/**
 * Mutable single tuple interface.
 *
 * @param <T1> the type of the first element
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public interface MutableSingle<T1>
    extends
    SingleView<T1>,
    Self<MutableSingle<T1>> {

  /**
   * Sets the value of the first element.
   *
   * @param item1 the value
   * @return this instance
   */
  MutableSingle<T1> item1(T1 item1);

  /**
   * Copy the value from the given single.
   *
   * @param source the single to copy from
   * @return this instance
   */
  MutableSingle<T1> copyFrom(SingleView<T1> source);

  /**
   * Creates a new instance of a mutable single with the given
   * initial value.
   *
   * @param item1 the first element.
   * @param <T1>  the type of the first element.
   * @return the new instance of a mutable single.
   */
  static <T1> MutableSingle<T1> of(final T1 item1) {
    return TupleSupport.mutableOf(item1);
  }
}
