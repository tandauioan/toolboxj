package org.ticdev.toolboxj.self;

import org.ticdev.toolboxj.functions.UnaryConsumer;

/**
 * A consumer on a self interface.
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 * @param <T>
 *            the self referencing implementation
 */
public interface SelfConsumer<T extends SelfConsumer<T>>
    extends
    Self<T> {

    /**
     * Calls the given consumer on this instance.
     * 
     * @param consumer
     *            the consumer
     * @return this instance
     */
    default T doConsume(UnaryConsumer<? super T> consumer) {
        T self = self();
        consumer.accept(self);
        return self;
    }

}
