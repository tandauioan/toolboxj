package org.ticdev.toolboxj.collections;

import java.util.Arrays;

/**
 * Arrays support methods.
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public interface ArraySupport {

  /**
   * Creates and returns a reversed copy of the given array.
   *
   * @param <T> the type of the elements in the array
   * @param arr the array to copy reversed
   * @return the reversed copy of the array
   */
  static <T> T[] reverseCopy(final T[] arr) {
    T[] result = Arrays.copyOf(arr, arr.length);
    for (int i = 0; i < arr.length; i++) {
      result[result.length - i - 1] = arr[i];
    }
    return result;
  }

  /**
   * Reverses the given array in place.
   *
   * @param <T> the type of the elements in the array
   * @param arr the array to reverse
   */
  static <T> void reverse(final T[] arr) {
    int left = 0;
    int right = arr.length - 1;
    while (left < right) {
      T tmp = arr[left];
      arr[left++] = arr[right];
      arr[right--] = tmp;
    }
  }

  /**
   * Returns a string representation of an array.
   *
   * @param <T> the type of the elements in the array
   * @param arr the array
   * @return the string representation
   */
  static <T> String stringify(final T[] arr) {
    if (arr == null) {
      return "null";
    }
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("[");
    if (arr.length == 0) {
      return stringBuilder.append("]").substring(0);
    }
    if (arr.length == 1) {
      return stringBuilder.append(arr[0]).append("]").substring(0);
    }
    stringBuilder.append(arr[0]);
    for (int i = 1; i < arr.length; i++) {
      stringBuilder.append(", ").append(arr[i]);
    }
    return stringBuilder.append("]").substring(0);
  }

  /**
   * Returns an {@link IntIndexedGetterSetter} that wraps the given array and
   * exposes the region of the array delimited by the left and right
   * parameters. Index 0 corresponds to the left argument, and the size
   * corresponds to right-left+1, since both left and right are inclusive.
   *
   * @param <T>   the type of elements in the array
   * @param array the wrapped array
   * @param left  the left index
   * @param right the right index
   * @return the new {@link IntIndexedGetterSetter} instance
   * @throws NullPointerException     if the array is null
   * @throws IllegalArgumentException if either left or right are outside
   *                                  the bounds of the array, or if the
   *                                  right index is smaller than the right
   *                                  index
   */
  static <T> IntIndexedGetterSetter<T> indexedGetterSetterOf(
      final T[] array,
      final int left,
      final int right)
      throws
      NullPointerException,
      IllegalArgumentException {
    if (array == null) {
      throw new NullPointerException();
    }
    if (left < 0 || left >= array.length) {
      throw new IllegalArgumentException(
          String.format("Left index must "
                  +
                  "be within the bounds of the array. Actual: %d",
              left));
    }
    if (right < 0 || right >= array.length || right < left) {
      throw new IllegalArgumentException(String.format(
          "Right index must be within the bounds of the array. Actual: %d",
          right));
    }

    /*
     * return instance based on the offset to optimize index access
     */
    if (left > 0) {
      return new IntIndexedGetterSetter<T>() {

        private final int offset = left;

        private final int size = right - left + 1;

        @Override
        public T get(final int index)
            throws
            IndexOutOfBoundsException {
          return array[offset + index];
        }

        @Override
        public void set(final int index, final T value) {
          array[offset + index] = value;
        }

        @Override
        public int size() {
          return size;
        }
      };
    }

    return new IntIndexedGetterSetter<T>() {

      private final int size = right + 1;

      @Override
      public T get(final int index)
          throws
          IndexOutOfBoundsException {
        return array[index];
      }

      @Override
      public void set(final int index, final T value) {
        array[index] = value;
      }

      @Override
      public int size() {
        return size;
      }

    };

  }

  /**
   * Wraps the entire array into an {@link IntIndexedGetterSetter}.
   *
   * @param <T>   the type of elements in the array
   * @param array the wrapped array
   * @return the new {@link IntIndexedGetterSetter} instance
   * @throws NullPointerException     if the array is null
   * @throws IllegalArgumentException if the length of the array is 0.
   */
  static <T> IntIndexedGetterSetter<T> indexedGetterSetterOf(
      final T[] array)
      throws
      NullPointerException,
      IllegalArgumentException {
    return indexedGetterSetterOf(array, 0, array.length - 1);
  }

  /**
   * Checks that the offset and length are valid for an array of the
   * given length.
   *
   * @param arrayLength the length of the array
   * @param offset      the offset
   * @param length      the length from the offset
   * @throws IndexOutOfBoundsException if offset is negative,
   *                                   length is negative, or length is
   *                                   greater than arrayLength-offset.
   */
  static void validateArrayOffsetLength(
      int arrayLength, int offset, int length)
      throws
      IndexOutOfBoundsException {
    if (offset < 0 || length < 0 || arrayLength - offset < length) {
      throw new IndexOutOfBoundsException();
    }
  }

}
