package org.ticdev.toolboxj.tuples;

import org.ticdev.toolboxj.collections.IntIndexedGetter;
import org.ticdev.toolboxj.collections.IntSized;
import org.ticdev.toolboxj.functions.UnaryFunction;
import org.ticdev.toolboxj.tuples.impl.TupleIndexedLookup;

import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;

/**
 * Single view interface.
 *
 * @param <T1> the type of the element.
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public interface SingleView<T1>
    extends
    Serializable,
    IntSized,
    IntIndexedGetter<Object>,
    Iterable<Object> {

  /**
   * Returns the element.
   *
   * @return the element.
   */
  T1 item1();

  /**
   * Maps the element with the given mapper.
   *
   * @param mapper the mapper.
   * @param <R>    the type of result returned by the mapper.
   * @return the result returned by the mapper.
   */
  default <R> R map(UnaryFunction<T1, R> mapper) {
    return mapper.apply(item1());
  }

  @Override
  default int size() {
    return TupleIndexedLookup.SINGLE_SIZE;
  }

  @Override
  default Object get(int index) throws IndexOutOfBoundsException {
    return TupleIndexedLookup.SINGLE.get(this, index);
  }

  @Override
  default Iterator<Object> iterator() {
    return Collections.<Object>singleton(item1()).iterator();
  }

}
