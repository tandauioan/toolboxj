package org.ticdev.toolboxj.tuples;

/**
 * Single tuple interface.
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 * @param <T1>
 *            argument type
 */
public interface Single<T1> {

    /**
     * Returns the first (and only) item.
     * 
     * @return the first item
     */
    T1 item1();

}
