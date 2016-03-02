package org.ticdev.toolboxj.tuples;

/**
 * Mutable single interface.
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 * @param <T1>
 *            argument type
 */
public interface MutableSingle<T1>
    extends
    Single<T1> {

    /**
     * Sets the new value of the tuple.
     * 
     * @param item1
     *            the new value
     * @return this instance
     */
    MutableSingle<T1> item1(T1 item1);

}
