package org.ticdev.toolboxj.collections.impl;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import org.ticdev.toolboxj.collections.IntIterator;
import org.ticdev.toolboxj.collections.RingBufferIdx;
import org.ticdev.toolboxj.functions.UnaryConsumer;

/**
 *
 * Fixed size deque based on a circular array, that overwrites older elements
 * when full.
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 * @param <E> the type of the element contained by this collection
 */
public class CircularDequeOverwriteImpl<E> implements Deque<E> {

    /**
     * The ring buffer idx object
     */
    private final RingBufferIdx rbi;

    /**
     * The elements
     */
    private final Object[] elements;

    /**
     * Class constructor.
     *
     * @param capacity the capacity of the deque
     * @param deleteOperator optional delete operator that receives removed
     * objects which are not returned to the caller
     * @throws IllegalArgumentException if the capacity is invalid
     */
    public CircularDequeOverwriteImpl(int capacity,
                                  UnaryConsumer<Object> deleteOperator) throws
            IllegalArgumentException {

        elements = new Object[RingBufferIdx.allocationSizeForCapacity(capacity)];
        if (deleteOperator == null) {
            rbi = new RingBufferIdx(capacity, d -> elements[d] = null,
                    (to, from) -> elements[to] = elements[from]);
        } else {
            rbi = new RingBufferIdx(capacity, d -> {
                deleteOperator.accept(elements[d]);
                elements[d] = null;
            }, (to, from) -> elements[to] = elements[from]);
        }

    }

    /**
     * Class constructor. No side-effect calls are triggered on object removal.
     *
     * @param capacity the capacity of the deque.
     * @throws IllegalArgumentException if the capacity is invalid
     */
    public CircularDequeOverwriteImpl(int capacity) throws IllegalArgumentException {
        this(capacity, null);
    }

    @Override
    public void addFirst(E e) {
        elements[rbi.acquirePrepend()] = e;
    }

    @Override
    public void addLast(E e) {
        elements[rbi.acquireAppend()] = e;
    }

    @Override
    public boolean offerFirst(E e) {
        elements[rbi.acquirePrepend()] = e;
        return true;
    }

    @Override
    public boolean offerLast(E e) {
        elements[rbi.acquireAppend()] = e;
        return true;
    }

    @Override
    public E removeFirst() {
        if (rbi.isEmpty()) {
            throw new NoSuchElementException();
        }
        int cursor = rbi.removeHead();
        @SuppressWarnings("unchecked")
        E result = (E) elements[cursor];
        elements[cursor] = null;
        return result;
    }

    @Override
    public E removeLast() {
        if (rbi.isEmpty()) {
            throw new NoSuchElementException();
        }
        int cursor = rbi.removeTail();
        @SuppressWarnings("unchecked")
        E result = (E) elements[cursor];
        elements[cursor] = null;
        return result;
    }

    @Override
    public E pollFirst() {
        if (rbi.isEmpty()) {
            return null;
        }
        int cursor = rbi.removeHead();
        @SuppressWarnings("unchecked")
        E result = (E) elements[cursor];
        elements[cursor] = null;
        return result;
    }

    @Override
    public E pollLast() {
        if (rbi.isEmpty()) {
            return null;
        }
        int cursor = rbi.removeTail();
        @SuppressWarnings("unchecked")
        E result = (E) elements[cursor];
        elements[cursor] = null;
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E getFirst() {
        if (rbi.isEmpty()) {
            throw new NoSuchElementException();
        }
        return (E) elements[rbi.head()];
    }

    @Override
    @SuppressWarnings("unchecked")
    public E getLast() {
        if (rbi.isEmpty()) {
            throw new NoSuchElementException();
        }
        return (E) elements[rbi.tail()];
    }

    @Override
    @SuppressWarnings("unchecked")
    public E peekFirst() {
        if (rbi.isEmpty()) {
            return null;
        }
        return (E) elements[rbi.head()];
    }

    @Override
    @SuppressWarnings("unchecked")
    public E peekLast() {
        if (rbi.isEmpty()) {
            return null;
        }
        return (E) elements[rbi.tail()];
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        return rbi.removeFirst(e -> Objects.equals(e, o));
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        return rbi.removeLast(e -> Objects.equals(e, o));
    }

    @Override
    public boolean add(E e) {
        elements[rbi.acquireAppend()] = e;
        return true;
    }

    @Override
    public boolean offer(E e) {
        elements[rbi.acquireAppend()] = e;
        return true;
    }

    @Override
    public E remove() {
        return removeFirst();
    }

    @Override
    public E poll() {
        return pollFirst();
    }

    @Override
    public E element() {
        return getFirst();
    }

    @Override
    public E peek() {
        return peekFirst();
    }

    @Override
    public void push(E e) {
        addFirst(e);
    }

    @Override
    public E pop() {
        return removeFirst();
    }

    @Override
    public boolean remove(Object o) {
        return rbi.removeAll(e -> Objects.equals(e, o));
    }

    @Override
    public boolean contains(Object o) {
        IntIterator rbIterator = rbi.forwardIterator();
        while (rbIterator.hasNext()) {
            if (Objects.equals(elements[rbIterator.next()], o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return rbi.size();
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {

            final IntIterator rbIterator = rbi.forwardIterator();

            @Override
            public boolean hasNext() {
                return rbIterator.hasNext();
            }

            @Override
            @SuppressWarnings("unchecked")
            public E next() {
                return (E) elements[rbIterator.next()];
            }

            @Override
            public void remove() {
                rbIterator.remove();
            }

        };
    }

    @Override
    public Iterator<E> descendingIterator() {
        return new Iterator<E>() {

            final IntIterator rbIterator = rbi.backwardIterator();

            @Override
            public boolean hasNext() {
                return rbIterator.hasNext();
            }

            @Override
            @SuppressWarnings("unchecked")
            public E next() {
                return (E) elements[rbIterator.next()];
            }

            @Override
            public void remove() {
                rbIterator.remove();
            }
        };
    }

    @Override
    public boolean isEmpty() {
        return rbi.isEmpty();
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[rbi.size()];
        int pos = 0;
        for (E e : this) {
            result[pos++] = e;
        }
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        if (a.length < rbi.size()) {
            a = (T[]) Array.newInstance(a.getClass().getComponentType(), rbi.
                    size());
        }
        int pos = 0;
        for (E e : this) {
            a[pos++] = (T) e;
        }
        return a;
    }

    @Override
    public boolean containsAll(
            Collection<?> c) {
        return c.stream().allMatch(this::contains);
    }

    @Override
    public boolean addAll(
            Collection<? extends E> c) {
        if (c.isEmpty()) {
            return false;
        }
        c.forEach(this::add);
        return true;
    }

    @Override
    public boolean removeAll(
            Collection<?> c) {
        return rbi.removeAll(i->c.contains(elements[i]));
    }

    @Override
    public boolean retainAll(
            Collection<?> c) {
        return rbi.removeAll(i -> !c.contains(elements[i]));
    }

    @Override
    public void clear() {
        rbi.removeAll();
    }

}
