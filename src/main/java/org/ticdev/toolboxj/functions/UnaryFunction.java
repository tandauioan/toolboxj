package org.ticdev.toolboxj.functions;

/**
 * A function receiving an argument and returning a result.
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 * @param <A1>
 *            the type of the argument
 * @param <R>
 *            the type of the result
 */
public interface UnaryFunction<A1, R> {
    /**
     * Calls the function on the given argument
     * 
     * @param arg1
     *            the argument
     * @return the result
     */
    R apply(A1 arg1);
}
