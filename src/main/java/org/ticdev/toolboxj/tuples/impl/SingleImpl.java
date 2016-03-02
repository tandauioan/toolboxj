package org.ticdev.toolboxj.tuples.impl;

import org.ticdev.toolboxj.tuples.Single;
import org.ticdev.toolboxj.tuples.Tuples;

/**
 * Concrete implementation of a {@link Single}.
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 * @param <T1>
 *            argument type
 */
public class SingleImpl<T1>
    implements
    Single<T1> {

    /**
     * item 1
     */
    private final T1 item1;

    /**
     * cached hash code
     */
    private int cached_hash_code_;

    /**
     * Class constructor.
     * 
     * @param item1
     *            the initialization value
     */
    public SingleImpl(
        T1 item1) {
        this.item1 = item1;
    }

    @Override
    public T1 item1() {
        return item1;
    }

    @Override
    public int hashCode() {
        if (cached_hash_code_ == 0) {
            return cached_hash_code_ = Tuples.hashCode(item1);
        }
        return cached_hash_code_;
    }

    @Override
    public boolean equals(Object obj) {
        return Tuples.singleEquals(this, obj);
    }

}
