package org.ticdev.toolboxj.tuples;

import java.util.Objects;

import org.ticdev.toolboxj.tuples.impl.MutablePairImpl;
import org.ticdev.toolboxj.tuples.impl.MutableSingleImpl;
import org.ticdev.toolboxj.tuples.impl.MutableTripletImpl;
import org.ticdev.toolboxj.tuples.impl.PairImpl;
import org.ticdev.toolboxj.tuples.impl.SingleImpl;
import org.ticdev.toolboxj.tuples.impl.TripletImpl;

/**
 * Tuples utility methods.
 * 
 * <p>
 * Provides factory methods and utility methods for creating alternative
 * implementations to existing tuples.
 * </p>
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 */
public class Tuples {

    /**
     * Consistent method for creating hashes for tuples.
     * 
     * The method returns 0 only if all the objects in the tuple are null.
     * Otherwise it will return {@link Objects#hash(Object...)} if different
     * from 0, otherwise 1.
     * 
     * @param objects
     *            the objects for which to compute the hash code
     * @return the hash code
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
     * Equals method used by single implementations to provide a consistent
     * comparison.
     * 
     * @param single
     *            the non-null single
     * @param obj
     *            the non-null single object to compare against
     * @return true if the two objects are equal and false otherwise
     */
    public static boolean
        singleEquals(SingleContainer<?> single, SingleContainer<?> obj) {
            return Objects.equals(single.item1(), obj.item1());
    }

    /**
     * Equals method used by pair implementations to provide a consistent
     * comparison.
     * 
     * @param pair
     *            the non-null pair
     * @param obj
     *            the non-null pair object to compare against
     * @return true if the two objects are equal and false otherwise
     */
    public static boolean
        pairEquals(PairContainer<?, ?> pair, PairContainer<?, ?> obj) {
        if (pair == obj) {
            return true;
        }
        return Objects.equals(pair.item1(), obj.item1()) 
            && Objects.equals(pair.item2(), obj.item2());
    }

    /**
     * Equals method used by triplet implementations to provide a consistent
     * comparison.
     * 
     * @param triplet
     *            the non-null triplet
     * @param obj
     *            the non-null triplet to compare against.
     * @return true iof the two objects are equal and false otherwise
     */
    public static boolean
        tripletEquals(TripletContainer<?, ?, ?> triplet, TripletContainer<?, ?, ?>  obj) {
        if (triplet == obj) {
            return true;
        }
        return Objects.equals(triplet.item1(), obj.item1())
            && Objects.equals(triplet.item2(), obj.item2())
            && Objects.equals(triplet.item3(), obj.item3());
    }

    /**
     * Creates and returns a new instance of a single.
     * 
     * @param item1
     *            the value of the single's item
     * @return the new single
     * @param <T1>
     *            the type of the first element
     */
    public static <T1> Single<T1> of(T1 item1) {
        return new SingleImpl<>(item1);
    }

    /**
     * Returns a new single instance whose content is copied from the given
     * single.
     * 
     * @param source
     *            the non-null source
     * @return the new single
     * @param <T1>
     *            the type of the first element
     */
    public static <T1> Single<T1> copyOf(SingleContainer<T1> source) {
        return of(source.item1());
    }

    /**
     * Creates and returns a new instance of a mutable single
     * 
     * @param item1
     *            the value of the single's item
     * @return the new single
     * @param <T1>
     *            the type of the first element
     */
    public static <T1> MutableSingle<T1> mutableOf(T1 item1) {
        return new MutableSingleImpl<>(item1);
    }

    /**
     * Returns a new mutable single instance whose content is copied from the
     * given single
     * 
     * @param source
     *            the non-null source
     * @return the new mutable single
     * @param <T1>
     *            the type of the first element
     */
    public static <T1> MutableSingle<T1>
        mutableCopyOf(SingleContainer<T1> source) {
        return mutableOf(source.item1());
    }

    /**
     * Returns a new instance of a mutable single with all null elements.
     * 
     * @return the new mutable single
     * @param <T1>
     *            the type of the first element
     */
    public static <T1> MutableSingle<T1> newMutableSingle() {
        return new MutableSingleImpl<>();
    }

    /**
     * Creates and returns a new instance of a pair.
     * 
     * @param item1
     *            the first element
     * @param item2
     *            the second element
     * @return the new pair
     * @param <T1>
     *            the type of the first element
     * @param <T2>
     *            the type of the second element
     */
    public static <T1, T2> Pair<T1, T2> of(T1 item1, T2 item2) {
        return new PairImpl<>(item1, item2);
    }

