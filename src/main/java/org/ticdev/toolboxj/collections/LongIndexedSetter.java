package org.ticdev.toolboxj.collections;

/**
 * Declaration for collections that provide long indexed write access to their
 * elements.
 *
 * @param <E> the type of elements in the collection
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public interface LongIndexedSetter<E> {

  /**
   * Sets the value at the given index.
   *
   * @param index the index
   * @param value the new value
   */
  void set(long index, E value);
}
