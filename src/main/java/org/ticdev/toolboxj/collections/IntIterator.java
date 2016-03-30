package org.ticdev.toolboxj.collections;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.ticdev.toolboxj.functions.UnaryConsumerInt;

/**
 * Iterator over a collection of int values.
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 * @see Iterator
 */
public interface IntIterator extends PrimitiveIterator {

    /**
     * Returns the next element.
     * 
     * @return the next element.
     * 
     * @throws NoSuchElementException
     *             if there are no more elements.
     */
    int next()
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
    default void forEachRemaining(UnaryConsumerInt operator)
        throws NullPointerException {
        if (operator == null) {
            throw new NullPointerException();
        }
        while (hasNext()) {
            operator.acceptInt(next());
        }
    }

}
