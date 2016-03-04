package org.ticdev.toolboxj.tuples;

import java.io.Serializable;

/**
 * Single tuple interface.
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 * @param <T1>
 *            the type of the first element
 */
public interface Single<T1>
    extends
    SingleContainer<T1>,
    Tuple<Single<T1>>,
    Serializable {

    @Override
    default Single<T1> self() {
        return this;
    }

}
