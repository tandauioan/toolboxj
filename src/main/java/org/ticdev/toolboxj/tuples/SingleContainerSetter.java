package org.ticdev.toolboxj.tuples;

import java.io.Serializable;

/**
 * Single container setter.
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 * @param <T1>
 *            the type of the first element
 * @param <SELF>
 *            the type of this as the implementing tuple
 */
public interface SingleContainerSetter<T1, SELF extends SingleContainerSetter<T1, SELF>>
    extends
    Serializable {

    /**
     * Sets the first element and returns the tuple
     * 
     * @param item1
     *            the new value
     * @return this as the implementing tuple
     */
    SELF item1(T1 item1);

}
