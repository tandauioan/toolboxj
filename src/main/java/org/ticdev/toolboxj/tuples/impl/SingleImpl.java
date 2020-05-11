package org.ticdev.toolboxj.tuples.impl;

import org.ticdev.toolboxj.tuples.Single;
import org.ticdev.toolboxj.tuples.SingleView;
import org.ticdev.toolboxj.tuples.TupleSupport;

/**
 * Concrete single implementation.
 *
 * @param <T1> the type of the first element.
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public final class SingleImpl<T1>
        implements Single<T1> {

    /**
     * Default serial version.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The first element.
     */
    private final T1 item1;

    /**
     * The cached hash code.
     */
    private int cachedHashCode;

    /**
     * Class constructor.
     *
     * @param item1Value the first element
     */
    public SingleImpl(final T1 item1Value) {
        this.item1 = item1Value;
    }

    /**
     * Copy constructor.
     *
     * @param source the single from which to copy the value.
     */
    public SingleImpl(final SingleView<T1> source) {
        this(source.item1());
    }

    @Override
    public T1 item1() {
        return item1;
    }

    @Override
    public int hashCode() {
        if (cachedHashCode == 0) {
            cachedHashCode = TupleSupport.hashCode(item1);
        }
        return cachedHashCode;
    }

    @Override
    public boolean equals(final Object obj) {
        return this == obj
            || obj instanceof SingleView<?>
            && TupleSupport.singleEquals(this, (SingleView<?>) obj);
    }

    @Override
    public Single<T1> self() {
        return this;
    }
}
