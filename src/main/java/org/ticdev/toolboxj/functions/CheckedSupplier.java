package org.ticdev.toolboxj.functions;

/**
 * A {@link java.util.function.Supplier} alternative which may throw a
 * checked exception.
 *
 * @param <R> the type of elements returned by this supplier.
 * @param <E> the type of exception thrown by this supplier.
 * @author <a href="mailto:tandauioan@gmail.com">Ioan - Ciprian Tandau</a>
 */
@FunctionalInterface
public interface CheckedSupplier<R, E extends Throwable> {

  /**
   * Returns an element.
   *
   * @return the element.
   * @throws E exception thrown if an error occurred.
   */
  R get() throws E;

}
