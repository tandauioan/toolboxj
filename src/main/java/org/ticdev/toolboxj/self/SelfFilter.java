package org.ticdev.toolboxj.self;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * A filter on a Self interface.
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 * @param <T>
 *            the self-referencing implementation
 */
public interface SelfFilter<T extends SelfFilter<T>>
    extends
    Self<T> {

    /**
     * Filters this instance using the predicate and returns an optional
     * containing this instance of the predicate returns true, and an empty
     * optional otherwise.
     * 
     * @param predicate
     *            the predicate to apply
     * @return an optional containing this instance if the predicate returns
     *         true, and an empty optional otherwise.
     */
    default Optional<T> filter(Predicate<? super T> predicate) {
        T self = self();
        return predicate.test(self)
            ? Optional.of(self)
            : Optional.empty();
    }

}
