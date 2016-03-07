package org.ticdev.toolboxj.tools;

import java.util.Objects;

/**
 * Utility methods for generic objects.
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 */
public class ObjectTools {

    /**
     * Returns true if all the arguments are null and false if any of them is
     * non-null.
     * 
     * @param objects
     *            the objects to check
     * @return true if all the arguments are null and false if any of them is
     *         non-null.
     */
    public boolean allNull(Object... objects) {
        for (Object object : objects) {
            if (object != null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns true if all the arguments are non-null and false if any of them
     * is null.
     * 
     * @param objects
     *            the objects to check
     * @return true if all the arguments are non-null and false if any of them
     *         is null.
     */
    public boolean allNonNull(Object... objects) {
        for (Object object : objects) {
            if (object == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Method used to check that all the parameters are non-null.
     * 
     * @param objects
     *            the objects to check
     * @throws NullPointerException
     *             if any object is null
     */
    public void requireNonNull(Object... objects)
        throws NullPointerException {
        if (!allNonNull(objects)) {
            throw new NullPointerException();
        }
    }

    /**
     * Returns true if all the objects have the same hash (
     * {@link Object#hashCode()}) value.
     * 
     * @param objects
     *            the objects to check
     * @return true if all the objects have the same hash (
     *         {@link Object#hashCode()}) value.
     */
    public boolean allEqualHashes(Object... objects) {
        if (objects.length > 0) {
            Object check = objects[0];
            if (check == null) {
                return allNull(objects);
            } else {
                for (int i = 1; i < objects.length; i++) {
                    if (check.hashCode() != objects[i].hashCode()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Returns true if all the objects are equal ({@link Object#equals(Object)}
     * and false otherwise.
     * 
     * @param objects
     *            the objects to check
     * @return true if all the objects are equal ({@link Object#equals(Object)}
     *         and false otherwise.
     */
    public boolean allEqual(Object... objects) {
        if (objects.length > 0) {
            Object check = objects[0];
            for (int i = 1; i < objects.length; i++) {
                if (!Objects.equals(check, objects[i])) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns true if none of these objects are equal (
     * {@link Object#equals(Object)}) two by two.
     * 
     * @param objects
     *            the objects to check
     * @return true if none of these objects are equal (
     *         {@link Object#equals(Object)}) two by two.
     */
    public boolean nonEqual(Object... objects) {

        for (int i = 0; i < objects.length - 1; i++) {
            for (int j = i + 1; j < objects.length; j++) {
                if (!Objects.equals(objects[i], objects[j])) {
                    return false;
                }
            }
        }
        return true;
    }

}
