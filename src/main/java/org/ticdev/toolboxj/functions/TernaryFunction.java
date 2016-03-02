package org.ticdev.toolboxj.functions;

/**
 * A function receiving three arguments and returning a result.
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 * @param <A1>
 *            the type of the first argument
 * @param <A2>
 *            the type of the second argument
 * @param <A3>
 *            the type of the third argument
 * @param <R>
 *            the type of the result
 */
public interface TernaryFunction<A1, A2, A3, R> {

    /**
     * Calls the function with the fiven arguments
     * 
     * @param arg1
     *            the first argument
     * @param arg2
     *            the second argument
     * @param arg3
     *            the third argument
     * @return the result
     */
    R apply(A1 arg1, A2 arg2, A3 arg3);

}
