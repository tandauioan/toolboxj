package org.ticdev.toolboxj.tuples;

import org.ticdev.toolboxj.self.Self;

/**
 * Single tuple interface.
 *
 * @param <T1> the type of the element
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public interface Single<T1>
    extends
    SingleView<T1>,
    Self<Single<T1>> {

  /**
   * Creates a new single instance with the given element.
   *
   * @param item1 the element.
   * @param <T1>  the type of the element.
   * @return the new single instance.
   */
  static <T1> Single<T1> of(final T1 item1) {
    return TupleSupport.of(item1);
  }

}
