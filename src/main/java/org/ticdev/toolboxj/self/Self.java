package org.ticdev.toolboxj.self;

/**
 * A self-referencing extendable interface.
 * 
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 *
 * @param <T>
 *            the self-referenced implementation
 */
public interface Self<T extends Self<T>> {

    /**
     * Returns the self-referencing implementation or extension of this
     * interface.
     * 
     * @return the self-referencing implementation or extension of this
     *         interface.
     */
    T self();

}
