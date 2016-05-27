package org.ticdev.toolboxj.tuplesnew;

/**
 * Mutable quad interface.
 *
 * @param <T1> the type of the first element
 * @param <T2> the type of the second element
 * @param <T3> the type of the third element
 * @param <T4> the type of the fourth element
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public interface MutableQuad<T1, T2, T3, T4>
        extends Quad<T1, T2, T3, T4> {

    /**
     * Sets the value of the first element.
     *
     * @param item1 the value
     * @return this instance
     */
    MutableQuad<T1, T2, T3, T4> item1(T1 item1);

    /**
     * Sets the value of the second element
     *
     * @param item2 the value
     * @return this instance
     */
    MutableQuad<T1, T2, T3, T4> item2(T2 item2);

    /**
     * Sets the value of the third element
     *
     * @param item3 the value
     * @return this instance
     */
    MutableQuad<T1, T2, T3, T4> item3(T3 item3);

    /**
     * Sets the value of the fourth element
     *
     * @param item4 the value
     * @return this instance
     */
    MutableQuad<T1, T2, T3, T4> item4(T4 item4);

    /**
     * Copy the value from the given quad.
     *
     * @param source the quad to copy from
     * @return this instance
     */
    MutableQuad<T1, T2, T3, T4> copyFrom(Quad<T1, T2, T3, T4> source);

}
