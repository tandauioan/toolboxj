package org.ticdev.toolboxj.tuples;

import java.io.Serializable;

/**
 * Triplet tuple interface.
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
public interface Triplet<T1, T2, T3>
    extends
    Tuple<Triplet<T1, T2, T3>>,
    Serializable {

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

    /**
     * Returns the third element
     * 
     * @return the third element
     */
    T3 item3();

    @Override
    default Triplet<T1, T2, T3> self() {
        return this;
    }

}
