package org.ticdev.toolboxj.tuples;

import java.util.Objects;

import org.ticdev.toolboxj.tuples.impl.SingleImpl;

public class Tuples {

    /**
     * Consistent method for creating hashes for tuples.
     * 
     * The method returns 0 only if all the objects in the tuple are null.
     * Otherwise it will return {@link Objects#hash(Object...)} if different
     * from 0, otherwise 1.
     * 
     * @param objects
     * @return
     */
    public static int hashCode(Object... objects) {
        for (Object o : objects) {
            if (o != null) {
                int hash_code = Objects.hash(objects);
                return hash_code == 0 ? 1 : hash_code;
            }
        }
        return 0;
    }

    /**
     * Equals method use by single implementations to provide a consistent
     * comparison.
     * 
     * @param single
     *            the non-null single
     * @param obj
     *            the object to compare against
     * @return true if the two objects are equal
     */
    public static boolean singleEquals(Single<?> single, Object obj) {
        return (single == obj) || ((obj instanceof Single)
            && Objects.equals(single.item1(), ((Single<?>) obj).item1()));
    }

    /**
     * Creates and returns a new instance of a single.
     * 
     * @param item1
     *            the value of the single's item
     * @return the new single
     */
    public static <T1> Single<T1> of(T1 item1) {
        return new SingleImpl<T1>(item1);
    }

}
