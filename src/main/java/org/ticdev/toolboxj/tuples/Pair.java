package org.ticdev.toolboxj.tuples;

import org.ticdev.toolboxj.collections.IntIndexedGetter;
import org.ticdev.toolboxj.collections.IntSized;
import org.ticdev.toolboxj.self.Self;
import org.ticdev.toolboxj.tuples.impl.TupleIndexedLookup;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;

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

  static <T1, T2> Pair<T1, T2> of(
      final T1 item1,
      final T2 item2
  ) {
    return TupleSupport.of(item1, item2);
  }

}
