package org.ticdev.toolboxj.tuples;

/**
 * Triplet container setter.
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 * @param <T1>
 *            the type of the first element
 * @param <T2>
 *            the type of the second element
 * @param <T3>
 *            the type of the third element
 * @param <SELF>
 *            the type of this as the implementing tuple
 */
public interface TripletContainerSetter<T1, T2, T3, SELF extends TripletContainerSetter<T1, T2, T3, SELF>> {

    /**
     * Sets the first element and returns the tuple
     * 
     * @param item1
     *            the new value
     * @return this as the implementing tuple
     */
    SELF item1(T1 item1);

    /**
     * Sets the second element and returns the tuple
     * 
     * @param item2
     *            the new value
     * @return this as the implementing tuple
     */
    SELF item2(T2 item2);

    /**
     * Sets the third element and returns the tuple
     * 
     * @param item3
     *            the new value
     * @return this as the implementing tuple
     */
    SELF item3(T3 item3);

}
