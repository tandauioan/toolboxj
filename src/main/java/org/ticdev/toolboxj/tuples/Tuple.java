package org.ticdev.toolboxj.tuples;

import java.util.Optional;
import java.util.function.Predicate;

import org.ticdev.toolboxj.functions.UnaryConsumer;
import org.ticdev.toolboxj.functions.UnaryFunction;

/**
 * Basic tuple interface.
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 * @param <T>
 *            tuple type
 */
public interface Tuple<T extends Tuple<T>> {

    /**
     * A consumer that does nothing
     */
    static final UnaryConsumer<Object> DO_NOTHING = (e) -> {
    };

    /**
     * If the predicate applied to this instance returns true then do thenDo,
     * else do elseDo.
     * 
     * @param predicate
     *            the predicate
     * @param thenDo
     *            consumer if the predicate is true
     * @param elseDo
     *            consumer if the predicate is false
     * @return this instance
     */
    default T ifThenElse(
        Predicate<? super T> predicate,
        UnaryConsumer<? super T> thenDo,
        UnaryConsumer<? super T> elseDo) {
        T self = self();
        if (predicate.test(self)) {
            thenDo.accept(self);
        } else {
            elseDo.accept(self);
        }
        return self;
    }

    /**
     * Returns itself.
     * 
     * @return this instance
     */
    T self();

    /**
     * Execute thenDo if the predicate applied to this tuple returns true.
     * 
     * @param predicate
     *            the predicate to apply
     * @param thenDo
     *            the action to take if the predicate returns true
     * @return this instance
     */
    default T ifDo(
        Predicate<? super T> predicate,
        UnaryConsumer<? super T> thenDo) {
        return ifThenElse(predicate, thenDo, DO_NOTHING);
    }

    /**
     * Map this instance to the given result
     * 
     * @param mapper
     *            the mapping function
     * @return an optional containing the result
     * @param <R>
     *            the type of the result
     * 
     */
    default <R> Optional<R> map(UnaryFunction<? super T, R> mapper) {
        return Optional.ofNullable(mapper.apply(self()));
    }

    /**
     * Filter this instance using the predicate and returning an optional
     * containing this instance if the predicate returns true, and and empty
     * optional otherwise.
     * 
     * @param predicate
     *            the predicate to apply to this tuple
     * @return an optional containing this instance if the predicate is true and
     *         empty otherwise
     */
    default Optional<T> filter(Predicate<? super T> predicate) {
        T self = self();
        return predicate.test(self)
            ? Optional.of(self)
            : Optional.empty();
    }

    /**
     * Calls the given consumer with this instance.
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
