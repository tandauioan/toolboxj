package org.ticdev.toolboxj.tuples;

import org.ticdev.toolboxj.collections.IntIndexedGetter;
import org.ticdev.toolboxj.collections.IntSized;
import org.ticdev.toolboxj.functions.TernaryFunction;
import org.ticdev.toolboxj.tuples.impl.TupleIndexedLookup;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Triplet view interface.
 *
 * @param <T1> the type of the first element.
 * @param <T2> the type of the second element.
 * @param <T3> the type of the third element.
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public interface TripletView<T1, T2, T3>
    extends
    Serializable,
    IntSized,
    IntIndexedGetter<Object>,
    Iterable<Object> {

  /**
   * Returns the first item.
   *
   * @return the first item.
   */
  T1 item1();

  /**
   * Returns the second item.
   *
   * @return the second item.
   */
  T2 item2();

  /**
   * Returns the third item.
   *
   * @return the third item.
   */
  T3 item3();

  /**
   * Passes this triplet's element through to the given mapper and
   * returns its result.
   *
   * @param mapper the mapper function.
   * @param <R>    the type of result returned by the mapper.
   * @return the result returned by the mapper.
   */
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
