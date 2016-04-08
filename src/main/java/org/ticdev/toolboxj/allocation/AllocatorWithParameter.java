package org.ticdev.toolboxj.allocation;

/**
 * Allocator interface that creates objects configured to conform to the given
 * parameter.
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 * @param <T>
 *            the type of instance returned by the allocator
 * @param
 *            <P>
 *            the allocation parameter
 */
public interface AllocatorWithParameter<T, P> {

    /**
     * Allocates and returns a new instance of T. Returns null if the object
     * could not be allocated.
     * 
     * @param parameter
     *            the parameter for construction
     * @return a new instance of T, or null if the allocation fails.
     */
    default T allocate(P parameter) {
        try {
            return allocateOrThrow();
        } catch (NullPointerException | IllegalArgumentException ex) {
            return null;
        }
    }

    /**
     * Allocates an instance of T. It will throw an
     * {@link IllegalArgumentException} if the allocation fails because the
     * object cannot be constructed based on the parameter and
     * {@link NullPointerException} if the allocation fails for any other
     * reason.
     * 
     * @return the allocated instance of T.
     * @throws NullPointerException
     *             if an allocation exception occurs.
     * @throws IllegalArgumentException
     *             if the object cannot be constructed to conform to the
     *             parameter.
     */
    T allocateOrThrow()
        throws NullPointerException,
        IllegalArgumentException;

}
