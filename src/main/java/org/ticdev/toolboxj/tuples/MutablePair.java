package org.ticdev.toolboxj.tuples;

/**
 * Mutable pair interface.
 *
 * @param <T1> the type of the first element
 * @param <T2> the type of the second element
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public interface MutablePair<T1, T2>
        extends Pair<T1, T2> {

    /**
     * Sets the value of the first element
     *
     * @param item1 the value
     * @return this instance
     */
    MutablePair<T1, T2> item1(T1 item1);

    /**
     * Sets the value of the second element
     *
     * @param item2 the value
     * @return this instance
     */
    MutablePair<T1, T2> item2(T2 item2);

    /**
     * Copy the value from the given pair.
     *
     * @param source the pair to copy from
     * @return this instance
     */
    MutablePair<T1, T2> copyFrom(Pair<T1, T2> source);

}
