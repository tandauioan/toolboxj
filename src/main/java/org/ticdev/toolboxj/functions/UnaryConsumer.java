package org.ticdev.toolboxj.functions;

/**
 * A method that is called with one argument.
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 * @param <A1>
 *            the type of the argument
 */
public interface UnaryConsumer<A1> {

    /**
     * Calls the method on the given argument
     * 
     * @param arg1
     *            the argument
     */
    void accept(A1 arg1);

}
