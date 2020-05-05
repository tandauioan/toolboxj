package org.ticdev.toolboxj.tuples;

import org.ticdev.toolboxj.collections.IntIndexedGetter;
import org.ticdev.toolboxj.collections.IntSized;
import org.ticdev.toolboxj.functions.BinaryFunction;
import org.ticdev.toolboxj.tuples.impl.TupleIndexedLookup;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;

/**
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public interface PairView<T1, T2>
extends
    Serializable,
    IntSized,
    IntIndexedGetter<Object>,
    Iterable<Object> {

  T1 item1();
  T2 item2();

  default <R> R map(BinaryFunction<T1, T2, R> mapper) {
    return mapper.apply(item1(), item2());
  }

  @Override
  default int size() {
    return TupleIndexedLookup.PAIR_SIZE;
  }

  @Override
  default Object get(int index) throws IndexOutOfBoundsException {
    return TupleIndexedLookup.PAIR.get(this, index);
  }

  @Override
  default Iterator<Object> iterator() {
    return Arrays.<Object>asList(item1(), item2()).iterator();
  }

}
