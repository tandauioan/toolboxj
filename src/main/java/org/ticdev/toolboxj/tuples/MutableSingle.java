package org.ticdev.toolboxj.tuples;

/**
 * Mutable single interface.
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 * @param <T1>
 *            the type of the first element
 */
public interface MutableSingle<T1>
    extends
    Single<T1>, MutableTuple<MutableSingle<T1>> {

    /**
     * Sets the new value for the first item
     * 
     * @param item1
     *            the new value
     * @return this instance
     */
    MutableSingle<T1> item1(T1 item1);

}
