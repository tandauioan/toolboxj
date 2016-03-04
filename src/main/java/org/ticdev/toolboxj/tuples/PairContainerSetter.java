package org.ticdev.toolboxj.tuples;

/**
 * Pair container setter.
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 * @param <T1>
 *            the type of the first element
 * @param <T2>
 *            the type of the second element
 * @param <SELF>
 *            the type of this as the implementing tuple
 */
public interface PairContainerSetter<T1, T2, SELF extends PairContainerSetter<T1, T2, SELF>> {

    /**
     * Sets the first element and returns the tuple.
     * 
     * @param item1
     *            the new value
     * @return this as the implementing tuple
     */
    SELF item1(T1 item1);

    /**
     * Sets the second element and returns the tuple.
     * 
     * @param item2
     *            the new value
     * @return this of this as the implementing tuple
     */
    SELF item2(T2 item2);

}