    /**
     * Returns a new pair instance whose content is copied from the given pair
     * 
     * @param source
     *            the non-null source
     * @return the new pair
     * @param <T1>
     *            the type of the first element
     * @param <T2>
     *            the type of the second element
     */
    public static <T1, T2> Pair<T1, T2>
        copyOf(PairContainer<T1, T2> source) {
        return of(source.item1(), source.item2());
    }

    /**
     * Returns a new instance of a mutable pair
     * 
     * @param item1
     *            the first element
     * @param item2
     *            the second element
     * @return the new mutable pair
     * @param <T1>
     *            the type of the first element
     * @param <T2>
     *            the type of the second element
     */
    public static <T1, T2> MutablePair<T1, T2>
        mutableOf(T1 item1, T2 item2) {
        return new MutablePairImpl<>(item1, item2);
    }

    /**
     * Returns a new mutable pair instance whose content is copied from the
     * given pair.
     * 
     * @param source
     *            the non-null source
     * @return the new mutable pair
     * @param <T1>
     *            the type of the first element
     * @param <T2>
     *            the type of the second element
     */
    public static <T1, T2> MutablePair<T1, T2>
        mutableCopyOf(PairContainer<T1, T2> source) {
        return mutableOf(source.item1(), source.item2());
    }

    /**
     * Returns a new instance of a mutable pair with all null elements.
     * 
     * @return the new mutable pair
     * @param <T1>
     *            the type of the first element
     * @param <T2>
     *            the type of the second element
     */
    public static <T1, T2> MutablePair<T1, T2> newMutablePair() {
        return new MutablePairImpl<>();
    }

    /**
     * Returns a new instance of a triplet.
     * 
     * @param item1
     *            the first element
     * @param item2
     *            the second element
     * @param item3
     *            the third element
     * @return the new triplet
     * @param <T1>
     *            the type of the first element
     * @param <T2>
     *            the type of the second element
     * @param <T3>
     *            the type of the third element
     */
    public static <T1, T2, T3> Triplet<T1, T2, T3>
        of(T1 item1, T2 item2, T3 item3) {
        return new TripletImpl<>(item1, item2, item3);
    }

    /**
     * Returns a new triplet instance whose content is copied from the given
     * triplet
     * 
     * @param source
     *            the non-null source
     * @return the new triplet
     * @param <T1>
     *            the type of the first element
     * @param <T2>
     *            the type of the second element
     * @param <T3>
     *            the type of the third element
     */
    public static <T1, T2, T3> Triplet<T1, T2, T3>
        copyOf(TripletContainer<T1, T2, T3> source) {
        return of(source.item1(), source.item2(), source.item3());
    }

    /**
     * Returns a new instance of a mutable triplet.
     * 
     * @param item1
     *            the first element
     * @param item2
     *            the second element
     * @param item3
     *            the third element
     * @return the new mutable triplet
     * @param <T1>
     *            the type of the first element
     * @param <T2>
     *            the type of the second element
     * @param <T3>
     *            the type of the third element
     */
    public static <T1, T2, T3> MutableTriplet<T1, T2, T3>
        mutableOf(T1 item1, T2 item2, T3 item3) {
        return new MutableTripletImpl<>(item1, item2, item3);
    }

    /**
     * Returns a new mutable triplet whose content is copied from the given
     * triplet.
     * 
     * @param source
     *            the non-null source
     * @return the new mutable triplet
     * @param <T1>
     *            the type of the first element
     * @param <T2>
     *            the type of the second element
     * @param <T3>
     *            the type of the third element
     */
    public static <T1, T2, T3> MutableTriplet<T1, T2, T3>
        mutableCopyOf(TripletContainer<T1, T2, T3> source) {
        return mutableOf(source.item1(), source.item2(), source.item3());
    }

    /**
     * Returns a new instance of a mutable triplet with all null elements.
     * 
     * @return the new mutable triplet
     * @param <T1>
     *            the type of the first element
     * @param <T2>
     *            the type of the second element
     * @param <T3>
     *            the type of the third element
     */
    public static <T1, T2, T3> MutableTriplet<T1, T2, T3>
        newMutableTriplet() {
        return new MutableTripletImpl<>();
    }

}
