package org.ticdev.toolboxj.tuples.impl;

import org.ticdev.toolboxj.tuples.Single;
import org.ticdev.toolboxj.tuples.TupleSupport;

/**
 * Concrete single implementation.
 *
 * @param <T1> the type of the first element.
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public class SingleImpl<T1>
        implements Single<T1> {

    /**
     * default serial version
     */
    private static final long serialVersionUID = 1L;

    /**
     * The first element
     */
    private final T1 item1;

    /**
     * the cached hash code
     */
    private int cached_hash_code_;

    /**
     * Class constructor.
     *
     * @param item1 the first element
     */
    public SingleImpl(T1 item1) {
        this.item1 = item1;
    }

    /**
     * Copy constructor.
     *
     * @param source the single from which to copy the value.
     */
    public SingleImpl(Single<T1> source) {
        this(source.item1());
    }

    @Override
    public T1 item1() {
        return item1;
    }

    @Override
    public int hashCode() {
        if (cached_hash_code_ == 0) {
            cached_hash_code_ = TupleSupport.hashCode(item1);
        }
        return cached_hash_code_;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Single<?>) &&
               TupleSupport.singleEquals(this, (Single<?>) obj);
    }
}
