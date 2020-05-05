package org.ticdev.toolboxj.tuples;

import org.ticdev.toolboxj.collections.IntIndexedGetter;
import org.ticdev.toolboxj.collections.IntSized;
import org.ticdev.toolboxj.self.Self;
import org.ticdev.toolboxj.tuples.impl.TupleIndexedLookup;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Triplet tuple interface
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


  static <T1, T2, T3> Triplet<T1, T2, T3> of(
      final T1 item1,
      final T2 item2,
      final T3 item3
  ) {
    return TupleSupport.of(item1, item2, item3);
  }

}
