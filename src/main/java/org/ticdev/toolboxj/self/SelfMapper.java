package org.ticdev.toolboxj.self;

import java.util.Optional;

import org.ticdev.toolboxj.functions.UnaryFunction;

/**
 * A mapper on a self interface.
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 * @param <T>
 *            the self-referencing implementation
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
     */
    default <R> Optional<R> map(UnaryFunction<? super T, R> mapper) {
        return Optional.ofNullable(mapper.apply(self()));
    }

}
