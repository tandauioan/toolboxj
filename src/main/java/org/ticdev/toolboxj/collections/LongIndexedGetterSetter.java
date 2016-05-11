package org.ticdev.toolboxj.collections;

/**
 * Declaration for sized collections that provide long indexed read write access
 * to their elements.
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 * @param <E> the type of elements in the collection
 */
public interface LongIndexedGetterSetter<E>
        extends
        LongIndexedGetter<E>,
        LongIndexedSetter<E>,
        LongSized {

    /**
     * Returns a new instance of {@link LongIndexedGetterSetter} that wraps an
     * instance of {@link IntIndexedGetterSetter}.
     *
     * @param <T> the type of elements in the getter/setter.
     * @param gs the int indexed getter setter instance
     * @return the long indexed wrapper
     */
    static <T> LongIndexedGetterSetter<T> wrap(
            final IntIndexedGetterSetter<T> gs) {
        return new LongIndexedGetterSetter<T>() {
            @Override
            public T get(long index) throws IndexOutOfBoundsException {
                return gs.get((int) index);
            }

            @Override
            public void set(long index, T value) {
                gs.set((int) index, value);
            }

            @Override
            public long size() {
                return gs.size();
            }
        };
    }

}
