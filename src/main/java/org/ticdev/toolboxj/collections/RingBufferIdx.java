package org.ticdev.toolboxj.collections;

import org.ticdev.toolboxj.functions.BinaryConsumerInt;
import org.ticdev.toolboxj.functions.UnaryConsumerInt;

import java.util.NoSuchElementException;
import java.util.function.IntPredicate;

/**
 * Concrete implementation of a index operations for a ring buffer.
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public class RingBufferIdx {

  /**
   * The maximum number of elements in the buffer.
   */
  private final int capacity;

  /**
   * The last addressable index in the buffer.
   */
  private final int lastIndex;

  /**
   * The size of a collection/array of elements mapped on this ring
   * buffer.
   */
  private final int allocationSize;

  /**
   * The head of the ring buffer.
   */
  private int head;

  /**
   * The tail of the ring buffer.
   */
  private int tail;

  /**
   * The size of the ring buffer.
   */
  private int size;

  /**
   * The maximum capacity of a ring buffer.
   */
  public static final int MAX_CAPACITY = Integer.MAX_VALUE - 2;

  /**
   * The minimum capacity of a ring buffer.
   */
  public static final int MIN_CAPACITY = 1;

  /**
   * Callback operator that receives indexes from the ring buffer
   * that should have their content deleted.
   */
  private final UnaryConsumerInt deleteOperator;

  /**
   * Callback operator that receives content copy commands to an index
   * from another index.
   */
  private final BinaryConsumerInt copyToFrom;

  /**
   * Asserts that the capacity is within the predefined limits.
   *
   * @param capacity the capacity
   * @throws IllegalArgumentException if the capacity is outside the
   *                                  correct limits
   */
  private static void assertValidCapacity(final int capacity)
      throws IllegalArgumentException {
    if (capacity < MIN_CAPACITY || capacity > MAX_CAPACITY) {
      throw new IllegalArgumentException(String.format(
          "Valid capacity between %d and %d. Actual value: %d.",
          MIN_CAPACITY, MAX_CAPACITY, capacity));
    }
  }

  /**
   * Class constructor.
   *
   * @param capacityValue     the capacity of the ring buffer, between
   *                          {@link #MIN_CAPACITY}
   *                          and {@link #MAX_CAPACITY}
   * @param deleteOperatorRef the non-null callback operator that
   *                          receives indexes from the ring buffer
   *                          that should have their content deleted.
   * @param copyToFromRef     the non-null callback operator that
   *                          receives content
   *                          copy commands to an index from another
   *                          index
   * @throws IllegalArgumentException if the arguments are invalid
   * @throws NullPointerException     if any argument is null.
   */
  public RingBufferIdx(
      final int capacityValue,
      final UnaryConsumerInt deleteOperatorRef,
      final BinaryConsumerInt copyToFromRef
  ) throws IllegalArgumentException, NullPointerException {
    this.allocationSize = allocationSizeForCapacity(capacityValue);
    if (deleteOperatorRef == null) {
      throw new NullPointerException(
          "Delete operator cannot be null.");
    }
    if (copyToFromRef == null) {
      throw new NullPointerException(
          "Move operator cannot be null.");
    }
    this.deleteOperator = deleteOperatorRef;
    this.copyToFrom = copyToFromRef;
    this.capacity = capacityValue;
    this.lastIndex = capacityValue;
    resetHeaderTailSize();
  }

  /**
   * Resets head, tail and size to empty list.
   */
  private void resetHeaderTailSize() {
    this.head = 0;
    this.tail = lastIndex;
    this.size = 0;
  }

  /**
   * Returns the allocation size of an array that can map on
   * this ring buffer.
   *
   * @return the allocation size of an array that can map on
   *     this ring buffer.
   */
  public int allocationSize() {
    return allocationSize;
  }

  /**
   * Returns the allocation size required for an array based on the
   * desired ring buffer capacity.
   *
   * @param capacity the capacity
   * @return the allocation size;
   * @throws IllegalArgumentException if the capacity value is invalid
   */
  public static int allocationSizeForCapacity(final int capacity) throws
      IllegalArgumentException {
    assertValidCapacity(capacity);
    return capacity + 1;
  }

  /**
   * Given a cursor, this method returns the next index
   * following the cursor.
   *
   * <p>
   * If the cursor is on the last index, then the cursor will wrap
   * around to position 0. Otherwise it is just incremented.
   * </p>
   *
   * @param cursor the cursor
   * @return the next index following the cursor
   */
  private int next(final int cursor) {
    return cursor == lastIndex ? 0 : cursor + 1;
  }

  /**
   * Given a cursor, this method returns the previous index preceding the
   * cursor
   *
   * <p>
   * If the cursor is on 0, then the cursor will wrap around to the last
   * index. Otherwise it is just decremented.
   * </p>
   *
   * @param cursor the cursor
   * @return the previous index preceding the cursor
   */
  private int prev(final int cursor) {
    return cursor == 0 ? lastIndex : cursor - 1;
  }

  /**
   * Returns true if the ring buffer would be empty if the cursor was the
   * head, and false otherwise.
   *
   * @param cursor the cursor
   * @return true if the ring buffer would be empty if the cursor was the
   *     head, and false otherwise.
   */
  private boolean emptyAsHead(final int cursor) {
    return next(tail) == cursor;
  }

  /**
   * Returns true if the ring buffer would be empty if the cursor was the
   * tail, and false otherwise.
   *
   * @param cursor the cursor
   * @return true if the ring buffer would be empty if the cursor was the
   *     tail, and false otherwise
   */
  private boolean emptyAsTail(final int cursor) {
    return next(cursor) == head;
  }

  /**
   * Returns true if the ring buffer would be full if the
   * cursor was the head, and false otherwise.
   *
   * @param cursor the cursor
   * @return true if the ring buffer would be full if the cursor was
   *     the head, and false otherwise
   */
  protected boolean fullAsHead(final int cursor) {
    return next(next(tail)) == cursor;
  }

  /**
   * Returns true if the ring buffer would be full if the cursor
   * was the tail, and false otherwise.
   *
   * @param cursor the cursor
   * @return true if the ring buffer would be full if the cursor
   *     was the tail, and false otherwise
   */
  protected boolean fullAsTail(final int cursor) {
    return next(next(cursor)) == head;
  }

  /**
   * Returns true if the ring buffer is full, and false otherwise.
   *
   * @return true if the ring buffer is full, and false otherwise
   */
  public boolean isFull() {
    return size == capacity;
  }

  /**
   * Returns true if the ring buffer is empty, and false otherwise.
   *
   * @return true if the ring buffer is empty, and false otherwise
   */
  public boolean isEmpty() {
    return size == 0;
  }

  /**
   * The size of the ring buffer.
   *
   * @return the size of the ring buffer.
   */
  public int size() {
    return size;
  }

  /**
   * Returns the capacity of the ring buffer.
   *
   * @return the capacity of the ring buffer.
   */
  public int capacity() {
    return capacity;
  }

  /**
   * Returns the size of the ring buffer computed based on the position of
   * head and tail rather than relying on the size() value.
   *
   * <p>
   * This is a validation method, mostly for debug purposes.
   * </p>
   *
   * @return the size of the ring buffer computed based on the position of
   *     head and tail rather than relying on the size() value.
   */
  protected int computeSize() {
    int result = tail - head + 1;
    if (result == allocationSize) {
      return 0;
    }
    return result >= 0
        ? result
        : allocationSize + result;
  }

  /**
   * Returns the size of the ring buffer if the given cursor was the head.
   *
   * @param cursor the cursor
   * @return the size of the ring buffer if the given cursor was the head.
   */
  protected int computeSizeAsHead(final int cursor) {
    int result = tail - cursor + 1;
    if (result == allocationSize) {
      return 0;
    }
    return result >= 0 ? result : allocationSize - result;
  }

  /**
   * Returns the size of the ring buffer if the given cursor was the tail.
   *
   * @param cursor the cursor
   * @return the size of the ring buffer if the given cursor was the tail.
   */
  protected int computeSizeAsTail(final int cursor) {
    int result = cursor - head + 1;
    if (result == allocationSize) {
      return 0;
    }
    return result >= 0
        ? result
        : allocationSize - result;
  }

  /**
   * Asserts that the given external index (0-based) is a valid internal
   * index.
   *
   * @param index the external index.
   * @throws IndexOutOfBoundsException if the index is invalid.
   */
  private void assertValidExternalIndex(final int index)
      throws IndexOutOfBoundsException {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException();
    }
  }

  /**
   * Maps an external index (0-based) to the corresponding index in the ring
   * buffer.
   *
   * @param index the external index
   * @return the corresponding internal index
   * @throws IndexOutOfBoundsException if the index is invalid.
   */
  public int mapIndex(final int index)
      throws IndexOutOfBoundsException {
    assertValidExternalIndex(index);
    int diff = allocationSize - head;
    if (index < diff) {
      return index + head;
    } else {
      return index - diff;
    }
  }

  /**
   * Allocates and returns the index for an append operation.
   *
   * <p>
   * This method will remove the head of the ring buffer if the ring buffer is
   * full, to make space for the next element.
   * </p>
   *
   * @return the index for an append operation.
   */
  public int acquireAppend() {
    if (isFull()) {
      deleteOperator.accept(head);
      head = next(head);
      size--;
    }
    tail = next(tail);
    size++;
    return tail;
  }

  /**
   * Allocates and returns the index for a prepend operation.
   *
   * <p>
   * This method will remove the tail of the ring buffer if the ring buffer is
   * full, to make space for the next element.
   * </p>
   *
   * @return the index for an append operation.
   */
  public int acquirePrepend() {
    if (isFull()) {
      deleteOperator.accept(tail);
      tail = prev(tail);
      size--;
    }
    head = prev(head);
    size++;
    return head;
  }

  /**
   * Allocates and returns the cursor for an insert operation.
   *
   * <p>
   * The head of the ring buffer will be removed if the ring buffer is full,
   * to make space for the new index.</p>
   *
   * <p>
   * The method will call the delete operator to delete the content at the
   * cursor and for the removed head, if the case, to delete the content
   * before returning.</p>
   *
   * @param cursor the cursor for insertion
   * @return the cursor
   */
  private int acquireCursorRemoveHeadOnFull(final int cursor) {
    int diffHead = computeSizeAsTail(cursor);
    int diffTail = computeSizeAsHead(cursor);

    if (isFull()) {
      deleteOperator.accept(head);
      if (head == cursor) {
        return cursor;
      }
      head = next(head);
      size--;
      diffHead--;
    }
    size++;
    int addCursor;
    if (diffHead < diffTail) {
      /* head is closer */
      head = prev(head);
      addCursor = head;
      int c = next(addCursor);
      while (c != cursor) {
        copyToFrom.accept(addCursor, c);
        addCursor = next(addCursor);
        c = next(c);
      }
      if (addCursor != head) {
        deleteOperator.accept(addCursor);
      }
    } else {
      tail = next(tail);
      addCursor = tail;
      int c = prev(tail);
      while (c != prev(cursor)) {
        copyToFrom.accept(addCursor, c);
        addCursor = prev(addCursor);
        c = prev(c);
      }
      if (addCursor != tail) {
        deleteOperator.accept(addCursor);
      }
    }
    return addCursor;
  }

  /**
   * Allocates and returns the index for an insert operation.
   *
   * <p>
   * This method will remove the head of the ring buffer to make space for the
   * new position if the ring buffer is full.</p>
   *
   * <p>
   * Using an index that is equal to the current size of the ring buffer will
   * result in an append operation.</p>
   *
   * <p>
   * The index used with this method is the external, zero-based index, not an
   * internal cursor.
   * </p>
   *
   * @param index the external index
   * @return the allocated index
   */
  public int acquireIndex(final int index) {
    if (index == size) {
      return acquireAppend();
    }
    return acquireCursorRemoveHeadOnFull(mapIndex(index));
  }

  /**
   * Removes the head of the ring buffer and returns its index.
   *
   * <p>
   * This method returns the index to be deleted, so it does not call the
   * delete operator.
   * </p>
   *
   * @return the index of the former head
   * @throws IllegalStateException if the ring buffer is empty.
   */
  public int removeHead()
      throws IllegalStateException {
    if (size == 0) {
      throw new IllegalStateException();
    }
    int result = head;
    head = next(head);
    size--;
    return result;
  }

  /**
   * Removes the tail of the ring buffer and returns its index.
   *
   * <p>
   * This method returns the index to be deleted, do it does not call the
   * delete operator.
   * </p>
   *
   * @return the index of the former tail
   * @throws IllegalStateException if the ring buffer is empty.
   */
  public int removeTail()
      throws IllegalStateException {
    if (size == 0) {
      throw new IllegalStateException();
    }
    int result = tail;
    tail = prev(tail);
    size--;
    return result;
  }

  /**
   * If the cursor position is deleted, this method will compact the array
   * toward the head, calling the deleteOperator and copyToFrom operator to
   * compact the data. The method will not decrement the size. That is
   * expected to be done by the calling method.
   *
   * @param cursorValue the cursor that was vacated.
   */
  private void compactTowardHead(final int cursorValue) {
    int cursor = cursorValue;
    if (cursor != head) {
      int compactCursor = cursor;
      cursor = prev(cursor);
      while (!emptyAsTail(cursor)) {
        copyToFrom.accept(compactCursor, cursor);
        compactCursor = prev(compactCursor);
        cursor = prev(cursor);
      }
      deleteOperator.acceptInt(head);
    }
    head = next(head);
  }

  /**
   * If the cursor position is deleted, this method will compact the array
   * toward the tail, calling the deleteOperator and copyToFrom operator to
   * compact the data. The method will not decrement the size. That is
   * expected to be done by the calling method.
   *
   * @param cursorValue the cursor that was vacated.
   */
  private void compactTowardTail(final int cursorValue) {
    int cursor = cursorValue;
    if (cursor != tail) {
      int compactCursor = cursor;
      cursor = next(cursor);
      while (!emptyAsHead(cursor)) {
        copyToFrom.acceptInt(compactCursor, cursor);
        compactCursor = next(compactCursor);
        cursor = next(cursor);
      }
      deleteOperator.acceptInt(tail);
    }
    tail = prev(tail);
  }

  /**
   * Removes the element at the given cursor and compacts the elements toward
   * the head.
   *
   * @param cursor the cursor
   */
  private void removeCompactTowardHead(final int cursor) {
    deleteOperator.acceptInt(cursor);
    compactTowardHead(cursor);
    size--;
  }

  /**
   * Removes the element at the given cursor and compacts toward the tail.
   *
   * @param cursor the cursor.
   */
  private void removeCompactTowardTail(final int cursor) {
    deleteOperator.acceptInt(cursor);
    compactTowardTail(cursor);
    size--;
  }

  /**
   * Returns the first index, starting from the head, that matches the given
   * predicate.
   *
   * @param predicate the predicate
   * @return the index matching the predicate
   */
  private int findFromHead(final IntPredicate predicate) {
    int cursor = head;
    while (!emptyAsHead(cursor)) {
      if (predicate.test(cursor)) {
        return cursor;
      }
      cursor = next(cursor);
    }
    return -1;
  }

  /**
   * Returns the last index, starting from the tail, that matches the given
   * predicate.
   *
   * @param predicate the predicate
   * @return the index matching the predicate
   */
  private int findFromTail(final IntPredicate predicate) {
    int cursor = tail;
    while (!emptyAsTail(cursor)) {
      if (predicate.test(cursor)) {
        return cursor;
      }
      cursor = prev(cursor);
    }
    return -1;
  }

  /**
   * Removes the first index, starting from the head, that matches the given
   * predicate.
   *
   * <p>
   * It will compact the ring buffer either toward the head or the tail,
   * depending on which is closer.
   * </p>
   *
   * <p>
   * The delete operator will be called on a match. The copy operator will be
   * called when compacting.
   * </p>
   *
   * @param predicate the predicate
   * @return true if an element was removed, and false otherwise.
   */
  public boolean removeFirst(final IntPredicate predicate) {
    int cursor = findFromHead(predicate);
    if (cursor == -1) {
      return false;
    }
    if (computeSizeAsHead(cursor) >= computeSizeAsTail(
        cursor)) {
      /* tail closer */
      removeCompactTowardTail(cursor);
    } else {
      /* head closer */
      removeCompactTowardHead(cursor);
    }
    return true;
  }

  /**
   * Removes the last index, starting from the tail, that matches the given
   * predicate.
   *
   * <p>
   * It will compact the ring buffer either toward the head or the tail,
   * depending on which is closer.
   * </p>
   *
   * <p>
   * The delete operator will be called on a match. The copy operator will be
   * called when compacting.
   * </p>
   *
   * @param predicate the predicate
   * @return true if an element was removed, and false otherwise.
   */
  public boolean removeLast(final IntPredicate predicate) {
    int cursor = findFromTail(predicate);
    if (cursor == -1) {
      return false;
    }
    if (computeSizeAsHead(cursor) >= computeSizeAsTail(
        cursor)) {
      /* tail closer */
      removeCompactTowardTail(cursor);
    } else {
      /* head closer */
      removeCompactTowardHead(cursor);
    }
    return true;
  }

  /**
   * Removes all the elements matching the given predicate.
   *
   * <p>
   * The iteration starts from the head and it will compact toward the tail.
   * </p>
   *
   * @param predicate the predicate
   * @return true if anything was removed and false otherwise.
   */
  public boolean removeAll(final IntPredicate predicate) {
    int cursor = head;
    int compactCursor = head;

    while (!emptyAsHead(cursor)) {
      if (predicate.test(cursor)) {
        size--;
      } else {
        if (cursor != compactCursor) {
          deleteOperator.acceptInt(compactCursor);
          copyToFrom.acceptInt(compactCursor, cursor);
        }
        compactCursor = next(compactCursor);
      }
      cursor = next(cursor);
    }

    if (compactCursor == cursor) {
      return false;
    }

    tail = prev(compactCursor);

    while (compactCursor != cursor) {
      deleteOperator.acceptInt(compactCursor);
      compactCursor = next(compactCursor);
    }

//        cursor = compactCursor;
//        while (!empty_as_head_(cursor)) {
//            deleteOperator.acceptInt(cursor);
//            cursor = next_(cursor);
//        }
//        tail = prev_(compactCursor);
    return true;

  }

  /**
   * Considers the cursor position as vacated and compacts toward either the
   * head or the tail depending on which one is closer. There is no delete
   * operation for the cursor position since the user performs the action.
   *
   * @param cursor the cursor that has been vacated.
   */
  private void removeInternal(final int cursor) {
    if (computeSizeAsHead(cursor) >= computeSizeAsTail(
        cursor)) {
      /* tail closer */
      compactTowardTail(cursor);
    } else {
      /* head closer */
      compactTowardHead(cursor);
    }
    size--;
  }

  /**
   * Removes the element at the given external index, and compacts either
   * toward the head or the tail depending which one is closer. There is no
   * delete operation for the cursor position since the user performs the
   * action.
   *
   * @param index the external index
   * @throws IndexOutOfBoundsException if the index is out of bounds
   */
  public void remove(final int index) throws IndexOutOfBoundsException {
    removeInternal(mapIndex(index));
  }

  /**
   * Walks through all the valid indexes and reports them as deleted, then it
   * resets the ring buffer to empty.
   */
  public void removeAll() {
    if (!isEmpty()) {
      int cursor = head;
      while (!emptyAsHead(cursor)) {
        deleteOperator.acceptInt(cursor);
        cursor = next(cursor);
      }
    }
    resetHeaderTailSize();
  }

  /**
   * Resets the ring buffer to empty without reporting any deleted elements
   * (useful when the elements are moved and don't need to be reported as
   * deleted.
   */
  public void removeAllNoReporting() {
    resetHeaderTailSize();
  }

  /**
   * Returns a forward iterator, that starts iterating from head toward the
   * tail.
   *
   * <p>
   * The ring buffer should not be altered outside the iterator, during the
   * iteration. The iterator supports the {@link IntIterator#remove()} method.
   * </p>
   *
   * @return a forward iterator
   */
  public IntIterator forwardIterator() {

    return new IntIterator() {

      /**
       * Cursor, initialized before the head
       */
      private int cursor = prev(head);

      /**
       * specifies whether or not the cursor is valid (on a valid
       * position)
       */
      private boolean validCursor = false;

      /**
       * specifies whether or not there is a next element
       */
      private boolean hasNext = !isEmpty();

      @Override
      public void remove()
          throws UnsupportedOperationException,
          IllegalStateException {
        if (!validCursor) {
          throw new IllegalStateException();
        }
        removeCompactTowardTail(cursor);
        validCursor = false;
        hasNext = !emptyAsHead(cursor);
        cursor = prev(cursor);
      }

      @Override
      public boolean hasNext() {
        return hasNext;
      }

      @Override
      public int next()
          throws NoSuchElementException {
        if (!hasNext) {
          throw new NoSuchElementException();
        }
        cursor = RingBufferIdx.this.next(cursor);
        validCursor = true;
        hasNext = !emptyAsHead(RingBufferIdx.this.next(cursor));
        return cursor;
      }
    };

  }

  /**
   * Returns a backward iterator, that starts iterating from tail toward the
   * head.
   *
   * <p>
   * The ring buffer should not be altered outside the iterator, during the
   * iteration. The iterator supports the {@link IntIterator#remove()} method.
   * </p>
   *
   * @return a backward iterator
   */
  public IntIterator backwardIterator() {

    return new IntIterator() {

      /**
       * Cursor, initialized after the tail
       */
      private int cursor = RingBufferIdx.this.next(tail);

      /**
       * specifies whether or not the cursor is valid (on a valid
       * position)
       */
      private boolean validCursor = false;

      /**
       * specifies whether or not there is a next element
       */
      private boolean hasNext = !isEmpty();

      @Override
      public boolean hasNext() {
        return hasNext;
      }

      @Override
      public void remove()
          throws UnsupportedOperationException,
          IllegalStateException {
        if (!validCursor) {
          throw new IllegalStateException();
        }
        removeCompactTowardHead(cursor);
        validCursor = false;
        hasNext = !emptyAsTail(cursor);
        cursor = RingBufferIdx.this.next(cursor);
      }

      @Override
      public int next()
          throws NoSuchElementException {
        if (!hasNext) {
          throw new NoSuchElementException();
        }
        cursor = prev(cursor);
        validCursor = true;
        hasNext = !emptyAsTail(prev(cursor));
        return cursor;
      }

    };
  }

  /**
   * Returns the head of the ring buffer.
   *
   * @return the head of the ring buffer
   */
  public int head() {
    return head;
  }

  /**
   * Returns the tail of the ring buffer.
   *
   * @return the tail of the ring buffer
   */
  public int tail() {
    return tail;
  }

}
