package org.ticdev.toolboxj.tuples;

import java.io.Serializable;

/**
 * Pair tuple interface.
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 * @param <T1>
 *            the type of the first element
 * @param <T2>
 *            the type of the second element
 */
public interface Pair<T1, T2>
    extends
    Tuple<Pair<T1, T2>>,
    Serializable {

    /**
     * Returns the first item
     * 
     * @return the first item
     */
    T1 item1();

    /**
     * Returns the second item
     * 
     * @return the second item
     */
    T2 item2();

    @Override
    default Pair<T1, T2> self() {
        return this;
    }

}
