package org.ticdev.toolboxj.tuplesnew.impl;

import org.ticdev.toolboxj.tuplesnew.Pair;
import org.ticdev.toolboxj.tuplesnew.TupleSupport;

/**
 * Concrete implementation of {@link Pair}.
 *
 * @param <T1> the type of the first element
 * @param <T2> the type of the second element
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public class PairImpl<T1, T2>
        implements Pair<T1, T2> {

    /**
     * default serial version
     */
    private static final long serialVersionUID = 1L;

    /**
     * the first element
     */
    private final T1 item1;

    /**
     * the second element
     */
    private final T2 item2;

    /**
     * cached hash code
     */
    private int cached_hash_code_;

    /**
     * Class constructor
     *
     * @param item1 the first element
     * @param item2 the second element
     */
    public PairImpl(T1 item1, T2 item2) {
        this.item1 = item1;
        this.item2 = item2;
    }

    /**
     * Copy constructor.
     *
     * @param source the pair from which to copy the value
     */
    public PairImpl(Pair<T1, T2> source) {
        this(source.item1(), source.item2());
    }

    @Override
    public T1 item1() {
        return item1;
    }

    @Override
    public T2 item2() {
        return item2;
    }

    @Override
    public int hashCode() {
        if (cached_hash_code_ == 0) {
            cached_hash_code_ = TupleSupport.hashCode(item1, item2);
        }
        return cached_hash_code_;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Pair<?, ?>) &&
               TupleSupport.pairEquals(this, (Pair<?, ?>) obj);
    }
}
