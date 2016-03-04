package org.ticdev.toolboxj.tuples;

/**
 * Mutable pair interface.
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 * @param <T1>
 *            the type of the first element
 * @param <T2>
 *            the type of the second element
 */
public interface MutablePair<T1, T2>
    extends
    PairContainer<T1, T2>,
    PairContainerSetter<T1, T2, MutablePair<T1, T2>>,
    Tuple<MutablePair<T1, T2>>,
    MutableTuple<MutablePair<T1, T2>> {

    @Override
    default org.ticdev.toolboxj.tuples.MutablePair<T1, T2> self() {
        return this;
    }

    /**
     * Sets the new value of the first item
     * 
     * @param item1
     *            the new value
     * @return this instance
     */
    MutablePair<T1, T2> item1(T1 item1);

    /**
     * Sets the new value of the second item
     * 
     * @param item2
     *            the new value
     * @return this instance
     */
    MutablePair<T1, T2> item2(T2 item2);

}
