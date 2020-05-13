package org.ticdev.toolboxj.tuples;

import org.ticdev.toolboxj.collections.IntIndexedGetter;
import org.ticdev.toolboxj.collections.IntSized;
import org.ticdev.toolboxj.functions.QuadFunction;
import org.ticdev.toolboxj.tuples.impl.TupleIndexedLookup;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;

/**
 * A quad view. Base interface for four element containers / tuples.
 *
 * @param <T1> the type of the first element.
 * @param <T2> the type of the second element.
 * @param <T3> the type of the third element.
 * @param <T4> the type of the fourth element.
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public interface QuadView<T1, T2, T3, T4>
    extends
    Serializable,
    IntSized,
    IntIndexedGetter<Object>,
    Iterable<Object> {

  /**
   * Returns the first element.
   *
   * @return the first element.
   */
  T1 item1();

  /**
   * Returns the second element.
   *
   * @return the second element.
   */
  T2 item2();

  /**
   * Returns the third element.
   *
   * @return the third element.
   */
  T3 item3();

  /**
   * Returns the fourth element.
   *
   * @return the fourth element.
   */
  T4 item4();

  /**
   * Maps the elements of the quad on the given quad function mapper.
   *
   * @param mapper the mapper.
   * @param <R>    the type of the result returned by the mapper.
   * @return the result returned by the mapper.
   */
  default <R> R map(final QuadFunction<T1, T2, T3, T4, R> mapper) {
    return mapper.apply(item1(), item2(), item3(), item4());
  }

  @Override
  default int size() {
    return TupleIndexedLookup.QUAD_SIZE;
  }

  @Override
  default Object get(int index) throws IndexOutOfBoundsException {
    return TupleIndexedLookup.QUAD.get(this, index);
  }

  @Override
  default Iterator<Object> iterator() {
    return Arrays.<Object>asList(item1(), item2(), item3(), item4())
        .iterator();
  }
}
