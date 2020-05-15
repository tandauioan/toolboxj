package org.ticdev.toolboxj.allocation;

import java.util.Collection;

/**
 * Simple object collector interface.
 *
 * @param <T> the type of object to be collected.
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public interface Collector<T> {

  /**
   * Releases an object to this collector.
   *
   * @param obj the object to release.
   */
  void release(T obj);

  /**
   * Releases len the objects starting from off, from the given object array.
   *
   * @param objArray  the object array
   * @param off       the offset
   * @param len       the length
   * @param setToNull true to set all the positions of the released
   *                  objects to null, and false otherwise.
   */
  default void
  release(T[] objArray, int off, int len, boolean setToNull) {
    if (off < 0 || len < 0 || objArray.length < len
        || objArray.length - len < off) {
      throw new IndexOutOfBoundsException();
    }
    if (setToNull) {
      while (len-- > 0) {
        release(objArray[off]);
        objArray[off++] = null;
      }
    } else {
      while (len-- > 0) {
        release(objArray[off++]);
      }
    }
  }

  /**
   * Like {@link #release(Object[], int, int, boolean)} using an offset of 0
   * and length of the array.
   *
   * @param objArray  the object array
   * @param setToNull true to set all the positions to null and false otherwise.
   */
  default void release(T[] objArray, boolean setToNull) {
    release(objArray, 0, objArray.length, setToNull);
  }

  /**
   * Like {@link #release(Object[], int, int, boolean)} without setting the
   * values to null.
   *
   * @param objArray the object array
   * @param off      the offset
   * @param len      the length
   */
  default void release(T[] objArray, int off, int len) {
    release(objArray, off, len, false);
  }

  /**
   * Like {@link #release(Object[], boolean)} without setting
   * the values to null.
   *
   * @param objArray the object array.
   */
  default void release(T[] objArray) {
    release(objArray, false);
  }

  /**
   * Collects all the objects in the collection.
   *
   * @param source          the source
   * @param clearCollection true to clear the collection when the
   *                        collector finishes, and false otherwise.
   * @return the source
   */
  default Collection<T>
  release(Collection<T> source, boolean clearCollection) {
    source.stream().filter(o -> {
      release(o);
      return true;
    });
    if (clearCollection) {
      source.clear();
    }
    return source;
  }

}
