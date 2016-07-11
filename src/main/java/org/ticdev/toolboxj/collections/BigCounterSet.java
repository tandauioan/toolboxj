package org.ticdev.toolboxj.collections;

import org.ticdev.toolboxj.numbers.BigCounter;

import java.math.BigInteger;
import java.util.*;

/**
 * Implementation of a set-like container (not a {@link Set} though) that
 * maps a key to the number of times it was added to the set. The counter
 * for each key is a {@link BigCounter} so there are no concrete maximum
 * limits for the value (other than memory limitations).
 *
 * @param <K> the key type
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public final class BigCounterSet<K>
        implements Iterable<K> {

    /**
     * The number of elements
     */
    private final BigCounter size = new BigCounter();

    /**
     * The map.
     */
    private final Map<K, BigCounter> map = new HashMap<>();

    /**
     * Returns the number of elements
     *
     * @return the number of elements
     */
    public BigCounter size() {
        return new BigCounter(size);
    }

    /**
     * Returns the number of times an element was added to the set
     *
     * @param o the element
     * @return the number of times the element was added to the set
     */
    public BigInteger count(Object o) {
        BigCounter c = map.get(o);
        return c == null ? BigInteger.ZERO : c.getCounter();
    }

    /**
     * Returns true if there are no elements in this set and false
     * otherwise.
     *
     * @return true if there are no elements in this set and false
     * otherwise.
     */
    public boolean isEmpty() {
        return size.isZero();
    }

    /**
     * Returns true if the given object is contained in this set, and
     * false otherwise.
     *
     * @param o the object to test
     * @return true if the given object is contained in this set, and
     * false otherwise.
     */
    public boolean contains(Object o) {
        return map.containsKey(o);
    }

    /**
     * Returns an iterator over the elements in this set.
     *
     * @return an iterator over the elements in this set.
     */
    public Iterator<K> iterator() {
        return map.keySet().iterator();
    }

    /**
     * Adds the given element to the set. If the element is new then it is
     * associated with a count of 1. If there is already a count for the
     * elements then the count is incremented by 1.
     *
     * @param k the element
     * @return true if the set was modified and false otherwise.
     */
    public boolean add(K k) {
        BigCounter c = map.get(k);
        if (c == null) {
            map.put(k, new BigCounter(1));
        } else {
            c.increment();
        }
        size.increment();
        return true;
    }

    /**
     * Adds an element  with the given count. If the element is new then it
     * is associated with a new count equal to the given count. If the
     * element already exists, then its current count is incremented by
     * this count.
     *
     * @param k     the element
     * @param count the count
     * @return true if the set was modified and false otherwise.
     */
    private boolean add_(K k, BigCounter count) {
        if (count.isZero()) {
            return false;
        }
        BigCounter c = map.get(k);
        if (c == null) {
            c = new BigCounter(count);
            map.put(k, c);
        } else {
            c.add(count);
        }
        size.add(c);
        return true;
    }

    /**
     * Adds all the elements with their respective counts from the given
     * set to the current set.
     *
     * @param other the given set
     * @return true if the current set was modified and false otherwise
     */
    public boolean addAll(BigCounterSet<K> other) {
        boolean result = false;
        for (Map.Entry<K, BigCounter> entry : other.map.entrySet()) {
            result = result | add_(entry.getKey(), entry.getValue());
        }
        return result;
    }

    /**
     * Removes an entry from the set.
     *
     * @param o the element to remove
     * @return true if the set was modified and false otherwise.
     */
    public boolean remove(Object o) {
        BigCounter c = map.get(o);
        if (c == null) {
            return false;
        }
        size.subtract(c);
        map.remove(o);
        return true;
    }

    /**
     * Removes one instance of the element from the set. If the instance
     * doesn't exist in the set then nothing is done. If the instance
     * exists its counter is decremented. If the decremented count is
     * 0 then the instance is completely removed from the set.
     *
     * @param o the instance to be removed once
     * @return true if the set was modified and false otherwise.
     */
    public boolean removeOne(Object o) {
        BigCounter c = map.get(o);
        if (c == null) {
            return false;
        }
        if (c.decrement().isZero()) {
            map.remove(o);
        }
        size.decrement();
        return true;
    }

    /**
     * Returns true if this set contains all the elements from the given
     * collection, and false otherwise.
     *
     * @param c the collection
     * @return true if this set contains all the elements from the given
     * collection, and false otherwise.
     */
    public boolean containsAll(Collection<?> c) {
        return map.keySet().containsAll(c);
    }

    /**
     * Adds all the elements from the given collection to this set.
     *
     * @param c the collection
     * @return true if the set was modified and false otherwise.
     */
    public boolean addAll(Collection<? extends K> c) {
        boolean result = false;
        for (K k : c) {
            result = result | add(k);
        }
        return result;
    }

    /**
     * Removes all the elements from the set.
     */
    public void clear() {
        size.reset();
        map.clear();
    }

    /**
     * Returns a sorted map from this set, where the keys are the elements
     * of the set and the values are their respective count. The keys are
     * sorted using the provided comparator.
     *
     * @param comparator the comparator
     * @return a sorted map
     */
    public SortedMap<K, BigCounter> sortedMap(Comparator<K> comparator) {
        SortedMap<K, BigCounter> result = new TreeMap<>(comparator);
        map.entrySet().forEach(
                p -> result.put(p.getKey(), new BigCounter(p.getValue())));
        return result;
    }

    /**
     * Returns a sorted map from this set, where the keys are the elements
     * of the set and the values are their respective count. The keys are
     * sorted using the natural ordering of these elements.
     *
     * @return a sorted map
     */
    public SortedMap<K, BigCounter> sortedMap() {
        SortedMap<K, BigCounter> result = new TreeMap<>();
        map.entrySet().forEach(
                p -> result.put(p.getKey(), new BigCounter(p.getValue())));
        return result;
    }


}
