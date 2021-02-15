package org.ticdev.toolboxj.functions;

import java.util.Collection;
import java.util.Iterator;

/**
 *
 * Reduce function declaration.
 *
 * <p>
 * The {@link #reduce()} method, if implemented, returns a default initial value
 * for the operation. The method must throw
 * {@link UnsupportedOperationException} if it's not implemented.</p>
 *
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 * @param <TYPE> the type of the operands
 */
public interface ReduceFunction<TYPE> {

    /**
     * Optional function that returns an identity value relative to the
     * reduction operation.
     *
     * @return an identity value relative to the reduction operation.
     * @throws UnsupportedOperationException if the method is not implemented
     */
    TYPE reduce() throws UnsupportedOperationException;

    /**
     * Identity operation that returns its argument.
     *
     * @param arg1 the argument
     * @return the argument
     */
    default TYPE reduce(TYPE arg1) {
        return arg1;
    }

    /**
     * Reduces the two arguments.
     *
     * @param arg1 first argument
     * @param arg2 second argument
     * @return the result of the reduction
     */
    TYPE reduce(TYPE arg1, TYPE arg2);

    /**
     * Reduces the given arguments.
     *
     * @param arg1 the first argument
     * @param arg2 the second argument
     * @param rest the rest of the arguments
     * @return the reduced value
     */
    @SuppressWarnings("unchecked")
    default TYPE reduce(TYPE arg1, TYPE arg2, TYPE... rest) {
        TYPE res = reduce(arg1, arg2);
        for (TYPE r : rest) {
            res = reduce(res, r);
        }
        return res;
    }

    /**
     * Reduces the given arguments. By default, if the operands is an empty
     * collection the {@link #reduce() } method is called, but that may fail if
     * the method is not implemented.
     *
     * @param operands the arguments
     * @return the reduced value.
     */
    default TYPE reduce(Collection<TYPE> operands) {
        int sz = operands.size();
        Iterator<TYPE> it;
        switch (sz) {
            case 0:
                return reduce();
            case 1:
                return reduce(operands.iterator().next());
            case 2:
                it = operands.iterator();
                return reduce(it.next(), it.next());
            default:
                it = operands.iterator();
                TYPE res = reduce(it.next(), it.next());
                while (it.hasNext()) {
                    res = reduce(res, it.next());
                }
                return res;
        }
    }

}
