package org.ticdev.toolboxj.collections;

import java.util.Arrays;

/**
 *
 * Arrays support methods.
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public class ArraysSupport {

    /**
     * Creates and returns a reversed copy of the given array
     *
     * @param <T> the type of the elements in the array
     * @param arr the array to copy reversed
     * @return the reversed copy of the array
     */
    public static <T> T[] reverseCopy(T[] arr) {
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
    public static <T> void reverse(T[] arr) {
        int left = 0;
        int right = arr.length - 1;
        while (left < right) {
            T tmp = arr[left];
            arr[left] = arr[right];
            arr[right] = tmp;
        }
    }

    /**
     * Returns a string representation of an array.
     *
     * @param arr the array
     * @return the string representation
     */
    public static <T> String stringify(T[] arr) {
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

}
