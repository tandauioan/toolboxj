package org.ticdev.toolboxj.collections;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Concrete implementation of a reusable iterator on an array.
 *
 * <p>
 * The underlying array can be changed at any time using the reset methods. The
 * implementation is not thread-safe.
 * </p>
 *
 * <p>
 * This implementation will expose internal representation by storing a
 * reference to the external array that is passed to it.
 * </p>
 *
 * @param <T> the type of elements in the array.
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public final class ReusableArrayIterator<T> implements Iterator<T> {

  /**
   * Current array.
   */
  private T[] elements = null;

  /**
   * Current offset.
   */
  private int off = 0;

  /**
   * Current length.
   */
  private int len = 0;

  /**
   * Private class constructor.
   */
  private ReusableArrayIterator() {
  }

  /**
   * Resets the iterator to the given array, offset and length.
   *
   * @param elementsArray the new array
   * @param offset        the offset in the array
   * @param length        the length
   * @return this instance
   */
  public ReusableArrayIterator<T> reset(
      final T[] elementsArray,
      final int offset,
      final int length) {
    if (offset < 0 || length < 0 || elementsArray.length - length < 0
        || elementsArray.length - length < offset) {
      throw new IndexOutOfBoundsException();
    }
    this.elements = elementsArray;
    this.off = offset;
    this.len = length;
    return this;
  }

  /**
   * Resets the iterator to the given array, 0 offset, and array length.
   *
   * @param elementsArray the new array
   * @return this instance
   */
  public ReusableArrayIterator<T> reset(final T[] elementsArray) {
    this.elements = elementsArray;
    this.off = 0;
    this.len = elementsArray.length;
    return this;
  }

  /**
   * Returns the number of elements remaining to iterate over.
   *
   * @return the number of elements remaining to iterate over.
   */
  public int remaining() {
    return len;
  }

  @Override
  public boolean hasNext() {
    return elements != null && len > 0;
  }

  @Override
  public T next() {
    T result = elements[off++];
    --len;
    return result;
  }

  /**
   * Returns a new, uninitialized instance of the iterator.
   *
   * @param <T> the type of the elements in the iterator
   * @return a new, uninitialized instance of the iterator
   */
  public static <T> ReusableArrayIterator<T> newInstance() {
    return new ReusableArrayIterator<>();
  }

  /**
   * Returns a new, initialized instance of the iterator.
   *
   * @param elements the object array
   * @param off      the offset
   * @param len      the length
   * @param <T>      the type of the elements in the iterator
   * @return a new, initialized instance of the iterator.
   */
  public static <T> ReusableArrayIterator<T>
  of(final T[] elements, final int off, final int len) {
    return ReusableArrayIterator.<T>newInstance().reset(elements, off,
        len);
  }

  /**
   * Returns a new, initialized instance of the iterator.
   *
   * @param elementsArray the object array
   * @param <T>           the type of the elements in the iterator.
   * @return a new, initialized instance of the iterator.
   */
  public static <T> ReusableArrayIterator<T> of(final T[] elementsArray) {
    return ReusableArrayIterator.<T>newInstance().reset(elementsArray);
  }

  /**
   * Returns a stream from the current state of the iterator.
   *
   * @return a stream from the current state of the iterator.
   */
  public Stream<T> stream() {
    return StreamSupport
        .stream(Spliterators.spliterator(this, len,
            Spliterator.SUBSIZED | Spliterator.SIZED
                | Spliterator.IMMUTABLE | Spliterator.ORDERED),
            false);
  }

}
