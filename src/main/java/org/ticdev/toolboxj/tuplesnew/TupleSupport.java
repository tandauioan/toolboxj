package org.ticdev.toolboxj.tuplesnew;

import org.ticdev.toolboxj.tuplesnew.impl.*;

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
     * Single equals method.
     *
     * @param single the non-null single
     * @param obj    the non-null pair to compare against
     * @return true if the two objects are equal and false otherwise.
     */
    public static boolean singleEquals(
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
    public static boolean pairEquals(
            Pair<?, ?> pair, Pair<?, ?> obj) {
        return pair == obj ||
               (Objects.equals(pair.item1(), obj.item1()) &&
                Objects.equals(pair.item2(), obj.item2()));
    }

    /**
     * Pair equals method
     *
     * @param triplet the non-null pair
     * @param obj     the non-null pair to compare against
     * @return true if the two objects are equal and false otherwise
     */
    public static boolean tripletEquals(
            Triplet<?, ?, ?> triplet, Triplet<?, ?, ?> obj) {
        return triplet == obj ||
               (Objects.equals(triplet.item1(), triplet.item1()) &&
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
    public static boolean quadEquals(
            Quad<?, ?, ?, ?> quad, Quad<?, ?, ?, ?> obj) {
        return quad == obj ||
                (Objects.equals(quad.item1(), obj.item1()) &&
                   Objects.equals(quad.item2(), obj.item2()) &&
                   Objects.equals(quad.item3(), obj.item3()) &&
                   Objects.equals(quad.item4(), obj.item4()));
    }

    /**
     * Returns a new instance of {@link Single}.
     *
     * @param item1 the first element
     * @param <T1>  the type of the first element
     * @return the new instance
     */
    public static <T1> Single<T1> of(T1 item1) {
        return new SingleImpl<>(item1);
    }

    /**
     * Returns a new instance of {@link Single}
     *
     * @param source the source of values
     * @param <T1>   the type of the first element
     * @return the new instance
     */
    public static <T1> Single<T1> copyOf(Single<T1> source) {
        return new SingleImpl<>(source);
    }

    /**
     * Returns a new instance of {@link MutableSingle}.
     *
     * @param item1 the first element
     * @param <T1>  the type of the first elemnet
     * @return the new instance
     */
    public static <T1> MutableSingle<T1> mutableOf(T1 item1) {
        return new MutableSingleImpl<>(item1);
    }

    /**
     * Returns a new instance of {@link MutableSingle} will all the values set to null.
     *
     * @param <T1> the type of the first element
     * @return the new instance
     */
    public static <T1> MutableSingle<T1> newMutableSingle() {
        return new MutableSingleImpl<>((T1) null);
    }

    /**
     * Returns a new instance of {@link MutableSingle}.
     *
     * @param source the source of values
     * @param <T1>   the type of the first element
     * @return the new instance
     */
    public static <T1> MutableSingle<T1> mutableCopyOf(
            Single<T1> source) {
        return new MutableSingleImpl<>(source);
    }

    /**
     * Returns a new instance of {@link Pair}.
     *
     * @param item1 the first element
     * @param item2 the second element
     * @param <T1>  the type of the first element
     * @param <T2>  the type of the second element
     * @return the new instance
     */
    public static <T1, T2> Pair<T1, T2> of(T1 item1, T2 item2) {
        return new PairImpl<>(item1, item2);
    }

    /**
     * Returns a new instance of {@link Pair}
     *
     * @param source the source of values
     * @param <T1>   the type of the first element
     * @param <T2>   the type of the second element
     * @return the new instance
     */
    public static <T1, T2> Pair<T1, T2> copyOf(Pair<T1, T2> source) {
        return new PairImpl<>(source);
    }

    /**
     * Returns a new instance of {@link MutablePair}.
     *
     * @param item1 the first element
     * @param item2 the second element
     * @param <T1>  the type of the first element
     * @param <T2>  the type of the second element
     * @return the new instance
     */
    public static <T1, T2> MutablePair<T1, T2> mutableOf(
            T1 item1, T2 item2) {
        return new MutablePairImpl<>(item1, item2);
    }

    /**
     * Returns a new instance of {@link MutablePair} with all the values set to null.
     *
     * @param <T1> the type of the first element
     * @param <T2> the type of the second element
     * @return the new instance
     */
    public static <T1, T2> MutablePair<T1, T2> newMutablePair() {
        return new MutablePairImpl<>(null, null);
    }

    /**
     * Returns a new instance of {@link MutablePair}
     *
     * @param source the source of values
     * @param <T1>   the type of the first element
     * @param <T2>   the type of the second element
     * @return the new instance
     */
    public static <T1, T2> MutablePair<T1, T2> mutableCopyOf(
            Pair<T1, T2> source) {
        return new MutablePairImpl<>(source);
    }

    /**
     * Returns a new instance of {@link Triplet}
     *
     * @param item1 the first element
     * @param item2 the second element
     * @param item3 the third element
     * @param <T1>  the type of the first element
     * @param <T2>  the type of the second element
     * @param <T3>  the type of the third element
     * @return the new instance
     */
    public static <T1, T2, T3> Triplet<T1, T2, T3> of(
            T1 item1, T2 item2, T3 item3) {
        return new TripletImpl<>(item1, item2, item3);
    }

    /**
     * Returns a new instance of {@link Triplet}
     *
     * @param source the source of values
     * @param <T1>   the type of the first element
     * @param <T2>   the type of the second element
     * @param <T3>   the type of the third element
     * @return the new instance
     */
    public static <T1, T2, T3> Triplet<T1, T2, T3> copyOf(
            Triplet<T1, T2, T3> source) {
        return new TripletImpl<>(source);
    }

    /**
     * Returns a new instance of {@link MutableTriplet}
     *
     * @param item1 the first element
     * @param item2 the second element
     * @param item3 the third element
     * @param <T1>  the type of the first element
     * @param <T2>  the type of the second element
     * @param <T3>  the type of the third element
     * @return the new instance
     */
    public static <T1, T2, T3> MutableTriplet<T1, T2, T3> mutableOf(
            T1 item1, T2 item2, T3 item3) {
        return new MutableTripletImpl<>(item1, item2, item3);
    }

    /**
     * Returns a new instance of {@link MutableTriplet} will all the values set to null.
     *
     * @param <T1> the type of the first element
     * @param <T2> the type of the second element
     * @param <T3> the type of the third element
     * @return the new instance
     */
    public static <T1, T2, T3> MutableTriplet<T1, T2, T3> newMutableTriplet() {
        return new MutableTripletImpl<>(null, null, null);
    }

    /**
     * Returns a new instance of {@link MutableTriplet}
     *
     * @param source the source of values
     * @param <T1>   the type of the first element
     * @param <T2>   the type of the second element
     * @param <T3>   the type of the third element
     * @return the new instance
     */
    public static <T1, T2, T3> MutableTriplet<T1, T2, T3> mutableCopyOf(
            Triplet<T1, T2, T3> source) {
        return new MutableTripletImpl<>(source);
    }


    /**
     * Returns a new instance of {@link Quad}
     *
     * @param item1 the first element
     * @param item2 the second element
     * @param item3 the third element
     * @param item4 the fourth element
     * @param <T1>  the type of the first element
     * @param <T2>  the type of the second element
     * @param <T3>  the type of the third element
     * @param <T4>  the type of the fourth element
     * @return the new instance
     */
    public static <T1, T2, T3, T4> Quad<T1, T2, T3, T4> of(
            T1 item1, T2 item2, T3 item3, T4 item4) {
        return new QuadImpl<>(item1, item2, item3, item4);
    }

    /**
     * Returns a new instance of {@link Quad}
     *
     * @param source the source of values
     * @param <T1>   the type of the first element
     * @param <T2>   the type of the second element
     * @param <T3>   the type of the third element
     * @param <T4>   the type of the fourth element
     * @return the new instance
     */
    public static <T1, T2, T3, T4> Quad<T1, T2, T3, T4> copyOf(
            Quad<T1, T2, T3, T4> source) {
        return new QuadImpl<>(source);
    }

    /**
     * Retruns a new instance of {@link MutableQuad}
     *
     * @param item1 the first element
     * @param item2 the second element
     * @param item3 the third element
     * @param item4 the fourth element
     * @param <T1>  the type of the first element
     * @param <T2>  the type of the second element
     * @param <T3>  the type of the third element
     * @param <T4>  the type of the fourth element
     * @return the new instance
     */
    public static <T1, T2, T3, T4> MutableQuad<T1, T2, T3, T4> mutableOf(
            T1 item1, T2 item2, T3 item3, T4 item4) {
        return new MutableQuadImpl<>(item1, item2, item3, item4);
    }

    /**
     * Returns a new instance of {@link MutableQuad} with all the values set to null
     *
     * @param <T1> the type of the first element
     * @param <T2> the type of the second element
     * @param <T3> the type of the third element
     * @param <T4> the type of the fourth element
     * @return the new instance
     */
    public static <T1, T2, T3, T4> MutableQuad<T1, T2, T3, T4> newMutableQuad() {
        return new MutableQuadImpl<>(null, null, null, null);
    }

    /**
     * Returns a new instance of {@link MutableQuad}
     *
     * @param source the source of values
     * @param <T1>   the type of the first element
     * @param <T2>   the type of the second element
     * @param <T3>   the type of the third element
     * @param <T4>   the type of the fourth element
     * @return the new instance
     */
    public static <T1, T2, T3, T4> MutableQuad<T1, T2, T3, T4> mutableCopyOf(
            Quad<T1, T2, T3, T4> source) {
        return new MutableQuadImpl<>(source);
    }


}
