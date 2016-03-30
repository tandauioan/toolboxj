package org.ticdev.toolboxj.collections;

/**
 * Base interface for a primitive iterator.
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 * @see Iterator
 */
public interface PrimitiveIterator {

    /**
     * Returns true if there are more elements available to iterate over and
     * false otherwise.
     * 
     * @return true if there are more elements available to iterate over and
     *         false otherwise.
     */
    boolean hasNext();

    /**
     * Removes the current element.
     * 
     * @throws UnsupportedOperationException
     *             if the operation is not supported.
     * @throws IllegalStateException
     *             if the state of the iterator does not allow this method to be
     *             called.
     */
    void remove()
        throws UnsupportedOperationException,
        IllegalStateException;

}
