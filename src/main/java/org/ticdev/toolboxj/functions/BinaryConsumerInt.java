package org.ticdev.toolboxj.functions;

/**
 * {@link BinaryConsumer} generalization for int arguments.
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 */
public interface BinaryConsumerInt
    extends
    BinaryConsumer<Integer, Integer> {

    /**
     * Calls the method with the given arguments.
     * 
     * @param arg1
     *            the first argument
     * @param arg2
     *            the second argument
     */
    void acceptInt(int arg1, int arg2);

    @Override
    default void accept(Integer arg1, Integer arg2) {
        acceptInt(arg1, arg2);
    }

}
