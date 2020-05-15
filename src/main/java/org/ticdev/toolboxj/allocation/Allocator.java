package org.ticdev.toolboxj.allocation;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Simple object allocator interface.
 *
 * <p>
 * Concrete implementations are required to create new objects for each call, or
 * it can reuse objects if it guarantees that they are no longer used.
 * </p>
 *
 * <p>
 * Bulk allocation methods will call the {@link #allocate()} method by default
 * if no other strategy is used.
 * </p>
 *
 * @param <T> the type of objects allocated by this allocator
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public interface Allocator<T> {

  /**
   * Allocate a new instance of type T.
   *
   * @return the new instance.
   */
  T allocate();

  /**
   * Create len new instances of type T starting from the given offset, into
   * the given array.
   *
   * @param objArray the destination array
   * @param off      the starting index
   * @param len      the number of elements to allocate.
   */
  default void allocate(T[] objArray, int off, int len) {
    if (off < 0 || len < 0 || objArray.length < len
        || objArray.length - len < off) {
      throw new IndexOutOfBoundsException();
    }
    while (len-- > 0) {
      objArray[off++] = allocate();
    }
  }

  /**
   * Fill the given array with new instances of type T.
   *
   * @param objArray the destination array
   */
  default void allocate(T[] objArray) {
    allocate(objArray, 0, objArray.length);
  }

  /**
   * Add len new instances of T to the given destination collection.
   *
   * <p>
   * If the collection is null, a new collection is created, populated, and
   * returned.
   * </p>
   *
   * @param destination the destination collection
   * @param len         the number of instances to add
   * @return the destination, or a new collection.
   */
  default Collection<T> allocate(Collection<T> destination, int len) {
    if (len < 0) {
      throw new IllegalArgumentException();
    }
    if (destination == null) {
      destination = new ArrayList<>(len);
    }
    while (len-- > 0) {
      destination.add(allocate());
    }
    return destination;
  }

}
