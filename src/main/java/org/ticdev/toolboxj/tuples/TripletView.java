package org.ticdev.toolboxj.tuples;

import org.ticdev.toolboxj.collections.IntIndexedGetter;
import org.ticdev.toolboxj.collections.IntSized;
import org.ticdev.toolboxj.functions.TernaryFunction;
import org.ticdev.toolboxj.tuples.impl.TupleIndexedLookup;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;

/**
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public interface TripletView<T1, T2, T3>
    extends
    Serializable,
    IntSized,
    IntIndexedGetter<Object>,
    Iterable<Object> {

  T1 item1();

  T2 item2();

  T3 item3();

  default <R> R map(final TernaryFunction<T1, T2, T3, R> mapper) {
    return mapper.apply(item1(), item2(), item3());
  }

  @Override
  default int size() {
    return TupleIndexedLookup.TRIPLET_SIZE;
  }

  @Override
  default Object get(int index) throws IndexOutOfBoundsException {
    return TupleIndexedLookup.TRIPLET.get(this, index);
  }

  @Override
  default Iterator<Object> iterator() {
    return Arrays.<Object>asList(item1(), item2(), item3()).iterator();
  }
}
