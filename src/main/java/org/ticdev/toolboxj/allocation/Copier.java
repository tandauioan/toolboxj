package org.ticdev.toolboxj.allocation;

/**
 * Interface for classes that need to provide a copy mechanism for objects.
 * 
 * <p>
 * The type / depth of the copy process is not specified.
 * </p>
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 * @param <T>
 *            the type of object that can be copied.
 */
public interface Copier<T> {

    /**
     * Given the source and destination objects, this method copies the content
     * of the source object into the destination object.
     * 
     * @param source
     *            the object containing the information to be copied.
     * @param destination
     *            the object that will reflect the values of the source object
     */
    void copy(T source, T destination);

}
