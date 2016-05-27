package org.ticdev.toolboxj.tuplesnew;

import java.util.Objects;

/**
 * TupleSupport utility methods.
 *
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
public class TupleSupport {

    /**
     * Method for creatings consistent hash codes for tuples.
     * <p/>
     * The method returns 0 only if all the objects in the tuple are null.
     * Otherwise it will return {@link Objects#hash(Object...)} if
     * different from 0, otherwise 1.
     *
     * @param objects the objects for which to compute the hash code
     * @return the hash code
     */
    public static final int hashCode(Object... objects) {
        for (Object o : objects) {
            if (o != null) {
                int hash_code = Objects.hash(objects);
                return hash_code == 0 ? 1 : hash_code;
            }
        }
        return 0;
    }

    /**
     * Single equals method.
     *
     * @param single the non-null single
     * @param obj    the non-null pair to compare against
     * @return true if the two objects are equal and false otherwise.
     */
    public static final boolean singleEquals(
            Single<?> single, Single<?> obj) {
        return Objects.equals(single.item1(), obj.item1());
    }

    /**
     * Pair equals method
     *
     * @param pair the non-null pair
     * @param obj  the non-null pair to compare against
     * @return true if the two objects are equal and false otherwise
     */
    public static final boolean pairEquals(
            Pair<?, ?> pair, Pair<?, ?> obj) {
        return pair == obj
                ? true
                : (Objects.equals(pair.item1(), obj.item1()) &&
                   Objects.equals(pair.item2(), obj.item2()));
    }

    /**
     * Pair equals method
     *
     * @param triplet the non-null pair
     * @param obj     the non-null pair to compare against
     * @return true if the two objects are equal and false otherwise
     */
    public static final boolean tripletEquals(
            Triplet<?, ?, ?> triplet, Triplet<?, ?, ?> obj) {
        return triplet == obj
                ? true
                : (Objects.equals(triplet.item1(), triplet.item1()) &&
                   Objects.equals(triplet.item2(), obj.item2()) &&
                   Objects.equals(triplet.item3(), obj.item3()));
    }

    /**
     * Pair equals method
     *
     * @param quad the non-null pair
     * @param obj  the non-null pair to compare against
     * @return true if the two objects are equal and false otherwise
     */
    public static final boolean quadEquals(
            Quad<?, ?, ?, ?> quad, Quad<?, ?, ?, ?> obj) {
        return quad == obj
                ? true
                : (Objects.equals(quad.item1(), obj.item1()) &&
                   Objects.equals(quad.item2(), obj.item2()) &&
                   Objects.equals(quad.item3(), obj.item3()) &&
                   Objects.equals(quad.item4(), obj.item4()));
    }

}
