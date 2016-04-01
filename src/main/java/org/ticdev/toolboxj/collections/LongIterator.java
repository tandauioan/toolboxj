package org.ticdev.toolboxj.collections;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.ticdev.toolboxj.functions.UnaryConsumerLong;

/**
 * Iterator over a collection of long values.
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 * @see Iterator
 */
public interface LongIterator
    extends
    PrimitiveTypeIterator {

    /**
     * Returns the next element.
     * 
     * @return the next element
     * @throws NoSuchElementException
     *             if there are no more elements.
     */
    long next()
        throws NoSuchElementException;

    /**
     * Iterates over the remaining elements in the iterator.
     * 
     * @param operator
     *            the function to be called with each of the remaining elements
     *            as argument.
     * @throws NullPointerException
     *             if the operator is null
     */
    default void forEachRemaining(UnaryConsumerLong operator)
        throws NullPointerException {
        if (operator == null) {
            throw new NullPointerException();
        }
        while (hasNext()) {
            operator.acceptLong(next());
        }
    }

}
