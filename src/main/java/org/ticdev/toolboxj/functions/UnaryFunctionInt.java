package org.ticdev.toolboxj.functions;

/**
 * {@link UnaryFunction} generalization for int primitives.
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 * @param <R>
 *            the type of the result.
 */
public interface UnaryFunctionInt<R>
    extends
    UnaryFunction<Integer, R> {

    /**
     * Calls the function with the given argument and returns a result.
     * 
     * @param arg1
     *            the argument
     * @return the result
     */
    R applyInt(int arg1);

    @Override
    default R apply(Integer arg1) {
        return applyInt(arg1);
    }

}
