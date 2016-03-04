package org.ticdev.toolboxj.tuples;

import java.io.Serializable;

/**
 * Triplet tuple interface.
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 * @param <T1>
 *            the type of the first element
 * @param <T2>
 *            the type of the second element
 * @param <T3>
 *            the type of the third element
 */
public interface Triplet<T1, T2, T3>
    extends
    TripletContainer<T1, T2, T3>,
    Tuple<Triplet<T1, T2, T3>>,
    Serializable {

    
    @Override
    default Triplet<T1, T2, T3> self() {
        return this;
    }

}
