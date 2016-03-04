package org.ticdev.toolboxj.tuples;

/**
 * Mutable triplet tuple interface
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 * @param <T1>
 *            the type of the first element
 * @param <T2>
 *            the type of the second element
 * @param <T3>
 *            the type of the third element
 */
public interface MutableTriplet<T1, T2, T3>
    extends
    TripletContainer<T1, T2, T3>,
    TripletContainerSetter<T1, T2, T3, MutableTriplet<T1, T2, T3>>,
    Tuple<MutableTriplet<T1, T2, T3>>,
    MutableTuple<MutableTriplet<T1, T2, T3>> {

    @Override
    default MutableTriplet<T1, T2, T3> self() {
        return this;
    }

    /**
     * Sets the new value of the first element
     * 
     * @param item1
     *            the new value
     * @return this instance
     */
    MutableTriplet<T1, T2, T3> item1(T1 item1);

    /**
     * Sets the new value of the second element
     * 
     * @param item2
     *            the new value
     * @return this instance
     */
    MutableTriplet<T1, T2, T3> item2(T2 item2);

    /**
     * Sets the new value of the third element
     * 
     * @param item3
     *            the new value
     * @return this instance
     */
    MutableTriplet<T1, T2, T3> item3(T3 item3);

}
