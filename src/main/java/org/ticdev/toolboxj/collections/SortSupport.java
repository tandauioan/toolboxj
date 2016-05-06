package org.ticdev.toolboxj.collections;

import java.util.Comparator;

/**
 * Support methods for sorting algorithms.
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public class SortSupport {

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
                if(mean==Integer.MAX_VALUE) {
                    return -1;
                }
                l = mean + 1;
            } else if (c < 0) {
                if(mean==0) {
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
            long c = comp.compare(search, gs.get(mean));
            if (c > 0) {
                if(mean==Long.MAX_VALUE) {
                    return -1;
                }
                l = mean + 1;
            } else if (c < 0) {
                if(mean==0) {
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
