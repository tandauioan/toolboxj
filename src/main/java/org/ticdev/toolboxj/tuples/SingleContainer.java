package org.ticdev.toolboxj.tuples;

import java.io.Serializable;

/**
 * Single container.
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 * @param <T1>
 *            the type of the first element
 */
public interface SingleContainer<T1>
    extends
    Serializable {

    /**
     * Returns the first element
     * 
     * @return the first element
     */
    T1 item1();

}
