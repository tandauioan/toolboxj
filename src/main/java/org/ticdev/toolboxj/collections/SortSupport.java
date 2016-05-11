package org.ticdev.toolboxj.collections;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.RandomAccess;

/**
 * Support methods for sorting algorithms.
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public class SortSupport {

    /**
     * An integer sized collection cannot be larger than this value for binary
     * search insertion point to work.
     */
    public static final int INT_BINARY_SEARCH_INSERTION_POINT_SIZE_LIMIT
            = Integer.MAX_VALUE + Integer.MIN_VALUE > 0
                    ? -Integer.MIN_VALUE - 1
                    : Integer.MAX_VALUE - 1;
    /**
     * A long sized collection cannot be larger than this value for binary
     * search insertion point to work.
     */
    public static final long LONG_BINARY_SEARCH_INSERTION_POINT_SIZE_LIMIT
            = Long.MAX_VALUE + Long.MIN_VALUE > 0
                    ? -Long.MIN_VALUE - 1
                    : Long.MAX_VALUE - 1;

    ;
    
    /**
     * Binary search insertion point modes, when the element is in the collection.
     * 
     * <p>Example: e[0]=1, e[1]=1, e[2]=1. 
     * If {@link BinarySearchInsertionMode#NONE} then return  1. 
     * If {@link BinarySearchInsertionMode#FIRST} then return 0. 
     * If {@link BinarySearchInsertionMode#LAST} then return 3.</p>
     */
    public static enum BinarySearchInsertionMode {
        /**
         * First found index is returned
         */
        NONE,
        /**
         * Iterate to the first matching object from the found position and
         * return that index.
         */
        FIRST,
        /**
         * Iterator to the last matching object from the found position and
         * return that index.
         */
        LAST
    }

    /**
     * This method sorts the specified {@link IntIndexedGetterSetter} object
     * using the Comparator object sent as argument. The method uses heap sort,
     * thus it guarantees O(n*log n), is done in place, and it's not stable
     * (does not retain the relative position of equal elements).
     *
     * @param <T> the type of the elements in the {@link IntIndexedGetterSetter}
     * @param gs the {@link IntIndexedGetterSetter} object.
     * @param comp the comparator used to perform the sorting.
     */
    public static final <T> void heapSort(IntIndexedGetterSetter<T> gs,
                                          Comparator<T> comp) {
        T tmp;
        int n = gs.size();
        int i = n / 2;
        int v, w;
        while (true) {
            if (i > 0) {
                i--;
                v = i;
            } else {
                n--;
                if (n == 0) {
                    return;
                }
                tmp = gs.get(0);
                gs.set(0, gs.get(n));
                gs.set(n, tmp);
                v = 0;
            }
            w = 2 * v + 1;
            while (w < n) {
                if (w + 1 < n && comp.compare(gs.get(w + 1), gs.get(w)) > 0) {
                    w++;
                }
                if (comp.compare(gs.get(v), gs.get(w)) >= 0) {
                    break;
                }
                tmp = gs.get(v);
                gs.set(v, gs.get(w));
                gs.set(w, tmp);

                v = w;
                w = 2 * v + 1;
            }
        }
    }

    /**
     * This method sorts the specified {@link LongIndexedGetterSetter} object
     * using the Comparator object sent as argument. The method uses heap sort,
     * thus it guarantees O(n*log n), is done in place, and it's not stable
     * (does not retain the relative position of equal elements).
     *
     * @param <T> the type of the elements in the {@link IntIndexedGetterSetter}
     * @param gs the {@link IntIndexedGetterSetter} object.
     * @param comp the comparator used to perform the sorting.
     */
    public static final <T> void heapSort(LongIndexedGetterSetter<T> gs,
                                          Comparator<T> comp) {
        T tmp;
        long n = gs.size();
        long i = n / 2;
        long v, w;
        while (true) {
            if (i > 0) {
                i--;
                v = i;
            } else {
                n--;
                if (n == 0) {
                    return;
                }
                tmp = gs.get(0);
                gs.set(0, gs.get(n));
                gs.set(n, tmp);
                v = 0;
            }
            w = 2 * v + 1;
            while (w < n) {
                if (w + 1 < n && comp.compare(gs.get(w + 1), gs.get(w)) > 0) {
                    w++;
                }
                if (comp.compare(gs.get(v), gs.get(w)) >= 0) {
                    break;
                }
                tmp = gs.get(v);
                gs.set(v, gs.get(w));
                gs.set(w, tmp);

                v = w;
                w = 2 * v + 1;
            }
        }
    }

    /**
     * This method sorts the specified {@link List} object using the
     * {@link Comparator} object sent as argument. The method uses heap sort,
     * thus it guarantees O(n*log n), is done in place, and it's not stable
     * (does not retain the relative position of equal elements).
     *
     * <p>
     * This method should be used with lists that provide optimized access to
     * elements by index.</p>
     *
     * @param <T> the type of elements in the {@link List}
     * @param list the list
     * @param comp the comparator used to perform the sorting.
     * @see RandomAccess
     */
    public static final <T> void heapSort(List<T> list, Comparator<T> comp) {
        T tmp;
        int n = list.size();
        int i = n / 2;
        int v, w;
        while (true) {
            if (i > 0) {
                i--;
                v = i;
            } else {
                n--;
                if (n == 0) {
                    return;
                }
                tmp = list.get(0);
                list.set(0, list.get(n));
                list.set(n, tmp);
                v = 0;
            }
            w = 2 * v + 1;
            while (w < n) {
                if (w + 1 < n && comp.compare(list.get(w + 1), list.get(w)) > 0) {
                    w++;
                }
                if (comp.compare(list.get(v), list.get(w)) >= 0) {
                    break;
                }
                tmp = list.get(v);
                list.set(v, list.get(w));
                list.set(w, tmp);

                v = w;
                w = 2 * v + 1;
            }
        }
    }

    /**
     * The method searches for an object in the ascending sorted structure using
     * binary search. The result is either a positive value representing the
     * position where the object was found, or -1 if the object was not found.
     *
     * @param <T> the type of elements in the collection
     * @param gs the collection
     * @param comp the comparator matching the sorting order of the objects in
     * the collection
     * @param search the object to search for
     * @return either a positive value representing the position where the
     * searched element was found, or -1 if the object was not found.
     */
    public static final <T> int binarySearch(IntIndexedGetterSetter<T> gs,
                                             Comparator<T> comp, T search) {
        int l = 0;
        int r = gs.size() - 1;
        int mean;
        while (l <= r) {
            mean = l + ((r - l) / 2);
            int c = comp.compare(search, gs.get(mean));
            if (c > 0) {
                if (mean == Integer.MAX_VALUE) {
                    return -1;
                }
                l = mean + 1;
            } else if (c < 0) {
                if (mean == 0) {
                    return -1;
                }
                r = mean - 1;
            } else {
                return mean;
            }
        }
        return -1;
    }

    /**
     * Does a binary search to find the insertion point in an ascending order
     * sorted collection.
     *
     *
     * <p>
     * This method will fail if the size of the collection is larger than the
     * given {@link #INT_BINARY_SEARCH_INSERTION_POINT_SIZE_LIMIT} limit.</p>
     *
     * <p>
     * If the search mode is {@link BinarySearchInsertionMode#NONE} and the
     * search element is found in the collection, the position of the element
     * will be returned as the insertion point.</p>
     *
     * * <p>
     * The insertion point is calculated from the function's return like this:
     * <code>insertion point = result &lt; 0 ? -(result + 1) : result</code></p>
     *
     *
     * @param <T> the type of elements in the collection
     * @param gs the collection direct access methods
     * @param comp the comparator instance
     * @param search the object to search for
     * @param mode the insertion point search mode
     * @return the insertion point as a positive value if the object was found
     * in the collection, and (-1 - insertion point) otherwise. If the value is
     * negative, the insertion point is obtained by taking the absolute value of
     * the result of adding 1 to the return value.
     * @throws IllegalStateException if the size assertions on insertion failed.
     */
    public static final <T> int binarySearchInsertionPoint(
            IntIndexedGetterSetter<T> gs, Comparator<T> comp, T search,
            BinarySearchInsertionMode mode) throws IllegalStateException {

        if (gs.size() > INT_BINARY_SEARCH_INSERTION_POINT_SIZE_LIMIT) {
            throw new IllegalArgumentException(String.format(
                    "INT_BINARY_SEARCH_INSERTION_POINT_SIZE_LIMIT: %d. Actual: %d",
                    INT_BINARY_SEARCH_INSERTION_POINT_SIZE_LIMIT, gs.size()));
        }
        int l = 0;
        int r = gs.size() - 1;
        int mean;
        int ins = 0;
        while (l <= r) {
            mean = l + (r - l) / 2;
            int c = comp.compare(search, gs.get(mean));
            if (c > 0) {
                if (mean == INT_BINARY_SEARCH_INSERTION_POINT_SIZE_LIMIT) {
                    return -1 - INT_BINARY_SEARCH_INSERTION_POINT_SIZE_LIMIT;
                }
                l = mean + 1;
                ins = l;
            } else if (c < 0) {
                if (mean == 0) {
                    return -1;
                }
                r = mean - 1;
                ins = r;
            } else {
                switch (mode) {
                    case FIRST:
                        T mObj = gs.get(mean);
                        while (mean > 0 && Objects.
                                equals(mObj, gs.get(mean - 1))) {
                            mean--;
                        }
                        break;
                    case LAST:
                        mObj = gs.get(mean);
                        int sz = gs.size() - 1;
                        while (mean < sz && Objects.equals(mObj, gs.
                                get(mean + 1))) {
                            mean++;
                        }
                        break;
                }
                return mean;
            }
        }
        if (ins <= 0) {
            return -1;
        } else {
            return -1 - ins;
        }
    }

    /**
     * Does a binary search to find the insertion point in an ascending order
     * sorted collection.
     *
     *
     * <p>
     * This method will fail if the size of the collection is larger than the
     * given {@link #LONG_BINARY_SEARCH_INSERTION_POINT_SIZE_LIMIT} limit.</p>
     *
     * <p>
     * If the search mode is {@link BinarySearchInsertionMode#NONE} and the
     * search element is found in the collection, the position of the element
     * will be returned as the insertion point.</p>
     *
     * <p>
     * The insertion point is calculated from the function's return like this:
     * <code>insertion point = result &lt; 0 ? -(result + 1) : result</code></p>
     *
     * @param <T> the type of elements in the collection
     * @param gs the collection direct access methods
     * @param comp the comparator instance
     * @param search the object to search for
     * @param mode the insertion point search mode
     * @return the insertion point as a positive value if the object was found
     * in the collection, and (-1 - insertion point) otherwise. If the value is
     * negative, the insertion point is obtained by taking the absolute value of
     * the result of adding 1 to the return value.
     * @throws IllegalStateException if the size assertions on insertion failed.
     */
    public static final <T> long binarySearchInsertionPoint(
            LongIndexedGetterSetter<T> gs, Comparator<T> comp, T search,
            BinarySearchInsertionMode mode) throws IllegalStateException {

        if (gs.size() > LONG_BINARY_SEARCH_INSERTION_POINT_SIZE_LIMIT) {
            throw new IllegalArgumentException(String.format(
                    "LONG_BINARY_SEARCH_INSERTION_POINT_SIZE_LIMIT: %d. Actual: %d",
                    LONG_BINARY_SEARCH_INSERTION_POINT_SIZE_LIMIT, gs.size()));
        }
        long l = 0;
        long r = gs.size() - 1;
        long mean;
        long ins = 0;
        while (l <= r) {
            mean = l + (r - l) / 2;
            int c = comp.compare(search, gs.get(mean));
            if (c > 0) {
                if (mean == INT_BINARY_SEARCH_INSERTION_POINT_SIZE_LIMIT) {
                    return -1 - INT_BINARY_SEARCH_INSERTION_POINT_SIZE_LIMIT;
                }
                l = mean + 1;
                ins = l;
            } else if (c < 0) {
                if (mean == 0) {
                    return -1;
                }
                r = mean - 1;
                ins = r;
            } else {
                switch (mode) {
                    case FIRST:
                        T mObj = gs.get(mean);
                        while (mean > 0 && Objects.
                                equals(mObj, gs.get(mean - 1))) {
                            mean--;
                        }
                        break;
                    case LAST:
                        mObj = gs.get(mean);
                        long sz = gs.size() - 1;
                        while (mean < sz && Objects.equals(mObj, gs.
                                get(mean + 1))) {
                            mean++;
                        }
                        break;
                }
                return mean;
            }
        }
        if (ins <= 0) {
            return -1;
        } else {
            return -1 - ins;
        }
    }
    
    /**
     * Does a binary search to find the insertion point in an ascending order
     * sorted list.
     *
     *
     * <p>
     * This method will fail if the size of the list is larger than the
     * given {@link #LONG_BINARY_SEARCH_INSERTION_POINT_SIZE_LIMIT} limit.</p>
     *
     * <p>
     * If the search mode is {@link BinarySearchInsertionMode#NONE} and the
     * search element is found in the list, the position of the element
     * will be returned as the insertion point.</p>
     *
     * <p>
     * The insertion point is calculated from the function's return like this:
     * <code>insertion point = result &lt; 0 ? -(result + 1) : result</code></p>
     *
     * @param <T> the type of elements in the list
     * @param list the list
     * @param comp the comparator instance
     * @param search the object to search for
     * @param mode the insertion point search mode
     * @return the insertion point as a positive value if the object was found
     * in the collection, and (-1 - insertion point) otherwise. If the value is
     * negative, the insertion point is obtained by taking the absolute value of
     * the result of adding 1 to the return value.
     * @throws IllegalStateException if the size assertions on insertion failed.
     */
    public static final <T> long binarySearchInsertionPoint(
            List<T> list, Comparator<T> comp, T search,
            BinarySearchInsertionMode mode) throws IllegalStateException {

        if (list.size() > INT_BINARY_SEARCH_INSERTION_POINT_SIZE_LIMIT) {
            throw new IllegalArgumentException(String.format(
                    "INT_BINARY_SEARCH_INSERTION_POINT_SIZE_LIMIT: %d. Actual: %d",
                    INT_BINARY_SEARCH_INSERTION_POINT_SIZE_LIMIT, list.size()));
        }
        int l = 0;
        int r = list.size() - 1;
        int mean;
        int ins = 0;
        while (l <= r) {
            mean = l + (r - l) / 2;
            int c = comp.compare(search, list.get(mean));
            if (c > 0) {
                if (mean == INT_BINARY_SEARCH_INSERTION_POINT_SIZE_LIMIT) {
                    return -1 - INT_BINARY_SEARCH_INSERTION_POINT_SIZE_LIMIT;
                }
                l = mean + 1;
                ins = l;
            } else if (c < 0) {
                if (mean == 0) {
                    return -1;
                }
                r = mean - 1;
                ins = r;
            } else {
                switch (mode) {
                    case FIRST:
                        T mObj = list.get(mean);
                        while (mean > 0 && Objects.
                                equals(mObj, list.get(mean - 1))) {
                            mean--;
                        }
                        break;
                    case LAST:
                        mObj = list.get(mean);
                        long sz = list.size() - 1;
                        while (mean < sz && Objects.equals(mObj, list.
                                get(mean + 1))) {
                            mean++;
                        }
                        break;
                }
                return mean;
            }
        }
        if (ins <= 0) {
            return -1;
        } else {
            return -1 - ins;
        }
    }

    /**
     * The method searches for an object in the ascending sorted list using
     * binary search. The result is either a positive value representing the
     * position where the object was found, or -1 if the object was not found.
     *
     * <p>
     * This method should be used with lists that provide optimized access to
     * elements by index.</p>
     *
     * @param <T> the type of elements in the list
     * @param list the list
     * @param comp the comparator matching the sorting order of the objects in
     * the list
     * @param search the object to search for
     * @return either a positive value representing the position where the
     * searched element was found, or -1 if the object was not found.
     * @see RandomAccess
     */
    public static final <T> int binarySearch(List<T> list,
                                             Comparator<T> comp, T search) {
        int l = 0;
        int r = list.size() - 1;
        int mean;
        while (l <= r) {
            mean = l + ((r - l) / 2);
            int c = comp.compare(search, list.get(mean));
            if (c > 0) {
                if (mean == Integer.MAX_VALUE) {
                    return -1;
                }
                l = mean + 1;
            } else if (c < 0) {
                if (mean == 0) {
                    return -1;
                }
                r = mean - 1;
            } else {
                return mean;
            }
        }
        return -1;
    }

    /**
     * The method searches for an object in the ascending sorted structure using
     * binary search. The result is either a positive value representing the
     * position where the object was found, or -1 if the object was not found.
     *
     * @param <T> the type of elements in the collection
     * @param gs the collection
     * @param comp the comparator matching the sorting order of the objects in
     * the collection
     * @param search the object to search for
     * @return either a positive value representing the position where the
     * searched element was found, or -1 if the object was not found.
     */
    public static final <T> long binarySearch(LongIndexedGetterSetter<T> gs,
                                              Comparator<T> comp, T search) {
        long l = 0;
        long r = gs.size() - 1;
        long mean;
        while (l <= r) {
            mean = l + ((r - l) / 2);
            int c = comp.compare(search, gs.get(mean));
            if (c > 0) {
                if (mean == Long.MAX_VALUE) {
                    return -1;
                }
                l = mean + 1;
            } else if (c < 0) {
                if (mean == 0) {
                    return -1;
                }
                r = mean - 1;
            } else {
                return mean;
            }
        }
        return -1;
    }

}
