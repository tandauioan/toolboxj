package org.ticdev.toolboxj.tuples.impl;

import org.ticdev.toolboxj.tuples.Triplet;
import org.ticdev.toolboxj.tuples.TripletView;
import org.ticdev.toolboxj.tuples.TupleSupport;

/**
 * Concrete implementation of {@link Triplet}.
 *
 * @param <T1> the type of the first element
 * @param <T2> the type of the second element
 * @param <T3> the type of the third element
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public class TripletImpl<T1, T2, T3>
        implements Triplet<T1, T2, T3> {

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
     * the third element
     */
    private final T3 item3;

    /**
     * cached hash code
     */
    private int cached_hash_code_;

    /**
     * Class constructor.
     *
     * @param item1 the first element
     * @param item2 the second element
     * @param item3 the third element
     */
    public TripletImpl(T1 item1, T2 item2, T3 item3) {
        this.item1 = item1;
        this.item2 = item2;
        this.item3 = item3;
    }

    /**
     * Copy constructor.
     *
     * @param source the triplet from which to copy the value
     */
    public TripletImpl(TripletView<T1, T2, T3> source) {
        this(source.item1(), source.item2(), source.item3());
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
    public T3 item3() {
        return item3;
    }

    @Override
    public int hashCode() {
        if (cached_hash_code_ == 0) {
            cached_hash_code_ =
                    TupleSupport.hashCode(item1, item2, item3);
        }
        return cached_hash_code_;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof TripletView<?, ?, ?>) &&
               TupleSupport.tripletEquals(this, (TripletView<?, ?, ?>) obj);
    }

    @Override
    public Triplet<T1, T2, T3> self() {
        return this;
    }
}
