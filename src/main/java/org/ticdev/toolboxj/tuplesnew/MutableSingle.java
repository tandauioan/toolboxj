package org.ticdev.toolboxj.tuplesnew;

/**
 * Mutable single tuple interface.
 *
 * @param <T1> the type of the first element
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public interface MutableSingle<T1>
        extends Single<T1> {

    /**
     * Sets the value of the first element
     *
     * @param item1 the value
     * @return this instance
     */
    MutableSingle<T1> item1(T1 item1);

    /**
     * Copy the value from the given single.
     *
     * @param source the single to copy from
     * @return this instance
     */
    MutableSingle<T1> copyFrom(Single<T1> source);
}
