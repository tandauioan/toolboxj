package org.ticdev.toolboxj.tuples;

import java.io.Serializable;

/**
 * Pair tuple interface.
 *
 * @param <T1> the type of the first element
 * @param <T2> the type of the second element
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public interface Pair<T1, T2>
        extends Serializable {

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
