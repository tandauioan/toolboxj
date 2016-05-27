package org.ticdev.toolboxj.tuplesnew;

import java.io.Serializable;

/**
 * Single tuple interface.
 * @param <T1> the type of the element
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public interface Single<T1>
        extends Serializable {

    /**
     * Returns the single element
     *
     * @return the single element
     */
    T1 item1();

}
