package org.ticdev.toolboxj.tuples;

import java.io.Serializable;

/**
 * Quad tuple interface.
 *
 * @param <T1> the type of the first element
 * @param <T2> the type of the second element
 * @param <T3> the type of the third element
 * @param <T4> the type of the fourth element
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public interface Quad<T1, T2, T3, T4>
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

    /**
     * Returns the third element
     *
     * @return the third element
     */
    T3 item3();

    /**
     * Returns the fourth element
     *
     * @return the fourth element
     */
    T4 item4();

}
