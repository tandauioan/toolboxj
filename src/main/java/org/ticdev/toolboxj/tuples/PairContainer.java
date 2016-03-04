package org.ticdev.toolboxj.tuples;

/**
 * Pair container.
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 * @param <T1>
 *            the type of the first element
 * @param <T2>
 *            the type of the second element
 */
public interface PairContainer<T1, T2> {

    /**
     * Returns the first element
     * 
     * @return the first element
     */
    T1 item1();

    /**
     * Returns the second element
     * 
     * @return the second element
     */
    T2 item2();

}
