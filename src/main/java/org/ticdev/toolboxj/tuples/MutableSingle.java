package org.ticdev.toolboxj.tuples;

/**
 * Mutable single interface.
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 * @param <T1>
 *            the type of the first element
 */
public interface MutableSingle<T1>
    extends
    SingleContainer<T1>,
    SingleContainerSetter<T1, MutableSingle<T1>>,
    Tuple<MutableSingle<T1>>,
    MutableTuple<MutableSingle<T1>> {

    @Override
    default org.ticdev.toolboxj.tuples.MutableSingle<T1> self() {
        return this;
    };

    /**
     * Sets the new value for the first item
     * 
     * @param item1
     *            the new value
     * @return this instance
     */
    @Override
    MutableSingle<T1> item1(T1 item1);

}
