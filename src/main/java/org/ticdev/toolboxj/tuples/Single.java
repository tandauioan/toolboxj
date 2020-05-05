package org.ticdev.toolboxj.tuples;

import org.ticdev.toolboxj.collections.IntIndexedGetter;
import org.ticdev.toolboxj.collections.IntSized;
import org.ticdev.toolboxj.self.Self;
import org.ticdev.toolboxj.tuples.impl.TupleIndexedLookup;

import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;

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

  static <T1> Single<T1> of(final T1 item1) {
    return TupleSupport.of(item1);
  }

}
