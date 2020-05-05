package org.ticdev.toolboxj.self;

import org.ticdev.toolboxj.functions.UnaryFunction;

/**
 * A mapper on a self interface.
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 * @deprecated {@link Self} generalizations have been deprecated in favor
 * on default methods too keep the {@link Self} interface self-contained.
 * @param <T> the self-referencing implementation
 */
public interface SelfMapper<T extends SelfMapper<T>>
    extends
    Self<T> {

    /**
     * Map this instance to a result
     * 
     * @param mapper
     *            the mapper function
     * @return the result of the mapping operation
     * @param <R>
     *            the type of the mapping result
     */
    default <R> R map(UnaryFunction<? super T, R> mapper) {
        return mapper.apply(self());
    }

}
